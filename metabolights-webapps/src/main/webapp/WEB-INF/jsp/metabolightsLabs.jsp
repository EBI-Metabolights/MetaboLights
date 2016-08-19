<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 5/21/14 12:39 PM
  ~ Modified by:   venkata
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

<script src="${pageContext.request.contextPath}/metabolightslabs/ember-cli-live-reload.js" type="text/javascript"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/MetCompound.css" type="text/css"/>
<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css">
<base href="${pageContext.request.contextPath}/metabolightslabs/">
    <div class="container-fluid" id="app">
        <app-root>Loading...</app-root>
    </div>
<script src="${pageContext.request.contextPath}/metabolightslabs/vendor/es6-shim/es6-shim.js"></script>
<script src="${pageContext.request.contextPath}/metabolightslabs/vendor/reflect-metadata/Reflect.js"></script>
<script src="${pageContext.request.contextPath}/metabolightslabs/vendor/systemjs/dist/system.src.js"></script>
<script src="${pageContext.request.contextPath}/metabolightslabs/vendor/zone.js/dist/zone.js"></script>
<script>
    System.import('${pageContext.request.contextPath}/metabolightslabs/system-config.js').then(function () {
        System.import('main');
    }).catch(console.error.bind(console));
</script>


<script>
    window.onload = function(){
        document.getElementById('local-nav').style.display = 'none';
        document.getElementById('breadcrumb').style.display = 'none';
    };
</script>