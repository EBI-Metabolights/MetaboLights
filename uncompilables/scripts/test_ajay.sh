lastUpdateDate=$(git --git-dir "/net/isilonP/public/rw/homes/tc_cm01/metabolights/software/ws-py/prod/MtblsWS-Py/.git" log -1 --format="%at")
now=$(date +'%s')
let diff="$now"-"$lastUpdateDate"
echo $diff
if [ "$diff" -gt 86400 ]
then
echo "helllo"
else
echo "non hello"
fi

