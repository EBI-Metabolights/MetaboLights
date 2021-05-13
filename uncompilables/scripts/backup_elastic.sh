#!/bin/bash 
cd /ebi/production/panda/metabolights/backup/
today=`date +%d`
count=`date +%H`
tar -cvf elastic-$today-$count.tar /nfs/www-prod/web_hx2/cm/metabolights/prod/index
gzip elastic-$today-$count.tar
echo "Done!"
