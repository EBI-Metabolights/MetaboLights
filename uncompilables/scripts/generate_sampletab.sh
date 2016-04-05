CONFIGFOLDER=/nfs/www-prod/web_hx2/cm/metabolights/dev/isatab/configurations
STUDYFOLDER=/ebi/ftp/pub/databases/metabolights/studies/public
DERIVEDFOLDER=/ebi/ftp/pub/databases/metabolights/derived/public
SCRIPTS=/nfs/www-prod/web_hx2/cm/metabolights/scripts

for study in `ls -l $STUDYFOLDER | grep '^d' | awk '{ print $9 }'`
do
  mkdir -p $DERIVEDFOLDER/$study
  cd $DERIVEDFOLDER/$study
  echo "-----------------------------------------------------------------------"
  echo $DERIVEDFOLDER/$study
  java -jar ${SCRIPTS}/metabolights-sampledb-1.2-SNAPSHOT-deps.jar ${CONFIGFOLDER} ${STUDYFOLDER}/${study} ${study}_sampleTab.tsv
  rm isacreator.log
done
