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
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Data', 'Total number of studies', count(*), 1 from studies;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Data', ' - Public', count(*), 2 from studies where status = 3;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Data', ' - in Review', count(*), 3 from studies where status = 2;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Data', ' - in Curation', count(*), 4 from studies where status = 1;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Data', ' - in Submission', count(*), 5 from studies where status = 0;
insert into ml_stats(page_section,str_name,str_value,sort_order) select distinct 'Data', 'Different organisms', count(*), 6 from ref_species where final_id is null and species_member is not null;
insert into ml_stats(page_section,str_name,str_value,sort_order) select distinct 'Data', 'Reference compounds', count(*), 7 from ref_metabolite;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Data','Total study size (TB)', round(sum(studysize)/1000/1000/1000,2), 8 from studies;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Data','- Max study size (GB)', round(max(studysize)/1000/1000,2), 9 from studies;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Data','- Average study size (GB)', round(avg(studysize)/1000/1000,2), 10 from studies;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Data','- Median study size (GB)', round(median(studysize)/1000/1000,2), 11 from studies;


-- Section "Submitters"
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Submitters', 'Number of registered submitters', count(*), 1 from users;
--insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Submitters', 'Number of reviewers', count(*), 2 from user_detail where role = 2;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Submitters', 'Number of curators', count(*), 3 from users where role = 1;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Submitters', 'Number of countries', count(distinct address), 4 from users;

-- Section "Most active submitters"
insert into ml_stats(page_section,str_name,str_value,sort_order)
select 'Topsubmitters', u.firstname||' '||u.lastname, count(s.acc), 1
from studies s, study_user s2u, users u
where
  s.id = s2u.studyid and
  s2u.userid = u.id
  group by u.firstname||' '||u.lastname
  having count(s.acc) >=4
  order by 3 desc;

--Force a different sort order, most submissions first
update ml_stats set sort_order = rownum where page_section = 'Topsubmitters';

insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Info', 'Last updated', to_char(sysdate,'DD-Mon-YYYY HH24:MI:SS'), 1 from dual;

update ml_stats set sort_order = 999 where sort_order is null;

update metabolights_parameters set value = (select listagg(acc,',')  WITHIN GROUP (order by acc) from  (select acc, releasedate from studies where status = 0 order by 2 desc) where rownum <= 10)
where name = 'GALLERY_ITEMS';

commit;

purge recyclebin;
exit
