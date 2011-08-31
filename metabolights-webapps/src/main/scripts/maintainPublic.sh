#!/bin/ksh

clear

. /homes/mtbl/metabolights/metabolights-webapps/src/main/scripts/logging_functions

#Changable Settings
EMAILTO=kenneth@ebi.ac.uk


# Do not change any settings below
PROPS_FILE=/homes/mtbl/metabolights/metabolights-webapps/src/main/webapp/resources/application.properties
SHELL_LOG_FILE=/homes/mtbl/maintainPublic.${DATE}.log
DB_CONNECTION=`grep jdbc.username ${PROPS_FILE} | grep -v '!' | grep -v '#' |cut -f2 -d=`  
DB_CONNECTION=$DB_CONNECTION'/'`grep jdbc.password ${PROPS_FILE} | grep -v '!' | grep -v '#' |cut -f2 -d=`  
DB_CONNECTION=$DB_CONNECTION'@'`grep jdbc.databaseurl ${PROPS_FILE} | grep -v '!' | grep -v '#' |cut -f6 -d:`  
PUB_FTP=`grep publicFtpLocation ${PROPS_FILE} | grep -v '!' | grep -v '#' |cut -f2 -d=`  
PRIV_FTP=`grep privateFtpLocation ${PROPS_FILE} | grep -v '!' | grep -v '#' |cut -f2 -d=`  

Info ------------------------------------------------------------------------------------------ 
Info Settings:
Info '  Using properties file:             '$PROPS_FILE 
Info '  Shell script log file:             '$SHELL_LOG_FILE
Info ------------------------------------------------------------------------------------------
Info '  MetaboLights Database connection:  '$DB_CONNECTION
Info ------------------------------------------------------------------------------------------
Info '  MetaboLights PRIVATE ftp location: '$PRIV_FTP
Info '  MetaboLights PUBLIC ftp location:  '$PUB_FTP
Info ------------------------------------------------------------------------------------------

Info "Testing required parameters"
[ -z $PROPS_FILE ] && Error "Properties file not found, exiting"
[ -z $DB_CONNECTION ] && Error "Database connection string is not set, exiting"
[ -z $PRIV_FTP ] && Error "PRIVATE ftp location is not set, exiting"
[ -z $PUB_FTP ] && Error "PUBLIC ftp location is not set, exiting"

mailx -s 'MetaboLights Public File Maintenance' ${EMAILTO} < ${SHELL_LOG_FILE}
