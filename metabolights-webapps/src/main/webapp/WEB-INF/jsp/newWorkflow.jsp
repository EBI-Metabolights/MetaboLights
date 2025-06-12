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

        <!-- Third change: centred image -->
        <div class="text-center">
            <img src="${pageContext.request.contextPath}/img/Curation Status.png" class="img-responsive center-block" alt="Curation Status Diagram" />
        </div>

        <p><b>MetaboLights curation will be retained in certain cases.</b></p>
        <h4>'In Curation' Studies</h4>
        <p>Current studies with the status ‘In Curation’ will now have the status <b>’PRIVATE’</b> under the new workflow. </p>
        <p>For those submitters who wish to make their studies <b>’PUBLIC’</b>, please:</p>

        <!-- Fourth change: numbered list -->
        <ol>
            <li>Change status to ’PROVISIONAL’ (drop down menu in the status bar at the top of your study).</li>
            <li>Run new Study Validations and address errors, until validation is successful.</li>
            <li>Change status to ’PRIVATE’ (i.e. keep private until publication is out) or ’PUBLIC’ (for immediate release).</li>
        </ol>

    </div>

    <p><a href="mailto:metabolights-help@ebi.ac.uk?subject=New%20Study%20Release%20Pipeline">Give us feedback about this new mechanism</a></p>
    <br>
</div>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>