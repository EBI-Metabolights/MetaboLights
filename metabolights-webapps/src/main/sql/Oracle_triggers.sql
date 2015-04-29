--------------------------------------------------------
--  DDL for Sequence DB_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ISATAB"."DB_SEQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 261 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence HIBERNATE_SEQUENCE
--------------------------------------------------------

   CREATE SEQUENCE  "ISATAB"."HIBERNATE_SEQUENCE"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence ML_STATS_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ISATAB"."ML_STATS_SEQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 11834 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence REF_ATTRIBUTE_DEF_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ISATAB"."REF_ATTRIBUTE_DEF_SEQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 60 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence REF_ATTRIBUTE_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ISATAB"."REF_ATTRIBUTE_SEQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 92320 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence REF_MET_PATHWAYS_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ISATAB"."REF_MET_PATHWAYS_SEQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 2309 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence REF_MET_SPECTRA_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ISATAB"."REF_MET_SPECTRA_SEQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 13302 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence REF_MET_TO_SPECIES_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ISATAB"."REF_MET_TO_SPECIES_SEQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 60120 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence REF_METABOLITE_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ISATAB"."REF_METABOLITE_SEQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1205040 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence REF_SPECIES_GROUP_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ISATAB"."REF_SPECIES_GROUP_SEQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 150 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence REF_SPECIES_MEMBERS_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ISATAB"."REF_SPECIES_MEMBERS_SEQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 150 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence REF_SPECIES_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ISATAB"."REF_SPECIES_SEQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 5540 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence REF_XREF_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ISATAB"."REF_XREF_SEQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 2261 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence REF_XREF_SEQ1
--------------------------------------------------------

   CREATE SEQUENCE  "ISATAB"."REF_XREF_SEQ1"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 16500 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence USER_DETAIL_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ISATAB"."USER_DETAIL_SEQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1350 CACHE 20 NOORDER  NOCYCLE ;




--------------------------------------------------------
--  DDL for Trigger DB_SEQ_TRIGGER
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "ISATAB"."DB_SEQ_TRIGGER" 
before insert or update on "ISATAB"."REF_DB"    
for each row 
begin
  if inserting then
    if :NEW."ID" is null then
      select DB_SEQ.nextval into :NEW."ID" from dual;
    end if;
  end if;
end;
/
ALTER TRIGGER "ISATAB"."DB_SEQ_TRIGGER" ENABLE;
--------------------------------------------------------
--  DDL for Trigger ML_STATS_TRG
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "ISATAB"."ML_STATS_TRG" before
  INSERT ON ML_STATS FOR EACH row
  BEGIN
    IF inserting THEN
      IF :NEW.ID IS NULL THEN
        SELECT ML_STATS_SEQ.nextval INTO :NEW.ID FROM dual;
      END IF;
  END IF;
END;
/
ALTER TRIGGER "ISATAB"."ML_STATS_TRG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger REF_ATTRIBUTE_DEF_TRG
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "ISATAB"."REF_ATTRIBUTE_DEF_TRG" 
   before insert on ISATAB.REF_ATTRIBUTE_DEF 
   for each row 
begin  
   if inserting then 
      if :NEW.ID is null then 
         select REF_ATTRIBUTE_DEF_SEQ.nextval into :NEW.ID from dual; 
      end if; 
   end if; 
end;
/
ALTER TRIGGER "ISATAB"."REF_ATTRIBUTE_DEF_TRG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger REF_ATTRIBUTE_TRG
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "ISATAB"."REF_ATTRIBUTE_TRG" 
   before insert on ISATAB.REF_ATTRIBUTE 
   for each row 
begin  
   if inserting then 
      if :NEW.ID is null then 
         select REF_ATTRIBUTE_SEQ.nextval into :NEW.ID from dual; 
      end if; 
   end if; 
end;
/
ALTER TRIGGER "ISATAB"."REF_ATTRIBUTE_TRG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger REF_MET_PATHWAYS_TRG
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "ISATAB"."REF_MET_PATHWAYS_TRG" 
   before insert on ISATAB.REF_MET_PATHWAYS 
   for each row 
begin  
   if inserting then 
      if :NEW.ID is null then 
         select REF_MET_PATHWAYS_SEQ.nextval into :NEW.ID from dual; 
      end if; 
   end if; 
end;
/
ALTER TRIGGER "ISATAB"."REF_MET_PATHWAYS_TRG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger REF_MET_SPECTRA_SEQ_TR
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "ISATAB"."REF_MET_SPECTRA_SEQ_TR" before insert on "ISATAB"."REF_MET_SPECTRA"    for each row begin     if inserting then       if :NEW."ID" is null then          select REF_MET_SPECTRA_SEQ.nextval into :NEW."ID" from dual;       end if;    end if; end;
/
ALTER TRIGGER "ISATAB"."REF_MET_SPECTRA_SEQ_TR" ENABLE;
--------------------------------------------------------
--  DDL for Trigger REF_MET_TO_SPECIES_HACK_PK
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "ISATAB"."REF_MET_TO_SPECIES_HACK_PK" 
   before insert on "ISATAB"."REF_MET_TO_SPECIES" 
   for each row 
begin  
   if inserting then    
         select REF_MET_TO_SPECIES_SEQ.nextval into :NEW."ID" from dual; 
   end if; 
end;
/
ALTER TRIGGER "ISATAB"."REF_MET_TO_SPECIES_HACK_PK" ENABLE;
--------------------------------------------------------
--  DDL for Trigger REF_MET_TO_SPECIES_PK
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "ISATAB"."REF_MET_TO_SPECIES_PK" 
   before insert on "ISATAB"."REF_MET_TO_SPECIES" 
   for each row 
begin  
   if inserting then 
      if :NEW."ID" is null then 
         select REF_MET_TO_SPECIES_SEQ.nextval into :NEW."ID" from dual; 
      end if; 
   end if; 
end;
/
ALTER TRIGGER "ISATAB"."REF_MET_TO_SPECIES_PK" ENABLE;
--------------------------------------------------------
--  DDL for Trigger REF_METABOLITE_TRIGGER
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "ISATAB"."REF_METABOLITE_TRIGGER" 
before insert or update on ISATAB.REF_METABOLITE
for each row
DECLARE
  new_id NUMBER(10);
BEGIN
  if inserting then
    if :NEW.ID is null then
      select REF_METABOLITE_SEQ.nextval into new_id from dual;
      :NEW.id := new_id;
    end if;
    if :NEW.ACC is null then
      :NEW.acc := 'MTBLC'||new_id;
    end if;

  end if;
end;
/
ALTER TRIGGER "ISATAB"."REF_METABOLITE_TRIGGER" ENABLE;
--------------------------------------------------------
--  DDL for Trigger REF_SPECIES_GROUP_TRG
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "ISATAB"."REF_SPECIES_GROUP_TRG" 
   before insert on "ISATAB"."REF_SPECIES_GROUP" 
   for each row 
begin  
   if inserting then 
      if :NEW."ID" is null then 
         select REF_SPECIES_GROUP_SEQ.nextval into :NEW."ID" from dual; 
      end if; 
   end if; 
end;
/
ALTER TRIGGER "ISATAB"."REF_SPECIES_GROUP_TRG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger REF_SPECIES_MEMBERS_TRG
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "ISATAB"."REF_SPECIES_MEMBERS_TRG" 
   before insert on "ISATAB"."REF_SPECIES_MEMBERS" 
   for each row 
begin  
   if inserting then 
      if :NEW."ID" is null then 
         select REF_SPECIES_MEMBERS_SEQ.nextval into :NEW."ID" from dual; 
      end if; 
   end if; 
end;
/
ALTER TRIGGER "ISATAB"."REF_SPECIES_MEMBERS_TRG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger REF_SPECIES_TRG
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "ISATAB"."REF_SPECIES_TRG" 
   before insert on "ISATAB"."REF_SPECIES" 
   for each row 
begin  
   if inserting then 
      if :NEW."ID" is null then 
         select REF_SPECIES_SEQ.nextval into :NEW."ID" from dual; 
      end if; 
   end if; 
end;
/
ALTER TRIGGER "ISATAB"."REF_SPECIES_TRG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger REF_XREF_TRG
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "ISATAB"."REF_XREF_TRG" BEFORE INSERT ON REF_XREF 
FOR EACH ROW 
BEGIN
  <<COLUMN_SEQUENCES>>
  BEGIN
    IF :NEW.ID IS NULL THEN
      SELECT REF_XREF_SEQ1.NEXTVAL INTO :NEW.ID FROM DUAL;
    END IF;
  END COLUMN_SEQUENCES;
END;
/
ALTER TRIGGER "ISATAB"."REF_XREF_TRG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger STUDY_BACKUP_TRG
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "ISATAB"."STUDY_BACKUP_TRG" 
BEFORE DELETE OR UPDATE OF ACC,OBFUSCATIONCODE,RELEASEDATE,STATUS,SUBMISSIONDATE,SUBMISSION_TS ON STUDY
for each row
BEGIN
  insert into study_backup(ACC,ID,OBFUSCATIONCODE,RELEASEDATE,STATUS,SUBMISSIONDATE,SUBMISSION_TS)
  values(:old.ACC,:old.ID,:old.OBFUSCATIONCODE,:old.RELEASEDATE,:old.STATUS,:old.SUBMISSIONDATE,:old.SUBMISSION_TS);
    
  if (updating) 
  then
    update studies set status = decode(:new.status,0,3,1), releasedate = :new.releasedate where acc = :new.acc;
  end if;
  
    EXCEPTION
    WHEN OTHERS THEN null;
  
END;
/
ALTER TRIGGER "ISATAB"."STUDY_BACKUP_TRG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger STUDY_TRG
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "ISATAB"."STUDY_TRG" 
AFTER INSERT ON study
FOR EACH ROW
BEGIN
  insert into studies (id, acc, OBFUSCATIONCODE, RELEASEDATE, STATUS)
  values(:new.id, :new.acc, :new.OBFUSCATIONCODE, :new.releasedate, :new.status);
  EXCEPTION
    WHEN OTHERS THEN null;
END;
/
ALTER TRIGGER "ISATAB"."STUDY_TRG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger STUDY2USER_TRG
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "ISATAB"."STUDY2USER_TRG" 
AFTER INSERT ON study2user
FOR EACH ROW
BEGIN
  insert into study_user(userid, studyid)
  values(:new.user_id, :new.study_id);
EXCEPTION
    WHEN OTHERS THEN null;
END;
/
ALTER TRIGGER "ISATAB"."STUDY2USER_TRG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger USER_DETAIL_TRG
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "ISATAB"."USER_DETAIL_TRG" 
AFTER INSERT ON USER_DETAIL
FOR EACH ROW
BEGIN
  insert into users (id, address, affiliation, affiliationurl, apitoken, email, firstname, joindate, lastname, password, role, status, username)
  values(:new.id, :new.address, :new.affiliation, :new.affiliation_url, :new.api_token, :new.email, :new.firstname, :new.joindate, :new.lastname, :new.password, 0, 2, :new.username);
END;
/
ALTER TRIGGER "ISATAB"."USER_DETAIL_TRG" ENABLE;
