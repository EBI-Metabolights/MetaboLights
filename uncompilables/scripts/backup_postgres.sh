#!/bin/bash 
. /nfs/www-prod/web_hx2/cm/metabolights/scripts/postgres.properties.prod
cd /ebi/production/panda/metabolights/backup/
export today=`date +%d`
export count=`date +%H`
module load pgsql/pgsql-95
pg_dump -f database.${today}-${count} -U user -n schema -w -h database -p port
find /ebi/production/panda/metabolights/backup/* -mtime +14 -exec rm -r {} \;
