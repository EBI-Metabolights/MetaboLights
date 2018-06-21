OIFS="$IFS"
IFS=$'\n'

echo "truncate table study_row_info;" >> all_files.sql
echo "drop table study_row_info;" >> all_files.sql
echo "create table study_row_info(study_acc VARCHAR, num_rows VARCHAR, sheet_name VARCHAR);" >> all_files.sql
echo "copy study_row_info from STDIN;" >> all_files.sql


for i in `find . -name "s_*.txt" | grep -v audit`
do  
  STUDY=$(echo ${i} | cut -f2 -d"/")
  echo "Study: ${STUDY}"
  #cut -f1-7 "$i" | sed 1d > file.tsv
  wc -l "$i" > file.tsv
  while read f; do
    echo -e '"'$STUDY'"''\t' $f
  done < file.tsv > temp.tsv
  cat temp.tsv >> all_files.sql
done

for i in `find . -name "a_*.txt" | grep -v audit`
do  
  STUDY=$(echo ${i} | cut -f2 -d"/")
  echo "Study: ${STUDY}"
  wc -l "$i" > file.tsv
  while read f; do
    echo -e '"'$STUDY'"''\t' $f
  done < file.tsv > temp.tsv
  cat temp.tsv >> all_files.sql
done

rm file.tsv temp.tsv

echo "\q" >> all_files.sql
  
mv all_files.* $HOME/metabolights/scripts

cd $HOME/metabolights/scripts
source ./postgres.properties.prod
${PG_COMMAND} -f all_files.sql
IFS="$OIFS"
