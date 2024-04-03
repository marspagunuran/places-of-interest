#!/bin/sh

git checkout develop
git pull origin develop
LATEST_COMMIT=`git show --pretty=format:%s -s HEAD`
echo Latest Commit: $LATEST_COMMIT

CURRENT_VERSION=`./mvnw org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version|grep -Ev '(^\[|Download\w+:)'`
echo Current version:$CURRENT_VERSION
IFS='. ' read -r -a versions <<< "$CURRENT_VERSION"

MSG=`echo $LATEST_COMMIT | tr a-z A-Z`

NEW_VERSION=$CURRENT_VERSION

if [[ $MSG == FEATURE*  || $MSG == CHG* || $MSG == CHNAGE* ]]; then
   echo "NEW FEATURE"
   NEW_VERSION=$[versions[0]+1].0.0
 
elif [[ $MSG == FIX*  || $MSG == IMPR* ]]; then
   echo "MINOR"
   NEW_VERSION=${versions[0]}.$[versions[1]+1].0
else
   echo "PATCH"
   NEW_VERSION=${versions[0]}.${versions[1]}.$[versions[2]+1]
fi

echo $NEW_VERSION