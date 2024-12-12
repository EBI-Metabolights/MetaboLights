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
    <h3>Data Submission Updates 2025</h3><br>
    <div class="well nbr">
        <p>January 15th 2025 marks the release of two new MetaboLights functionalities, which are the first of several to be introduced. These have been implemented to improve the handling of the volume and quality of submissions without MetaboLights curation.</p>
        <br>
        <h4 class="modal-title">New Accessioning</h4>
        <p>The previous iteration of the MetaboLights accessioning issued a MetaboLights accession number (i.e. MTBLSxxx) upon study creation. This would occasionally result in accessions being misused on incomplete submissions, some being cited in published manuscripts.</p>
       <br>
        <p>The new accessioning will first issue a temporary submission request (i.e. REQxxx). This identifier is internal, temporary, and follows the rough format REQ{datetime}, for example, REQ20241203125485. A full MetaboLights study accession number will be assigned only once a study passes Validation Framework v2, and status promoted to ‘In Curation’, to ensure a dataset is complete.</p>
        <br>
        <p>This will affect new submissions only.</p>
        <br>
        <h4 class="modal-title">Validation Framework v2</h4>
        <p>The new study validation comprises a more structured, stricter ruleset (https://github.com/EBI-Metabolights/mtbls-validation). The reason for this new validation is to improve reporting and speed up data release. The volume of submissions to MetaboLights has increased significantly in the last few years, and curating each study as a precondition for release is no longer sustainable. This version allows validation online as well as locally. It is accompanied by a redesigned user interface in the Online Editor, and includes the capability to browse and download previous validation reports.</p>
        <br>
        <p>Studies which pass this version of validation and have status promoted to ‘In Curation’ will instantly receive a private reviewer link and be eligible to go public without MetaboLights curation. </p>
        <br>
        <p>New submissions and current studies in ‘Submitted’ status - even if they were created while validation framework v1 was in use - are now required to pass validation framework v2 to promote the status from ‘Submitted’ to ‘In Curation’. Current studies with the status ‘In Curation’ will be assessed for release without MetaboLights curation or contacted for further information. Studies with the status ‘In Review’ or ‘Public’ will be unaffected. MetaboLights curation will be retained in certain cases.</p>
    </div>
    <p><a href="mailto:metabolights-help@ebi.ac.uk?subject=New%20Study%20Release%20Pipeline">Give us feedback about this new mechanism</a>
    </p>
    <br>

</div>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>