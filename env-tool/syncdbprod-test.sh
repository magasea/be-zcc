#!/bin/bash

mongodump --host 10.20.100.238 --port 27017 --db wszcc --out /home/chenwei/tmp/backup/mongodump-wszcc-`date +%Y-%m-%d`
mongodump --host 10.20.100.238 --port 27017 --db wszcc_log --out /home/chenwei/tmp/backup/mongodump-wszcc-log-`date +%Y-%m-%d`
mongorestore --host 10.20.100.235 --port 27017 --drop /home/chenwei/tmp/backup/mongodump-wszcc-`date +%Y-%m-%d`
mongorestore --host 10.20.100.235 --port 27017 --drop /home/chenwei/tmp/backup/mongodump-wszcc-log-`date +%Y-%m-%d`
echo '#!/bin/bash' > ./commands.sh
echo 'cd /home/chenwei/tmp' >> ./commands.sh
echo 'mysqldump -u root -pWensheng@12345678 -B ZCC_AMC > ./zccamc-`date +%Y-%m-%d`.sql' >> ./commands.sh
echo 'mysqldump -u root -pWensheng@12345678 -B ZCC_CUST > ./zcccust-`date +%Y-%m-%d`.sql' >> ./commands.sh
echo 'mysqldump -u root -pWensheng@12345678 -B ZCC_SSO > ./zccsso-`date +%Y-%m-%d`.sql' >> ./commands.sh
ssh chenwei@10.20.100.238 '[ ! -d /home/chenwei/tmp ] && mkdir -p /home/chenwei/tmp'
scp ./commands.sh chenwei@10.20.100.238:/home/chenwei/tmp/commands.sh
ssh chenwei@10.20.100.238 'chmod 755 /home/chenwei/tmp/commands.sh && /home/chenwei/tmp/commands.sh'
[ ! -d /home/chenwei/tmp/mysqlback ] && mkdir -p /home/chenwei/tmp/mysqlback
scp chenwei@10.20.100.238:/home/chenwei/tmp/*.sql /home/chenwei/tmp/mysqlback/
ssh chenwei@10.20.100.70 '[ ! -d /home/chenwei/tmp/mysqlback ] && mkdir -p /home/chenwei/tmp/mysqlback'
scp /home/chenwei/tmp/mysqlback/*-`date +%Y-%m-%d`.sql chenwei@10.20.100.70:/home/chenwei/tmp/mysqlback/
ssh chenwei@10.20.100.70 "cd /home/chenwei/tmp/mysqlback/ && mysql -u root -pwensheng < ./zccamc-`date +%Y-%m-%d`.sql"
ssh chenwei@10.20.100.70 "cd /home/chenwei/tmp/mysqlback/ && mysql -u root -pwensheng < ./zcccust-`date +%Y-%m-%d`.sql"
ssh chenwei@10.20.100.70 "cd /home/chenwei/tmp/mysqlback/ && mysql -u root -pwensheng < ./zccsso-`date +%Y-%m-%d`.sql"
