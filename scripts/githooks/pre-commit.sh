#!/bin/sh
format_cmd="$(dirname $(realpath "$0"))/format.sh"

if [ "$NO_VERIFY" ]; then
    echo 'google-java-format skipped' 1>&2
    exit 0
fi

git diff -z --staged --name-only --diff-filter=ACMR | grep -z '.java$' | xargs -0 -I % echo "$format_cmd -i '%'; git add -f '%'" | sh
