#!/bin/bash
cd /home/chenwei/workingTools/
cd *zookeeper*
./bin/zkServer.sh start
cd ../kafka_*
./bin/kafka-server-start.sh -daemon config/server.properties 

