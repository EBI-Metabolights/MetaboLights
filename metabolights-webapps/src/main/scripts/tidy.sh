# Tidy up old submission files
find /nfs/public/rw/homes/tc_cm01/upload/* -mtime +5 -exec rm -Rf {} \;
find /nfs/public/rw/homes/tc_cm01/isatab/metadata/* -mtime +5 -exec rm -Rf {} \;
find /nfs/production/panda/metabolights/logs/* -mtime +14 -exec rm  {} \;

# Tidy up on-demand generated zip files
find /nfs/public/rw/homes/tc_cm01/zip_ondemand/ -name "*.zip" -mtime +5 -exec rm -Rf {} \;