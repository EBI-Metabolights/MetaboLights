# Tidy up old submission files
find /nfs/public/rw/homes/tc_cm01/metabolights/prod/upload/* -mtime +15 -exec rm -Rf {} \; 2>&1 >/dev/null
find /nfs/public/rw/homes/tc_cm01/metabolights/dev/isatab/metadata/* -mtime +5 -exec rm -Rf {} \; 2>&1 >/dev/null
find /nfs/public/rw/homes/tc_cm01/metabolights/prod/isatab/metadata/* -mtime +15 -exec rm -Rf {} \; 2>&1 >/dev/null

find /nfs/production/panda/metabolights/logs/* -mtime +14 -exec rm -f {} \; 2>&1 >/dev/null
find /net/isilonP/public/rw/homes/tc_cm01/ml_prod/logs/* -mtime +14 -exec rm -f {} \; 2>&1 >/dev/null
find /net/isilonP/public/rw/homes/tc_cm01/ml_test/logs/* -mtime +14 -exec rm -f {} \; 2>&1 >/dev/null

# Tidy up on-demand generated zip files
find /nfs/public/rw/homes/tc_cm01/metabolights/prod/zip_ondemand/ -name "*.zip" -mtime +60 -exec rm {} \; 2>&1 >/dev/null
find /nfs/public/rw/homes/tc_cm01/metabolights/prod/zip_ondemand/ -name "MTBLS*_*_*.zip" -exec rm {} \; 2>&1 >/dev/null