#!/bin/bash 
. /nfs/www-prod/web_hx2/cm/metabolights/scripts/postgres.properties.prod
export pg_program=/nfs/dbtools/pgsql10/usr/pgsql-10/bin/pg_dump
cd /ebi/production/panda/metabolights/backup/
export today=`date +%d`
export count=`date +%H`
#module load pgsql/pgsql-95
$pg_program -f $PG_DB.${today}-${count} -U $PG_USER -n $PG_USER -w -h $PG_HOST -p $PG_PORT
find /ebi/production/panda/metabolights/backup/* -mtime +14 -exec rm -r {} \;
echo "Backup done!"
