#!/bin/ksh

echo "Move file script started"
base_dir=/nfs/public/rw/homes/tc_cm01/studies/public
ftp_folder=/ebi/ftp/pub/databases/metabolights/studies/public/
fuser_binary=/sbin/fuser
#fuser_binary=fuser

cd $base_dir
echo "Current folder is `pwd`"

if [ -d MTBLS* ]
then
  echo "MetaboLights study folder(s) found `ls`" 

  for archive_folder in `ls`
  do
    echo "archive_folder is $archive_folder"
    in_use=$($fuser_binary -u $archive_folder)
    if [ ${#in_use} -gt 0 ] ; then
       echo "Folder in use $archive_folder"
    else
       echo "Folder $archive_folder not in use, moving to $ftp_folder"
       mv ./$archive_folder $ftp_folder
    fi
   done

fi
