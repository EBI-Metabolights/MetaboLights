#!/bin/csh 
cd /ebi/production/panda/metabolights/backup/
set today=`date +%d`
set count=`date +%H`

tar -cvf elastic-$today-$count.tar /nfs/www-prod/web_hx2/cm/metabolights/prod/index
gzip elastic-$today-$count.tar
