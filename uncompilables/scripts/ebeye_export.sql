set pages 0 lines 10000 echo off verify off trimspool on
set serveroutput on size unlimited
spool &1
exec EBEYE_EXPORT(RELEASE_NUMBER => &2 );
spool off
exit
