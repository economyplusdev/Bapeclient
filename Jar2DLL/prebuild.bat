@echo off
echo [.]Prebuilding...
del classes.h
cd jar2classes
del classes.h
java -jar Jar2Classes.jar client.jar
move classes.h ../classes.h
cd..
echo [+]Prebuilding done.