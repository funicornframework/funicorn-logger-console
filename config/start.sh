#!/bin/bash


#Java项目名称
APP_NAME=ffunicorn-logger-console.jar

if [ "`ps -ef |grep $APP_NAME|grep -v grep |awk '{print $2}'`" ];then
 for pid in `ps -ef |grep $APP_NAME|grep -v grep |awk '{print $2}'`;do
   kill -9 $pid
 done
fi
java -Xms512M -Xmx512M -jar $APP_NAME --spring.config.location=config/application.yml 1>nohup.out 2>&1 &
