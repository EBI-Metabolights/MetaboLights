#!/bin/bash

FILE_PATH=$(realpath $(dirname "$0"))
PARENT_PATH=$(dirname "$FILE_PATH")

echo "Parent path: $PARENT_PATH"
bash $PARENT_PATH/deploy.sh metabolights-web "$APP_VERSION"
