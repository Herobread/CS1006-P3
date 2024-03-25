#!/usr/bin/env bash

javac -cp "lib/*" $(find ./src/* | grep .java) -d ./dist/