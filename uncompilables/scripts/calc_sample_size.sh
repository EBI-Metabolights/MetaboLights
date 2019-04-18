#!/bin/bash

studyfolder="/nfs/www-prod/web_hx2/cm/metabolights/prod/studies/stage/private"
sql_script="/nfs/www-prod/web_hx2/cm/metabolights/scripts/db_update_sample_prod_postgres.sql"

source ./postgres.properties.prod

rm $sql_script
touch $sql_script
cd $studyfolder

OIFS="$IFS"
IFS=$'\n'

for study in `ls -1`
do
  cd $study

  for s_file in `ls -1 s_*.txt 2>/dev/null`
  do
    s_rows=$(cat s_*.txt | wc -l)
  done

  for a_file in `ls -1 a_*.txt 2>/dev/null`
  do
    a_rows=$(cat a_*.txt | wc -l)
  done

  for m_file in `ls -1 m_*.t* 2>/dev/null`
  do
    m_rows=$(cat m_*.t* | wc -l)
  done

  echo "UPDATE STUDIES SET sample_rows = "${s_rows}", assay_rows = "${a_rows}",maf_rows = "${m_rows}" WHERE ACC = '"${study}"';" >> $sql_script
  cd ..
done

IFS="$OIFS"

echo "\q" >> $sql_script

${PG_COMMAND} -f ${sql_script}
