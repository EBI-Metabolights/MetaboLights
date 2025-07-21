<%@taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"   %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page language="java" contentType="text/html; charset=UTF-8"%>

<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 3/21/14 2:54 PM
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

<%--<div class="container">--%>
    <%--<div class ="row">--%>
        <%--<div class="col-md-3 col">--%>
            <%--<h2><spring:message code="title.serviceName" /></h2>--%>
            <%--<p><spring:message code="msg.metabolightsAbout1" />--%>
                <%--&lt;%&ndash;&nbsp;<spring:message code="msg.metabolightsAbout" />&ndash;%&gt;--%>
            <%--</p>--%>
        <%--</div>--%>
        <%--<div class="col-md-5 col">--%>
            <%--<h2><spring:message code="title.download" /></h2>--%>
            <%--<p>--%>
                <%--<a class="icon icon-generic bigfont" data-icon="T" href="ftp://ftp.ebi.ac.uk/pub/databases/metabolights/submissionTool/ISAcreatorMetaboLights.zip"></a>--%>
                <%--<spring:message code="msg.metabolightsAbout12" />--%>
            <%--</p>--%>
            <%--<br/>&nbsp;--%>
            <%--<p>--%>
                <%--<a class="icon icon-functional bigfont" data-icon="A" href="ftp://ftp.ebi.ac.uk/pub/databases/metabolights/"></a>--%>
                <%--<spring:message code="msg.metabolightsAbout7" />--%>
            <%--</p>--%>
        <%--</div>--%>

        <%--<div class="col-md-4 col">--%>
            <%--<a class="twitter-timeline"  href="https://twitter.com/MetaboLights" data-widget-id="642268788906422272">Tweets by @MetaboLights</a>--%>
            <%--<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+"://platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>
<%--<br/>--%>

<%--<div class="container">--%>
    <%--&lt;%&ndash;<h2><spring:message code="title.submit"/> </h2>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<br/><br/><br/>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<div class='col-md-6'>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<a href="submittoqueue">&ndash;%&gt;--%>
            <%--&lt;%&ndash;<div class="bigbutton maincolorI">&ndash;%&gt;--%>
                <%--&lt;%&ndash;<span class="bigfont"><spring:message code="label.submitNewStudy"/></span><br/>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<span><spring:message code="label.submitNewStudySub"/></span>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</a>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<div class='col-md-6'>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<a href="mysubmissions?status=PRIVATE">&ndash;%&gt;--%>
            <%--&lt;%&ndash;<div class="bigbutton seccolorI">&ndash;%&gt;--%>

                <%--&lt;%&ndash;<span class="bigfont"><spring:message	code="label.updateOldStudy"/></span></br>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<span><spring:message code="label.updateOldStudySub"/></span>&ndash;%&gt;--%>
                <%--&lt;%&ndash;</br>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</a>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
    <%--&nbsp;--%>
<%--</div>--%>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default nbr">
            <div class="panel-body grey">
                <h3 class="metabolights-logo-orange" style="font-size: 1.6em;">Data Submission Updates II - June 2025 (NEW SUBMISSION WORKFLOW)</h3>
                <p>MetaboLights is committed to improving the data submission process for our users. In January 2025 the team introduced the <b>New Accessioning</b> and <b>Validation Framework v2</b>, aimed to improve the way we handle the volume and quality of submissions.</p>
                <br>
                <p>Starting June 12, the following new changes are now introduced.</p>
                <br>
                <p><b>New Submission Workflow:</b> Once a study passes Validation Framework v2, submitters will be required to change the status of their studies from ‘<b>PROVISIONAL</b>’ to ‘<b>PRIVATE</b>’ (previously from ‘Submitted’ to ‘In Curation’).</p>
                <br>
                <p>As a major update, submitters will be able to change the status of their studies from ‘<b>PRIVATE</b>’ to ‘<b>PUBLIC</b>’ without needing to contact MetaboLights or undergo MetaboLights curation. <a class="more" target="_blank" href="newWorkflow">For more information click here</a></p>
                <hr style="border-top: 1px solid #ccc; margin: 10px 0;">
                <h3 class="metabolights-logo-orange" style="font-size: 1.6em;">Data Submission Updates I - January 2025</h3>
                <p>Starting January 2025, MetaboLights has introduced two major updates:</p>
                <p><b>New Accessioning:</b> Submissions initially get a temporary ID (REQxxx). A full accession number (MTBLSxxx) is assigned only after passing the new Validation Framework v2 and reaching ‘Private’ status.</p>
                <p><b>Validation Framework v2:</b> This stricter system ensures only validated studies receive full accessioning and a reviewer link, allowing them to go public without further curation.</p>
                <p>
                    <a class="more" target="_blank" href="newAccessions">For more information click here</a>
                </p>
                <div>&nbsp;</div>

            </div>
        </div>

        <div class="panel panel-default nbr">
            <div class="panel-body grey">
                <h3 class="metabolights-logo">MetaboLights</h3>
                <p>MetaboLights is a database for Metabolomics experiments and derived information. The database is cross-species, cross-technique and covers metabolite structures and their reference spectra as well as their biological roles, locations and concentrations, and experimental data from metabolic experiments. MetaboLights is the recommended Metabolomics repository for a number of leading journals.
                    <br>
                    <small><a target="_blank" href="https://www.ebi.ac.uk/about/people/juan-vizcaino">More about us</a></small>
                </p>
                <br>
                <a class="more" target="_blank" href="https://www.ebi.ac.uk/training/online/course/metabolights-quick-tour-0">Quick tour</a>
                <div>&nbsp;</div>

            </div>
        </div>
        <div class="row col">
            <div class="col-md-6">
                <div class="panel nbr panel-primary">
                    <div class="panel-body text-center"><br>
                        <i class="fa fa-2x  text-primary fa-book"></i><br><br>
                        <h4 class="section-heading text-primary">Study</h4>

                    </div>
                    <div class="custom-footer text-center">
                        <ul>
                            <li class="vc vh50"><a href="studies"><b>Browse</b></a></li>
                            <li class="vc vh50"><a href="advancedsearch"><b>ORCID search</b></a></li>
                            <li class="vc vh50"><a target="_blank" href="https://metabolights-labs.org/"><b>MetaboLights Labs</b></a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="panel nbr panel-success">
                    <div class="panel-body text-center"><br>
                        <i class="fa fa-2x fa-database text-success"></i><br><br>
                        <h4 class="section-heading text-success">Compound Library</h4>

                    </div>
                    <div class="custom-footer text-center">
                        <ul>
                            <li class="vc vh50"><a href="compounds"><b>Compounds</b></a></li>
                            <li class="vc vh50"><a href="species"><b>Species</b></a></li>
                            <%--<li><a href="analysis"><b>Analysis</b></a></li>--%>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
<div class="col-md-12"> 
    <div class="row">
        <div class="vf-content">
                <h3>Training</h3>
                        <!-- 
                    This is from EMBL VF Tabs component. You may use Tabs functionality from a different framework.
                    The layout of the design: maximum of two training courses metadata, each wrapped in VF summary component which are placed inside VF Grid component to arrange them in clumns. The Grid component is inside the Tabs.
                    -->
                <div class="vf-tabs">
                        <ul class="vf-tabs__list" data-vf-js-tabs="" role="tabsList">
                        <li class="vf-tabs__item" id="vf-tabs__item-1" role="presentation">
                            <a class="vf-tabs__link is-active" role="tab" id="vf-tabs__head--1" data-tabs__item="vf-tabs__section--1" aria-selected="true">Live training</a>
                        </li>
                        <li class="vf-tabs__item" id="vf-tabs__item-2" role="presentation">
                            <a class="vf-tabs__link" role="tab" id="vf-tabs__head--2" data-tabs__item="vf-tabs__section--2" tabindex="-1">On-demand training</a>
                        </li>
                        </ul>
                </div>

                <div class="vf-tabs-content" data-vf-js-tabs-content="">
                    <!-- Live training tab content -->
                        <section class="vf-tabs__section" id="vf-tabs__section--1" role="tabpanel" tabindex="-1" aria-labelledby="vf-tabs__section--1">
                            <div class="vf-grid vf-grid__col-2">
                                <c:choose>
                                    <c:when test="${not empty livelist}">
                                         <c:forEach var="liveTraining" items="${livelist}">
                                             <div class="vf-summary vf-summary--event ng-scope" ng-repeat="item in resultsLive">
                                                <p class="vf-summary__date ng-binding"> ${liveTraining.type} </p>
                                                <!-- You may need to apply some workaround if the links are showing an 'unsafe' prefix in your application -->
                                                <h3 class="vf-summary__title"><a href="${liveTraining.link}" target="_blank" class="vf-summary__link ng-binding">${liveTraining.title} </a></h3>
                                                <div>
                                                    <!-- The description field may contain some html, so you may want to use an HTML parser to translate html tags. This example doesn't cover that. The description text  might be very long, so slicing it upto a certain length would be better. By default we slice it upto first 200 characaters-->
                                                    <div class="vf-summary__text ng-binding">
                                                   ${liveTraining.description}
                                                    </div>
                                                    <!-- The icons in this section are being rendered from EMBL VF Icon Font classes. You may have a different library of icons to use or not use them at all. -->
                                                    <div class="vf-summary__location"><div class="vf-u-margin__top--400"></div><span class="ng-binding">${liveTraining.status}</span> | <span class="ng-binding"><i class="icon icon-common icon-calendar-alt"></i>${liveTraining.date}</span><span class="ng-binding"> | <i class="icon icon-common icon-location"></i> ${liveTraining.venue}</span></div>
                                                </div>
                                             </div>
                                         </c:forEach>
                                    </c:when>
                                 </c:choose>
                                 <div class="vf-summary__text"><a href="https://www.ebi.ac.uk/training/services/metabolights" target="_blank">Click here</a> to see all the tranings.</div>
                            </div>
                        </section>

                        <section class="vf-tabs__section" id="vf-tabs__section--2" role="tabpanel" tabindex="-1" aria-labelledby="vf-tabs__section--2" hidden="">
                            <!-- On-demand training tab content -->
                            <div class="vf-grid vf-grid__col-2">
                                 <c:choose>
                                    <c:when test="${not empty odlist}">
                                         <c:forEach var="odTraining" items="${odlist}">
                                            <div class="vf-summary vf-summary--event ng-scope" ng-repeat="item in resultsOndemand">
                                                <p class="vf-summary__date ng-binding"> ${odTraining.type} </p>
                                                <h3 class="vf-summary__title"><a href="${odTraining.link}" target="_blank" class="vf-summary__link ng-binding">${odTraining.title} <c:if test="${odTraining.subTitle}">: ${odTraining.subTitle}</c:if> </a></h3>
                                                <div>
                                                    <div class="vf-summary__text ng-binding">
                                                     ${odTraining.description}
                                                    </div>
                                                </div>
                                            </div>
                                         </c:forEach>
                                    </c:when>
                                 </c:choose>
                                 <div class="vf-summary__text"><a href="https://www.ebi.ac.uk/training/services/metabolights" target="_blank">Click here</a> for to see all the tranings.</div>
                            </div>
                        </section>
                    </div>
                    
        </div>
    </div>
</div>     
<br/>
<br/>
<br/>
<div class="col-md-12">
    <div class="row col" style="border: 1px solid #d1d9d6; border-radius: 0px; padding:15px 0px;">
        <div id="support-journals" href="#publishers">
            <div class="col-md-12">
                <p><spring:message code="msg.metaboLightsAbout17"/></p><br>
                <div>
                    <div class="col-md-2 vc" style="padding: 0">
                        <a class="noLine" href="http://www.nature.com/sdata/policies/repositories"><img src="img/support-journals/scientificData.png" class="img-responsive" style="width: 100%; height: auto" alt="Nature Scientific Data"/></a>
                    </div>
                    <div class="col-md-2 vc" style="padding: 0">
                        <a class="noLine" href="https://www.biomedcentral.com/"><img src="img/support-journals/biomedCentral.png" class="img-responsive" style="width: 100%; height: auto" alt="BioMed Central"/></a>
                    </div>
                    <div class="col-md-2 vc" style="padding: 0">
                        <a class="noLine" href="http://journals.plos.org/plosbiology/s/data-availability#loc-recommended-repositories"><img src="img/support-journals/Plos-biology.png" class="img-responsive" style="width: 100%; height: auto" alt="PLOS Biology"/></a>
                    </div>
                    <div class="col-md-2 vc" style="padding: 0">
                        <a class="noLine" href="http://msb.embopress.org/authorguide#datadeposition"><img src="img/support-journals/EMBO-press.png" class="img-responsive" style="width: 100%; height: auto" alt="EMBO press"/></a>
                    </div>
                    <div class="col-md-2 vc" style="padding: 0">
                        <a class="noLine" href="https://f1000research.com/for-authors/data-guidelines"><img src="img/support-journals/F1000-research.png" class="img-responsive" style="width: 100%; height: auto" alt="F1000 Research"/></a>
                    </div>
                    <div class="col-md-2 vc" style="padding: 0">
                        <a class="noLine" href="http://link.springer.com/journal/11306"><img src="img/support-journals/metabolomics.png" class="img-responsive" style="width: 100%; height: auto" alt="Springer Link - Metabolomics"/></a>
                    </div>
                    <div class="col-md-2 vc" style="padding: 0">
                        <a class="noLine" href="http://journal.frontiersin.org/journal/molecular-biosciences/section/metabolomics#author-guidelines"><img src="img/support-journals/frontiers.png" class="img-responsive" style="width: 100%; height: auto" alt="Frontiers"/></a>
                    </div>
                    <div class="col-md-2 vc" style="padding: 0">
                        <a class="noLine" href="http://www.mdpi.com/journal/metabolites/instructions#suppmaterials"><img src="img/support-journals/metabolites.png" class="img-responsive" style="width: 100%; height: auto" alt="Metabolites"/></a>
                    </div>
                    <div class="col-md-2 vc" style="padding: 0">
                        <a class="noLine" href="http://www.mdpi.com/journal/data/instructions#suppmaterials"><img src="img/support-journals/data.png" class="img-responsive" style="width: 100%; height: auto" alt="data"/></a>
                    </div>
                    <div class="col-md-2 vc" style="padding: 0">
                        <a class="noLine" href="https://wellcomeopenresearch.org/for-authors/data-guidelines"><img src="img/support-journals/wellcome.png" class="img-responsive" style="width: 100%; height: auto" alt="Wellcome Trust Open Research"/></a>
                    </div>
                </div>
                <div class="clearfix">&nbsp;</div>
                <div class="clearfix">&nbsp;</div>
                <p><spring:message code="msg.metaboLightsAbout18"/></p>
            </div>
        </div>
    </div>
</div>
<c:if test="${not empty message}">
    <div class="modal fade" id="messageModal" tabindex="-1" role="dialog" aria-labelledby="messageModal">
        <div class="modal-dialog" role="document">
            <div class="modal-content nbr">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="messageModalLabel">MetaboLights message</h4>
                </div>
                <div class="modal-body">
                    <c:if test="${not empty message}">
                        <p><b><c:out value="${message}"/></b></p>
                    </c:if>
                </div>
                <div class="modal-footer text-center">
                    <button type="button" class="btn btn-default btn-xs" data-dismiss="modal">OK</button>
                </div>
            </div>
        </div>
    </div>
</c:if>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.18.0/axios.min.js"></script>
<script>
    $(function() {
        $('#messageModal').modal('show');
    });

    $(document).ready(function () {
        $('#redirectToMyStudiesPage').click(function(){
            window.open("${pageContext.request.contextPath}/editor/console", 'toolbar=no, menubar=no,scrollbars=yes,resizable=yes');
        })
        $('#vf-tabs__item-1').hover(function() {
            $(this).css('cursor','pointer');
          });
        $('#vf-tabs__item-2').hover(function() {
            $(this).css('cursor','pointer');
          });
    });
    $("#vf-tabs__item-1").click(function(){
        $("#vf-tabs__head--1").attr("aria-selected", "true");
        $("#vf-tabs__head--1").attr("class","vf-tabs__link is-active")
        $("#vf-tabs__head--2").attr("aria-selected", "false");
        $("#vf-tabs__head--2").attr("class","vf-tabs__link")
        $("#vf-tabs__section--1").removeAttr("hidden")
        $("#vf-tabs__section--2").attr("hidden","true")
    });
    $("#vf-tabs__item-2").click(function(){
        $("#vf-tabs__head--2").attr("aria-selected", "true");
        $("#vf-tabs__head--2").attr("class","vf-tabs__link is-active")
        $("#vf-tabs__head--1").attr("aria-selected", "false");
        $("#vf-tabs__head--1").attr("class","vf-tabs__link")
        $("#vf-tabs__section--2").removeAttr("hidden")
        $("#vf-tabs__section--1").attr("hidden","true")
        
    });
</script>