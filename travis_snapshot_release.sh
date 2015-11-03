#!/bin/sh
set -ev

BRANCH=${TRAVIS_BRANCH}

if [ "$BRANCH" = "develop" ] || [ "$BRANCH" = "release" ]
then
    python addServer.py
    mvn clean deploy -pl 'mvvmfx,mvvmfx-cdi,mvvmfx-guice,mvvmfx-archetype' -am -DskipTests=true --settings ~/.m2/mySettings.xml
fi