@ECHO OFF
setLocal EnableDelayedExpansion

echo.

if "%JRE_HOME%" == "" goto :use_jdk
	set _JAVA_HOME="%JRE_HOME%"
	goto :set_classpath

:use_jdk
set _JAVA_HOME=%JAVA_HOME%


:set_classpath
set _CLASSPATH=%CLASSPATH%

set _JAVA=%_JAVA_HOME%\bin\java

set APP_PATH="%~dp0.."
set APP_LIBPATH=%APP_PATH%\lib
set APP_MAIN_CLASS=consumer.master.ConsumerMaster



set APP_CLASSPATH=''
for /R ../lib %%a in (*.jar) do (
    set APP_CLASSPATH=!APP_CLASSPATH!;%%a
)




@ECHO ON

%_JAVA% -Duser.dir=%APP_PATH% -classpath %APP_CLASSPATH%;'%_CLASSPATH%' %APP_MAIN_CLASS% %*



pause