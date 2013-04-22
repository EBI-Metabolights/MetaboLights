

--metspecies.core:\
SELECT ID, SPECIES_ID, DB_ID \
FROM REF_MET_TO_SPECIES \
WHERE {0}

--where.metspecies.by.id:\
ID = ?

--where.metspecies.by.metid:\
MET_ID = ?


--update.metspecies:\
UPDATE REF_MET_TO_SPECIES \
	SET MET_ID = ?, SPECIES_ID = ?, DB_ID = ? WHERE ID = ?
	
--insert.metspecies:\
INSERT INTO REF_MET_TO_SPECIES \
	(MET_ID, SPECIES_ID, DB_ID) \
	VALUES (?, ?, ?)

--delete.metspecies:\
DELETE FROM REF_MET_TO_SPECIES \
WHERE {0}


