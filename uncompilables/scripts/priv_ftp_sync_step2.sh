#!/bin/bash

if [ -n $1 -a -z $1 ] || [ -n $2 -a -z $2 ] ; then
    echo "Invalid param: $1"
    echo "Use $0 [-s|--Server] [test|dev|prod]"
    exit
fi

case $1 in
    -s|--Server)

    case $2 in
        test|TEST)
        SERVER="test"
        ;;
        dev|DEV)
        SERVER="dev"
        ;;
        prod|PROD)
        SERVER="prod"
        ;;
        *)
        # unknown option
        echo "Invalid param: $2"
        echo "Use $0 [-s|--Server] [test|dev|prod]"
        exit
        ;;
    esac

    echo Right, will use:  $SERVER
    ;;
    *)
    # unknown option
    echo "Invalid param: $1"
    echo "Use $0 [-s|--Server] [test|dev|prod]"
    exit
    ;;
esac

VER=$SERVER
FS_FOLDER="/net/isilonP/public/rw/homes/tc_cm01/metabolights/ftp_private"
FTP_FOLDER="/ebi/ftp/private/mtblight"

# Syncing FTP_FOLDER into FS_FOLDER
echo Syncing $FTP_FOLDER/$VER into $FS_FOLDER/$VER
rsync -av --partial $FTP_FOLDER/$VER/ $FS_FOLDER/$VER/
