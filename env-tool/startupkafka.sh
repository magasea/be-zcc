#!/bin/bash
cd /home/chenwei/workingTools/
./apache-zookeeper-3.5.6-bin/bin/zkServer.sh start
cd kafka_2.11-2.4.0
./bin/kafka-server-start.sh -daemon config/server.properties 

