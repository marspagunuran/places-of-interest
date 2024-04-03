#!/bin/sh -e

FORMAT_VERSION=1.7
FORMAT_JAR="$HOME/Downloads/google-java-format-$FORMAT_VERSION-all-deps.jar"

if [ ! -f "$FORMAT_JAR" ]; then
    wget -O "$FORMAT_JAR" https://github.com/google/google-java-format/releases/download/google-java-format-$FORMAT_VERSION/google-java-format-$FORMAT_VERSION-all-deps.jar
fi

# execute formatter

changed_java_files=$(git diff --cached --name-only --diff-filter=ACMR | grep ".*java$" || true)
if [[ -n "$changed_java_files" ]]
then
    echo "Reformatting Java files: $changed_java_files"
    if ! java -jar "$FORMAT_JAR" --replace --set-exit-if-changed $changed_java_files
    then
        echo "Some files were changed, aborting commit!" >&2
        exit 1
    fi
else
    echo "No Java files changes found."
fi