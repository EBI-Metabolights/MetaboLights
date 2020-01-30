#!/bin/tcsh
bsub -M 4096 -R "rusage[mem=4096]" -q production-rh74 -u metabolights-dev /nfs/www-prod/web_hx2/cm/metabolights/scripts/chebi_import/import_chebi.sh
