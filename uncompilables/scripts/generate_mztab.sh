STUDYFOLDER=/ebi/ftp/pub/databases/metabolights/studies/public
DERIVEDFOLDER=/ebi/ftp/pub/databases/metabolights/derived/public
SCRIPTS=/nfs/www-prod/web_hx2/cm/metabolights/scripts

#for study in `ls -l $STUDYFOLDER | grep '^d' | awk '{ print $9 }'`
for study in MTBLS1
do
  mkdir -p $DERIVEDFOLDER/$study
  cd $STUDYFOLDER/$study
  find . -name "a_*.csv" -o -name "a_*.tsv" -o -name "m_*.tsv" -printf "%f\n"| while read file
  do 
    echo "-----------------------------------------------------------------------"
    echo $STUDYFOLDER/$study
    java -jar ${SCRIPTS}/metabolights-mztab-2.0-SNAPSHOT-deps.jar "${STUDYFOLDER}/${study}/${file}" "${DERIVEDFOLDER}/${study}/${file}_mzTab.tsv" ${study}
  done
  #rm isacreator.log
done
