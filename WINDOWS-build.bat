@ECHO off
javac -d bin src/*.java
cd bin
jar cfe ../build/OneOrTwo.jar MainGame *.class

