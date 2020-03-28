#!/bin/bash
cd `dirname $0`
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`
CONF_DIR=$DEPLOY_DIR/conf

SERVER_NAME=rongsell

if [ -z "$SERVER_NAME" ]; then
    SERVER_NAME=`hostname`
fi

APP_PID=`cat $BIN_DIR/profile.pid`
if [ -n "$APP_PID" ]; then
    echo "INFO: The $SERVER_NAME already started!"
    echo "PID: $APP_PID"
    exit 0
fi

LOGS_DIR=~/logs/$SERVER_NAME
if [ ! -d "$LOGS_DIR" ]; then
    mkdir -p "$LOGS_DIR"
fi
STDOUT_FILE=$LOGS_DIR/stdout.log

LIB_DIR=$DEPLOY_DIR/lib
LIB_JARS=`ls $LIB_DIR|grep .jar|awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`

JAVA_OPTS="-DappName=$SERVER_NAME -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true -Ddubbo.shutdown.hook=true "
JAVA_DEBUG_OPTS=""
if [ "$1" = "debug" ]; then
    JAVA_DEBUG_OPTS=" -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n "
fi
JAVA_JMX_OPTS=""
if [ "$1" = "jmx" ]; then
    JAVA_JMX_OPTS=" -Dcom.sun.management.jmxremote.port=1099 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false "
fi
JAVA_MEM_OPTS=""
BITS=`java -version 2>&1 | grep -i 64-bit`
if [ -n "$BITS" ]; then
    JAVA_MEM_OPTS=" -server -Xmx512m -Xms512m -Xmn512m -XX:PermSize=128m -XX:MaxPermSize=256M -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "
else
    JAVA_MEM_OPTS=" -server -Xms1g -Xmx1g -XX:PermSize=256m -XX:MaxPermSize=512M -XX:SurvivorRatio=2 -XX:+UseParallelGC "
fi

echo -e "Starting the $SERVER_NAME ..."
nohup java $JAVA_OPTS $JAVA_MEM_OPTS $JAVA_DEBUG_OPTS $JAVA_JMX_OPTS $JAVA_PROPERTIES_OPTS -Dfile.encoding=UTF8 -classpath $CONF_DIR:$LIB_JARS org.springframework.boot.loader.JarLauncher > $STDOUT_FILE 2>&1 &
sleep 1
echo $! > $BIN_DIR/profile.pid
APP_PID=`cat $BIN_DIR/profile.pid`

if [ -z "$APP_PID" ]; then
    echo "START APP FAIL!"
    echo "STDOUT: $STDOUT_FILE"
    exit 1
fi

echo "${SERVER_NAME} start success"
