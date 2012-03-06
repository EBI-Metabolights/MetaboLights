#!/bin/bash

# Ken Haug.  Mac OS X startup script to set UTF-8 and memory parameters
# The MetaboLights team
#
if [ `uname` != "Darwin" ]; then
  echo "This file is only for Mac OS X"
  exit
fi  

JAVA_OPTS="-Dfile.encoding=utf-8 -Xmx2048m -Xms512m"
export JAVA_OPTS

CURR_DIR=`dirname $0`
ISACREATOR_VERSION="1.5.0"
PLIST="Contents/Info.plist"

cd $CURR_DIR
echo "ISAcreator version is ${ISACREATOR_VERSION}"  
echo "Java options are $JAVA_OPTS"
echo "Working directory is $CURR_DIR"

if [ -f $CURR_DIR/ISAcreator$ISACREATOR_VERSION.jar ]
then
    echo "Found $CURR_DIR/ISAcreator$ISACREATOR_VERSION.jar"
    java -jar $JAVA_OPTS $CURR_DIR/ISAcreator$ISACREATOR_VERSION.jar &
elif [ -f $CURR_DIR/ISAcreator.jar ]
then
    echo "Found $CURR_DIR/ISAcreator.jar"
    java -jar $JAVA_OPTS $CURR_DIR/ISAcreator.jar &
elif [ -f $CURR_DIR/ISAcreator-$ISACREATOR_VERSION.jar ]
then
    echo "Found $CURR_DIR/ISAcreator-$ISACREATOR_VERSION.jar"
    java -jar $JAVA_OPTS $CURR_DIR/ISAcreator.jar &
elif [ -f $CURR_DIR/ISAcreator-$ISACREATOR_VERSION.app/$PLIST ]
then
    echo "Found $CURR_DIR/ISAcreator-$ISACREATOR_VERSION.app"
    #Check if we need to add UTF-8 settings
    LPLIST=$CURR_DIR/ISAcreator-$ISACREATOR_VERSION.app/$PLIST
    UTF8_SET=`grep -c "file.encoding=utf-8" $LPLIST`
    ORIG_TEXT="-Xmx512m -Xms128m"

    if [ $UTF8_SET == 0 ]
    then
       echo "Adding UFT-8 settings to the startup of the application (${LPLIST})" 
       echo "Replacing $ORIG_TEXT with $JAVA_OPTS"
       sed -i.bak "s/$ORIG_TEXT/$JAVA_OPTS/g" $LPLIST
    else
       echo "UFT-8 already set"
    fi

  echo "Found $CURR_DIR/ISAcreator-$ISACREATOR_VERSION.app"
  open $CURR_DIR/ISAcreator-$ISACREATOR_VERSION.app &
else
  echo "Can not find ISAcreator!"
  read someinput
fi
