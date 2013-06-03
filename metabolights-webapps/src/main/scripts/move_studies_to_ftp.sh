#!/bin/ksh

echo "Move file script started"
private_stage=/nfs/public/rw/homes/tc_cm01/studies/public
public_stage=/nfs/public/rw/homes/tc_cm01/studies/public
ftp_folder=/ebi/ftp/pub/databases/metabolights/studies/public
fuser_binary=/sbin/fuser
#fuser_binary=fuser


#find all folders in public stage that should be moved to the public ftp site
cd $public_stage
echo "Current folder is `pwd`"

if [ -d MTBLS* ]
then
  echo "MetaboLights study folder(s) found `ls`" 

  for archive_folder in `ls`
  do
    echo "archive_folder is $archive_folder"

    # check if there is already a folder in the public ftp site, if so we need to remove it first
    if [ -d $ftp_folder/$archive_folder ] ; then
       ftp_in_use=$($fuser_binary -u $ftp_folder/$archive_folder)
       if [ ${#ftp_in_use} -gt 0 ] ; then
          echo "The public FTP folder is in use $ftp_folder/$archive_folder)"
       else
           echo "INFO: there is already a public folder for ${archive_folder} (${ftp_folder}/${archive_folder})"
           echo "INFO: Removing the folder"
           rm $ftp_folder/$archive_folder/*
           rmdir $ftp_folder/$archive_folder
       fi

    else

       # try to move the folder
       in_use=$($fuser_binary -u $archive_folder)
       if [ ${#in_use} -gt 0 ] ; then
          echo "Folder in use $archive_folder"
       else
          echo "Folder $archive_folder not in use, moving to $ftp_folder"
          mv ./$archive_folder $ftp_folder/
       fi

    fi

   done

fi
