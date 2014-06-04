create or replace and compile
java source named "RandomUUID"
as
public class RandomUUID
{
   public static String create()
   {
            return java.util.UUID.randomUUID().toString();
    }
}
/

CREATE OR REPLACE FUNCTION RandomUUID
RETURN VARCHAR2
AS LANGUAGE JAVA
NAME 'RandomUUID.create() return java.lang.String';
/
;

alter table user_detail add api_token VARCHAR2(512);
update user_detail set api_token = randomUUID() where api_token is null;

alter table user_detail modify (api_token varchar2(512) not null);
alter table user_detail add constraint api_token_uq unique("API_TOKEN");
