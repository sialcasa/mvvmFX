#!/bin/bash
set -ev

if ["${TRAVIS_BRANCH}" == "develop" || "${TRAVIS_BRANCH}" == "release" ]; then
    python addServer.py
    mvn clean deploy -pl 'mvvmfx,mvvmfx-cdi,mvvmfx-guice,mvvmfx-archetype' -am -DskipTests=true --settings ~/.m2/mySettings.xml
fi