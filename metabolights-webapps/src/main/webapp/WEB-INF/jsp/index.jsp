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
    <div class="col-md-9">
        <div class="panel panel-default nbr">
            <div class="panel-body grey">
                <h3 class="metabolights-logo">MetaboLights</h3>
                <p>MetaboLights is a database for Metabolomics experiments and derived information. The database is cross-species, cross-technique and covers metabolite structures and their reference spectra as well as their biological roles, locations and concentrations, and experimental data from metabolic experiments. MetaboLights is the recommended Metabolomics repository for a number of leading journals.
                    <br>
                    <small><a target="_blank" href="https://www.ebi.ac.uk/about/people/claire-odonovan">More about us</a></small>
                </p>
                <br>
                <a class="more" target="_blank" href="https://www.ebi.ac.uk/training/online/course/metabolights-quick-tour-0">Quick tour</a>
                <div>&nbsp;</div>
            </div>
        </div>
        <div class="row col">
            <div class="col-md-4">
                <div class="panel nbr panel-primary">
                    <div class="panel-body text-center"><br>
                        <i class="fa fa-2x  text-primary fa-book"></i><br><br>
                        <h4 class="section-heading text-primary">Study</h4>

                    </div>
                    <div class="custom-footer text-center">
                        <ul>
                            <li class="vc vh50"><a href="studies"><b>Browse</b></a></li>
                            <li class="vc vh50"><a href="advancedsearch"><b>ORCID search</b></a></li>
                            <%--<li class="vc vh50"><a><b>MetaboLights Labs</b>--%>
                                <%--<br>&nbsp;<span class="label label-warning">Coming soon</span></a></li>--%>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
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
            <div class="col-md-4">
                <div class="panel nbr panel-danger">
                    <div class="panel-body text-center"><br>
                        <i class="fa fa-2x fa-user-circle text-danger"></i><br><br>
                        <h4 class="section-heading text-danger">Training</h4>

                    </div>
                    <div class="custom-footer text-center">
                        <ul>
                            <%--<li><a href="https://www.ebi.ac.uk/training/online/about-train-online"><b>Webinar</b></a></li>--%>
                            <li class="vc vh50"><a target="_blank" href="https://www.ebi.ac.uk/training/online/topic/metabolomics"><b>Metabolomics Train online</b></a></li>
                            <li class="vc vh50"><a target="_blank" href="https://www.ebi.ac.uk/training/online/course/metabolights-quick-tour-0" target="_blank"><b>MetaboLights Quick tour</b></a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
            <div class="row">
                <div style="height: 80px;" class="col-md-6 col-md-offset-3 text-center vc vh100">
                    <a style="width: 90%; padding: 20px" class="btn btn-lg btn-primary" id="redirectToMyStudiesPage">
                        &emsp;Submit to MetaboLights&emsp;
                    </a>
                </div>
            </div>
    </div>
    <div class="col-md-3">
        <div class="panel nbr panel-default">
            <div class="panel-body ntbp">
                <div class="row p1px">
                    <a class="twitter-timeline"  href="https://twitter.com/MetaboLights" data-widget-id="642268788906422272">Tweets by @MetaboLights</a>
                    <script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+"://platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>
                </div>
            </div>
        </div>
        <%--<div class="panel nbr panel-primary">--%>
            <%--<div class="panel-heading nbtr">--%>
                <%--Submit to MetaboLights--%>
            <%--</div>--%>
            <%--<div class="panel-body">--%>
                <%--<a href="presubmit" class="more"><b>Upload Study</b></a>--%>
                <%--&lt;%&ndash;<div class="row">&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<hr>&ndash;%&gt;--%>
                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                <%--&lt;%&ndash;Upload reference spectra to MetaboLights - Compounds Reference Library<br><a href="#" class="more">more</a>&ndash;%&gt;--%>
            <%--</div>--%>
        <%--</div>--%>
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
    });
</script>