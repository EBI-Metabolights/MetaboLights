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
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Data', ' - Dormant', count(*), 6 from studies where status = 4;
insert into ml_stats(page_section,str_name,str_value,sort_order) select distinct 'Data', 'Different organisms', count(*), 7 from ref_species where final_id is null and species_member is not null;
insert into ml_stats(page_section,str_name,str_value,sort_order) select distinct 'Data', 'Reference compounds', count(*), 8 from ref_metabolite;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Data','Total study size (TB)', round(sum(studysize)/1024/1024/1024,1), 9 from studies;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Data','- Max study size (TB)', round(max(studysize)/1024/1024/1024,1), 10 from studies;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Data','- Average study size (GB)', round(avg(studysize)/1024/1024,2), 11 from studies where status != 4;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Data','- Median study size (GB)', round(median(studysize)/1024/1024,2), 12 from studies where status != 4;


-- Section "Submitters"
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Submitters', 'Number of registered submitters', count(*), 1 from users;
--insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Submitters', 'Number of reviewers', count(*), 2 from user_detail where role = 2;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Submitters', 'Number of curator accounts', count(*), 3 from users where role = 1;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Submitters', 'Number of countries', count(distinct address), 4 from users;

-- Section "Most active submitters"
insert into ml_stats(page_section,str_name,str_value,sort_order)
select 'Topsubmitters', u.firstname||' '||u.lastname, count(s.acc), 1
from studies s, study_user s2u, users u
where
  s.id = s2u.studyid and
  s2u.userid = u.id
  group by u.firstname||' '||u.lastname
  having count(s.acc) >= 5
  order by 3 desc;


-- Section for growth stats
insert into ml_stats(page_section, str_name, str_value, sort_order)
select 'Stats_size', to_char(submissiondate,'YYYY-MM'), sum(sum(studysize)) over (order by to_char(submissiondate,'YYYY-MM')),'0' from studies
group by to_char(submissiondate,'YYYY-MM') order by to_char(submissiondate,'YYYY-MM') asc;

insert into ml_stats(page_section, str_name, str_value, sort_order)  
select 'Stats_number', to_char(submissiondate,'YYYY-MM'), sum(count(*)) over (order by to_char(submissiondate,'YYYY-MM')), '0' 
from studies
group by to_char(submissiondate,'YYYY-MM') order by to_char(submissiondate,'YYYY-MM') asc;

--Force a different sort order, most submissions first
update ml_stats set sort_order = rownum where page_section = 'Topsubmitters';

insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Info', 'Last updated', to_char(sysdate,'DD-Mon-YYYY HH24:MI:SS'), 1 from dual;

update ml_stats set sort_order = 999 where sort_order is null;

update metabolights_parameters set value = (select listagg(acc,',')  WITHIN GROUP (order by acc) from  (select acc, releasedate from studies where status = 0 order by 2 desc) where rownum <= 10)
where name = 'GALLERY_ITEMS';

--insert static values for months that had no data submission
insert into ml_stats(page_section, str_name, str_value,sort_order) values('Stats_size','2012-06','15839452','0');
insert into ml_stats(page_section, str_name, str_value,sort_order) values('Stats_size','2012-07','15839452','0');
insert into ml_stats(page_section, str_name, str_value,sort_order) values('Stats_size','2012-12','26829660','0');
insert into ml_stats(page_section, str_name, str_value,sort_order) values('Stats_size','2013-12','306277328','0');
insert into ml_stats(page_section, str_name, str_value,sort_order) values('Stats_size','2014-01','306277328','0');
insert into ml_stats(page_section, str_name, str_value,sort_order) values('Stats_size','2014-04','517913000','0');

insert into ml_stats(page_section, str_name, str_value,sort_order) values('Stats_number','2012-06','6','0');
insert into ml_stats(page_section, str_name, str_value,sort_order) values('Stats_number','2012-07','6','0');
insert into ml_stats(page_section, str_name, str_value,sort_order) values('Stats_number','2012-12','12','0');
insert into ml_stats(page_section, str_name, str_value,sort_order) values('Stats_number','2013-12','41','0');
insert into ml_stats(page_section, str_name, str_value,sort_order) values('Stats_number','2014-01','41','0');
insert into ml_stats(page_section, str_name, str_value,sort_order) values('Stats_number','2014-04','50','0');

commit;

purge recyclebin;
exit