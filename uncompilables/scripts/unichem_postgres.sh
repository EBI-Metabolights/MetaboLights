cd $HOME/metabolights/scripts
source ./postgres.properties.prod
${PG_COMMAND} -f unichem_postgres.sql > unichem_temp.tsv
grep MTBLC unichem_temp.tsv > /ebi/ftp/pub/databases/metabolights/eb-eye/unichem.tsv 
