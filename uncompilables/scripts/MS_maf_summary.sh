rm all_files.tsv file.tsv temp.tsv
OIFS="$IFS"
IFS=$'\n'
head -1 "./MTBLS260/m_mtbls260_metabolite_profiling_mass_spectrometry_v2_maf.tsv" | cut -f1-14 > all_files.tsv
for i in `find . -name "*_v2_maf.tsv" | grep -v audit`
do  
  STUDY=$(echo ${i} | cut -f2 -d"/")
  if [ "$(grep -c retention_time $i)" -eq 1 ]; then
    echo "MS Study found : ${STUDY}"
    cut -f1-14 "$i" | sed 1d > file.tsv
    while read f; do
    echo -e '"'$STUDY'"''\t' $f
    done < file.tsv > temp.tsv
    cat temp.tsv >> all_files.tsv
  else
    echo "NMR study ${STUDY}"
  fi
  
done
IFS="$OIFS"
