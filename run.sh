#!/bin/bash

rm *.class
#javac PlantDHKey.java && javac DiffieHellmanExchange.java && javac Karn.java && javac MessageParser.java && javac ActiveClient.java && javac Server.java && javac Homework.java && java PlantDHKey && java Homework helios.ececs.uc.edu 8180 johne
javac PlantDHKey.java && javac DiffieHellmanExchange.java && javac Karn.java && javac MessageParser.java && javac ActiveClient.java && javac Server.java && javac Homework.java && java PlantDHKey && java Homework localhost 8180 johne
