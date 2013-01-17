truncate table study_compound;
drop table study_compound;

-- All public studies and all metabolites
create table study_compound as
  select 
      'STUDY' as source, s.id as entry_id, s.acc as name, TO_CHAR(dbms_lob.substr(s.title, 3999, 1 )) as title, 
      s.submissiondate, s.releasedate, TO_CHAR(dbms_lob.substr(s.description, 3999, 1 )) as description, null as chebi_id, null as inchi, null as formula, null as iupac
    from study s where status = 0 
     UNION
    select distinct 'COMPOUND' as source, rm.id as entry_id, rm.acc as name, 
    replace(replace(replace(replace(trim(rm.name),'/',' '),'|',' '),'?',' '),'  ',' ') as title,  
      rm.created_date as submissiondate, nvl(rm.updated_date,rm.created_date) as releasedate, rm.description as description, rm.temp_id as chebi_id, rm.inchi as inch, rm.formula as formula, rm.iupac_names as iupac
    from
      ref_metabolite rm;
      
      
truncate table study_compound_ref;
drop table study_compound_ref;
purge recyclebin;

-- Studies linked to MTBLC entries (entry_id in ) 
create table study_compound_ref as 
  select distinct
    rm.id as entry_id, 
    s.acc as id, 
    'study_id' as ref_type,
    decode(substr(m.identifier,1,3), 
              'CHE','chebi',
              'HMD','hmdb', 
              'CID','pubchem',
              'MTB','metabolights',
              'LMP','Lipid Maps',
              'LMG','Lipid Maps',
              'C01','KEGG','C02','KEGG','C03','KEGG','C04','KEGG','C05','KEGG','C06','KEGG','C07','KEGG','C08','KEGG','C09','KEGG','C10','KEGG','C11','KEGG','C12','KEGG','C13','KEGG','C14','KEGG','C15','KEGG',
              'C16','KEGG','C17','KEGG','C18','KEGG','C19','KEGG','C20','KEGG',
              'OTHER') as database
    from
      ref_metabolite rm, 
      study s,
      assaygroup ag,
      metabolite m
    where 
      rm.temp_id = m.identifier and
      m.assaygroup_id = ag.id AND
      ag.study_id = s.id;  


commit;
exit

