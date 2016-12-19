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
        s.id as studyid
      from studies s where 1 = 2
      order by acc_short asc;

DO $$
BEGIN

  FOR i_acc in 1..500 LOOP
   insert into curation_log_temp(acc, acc_short) values('MTBLS'||i_acc, i_acc);
  END LOOP;
 
  update curation_log_temp
    set studysize = s.studysize,
        status = case when s.status = 0 then 'Submitted' 
              when s.status = 1 then 'In Curation'
              when s.status = 2 then 'In Review'
              when s.status = 3 then 'Public'
              else 'Dormant' end, 
        releasedate = to_char(s.releasedate,'YYYY-MM-DD'), 
        submissiondate = to_char(s.submissiondate,'YYYY-MM-DD'),
        studytype = s.studytype,
        studyid = s.id 
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
  
END
$$;      
      
      
select * from curation_log_temp order by acc_short asc;

