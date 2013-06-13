--pathway.core:\
SELECT ID, PATHWAY_DB_ID, MET_ID, NAME, PATH_TO_PATHWAY_FILE \
FROM REF_MET_PATHWAYS \
WHERE {0}

--where.pathway.by.id:\
ID = ?

--where.pathway.by.metid:\
MET_ID = ?


--update.pathway:\
UPDATE REF_MET_PATHWAYS \
	SET MET_ID = ?, PATHWAY_DB_ID = ?, NAME = ?, PATH_TO_PATHWAY_FILE = ? WHERE ID = ?
	
--insert.pathway:\
INSERT INTO REF_MET_PATHWAYS \
	(MET_ID, PATHWAY_DB_ID, NAME, PATH_TO_PATHWAY_FILE) \
	VALUES (?, ?, ?, ?)

--delete.pathway:\
DELETE FROM REF_MET_PATHWAYS \
WHERE {0}


