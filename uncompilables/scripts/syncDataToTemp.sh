METAFOLDER=/nfs/www-prod/web_hx2/cm/metabolights/scripts/temp
STUDYFOLDER=/nfs/www-prod/web_hx2/cm/metabolights/prod/studies/stage/private

mkdir -p $METAFOLDER

for study in `ls -1 $STUDYFOLDER` 
do
  mkdir -p $METAFOLDER/$study
  #Look for metadata files
  rsync -av ${STUDYFOLDER}/${study}/i_*.txt ${METAFOLDER}/${study} 2>/dev/null
  rsync -av ${STUDYFOLDER}/${study}/s_*.txt ${METAFOLDER}/${study} 2>/dev/null
  rsync -av ${STUDYFOLDER}/${study}/a_*.txt ${METAFOLDER}/${study} 2>/dev/null
  rsync -av ${STUDYFOLDER}/${study}/m_*.tsv ${METAFOLDER}/${study} 2>/dev/null
done
