PATH=/net/isilonP/public/rw/homes/tc_cm01/metabolights/software/bin:$PATH
#bsub -u metabolights-dev /nfs/www-prod/web_hx2/cm/metabolights/scripts/tidy.sh

bsub -u metabolights-dev /net/isilonP/public/rw/homes/tc_cm01/metabolights/scripts/tidy.sh
bsub -u metabolights-dev /net/isilonP/public/rw/homes/tc_cm01/metabolights/scripts/metexplore.sh
bsub -u metabolights-dev /net/isilonP/public/rw/homes/tc_cm01/metabolights/scripts/maintainPublicV3_postgres.sh
bsub -u metabolights-dev /net/isilonP/public/rw/homes/tc_cm01/metabolights/scripts/unichem_postgres.sh
bsub -u metabolights-dev -R "rusage[mem=32000]" -M 32000 -n 8 /net/isilonP/public/rw/homes/tc_cm01/metabolights/scripts/export.sh
bsub -u metabolights-curation /net/isilonP/public/rw/homes/tc_cm01/metabolights/scripts/email_curators.sh
bsub -u metabolights-curation /net/isilonP/public/rw/homes/tc_cm01/metabolights/scripts/validate_studies.sh
