#!/bin/bash
mvn clean compile -DskipTests package
scp zcc-sso/target/zcc-sso-0.1-SNAPSHOT.jar sunht@10.20.100.236:/home/sunht/working/zcc
scp zcc-amc/target/zcc-amc-0.1-SNAPSHOT.jar sunht@10.20.100.236:/home/sunht/working/zcc
scp zcc-sso/target/zcc-sso-0.1-SNAPSHOT.jar chenwei@10.20.100.238:/home/chenwei/working/zcc
scp zcc-amc/target/zcc-amc-0.1-SNAPSHOT.jar chenwei@10.20.100.238:/home/chenwei/working/zcc
