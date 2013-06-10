--attributedefinition.core:\
SELECT ID, NAME, DESCRIPTION \
FROM REF_ATTRIBUTE_DEF \
WHERE {0}

--where.attributedefinition.by.id:\
ID = ?

--where.attributedefinition.all:\
1 = ?


--update.attributedefinition:\
UPDATE REF_ATTRIBUTE_DEF \
	SET NAME = ?, DESCRIPTION = ? WHERE ID = ?
	
--insert.attributedefinition:\
INSERT INTO REF_ATTRIBUTE_DEF \
	(NAME, DESCRIPTION) \
	VALUES (?, ?)

--delete.attributedefinition:\
DELETE FROM REF_ATTRIBUTE_DEF \
WHERE {0}