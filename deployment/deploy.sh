#!/bin/bash

FILE_PATH=$(realpath $(dirname "$0"))
CHART_NAME="$1"
VERSION="$2"
echo "File path: $PARENT_PATH"
source $FILE_PATH/configuration.sh "$CI_COMMIT_REF_NAME" "$VERSION"

mkdir -p build
cd build

echo "git clone https://$CI_REGISTRY_USER:$CI_REGISTRY_PASSWORD@${APPS_PROJECT_URL}"
git clone https://$CI_REGISTRY_USER:$CI_REGISTRY_PASSWORD@${APPS_PROJECT_URL}
if [ $? -eq 0 ]; then
    git status
    echo "Apps project is cloned."
else
    git status
    echo "App project clone task failed."
    exit 1
fi

cd apps
echo "git switch $APPS_PROJECT_BRANCH_NAME"
git switch $APPS_PROJECT_BRANCH_NAME
if [ $? -eq 0 ]; then
    git status
    echo "The latest configuration on branch apps $APPS_PROJECT_BRANCH_NAME."
else
    git status
    echo "Branch checkout task failed."
    exit 1
fi

git pull

if [ $? -eq 0 ]; then
    git status
    echo "he latest configuration on apps $APPS_PROJECT_BRANCH_NAME is pulled."
else
    git status
    echo "The latest configuration task failed."
    exit 1
fi

APPS_ROOT_PATH=$(pwd)
DEPLOYMENTS_FOLDER_SCRIPTS="$APPS_ROOT_PATH/$DEPLOYMENTS_FOLDER/scripts"
DEPLOYMENTS_CHART_PATH="$APPS_ROOT_PATH/$DEPLOYMENTS_FOLDER/charts/$CHART_NAME"


cd $DEPLOYMENTS_FOLDER_SCRIPTS
ls -al
echo "cd $DEPLOYMENTS_FOLDER_SCRIPTS"
echo "Init environment"
echo "---------------------------------------------------------------------"
bash initial_setup.sh
echo "---------------------------------------------------------------------"

echo "cd $DEPLOYMENTS_CHART_PATH"
cd $DEPLOYMENTS_CHART_PATH
ls -al
echo "Template content"
echo "---------------------------------------------------------------------"
bash template.sh "image.repository=$CI_REGISTRY_IMAGE,image.tag=$IMAGE_TAG"
echo "---------------------------------------------------------------------"


echo "Deploy"
echo "---------------------------------------------------------------------"
bash install.sh "image.repository=$CI_REGISTRY_IMAGE,image.tag=$IMAGE_TAG"
if [ $? -eq 0 ]; then
    echo "Deployment completed."
else
    echo "Deployment failed."
    exit 1
fi
echo "---------------------------------------------------------------------"
