#!/usr/bin/env bash

javac -cp "lib/*" $(find ./src/rgou/* | grep .java) -d ./dist/

# Taken from 
# https://github.com/p2js/sta-java-template/blob/main/build.sh