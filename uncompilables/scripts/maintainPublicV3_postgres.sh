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
# 20161205  : Ken Haug - PostgreSQL version
# 20161220  : Ken Haug - Included PostgreSQL backup
#
##########################################################################

. /nfs/production/panda/metabolights/source/metabolights/metabolights-webapps/src/main/scripts/logging_functions

#################################
#  Configurable Options Follow  #
#################################

EMAILTO=metabolights-dev@ebi.ac.uk,metabolights-curation@ebi.ac.uk
SCRIPT_LOC=/nfs/www-prod/web_hx2/cm/metabolights/scripts
PRIVATE_LOC=/nfs/www-prod/web_hx2/cm/metabolights/prod/studies/stage/private
PUBLIC_FTP_LOC=/ebi/ftp/pub/databases/metabolights/studies/public
NUM_DAYS=2

source $SCRIPT_LOC/postgres.properties.prod
#################################
#  End of Configurable Options  #
#################################

SHELL_LOG_FILE=$SCRIPT_LOC/maintainPublicV3.log 

# Get private studies that have passed the release date
GET_STUDIES_SQL="select acc from studies where status = 3 AND ((date_trunc('day',releasedate)>=date_trunc('day',current_date-${NUM_DAYS})) or (date_trunc('day',updatedate) >=date_trunc('day',current_date-${NUM_DAYS})));"

rm $SHELL_LOG_FILE
Info ------------------------------------------------------------------------------------------ 
Info Settings:
Info '  Shell script log file:             '$SHELL_LOG_FILE
Info '  Number of days to sync:            '$NUM_DAYS
Info ------------------------------------------------------------------------------------------

Info "Testing required parameters"
[ -z $PG_HOST ] && Error "Database host not known, exiting"
[ -z $PG_USER ] && Error "Database user string is not set, exiting"

Info ------------------------------------------------------------------------------------------
Info "Start"

PUBLIC_STUDIES=`$PG_COMMAND -c "${GET_STUDIES_SQL}" | grep MTBLS`
 
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
$PG_COMMAND -f $SCRIPT_LOC/ml_stats2_postgres.sql

Info "Starting Postgres backup"
$SCRIPT_LOC/backup_postgres.sh

[ -z $PUBLIC_STUDIES ] ||  mailx -s 'MetaboLights PostgreSQL Public File Maintenance' ${EMAILTO} < ${SHELL_LOG_FILE}
