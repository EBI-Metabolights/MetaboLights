mvn clean
rm metabolights-webapps/src/main/webapp/WEB-INF/lib/*.jar
rm metabolights-webapps/target/metabolights-webapp-*.war
mvn dependency:copy-dependencies
mvn compile
mvn install -DskipTests=true
cd metabolights-webapps
mvn war:inplace war:war
cp /Users/kenneth/Development/metabolights/metabolights-webapps/target/metabolights-webapp-0.1.1-SNAPSHOT.war /nfs/production/panda/metabolights/deploy/metabolights-webapp.war
cd ..
ls -Fla /nfs/production/panda/metabolights/deploy/
