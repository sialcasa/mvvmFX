#!/bin/sh

# Script for the deployment of releases to maven central
#
# Expecting a "settings-mvvmfx.xml" file in maven_home with the authentication configuration for the central repository.
#
# The settings file should look something like this:
#
# <settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
#   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
#   xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
#   http://maven.apache.org/xsd/settings-1.0.0.xsd">
#
#   <servers>
#     <server>
#       <id>sonatype-nexus-snapshots</id>
#       <username>YOUR USERNAME</username>
#       <password>YOUR PASSWORD</password>
#     </server>
#     <server>
#       <id>sonatype-nexus-staging</id>
#       <username>YOUR USERNAME</username>
#       <password>YOUR PASSWORD</password>
#     </server>
#   </servers>
# </settings>

mvn clean deploy -pl 'mvvmfx,mvvmfx-cdi,mvvmfx-guice,mvvmfx-easydi,mvvmfx-archetype,mvvmfx-utils,mvvmfx-testing-utils,mvvmfx-validation' -am -DskipTests=true -Pdeploy-release --settings ~/.m2/settings-mvvmfx.xml
