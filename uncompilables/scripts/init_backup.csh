#!/bin/tcsh
#bsub -u metabolights-dev /nfs/www-prod/web_hx2/cm/metabolights/scripts/backup_isa.csh
bsub -u metabolights-dev /nfs/www-prod/web_hx2/cm/metabolights/scripts/backup_postgres.sh
bsub -u metabolights-dev /nfs/www-prod/web_hx2/cm/metabolights/scripts/backup_elastic.csh
