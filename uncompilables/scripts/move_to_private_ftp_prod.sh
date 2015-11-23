SYSTEM=prod
FTP_FOLDER=/ebi/ftp/private/mtblight/$SYSTEM
cd ~/metabolights/$SYSTEM/ftp_private
for i in `ls -1`
do
  mv $i $FTP_FOLDER
  chmod -R 770 $FTP_FOLDER/$i
done
