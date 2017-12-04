<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" %>
<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 7/8/14 4:27 PM
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

<%--<script type="text/javascript" src="javascript/jquery-imtechPager.js"></script>--%>
<script type="text/javascript" src="javascript/jquery.jqpagination.min.js"></script>
<link rel="stylesheet" href="cssrl/jqpagination.css" type="text/css"/>
<script type="text/javascript" src="javascript/jquery-highlight.js"></script>

<div id="app">
    <div class="">
        <div class="col-md-12">
            <h4>
                <%-- Error --%>
                <c:if test="${!empty searcResult.err}">
                    ${searchResponse.message}
                </c:if>

                <c:set var="query" value="${searchResponse.content.query}"/>
                <c:set var="pagination" value="${query.pagination}"/>
                <c:set var="hits" value="${searchResponse.content.results}"/>
                <c:set var="facets" value="${query.facets}"/>
                <script type="text/javascript">
                    $(document).ready(function () {
                        $('.pagination').jqPagination({
                            paged: function (page) {
                                // do something with the page variable
                                filterForm = document.forms['filterForm'];
                                pageNumberField = filterForm.elements["pageNumber"];
                                pageNumberField.value = page;
                                filterForm.submit();

                            },
                            current_page: ${pagination.page},
                            max_page: ${pagecount}
                        });

                    });
                </script>

            </h4>
        </div>
        <c:if test="${!empty searchResponse}">
            <c:if test="${!empty query.text}">
                <aside class="grid_6 omega shortcuts expander right" id="search-extras">
                    <div id="ebi_search_results">
                        <h3 class="slideToggle icon icon-functional" data-icon="u"><spring:message
                                code="msg.otherebiresults"/></h3>
                    </div>
                </aside>
                <script>
                    $("[name='freeTextQuery']").val("${query.text}");
                </script>
            </c:if>
            <div class="col-md-3" id="search-filters">
                <%@include file="searchFilter.jsp" %>
            </div>
            <div class="col-md-9" id="search-results">
                <div class="panel panel-default nbr cpanel">
                    <div class="panel-heading">
                        <h5>
                                ${pagination.itemsCount}&nbsp;result<c:if test="${pagination.itemsCount ne 1}">s</c:if>
                                <c:if test="${pagecount gt 1}">
                                    ,&nbsp;showing ${firstPageItemNumber} to ${lastPageItemNumber}
                                </c:if>
                            <span class="pull-right">
                                <c:if test="${pagecount gt 1}">
                                    <span class="right pagination ml-pagination">
                                        <a class="btn btn-default btn-xs" href="#" data-action="first">&laquo;</a>
                                        <a class="btn btn-default btn-xs"  href="#" data-action="previous">&lsaquo;</a>
                                        <input type="text" readonly="readonly"/>
                                        <a class="btn btn-default btn-xs"  href="#" data-action="next">&rsaquo;</a>
                                        <a class="btn btn-default btn-xs"  href="#" data-action="last">&raquo;</a>
                                    </span>
                                </c:if>
                            </span>
                        </h5>
                    </div>
                    <%--<div  class="pull-right">--%>
                        <%--<a target="_blank" href="https://www.ebi.ac.uk/ebisearch/search.ebi?db=metabolights&query=domain_source:metabolights"><h4>Batch Claim Studies to ORCID</h4></a>--%>
                    <%--</div>--%>
                </div>
                <div id="highlight-plugin">
                    <c:forEach var="hit" items="${hits}">
                        <%@include file="summaryWrapper.jsp" %>
                    </c:forEach>
                </div>
                <c:if test="${pagecount gt 1}">
                    <div class="panel panel-default nbr cpanel">
                        <div class="panel-heading">
                            <h5>&nbsp;
                                <div class="pull-right" id="paginationBtm"></div>
                            </h5>
                        </div>
                    </div>
                    <script>$('.pagination').clone().appendTo('#paginationBtm');</script>
                </c:if>
                <c:if test="${!empty query.text}">
                    <script>
                        $('#highlight-plugin').removeHighlight().highlight('${query.text}');
                    </script>
                </c:if>
                <br/>
            </div>
        </c:if>
    </div>
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">{{ study.studyIdentifier }}:&nbsp;{{ study.title }}</h4>
                </div>
                <div class="modal-body study-modal-body">
                    <p><b>Description: </b></p>
                    <p>
                        {{ study.description }}
                    </p>
                    <div class="row">
                        <hr>
                    </div>
                    <div class="row">
                        <div class="">
                            <div class="col-md-5">
                                <small><i class="fa fa-user"></i>&nbsp;<spring:message code="ref.msg.CitationAuthors"/>:
                                    <span id="aff" v-for="contact in study.contacts" >
                                                   <strong>{{contact.firstName}}&nbsp;{{contact.lastName}}</strong>
                                                </span>
                                </small>
                            </div>
                            <div class="col-md-7">
                                <small class="pull-right">
                                    <i class="fa fa-calendar"></i>&nbsp;
                                    <spring:message code="label.subDate"/>:
                                    <strong>{{study.studySubmissionDate | formatDate}}</strong>
                                    <spring:message code="label.releaseDate"/>: <strong>{{study.studyPublicReleaseDate | formatDate}}</strong>
                                    <spring:message code="label.updateDate"/>: <strong>{{study.updateDate | formatDate}}</strong>
                                </small><br>
                                <small class="pull-right" id="mStudyStatus">
                                    <i class="fa fa-user">&nbsp;</i><spring:message code="label.subm"/>:&nbsp;
                                    <span v-for="user in study.users">
                                                       <a href="mailto:{{user.userName}}?subject=<spring:message code="msg.emailStudyLinkSubject"/>&nbsp;">{{user.fullName}}</a>
                                                   </span>
                                    &nbsp;|&nbsp;
                                    <i class="fa fa-bookmark"></i>&nbsp;<spring:message
                                        code="ref.msg.status"/>:&nbsp;{{ study.studyStatus }}
                                    <%-- &emsp;
                                    <button type="button" class="btn btn-default btn-xs" data-toggle="tooltip" data-placement="bottom" id="tourButton" title="Study Tour"><i class="fa fa-compass"></i></button>--%>
                                </small>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <hr>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="panel panel-default">
                                <div class="panel-heading"><span class="glyphicon glyphicon-globe"
                                                                 aria-hidden="true"></span>&nbsp;
                                    <spring:message code="label.organisms"/></div>
                                <div class="panel-body">
                                           <span v-for="organism in study.organism">
                                            <span>
                                                {{ organism.organismName }};
                                            </span>
                                           </span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="panel panel-default">
                                <div class="panel-heading"><span class="glyphicon glyphicon-list"
                                                                 aria-hidden="true"></span>&nbsp;
                                    <spring:message code="label.studyDesign"/></div>
                                <div class="panel-body">
                                           <span v-for="descriptor in study.descriptors">
                                            <span>
                                                {{ descriptor.description }};
                                            </span>
                                           </span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="panel panel-default">
                                <div class="panel-heading"><span class="glyphicon glyphicon-tags"
                                                                 aria-hidden="true"></span>&nbsp;
                                    <spring:message code="label.experimentalFactors"/></div>
                                <div class="panel-body">
                                    <span v-for="factor in study.factors">
                                            <span>
                                                {{ factor.name }};
                                            </span>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.17.0/axios.min.js"></script>
<script>
    $(".grey").on('shown.bs.collapse', function () {
        var active = $(this).attr('id');
        $.cookie(active, "1");
        var panels = $.cookie(); //get all cookies
        //console.log(panels);
    });

    $(".grey").on('hidden.bs.collapse', function () {
        var active = $(this).attr('id');
        $.removeCookie(active);
        var panels = $.cookie(); //get all cookies
        //console.log(panels);
    });

    $(document).ready(function () {
        var panels = $.cookie(); //get all cookies
        //console.log(panels);
        for (var panel in panels) { //<-- panel is the name of the cookie
            if ($("#" + panel).hasClass('grey')) // check if this is a panel
            {
                $("#" + panel).collapse("show");
            }
        }

        $('[data-toggle="tooltip"]').tooltip();
    });

</script>

<c:if test="${!empty query.text}">
    <script src="//www.ebi.ac.uk/web_guidelines/js/ebi-global-search-run.js"></script>
    <script src="//www.ebi.ac.uk/web_guidelines/js/ebi-global-search.js"></script>
</c:if>


<script>
    var app = new Vue({
        el: '#app',
        data: {
            selectedStudy: null,
            study: {},
            hasGalaxyDetails: '',
            toolId: null,
            URL: null
        },
        mounted: function() {
            this.$nextTick(function () {
                var galaxyURL = decodeURIComponent(this.getUrlParameter('GALAXY_URL', window.location));
                if (galaxyURL != 'null'){
                    this.hasGalaxyDetails = galaxyURL
                }
                this.toolId = this.getUrlParameter('tool_id', window.location)
                this.URL = window.location.origin;
                console.log(this.hasGalaxyDetails);
            })
        },
        methods: {
            studyQuickView: function (id) {
                this.selectedStudy = id;
                this.getStudyDetails();
            },
            getStudyDetails: function () {
                var that = this;
                axios.get('${pageContext.request.contextPath}/webservice/study/' + this.selectedStudy)
                    .then(function (response){
                        that.study = response.data.content;
                        $('#myModal').modal('show');
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            },
            getUrlParameter: function(name, url){
                if (!url) url = location.href;
                name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
                var regexS = "[\\?&]"+name+"=([^&#]*)";
                var regex = new RegExp( regexS );
                var results = regex.exec( url );
                return results == null ? null : results[1];
            }
        },
        filters: {
            formatDate: function (value) {
                var a = new Date(value);
                var months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
                var year = a.getFullYear();
                var month = months[a.getMonth()];
                var date = a.getDate();
                var hour = a.getHours();
                var min = a.getMinutes();
                var sec = a.getSeconds();
                var time = date + ' ' + month + ' ' + year ;
                return time;
            }
        }
    })
</script>

<script>

    $( ".crossRefWrapper" ).each(function( index ) {
        var uniqRefs = []
        $( this ).find('.crossRef').each(function() {
            //console.log($(this).text());
            if ($.inArray($(this).text(), uniqRefs) == -1)
            {
                uniqRefs.push($(this).text())
            }else{
                $(this).prev('span.separator').remove();
                $(this).remove();
            }
        });
    });


</script>