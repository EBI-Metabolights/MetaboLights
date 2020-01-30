METAFOLDER=/nfs/www-prod/web_hx2/cm/metabolights/dev/studies/stage/private
STUDYFOLDER=/net/isilonP/public/rw/homes/tc_cm01/metabolights/prod/studies/stage/private

mkdir -p $METAFOLDER

for study in `ls -1 $STUDYFOLDER` 
do
  mkdir -p $METAFOLDER/$study
  #Look for metadata files
  cp ${STUDYFOLDER}/${study}/*.txt ${METAFOLDER}/${study}/
  cp ${STUDYFOLDER}/${study}/*.tsv ${METAFOLDER}/${study}/
  cp ${STUDYFOLDER}/${study}/*.csv ${METAFOLDER}/${study}/
done
