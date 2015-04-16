#!/bin/tcsh
bsub -u metabolights-dev /nfs/public/rw/homes/tc_cm01/metabolights/scripts/tidy.sh
bsub -u metabolights-dev /nfs/public/rw/homes/tc_cm01/metabolights/scripts/maintainPublicV2.sh
bsub -u metabolights-dev /nfs/public/rw/homes/tc_cm01/metabolights/scripts/ebeye_export.sh
bsub -u metabolights-curators /nfs/public/rw/homes/tc_cm01/metabolights/scripts/email_curators.sh
# Make sure access rights are set on the public ftp site
#bsub -u kenneth chmod -R go+rx /ebi/ftp/pub/databases/metabolights/studies/public/*
