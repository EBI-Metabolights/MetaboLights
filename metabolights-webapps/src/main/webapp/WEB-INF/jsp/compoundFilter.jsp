<div class="btn-group" data-toggle="buttons-checkbox">
    <c:forEach var="facet" items="${facets}">
        <c:if test="${facet.name eq 'compound.hasSpecies'}">
            <c:set var="classSet" value="false"/>
            <label class="btn btn-default btn-xs">
                <input type="checkbox"
                       class="<c:forEach var="line" items="${facet.lines}">
                                            <c:if test='${line.checked}'>
                                            <c:if test="${line.value eq 'T' && classSet eq 'false' }">active
                                            <c:set var="classSet" value="true"/>
                                            </c:if>
                                            </c:if>
                                            </c:forEach>
                                            " data-toggle="button" name="${facet.name}"
                       value="T"
                <c:if test="${classSet eq 'true'}">
                       CHECKED
                </c:if>
                >
                                            <span class="icon icon-species forSpecies" data-icon="R"
                                                  title="Species"></span>
            </label>
        </c:if>
        <c:if test="${facet.name eq 'compound.hasPathways'}">
            <c:set var="classSet" value="false"/>
            <label class="btn btn-default btn-xs">
                <input type="checkbox"
                       class="<c:forEach var="line" items="${facet.lines}">
                                            <c:if test='${line.checked}'>
                                            <c:if test="${line.value eq 'T' && classSet eq 'false' }">active
                                            <c:set var="classSet" value="true"/>
                                            </c:if>
                                            </c:if>
                                            </c:forEach>
                                            " data-toggle="button" name="${facet.name}"
                       value="T"
                <c:if test="${classSet eq 'true'}">
                       CHECKED
                </c:if>
                >
                                                    <span class="icon icon-conceptual forConceptual" data-icon="y"
                                                          title="Pathways"></span>
            </label>
        </c:if>

        <c:if test="${facet.name eq 'compound.hasReactions'}">
            <c:set var="classSet" value="false"/>
            <label class="btn btn-default btn-xs">
                <input type="checkbox"
                       class="<c:forEach var="line" items="${facet.lines}">
                                            <c:if test='${line.checked}'>
                                            <c:if test="${line.value eq 'T' && classSet eq 'false' }">active
                                            <c:set var="classSet" value="true"/>
                                            </c:if>
                                            </c:if>
                                            </c:forEach>
                                            " data-toggle="button" name="${facet.name}"
                       value="T"
                <c:if test="${classSet eq 'true'}">
                       CHECKED
                </c:if>
                >
                                                    <span class="icon icon-chemistry forChemistry" data-icon="R"
                                                          title="Reactions"></span>
            </label>
        </c:if>

        <c:if test="${facet.name eq 'compound.hasNMR'}">
            <c:set var="classSet" value="false"/>
            <label class="btn btn-default btn-xs">
                <input type="checkbox"
                       class="<c:forEach var="line" items="${facet.lines}">
                                            <c:if test='${line.checked}'>
                                            <c:if test="${line.value eq 'T' && classSet eq 'false' }">active
                                            <c:set var="classSet" value="true"/>
                                            </c:if>
                                            </c:if>
                                            </c:forEach>
                                            " data-toggle="button" name="${facet.name}"
                       value="T"
                <c:if test="${classSet eq 'true'}">
                       CHECKED
                </c:if>
                >
                                                   <span class="icon2-NMRLogo icon2-biggerFonts" data-icon="*"
                                                         title="NMR"></span>
            </label>
        </c:if>

        <c:if test="${facet.name eq 'compound.hasMS'}">
            <c:set var="classSet" value="false"/>
            <label class="btn btn-default btn-xs">
                <input type="checkbox"
                       class="<c:forEach var="line" items="${facet.lines}">
                                            <c:if test='${line.checked}'>
                                            <c:if test="${line.value eq 'T' && classSet eq 'false' }">active
                                            <c:set var="classSet" value="true"/>
                                            </c:if>
                                            </c:if>
                                            </c:forEach>
                                            " data-toggle="button" name="${facet.name}"
                       value="T"
                <c:if test="${classSet eq 'true'}">
                       CHECKED
                </c:if>
                >
                                                 <span class="icon2-MSLogo icon2-biggerFonts" data-icon=")"
                                                       title="MS"></span>
            </label>
        </c:if>
    </c:forEach>

</div>