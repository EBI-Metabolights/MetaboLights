#!/bin/csh
source /homes/oracle/ora11setup.csh
cd /ebi/production/panda/metabolights/backup/
set today=`date +%d`
set count=`date +%H`

exp user/pass@database buffer=52428800 compress=y consistent=y file=mtaprovm-$today-$count.dmp log=$today-$count.log owner=ISATAB
gzip mtaprovm-$today-$count.dmp
find /ebi/production/panda/metabolights/backup/* -mtime +14 -exec rm  {} \;
