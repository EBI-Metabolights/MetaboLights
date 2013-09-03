mvn clean
rm metabolights-webapps/src/main/webapp/WEB-INF/lib/*.jar
rm -r metabolights-webapps/src/main/webapp/WEB-INF/classes
rm metabolights-webapps/target/metabolights-webapp-*.war
mvn dependency:copy-dependencies
mvn compile
mvn install -DskipTests=true
cd metabolights-webapps
mvn war:inplace war:war
echo "to deploy:"
echo "    scp metabolights-webapps/target/metabolights-webapp-*.war ebi-003:/nfs/public/rw/homes/tc_cm01/metabolights/deploy/metabolights-webapp-dev.war "
echo "    scp metabolights-webapps/target/metabolights-webapp-*.war ebi-003:/nfs/public/rw/homes/tc_cm01/metabolights/deploy/metabolights-webapp-prod.war "
cd ..
