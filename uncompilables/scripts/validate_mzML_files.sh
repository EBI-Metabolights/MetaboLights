error_folder="invalid_mzML_files"
xsd_schema="/net/isilonP/public/rw/homes/tc_cm01/metabolights/scripts/mzML1.1.1_idx.xsd"
log_progress="validation_progress_log.txt"

for i in *.mzML
do   
  echo "Validating file $i"
  grep -q $i $log_progress
  RC=$?
  if [ $RC -eq 0 ]
    then
     echo "  - This file has already been successfully validated"
    else
     xmllint --noout --schema $xsd_schema $i >/dev/null 2>&1
     RC=$?
     if [ $RC -eq 0 ]
       then
        echo "$i validates" >> $log_progress
       else
        mkdir -p ./$error_folder 
        echo "$i validation failed, moving file to $error_folder"
        mv $i ./$error_folder
     fi 
  fi
done
echo "The following files did not validate:"
ls ./$error_folder

