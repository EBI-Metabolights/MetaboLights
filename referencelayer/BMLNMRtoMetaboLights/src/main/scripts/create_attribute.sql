set scan off;
truncate table REF_attribute;
drop table REF_attribute purge;
truncate table REF_attribute_def;
drop table REF_attribute_def purge;


DROP SEQUENCE REF_ATTRIBUTE_DEF_SEQ;
DROP SEQUENCE REF_ATTRIBUTE_SEQ;

CREATE SEQUENCE REF_ATTRIBUTE_DEF_SEQ INCREMENT BY 1 START WITH 1 NOCACHE;
CREATE SEQUENCE REF_ATTRIBUTE_SEQ INCREMENT BY 1 START WITH 1 NOCACHE;

CREATE TABLE REF_ATTRIBUTE_DEF(
  ID NUMBER NOT NULL 
, NAME VARCHAR2(500) 
, DESCRIPTION VARCHAR2(500) 
, CONSTRAINT REF_ATTRIBUTE_DEF_PK PRIMARY KEY(ID) ENABLE
);

CREATE TABLE REF_ATTRIBUTE(
  ID NUMBER NOT NULL 
, ATTRIBUTE_DEF_ID NUMBER NOT NULL 
, SPECTRA_ID NUMBER 
, VALUE VARCHAR2(4000) 
, CONSTRAINT REF_ATTRIBUTE_PK PRIMARY KEY(ID) ENABLE
);


CREATE OR REPLACE TRIGGER ISATAB.REF_ATTRIBUTE_DEF_TRG 
   before insert on ISATAB.REF_ATTRIBUTE_DEF 
   for each row 
begin  
   if inserting then 
      if :NEW.ID is null then 
         select REF_ATTRIBUTE_DEF_SEQ.nextval into :NEW.ID from dual; 
      end if; 
   end if; 
end;
/

CREATE OR REPLACE TRIGGER ISATAB.REF_ATTRIBUTE_TRG 
   before insert on ISATAB.REF_ATTRIBUTE 
   for each row 
begin  
   if inserting then 
      if :NEW.ID is null then 
         select REF_ATTRIBUTE_SEQ.nextval into :NEW.ID from dual; 
      end if; 
   end if; 
end;
/

ALTER TRIGGER ISATAB.REF_ATTRIBUTE_DEF_TRG ENABLE;
ALTER TRIGGER ISATAB.REF_ATTRIBUTE_TRG ENABLE;


insert into ref_attribute_def(id, name,description) values(1, 'pH','pH value');
insert into ref_attribute_def(id, name,description) values(2, 'pulseSequence','Pulse Sequence');
insert into ref_attribute_def(id, name,description) values(3, 'acquisitionNucleus','Acquisition Nucleus');
insert into ref_attribute_def(id, name,description) values(4, 'relaxationDelay','Relaxation Delay');



/*

--Option 1
INSERT INTO REF_attribute_def(id, name, description) VALUES (REF_ATTRIBUTE_DEF_SEQ.nextval,'A','KEN A');
INSERT INTO REF_attribute(attribute_def_id, value) VALUES (REF_ATTRIBUTE_DEF_SEQ.currval,'KEN A value');
commit;

select * from REF_attribute;
select * from REF_attribute_def;

--Option 2
variable attdefid number
exec select REF_ATTRIBUTE_DEF_SEQ.nextval into :attdefid from dual;

INSERT INTO REF_attribute_def(id, name, description) VALUES (:attdefid,'B','KEN B');
INSERT INTO REF_attribute(attribute_def_id, value) VALUES (:attdefid,'KEN B value');
commit;

select * from REF_attribute;
select * from REF_attribute_def;


-- More 
*/