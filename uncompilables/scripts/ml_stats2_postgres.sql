/**

DROP TABLE ML_STATS;

CREATE TABLE ML_STATS (
  ID SERIAL PRIMARY KEY
, PAGE_SECTION VARCHAR(20) NOT NULL
, STR_NAME VARCHAR(200) NOT NULL
, STR_VALUE VARCHAR(200) NOT NULL
, SORT_ORDER smallint
);

CREATE OR REPLACE FUNCTION _final_median(NUMERIC[])
   RETURNS NUMERIC AS
$$
   SELECT AVG(val)
   FROM (
     SELECT val
     FROM unnest($1) val
     ORDER BY 1
     LIMIT  2 - MOD(array_upper($1, 1), 2)
     OFFSET CEIL(array_upper($1, 1) / 2.0) - 1
   ) sub;
$$
LANGUAGE 'sql' IMMUTABLE;
 
CREATE AGGREGATE median(NUMERIC) (
  SFUNC=array_append,
  STYPE=NUMERIC[],
  FINALFUNC=_final_median,
  INITCOND='{}'
);


**/
truncate table ml_stats;

-- Section "Data"
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Data', 'Total number of studies', count(*), 1 from studies where status != 4;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Data', ' - Public', count(*), 2 from studies where status = 3;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Data', ' - in Review', count(*), 3 from studies where status = 2;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Data', ' - in Curation', count(*), 4 from studies where status = 1;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Data', ' - in Submission', count(*), 5 from studies where status = 0;
--insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Data', ' - Dormant', count(*), 6 from studies where status = 4;
--insert into ml_stats(page_section,str_name,str_value,sort_order) select distinct 'Data', 'Different organisms', count(*), 7 from ref_species where final_id is null and species_member is not null;
insert into ml_stats(page_section,str_name,str_value,sort_order) select distinct 'Data', 'Different organisms', count(*), 7 from ref_species where final_id is null;
insert into ml_stats(page_section,str_name,str_value,sort_order) select distinct 'Data', 'Reference compounds', count(*), 8 from ref_metabolite;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Data','Total study size (TB)', round(sum(studysize)/1024/1024/1024,1), 9 from studies;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Data','- Max study size (TB)', round(max(studysize)/1024/1024/1024,1), 10 from studies;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Data','- Average study size (GB)', round(avg(studysize)/1024/1024,2), 11 from studies where status != 4;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Data','- Median study size (GB)', round(median(studysize)/1024/1024,2), 12 from studies where status != 4;


-- Section "Submitters"
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Submitters', 'Number of registered submitters', count(*), 1 from users where role != 1;
--insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Submitters', 'Number of curator accounts', count(*), 3 from users where role = 1;
insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Submitters', 'Number of countries', count(distinct address), 2 from users;

-- Section "Most active submitters"
insert into ml_stats(page_section,str_name,str_value,sort_order)
select 'Topsubmitters', u.firstname||' '||u.lastname, count(s.acc), count(s.acc)*-1
from studies s, study_user s2u, users u
where
  s.id = s2u.studyid and
  s2u.userid = u.id and
  s.status != 0 -- Ignore submitted
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

insert into ml_stats(page_section,str_name,str_value,sort_order) select 'Info', 'Last updated', to_char(current_timestamp,'DD-Mon-YYYY HH24:MI:SS'), 1;

update ml_stats set sort_order = 999 where sort_order is null;

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

\q
