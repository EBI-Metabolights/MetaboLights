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
, CONSTRAINT REF_ATTRIBUTE_SPECTRA_FK FOREIGN KEY (SPECTRA_ID) REFERENCES ISATAB.REF_MET_SPECTRA (ID) ON DELETE CASCADE ENABLE
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





commit;


delete from ref_met_spectra where met_id = 1000070;
insert into ref_met_spectra(id, name, path_to_json, spectra_type, met_id) values(REF_MET_SPECTRA_SEQ.nextval,'BML_Analysis_1265','/nfs/public/rw/homes/tc_cm01/reference/MTBLC16811/BML_Analysis_1265.xml.json','NMR','1000070');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(1,REF_MET_SPECTRA_SEQ.currval,'7.0');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(2,REF_MET_SPECTRA_SEQ.currval,'1D');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(3,REF_MET_SPECTRA_SEQ.currval,'1H');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(4,REF_MET_SPECTRA_SEQ.currval,'10.0 seconds');


insert into ref_met_spectra(id, name, path_to_json, spectra_type, met_id) values(REF_MET_SPECTRA_SEQ.nextval,'BML_Analysis_1266','/nfs/public/rw/homes/tc_cm01/reference/MTBLC16811/BML_Analysis_1266.xml.json','NMR','1000070');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(1,REF_MET_SPECTRA_SEQ.currval,'7.0');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(2,REF_MET_SPECTRA_SEQ.currval,'1D');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(3,REF_MET_SPECTRA_SEQ.currval,'1H');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(4,REF_MET_SPECTRA_SEQ.currval,'3.0 seconds');

insert into ref_met_spectra(id, name, path_to_json, spectra_type, met_id) values(REF_MET_SPECTRA_SEQ.nextval,'BML_Analysis_1267','/nfs/public/rw/homes/tc_cm01/reference/MTBLC16811/BML_Analysis_1267.xml.json','NMR','1000070');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(1,REF_MET_SPECTRA_SEQ.currval,'7.0');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(2,REF_MET_SPECTRA_SEQ.currval,'1D');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(3,REF_MET_SPECTRA_SEQ.currval,'1H');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(4,REF_MET_SPECTRA_SEQ.currval,'3.0 seconds');

insert into ref_met_spectra(id, name, path_to_json, spectra_type, met_id) values(REF_MET_SPECTRA_SEQ.nextval,'BML_Analysis_1268','/nfs/public/rw/homes/tc_cm01/reference/MTBLC16811/BML_Analysis_1268.xml.json','NMR','1000070');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(1,REF_MET_SPECTRA_SEQ.currval,'7.0');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(2,REF_MET_SPECTRA_SEQ.currval,'1D');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(3,REF_MET_SPECTRA_SEQ.currval,'1H');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(4,REF_MET_SPECTRA_SEQ.currval,'10.0 seconds');

insert into ref_met_spectra(id, name, path_to_json, spectra_type, met_id) values(REF_MET_SPECTRA_SEQ.nextval,'BML_Analysis_1271','/nfs/public/rw/homes/tc_cm01/reference/MTBLC16811/BML_Analysis_1271.xml.json','NMR','1000070');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(1,REF_MET_SPECTRA_SEQ.currval,'7.0');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(2,REF_MET_SPECTRA_SEQ.currval,'1D');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(3,REF_MET_SPECTRA_SEQ.currval,'1H');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(4,REF_MET_SPECTRA_SEQ.currval,'0.5 seconds');

insert into ref_met_spectra(id, name, path_to_json, spectra_type, met_id) values(REF_MET_SPECTRA_SEQ.nextval,'BML_Analysis_1272','/nfs/public/rw/homes/tc_cm01/reference/MTBLC16811/BML_Analysis_1272.xml.json','NMR','1000070');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(1,REF_MET_SPECTRA_SEQ.currval,'7.0');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(2,REF_MET_SPECTRA_SEQ.currval,'1D');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(3,REF_MET_SPECTRA_SEQ.currval,'1H');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(4,REF_MET_SPECTRA_SEQ.currval,'0.75 seconds');

insert into ref_met_spectra(id, name, path_to_json, spectra_type, met_id) values(REF_MET_SPECTRA_SEQ.nextval,'BML_Analysis_1273','/nfs/public/rw/homes/tc_cm01/reference/MTBLC16811/BML_Analysis_1273.xml.json','NMR','1000070');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(1,REF_MET_SPECTRA_SEQ.currval,'7.0');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(2,REF_MET_SPECTRA_SEQ.currval,'1D');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(3,REF_MET_SPECTRA_SEQ.currval,'1H');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(4,REF_MET_SPECTRA_SEQ.currval,'1.0 seconds');

insert into ref_met_spectra(id, name, path_to_json, spectra_type, met_id) values(REF_MET_SPECTRA_SEQ.nextval,'BML_Analysis_1274','/nfs/public/rw/homes/tc_cm01/reference/MTBLC16811/BML_Analysis_1274.xml.json','NMR','1000070');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(1,REF_MET_SPECTRA_SEQ.currval,'7.0');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(2,REF_MET_SPECTRA_SEQ.currval,'1D');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(3,REF_MET_SPECTRA_SEQ.currval,'1H');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(4,REF_MET_SPECTRA_SEQ.currval,'1.5 seconds');

insert into ref_met_spectra(id, name, path_to_json, spectra_type, met_id) values(REF_MET_SPECTRA_SEQ.nextval,'BML_Analysis_1275','/nfs/public/rw/homes/tc_cm01/reference/MTBLC16811/BML_Analysis_1275.xml.json','NMR','1000070');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(1,REF_MET_SPECTRA_SEQ.currval,'7.0');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(2,REF_MET_SPECTRA_SEQ.currval,'1D');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(3,REF_MET_SPECTRA_SEQ.currval,'1H');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(4,REF_MET_SPECTRA_SEQ.currval,'2.0 seconds');

insert into ref_met_spectra(id, name, path_to_json, spectra_type, met_id) values(REF_MET_SPECTRA_SEQ.nextval,'BML_Analysis_1276','/nfs/public/rw/homes/tc_cm01/reference/MTBLC16811/BML_Analysis_1276.xml.json','NMR','1000070');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(1,REF_MET_SPECTRA_SEQ.currval,'7.0');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(2,REF_MET_SPECTRA_SEQ.currval,'1D');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(3,REF_MET_SPECTRA_SEQ.currval,'1H');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(4,REF_MET_SPECTRA_SEQ.currval,'5.0 seconds');

insert into ref_met_spectra(id, name, path_to_json, spectra_type, met_id) values(REF_MET_SPECTRA_SEQ.nextval,'BML_Analysis_1277','/nfs/public/rw/homes/tc_cm01/reference/MTBLC16811/BML_Analysis_1277.xml.json','NMR','1000070');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(1,REF_MET_SPECTRA_SEQ.currval,'7.0');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(2,REF_MET_SPECTRA_SEQ.currval,'2D J-resolved');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(3,REF_MET_SPECTRA_SEQ.currval,'1H');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(4,REF_MET_SPECTRA_SEQ.currval,'3.0 seconds');

insert into ref_met_spectra(id, name, path_to_json, spectra_type, met_id) values(REF_MET_SPECTRA_SEQ.nextval,'BML_Analysis_1279','/nfs/public/rw/homes/tc_cm01/reference/MTBLC16811/BML_Analysis_1279.xml.json','NMR','1000070');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(1,REF_MET_SPECTRA_SEQ.currval,'7.4');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(2,REF_MET_SPECTRA_SEQ.currval,'2D J-resolved');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(3,REF_MET_SPECTRA_SEQ.currval,'1H');
insert into ref_attribute(attribute_def_id, spectra_id, value) values(4,REF_MET_SPECTRA_SEQ.currval,'3.0 seconds');



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