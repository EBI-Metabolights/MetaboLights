#!/bin/ksh
##########################################################################
#
#   File    : email_curators.sh
#   Type    : Korn Shell Script
#
#   Purpose : Email metabolights-curators with studies going live over
#             the next 3 weeks 
#
# 20150414  : Ken Haug - Created script
#
##########################################################################
source /homes/oracle/ora11setup.sh
DB_CONNECTION=isatab/ethanol1@mtaprovm
EMAIL=kenneth@ebi.ac.uk
SQL_BASIC_STR="set pagesize 100\n column acc format a10\n column ReleaseDate format a20"
# Get private studies that are passed the release date
GET_STUDIES_SQL="${SQL_BASIC_STR}\n select acc, to_char(releasedate,'DD MON YYYY') ReleaseDate from study where status = 1 AND trunc(releasedate)<=trunc(sysdate+21) order by 2 asc;"
echo -e ${GET_STUDIES_SQL} | sqlplus -s ${DB_CONNECTION}
