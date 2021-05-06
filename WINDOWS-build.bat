@ECHO off
javac -d bin src/*.java
cd bin
jar cfe ../build/Game.jar MainGame *.class

