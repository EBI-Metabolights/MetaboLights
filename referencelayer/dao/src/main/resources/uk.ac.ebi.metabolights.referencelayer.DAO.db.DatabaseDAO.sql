--database.core:\
SELECT ID, DB_NAME \
FROM REF_DB \
WHERE {0}

--where.database.by.id:\
ID = ?

--where.database.all:\
1 = ?


--update.database:\
UPDATE REF_DB \
	SET DB_NAME = ? WHERE ID = ?
	
--insert.database:\
INSERT INTO REF_DB \
	(DB_NAME) \
	VALUES (?)

--delete.database:\
DELETE FROM REF_DB \
WHERE {0}



