@echo off

call mvn clean install -DskipTests -q
call mvn exec:java -q
call mvn test -q

