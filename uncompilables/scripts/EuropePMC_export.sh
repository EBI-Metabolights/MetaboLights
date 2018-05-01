#!/bin/bash
USER=<user>
PASS=<password>
FILE=$1

ftp -ivn <server.ebi.ac.uk> << EOF
user $USER $PASS
cd <folder>
put ${FILE}
quit
EOF
