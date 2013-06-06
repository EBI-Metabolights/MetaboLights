
delete from REF_METABOLITE where ACC like '%ACC%';


--select * from ref_xref where DB_ID > 4

delete from ref_xref where ACC = 'Random ACC';

delete from ref_db where db_name like 'Random%';

delete from ref_species where SPECIES like 'Random%';

delete from attribute_def where name like 'Random Ad%';