#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd)"
cd ${DIR}/..
mvn clean compile -DskipTests package
scp env-tool/startup.sh chenwei@10.20.100.235:/home/chenwei/working/zcc

#scp zcc-cust/target/zcc-cust-0.1-SNAPSHOT.jar chenwei@10.20.100.235:/home/chenwei/working/zcc
#scp zcc-sso/target/zcc-sso-0.1-SNAPSHOT.jar chenwei@10.20.100.235:/home/chenwei/working/zcc
#scp zcc-amc/target/zcc-amc-0.1-SNAPSHOT.jar chenwei@10.20.100.235:/home/chenwei/working/zcc
scp zcc-wechat/target/zcc-wechat-0.1-SNAPSHOT.jar chenwei@10.20.100.235:/home/chenwei/working/zcc
#scp zcc-log/target/zcc-log-0.1-SNAPSHOT.jar chenwei@10.20.100.235:/home/chenwei/working/zcc
#scp zcc-cust/target/zcc-cust-0.1-SNAPSHOT.jar chenwei@10.20.100.235:/home/chenwei/working/zcc
#scp zcc-comn-func/target/zcc-comn-func-0.1-SNAPSHOT.jar chenwei@10.20.100.235:/home/chenwei/working/zcc


ssh chenwei@10.20.100.235 "cd /home/chenwei/working/zcc && ./startup.sh wechat"

