<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

    <%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 8/21/14 9:46 AM
  ~ Modified by:   conesa
  ~
  ~
  ~ ©, EMBL, European Bioinformatics Institute, 2014.
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~    http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
  --%>

    <%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 5/2/13 2:34 PM
  ~ Modified by:   conesa
  ~
  ~
  ~ ©, EMBL, European Bioinformatics Institute, 2014.
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~    http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
  --%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/cssrl/iconfont/font_style.css" type="text/css"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/metabolights.css" type="text/css"/>

<div class="container-fluid">
    <div class="col-md-12">
        <h3 class="heading"><spring:message code="msg.importmetabolites.title"/></h3>
        <div class="well">
            <p>This page allows you to import metabolites from ChEBI into MetaboLights. The ChEBI Ontology tree is traversed starting from the give node (usually the HAS_ROLE:Metabolite-CHEBI:25212). It will check for a HAS_ROLE flag and traverses all the IS_A, IS_TAUTOMER_OF, IS_CONJUGATE_ACID_OF and IS_CONJUGATE_BASE_OF entries of given node.</p>
        </div>
        <div class="col-md-4">
            <form action="importmetabolitesrun">
                <div class="form-group">
                    <label>CHEBI Class (Metabolite - CHEBI:25212)</label>
                    <input name="chebiId" class="form-control" value="CHEBI:25212">
                </div>
                <div class="form-group">
                    <label><input name="updateSpecies" CHECKED type="checkbox" value="true"> Update species information for existing metabolites?</label>
                </div>
                <input class="btn btn-primary" type="submit">
            </form>
        </div>
    </div>
</div>