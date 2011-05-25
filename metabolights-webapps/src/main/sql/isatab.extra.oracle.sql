-- changes to the ISATAB schema in order to make MTBLight thingies work



-- User status column to prevent new account requests becoming active immediately
ALTER TABLE USER_DETAIL ADD status VARCHAR2(10) DEFAULT 'NEW' NOT NULL
/
ALTER TABLE USER_DETAIL ADD CONSTRAINT CHK_USER_STATUS CHECK (status in ('NEW','ACTIVE','FROZEN'))
/
ALTER TABLE USER_DETAIL ADD CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
/

-- a sequence
CREATE SEQUENCE user_detail_seq START WITH 100
/ 
