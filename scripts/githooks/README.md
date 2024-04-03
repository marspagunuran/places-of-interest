# Git Pre-Commit Hook

A script that formats java code according to the [Google Java Style Guide]https://github.com/google/google-java-format).

## Installing

To use this pre-commit script, pls clone this repo locally, and then link the `pre-commit.sh` script from this repo as the `.git/hooks/pre-commit` script in your java project:

```bash

[dxiong@localhost githooks]$ pwd
/home/dxiong/Workspace/Console/mw/consoleconnect-java-sdk/consoleconnect-java-sdk-parent/githooks
[dxiong@localhost githooks]$ cd ~/Workspace/Console/mw/consoleconnect-java-sdk/consoleconnect-java-sdk-workflow-layer3/
[dxiong@localhost consoleconnect-java-sdk-workflow-layer3]$ ln -s /home/dxiong/Workspace/Console/mw/consoleconnect-java-sdk/consoleconnect-java-sdk-parent/githooks/pre-commit.sh .git/hooks/pre-commit
[dxiong@localhost consoleconnect-java-sdk-workflow-layer3]$

```

## Details

The script automatically downloads the google-java-format jar from the [Google Java Format](https://github.com/google/google-java-format) project to your `~/Downloads` directory if the jar is not there. It will automatically format the java files on git commit.


To skip formatting a single commit, use the `--no-verify` flag (`-n` for short):
```bash
git commit --no-verify
```
