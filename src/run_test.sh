#!/bin/sh

rm -rf *.class

javac Solver/*.java Model/*.java Model/Block/*.java


if [ -z "$1"]
    then
    java Solver.KlotskiTester
else
    java Solver.KlotskiTester "$1" "$2"
fi

