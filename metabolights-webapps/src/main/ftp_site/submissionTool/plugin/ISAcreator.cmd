
@ECHO OFF
ECHO ON
REM Set JAva virtual machine options
set JAVA_OPTS=-Dfile.encoding=utf-8 -Xmx1024m -Xms512m
echo Java options are %JAVA_OPTS% 

java %JAVA_OPTS% -jar ISAcreator-1.5.0.jar


