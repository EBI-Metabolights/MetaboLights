-- Tidy species in studies
update property_value set value = 'Bos taurus (Bovine)' where value = 'Bos taurus';
update property_value set value = 'Arabidopsis thaliana (thale cress)' where value = 'Arabidopsis thaliana';
update property_value set value = 'Homo sapiens (Human)' where value = 'Homo sapiens';
update property_value set value = 'Hordeum vulgare var. distichum (Two-rowed barley)' where value = 'Hordeum vulgare subsp. vulgare';
update property_value set value = 'Solanum lycopersicum (Tomato) ' where value = 'Solanum lycopersicum';
update property_value set value = 'Malus domestica (Apple)' where value = 'Malus x domestica';
update property_value set value = 'Saccharomyces cerevisiae (Baker''s yeast)' where value = 'Saccharomyces cerevisiae';
update property_value set value = 'Schizosaccharomyces pombe 972h- (Fission yeast)' where value = 'Schizosaccharomyces pombe 972h-';
update property_value set value = 'Sorghum bicolor (Sorghum)' where value = 'Sorghum bicolor';
update property_value set value = 'Triticum aestivum (Wheat)' where value = 'Triticum aestivum';
commit;