#!/bin/bash

mvn clean install -DskipTests -q
mvn exec:java -q
mvn test -q
