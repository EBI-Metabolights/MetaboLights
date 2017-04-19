<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 9/24/12 12:40 PM
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

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" lang="eng"><!-- InstanceBegin template="/Templates/new_template_no_menus.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
    <meta name="description" content="Metabolights metabolmics experiments" />
    <meta name="author" content="The Metabolights team" />
    <meta http-equiv="Content-Language" content="en-GB" />
    <meta http-equiv="Window-target" content="_top" />
    <meta name="no-email-collection" content="http://www.unspam.com/noemailcollection/" />
    <meta name="generator" content="Dreamweaver 8" />
    <meta name="keywords" content="metabolite, metabolism, metabolic, metabonomics, metabolomics study, metabolomics experiment, metabolic pathway, metabolite database" />
   
    <title><tiles:insertAttribute name="title" ignore="true" /></title>

    <link rel="stylesheet"  href="//www.ebi.ac.uk/inc/css/contents.css"     type="text/css" />
    <link rel="stylesheet"  href="//www.ebi.ac.uk/inc/css/userstyles.css"   type="text/css" />
    <script  src="//www.ebi.ac.uk/inc/js/contents.js" type="text/javascript"></script>
    <link rel="SHORTCUT ICON" href="//www.ebi.ac.uk/bookmark.ico" />
    <link rel="stylesheet"  href="//www.ebi.ac.uk/inc/css/sidebars.css"   type="text/css" />
    <link rel="stylesheet" type="text/css" href="css/multi-line-button.css" media="all" />
    <link rel="stylesheet" type="text/css" href="css/styles.css" media="all" />
    <link rel="stylesheet" type="text/css" href="css/jquery-ui-1.8.21.custom.css" media="all" />

    <style type="text/css">
        @media print {
            body, .contents, .header, .contentsarea, .head {
                position: relative;
            }
        }
    </style>

    <script type="text/javascript">
        function onloadAction() {
            //dummy, to stop stupid messages from IE
        }

        function onunloadAction() {
            //dummy, to stop stupid messages from IE
        }
    </script>

</head>

<body onload="if(navigator.userAgent.indexOf('MSIE') != -1) {document.getElementById('head').allowTransparency = true;};onloadAction();" onunload="onunloadAction();">
    <div class="headerdiv" id="headerdiv" style="position:absolute; z-index: 1; width: 100%; min-width: 1047px">
        <iframe src="<spring:message code="url.EBIheaderTop"/>" name="head" id="head" frameborder="0" marginwidth="0px" marginheight="-10px" scrolling="no" width="100%" style="position:relative; z-index: 1; height: 57px;"></iframe>
    </div>
    <div class="contents" id="contents" >
        <table class="contentspane" id="contentspane" summary="The main content pane of the page" style="width: 100%; position:relative;">
            <tr>
              <td class="leftmargin"><img src="img/spacer.gif" class="spacer" alt="spacer" /></td>
              <td class="leftmenucell" id="leftmenucell"> <div class="leftmenu" id="leftmenu" style="width: 1px; visibility: hidden; display: none;">
                <img src="img/spacer.gif" class="spacer" alt="spacer" /></div></td>
                <td class="contentsarea" id="contentsarea">

                    <div class="wrapper" >
                        <div id="topAndBody">
                            <tiles:insertAttribute name="header"/>
                            <div id="leftFilterdiv" style="width:0px; float: left">
                                <tiles:insertAttribute name="bodyleft" />
                            </div>
                            <div id="bodyDiv" style="width:1000px; float:right">
                                <tiles:insertAttribute name="body" />
                            </div>
                        </div>
                    </div>

                   <img src="img/spacer.gif" class="spacer" alt="spacer" />
                </td>
                <td class="rightmenucell" id="rightmenucell">
                  <div class="rightmenu" id="rightmenu"><img src="img/spacer.gif" class="spacer" alt="spacer" /></div>
                </td>
            </tr>
        </table>

        <table class="footerpane" id="footerpane" summary="The main footer pane of the page">
           <tr>
             <td colspan ="4" class="footerrow">
                <div class="footerdiv" id="footerdiv" style="z-index:2; min-width: 1047px">
                    <iframe src="<spring:message code="url.EBIheaderFoot"/>" name="foot" frameborder="0" marginwidth="0px" marginheight="0px" scrolling="no"  height="22px" width="100%"  style="z-index:2;">[Your user agent does not support frames or is currently configured not to display iframes.]</iframe>
                </div>
             </td>
           </tr>
        </table>
    </div>
</body>
</html>

