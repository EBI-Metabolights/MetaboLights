#!/bin/bash
S_FOLDER=/net/isilonP/public/rw/homes/tc_cm01/metabolights/prod/studies/stage/private
cd $S_FOLDER
for study in `ls -1`
do
  cd $S_FOLDER/${study}
  echo "Checking study ${study}"
  if [ -s metexplore_mapping.json ]
  then
       echo "metexplore_mapping.json file is not empty. Checking if we should delete incorrect file"
       find $S_FOLDER/${study} -name metexplore_mapping.json -type f -exec grep 'Erreur' {} \; -delete
       find $S_FOLDER/${study} -name metexplore_mapping.json -type f -exec grep 'HTTP/1.1' {} \; -delete
  else
       echo "Trying to generate new metexplore_mapping.json file for ${study}"
       if ls m_* 1> /dev/null 2>&1; then
           echo "MAF file(s) exist, trying to generate a new metexplore_mapping.json"
           curl -H "Accept: application/json" -H "Content-Type: application/json" -X GET "https://metexplore.toulouse.inra.fr/metExploreWebService/mapping/launchaspathways/inchi/1363?name=EBI_Phenomanal_Test&wsurl=https://www.ebi.ac.uk/metabolights/webservice/study/${study}/getMetabolitesInchi" > $S_FOLDER/${study}/metexplore_mapping.json
		 wc -l $S_FOLDER/${study}/metexplore_mapping.json
       else
           echo "No MAF files, so cannot call MetExplore"
      fi
  fi
done
