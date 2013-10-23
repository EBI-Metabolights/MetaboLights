

--metspectra.core:\
SELECT ID, NAME, PATH_TO_JSON, SPECTRA_TYPE \
FROM REF_MET_SPECTRA \
WHERE {0}

--where.metspectra.by.id:\
ID = ?

--where.metspectra.by.metid:\
MET_ID = ?


--update.metspectra:\
UPDATE REF_MET_SPECTRA \
	SET MET_ID = ?, NAME = ?, PATH_TO_JSON = ?, SPECTRA_TYPE = ? WHERE ID = ?
	
--insert.metspectra:\
INSERT INTO REF_MET_SPECTRA \
	(MET_ID, NAME, PATH_TO_JSON, SPECTRA_TYPE) \
	VALUES (?, ?, ?,?)

--delete.metspectra:\
DELETE FROM REF_MET_SPECTRA \
WHERE {0}


