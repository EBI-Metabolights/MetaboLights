#!/bin/ksh
##########################################################################
#
#   File    : ebeye_export.sh
#   Type    : Korn Shell Script
#
#   Purpose : Export MTBLC and MTBLS entries from ML to EBI global search 
#
# 20130108  : Ken Haug - Created script
#
##########################################################################

. /nfs/production/panda/metabolights/source/metabolights/metabolights-webapps/src/main/scripts/logging_functions
source /homes/oracle/ora11setup.sh

#################################
#  Configurable Options Follow  #
#################################

EMAILTO=metabolights-dev@ebi.ac.uk
PROPS_FILE=/nfs/production/panda/metabolights/source/metabolights/metabolights-webapps/src/main/scripts/hibernateDEV.properties
SPOOL_FILE=output.xml
EXPORT_FILE=eb-eye_metabolights.xml
VERSION=2
FTP_LOCATION=/ebi/ftp/pub/databases/metabolights/eb-eye/

#################################
#  End of Configurable Options  #
#################################

SHELL_LOG_FILE=/nfs/production/panda/metabolights/logs/ebeye_export.log.`date +"%Y-%m-%d %H:%M:%S"` 
DB_CONNECTION=`grep hibernate.connection.username ${PROPS_FILE} | grep -v '!' | grep -v '#' |cut -f2 -d=`  
DB_CONNECTION=$DB_CONNECTION'/'`grep hibernate.connection.password ${PROPS_FILE} | grep -v '!' | grep -v '#' |cut -f2 -d=`  
DB_CONNECTION=$DB_CONNECTION'@'`grep hibernate.connection.url ${PROPS_FILE} | grep -v '!' | grep -v '#' |cut -f6 -d:`

Info ------------------------------------------------------------------------------------------ 
Info Settings:
Info '  Using properties file:             '$PROPS_FILE 
Info '  Shell script log file:             '$SHELL_LOG_FILE
Info ------------------------------------------------------------------------------------------

Info "Testing required parameters"
[ -z $PROPS_FILE ] && Error "Properties file not found, exiting"
[ -z $DB_CONNECTION ] && Error "Database connection string is not set, exiting"

Info ------------------------------------------------------------------------------------------
Info "Start"
sqlplus -s ${DB_CONNECTION} @ebeye_init.sql
sqlplus -s ${DB_CONNECTION} @ebeye_export.sql ${SPOOL_FILE} ${VERSION}
cat ${SPOOL_FILE} | perl -ne 'push(@a, $_); print shift(@a, ) if  $#a >=3 ;' > ${EXPORT_FILE}
ACTUAL_ENTRIES=`grep "<entry id=" ${EXPORT_FILE}  | wc -l` 
REPORTED_ENTRIES=`grep "<entry_count>" ${EXPORT_FILE} | cut -f2 -d'>' | cut -f1 -d'<'`

if [ $ACTUAL_ENTRIES -eq $REPORTED_ENTRIES ]
then
  Info "There are ${ACTUAL_ENTRIES} entries in the EBeye export file"
else
  Info "There are ${ACTUAL_ENTRIES} actual entries in the file, BUT we have ${REPORTED_ENTRIES} expected entries"
fi

cp $EXPORT_FILE $FTP_LOCATION
Info "File ${EXPORT_FILE} has been copied to ${FTP_LOCATION}"
mailx -s 'MetaboLights EBI global search export' ${EMAILTO} < ${SHELL_LOG_FILE}
rm "${SHELL_LOG_FILE}" 
rm $SPOOL_FILE
