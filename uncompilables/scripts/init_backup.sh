#!/bin/bash
#Postgres backup moved to mainenance script
/net/isilonP/public/rw/homes/tc_cm01/metabolights/software/bin/bsub -u metabolights-dev /nfs/www-prod/web_hx2/cm/metabolights/scripts/backup_postgres.sh
/net/isilonP/public/rw/homes/tc_cm01/metabolights/software/bin/bsub -u metabolights-dev /nfs/www-prod/web_hx2/cm/metabolights/scripts/backup_elastic.sh
