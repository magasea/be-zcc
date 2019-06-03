#!/bin/bash
cd /home/chenwei/tmp
mysqldump -u root -pWensheng@12345678 -B ZCC_AMC > ./zccamc-`date +%Y-%m-%d`.sql
mysqldump -u root -pWensheng@12345678 -B ZCC_CUST > ./zcccust-`date +%Y-%m-%d`.sql
mysqldump -u root -pWensheng@12345678 -B ZCC_SSO > ./zccsso-`date +%Y-%m-%d`.sql
