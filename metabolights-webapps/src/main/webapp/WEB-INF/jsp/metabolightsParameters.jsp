<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 5/8/13 3:34 PM
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

<%--
  Created by IntelliJ IDEA.
  User: tejasvi
  Date: 02/05/13
  Time: 10:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<link rel="stylesheet" href="${pageContext.request.contextPath}/cssrl/iconfont/font_style.css" type="text/css"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/metabolights.css" type="text/css"/>

<div class="container-fluid">
    <div class="col-md-12">
        <div class="col-md-12">
            <h3 class="heading"><spring:message code="metaboLights.parameters"/></h3>
            <div class="row">
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>
                                <spring:message code="metaboLights.parameters.name"/>
                            </th>
                            <th>
                                <spring:message code="metaboLights.parameters.value"/>
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <form name="parametersForm" action="parameters" method="post">
                            <c:forEach var="parameters" items="${mtblparamteres}">
                                <tr>
                                    <td>
                                        <a href="updateParameters?paramname=${parameters.parameterName}&paramvalue=${parameters.parameterValue}">${parameters.parameterName}</a>
                                    </td>
                                    <td>
                                            ${parameters.parameterValue}
                                    </td>
                                </tr>
                            </c:forEach>
                        </form>
                    </tbody>
                </table>
            </div>
            <a href="addParameters"><spring:message code="metaboLights.parameters.add"/></a>
        </div>
    </div>
</div>