

--speciesmembers.core:\
SELECT id, group_id, parent_member_id, taxon, taxon_desc \
FROM ref_species_members \
WHERE {0}

--where.speciesmembers.by.id:\
id = ?

--where.speciesmembers.by.taxon:\
taxon = ?

--where.speciesmembers.by.groupid:\
group_id = ?

--where.species.by.species:\
RS.SPECIES = ?

--where.speciesmembers.all:\
1 = 1

--update.speciesmembers:\
UPDATE ref_species_members \
SET group_id = ?, parent_member_id = ?, taxon = ?, taxon_desc = ? \
WHERE ID = ?

--insert.speciesmembers:\
INSERT INTO ref_species_members \
	(SPECIES, DESCRIPTION, TAXON) \
	VALUES (?, ?, ?)

--delete.speciesmembers:\
DELETE FROM ref_species_members \
WHERE ID = ?



