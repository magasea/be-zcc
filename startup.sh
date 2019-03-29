#!/bin/bash
#export JAVA_HOME=/home/sunht/workingTools/jdk1.8.0_191
#export JAVA_HOME=/home/sunht/workingTools/jdk-12
export JAVA_HOME=/home/sunht/workingTools/jdk-10.0.2
export PATH=${JAVA_HOME}/bin:${PATH}
export LANG=en_US.utf8
export JAVA_OPTS="-Dsun.jnu.encoding=UTF-8 -Dfile.encoding=UTF-8"
execJarName_amc=/home/sunht/working/zcc/zcc-amc-0.1-SNAPSHOT.jar
execJarName_sso=/home/sunht/working/zcc/zcc-sso-0.1-SNAPSHOT.jar
pattern_amc=zcc-amc
pattern_sso=zcc-sso
killProcess() {
    echo "$1"
    result=`ps -ef | grep "$1" | grep -v grep | awk '{print $2}'| wc -l`
    echo "now thre is $result process like:$1 are running"
    if [ $result -gt 0 ]; then
        echo "to kill process with pattern:$1 and pid:$result"
        kill $(ps -ef | grep "$1" | grep -v grep | awk '{print $2}')
    fi
}

killProcess ${pattern_amc}
killProcess ${pattern_sso}
echo ${execJarName_amc}
echo ${execJarName_sso}
sleep 5
nohup java -jar -Dspring.profiles.active=test ${execJarName_amc} &>amc.log &
nohup java -jar -Dspring.profiles.active=test ${execJarName_sso} &>sso.log &
