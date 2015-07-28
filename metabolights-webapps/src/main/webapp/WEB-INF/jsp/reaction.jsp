<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 6/21/13 4:21 PM
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

<c:choose>
	<c:when test="${not empty reactions}">
        <c:set var="chebiUrl" value="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&amp;chebiId=" />
        <c:set var="chebiDimension" value="&amp;dimensions=200&amp;scaleMolecule=true"/>
        <select id="reactionsList">
        <c:forEach var="reaction" items="${reactions}">
            <option value="${reaction.id}">
                <c:out value="${reaction.name}"/>
            </option>
        </c:forEach>
        </select>
        <br/>

        <h4 id="reactionTitle"></h4>
        <div id="BioJSReaction" class="grid_24">
        </div>

        <script>
            $(document).ready(function(){

                // Listen to clicks on reactions
                $("#reactionsList").change(function(e){

                    e.preventDefault();

                    var id = $(this).val();

                    var bioJSReaction = new Biojs.Rheaction({
                        target: 'BioJSReaction',
                        id: id,
                        proxyUrl: undefined,
                        rheaWsUrl: "./ebi/webservices/rhea/rest/1.0/ws/reaction/cmlreact/"

                    });

                    // Fill the reaction title
                    var header= $("#reactionTitle");

                    $(header).html("<a href='http://www.ebi.ac.uk/rhea/reaction.xhtml?id=" + id + "'>RHEA:" + id + "</a>");

                });

                // Trigger the change
                // And now fire change event when the DOM is ready
                $('#reactionsList').trigger('change');

            });


        </script>
	</c:when>
	<c:otherwise>
		<p align="center">
			<b><spring:message code="ref.msg.noReactions"/></b>
		</p>
	</c:otherwise>
</c:choose>

<%-- <img src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${ReactantChebiId}"  width="20px" height="20px" /> --%>