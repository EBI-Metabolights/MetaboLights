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

<link href='https://fonts.googleapis.com/css?family=Miriam+Libre:400,700|Source+Sans+Pro:200,400,700,600,400italic,700italic' rel='stylesheet' type='text/css'>

<script type="text/javascript" src="//code.jquery.com/jquery-1.11.3.js" ></script>
<script type="text/javascript" src="//code.jquery.com/ui/1.9.2/jquery-ui.min.js" charset="utf-8"></script>

<script type="text/javascript" src="assets/aspera/asperaweb-4.js" charset="utf-8"></script>
<script type="text/javascript" src="assets/aspera/connectinstaller-4.js" charset="utf-8"></script>
<script type="text/javascript" src="assets/aspera/jquery-ui.js" charset="utf-8"></script>
<script type="text/javascript" src="assets/aspera/jquery-namespace.js" charset="utf-8"></script>
<script type="text/javascript" src="assets/aspera/ml-aspera-config.js" charset="utf-8"></script>
<script type="text/javascript" src="assets/aspera/ml-aspera.js" charset="utf-8"></script>
<script type="text/javascript" src="assets/aspera/install.js" charset="utf-8"></script>
<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-110351649-1"></script>
<script>
    window.dataLayer = window.dataLayer || [];
    function gtag(){dataLayer.push(arguments);}
    gtag('js', new Date());

    gtag('config', 'UA-110351649-1');
</script>
<link rel="stylesheet" href="styles.ba1173085f6defca8c9b.css">
<app-root>
    <div class="page-wrapper vc">
      <span>
        <div class="spinner">
            <div class="bounce1"></div>
            <div class="bounce2"></div>
            <div class="bounce3"></div>
        </div>
      </span>
    </div>
</app-root>

<script type="text/javascript" src="runtime.ec2944dd8b20ec099bf3.js"></script>
<script type="text/javascript" src="polyfills.3252f90364ffcd9d8845.js"></script>
<script type="text/javascript" src="scripts.2812bf66564829e5b1d1.js"></script>
<script type="text/javascript" src="main.30d1d385606db51f66eb.js"></script>
<script>
    var localFrameworkVersion = 'other';
    var newDataProtectionNotificationBanner = document.createElement('script');
    newDataProtectionNotificationBanner.src = 'https://ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/js/ebi-global-includes/script/5_ebiFrameworkNotificationBanner.js?legacyRequest='+localFrameworkVersion;
    document.head.appendChild(newDataProtectionNotificationBanner);
    newDataProtectionNotificationBanner.onload = function() {
        ebiFrameworkRunDataProtectionBanner(); // invoke the banner
    };
</script>