#!/bin/tcsh
bsub -u metabolights-dev /nfs/production/panda/metabolights/source/metabolights/metabolights-webapps/src/main/scripts/tidy.sh
bsub -u metabolights-dev /nfs/production/panda/metabolights/source/metabolights/metabolights-webapps/src/main/scripts/maintainPublicV2.sh
bsub -u metabolights-dev /nfs/public/rw/homes/tc_cm01/metabolights/scripts/ebeye_export.sh
# Make sure access rights are set on the public ftp site
#bsub -u kenneth chmod -R go+rx /ebi/ftp/pub/databases/metabolights/studies/public/*
