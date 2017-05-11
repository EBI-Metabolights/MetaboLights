<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: kalai
  Date: 20/04/2017
  Time: 11:04
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
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
<base href="${pageContext.request.contextPath}/ngeditor/">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" type="image/x-icon" href="favicon.ico">
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" type="text/css" rel="stylesheet">
<link href="styles.8da2364522e0749f90c2.bundle.css" rel="stylesheet"/>
<script>
    localStorage.setItem("apiToken", "${apiToken}");
</script>

<%--Loading study editor for ${studyId}...--%>
<div class="container" style="background-color: #e6e6e6">
    <div class="row">
        <%--<ml-study-editor studyID="${studyId}" apiToken="${apiToken}"></ml-study-editor>--%>
        <ml-study-editor studyID="${studyId}"></ml-study-editor>
    </div>
    <div class="row">
        <div><a href="${pageContext.request.contextPath}/${studyId}" class="btn btn-primary btn-block"
                role="button">Back to ${studyId}</a></div>
    </div>
</div>

<script type="text/javascript" src="inline.4a157d5ef0412c72fe84.bundle.js"></script>
<script type="text/javascript" src="polyfills.63929c2b04758c996018.bundle.js"></script>
<script type="text/javascript" src="vendor.298e93e19050483cd5f3.bundle.js"></script>
<script type="text/javascript" src="main.c9f6acd0cf0fb6e8cc6c.bundle.js"></script>


