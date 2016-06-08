#!/bin/csh
source /homes/oracle/ora11setup.csh
cd /ebi/production/panda/metabolights/backup/
set today=`date +%d`
set count=`date +%H`

exp user/pass@database buffer=52428800 compress=y consistent=y file=mtaprovm-$today-$count.dmp log=$today-$count.log owner=ISATAB
gzip mtaprovm-$today-$count.dmp
tar -cvf elastic-91-$today-$count.tar /nfs/metabolights/cheminf/prod/index/ves-ebi-91/metabolights/*
tar -cvf elastic-92-$today-$count.tar /nfs/metabolights/cheminf/prod/index/ves-ebi-92/metabolights/*
gzip elastic-91-$today-$count.tar
gzip elastic-92-$today-$count.tar
find /ebi/production/panda/metabolights/backup/* -mtime +14 -exec rm  {} \;
