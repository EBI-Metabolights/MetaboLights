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
<%--<link rel="icon" type="image/x-icon" href="favicon.ico">--%>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
      integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
      integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
<link rel="stylesheet" media="screen"
      href="https://cdnjs.cloudflare.com/ajax/libs/handsontable/0.32.0/handsontable.full.css">
<link href="styles.32b2992df6194aa895ed.bundle.css" rel="stylesheet"/>

<div class="container">
    <div class="row">
        <app-study-editor studyID="${studyId}"></app-study-editor>
    </div>
    <div class="row">
        <div><a href="${pageContext.request.contextPath}/${studyId}" class="btn btn-primary pull-right"
                role="button"><span style="color:white;">Go back to ${studyId}</span></a></div>
    </div>
</div>

<script type="text/javascript" src="inline.0316639ae352dda940b4.bundle.js"></script>
<script type="text/javascript" src="polyfills.63929c2b04758c996018.bundle.js"></script>
<script type="text/javascript" src="vendor.4d9b987a0f2849e3adcf.bundle.js"></script>
<script type="text/javascript" src="main.1ee7ffaad43a7db5055b.bundle.js"></script>

















