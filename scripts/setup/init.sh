#!/bin/sh
if [ -z "$1" ]; then
    echo "./init_project.sh project-key"
    exit 1
fi

mvn -N io.takari:maven:wrapper

#ignore hidden files
#find . -type f -not -path '*/\.*' -exec sed -i 's/PROJECT_NAME/'${1}'/g' {} +

#search all files
find . -type f  -not -path 'scripts/setup/*' -not -path './.git/*' -exec sed -i 's/PROJECT_NAME/'${1}'/g' {} +