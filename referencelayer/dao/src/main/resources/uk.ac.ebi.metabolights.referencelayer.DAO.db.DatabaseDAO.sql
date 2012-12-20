--database.core:\
SELECT ID, DB_NAME \
FROM DB \
WHERE {0}

--where.database.by.id:\
ID = ?

--where.database.all:\
1 = ?


--update.database:\
UPDATE DB \
	SET DB_NAME = ? WHERE ID = ?
	
--insert.database:\
INSERT INTO DB \
	(DB_NAME) \
	VALUES (?)

--delete.database:\
DELETE FROM DB \
WHERE {0}



