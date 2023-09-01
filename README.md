# funicorn-logger-console
##页面操作日志管理平台
### 食用方式一
#### 下载源码自行编译并部署
1、创建数据库，执行/config目录下funicorn_logger.sql文件
2、修改application.yml数据库配置文件
3、启动 java -jar

### 食用方式二
#### 下载编译包，解压并部署
1、解压 
   --windows：用解压软件 
   --linux：tar -zxvf xxxx
1、创建数据库，执行config目录下funicorn_logger.sql文件
2、修改config目录下的application.yml配置文件
3、部署
   --windows：执行win-start.bat 
   --linux：./start.sh

###访问地址
http://ip:port

###console默认端口  
9900

###日志收集默认端口
17777  
配置 funicorn.logger.admin.port: 修改默认17777端口

###默认账号密码
admin/123456

