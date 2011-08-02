<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" lang="eng"><!-- InstanceBegin template="/Templates/new_template_no_menus.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="description" content="Metabolights metabolmics experiments" />
    <meta name="author" content="The Metabolights team" />
    <meta http-equiv="Content-Language" content="en-GB" />
    <meta http-equiv="Window-target" content="_top" />
    <meta name="no-email-collection" content="http://www.unspam.com/noemailcollection/" />
    <meta name="generator" content="Dreamweaver 8" />

    <title><tiles:insertAttribute name="title" ignore="true" /></title>

    <link rel="stylesheet"  href="http://www.ebi.ac.uk/inc/css/contents.css"     type="text/css" />
    <link rel="stylesheet"  href="http://www.ebi.ac.uk/inc/css/userstyles.css"   type="text/css" />
    <script  src="http://www.ebi.ac.uk/inc/js/contents.js" type="text/javascript"></script>
    <link rel="SHORTCUT ICON" href="http://www.ebi.ac.uk/bookmark.ico" />
    <link rel="stylesheet"  href="http://www.ebi.ac.uk/inc/css/sidebars.css"   type="text/css" />
    <link rel="stylesheet" type="text/css" href="css/styles.css" media="all" />

    <style type="text/css">
        @media print {
            body, .contents, .header, .contentsarea, .head {
                position: relative;
            }
        }
    </style>
</head>

<body onLoad="if(navigator.userAgent.indexOf('MSIE') != -1) {document.getElementById('head').allowTransparency = true;}; onloadAction();" onunload="onunloadAction();">
    <div class="headerdiv" id="headerdiv" style="position:absolute; z-index: 1;">
        <!--  remove http://www.ebi.ac.uk  on deployment-->
        <iframe src="http://www.ebi.ac.uk/inc/head.html" name="head" id="head" frameborder="0" marginwidth="0px" marginheight="-10px" scrolling="no"  width="100%" style="position:relative; z-index: 1; height: 57px;"></iframe>
    </div>
    <div class="contents" id="contents"  >
        <table class="contentspane" id="contentspane" summary="The main content pane of the page" style="width: 100%">
            <tr>
              <td class="leftmargin"><img src="http://www.ebi.ac.uk/inc/images/spacer.gif" class="spacer" alt="spacer" /></td>
              <td class="leftmenucell" id="leftmenucell"> <div class="leftmenu" id="leftmenu" style="1px; visibility: hidden; display: none;">
                <img src="http://www.ebi.ac.uk/inc/images/spacer.gif" class="spacer" alt="spacer" /></div></td>
                <td class="contentsarea" id="contentsarea">

                    <div class="wrapper" >
                        <div id="topAndBody"  >
                            <tiles:insertAttribute name="header"/>
                            <div id="leftFilterdiv" style="width:0px; float: left">
                                <tiles:insertAttribute name="bodyleft" />
                            </div>
                            <div id="bodyDiv" style="width:1000px; float:right">
                                <tiles:insertAttribute name="body" />
                            </div>
                        </div>
                    </div>
                   <img src="http://www.ebi.ac.uk/inc/images/spacer.gif" class="spacer" alt="spacer" />
                </td>
                <td class="rightmenucell" id="rightmenucell">
                  <div class="rightmenu" id="rightmenu"><img src="http://www.ebi.ac.uk/inc/images/spacer.gif" class="spacer" alt="spacer" /></div>
                </td>
            </tr>
        </table>

        <table class="footerpane" id="footerpane" summary="The main footer pane of the page">
           <tr>
             <td colspan ="4" class="footerrow">
                <div class="footerdiv" id="footerdiv"  style="z-index:2;">
                    <!--  remove http://www.ebi.ac.uk  on deployment-->
                    <iframe src="http://www.ebi.ac.uk/inc/foot.html" name="foot" frameborder="0" marginwidth="0px" marginheight="0px" scrolling="no"  height="22px" width="100%"  style="z-index:2;">[Your user agent does not support frames or is currently configured not to display iframes.]</iframe>
                </div>
             </td>
           </tr>
        </table>
    </div>
</body>
</html>

