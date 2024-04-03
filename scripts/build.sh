#!/bin/sh

PROJECT="${PWD##*/}"

if [ -z "$ARTIFACTORY_SETTINGS" ]
then
      echo "\$ARTIFACTORY_SETTINGS is empty"
      if [[ "$@" == "clean" ]]
	  then
		echo "Clean ${PROJECT}"
	    ./mvnw clean
	  elif [[ "$@" == "test" ]]
	  then
		echo "Test ${PROJECT}"
		./mvnw clean verify -Dmaven.test.failure.ignore=true
	  elif [[ "$@" == "upload" ]]
	  then
		echo "Upload ${PROJECT}"
		./mvnw deploy
	  elif [[ "$@" == "dev" ]]
	  then
		echo "Dev ${PROJECT}"
		java -jar target/*.jar
	  else
		echo "Build ${PROJECT}"
	  	./mvnw clean package -DskipTests 
	  fi
else
      echo "maven setting:$ARTIFACTORY_SETTINGS"
      if [[ "$@" == "clean" ]]
	  then
		echo "Clean ${PROJECT}"
	    ./mvnw clean -s "$ARTIFACTORY_SETTINGS"
	  elif [[ "$@" == "test" ]]
	  then
		echo "Test ${PROJECT}"
		./mvnw clean verify -Dmaven.test.failure.ignore=true -s "$ARTIFACTORY_SETTINGS"
	  elif [[ "$@" == "upload" ]]
	  then
		echo "Upload ${PROJECT}"
		./mvnw deploy -s "$ARTIFACTORY_SETTINGS"
	  elif [[ "$@" == "dev" ]]
	  then
		echo "Dev ${PROJECT}"
		java -jar target/*.jar
	  else
		echo "Build ${PROJECT}"
	  	./mvnw clean package -DskipTests -s "$ARTIFACTORY_SETTINGS"
	  fi
fi
