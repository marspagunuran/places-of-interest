#!/bin/sh

git config --local core.editor "vim"

git config --local commit.template `pwd`/scripts/githooks/gitmessage.txt

ln -s `pwd`/scripts/githooks/pre-commit.sh .git/hooks/pre-commit
