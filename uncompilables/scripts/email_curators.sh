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
#
##########################################################################
source /homes/oracle/ora11setup.sh
DB_CONNECTION=user/pass@database
EMAIL=metabolights-curation@ebi.ac.uk
SQL_BASIC_STR="set pagesize 100\n column acc format a10\n column ReleaseDate format a20"
GET_STUDIES_SQL="${SQL_BASIC_STR}\n select acc||CHR(9)||to_char(releasedate,'DD MON YYYY')||CHR(9)||decode(status,0,'Submitted',1,'In Curation',2,'In Review','Public') from studies where status in(0,1,2) AND trunc(releasedate)<=trunc(sysdate+21) order by releasedate asc;"
echo -e ${GET_STUDIES_SQL} | sqlplus -s ${DB_CONNECTION}
