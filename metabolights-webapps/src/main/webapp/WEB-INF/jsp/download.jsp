<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>


    <div class="text_header plain">
	    <spring:message code="menu.downloadHelp" />
        <br/> <br/>
    </div>

    <table>
     	<tr>
            <td align="center" class="img_alignment_yellow">
            	<a href="ftp://ftp.ebi.ac.uk/pub/databases/metabolights/submissionTool/ISAcreatorMetaboLights.zip"><img src="img/ebi-icons/32px/tool-box.png" alt="Tools download"/></a>
            </td>
            <td colspan='2'>
                <br/>
                <spring:message code="msg.metabolightsAbout12" />
            </td>
        </tr>
        <tr>
            <td colspan='3'>
                <br/>
                <spring:message code="msg.metabolightsAbout9" />
            </td>
        </tr>
        <tr><td><br/><br/></td></tr>
        <tr>
            <td align="center" class="img_alignment_yellow">
                <a href="ftp://ftp.ebi.ac.uk/pub/databases/metabolights/"><img src="img/ebi-icons/32px/download.png" alt="FTP download"/></a>
            </td>
            <td colspan='2'> <spring:message code="msg.metabolightsAbout7" /></td>
        </tr>
  
    </table>


    <div class="text-fullwidth">
        <br/>
        <hr/>
        <br/>
        <div class="text-column">
            <a href="<spring:message code="url.isatools"/>"><img src="img/softwaresuitelogo.png" alt="ISAtools"/></a>
            <br/>
        </div>
        <spring:message code="msg.metabolightsAbout6" />
    </div>

