#!/bin/bash
[ ! -d /home/chenwei/tmp/mongoback  ] && mkdir -p /home/chenwei/tmp/mongoback
mongodump --host 10.20.100.238 --port 27017 --db wszcc --out /home/chenwei/tmp/mongoback/mongodump-wszcc-`date +%Y-%m-%d`
mongodump --host 10.20.100.238 --port 27017 --db wszcc_log --out /home/chenwei/tmp/mongoback/mongodump-wszcc-log-`date +%Y-%m-%d`
cd /home/chenwei/tmp/mongoback/
tar zcvf mongodump-`date +%Y-%m-%d`.tar.gz *-`date +%Y-%m-%d`


