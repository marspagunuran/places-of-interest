#!/bin/sh
if [ -z "$1" ]; then
    echo "./weekly_patch.sh 1.X"
    exit 1
else
    REL_BRANCH=PL-$1
fi

git checkout $REL_BRANCH
git pull
git fetch --tags
LATEST_COMMIT=`git show --pretty=format:%s -s HEAD`
echo Latest Commit: $LATEST_COMMIT

CURRENT_VERSION=`./mvnw org.apache.maven.plugins:maven-help-plugin:3.2.0:evaluate -Dexpression=project.version -q -DforceStdout`
IFS='. ' read -r -a versions <<< "$CURRENT_VERSION"
NEW_VERSION=${versions[0]}.${versions[1]}.$[versions[2]+1]

echo $CURRENT_VERSION"==>"$NEW_VERSION

./mvnw versions:set-property -Dproperty=platform.service.version -DnewVersion=$NEW_VERSION
if [ $? -eq 0 ]
then
  echo -e "mvn version $NEW_VERSION successfully"
else
  echo -e "mvn version $NEW_VERSION failed"
  exit 1
fi
git add pom.xml
git add */pom.xml
git commit -m "RELEASE:$NEW_VERSION"
git tag -a $NEW_VERSION -m $NEW_VERSION
git push
git push --tags


# send out release notifiction and generate release note
TESTING=''
PROXY_URL=localhost:7999
PROJECT="${PWD##*/}"
PRE_VERSION=$CURRENT_VERSION
REL_VERSION=$NEW_VERSION
USER=$(git show -s --format='%an')
RELEASE_NOTES=""
PAYLOAD="*${TESTING}${PROJECT}* updated to ${REL_VERSION},published by $USER\n \`\`\`$PROJECT@$REL_VERSION\`\`\` *Release Notes($REL_BRANCH):*\n$RELEASE_NOTES"
echo $PAYLOAD
DATA={\"text\":\"$PAYLOAD\"}
curl -X POST -H 'Content-type: application/json' --data "${DATA}" https://hooks.slack.com/services/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
