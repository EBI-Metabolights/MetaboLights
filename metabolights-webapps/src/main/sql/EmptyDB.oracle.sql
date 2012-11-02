--WARNING: This will NOT remove the users and the stable id (Comment the IF if you want to do so).

declare

  cursor c1 is
    select table_name, constraint_name from user_constraints  where owner = 'ISATAB' and table_name not in('DB','REF_ENTRY','REF_XREF','REL_TYPE','REF_METABOLITE');
  
  cursor c2 is
    select table_name from user_tables where table_name not in('DB','REF_ENTRY','REF_XREF','REL_TYPE','REF_METABOLITE');

begin

 for cur in c1 loop
    execute immediate 'ALTER TABLE ISATAB.'||cur.table_name||'  MODIFY CONSTRAINT '||cur.constraint_name||' disable';
 end loop;

 for cur2 in c2 loop
   begin
	 IF (cur2.table_name <> 'USER_DETAIL' AND cur2.table_name <> 'STABLE_ID') THEN
     	execute immediate 'ALTER TABLE ISATAB.'||cur2.table_name||' disable all triggers';
     	execute immediate 'TRUNCATE TABLE ISATAB.'||cur2.table_name;
     	execute immediate 'ALTER TABLE ISATAB.'||cur2.table_name||' enable all triggers';
     	drop sequence ISATAB.USER_DETAIL_SEQ;
     END IF;
   exception when others then null;
  end;
 end loop;

 for cur in c1 loop
    execute immediate 'ALTER TABLE ISATAB.'||cur.table_name||'  MODIFY CONSTRAINT '||cur.constraint_name||' enable';
 end loop;
   
end;
/