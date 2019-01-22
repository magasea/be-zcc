#!/bin/bash
export execJarName=/home/sunht/working/zcc/zcc-amc-0.1-SNAPSHOT.jar
export pattern=zcc
killProcess() {
    result=`ps -ef | grep "$pattern" | grep -v grep | awk '{print $2}'| wc -l`
    echo "now thre is $result process like:$1 are running"
    if [ $result -gt 0 ]; then
        echo "to kill process with pattern:$1 and pid:$result"
        kill $(ps -ef | grep $pattern | grep -v grep | awk '{print $2}')
    fi
}

killProcess $1
echo ${execJarName}
sleep 5
nohup java -jar -Dspring.profiles.active=dev ${execJarName} &>zcc.log &
