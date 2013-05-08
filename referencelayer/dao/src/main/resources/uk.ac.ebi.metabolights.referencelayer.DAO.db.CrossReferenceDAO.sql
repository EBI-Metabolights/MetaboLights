

--crossreference.core:\
SELECT ID, ACC, DB_ID \
FROM REF_XREF \
WHERE {0}

--where.crossreference.by.id:\
ID = ?


--update.crossreference:\
UPDATE REF_XREF \
	SET ACC = ?, DB_ID = ? WHERE ID = ?
	
--insert.crossreference:\
INSERT INTO REF_XREF \
	(ACC, DB_ID) \
	VALUES (?, ?)

--delete.crossreference:\
DELETE FROM REF_XREF \
WHERE ID = ?



