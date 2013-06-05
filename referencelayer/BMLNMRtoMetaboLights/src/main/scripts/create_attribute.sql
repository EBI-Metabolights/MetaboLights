set scan off;
truncate table attribute;
drop table attribute;
truncate table attribute_def;
drop table attribute_def;

DROP SEQUENCE ATTRIBUTE_DEF_SEQ;
DROP SEQUENCE ATTRIBUTE_SEQ;

CREATE SEQUENCE ATTRIBUTE_DEF_SEQ INCREMENT BY 1 START WITH 1 NOCACHE;
CREATE SEQUENCE ATTRIBUTE_SEQ INCREMENT BY 1 START WITH 1 NOCACHE;

CREATE TABLE ATTRIBUTE_DEF(
  ID NUMBER NOT NULL 
, NAME VARCHAR2(500) 
, DESCRIPTION VARCHAR2(500) 
, CONSTRAINT ATTRIBUTE_DEF_PK PRIMARY KEY(ID) ENABLE
);

CREATE TABLE ATTRIBUTE(
  ID NUMBER NOT NULL 
, ATTRIBUTE_DEF_ID NUMBER NOT NULL 
, SPECTRA_ID NUMBER 
, VALUE VARCHAR2(4000) 
, CONSTRAINT ATTRIBUTE_PK PRIMARY KEY(ID) ENABLE
);


CREATE OR REPLACE TRIGGER ISATAB.ATTRIBUTE_DEF_TRG 
   before insert on ISATAB.ATTRIBUTE_DEF 
   for each row 
begin  
   if inserting then 
      if :NEW.ID is null then 
         select ATTRIBUTE_DEF_SEQ.nextval into :NEW.ID from dual; 
      end if; 
   end if; 
end;
/

CREATE OR REPLACE TRIGGER ISATAB.ATTRIBUTE_TRG 
   before insert on ISATAB.ATTRIBUTE 
   for each row 
begin  
   if inserting then 
      if :NEW.ID is null then 
         select ATTRIBUTE_SEQ.nextval into :NEW.ID from dual; 
      end if; 
   end if; 
end;
/

ALTER TRIGGER ISATAB.ATTRIBUTE_DEF_TRG ENABLE;
ALTER TRIGGER ISATAB.ATTRIBUTE_TRG ENABLE;



INSERT INTO attribute_def(id, name, description) VALUES (ATTRIBUTE_SEQ.nextval,'A','KEN A');
INSERT INTO attribute(attribute_def_id, value) VALUES (ATTRIBUTE_SEQ.currval,'KEN A value');
commit;

select * from attribute;
select * from attribute_def;