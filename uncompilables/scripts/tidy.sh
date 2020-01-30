###################
# CHEBI
###################

find /net/isilonP/public/rw/homes/cm_adm/chebi/tc-cm-chebi-submission/logs/ves-ebi-97/ -name "*.log*" -mtime +14 -exec rm {} \; 2>&1 >/dev/null 
find /net/isilonP/public/rw/homes/cm_adm/chebi/tc-cm-chebi-submission/logs/ves-ebi-97/ -name "*.out*" -mtime +14 -exec rm {} \; 2>&1 >/dev/null 
find /net/isilonP/public/rw/homes/cm_adm/chebi/tc-cm-chebi-submission/logs/ves-ebi-98/ -name "*.log*" -mtime +14 -exec rm {} \; 2>&1 >/dev/null 
find /net/isilonP/public/rw/homes/cm_adm/chebi/tc-cm-chebi-submission/logs/ves-ebi-98/ -name "*.out*" -mtime +14 -exec rm {} \; 2>&1 >/dev/null 

##################
# METABOLIGHTS
###################

find /nfs/production/panda/metabolights/ -name "*.log" -mtime +14 -exec rm {} \; 2>&1 >/dev/null
find /nfs/production/panda/metabolights/ -name "*.out" -mtime +14 -exec rm {} \; 2>&1 >/dev/null
find /nfs/www-prod/web_hx2/cm/metabolights/ml_prod/logs/ -name "*.log" -mtime +14 -exec rm {} \; 2>&1 >/dev/null
find /nfs/www-prod/web_hx2/cm/metabolights/ml_prod/logs/ -name "*.out" -mtime +14 -exec rm {} \; 2>&1 >/dev/null
find /nfs/www-prod/web_hx2/cm/metabolights/ml_dev/logs/ -name "*.log" -mtime +7 -exec rm {} \; 2>&1 >/dev/null
find /nfs/www-prod/web_hx2/cm/metabolights/ml_dev/logs/ -name "*.out" -mtime +7 -exec rm {} \; 2>&1 >/dev/null
find /nfs/www-prod/web_hx2/cm/metabolights/ml_test/logs/ -name "*.log" -mtime +4 -exec rm {} \; 2>&1 >/dev/null
find /nfs/www-prod/web_hx2/cm/metabolights/ml_test/logs/ -name "*.out" -mtime +7 -exec rm {} \; 2>&1 >/dev/null

# Tidy up on-demand generated zip files
find /nfs/www-prod/web_hx2/cm/metabolights/prod/zip_ondemand/ -name "*.zip" -mtime +14 -exec rm {} \; 2>&1 >/dev/null
find /nfs/www-prod/web_hx2/cm/metabolights/prod/zip_ondemand/ -name "MTBLS*_*_*.zip" -mtime +1 -exec rm {} \; 2>&1 >/dev/null

cd /ebi/ftp/private/mtblight/prod/userSpace/
find . -atime +180 -exec mv {} ../old \;

