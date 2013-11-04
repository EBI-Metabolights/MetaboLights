/**
DROP TABLE ML_STATS;

CREATE TABLE ML_STATS (
  ID NUMBER(10, 0) NOT NULL
, PAGE_SECTION VARCHAR2(20) NOT NULL
, STR_NAME VARCHAR2(200) NOT NULL
, STR_VALUE VARCHAR2(200) NOT NULL
, SORT_ORDER NUMBER(1)
, CONSTRAINT ML_STATS_PK PRIMARY KEY (ID) ENABLE
);

DROP SEQUENCE ML_STATS_SEQ;
CREATE SEQUENCE ML_STATS_SEQ INCREMENT BY 1 START WITH 1 NOCACHE;

set scan off;
CREATE OR REPLACE TRIGGER ML_STATS_TRG before
  INSERT ON ML_STATS FOR EACH row
  BEGIN
    IF inserting THEN
      IF :NEW.ID IS NULL THEN
        SELECT ML_STATS_SEQ.nextval INTO :NEW.ID FROM dual;
      END IF;
  END IF;
END;
/

ALTER TRIGGER ML_STATS_TRG ENABLE;

**/

truncate table ml_stats;

-- Section "Data"
insert into ml_stats(page_section,str_name,str_value) select 'Data', 'Total number of studies', count(*) from study;
insert into ml_stats(page_section,str_name,str_value) select 'Data', decode(status,1,'Private studies','Public studies'), count(*) from study group by status;
insert into ml_stats(page_section,str_name,str_value) select 'Data', 'Number of protocols', count(*) from protocol;
insert into ml_stats(page_section,str_name,str_value) select 'Data', 'Number of samples', count(*) from assay;
insert into ml_stats(page_section,str_name,str_value) select 'Data', 'Different organisms', count(distinct PV.VALUE)
    FROM PROPERTY_VALUE PV
      LEFT JOIN PROPERTY P ON PV.PROPERTY_ID = P.ID
      LEFT JOIN MATERIAL M ON PV.MATERIAL_ID = M.ID
      LEFT JOIN NODE N ON M.NODE_ID = N.ID
    WHERE LOWER(P.VALUE) = 'organism'
      AND PV.VALUE <> 'none';

-- Section "Metabolites identified"
insert into ml_stats(page_section,str_name,str_value) select 'Identified', DB, Count(*) as Total FROM (select CASE
  WHEN instr(identifier,'CHEBI:')=1 THEN 'ChEBI'
  WHEN instr(identifier,'CID')=1 THEN 'PubChem'
  WHEN instr(identifier,'HMDB')=1 THEN 'HMDB'
  WHEN instr(identifier,'LM')=1 THEN 'LIPID MAPS'
  WHEN instr(identifier,'MPIMP')=1 THEN 'GOLM'
  WHEN instr(identifier,'GMD')=1 THEN 'GOLM'
  WHEN instr(identifier,'C')=1 THEN 'KEGG'
  WHEN instr(identifier,'unknown')=1 THEN 'Unknown'
  WHEN identifier IS NULL THEN 'not mapped to any database'
  ELSE 'Others' --ELSE initCap(identifier)
END AS DB from METABOLITE)
group by DB;

insert into ml_stats(page_section,str_name,str_value) select 'Identified',' --- Total', Count(*) as Total from METABOLITE;

-- Section "Submitters"
insert into ml_stats(page_section,str_name,str_value) select 'Submitters', 'Number of registered users', count(*) from user_detail;

commit;

purge recyclebin;
exit




