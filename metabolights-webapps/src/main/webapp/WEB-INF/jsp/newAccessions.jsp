<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 05/30/14 2:25 PM
  ~ Modified by:   cmartin
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
<div class="col-md-12">
    <p class="row">
    <h3>New Study Release Mechanism</h3><br>
    <div class="well nbr">
        January 15th 2025 marks the release of two new versions of Metabolights functionality. These have been implemented to handle the volume of submissions and to improve the quality of submissions without manual intervention.
        <h4>New Accessioning System</h4>
        <p>
            The first iteration of the MetaboLights accessioning system issued an MTBLS accession upon study creation. This would occasionally result in accessions being wasted on incomplete studies, or journal articles referencing a given MTBLS accession that was not yet public / had not been approved by the curation team.
            Version 2 instead issues you a request ID. This identifier is temporary, and follows the rough format REQ{datetime} e.g. REQ20241203125485. This identifier is temporary, and a full metabolights accession will be given once a study passes version 2 of our validation system. This enables us to only issue accessions once a submission is complete, and moves us in line with other EBI resources. Existing studies that are not yet public will be unaffected.
        </p>
        <h4>Validation Framework V2</h4>
        <p>
            The new study validation system comprises a more structured, stricter ruleset. If a study passes this new validation system, it can then be promoted to {NON CURATED} without the need for manual curation. The reason for this new system is to help get datasets public more quickly, while retaining manual curation for some in certain cases. The volume of submissions to MetaboLights has increased tremendously in the last few years, and manually curating each study as a precondition for release was no longer feasible. The new validation system uses a policy engine to validate studies - it is possible to validate locally. The new validation system is accompanied by a redesigned user interface, and includes the capability to browse and download previous validation reports.
            New studies and studies in submission - even if they were created while v1 validation was in use - are now required to pass this validation system as a minimum threshold for public release. Studies that are In Curation, In Review or Public are unaffected.
        </p>


    </div>
    <p><a href="mailto:example@example.com?subject=New%20Study%20Release%20Pipeline">Give us feedback about this new mechanism</a>
    </p>
    <br>

</div>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>