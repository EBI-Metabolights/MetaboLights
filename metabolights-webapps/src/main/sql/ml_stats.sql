/**
DROP TABLE ML_STATS;

CREATE TABLE ML_STATS (
  ID NUMBER(10, 0) NOT NULL
, PAGE_SECTION VARCHAR2(20) NOT NULL
, STR_NAME VARCHAR2(200) NOT NULL
, STR_VALUE VARCHAR2(200) NOT NULL
, SORT_ORDER NUMBER(10)
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
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Data', 'Total number of studies', count(*), 1 from study;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Data', decode(status,1,' - Private studies',' - Public studies'), count(*), 2 from study group by status;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Data', 'Number of protocols', count(*), 3 from protocol;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Data', 'Number of samples', count(*), 4 from assay;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Data', 'Different organisms from submitted studies', count(distinct PV.VALUE), 5
    FROM PROPERTY_VALUE PV
      LEFT JOIN PROPERTY P ON PV.PROPERTY_ID = P.ID
      LEFT JOIN MATERIAL M ON PV.MATERIAL_ID = M.ID
      LEFT JOIN NODE N ON M.NODE_ID = N.ID
    WHERE LOWER(P.VALUE) = 'organism'
      AND PV.VALUE <> 'none';
insert into ml_stats(page_section,str_name,str_value,sort_order) select distinct 'Data', 'Different organisms from compounds', count(*), 6 from ref_species where final_id is null and species_member is not null;
insert into ml_stats(page_section,str_name,str_value,sort_order) select distinct 'Data', 'Reference compounds', count(*), 7 from ref_metabolite;

--Split any reported metabolies with "|"
exec split_metabolites;

-- Section "Metabolites identified"
/**insert into ml_stats(page_section,str_name,str_value, sort_order) select 'Identified', DB, Count(*) as Total, '999' FROM (select CASE
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
**/
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Identified','Total', Count(*) as Total, 1 from METABOLITE;

-- Section "Submitters"
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Submitters', 'Number of registered submitters', count(*), 1 from user_detail;
--insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Submitters', 'Number of reviewers', count(*), 2 from user_detail where role = 2;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Submitters', 'Number of curators', count(*), 3 from user_detail where role = 1;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Submitters', 'Number of countries', count(distinct address), 4 from user_detail;

-- Section "Most active submitters"
insert into ml_stats(page_section,str_name,str_value,sort_order)
select 'Topsubmitters', u.firstname||' '||u.lastname, count(s.acc), 1
from study s, study2user s2u, user_detail u
where
  s.id = s2u.study_id and
  s2u.user_id = u.id
  group by u.firstname||' '||u.lastname
  having count(s.acc) >=3
  order by 3 desc;

--Force a different sort order, most submissions first
update ml_stats set sort_order = rownum where page_section = 'Topsubmitters';

update ml_stats set sort_order = 999 where sort_order is null;

update metabolights_parameters set value = (select listagg(acc,',')  WITHIN GROUP (order by acc) from  (select acc, releasedate from study where status = 0 order by 2 desc) where rownum <= 10)
where name = 'GALLERY_ITEMS';

commit;

purge recyclebin;
exit




