#!/bin/sh
export ML="/Users/kenneth/Development/metabolights"
#export DEPLOY="deploy"
export DEPLOY=""
cd $ML
mvn clean
rm metabolights-webapps/src/main/webapp/WEB-INF/lib/*.jar
rm -rf /nfs/public/rw/homes/tc_cm01/dev/upload/process/*
rm -r metabolights-webapps/src/main/webapp/WEB-INF/classes
rm -r /usr/local/tomcat/webapps/metabolights
rm metabolights-webapps/target/metabolights-webapp-*.war
rm  $ML/metabolights-webservice/target/*.war
rm  -rf $ML/metabolights-webservice/target/*
cd metabolights-domain
mvn clean install $DEPLOY -DskipTests=true
cd ../metabolights-dao
mvn clean install $DEPLOY -DskipTests=true
cd ../MetaboLights-Search
mvn clean package $DEPLOY -DskipTests=true
cd ../metabolights-webservice
mvn clean package $DEPLOY -DskipTests=true
cd ../metabolights-ws-client
mvn clean install $DEPLOY -DskipTests=true
cd ../metabolights-webapps
mvn compile war:inplace war:war
cp $ML/metabolights-webapps/target/metabolights-webapp-*.war /nfs/public/rw/homes/tc_cm01/metabolights/deploy/metabolights-webapp-dev.war
cp $ML/metabolights-webservice/target/metabolights-webservice*.war /nfs/public/rw/homes/tc_cm01/metabolights/deploy/metabolights-webservice-dev.war
cd ..
ls -Fla /nfs/public/rw/homes/tc_cm01/metabolights/deploy/

echo "to deploy:"
echo "    scp /nfs/public/rw/homes/tc_cm01/metabolights/deploy/metabolights-webapp-dev.war ebi-003:/nfs/public/rw/homes/tc_cm01/metabolights/deploy/metabolights-webapp-dev.war "
echo "    scp /nfs/public/rw/homes/tc_cm01/metabolights/deploy/metabolights-webapp-dev.war ebi-003:/nfs/public/rw/homes/tc_cm01/metabolights/deploy/metabolights-webapp-prod.war "
echo "    ------------------- "
echo "    scp /nfs/public/rw/homes/tc_cm01/metabolights/deploy/metabolights-webservice-dev.war ebi-003:/net/isilonP/public/rw/homes/tc_cm01/metabolights/deploy/metabolights-webservice-dev.war "
