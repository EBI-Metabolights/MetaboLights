#!/bin/sh
export ML="/Users/`whoami`/Development/metabolights"
export DEPLOY=" install -DskipTests=true"
#export DEPLOY=" deploy -DskipTests=true"
export MVNOPTS=""
#export MVNOPTS="--offline"
cd $ML

#cd metabolights-utils
#mvn $MVNOPTS clean compile
#mvn $MVNOPTS clean compile $DEPLOY
#cd ../ metabolights-webservice
#mvn dependency:copy-dependencies

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
mvn $MVNOPTS clean compile $DEPLOY
cd ../metabolights-dao
mvn $MVNOPTS clean compile $DEPLOY
cd ../metabolights-referencelayer/dao
mvn $MVNOPTS clean compile $DEPLOY
cd ../../metabolights-referencelayer/importer
mvn $MVNOPTS clean compile $DEPLOY
cd ../../MetaboLights-Search
mvn $MVNOPTS clean package $DEPLOY
cd ../metabolights-webservice
mvn $MVNOPTS clean package $DEPLOY
cd ../metabolights-ws-client
mvn $MVNOPTS clean compile $DEPLOY
cd ../metabolights-webapps
mvn $MVNOPTS compile war:inplace war:war
cp $ML/metabolights-webapps/target/metabolights-webapp-*.war /nfs/public/rw/homes/tc_cm01/metabolights/deploy/metabolights-webapp-dev.war
cp $ML/metabolights-webservice/target/metabolights-webservice*.war /nfs/public/rw/homes/tc_cm01/metabolights/deploy/metabolights-webservice-dev.war
cd ..
ls -Fla /nfs/public/rw/homes/tc_cm01/metabolights/deploy/

echo "to deploy:"
echo "    PROD"
echo ""
echo "    scp metabolights-webapps/target/metabolights-webapp-*.war ebi-login:/nfs/www-prod/web_hx2/cm/metabolights/deploy/metabolights-webapp-prod.war "
echo "    scp metabolights-webservice/target/metabolights-webservice-*.war ebi-login:/nfs/www-prod/web_hx2/cm/metabolights/deploy/metabolights-webservice-prod.war "
echo ""
echo "    DEV"
echo ""
echo "    scp metabolights-webapps/target/metabolights-webapp-*.war ebi-login:/nfs/www-prod/web_hx2/cm/metabolights/deploy/metabolights-webapp-dev.war "
echo "    scp metabolights-webservice/target/metabolights-webservice-*.war ebi-login:/nfs/www-prod/web_hx2/cm/metabolights/deploy/metabolights-webservice-dev.war "
echo ""
echo "    TEST"
echo ""
echo "    scp metabolights-webapps/target/metabolights-webapp-*.war ebi-login:/nfs/www-prod/web_hx2/cm/metabolights/deploy/metabolights-webapp-test.war "
echo "    scp metabolights-webservice/target/metabolights-webservice-*.war ebi-login:/nfs/www-prod/web_hx2/cm/metabolights/deploy/metabolights-webservice-test.war "
echo "    ------------------- "
