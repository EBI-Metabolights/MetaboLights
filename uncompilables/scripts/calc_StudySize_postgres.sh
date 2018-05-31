#!/bin/bash

#studyfolder="/nfs/public/rw/homes/tc_cm01/metabolights/prod/studies/stage/private"
studyfolder="/nfs/www-prod/web_hx2/cm/metabolights/prod/studies/stage/private"
sql_script="/nfs/www-prod/web_hx2/cm/metabolights/scripts/db_update_size_prod_postgres.sql"

source ./postgres.properties.prod

rm $sql_script
touch $sql_script

#list all folders /nfs/www-prod/web_hx2/cm/metabolights/prod/studies/public
#Get folder size
#Output on console
#If it has MB or TB can then change the DB datatype to varchar

cd $studyfolder
sum=0;
size=""

#for value in $(du -s */)
for value in $(du -s -B1 */)
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

${PG_COMMAND} -f ${sql_script}
