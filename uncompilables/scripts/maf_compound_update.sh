
cd $HOME/metabolights/prod/studies/stage/private/
rm all_files.sql file.tsv temp.tsv
OIFS="$IFS"
IFS=$'\n'

echo "truncate table maf_info;" >> all_files.sql
echo "drop table maf_info;" >> all_files.sql
echo "create table maf_info(study_acc VARCHAR, database_identifier VARCHAR, metabolite_identification VARCHAR);" >> all_files.sql
echo "copy maf_info from STDIN;" >> all_files.sql

for i in `find . -maxdepth 2 -name "*_maf.tsv" | grep -v MTBLS597`
do  
  STUDY=$(echo ${i} | cut -f2 -d"/")
  echo "Study: ${STUDY}"
  cut -f1,5 -d$'\t' "$i" | sed 1d > file.tsv
  while read f; do
  echo -e '"'$STUDY'"''\t' $f
  done < file.tsv > temp.tsv
  cat temp.tsv >> all_files.sql
done
rm file.tsv temp.tsv

echo "\q" >> all_files.sql
  
mv all_files.* $HOME/metabolights/scripts

cd $HOME/metabolights/scripts
. ./postgres.properties.prod
./postgres.properties.prod
${PG_COMMAND} -f all_files.sql
IFS="$OIFS"

