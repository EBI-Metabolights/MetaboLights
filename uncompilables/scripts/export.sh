JAVA=/etc/alternatives/jre_1.7.0/bin/java

nohup $JAVA -jar metabolights-export-1.0-SNAPSHOT-deps-jar-with-dependencies.jar /ebi/ftp/pub/databases/metabolights/eb-eye/eb-eye_metabolights_studies.xml n n
 http://www.ebi.ac.uk/metabolights/webservice/ >export1.out 2>&1 </dev/null &
nohup $JAVA -jar metabolights-export-1.0-SNAPSHOT-deps-jar-with-dependencies.jar /ebi/ftp/pub/databases/metabolights/eb-eye/thomsonreuters_metabolights_studies
.xml n y http://www.ebi.ac.uk/metabolights/webservice/ >export2.out 2>&1 </dev/null &
