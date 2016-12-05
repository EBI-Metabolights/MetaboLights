#!/bin/bash

#studyfolder="/nfs/public/rw/homes/tc_cm01/metabolights/prod/studies/stage/private"
studyfolder="/nfs/www-prod/web_hx2/cm/metabolights/prod/studies/stage/private"
sql_script="/nfs/www-prod/web_hx2/cm/metabolights/scripts/db_update_size_prod.sql"

source /homes/oracle/ora11setup.sh

export PGPASSWORD=xx
export PG_USER=xx
export PG_DB=xx
export PG_HOST=xx
export PG_PORT=5432
export PG_COMMAND="psql -U ${PG_USER} -w -d ${PG_DB} -h ${PG_HOST} -p ${PG_PORT}"

rm $sql_script
touch $sql_script

#list all folders /nfs/www-prod/web_hx2/cm/metabolights/prod/studies/public
#Get folder size
#Output on console
#If it has MB or TB can then change the DB datatype to varchar

cd $studyfolder
sum=0;
size=""

for value in $(du -s */)

do
if [ $sum -eq 0 ]; then
   size=${value}
   #echo size $size
   sum=$[$sum+1]
else
    study=$(echo $value | awk -F'/' '{print $1}')
    echo size $size study $study
    sum=$[$sum-1]

    #do database querying and update here
    echo "UPDATE STUDIES SET STUDYSIZE = '"${size}"' WHERE ACC = '"${study}"';" >> $sql_script
fi
done

echo "\q" >> $sql_script

$PG_COMMAND -f ${sql_script}
