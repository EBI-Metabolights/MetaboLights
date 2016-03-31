#!/bin/tcsh
bsub -u metabolights-dev /nfs/www-prod/web_hx2/cm/metabolights/scripts/tidy.sh
bsub -q production-rh6 -u metabolights-dev /nfs/www-prod/web_hx2/cm/metabolights/scripts/maintainPublicV3.sh
bsub -q production-rh6 -u metabolights-dev -R "rusage[mem=32000]" -M 32000 -n 8 /nfs/www-prod/web_hx2/cm/metabolights/scripts/export.sh
bsub -u metabolights-curation /nfs/www-prod/web_hx2/cm/metabolights/scripts/email_curators.sh
