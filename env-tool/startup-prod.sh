#!/bin/bash
#export JAVA_HOME=/home/chenwei/workingTools/jdk1.8.0_191
#export JAVA_HOME=/home/chenwei/workingTools/jdk-12
export JAVA_HOME=/home/chenwei/workingTools/jdk-10.0.2
export PATH=${JAVA_HOME}/bin:${PATH}
export LANG=en_US.utf8
export JAVA_OPTS="-Dsun.jnu.encoding=UTF-8 -Dfile.encoding=UTF-8"
execJarName_amc=/home/chenwei/working/zcc/zcc-amc-0.1-SNAPSHOT.jar
execJarName_sso=/home/chenwei/working/zcc/zcc-sso-0.1-SNAPSHOT.jar
execJarName_log=/home/chenwei/working/zcc/zcc-log-0.1-SNAPSHOT.jar
execJarName_cust=/home/chenwei/working/zcc/zcc-cust-0.1-SNAPSHOT.jar
execJarName_wechat=/home/chenwei/working/zcc/zcc-wechat-0.1-SNAPSHOT.jar
execJarName_comnfunc=/home/chenwei/working/zcc/zcc-comn-func-0.1-SNAPSHOT.jar
pattern_amc=zcc-amc
pattern_sso=zcc-sso
pattern_log=zcc-log
pattern_cust=zcc-cust
pattern_wechat=zcc-wechat
pattern_comnfunc=zcc-comn-func

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
killProcess ${pattern_log}
killProcess ${pattern_cust}
killProcess ${pattern_wechat}
killProcess ${pattern_comnfunc}

echo ${execJarName_amc}
echo ${execJarName_sso}
echo ${execJarName_log}
echo ${execJarName_cust}
echo ${execJarName_wechat}
echo ${execJarName_comnfunc}

sleep 5
nohup java -jar -Dspring.profiles.active=prod -XX:+UseG1GC -Xms512M -Xmx1G ${execJarName_amc} &>amc.log &
nohup java -jar -Dspring.profiles.active=prod -XX:+UseG1GC -Xms128M -Xmx512M ${execJarName_sso} &>sso.log &
nohup java -jar -Dspring.profiles.active=prod -XX:+UseG1GC -Xms128M -Xmx512M ${execJarName_log} &>loger.log &
nohup java -jar -Dspring.profiles.active=prod -XX:+UseG1GC -Xms512M -Xmx1G ${execJarName_cust} &>cust.log &
nohup java -jar -Dspring.profiles.active=prod -XX:+UseG1GC -Xms128M -Xmx512M ${execJarName_wechat} &>wechat.log &
nohup java -jar -Dspring.profiles.active=prod -XX:+UseG1GC -Xms128M -Xmx512M ${execJarName_comnfunc} &>comnfunc.log &

