JAVA=/etc/alternatives/jre_1.7.0/bin/java
JAR_FILE=metabolights-export-1.1-SNAPSHOT.jar
WS_URL=http://www.ebi.ac.uk/metabolights/webservice/
FTP_DIR=/ebi/ftp/pub/databases/metabolights/eb-eye/

cd $HOME/metabolights/scripts/
nohup $JAVA -jar $JAR_FILE $FTP_DIR/eb-eye_metabolights_complete.xml y n $WS_URL >eb-eye_metabolights_complete.out 2>&1 </dev/null &
nohup $JAVA -jar $JAR_FILE $FTP_DIR/eb-eye_metabolights_studies.xml n n $WS_URL >eb-eye_metabolights_studies.out 2>&1 </dev/null &
nohup $JAVA -jar $JAR_FILE $FTP_DIR/thomsonreuters_metabolights_studies.xml n y $WS_URL >thomsonreuters_metabolights_studies.out 2>&1 </dev/null &