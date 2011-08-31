#!/bin/ksh
##########################################################################
#
#   File    : maintainPublic.sh
#   Type    : Korn Shell Script
#
#   Purpose : Check the database for recently modified studies and moving
#             archive files to the public ftp location
#
# 20110831  : Ken Haug - Created script
#
##########################################################################
clear

. /homes/mtbl/metabolights/metabolights-webapps/src/main/scripts/logging_functions
source /homes/oracle/ora11setup.sh

#################################
#  Configurable Options Follow  #
#################################

EMAILTO=kenneth@ebi.ac.uk
#MAILTO=`grep mtblAdminEmailAddress ${PROPS_FILE} | grep -v '!' | grep -v '#' |cut -f2 -d=` 
PROPS_FILE=/homes/mtbl/metabolights/metabolights-webapps/src/main/webapp/resources/application.properties
NUM_DAYS=90

#################################
#  End of Configurable Options  #
#################################

SHELL_LOG_FILE=/homes/mtbl/maintainPublic.log.`date +"%Y-%m-%d %H:%M:%S"` 
DB_CONNECTION=`grep jdbc.username ${PROPS_FILE} | grep -v '!' | grep -v '#' |cut -f2 -d=`  
DB_CONNECTION=$DB_CONNECTION'/'`grep jdbc.password ${PROPS_FILE} | grep -v '!' | grep -v '#' |cut -f2 -d=`  
DB_CONNECTION=$DB_CONNECTION'@'`grep jdbc.databaseurl ${PROPS_FILE} | grep -v '!' | grep -v '#' |cut -f6 -d:`  
PUB_FTP=`grep publicFtpLocation ${PROPS_FILE} | grep -v '!' | grep -v '#' |cut -f2 -d=`  
PRIV_FTP=`grep privateFtpLocation ${PROPS_FILE} | grep -v '!' | grep -v '#' |cut -f2 -d=`  
SQL_BASIC_STR="whenever sqlerror exit failure;\n set head off;\n set pagesize 0;\n "
# This SQL will get all id's that are private, release date is in the past or today AND it has been modified over the last few days
# NB! updated_date is our column, we have to add this if we upgrade the schema
GET_STUDIES_SQL="${SQL_BASIC_STR} select acc from study where status = 0 AND trunc(releasedate)<=trunc(sysdate) AND trunc(updated_date)>=trunc(sysdate-${NUM_DAYS});"

Info ------------------------------------------------------------------------------------------ 
Info Settings:
Info '  Using properties file:             '$PROPS_FILE 
Info '  Shell script log file:             '$SHELL_LOG_FILE
Info ------------------------------------------------------------------------------------------
Info '  MetaboLights Database connection:  '$DB_CONNECTION
Info ------------------------------------------------------------------------------------------
Info '  MetaboLights PRIVATE ftp location: '$PRIV_FTP
Info '  MetaboLights PUBLIC ftp location : '$PUB_FTP
Info ------------------------------------------------------------------------------------------

Info "Testing required parameters"
[ -z $PROPS_FILE ] && Error "Properties file not found, exiting"
[ -z $DB_CONNECTION ] && Error "Database connection string is not set, exiting"
[ -z $PRIV_FTP ] && Error "MetaboLights PRIVATE ftp location is not set, exiting"
[ -z $PUB_FTP ] && Error "MetaboLights PUBLIC ftp location is not set, exiting"
[ -z $GET_STUDIES_SQL ] && Error "GET_STUDIES_SQL is not set, exiting"

Info ------------------------------------------------------------------------------------------
Info "Start"
Info "Getting study data modified in the last ${NUM_DAYS} days"  

PUBLIC_STUDIES=`echo -e ${GET_STUDIES_SQL} | sqlplus -s ${DB_CONNECTION}`
 
for studies in $PUBLIC_STUDIES
do
    # Check if file exists
	[ -f $PRIV_FTP$studies.zip ] || Info "ERROR: File $PRIV_FTP$studies.zip does not exist"
	[ -f $PUB_FTP$studies.zip ] && Info "File $studies.zip already exists in $PUB_FTP"
	
	# Move the archive, if it exists, to the public location
	[ -f $PRIV_FTP$studies.zip ] && mv -f $PRIV_FTP$studies.zip $PUB_FTP$studies.zip;chmod +rx $PUB_FTP$studies.zip; Info "Study ${studies} moved to ${PUB_FTP}"
       
done

mailx -s 'MetaboLights Public File Maintenance' ${EMAILTO} < ${SHELL_LOG_FILE}






