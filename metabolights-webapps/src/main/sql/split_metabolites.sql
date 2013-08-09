create or replace
procedure split_metabolites
as

  cursor c1 is
    select s.acc, m.*
    from assaygroup ag, study s, metabolite m
    where
        --s.acc='MTBLS_DEV364' and
        s.id = ag.study_id
        and ag.id = m.assaygroup_id
        and m.identifier like '%|%';

  cursor ids(idents VARCHAR2) is
    select REGEXP_SUBSTR(idents,'[^|]+',1,2) ident from dual; -- Get the 2nd value '|CHEBI:12312'

  cursor nextid is
    select max(id)+1 from metabolite;

  next_number number;

BEGIN

	FOR met IN c1 LOOP -- Loops trough each MTBLS records metabolite records
		dbms_output.put_line('Splitting metabolites for '||met.acc);
    FOR m IN ids(met.identifier) LOOP

      next_number := 0;
      OPEN nextid;
        FETCH nextid into next_number;
      CLOSE nextid;

      dbms_output.put_line(' - Creating separate entry for '|| m.ident||' with identifier '||next_number);

      for r in (select * from metabolite where id = met.id)
      loop
        r.id := next_number;      -- New Primary key
        r.identifier := m.ident;  -- New separate identifier
        insert into metabolite values r;
        -- update original row,
        update metabolite set identifier = replace(identifier,'|'||m.ident,'') where id = met.id;

      end loop;



    END LOOP;
 	END LOOP;

  update hibernate_sequences set sequence_next_hi_value = sequence_next_hi_value + 1 where lower(sequence_name) = 'metabolite';
  -- Need to update hibernate_sequences so new studies can be loaded;
  commit;

END;