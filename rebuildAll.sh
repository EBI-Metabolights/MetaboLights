mvn clean
rm metabolights-webapps/src/main/webapp/WEB-INF/lib/*.jar
mvn dependency:copy-dependencies
mvn compile
cd metabolights-webapps
mvn war:inplace war:war
cd ..
