#!/bin/bash
:'
This funcation should exist in the database first:

create or replace function numberofpipedentries() returns integer as $$
DECLARE
lines record;
part text[];
parts text;
numberofparts integer := 0;
BEGIN
  FOR lines in
    select database_identifier from maf_info where database_identifier like '%|%'
  LOOP
    select into part string_to_array(lines.database_identifier,'|');   
    foreach parts in array part loop
      numberofparts := numberofparts + 1;  
    end loop;  
  END LOOP;
  
  return numberofparts;
END;
$$ LANGUAGE plpgsql;" 
' 

source ./postgres.properties.prod
cd $HOME/metabolights/prod/studies/stage/private/
rm all_files.sql file.tsv temp.tsv
OIFS="$IFS"
IFS=$'\n'

echo "truncate table maf_info;" >> all_files.sql
echo "drop table maf_info;" >> all_files.sql
echo "create table maf_info(study_acc VARCHAR, database_identifier VARCHAR, chemical_formula VARCHAR, smiles VARCHAR, inchi VARCHAR, metabolite_identification VARCHAR);" >> all_files.sql
echo "copy maf_info from STDIN;" >> all_files.sql

for i in `find . -name "*_maf.tsv" | grep -v audit`
do  
  STUDY=$(echo ${i} | cut -f2 -d"/")
  echo "Study: ${STUDY}"
  cut -f1-5 "$i" | sed 1d > file.tsv
  while read f; do
  echo -e '"'$STUDY'"''\t' $f
  done < file.tsv > temp.tsv
  cat temp.tsv >> all_files.sql
done
rm file.tsv temp.tsv

echo "
update maf_info set 
  database_identifier = replace(database_identifier,'\"',''), 
  chemical_formula = replace(chemical_formula,'\"',''),
  study_acc = replace(study_acc,'\"',''),
  smiles = replace(smiles,'\"',''),
  inchi = replace(inchi,'\"',''),
  metabolite_identification = replace(metabolite_identification,'\"','');
  commit;

update maf_info set database_identifier = 'not_reported' where length(database_identifier)=1;
update maf_info set database_identifier = 'unknown' where trim(lower(database_identifier)) ='unknown';
update maf_info set database_identifier = 'unknown' where trim(lower(database_identifier)) ='null';
update maf_info set chemical_formula = 'not_reported' where length(chemical_formula)=1; 
update maf_info set smiles = 'not_reported' where length(smiles)=1; 
update maf_info set inchi = 'not_reported' where length(inchi)=1; 
update maf_info set metabolite_identification = 'not_reported' where length(metabolite_identification)=1;
CREATE INDEX maf_info_acc ON maf_info(study_acc);
CREATE INDEX maf_info_db ON maf_info(database_identifier);
CREATE INDEX maf_info_formula ON maf_info(chemical_formula);
CREATE INDEX maf_info_smiles ON maf_info(smiles);
CREATE INDEX maf_info_inchi ON maf_info(inchi);
CREATE INDEX maf_info_maf ON maf_info(metabolite_identification);
commit;" >> all_files.sql


#echo "\q" >> all_files.sql
  
mv all_files.* $HOME/metabolights/scripts

cd $HOME/metabolights/scripts

${PG_COMMAND} -f all_files.sql
IFS="$OIFS"

