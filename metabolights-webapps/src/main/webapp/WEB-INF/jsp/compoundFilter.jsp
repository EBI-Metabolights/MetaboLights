
<c:set var="caption" value="Compound features"/>
<h5 class="ssh">${caption}</h5>
<c:forEach var="facet" items="${facets}">
    <c:if test="${facet.name eq 'compound.hasSpecies'}">
        <c:set var="classSet" value="false"/>
        <input type="checkbox"
               name="${facet.name}"
               value="T"
        <c:forEach var="line" items="${facet.lines}">
        <c:if test='${line.checked}'>
               <c:if test="${line.value eq 'T' && classSet eq 'false' }">CHECKED
            <c:set var="classSet" value="true"/>
        </c:if>
        </c:if>
        </c:forEach>
               onclick="this.form.submit();">
                                            <span class="icon icon-species forSpecies" data-icon="R"
                                                  title="Species" style="font-size: 0.80em;"></span> Species
        <br/>
    </c:if>
    <c:if test="${facet.name eq 'compound.hasPathways'}">
        <c:set var="classSet" value="false"/>
        <input type="checkbox"
               name="${facet.name}"
               value="T"
        <c:forEach var="line" items="${facet.lines}">
        <c:if test='${line.checked}'>
               <c:if test="${line.value eq 'T' && classSet eq 'false' }">CHECKED
            <c:set var="classSet" value="true"/>
        </c:if>
        </c:if>
        </c:forEach>
               onclick="this.form.submit();">
                                                    <span class="icon icon-conceptual forConceptual" data-icon="y"
                                                          title="Pathways" style="font-size: 0.80em;"></span> Pathways
        <br/>
    </c:if>

    <c:if test="${facet.name eq 'compound.hasReactions'}">
        <c:set var="classSet" value="false"/>
        <input type="checkbox"
               name="${facet.name}"
               value="T"
        <c:forEach var="line" items="${facet.lines}">
        <c:if test='${line.checked}'>
               <c:if test="${line.value eq 'T' && classSet eq 'false' }">CHECKED
            <c:set var="classSet" value="true"/>
        </c:if>
        </c:if>
        </c:forEach>
               onclick="this.form.submit();">
                                                    <span class="icon icon-chemistry forChemistry" data-icon="R"
                                                          title="Reactions" style="font-size: 0.80em;"></span> Reactions
        <br/>
    </c:if>

    <c:if test="${facet.name eq 'compound.hasNMR'}">
        <c:set var="classSet" value="false"/>
        <input type="checkbox"
               name="${facet.name}"
               value="T"
        <c:forEach var="line" items="${facet.lines}">
        <c:if test='${line.checked}'>
               <c:if test="${line.value eq 'T' && classSet eq 'false' }">CHECKED
            <c:set var="classSet" value="true"/>
        </c:if>
        </c:if>
        </c:forEach>
               onclick="this.form.submit();">
            <span class="icon2-NMRLogo icon2-biggerFonts" data-icon="*"
                  title="NMR" style="font-size: 1em;"></span> NMR
        <br/>
    </c:if>

    <c:if test="${facet.name eq 'compound.hasMS'}">
        <c:set var="classSet" value="false"/>
        <input type="checkbox"
               name="${facet.name}"
               value="T"
        <c:forEach var="line" items="${facet.lines}">
        <c:if test='${line.checked}'>
               <c:if test="${line.value eq 'T' && classSet eq 'false' }">CHECKED
            <c:set var="classSet" value="true"/>
        </c:if>
        </c:if>
        </c:forEach>
               onclick="this.form.submit();">
        <span id="compound-es-filter" class="icon2-MSLogo icon2-biggerFonts" data-icon=")"
                                                       title="MS" style="font-size: 1em;"></span> MS
    </c:if>
</c:forEach>

