#!/bin/bash

# compile
javac JavaProgram.java

if [ $? -ne 0 ]; then
  echo "Compilation Failed"
  exit 1
fi

# run
java JavaProgram