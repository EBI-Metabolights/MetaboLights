#!/bin/bash

FILE_PATH=$(realpath $(dirname "$0"))
echo "Current path: $FILE_PATH"
PARENT_PATH=$(dirname "$FILE_PATH")
echo "Parent path: $PARENT_PATH"
source $PARENT_PATH/configuration.sh "$CI_COMMIT_REF_NAME" "$APP_VERSION"

echo  "CI_REGISTRY $CI_REGISTRY"
docker image prune -f
echo "$CI_REGISTRY_PASSWORD" | docker login -u "$CI_REGISTRY_USER" "$CI_REGISTRY" --password-stdin
if [ $? -eq 0 ]; then
    echo "Docker login successfully "
else
    echo "Docker login failed"
    exit 1
fi

echo "Build number $BUILD_NUMBER, commit name ${CI_COMMIT_REF_NAME}"
echo "${BUILD_NUMBER}" > build_number

echo  "$APP_VERSION" | xargs > app_version
echo  "APP_VERSION $APP_VERSION"

echo  "$APP_VERSION" | xargs > api_version
echo  "$APP_VERSION $API_VERSION"

gradle build -x test

echo "docker build --build-arg GROUP1_ID=$MTBLS_NFS_USER_GROUP1_ID --build-arg GROUP2_ID=$MTBLS_NFS_USER_GROUP2_ID --build-arg USER_ID=$MTBLS_NFS_USER_ID -t $IMAGE_NAME -t $LATEST_IMAGE_NAME ."
docker build --build-arg GROUP1_ID=$MTBLS_NFS_USER_GROUP1_ID --build-arg GROUP2_ID=$MTBLS_NFS_USER_GROUP2_ID --build-arg USER_ID=$MTBLS_NFS_USER_ID -t $IMAGE_NAME .

if [ $? -eq 0 ]; then
    git status
    echo "Docker build completed successfully. Image name: $IMAGE_NAME"
    echo "Docker build completed successfully. Image name: $LATEST_IMAGE_NAME"

else
    git status
    echo "Docker build task failed. Image name: $IMAGE_NAME"
    echo "Docker build task failed. Image name: $LATEST_IMAGE_NAME"
    exit 1
fi


docker push $IMAGE_NAME
if [ $? -eq 0 ]; then
    git status
    echo "Docker push is successfull. Image name: $IMAGE_NAME"
else
    git status
    echo "Docker push task failed. Image name: $IMAGE_NAME"
    exit 1
fi


docker push $LATEST_IMAGE_NAME

if [ $? -eq 0 ]; then
    git status
    echo "Docker push is successfull. Image name: $LATEST_IMAGE_NAME"
else
    git status
    echo "Docker push task failed. Image name: $LATEST_IMAGE_NAME"
    exit 1
fi
