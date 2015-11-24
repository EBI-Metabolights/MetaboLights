#!/bin/tcsh
#bsub -u metabolights-dev /nfs/public/rw/homes/tc_cm01/metabolights/scripts/tidy.sh
bsub -q production-rh6 -u metabolights-dev /nfs/public/rw/homes/tc_cm01/metabolights/scripts/maintainPublicV3.sh
bsub -q production-rh6 -u metabolights-dev -R "rusage[mem=32000]" -M 32000 -n 8 /nfs/public/rw/homes/tc_cm01/metabolights/scripts/export.sh
bsub -u metabolights-curation /nfs/public/rw/homes/tc_cm01/metabolights/scripts/email_curators.sh
