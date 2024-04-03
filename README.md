# console-core-service-PROJECT_NAME
## CI/CD setup 
Replace SERVICE_SHORT_NAME in .github/workflow/cd.yaml to your real service short name
```
service-short-name: SERVICE_SHORT_NAME
```
## Setup Development Env

```
./scripts/init_dev.sh
```

## Test

```
./scripts/build.sh test
```

After test, code coverage report can be find in ./target/site/jacoco/index.html

## Package

```
./scripts/build.sh
```

## Run

```
./scripts/build.sh dev
```

## Release

A pull request will trigger the release pipeline when merged. If you don't want a pull request to trigger a release add the tag `no_release` to the pull request.

The release pipeline will increment the version in the pom.xml, commit it back to the repo and tag a release in Github. A container will be built afterwards and released to the Dev environment.
