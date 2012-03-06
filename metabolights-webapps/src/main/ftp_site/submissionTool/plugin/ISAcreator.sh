#! /bin/bash

if [ `uname` == "Darwin" ]; then
  echo "This file is not intended for Mac OS X, please run ISAcreator.command instead"
  exit
fi

JAVA_OPTS="-Dfile.encoding=utf-8 -Xmx2048m -Xms512m"
export JAVA_OPTS

CURR_DIR=`dirname $0`
ISACREATOR_VERSION="1.5.0"

if [ -f $CURR_DIR/ISAcreator$ISACREATOR_VERSION.jar ]
then
    echo "Found $CURR_DIR/ISAcreator$ISACREATOR_VERSION.jar"
    java -jar $JAVA_OPTS $CURR_DIR/ISAcreator$ISACREATOR_VERSION.jar &
elif [ -f $CURR_DIR/ISAcreator-$ISACREATOR_VERSION.jar ]
then
    echo "Found $CURR_DIR/ISAcreator-$ISACREATOR_VERSION.jar"
    java -jar $JAVA_OPTS $CURR_DIR/ISAcreator-$ISACREATOR_VERSION.jar &
elif [ -f $CURR_DIR/ISAcreator.jar ]
then
    echo "Found $CURR_DIR/ISAcreator.jar"
    java -jar $JAVA_OPTS $CURR_DIR/ISAcreator.jar &
else
  echo "Can not find ISAcreator!"
  read someinput
fi
