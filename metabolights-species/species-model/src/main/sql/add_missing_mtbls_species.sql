-- Get new species from studies
  insert into ref_species(species,description, taxon) select studies.species,'From MTBLS records',null from 
    (SELECT DISTINCT PV.VALUE as species
    FROM 
      PROPERTY_VALUE PV, PROPERTY P, MATERIAL M, NODE N, STUDY S
    WHERE
      PV.PROPERTY_ID = P.ID and
      PV.MATERIAL_ID = M.ID and
      M.NODE_ID = N.ID and
      N.STUDY_ID = S.ID AND 
      LOWER(P.VALUE) = 'organism' AND 
      PV.VALUE <> 'none') studies
    where trim(UPPER(studies.species)) not in (select trim(UPPER(species)) from ref_species)
      ;     

-- Create new crossreferences for new MTBLS      
-- select 'insert into ref_xref(acc,db_id) values('''|| acc ||''',2);' from study where status = 0 and acc not in(select acc from ref_xref where acc like 'MTBLS%');   
insert into ref_xref(acc,db_id) select acc,2 from study where status = 0 and acc not in(select acc from ref_xref where acc like 'MTBLS%'); 
      
-- Create new links between species and metabolites from studies
insert into ref_met_to_species(met_id, species_id, ref_xref_id, db_id) 
select 
  s.metabolite_id as met_id,
  spe.id as species_id,
  xref.id as ref_xref_id,
  2 as db_id
from 
  ( select distinct rm.id as metabolite_id, s.id as study_id, s.acc as study_acc
    from ref_metabolite rm, study s, assaygroup ag, metabolite m
    where 
      rm.temp_id = m.identifier and --chebi ids
      m.assaygroup_id = ag.id AND
      ag.study_id = s.id) s,
  ( select id, acc from ref_xref where acc like 'MTBLS%') xref,
  ( SELECT DISTINCT species.id, s.acc as study_acc 
    FROM PROPERTY_VALUE PV, PROPERTY P, MATERIAL M, NODE N, STUDY S, ref_species species
    WHERE
      PV.PROPERTY_ID = P.ID and
      PV.MATERIAL_ID = M.ID and
      M.NODE_ID = N.ID and
      N.STUDY_ID = S.ID AND 
      LOWER(P.VALUE) = 'organism' AND 
      PV.VALUE <> 'none' and 
      PV.VALUE = species.SPECIES and 
      species.DESCRIPTION = 'From MTBLS records') spe
where
  s.study_acc = xref.acc and
  s.study_acc = spe.study_acc; 
