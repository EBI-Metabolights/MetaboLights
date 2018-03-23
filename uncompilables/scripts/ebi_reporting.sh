cd $HOME/metabolights/scripts
rm MetaboLights_nmr.tsv MetaboLights_ms.tsv
source ./postgres.properties.prod
${PG_COMMAND} -f ebi_reporting.sql
${PG_COMMAND} -t -AF $'\t' --no-align -c "select submonth as date, size as bytes from ebi_reporting where ms_nmr='nmr';" -o MetaboLights_nmr.tsv 
${PG_COMMAND} -t -AF $'\t' --no-align -c "select submonth as date, size as bytes from ebi_reporting where ms_nmr='ms';" -o MetaboLights_ms.tsv
LAST_DATE=`tail -1 MetaboLights_ms.tsv | cut -f1 -d$'\t'`
echo -e "date\tbytes" | cat - MetaboLights_nmr.tsv > /nfs/web-hx/webadmin/tsc-usage/servicereportdata/${LAST_DATE}_MetaboLights_nmr.txt 
echo -e "date\tbytes" | cat - MetaboLights_ms.tsv > /nfs/web-hx/webadmin/tsc-usage/servicereportdata/${LAST_DATE}_MetaboLights_ms.txt 
