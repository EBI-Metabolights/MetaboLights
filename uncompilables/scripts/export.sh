JAVA=/usr/lib/jvm/java-1.7.0/bin/java
JAR_FILE=metabolights-export-1.3-SNAPSHOT-deps.jar
WS_URL=https://wp-p3s-15:8070/metabolights/webservice/
FTP_DIR=/ebi/ftp/pub/databases/metabolights/eb-eye
EBEYE_FILE=eb-eye_metabolights_complete.xml
PMC_FILE=europe_PMC_metabolights_studies.xml
PMC_main_class=uk.ac.ebi.metabolights.utils.exporter.MetabolightsEuropePMCExporter

cp $FTP_DIR/$EBEYE_FILE $FTP_DIR/$EBEYE_FILE.old

cd $HOME/metabolights/scripts/
$JAVA -jar $JAR_FILE $FTP_DIR/eb-eye_metabolights_studies.xml n n $WS_URL >eb-eye_metabolights_studies.log 2>&1 </dev/null 
$JAVA -jar $JAR_FILE $FTP_DIR/thomsonreuters_metabolights_studies.xml n y $WS_URL >thomsonreuters_metabolights_studies.log 2>&1 </dev/null
$JAVA -jar $JAR_FILE $FTP_DIR/$EBEYE_FILE y n $WS_URL >eb-eye_metabolights_complete.log 2>&1 </dev/null

$JAVA -cp $JAR_FILE $PMC_main_class ./$PMC_FILE $WS_URL >metabolights_epmc.log 2>&1 </dev/null 
./EuropePMC_export.sh ./$PMC_FILE
cp $PMC_FILE $FTP_DIR/

REAL_ENTRIES=`grep -ic '</entry>' $FTP_DIR/$EBEYE_FILE`
REPORTED_ENTRIES=`head $FTP_DIR/$EBEYE_FILE | grep entry_count | sed -e 's/<[^>]*>//g' `
REPORTED_ENTRIES="$(echo -e "${REPORTED_ENTRIES}" | tr -d '[:space:]')"
if [ "$REPORTED_ENTRIES" -gt "$REAL_ENTRIES" ]; then
  sed -i "s/<entry_count>$REPORTED_ENTRIES<\/entry_count>/<entry_count>$REAL_ENTRIES<\/entry_count>/g" $FTP_DIR/$EBEYE_FILE
  echo "Updated the $EBEYE_FILE file entry count from $REPORTED_ENTRIES to $REAL_ENTRIES" | mail -s "EB-EYE exporter entry count update" metabolights-dev@ebi.ac.uk
  #tail eb-eye_metabolights_complete.log | mail -s "Error in the EB-EYE exporter" metabolights-dev@ebi.ac.uk  
  cp $FTP_DIR/$EBEYE_FILE.old $FTP_DIR/$EBEYE_FILE
fi
