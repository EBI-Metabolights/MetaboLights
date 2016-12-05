cd $HOME/metabolights/scripts
./calc_StudySize_postgres.sh
PGPASSWORD="xx"
PG_USER=xx
PG_DB=xx
PG_HOST=xx
PG_PORT=xx
PG_COMMAND="psql -U ${PG_USER} -w -d ${PG_DB} -h ${PG_HOST} -p ${PG_PORT}"

$PG_COMMAND -f @$SCRIPT_LOC/ml_stats2.sql
