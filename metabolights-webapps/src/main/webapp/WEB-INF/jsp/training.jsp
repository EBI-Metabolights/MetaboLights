<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"   %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page language="java" contentType="text/html; charset=UTF-8"%>
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
                                 <div class="vf-summary__text"><a href="https://www.ebi.ac.uk/training/services/metabolights/live-events" target="_blank">View all live training</a></div>
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
                                 <div class="vf-summary__text"><a href="https://www.ebi.ac.uk/training/services/metabolights/on-demand" target="_blank">View all on-demand training</a></div>
                            </div>
                        </section>
                    </div>
                    
        </div>
    </div>
</div>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script>
    $(document).ready(function () {
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