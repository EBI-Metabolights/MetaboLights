<%--
  ~ EBI MetaboLights - https://www.ebi.ac.uk/metabolights
  ~ Metabolomics team
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 2017-Nov-10
  ~ Modified by:   kalai
  ~
  ~ Copyright 2017 EMBL - European Bioinformatics Institute
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~      http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
  --%>

<%--
  Created by IntelliJ IDEA.
  User: kalai
  Date: 10/11/2017
  Time: 17:16
  To change this template use File | Settings | File Templates.
--%>

<div id="orcidclaimfeaturediv">
    <div class="thor_div_showIf_notSigned">
        <div class="panel panel-info" style="margin: -6px 0px -8px -1px;">
            <div class="panel-heading thor_div_showIf_datasetAlreadyClaimedList">
                You can <a href="#" class="thor_a_generate_signinLink">sign-in
                to
                ORCID</a> to claim your data
            </div>
            <div class="panel-body">
                <div class="row existingClaimants" style="padding-left: 1em;">
                </div>
                <c:if test="${userApiToken ne 'MetaboLights-anonymous'}">
                    <c:if test="${empty userOrcidID}">
                        <br>
                        <div class="row">
                            <div class="panel panel-warning">
                                <div class="panel-body"> You can <a
                                        href="${pageContext.request.contextPath}/myAccount"
                                        target="_blank">Update your MTBLS account</a> with ORCID
                                </div>
                            </div>
                        </div>
                    </c:if>
                </c:if>
            </div>
            <div class="panel-footer">
                <input type="checkbox" class="thor_checkbox_rememberMe_cookie">
                <a target="_blank" href="https://orcid.org/"><i>ORCID</i></a> <i> can Remember me on
                this
                computer </i>
            </div>
        </div>
    </div>
    <div class="thor_div_showIf_signedIn">
        <div class="panel panel-warning" style="margin: -6px 0px -8px -1px;">
            <div class="panel-heading thor_div_showIf_datasetAlreadyClaimedList">

                <div class="row">
                    You have signed in as <label
                        class="thor_label_show_userName"></label>
                </div>
                <div class="row thor_div_showIf_datasetNotClaimed">
                    You can <a href="#"
                               class="thor_a_generate_claimLink"><strong>claim ${study.studyIdentifier}</strong></a>
                    into your ORCID.
                </div>
                <div class="row small thor_div_showIf_datasetAlreadyClaimed">
                    You have claimed <strong>${study.studyIdentifier}</strong> into your ORCID.
                </div>

            </div>
            <div class="panel-body">
                <div class="row existingClaimants" style="padding-left: 1em;">

                </div>
                <c:if test="${userApiToken ne 'MetaboLights-anonymous'}">
                    <c:if test="${empty userOrcidID}">
                        <br>
                        <div class="row">
                            <div class="panel panel-warning">
                                <div class="panel-body"> You can <a
                                        href="${pageContext.request.contextPath}/myAccount"
                                        target="_blank">Update your MTBLS account</a> with ORCID
                                </div>
                            </div>

                        </div>
                    </c:if>
                </c:if>
            </div>
            <div class="panel-footer">
                <a href="#" class="thor_a_generate_logoutLink"><i>Logout from ORCID</i></a>
            </div>
        </div>
    </div>
</div>
