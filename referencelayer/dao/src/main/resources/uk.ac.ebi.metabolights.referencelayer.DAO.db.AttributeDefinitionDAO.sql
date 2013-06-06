--attributedefinition.core:\
SELECT ID, NAME, DESCRIPTION \
FROM ATTRIBUTE_DEF \
WHERE {0}

--where.attributedefinition.by.id:\
ID = ?

--where.attributedefinition.all:\
1 = ?


--update.attributedefinition:\
UPDATE ATTRIBUTE_DEF \
	SET NAME = ?, DESCRIPTION = ? WHERE ID = ?
	
--insert.attributedefinition:\
INSERT INTO ATTRIBUTE_DEF \
	(NAME, DESCRIPTION) \
	VALUES (?, ?)

--delete.attributedefinition:\
DELETE FROM ATTRIBUTE_DEF \
WHERE {0}