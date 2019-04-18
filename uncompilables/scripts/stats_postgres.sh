cd $HOME/metabolights/scripts
./calc_StudySize_postgres.sh
./calc_sample_size.sh
source ./postgres.properties.prod
${PG_COMMAND} -f ml_stats2_postgres.sql
source ./postgres.properties.dev
${PG_COMMAND} -f ml_stats2_postgres.sql
source ./postgres.properties.test
${PG_COMMAND} -f ml_stats2_postgres.sql
