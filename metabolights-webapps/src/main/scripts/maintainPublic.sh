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
NUM_DAYS=10

#################################
#  End of Configurable Options  #
#################################

SHELL_LOG_FILE=/homes/mtbl/maintainPublic.log.`date +"%Y-%m-%d %H:%M:%S"` 
DB_CONNECTION=`grep jdbc.username ${PROPS_FILE} | grep -v '!' | grep -v '#' |cut -f2 -d=`  
DB_CONNECTION=$DB_CONNECTION'/'`grep jdbc.password ${PROPS_FILE} | grep -v '!' | grep -v '#' |cut -f2 -d=`  
DB_CONNECTION=$DB_CONNECTION'@'`grep jdbc.databaseurl ${PROPS_FILE} | grep -v '!' | grep -v '#' |cut -f6 -d:`  
PUB_FTP=`grep publicFtpLocation ${PROPS_FILE} | grep -v '!' | grep -v '#' |cut -f2 -d=`  
PRIV_FTP=`grep privateFtpLocation ${PROPS_FILE} | grep -v '!' | grep -v '#' |cut -f2 -d=`  
GET_STUDIES_SQL="select acc from study where status = 0 AND trunc(updated_date) >= trunc(sysdate-${NUM_DAYS});"

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

PUBLIC_STUDIES=`echo ${GET_STUDIES_SQL} | sqlplus -s ${DB_CONNECTION}`
PUBLIC_STUDIES=`grep -v '-' $PUBLIC_STUDIES`
Info "Public Studies found ${PUBLIC_STUDIES}"
Info "SQL ${GET_STUDIES_SQL}"
 
for studies in $PUBLIC_STUDIES
do
    ls -Fla $studies
    Info "Study ${studies}"
done

#mailx -s 'MetaboLights Public File Maintenance' ${EMAILTO} < ${SHELL_LOG_FILE}






