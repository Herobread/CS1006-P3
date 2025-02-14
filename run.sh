#!/usr/bin/env bash

./build.sh

case "$(uname -sr)" in
    CYGWIN*|MINGW*|MINGW32*|MSYS*)
        SEPARATOR=";"
        ;;
    *)
    SEPARATOR=":"
    ;;
esac

java -cp "lib/*${SEPARATOR}dist" rgou.Main

# Taken from
# https://github.com/p2js/sta-java-template/blob/main/run.sh