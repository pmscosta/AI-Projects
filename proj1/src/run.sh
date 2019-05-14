#!/bin/sh

rm -rf *.class

javac Solver/*.java Model/*.java Model/Block/*.java

java Solver.KlotskiSolver