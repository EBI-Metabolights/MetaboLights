--WARNING: This will remove the users and the stable id

declare

  cursor c1 is
    select table_name, constraint_name from user_constraints  where owner = 'ISATAB';
  
  cursor c2 is
    select table_name from user_tables;

begin

 for cur in c1 loop
    execute immediate 'ALTER TABLE ISATAB.'||cur.table_name||'  MODIFY CONSTRAINT '||cur.constraint_name||' disable';
 end loop;

 for cur2 in c2 loop
   begin
     execute immediate 'ALTER TABLE ISATAB.'||cur2.table_name||' disable all triggers';
     execute immediate 'TRUNCATE TABLE ISATAB.'||cur2.table_name;
     execute immediate 'ALTER TABLE ISATAB.'||cur2.table_name||' enable all triggers';
   exception when others then null;
  end;
 end loop;

 for cur in c1 loop
    execute immediate 'ALTER TABLE ISATAB.'||cur.table_name||'  MODIFY CONSTRAINT '||cur.constraint_name||' enable';
 end loop;
   
end;
/