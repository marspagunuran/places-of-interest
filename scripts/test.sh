#!/bin/sh

if [ -z "$ARTIFACTORY_SETTINGS" ]
then
    echo "\$ARTIFACTORY_SETTINGS is empty"
	./mvnw clean verify
      
else
	echo "maven setting:$ARTIFACTORY_SETTINGS"
    ./mvnw clean verify -s "$ARTIFACTORY_SETTINGS"
fi

