mvn clean
rm metabolights-webapps/src/main/webapp/WEB-INF/lib/*.jar
rm -rf /nfs/public/rw/homes/tc_cm01/dev/upload/process/*
rm target/dependency/*.jar
rm metabolights-webapps/target/dependency/*.jar
rm -r metabolights-webapps/src/main/webapp/WEB-INF/classes
rm -r /usr/local/tomcat/webapps/metabolights
rm metabolights-webapps/target/metabolights-webapp-*.war
mvn dependency:copy-dependencies
cd metabolights-webservice
mvn compile package -DskipTests=true
cd ../metabolights-webapps
mvn compile war:inplace war:war
cp /Users/kenneth/Development/metabolights/metabolights-webapps/target/metabolights-webapp-*.war /nfs/public/rw/homes/tc_cm01/metabolights/deploy/metabolights-webapp-dev.war
cd ..
ls -Fla /nfs/public/rw/homes/tc_cm01/metabolights/deploy/

echo "to deploy:"
echo "    scp /nfs/public/rw/homes/tc_cm01/deploy/metabolights-webapp-dev.war ebi-003:/nfs/public/rw/homes/tc_cm01/metabolights/deploy/metabolights-webapp-dev.war "
echo "    scp /nfs/public/rw/homes/tc_cm01/deploy/metabolights-webapp-dev.war ebi-003:/nfs/public/rw/homes/tc_cm01/metabolights/deploy/metabolights-webapp-prod.war "
