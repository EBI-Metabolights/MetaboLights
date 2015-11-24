<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>

<%--<script type="text/javascript" src="javascript/jquery-imtechPager.js"></script>--%>
<link rel="stylesheet" href="cssrl/iconfont/font_style.css" type="text/css" />


<div class="grid_24 alpha omega box">

    <div class="grid_6 alpha">

      <a href="${compound.accession}">
        <img src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${fn:replace(compound.accession, "MTBLC", "")}&amp;dimensions=200&amp;scaleMolecule=true"
             onerror="this.src='img/large_noImage.gif';" width="100px"
             height="100px" />
      </a>

    </div>

    <div class="grid_18">

        <span id="icons" class="right noLine">

            <c:if test="${compound.hasSpecies}">
              <a href="${compound.accession}#biology"><span class="icon icon-species forSpecies" data-icon="R" title="Species"></span></a>
            </c:if>

            <c:if test="${compound.hasPathways}">
              <a href="${compound.accession}#pathways"><span class="icon icon-conceptual forConceptual" data-icon="y" title="Pathways"></span></a>
            </c:if>

            <c:if test="${compound.hasReactions}">
              <a href="${compound.accession}#reactions"><span class="icon icon-chemistry forChemistry" data-icon="R" title="Reactions"></span></a>
            </c:if>

            <c:if test="${compound.hasNMR}">
              <a href="${compound.accession}#nmrspectra"><span class="icon2-NMRLogo icon2-biggerFonts" data-icon="*" title="NMR"></span></a>
            </c:if>

            <c:if test="${compound.hasMS}">
              <a href="${compound.accession}#msspectra"><span class="icon2-MSLogo icon2-biggerFonts" data-icon=")" title="MS"></span></a>
            </c:if>

            <c:if test="${compound.hasLiterature}">
              <a href="${compound.accession}#literature"><span class="icon icon-conceptual forConceptual" data-icon="l" title="Literature"></span></a>
            </c:if>

        </span>

      <div class="grid_24">

        <h5>
          ${compound.name} (<a href="${compound.accession}">${compound.accession}</a>)
        </h5>
        <c:if test="${not empty compound.description}">
          <p>
            ${compound.description}
          </p>
        </c:if>
        <p>
          <c:forEach var="specie" items="${compound.metSpecies}"
                     varStatus="loopStatus">
            <c:choose>
              <c:when test="${loopStatus.index eq 0}">
                <b><spring:message code="ref.msg.mtbl.studies" /></b>
              </c:when>
              <c:otherwise>
                ,
              </c:otherwise>
            </c:choose>
            <a href="${specie.crossReference.accession}">${specie.crossReference.accession}</a>
          </c:forEach>
        </p>
      </div>

    </div>

</div>