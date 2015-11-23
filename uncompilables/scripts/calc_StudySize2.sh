#!/bin/bash

studyfolder="/nfs/public/rw/homes/tc_cm01/metabolights/prod/studies/stage/private"
sql_script="/net/isilonP/public/rw/homes/tc_cm01/metabolights/scripts/db_update_size_prod.sql"

source /homes/oracle/ora11setup.sh

rm $sql_script
touch $sql_script

#list all folders /nfs/public/rw/homes/tc_cm01/metabolights/prod/studies/public
#Get folder size
#Output on console
#If it has MB or TB can then change the DB datatype to varchar

cd $studyfolder
sum=0;
size=""

for value in $(find . -maxdepth 1 -type d -mtime 3 -exec du -s {}/ \;)

do  
if [ $sum -eq 0 ]; then
   size=${value}
   echo size $size
   sum=$[$sum+1]
else
    study=$(echo $value | awk -F'/' '{print $2}')
    echo size $size study $study
    sum=$[$sum-1]
    
    #do database querying and update here
    echo "UPDATE STUDIES SET STUDYSIZE = '"${size}"' WHERE ACC = '"${study}"';" >> $sql_script
fi
done

echo "commit;" >> $sql_script
echo "exit;" >> $sql_script

sqlplus USER/PASSWD@DB @${sql_script}
