--compound.core:\
SELECT ID, ACC, NAME, DESCRIPTION, INCHI, TEMP_ID \
FROM REF_METABOLITE \
WHERE {0}
	
--where.compound.by.name:\
NAME = ?

--where.compound.by.accession:\
ACC = ?

--where.compound.by.id:\
ID = ?

--update.compound:\
UPDATE REF_METABOLITE \
	SET ACC = ?, NAME = ?, DESCRIPTION = ?, INCHI = ?, TEMP_ID = ? WHERE ID = ?
	
--insert.compound:\
INSERT INTO REF_METABOLITE \
	(ACC, NAME, DESCRIPTION, INCHI, TEMP_ID) \
	VALUES (?,?,?,?,?)
	
--delete.compound:\
DELETE REF_METABOLITE \
WHERE {0}
	