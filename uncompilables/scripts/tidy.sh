find /nfs/production/panda/metabolights/logs/ -name "*.log" -mtime +14 -exec rm {} \; 2>&1 >/dev/null
find /nfs/www-prod/web_hx2/cm/metabolights/ml_prod/logs/ -name "*.log" -mtime +14 -exec rm {} \; 2>&1 >/dev/null
find /nfs/www-prod/web_hx2/cm/metabolights/ml_dev/logs/ -name "*.log" -mtime +14 -exec rm {} \; 2>&1 >/dev/null
find /nfs/www-prod/web_hx2/cm/metabolights/ml_test/logs/ -name "*.log" -mtime +14 -exec rm {} \; 2>&1 >/dev/null

# Tidy up on-demand generated zip files
find /nfs/www-prod/web_hx2/cm/metabolights/prod/zip_ondemand/ -name "*.zip" -mtime +60 -exec rm {} \; 2>&1 >/dev/null
find /nfs/www-prod/web_hx2/cm/metabolights/prod/zip_ondemand/ -name "MTBLS*_*_*.zip" -mtime +1 -exec rm {} \; 2>&1 >/dev/null
