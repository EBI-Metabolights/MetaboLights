#!/bin/ksh

clear

. /homes/mtbl/metabolights/metabolights-webapps/src/main/scripts/logging_functions
source /homes/oracle/ora11setup.sh

#Changable Settings
EMAILTO=kenneth@ebi.ac.uk
PROPS_FILE=/homes/mtbl/metabolights/metabolights-webapps/src/main/webapp/resources/application.properties
SQL_DIRECTORY=/homes/mtbl/metabolights/metabolights-webapps/src/main/sql/
NUM_DAYS=5

# Do not change any settings below
SHELL_LOG_FILE=/homes/mtbl/maintainPublic.`date +"%Y-%m-%d %H:%M:%S"`.log3
#MAILTO=`grep mtblAdminEmailAddress ${PROPS_FILE} | grep -v '!' | grep -v '#' |cut -f2 -d=`  
DB_CONNECTION=`grep jdbc.username ${PROPS_FILE} | grep -v '!' | grep -v '#' |cut -f2 -d=`  
DB_CONNECTION=$DB_CONNECTION'/'`grep jdbc.password ${PROPS_FILE} | grep -v '!' | grep -v '#' |cut -f2 -d=`  
DB_CONNECTION=$DB_CONNECTION'@'`grep jdbc.databaseurl ${PROPS_FILE} | grep -v '!' | grep -v '#' |cut -f6 -d:`  
PUB_FTP=`grep publicFtpLocation ${PROPS_FILE} | grep -v '!' | grep -v '#' |cut -f2 -d=`  
PRIV_FTP=`grep privateFtpLocation ${PROPS_FILE} | grep -v '!' | grep -v '#' |cut -f2 -d=`  
SQL_BASIC_STR="whenever sqlerror exit failure;\n set feedback off;\n set head off;\n set pagesize 0;\n "
GET_STUDIES_SQL="$SQL_BASIC_STR select acc from study where status = 0 AND trunc(updated_date) >= trunc(sysdate-'${NUM_DAYS}');\n exit\n"

Info ------------------------------------------------------------------------------------------ 
Info Settings:
Info '  Using properties file:             '$PROPS_FILE 
Info '  Shell script log file:             '$SHELL_LOG_FILE
Info ------------------------------------------------------------------------------------------
Info '  MetaboLights Database connection:  '$DB_CONNECTION
Info ------------------------------------------------------------------------------------------
Info '  MetaboLights PRIVATE ftp location: '$PRIV_FTP
Info '  MetaboLights PUBLIC ftp location : '$PUB_FTP
Info '  MetaboLights SQL script location : '$SQL_DIRECTORY
Info ------------------------------------------------------------------------------------------

Info "Testing required parameters"
[ -z $PROPS_FILE ] && Error "Properties file not found, exiting"
[ -z $DB_CONNECTION ] && Error "Database connection string is not set, exiting"
[ -z $PRIV_FTP ] && Error "MetaboLights PRIVATE ftp location is not set, exiting"
[ -z $PUB_FTP ] && Error "MetaboLights PUBLIC ftp location is not set, exiting"
[ -z $SQL_DIRECTORY ] && Error "MetaboLights SQL script location is not set, exiting"


Info ------------------------------------------------------------------------------------------
Info "Getting study data modified in the last ${NUM_DAYS} days"  

for study in $ALL_TAP_TABLES
  do
    mgr_logging $table TRUE &
  done


PUBLIC_STUDIES="`echo $GET_STUDIES_SQL | sqlplus -s $DB_CONNECTION`"

for studies in $PUBLIC_STUDIES
do
    ls -Fla $studies
    Info "Study " $studies
done

mailx -s 'MetaboLights Public File Maintenance' ${EMAILTO} < ${SHELL_LOG_FILE}






