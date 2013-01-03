--compound.core:\
SELECT ID, ACC, NAME, DESCRIPTION, INCHI, TEMP_ID, IUPAC_NAMES, FORMULA \
FROM REF_METABOLITE \
WHERE {0}
	
--where.compound.by.name:\
NAME = ?

--where.compound.by.accession:\
ACC = ?

--where.compound.by.id:\
ID = ?

--where.compound.all:\
1=1


--update.compound:\
UPDATE REF_METABOLITE \
	SET ACC = ?, NAME = ?, DESCRIPTION = ?, INCHI = ?, TEMP_ID = ?, IUPAC_NAMES = ?, FORMULA = ? WHERE ID = ?
	
--insert.compound:\
INSERT INTO REF_METABOLITE \
	(ACC, NAME, DESCRIPTION, INCHI, TEMP_ID, IUPAC_NAMES, FORMULA) \
	VALUES (?,?,?,?,?,?,?)
	
--delete.compound:\
DELETE FROM REF_METABOLITE \
WHERE {0}

--exist.compound:\
SELECT COUNT(*) FROM REF_METABOLITE WHERE {0}
	