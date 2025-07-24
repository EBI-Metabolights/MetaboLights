<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified:  12/06/25 2:25 PM
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
    <h3>Data Submission Updates</h3><br>
    <div class="well nbr">
        <p>Building on the initial improvements introduced in January 2025 to enhance the submission process, MetaboLights has implemented the following changes on June 12:</p>
        <br>

        <h4 class="modal-title">New Submission Workflow</h4>
        <p>The previous 4 stages in the study submission process have now been simplified to a more streamlined 3 stage process:  </p>
        <br>

        <!-- First change: two-column image layout -->
        <div class="row">
            <div class="col-sm-6">
                <img src="${pageContext.request.contextPath}/img/OLD WORKFLOW.png" class="img-responsive" alt="Submission Workflow Step 1" />
            </div>
            <div class="col-sm-6">
                <img src="${pageContext.request.contextPath}/img/NEW WORKFLOW.png" class="img-responsive" alt="Submission Workflow Step 2" />
            </div>
        </div>

        <br>

        <p>Main changes in the study status labels:</p>
    <br>
        <!-- Second change: 3-column, 5-row table -->
        <table class="table table-bordered table-striped">
            <thead>
            <tr>
                <th>PREVIOUS Status</th>
                <th>NEW Status</th>
                <th>Description of Updates</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>Submitted</td>
                <td>Provisional</td>
                <td>New status label</td>
            </tr>
            <tr>
                <td>In Curation</td>
                <td>Private</td>
                <td>New status label<br/>No MetaboLights curation needed<br/>Submitters can revert to Provisional</td>
            </tr>
            <tr>
                <td>In Review</td>
                <td>Private</td>
                <td>New status label</td>
            </tr>
            <tr>
                <td>Public</td>
                <td>Public</td>
                <td>Submitters can change to Public without contacting us<br/>Only selected studies will be manually curated</td>
            </tr>
            </tbody>
        </table>

        <br>
        <p>Going forward, Curation Status in MetaboLights will be as follows: </p>
        <br>

        <!-- Third change: centred image -->
        <div class="text-center">
            <img src="${pageContext.request.contextPath}/img/Curation Status with border.png" class="img-responsive center-block" alt="Curation Status Diagram" />
        </div>
        <br>
        <p><b>MetaboLights curation will be retained in certain cases.</b></p>
        <br>
        <h4>'In Curation' Studies</h4>
        <p>Current studies with the status ‘In Curation’ will now have the status <b>’PRIVATE’</b> under the new workflow. </p>
        <br>
        <p>For those submitters who wish to make their studies <b>’PUBLIC’</b>, please:</p>
        <br>

        <!-- Fourth change: numbered list -->
        <ol style="padding-left: 20px; margin-left: 0;">
            <li>Change status to ’PROVISIONAL’ (drop down menu in the status bar at the top of your study).</li>
            <li>Run new Study Validations and address errors, until validation is successful.</li>
            <li>Change status to ’PRIVATE’ (i.e. keep private until publication is out) or ’PUBLIC’ (for immediate release).</li>
        </ol>

    </div>
    <div class="well nbr">
        <p>January 2025 marks the release of two new MetaboLights functionalities, which are the first of several to be introduced. These have been implemented to improve the handling of the volume and quality of submissions without MetaboLights curation.</p>
        <br>
        <h4 class="modal-title">New Accessioning</h4>
        <p>The previous iteration of the MetaboLights accessioning issued a MetaboLights accession number (i.e. MTBLSxxx) upon study creation. This would occasionally result in accessions being misused on incomplete submissions, some being cited in published manuscripts.</p>
       <br>
        <p>The new accessioning will first issue a temporary submission request (i.e. REQxxx). This identifier is internal, temporary, and follows the rough format REQ{datetime}, for example, REQ20241203125485. A full MetaboLights study accession number will be assigned only once a study passes Validation Framework v2, and status promoted to ‘Private’, to ensure a dataset is complete.</p>
        <br>
        <p>This will affect new submissions only.</p>
        <br>
        <h4 class="modal-title">Validation Framework v2</h4>
        <p>The new study validation comprises a more structured, stricter ruleset and it is maintained on <a class="more" target="_blank" href="https://github.com/EBI-Metabolights/mtbls-validation">public repository</a>. The reason for this new validation is to improve reporting and speed up data release. The volume of submissions to MetaboLights has increased significantly in the last few years, and curating each study as a precondition for release is no longer sustainable. This version allows validation online as well as locally. It is accompanied by a redesigned user interface in the Online Editor, and includes the capability to browse and download previous validation reports.</p>
        <br>
        <p>Studies which pass this version of validation and have status promoted to Private will instantly receive a private reviewer link and be eligible to go public without MetaboLights curation. </p>
        <br>
        <p>New submissions and current studies in ‘Provisional’ status - even if they were created while validation framework v1 was in use - are now required to pass validation framework v2 to promote the status from 'Provisional' to ‘Private. Current studies with the status ‘Private’ will be assessed for release without MetaboLights curation or contacted for further information. Studies with the status ‘Public’ will be unaffected. MetaboLights curation will be retained in certain cases.</p>
    </div>

    <p><a href="mailto:metabolights-help@ebi.ac.uk?subject=New%20Study%20Release%20Pipeline">Give us feedback about this new mechanism</a></p>
    <br>
</div>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>