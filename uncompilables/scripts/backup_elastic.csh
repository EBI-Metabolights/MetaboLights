#!/bin/csh 
cd ~/elastic_backup
set today=`date +%d`
set count=`date +%H`

tar -cvf elastic-$today-$count.tar /net/vnas-metabolights/metabolights/prod/index
gzip elastic-$today-$count.tar