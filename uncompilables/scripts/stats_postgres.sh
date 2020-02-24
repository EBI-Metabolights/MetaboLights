cd $HOME/metabolights/scripts
# ./calc_sample_size.sh
./calc_StudySize_postgres.sh
source ./postgres.properties.prod
${PG_COMMAND} -f ml_stats2_postgres.sql
./update_study_meta_stats.sh 
#source ./postgres.properties.dev
#${PG_COMMAND} -f ml_stats2_postgres.sql
#source ./postgres.properties.test
#${PG_COMMAND} -f ml_stats2_postgres.sql
