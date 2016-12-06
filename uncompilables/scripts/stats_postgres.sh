cd $HOME/metabolights/scripts
./calc_StudySize_postgres.sh
source ./postgres.properties
${PG_COMMAND} -f ml_stats2_postgres.sql
