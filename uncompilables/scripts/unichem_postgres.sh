cd $HOME/metabolights/scripts
source ./postgres.properties.prod
${PG_COMMAND} --no-align --field-separator $'\t' --pset footer=off -f unichem_postgres.sql > unichem_temp.tsv
grep MTBLC unichem_temp.tsv > /ebi/ftp/pub/databases/metabolights/eb-eye/unichem.tsv 
wc -l /ebi/ftp/pub/databases/metabolights/eb-eye/unichem.tsv
