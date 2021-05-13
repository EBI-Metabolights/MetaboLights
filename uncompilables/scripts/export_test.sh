JAVA=/etc/alternatives/jre_1.7.0/bin/java
JAR_FILE=metabolights-export-1.1-SNAPSHOT-deps.jar
WS_URL=https://www.ebi.ac.uk/metabolights/webservice/
FTP_DIR=/ebi/ftp/pub/databases/metabolights/eb-eye-dev
EBEYE_FILE=eb-eye_metabolights_dev_2.xml


cp $FTP_DIR/$EBEYE_FILE $FTP_DIR/$EBEYE_FILE.old

cd $HOME/metabolights/scripts/
$JAVA -jar $JAR_FILE $FTP_DIR/eb-eye_metabolights_studies_2.xml n n $WS_URL >eb-eye_metabolights_studies_2.log 2>&1 </dev/null
$JAVA -jar $JAR_FILE $FTP_DIR/thomsonreuters_metabolights_studies_2.xml n y $WS_URL >thomsonreuters_metabolights_studies_2.log 2>&1 </dev/null
$JAVA -jar $JAR_FILE $FTP_DIR/$EBEYE_FILE y n $WS_URL >eb-eye_metabolights_dev_2.log 2>&1 </dev/null

REAL_ENTRIES=`grep -ic '</entry>' $FTP_DIR/$EBEYE_FILE`
REPORTED_ENTRIES=`head $FTP_DIR/$EBEYE_FILE | grep entry_count | sed -e 's/<[^>]*>//g' `
if [ "$REPORTED_ENTRIES" -gt "$REAL_ENTRIES" ]; then
  tail eb-eye_metabolights_dev_2.log | mail -s "Error in the EB-EYE exporter" metabolights-dev@ebi.ac.uk
  cp $FTP_DIR/$EBEYE_FILE.old $FTP_DIR/$EBEYE_FILE
fi

