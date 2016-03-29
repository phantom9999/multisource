#!/usr/bin/env bash

echo

if [ "$JRE_HOME" != "" ]
then
    _JAVA_HOME="$JRE_HOME"
    echo Using  JRE_HOME : $_JAVA_HOME
else
    _JAVA_HOME="$JAVA_HOME"
    echo Using JAVA_HOME : $_JAVA_HOME
fi

_CLASSPATH="$CLASSPATH"
echo Using CLASSPATH : $_CLASSPATH

_JAVA=$_JAVA_HOME/bin/java
# 到达bin文件根目录
APP_PATH="$(cd "$(dirname "$0")" && pwd)/.."
APP_LIBPATH=$APP_PATH/lib
APP_CLASSPATH=`find $APP_LIBPATH -name *.jar|tr '\n' ':'`

APP_MAIN_CLASS=Main

ARGS=
DAEMON='false'
CMD="$_JAVA -Duser.dir=$APP_PATH -classpath $_CLASSPATH:$APP_CLASSPATH $APP_MAIN_CLASS"
# CMD="$_JAVA -Duser.dir=$APP_PATH -Djava.ext.dirs=$APP_LIBPATH -cp $APP_CLASSPATH:$_CLASSPATH $APP_MAIN_CLASS"
echo $CMD

echo $0

echo

while (( $# > 0 ))
do
    if [ "$1" == '-d' ]
    then
        DAEMON='true'
    else
        ARGS="$ARGS $1"
    fi

    shift
done

if [ "$DAEMON" == 'false' ]
then
    $CMD $ARGS
else
    $CMD $ARGS &
fi
