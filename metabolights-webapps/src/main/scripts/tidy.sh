# Tidy up old submission files
find /net/isilonP/public/rw/homes/tc_cm01/upload/* -mtime +5 -exec rm -Rf {} \;
find /net/isilonP/public/rw/homes/tc_cm01/isatab/metadata/* -mtime +5 -exec rm -Rf {} \;
find /nfs/production/panda/metabolights/logs/* -mtime +14 -exec rm  {} \;
