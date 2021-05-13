METAFOLDER=/nfs/www-prod/web_hx2/cm/metabolights/dev/studies/stage/private
#METAFOLDER=/nfs/www-prod/web_hx2/cm/metabolights/test/studies/stage/private
STUDYFOLDER=/nfs/www-prod/web_hx2/cm/metabolights/prod/studies/stage/private

mkdir -p $METAFOLDER

for study in `ls -1 $STUDYFOLDER` 
do
  mkdir -p $METAFOLDER/$study
  #Look for metadata files
  for files in i_*.txt s_*.txt a_*.txt a_*.tsv a_*.csv m_*.tsv m_*.csv
  do
    cp ${STUDYFOLDER}/${study}/${files} ${METAFOLDER}/${study}/
  done
done

