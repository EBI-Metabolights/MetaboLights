# Tidy up old submission files
find /nfs/production/panda/metabolights/isatab/upload/ -mtime +5 -exec rm -Rf {} \;
find /nfs/production/panda/metabolights/isatab/metadata/ -mtime +5 -exec rm -Rf {} \;
find /nfs/production/panda/metabolights/logs/ -mtime +14 -exec rm -Rf {} \;
