#!/bin/ksh
##########################################################################
#
#   File    : email_curators.sh
#   Type    : Korn Shell Script
#
#   Purpose : Email metabolights-curators with studies going live over
#	      the next 3 weeks 
#
# 20150414  : Ken Haug - Created script
# 20161220  : Ken Haug - Postgres version
#
##########################################################################
SCRIPT_LOC=/nfs/www-prod/web_hx2/cm/metabolights/scripts
source $SCRIPT_LOC/postgres.properties.prod
EMAIL=metabolights-curation@ebi.ac.uk

# Get private studies that are passed the release date
GET_STUDIES_SQL="select lpad(acc,10,' ')||CHR(9)||to_char(releasedate,'DD MON YYYY')||CHR(9)|| case when status = 0 then 'Submitted' when status = 1 then 'In Curation' when status = 2 then 'In Review' when status = 3 then 'Public' else 'Dormant' end as Studies_not_yet_released from studies where status in(0,1,2) order by releasedate asc;"
$PG_COMMAND -c "${GET_STUDIES_SQL}"