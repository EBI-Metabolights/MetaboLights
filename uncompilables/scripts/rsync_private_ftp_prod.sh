SYSTEM=prod
cd ~/metabolights/$SYSTEM/ftp_private/
rsync -d --delete . /ebi/ftp/private/mtblight/$SYSTEM
rsync -av /ebi/ftp/private/mtblight/$SYSTEM/ .
