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

<base href="${pageContext.request.contextPath}/labs/">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" type="image/x-icon" href="favicon.ico">


<script type="text/javascript" src="//code.jquery.com/jquery-1.11.3.js" ></script>
<script type="text/javascript" src="//code.jquery.com/ui/1.9.2/jquery-ui.min.js" charset="utf-8"></script>

<script type="text/javascript" src="assets/aspera/asperaweb-4.js" charset="utf-8"></script>
<script type="text/javascript" src="assets/aspera/connectinstaller-4.js" charset="utf-8"></script>
<script type="text/javascript" src="assets/aspera/jquery-ui.js" charset="utf-8"></script>
<script type="text/javascript" src="assets/aspera/jquery-namespace.js" charset="utf-8"></script>
<script type="text/javascript" src="assets/aspera/ml-aspera-config.js" charset="utf-8"></script>
<script type="text/javascript" src="assets/aspera/ml-aspera.js" charset="utf-8"></script>
<script type="text/javascript" src="assets/aspera/install.js" charset="utf-8"></script>

<app-root>
    <div class="spinner">
        <div class="bounce1"></div>
        <div class="bounce2"></div>
        <div class="bounce3"></div>
    </div>
</app-root>

<script type="text/javascript" src="inline.bundle.js"></script>
<script type="text/javascript" src="polyfills.bundle.js"></script>
<script type="text/javascript" src="scripts.bundle.js"></script>
<script type="text/javascript" src="styles.bundle.js"></script>
<script type="text/javascript" src="vendor.bundle.js"></script>
<script type="text/javascript" src="main.bundle.js"></script>