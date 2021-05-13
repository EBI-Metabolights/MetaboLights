#!/bin/bash

params="$(getopt -o hv: -l help,token:,project:,job:,env: --name "$0" -- "$@")"
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
	-j|--job)  
            if [ -n "$2" ]; then
                JOBID=$2
                shift
            fi
            ;;
	-e|--env)
            if [ -n "$2" ]; then
                ENV=$2
                shift
            fi
            ;;
    esac
    shift
done

source ~/metabolights/scripts/LabsENV/bin/activate

if [ -z "$JOBID" ]; then
	python /net/isilonP/public/rw/homes/tc_cm01/metabolights/scripts/MetaboLightsBots/MetStudyBot/mzml2isaBot.py -p $PROJECT -t $TOKEN -e $ENV
else
	python /net/isilonP/public/rw/homes/tc_cm01/metabolights/scripts/MetaboLightsBots/MetStudyBot/mzml2isaBot.py -p $PROJECT -t $TOKEN -e $ENV -j $JOBID
fi
