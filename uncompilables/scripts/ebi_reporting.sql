drop table curation_log_temp;

create table curation_log_temp as
    select 
        s.acc, 
        s.studysize,
        'Study status'::text as status, 
        to_char(s.releasedate,'YYYY-MM-DD') as releasedate, 
        to_char(s.submissiondate,'YYYY-MM-DD') as submissiondate,
        'This is where the username will go'::text as username,
        s.studytype,
        lpad(replace(acc,'MTBLS',''),4,'0') as acc_short,
        s.id as studyid,
        to_char(s.updatedate,'YYYY-MM-DD') as updatedate,
        0 as nmr_size,
        0 as ms_size,
        to_char(s.releasedate,'YYYYMM') as relmonth, 
        to_char(s.submissiondate,'YYYYMM') as submonth
      from studies s where 1 = 2
      order by acc_short asc;
 
alter table curation_log_temp alter column studysize type bigint;
alter table curation_log_temp alter column nmr_size type bigint;
alter table curation_log_temp alter column ms_size type bigint;

DO $$
BEGIN

  FOR i_acc in 1..2000 LOOP
   insert into curation_log_temp(acc, acc_short) values('MTBLS'||i_acc, i_acc);
  END LOOP;
 
  update curation_log_temp
    set studysize = s.studysize,
        ms_size = s.studysize,
        nmr_size = 0,
        status = case when s.status = 0 then 'Submitted' 
              when s.status = 1 then 'In Curation'
              when s.status = 2 then 'In Review'
              when s.status = 3 then 'Public'
              else 'Dormant' end, 
        releasedate = to_char(s.releasedate,'YYYY-MM-DD'), 
        submissiondate = to_char(s.submissiondate,'YYYY-MM-DD'),
        studytype = s.studytype,
        studyid = s.id,
        updatedate = to_char(s.updatedate,'YYYY-MM-DD'),
        relmonth = to_char(s.releasedate,'YYYYMM'), 
        submonth = to_char(s.submissiondate,'YYYYMM')
      from studies s where s.acc = curation_log_temp.acc;

  update curation_log_temp set acc_short = lpad(acc_short,4,'0');  
  
  update curation_log_temp clt
    set username = us.uname
    from (
      select su.studyid, string_agg(u.firstname||' '||u.lastname,', ') as uname 
      from users u
      join study_user su on u.id = su.userid
      group by su.studyid) as us
    where clt.studyid = us.studyid;
    
   update curation_log_temp clt
    set 
      nmr_size = a.studysize, ms_size = 0
    from ( 
      select studyid, studysize from curation_log_temp where lower(studytype) like '%nmr%') as a
    where clt.studyid = a.studyid;

  update curation_log_temp set ms_size = (studysize-18000),       nmr_size = 18000 where acc='MTBLS200';  
  update curation_log_temp set ms_size = (studysize-12000),       nmr_size = 12000 where acc='MTBLS103';
  update curation_log_temp set ms_size = (studysize-355000),     nmr_size = 355000 where acc='MTBLS336';
  update curation_log_temp set ms_size = (studysize-45000000), nmr_size = 45000000 where acc='MTBLS353';
  
--  update curation_log_temp set studysize = studysize * 1000;
--  update curation_log_temp set ms_size = ms_size * 1000;
--  update curation_log_temp set nmr_size = nmr_size * 1000;

END
$$;      
      
drop table ebi_reporting;

CREATE TABLE ebi_reporting (
  ms_nmr VARCHAR(3)
, submonth VARCHAR(6)
, size VARCHAR(100)
);

insert into ebi_reporting(ms_nmr, submonth, size) 
select 'ms' , submonth, sum(sum(ms_size)) over (order by submonth) 
from curation_log_temp
group by submonth order by submonth desc;

insert into ebi_reporting(ms_nmr, submonth, size) 
select 'nmr', submonth, sum(sum(nmr_size)) over (order by submonth) 
from curation_log_temp
group by submonth order by submonth desc;

delete from ebi_reporting where submonth is null;

\q

commit;
