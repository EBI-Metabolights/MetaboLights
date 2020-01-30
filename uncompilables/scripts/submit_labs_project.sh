#!/bin/bash

params="$(getopt -o hv: -l help,token:,project:,zip:,location:,env: --name "$0" -- "$@")"
eval set -- "$params"

while [[ $# -gt 0 ]] ; do
    case $1 in
        -h|-\?|--help)
            echo "Help"
            ;;
        -t|--token)            
            if [ -n "$2" ]; then
		TOKEN=$2
                shift
            fi
            ;;
	-p|--project)  
            if [ -n "$2" ]; then
                PROJECT=$2
		shift
            fi
            ;;
	-z|--zip)  
            if [ -n "$2" ]; then
                ZIP=$2
                shift
            fi
            ;;
	-e|--env)
            if [ -n "$2" ]; then
                SERVER=$2
                shift
            fi
            ;;
	-l|--location)
            if [ -n "$2" ]; then
                LOCATION=$2
                shift
            fi
            ;;
    esac
    shift
done

if [ "$SERVER" == "prod" ]; then
	queue="/nfs/www-prod/web_hx2/cm/metabolights/prod/upload/queue/"
	location="/ebi/ftp/private/mtblight/prod/userSpace/$TOKEN/$PROJECT"
fi

if [ "$SERVER" == "dev" ]; then
        queue="/nfs/www-prod/web_hx2/cm/metabolights/dev/upload/queue/"
	location="/ebi/ftp/private/mtblight/dev/userSpace/$TOKEN/$PROJECT"
fi

if [ "$SERVER" == "test" ]; then
        queue="/nfs/www-prod/web_hx2/cm/metabolights/test/upload/queue/"
	location="/ebi/ftp/private/mtblight/dev/userSpace/$TOKEN/$PROJECT"
fi

cd $location
zip -r $ZIP .
pwd
mv $ZIP $queue

#if [ -z "$JOBID" ]; then
#	python /net/isilonP/public/rw/homes/tc_cm01/metabolights/scripts/MetaboLightsBots/MetStudyBot/mzml2isaBot.py -p $PROJECT -t $TOKEN -e $ENV
#else
#	python /net/isilonP/public/rw/homes/tc_cm01/metabolights/scripts/MetaboLightsBots/MetStudyBot/mzml2isaBot.py -p $PROJECT -t $TOKEN -e $ENV -j $JOBID
#fi
