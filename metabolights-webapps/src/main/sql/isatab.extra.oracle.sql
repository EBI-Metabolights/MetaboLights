-- Changes to the ISATAB schema in order to make MTBLight thingies work

-- User_Detail related changes
ALTER TABLE USER_DETAIL ADD status VARCHAR2(10) DEFAULT 'NEW' NOT NULL
/
ALTER TABLE USER_DETAIL ADD affiliation_url VARCHAR2(4000) 
/
ALTER TABLE USER_DETAIL ADD CONSTRAINT CHK_USER_STATUS CHECK (status in ('NEW','USER_VERIFIED', 'ACTIVE','FROZEN'))
/
ALTER TABLE USER_DETAIL ADD CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
/
CREATE SEQUENCE user_detail_seq START WITH 100
/ 

-- Stable identifier helper table (additional)
CREATE TABLE stable_id (
  ID        NUMBER(6) PRIMARY KEY
 ,PREFIX    VARCHAR2(10) NOT NULL 
 ,UPDATED   DATE NOT NULL 
 ,SEQ       NUMBER(10) NOT NULL 
)
/
INSERT INTO stable_id VALUES (1,'MTBLS',sysdate,1)
/
COMMIT
/

ALTER TABLE study ADD(updated_date DATE DEFAULT sysdate); --For new/replaced submissions AND "Make Public"
