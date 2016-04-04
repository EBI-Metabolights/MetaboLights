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

    echo Nice, will use:  $SERVER
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
DEL_PATTERN=".DELETEME-"

# remove the hidden files
echo Removing hidden files in $FS_FOLDER/$VER
find $FS_FOLDER/$VER -type f -name '.DELETEME-*' | while read -r file; do
    DIR=$(dirname "$file")
    FILENAME=$(basename "$file")
    FILENAME=${FILENAME#$DEL_PATTERN}
    FILE=${file#$FS_FOLDER/$VER/}
    SUBDIR=$(dirname $FILE)

    echo Found $FILE
    echo   Checking if $FTP_FOLDER/$VER/$SUBDIR/$FILENAME exists
    if [ -f $FTP_FOLDER/$VER/$SUBDIR/$FILENAME ]; then
      echo Deleting $FTP_FOLDER/$VER/$SUBDIR/$FILENAME
      rm $FTP_FOLDER/$VER/$SUBDIR/$FILENAME
      echo Deleting $file
      rm $file
    fi
done
echo done
echo


# remove the hidden folders
echo Removing hidden folders in $FS_FOLDER/$VER
find $FS_FOLDER/$VER -type d -name '.DELETEME-*' | while read -r file; do

echo $file
echo
    DIR=$(dirname "$file")
    FILENAME=$(basename "$file")
    FILENAME=${FILENAME#$DEL_PATTERN}
    FILE=${file#$FS_FOLDER/$VER/}
    SUBDIR=$(dirname $FILE)

    echo Checking if $FTP_FOLDER/$VER/$SUBDIR/$FILENAME exists
    if [ -d $FTP_FOLDER/$VER/$SUBDIR/$FILENAME ]; then
      echo Deleting $FTP_FOLDER/$VER/$SUBDIR/$FILENAME
      rm -r $FTP_FOLDER/$VER/$SUBDIR/$FILENAME
      echo Deleting $file
      rm -r $file
    fi
done
echo done
echo

# create new requested folders
echo Creating new requested folders...
### rsync -rdpv $FS_FOLDER/$VER/ $FTP_FOLDER/$VER/
rsync -av -f"+ */" -f"- *" $FS_FOLDER/$VER/ $FTP_FOLDER/$VER/
echo
