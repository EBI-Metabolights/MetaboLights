/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 12/5/13 10:41 AM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.utils.sampletab;

public class SampleTabUriResolver {

    private enum URLS {
        //TODO, must change all these url's to use BioPortal, OLS or Miriam
        NCBI("http://www.ncbi.nlm.nih.gov/taxonomy"), NCBI_direct_url("http://www.ncbi.nlm.nih.gov/taxonomy/"),
        NCIt("http://ncit.nci.nih.gov/ncitbrowser/pages/home.jsf?version=13.06d"), NCIt_direct_url("http://ncit.nci.nih.gov/ncitbrowser/ConceptReport.jsp?dictionary=NCI%20Thesaurus&code="),
        DOID("http://www.berkeleybop.org/ontologies/doid/doid.obo"), DOID_direct_url("http://purl.obolibrary.org/obo/DOID_"),
         MDR("http://purl.bioontology.org/ontology/MEDDRA"), MDR_direct_url("http://bioportal.bioontology.org/ontologies/42280?p=terms&conceptid="),
        NEWT("http://www.ebi.ac.uk/newt"),
         EFO("http://www.ebi.ac.uk/efo"), EFO_direct_url("http://www.ebi.ac.uk/efo/"),
         BAO("http://bioassayontology.org/bao"),
         SBO("http://www.ebi.ac.uk/sbo/main"),
         BFO("http://www.ifomis.org/bfo"),
          UO("http://code.google.com/p/unit-ontology"),
          EO("http://www.obofoundry.org/cgi-bin/detail.cgi?id=envo"),
    SNOMEDCT("http://www.ihtsdo.org/snomed-ct"),
        FBcv("http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=FBcv");

        private final String url;

        private URLS(String toString) {
            this.url = toString;
        }

        public String toString(){
            return url;
        }

    }

    /**
     * Returns the URL for the given ontology name
     * @param name
     * @return
     */
    public String getURLByName(String name){

        //TODO, try OLS or BioPortal first, then resolve below
        String uri = null;

        if (name.equals(URLS.NCIt.name()))
            uri = URLS.NCIt.url;
        else if (name.equals(URLS.DOID.name()))
            uri = URLS.DOID.url;
        else if (name.equals(URLS.NCBI.name()) || name.equals("NCBITaxon"))   //Annoying that two different names are being used
            uri = URLS.NCBI.url;
        else if (name.equals(URLS.MDR.name()))
            uri = URLS.MDR.url;
        else if (name.equals(URLS.EFO.name()))
            uri = URLS.EFO.url;
        else if (name.equals(URLS.BAO.name()))
            uri = URLS.BAO.url;
        else if (name.equals(URLS.SBO.name()))
            uri = URLS.SBO.url;
        else if (name.equals(URLS.NEWT.name()))
            uri = URLS.NEWT.url;
        else if (name.equals(URLS.BFO.name()))
            uri = URLS.BFO.url;
        else if (name.equals(URLS.UO.name()))
            uri = URLS.UO.url;
        else if (name.equals(URLS.EO.name()))
            uri = URLS.EO.url;
        else if (name.equals(URLS.FBcv.name()))
            uri = URLS.FBcv.url;
        else if (name.equals(URLS.SNOMEDCT.name()))
            uri = URLS.SNOMEDCT.url;

        return uri;
    }


}
