

--attribute.core:\
SELECT ID, ATTRIBUTE_DEF_ID, SPECTRA_ID, VALUE \
FROM REF_ATTRIBUTE \
WHERE {0}

--where.attribute.by.id:\
ID = ?

--where.attribute.by.spectraid:\
SPECTRA_ID = ?

--where.attribute.by.pathwayid:\
PATHWAY_ID = ?

--update.attribute:\
UPDATE REF_ATTRIBUTE \
	SET ATTRIBUTE_DEF_ID = ?, SPECTRA_ID = ?, PATHWAY_ID = ?, VALUE = ? WHERE ID = ?
	
--insert.attribute:\
INSERT INTO REF_ATTRIBUTE \
	(ATTRIBUTE_DEF_ID, SPECTRA_ID, PATHWAY_ID, VALUE) \
	VALUES (?, ?, ?, ?)

--delete.attribute:\
DELETE FROM REF_ATTRIBUTE \
WHERE {0}


