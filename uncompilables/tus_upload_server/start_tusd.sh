TUS_SERVER_PATH='/files/'
PRIV_FTP=/net/isilon3/ftp_private/mtblight/dev/file_upload
TUS_PORT=1080
HOOKS=./hooks

alias jq='/net/isilonP/public/rw/homes/tc_cm01/metabolights/software/tus/bin/jq-linux64.dms'
./tusd -base-path $TUS_SERVER_PATH -dir $PRIV_FTP -port $TUS_PORT --hooks-dir $HOOKS