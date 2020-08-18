/ustUpdateDate = $(git --git-dir "$dir/.git"  log -1 --format="%at")
now=$(date +'%s')
if [ ($now - lastUpdateDate)/86400 -gt 24 ]; then
 echo " todays date $now"
else
    echo "last update was on  $lastUpdateDate"sr/local/bin/bash: indent: command not found
