#!/bin/bash

if [ `uname` != "Darwin" ]; then
  echo "This file is only for Mac OS X"
  exit
fi  

JAVA_OPTS="-Dfile.encoding=UTF-8 -Xmx2048m -Xms512m $JAVA_OPTS";export JAVA_OPTS

CURR_DIR=`dirname $0`
ISACREATOR_VERSION="1.5.0"

cd $CURR_DIR
echo "ISAcreator version is ${ISACREATOR_VERSION}.  Java options are $JAVA_OPTS"

if [ -f $CURR_DIR/ISAcreator$ISACREATOR_VERSION.jar ]; then
  echo "Found $CURR_DIR/ISAcreator$ISACREATOR_VERSION.jar"
  java -jar $CURR_DIR/ISAcreator$ISACREATOR_VERSION.jar &
elif [ -f $CURR_DIR/ISAcreator.jar ]; then
  echo "Found $CURR_DIR/ISAcreator.jar"
  java -jar $CURR_DIR/ISAcreator.jar &
elif [ -f $CURR_DIR/ISAcreator-$ISACREATOR_VERSION.jar ]; then
  echo "Found $CURR_DIR/ISAcreator-$ISACREATOR_VERSION.jar"
  java -jar $CURR_DIR/ISAcreator.jar &
elif [ -f $CURR_DIR/ISAcreator-$ISACREATOR_VERSION.app/Contents/Info.plist ]; then
  echo "Found $CURR_DIR/ISAcreator-$ISACREATOR_VERSION.app"
  open -a $CURR_DIR/ISAcreator-$ISACREATOR_VERSION.app &
else
  echo "Can not find ISAcreator!"
fi
read someinput