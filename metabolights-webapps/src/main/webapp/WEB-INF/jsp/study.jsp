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
<base href="${pageContext.request.contextPath}/">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" type="image/x-icon" href="favicon.ico">

<meta name="viewport" content="width=device-width, initial-scale=1">
<%--<script>--%>
<%--if (location.protocol != 'https:')--%>
<%--{--%>
    <%--location.href = 'https:' + window.location.href.substring(window.location.protocol.length);--%>
<%--}--%>
<%--</script>--%>
<script type="text/javascript" src="//code.jquery.com/jquery-1.11.3.js" ></script>
<script type="text/javascript" src="//code.jquery.com/ui/1.9.2/jquery-ui.min.js" charset="utf-8"></script>

<script type="text/javascript" src="./editor/assets/aspera/asperaweb-4.js" charset="utf-8"></script>
<script type="text/javascript" src="./editor/assets/aspera/connectinstaller-4.js" charset="utf-8"></script>
<script type="text/javascript" src="./editor/assets/aspera/jquery-ui.js" charset="utf-8"></script>
<script type="text/javascript" src="./editor/assets/aspera/jquery-namespace.js" charset="utf-8"></script>
<script type="text/javascript" src="./editor/assets/aspera/ml-aspera-config.js" charset="utf-8"></script>
<script type="text/javascript" src="./editor/assets/aspera/ml-aspera.js" charset="utf-8"></script>
<script type="text/javascript" src="./editor/assets/aspera/install.js" charset="utf-8"></script>

<%--<script>--%>
<%--var editorToken = ${editorToken};--%>
<%--localStorage.setItem("user", JSON.stringify(editorToken));--%>
<%--</script>--%>
<style type="text/css">
    body, html {
        height: 100%;
        font-family: 'Roboto', sans-serif;
    }
    .app-loading {
        position: relative;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        height: 100%;
    }
    .app-loading .spinner {
        height: 100px;
        width: 100px;
        animation: rotate 2s linear infinite;
        transform-origin: center center;
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        margin: auto;
    }
    .app-loading .spinner .path {
        stroke-dasharray: 1, 200;
        stroke-dashoffset: 0;
        animation: dash 1.5s ease-in-out infinite;
        stroke-linecap: round;
        stroke: #ddd;
    }
    @keyframes rotate {
        100% {
            transform: rotate(360deg);
        }
    }
    @keyframes dash {
        0% {
            stroke-dasharray: 1, 200;
            stroke-dashoffset: 0;
        }
        50% {
            stroke-dasharray: 89, 200;
            stroke-dashoffset: -35px;
        }
        100% {
            stroke-dasharray: 89, 200;
            stroke-dashoffset: -124px;
        }
    }
</style>
<link rel="stylesheet" href="editor/styles.cdab5a6afcbf519327ed.css">
<noscript>
    <strong>
        We're sorry but MetaboLights online submission and editor tool doesn't work properly without JavaScript enabled. Please enable it to continue.
    </strong>
</noscript>
<app-root mtblsid="${studyId}" obfuscationcode="${obfuscationCode}" mtblsUser="${email}" isOwner="${isOwner}" isCurator="${isCurator}" mtblsJWT="${jwt}">
    <div class="app-loading">
        <div class="logo"></div>
        <svg class="spinner" viewBox="25 25 50 50">
            <circle class="path" cx="50" cy="50" r="20" fill="none" stroke-width="2" stroke-miterlimit="10"/>
        </svg>
    </div>
</app-root>
<script src="editor/runtime.359d5ee4682f20e936e9.js" defer></script><script src="editor/polyfills-es5.11d2987ae2d743afd2b7.js" nomodule defer></script><script src="editor/polyfills.c486699dfb727ed35554.js" defer></script><script src="editor/main.653cf9a958bc1d69af48.js" defer></script>