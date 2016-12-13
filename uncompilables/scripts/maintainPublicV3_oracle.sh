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
# 20110912  : Ken Haug - Simplified and more comprehensive file checks
# 20110914  : Ken Haug - Changed email notifications to Metabolights-dev@ebi.ac.uk
# 20120328  : Ken Haug - Removed "clear" as this is dependent on a TERM set, like xterm
# 20121115  : Ken Haug - Move folder as we no longer store the studies as zip files
# 20130103  : Ken Haug - Simplified to use the new queue system
# 20130312  : Ken Haug - Export to EB-eye when studies go public
# 20130321  : Ken Haug - Email users a week before the study goes live (and each day until it goes live!)
# 20130815  : Ken Haug - Make sure we only loop through MTBLS records, not error messages in the queue folder
# 20150914  : Ken Haug - New folder stucture, only sync public data directly with public ftp site
# 20160309  : Ken Haug - Maintenance of MetExplore mapping files
#
##########################################################################

. /nfs/production/panda/metabolights/source/metabolights/metabolights-webapps/src/main/scripts/logging_functions
source /homes/oracle/ora11setup.sh

#################################
#  Configurable Options Follow  #
#################################

EMAILTO=metabolights-dev@ebi.ac.uk,metabolights-curation@ebi.ac.uk
PROPS_FILE=/nfs/production/panda/metabolights/source/metabolights/metabolights-webapps/src/main/scripts/hibernate.properties
SCRIPT_LOC=/nfs/www-prod/web_hx2/cm/metabolights/scripts
#SCRIPT_LOC=/nfs/public/rw/homes/tc_cm01/metabolights/scripts
#PRIVATE_LOC=/net/isilonP/public/rw/homes/tc_cm01/metabolights/prod/studies/stage/private
PRIVATE_LOC=/nfs/www-prod/web_hx2/cm/metabolights/prod/studies/stage/private
PUBLIC_FTP_LOC=/ebi/ftp/pub/databases/metabolights/studies/public
NUM_DAYS=2

#################################
#  End of Configurable Options  #
#################################

SHELL_LOG_FILE=$SCRIPT_LOC/maintainPublicV3.log 
DB_CONNECTION=`grep hibernate.connection.username ${PROPS_FILE} | grep -v '!' | grep -v '#' |cut -f2 -d=`  
DB_CONNECTION=$DB_CONNECTION'/'`grep hibernate.connection.password ${PROPS_FILE} | grep -v '!' | grep -v '#' |cut -f2 -d=`  
DB_CONNECTION=$DB_CONNECTION'@'`grep hibernate.connection.url ${PROPS_FILE} | grep -v '!' | grep -v '#' |cut -f6 -d:`
QUEUE_LOCATION=`grep queuelocation ${PROPS_FILE} | grep -v '!' | grep -v '#' |cut -f2 -d=`
SQL_BASIC_STR="whenever sqlerror exit failure;\n set feedback off head off pagesize 0;\n "
# Get private studies that have passed the release date
GET_STUDIES_SQL="${SQL_BASIC_STR} select acc from studies where status = 3 AND ((trunc(releasedate)>=trunc(sysdate-${NUM_DAYS})) or (trunc(updatedate) >=trunc(sysdate-${NUM_DAYS})));"

rm $SHELL_LOG_FILE
Info ------------------------------------------------------------------------------------------ 
Info Settings:
Info '  Using properties file:             '$PROPS_FILE 
Info '  Shell script log file:             '$SHELL_LOG_FILE
Info '  Number of days to sync:            '$NUM_DAYS
Info ------------------------------------------------------------------------------------------

Info "Testing required parameters"
[ -z $PROPS_FILE ] && Error "Properties file not found, exiting"
[ -z $DB_CONNECTION ] && Error "Database connection string is not set, exiting"
[ -z $GET_STUDIES_SQL ] && Error "GET_STUDIES_SQL is not set, exiting"

Info ------------------------------------------------------------------------------------------
Info "Start"

PUBLIC_STUDIES=`echo -e ${GET_STUDIES_SQL} | sqlplus -s ${DB_CONNECTION} | grep MTBLS`
 
for studies in $PUBLIC_STUDIES
do
  Info "Removing MetExplore json mapping file"
  rm metexplore_mapping.json
  Info "Study ${studies} is now being synced to the public ftp folder"
  mkdir -p ${PUBLIC_FTP_LOC}/${studies}
  rsync -av ${PRIVATE_LOC}/${studies}/ ${PUBLIC_FTP_LOC}/${studies}
  chmod -R go+rx ${PUBLIC_FTP_LOC}/${studies}
done


Info "Update statistics table"
sqlplus -s ${DB_CONNECTION} @$SCRIPT_LOC/ml_stats2.sql

[ -z $PUBLIC_STUDIES ] ||  mailx -s 'MetaboLights Public File Maintenance' ${EMAILTO} < ${SHELL_LOG_FILE}

