#!/bin/bash

clear && mvn clean compile package && cd ./target && java -cp com.jakespringer.lostexhaust.LeWebserver -jar lost-exhaust-0.2.1-SNAPSHOT.jar
