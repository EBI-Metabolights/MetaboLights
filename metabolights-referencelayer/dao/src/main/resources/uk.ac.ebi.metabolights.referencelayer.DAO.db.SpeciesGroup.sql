
--speciesgroup.core:\
SELECT name, id FROM ref_species_group WHERE {0}

--where.speciesgroup.by.id:\
id = ?

--where.speciesgroup.by.name:\
name = ?

--where.speciesgroup.all:\
1 = 1

--update.speciesgroup:\
UPDATE ref_species_group SET name = ? WHERE ID = ?

--delete.speciesgroup:\
DELETE FROM ref_species_group WHERE id = ?



