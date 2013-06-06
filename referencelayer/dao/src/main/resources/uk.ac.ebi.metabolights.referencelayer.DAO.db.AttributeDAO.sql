

--attribute.core:\
SELECT ID, ATTRIBUTE_DEF_ID, SPECTRA_ID, VALUE \
FROM ATTRIBUTE \
WHERE {0}

--where.attribute.by.id:\
ID = ?

--where.attribute.by.spectraid:\
SPECTRA_ID = ?


--update.attribute:\
UPDATE ATTRIBUTE \
	SET ATTRIBUTE_DEF_ID = ?, SPECTRA_ID = ?, VALUE = ? WHERE ID = ?
	
--insert.attribute:\
INSERT INTO ATTRIBUTE \
	(ATTRIBUTE_DEF_ID, SPECTRA_ID, VALUE) \
	VALUES (?, ?, ?)

--delete.attribute:\
DELETE FROM ATTRIBUTE \
WHERE {0}


