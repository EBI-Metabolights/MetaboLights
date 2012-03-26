#!/bin/tcsh
source /homes/oracle/ora11setup.csh
cd /ebi/production/panda/metabolights/backup/
set today=`date +%d`
set count=`date +%H`

exp <USERNAME>/<USERPWD>@<DATABASE> buffer=52428800 compress=y consistent=y file=mtapro-$today-$count.dmp log=$today-$count.log owner=<USERNAME>
gzip mtapro-$today-$count.dmp
tar -cvf lucene-prod-$today-$count.tar /net/isilonP/public/rw/homes/tc_cm01/lucene/prod/bii/*
gzip lucene-prod-$today-$count.tar
find /ebi/production/panda/metabolights/backup/* -mtime +14 -exec rm  {} \;