!#/bin/bash
export execJarName=/home/sunht/working/zcc/zcc-amc*.jar
killProcess() {
    if [[ -z $1  ]]; then
        echo "please input the process pattern to kill"
    fi
    result=`ps -ef | grep "$1" | grep -v grep | awk '{print $2}'|wc -l`
    echo "now thre is $result process like:$1 are running"
    if [ $result -gt 0 ]; then
        echo "to kill process with pattern:$1"
        ps -ef | grep $1 | grep -v grep | awk '{print $2}' | xargs kill -9
    fi
}

killProcess $1
nohup java -jar -Dspring.profiles.active=dev $execJarName &>zcc.log &
