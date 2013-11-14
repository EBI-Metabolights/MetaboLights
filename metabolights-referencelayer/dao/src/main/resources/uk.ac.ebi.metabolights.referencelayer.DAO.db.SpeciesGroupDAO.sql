--speciesgroup.core:\
SELECT ID, NAME \
FROM REF_SPECIES_GROUP \
WHERE {0}

--where.speciesgroup.by.name:\
NAME = ?

--where.speciesgroup.by.id:\
ID = ?

--where.speciesgroup.all:\
1 = ?

--update.speciesgroup:\
UPDATE REF_SPECIES_GROUP \
	SET NAME = ? WHERE ID = ?
	
--insert.speciesgroup:\
INSERT INTO REF_SPECIES_GROUP \
	(NAME) \
	VALUES (?)

--delete.speciesgroup:\
DELETE FROM REF_SPECIES_GROUP \
WHERE {0}



