#!/bin/bash
# part 1
echo '#!/bin/bash' > ./mongodump.sh
echo '[ ! -d /home/chenwei/tmp/mongoback  ] && mkdir -p /home/chenwei/tmp/mongoback' >> ./mongodump.sh
echo 'mongodump --host 10.20.100.238 --port 27017 --db wszcc --out /home/chenwei/tmp/mongoback/mongodump-wszcc-`date +%Y-%m-%d`' >> ./mongodump.sh
echo 'mongodump --host 10.20.100.238 --port 27017 --db wszcc_log --out /home/chenwei/tmp/mongoback/mongodump-wszcc-log-`date +%Y-%m-%d`' >> ./mongodump.sh
echo 'mongodump --host 10.20.100.238 --port 27017 --db wszcc_cust --out /home/chenwei/tmp/mongoback/mongodump-wszcc-cust-`date +%Y-%m-%d`' >> ./mongodump.sh
echo 'mongodump --host 10.20.100.238 --port 27017 --db wszcc_wechat --out /home/chenwei/tmp/mongoback/mongodump-wszcc-wechat-`date +%Y-%m-%d`' >> ./mongodump.sh
echo 'cd /home/chenwei/tmp/mongoback/' >> ./mongodump.sh
echo 'tar zcvf mongodump-`date +%Y-%m-%d`.tar.gz *-`date +%Y-%m-%d`' >> ./mongodump.sh

ssh chenwei@10.20.100.238 '[ ! -d /home/chenwei/tmp ] && mkdir -p /home/chenwei/tmp'
scp ./mongodump.sh chenwei@10.20.100.238:/home/chenwei/tmp/mongodump.sh
ssh chenwei@10.20.100.238 'chmod 755 /home/chenwei/tmp/mongodump.sh && /home/chenwei/tmp/mongodump.sh'
[ ! -d /home/chenwei/tmp/mongoback ] && mkdir -p /home/chenwei/tmp/mongoback
scp chenwei@10.20.100.238:/home/chenwei/tmp/mongoback/*-`date +%Y-%m-%d`.tar.gz /home/chenwei/tmp/mongoback/
# mongodump --host 10.20.100.238 --port 27017 --db wszcc --out /home/chenwei/tmp/backup/mongodump-wszcc-`date +%Y-%m-%d`
# mongodump --host 10.20.100.238 --port 27017 --db wszcc_log --out /home/chenwei/tmp/backup/mongodump-wszcc-log-`date +%Y-%m-%d`
cd /home/chenwei/tmp/mongoback && tar xzvf *-`date +%Y-%m-%d`.tar.gz
mongorestore --host 10.20.100.235 --port 27017 --drop /home/chenwei/tmp/mongoback/mongodump-wszcc-`date +%Y-%m-%d`
mongorestore --host 10.20.100.235 --port 27017 --drop /home/chenwei/tmp/mongoback/mongodump-wszcc-log-`date +%Y-%m-%d`
mongorestore --host 10.20.100.235 --port 27017 --drop /home/chenwei/tmp/mongoback/mongodump-wszcc-cust-`date +%Y-%m-%d`
mongorestore --host 10.20.100.235 --port 27017 --drop /home/chenwei/tmp/mongoback/mongodump-wszcc-wechat-`date +%Y-%m-%d`
echo '#!/bin/bash' > ./commands.sh
echo 'cd /home/chenwei/tmp' >> ./commands.sh
echo 'mysqldump --compatible=ansi -u root -pWsamc@12345 -B ZCC_AMC > ./zccamc-`date +%Y-%m-%d`.sql' >> ./commands.sh
echo 'mysqldump --compatible=ansi -u root -pWsamc@12345 -B ZCC_CUST > ./zcccust-`date +%Y-%m-%d`.sql' >> ./commands.sh
echo 'mysqldump --compatible=ansi -u root -pWsamc@12345 -B ZCC_SSO > ./zccsso-`date +%Y-%m-%d`.sql' >> ./commands.sh
echo 'mysqldump --compatible=ansi -u root -pWsamc@12345 -B ZCC_WECHAT > ./zccwechat-`date +%Y-%m-%d`.sql' >> ./commands.sh
ssh chenwei@10.20.100.238 '[ ! -d /home/chenwei/tmp ] && mkdir -p /home/chenwei/tmp'
scp ./commands.sh chenwei@10.20.100.238:/home/chenwei/tmp/commands.sh
ssh chenwei@10.20.100.238 'chmod 755 /home/chenwei/tmp/commands.sh && /home/chenwei/tmp/commands.sh'
[ ! -d /home/chenwei/tmp/mysqlback ] && mkdir -p /home/chenwei/tmp/mysqlback
scp chenwei@10.20.100.238:/home/chenwei/tmp/*.sql /home/chenwei/tmp/mysqlback/
ssh chenwei@10.20.100.235 '[ ! -d /home/chenwei/tmp/mysqlback ] && mkdir -p /home/chenwei/tmp/mysqlback'
scp /home/chenwei/tmp/mysqlback/*-`date +%Y-%m-%d`.sql chenwei@10.20.100.235:/home/chenwei/tmp/mysqlback/
ssh chenwei@10.20.100.235 "cd /home/chenwei/tmp/mysqlback/ && mysql -u root -pWensheng@123 < ./zccamc-`date +%Y-%m-%d`.sql"
ssh chenwei@10.20.100.235 "cd /home/chenwei/tmp/mysqlback/ && mysql -u root -pWensheng@123 < ./zcccust-`date +%Y-%m-%d`.sql"
ssh chenwei@10.20.100.235 "cd /home/chenwei/tmp/mysqlback/ && mysql -u root -pWensheng@123 < ./zccsso-`date +%Y-%m-%d`.sql"
ssh chenwei@10.20.100.235 "cd /home/chenwei/tmp/mysqlback/ && mysql -u root -pWensheng@123 < ./zccwechat-`date +%Y-%m-%d`.sql"
