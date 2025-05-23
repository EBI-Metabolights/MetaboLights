<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

    <%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 2014-Dec-17
  ~ Modified by:   kenneth
  ~
  ~ Copyright 2015 EMBL - European Bioinformatics Institute
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~      http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
  --%>

<div class="col-md-12">
    <div class="row">
        <h3 class="heading"><spring:message code="msg.metabolights" /></h3>
    </div>
</div>
<div class="row">
    <div class="col-md-4">
        <p class="description">
            <spring:message code="msg.metabolightsAbout1" />
            <%--<spring:message code="msg.metabolightsAbout2" />--%>
        </p>
    </div>
    <div class="col-md-4">
        <p class="description"><spring:message code="msg.metabolightsAbout3" /></p>
    </div>
    <div class="col-md-4">
        <p class="description"><spring:message code="msg.metabolightsAbout4" /></p>
    </div>
</div>
<div class="row">
    <div>&nbsp;</div>
</div>
<div class="row">
    <div class="col-md-8">
        <div class="well nbr">
            <b>
                We are always looking at ways to improve MetaboLights. Please send your feedback in the form <a
                    href="contact">here</a> or email us directly at <a href="mailto:metabolights-help@ebi.ac.uk ">metabolights-help@ebi.ac.uk</a>
            </b><br><br>
            <p><a href="<spring:url value="statistics"/>"><spring:message code="label.stats"/></a></p>
            <p><a href="<spring:url value="advisoryboard"/>"><spring:message code="label.sab"/></a></p>
            <%--<a href="<spring:url value="acknowledgements"/>">Acknowledgements</a><br>--%>
        </div>
    </div>
    <div class="col-md-4">
        <div class="well nbr">
            <p><strong>Follow us on </strong><br><br>
                <span class="bigfont">
                    <a href="https://twitter.com/metabolights" class="icon icon-socialmedia" data-icon="T"></a>
                </span>
            </p>
            <br><br>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel nbr panel-success">
            <div class="panel-heading">Citing MetaboLights</div>
            <div class="panel-body">
                <span class="pubauthor">Ozgur Yurekten, Thomas Payne, Noemi Tejera, Felix Xavier Amaladoss, Callum Martin, Mark Williams, Claire O’Donovan.</span><br/>
                <a target="_blank" href="https://doi.org/10.1093/nar/gkad1045" class="pubtitle">MetaboLights: open data repository for metabolomics</a><br/><br/>
                <span class="pubjournal">Nucleic Acids Research, 2023, gkad1045, doi:10.1093/nar/gkad1045</span>
            </div>
        </div>
    </div>
    <div class="col-md-12">
        <div class="panel nbr panel-warning">
            <div class="panel-heading">
                Licensing
            </div>
            <div class="panel-body">
                All data is governed by the EMBL-EBI's <a href="http://www.ebi.ac.uk/about/terms-of-use" target="_blank">Terms of use</a> and aligned to EMBL-EBI’s <a href="https://www.ebi.ac.uk/long-term-data-preservation" target="_blank">Long-term data preservation</a>. All <a href="https://github.com/EBI-Metabolights" target="_blank">code</a> is licensed under <a href="http://www.apache.org/licenses/LICENSE-2.0" target="_blank">Apache 2.0</a>.
            </div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="col-md-6">
            <a class="noLine"><img src="img/phenomenal.png" style = "height: 60px;" alt="PhenoMeNal"/></a>
            <div class="clearfix">&nbsp;</div>
            <p>PhenoMeNal (Phenome and Metabolome aNalysis) is a comprehensive and standardised e-infrastructure that supports the data processing and analysis pipelines for molecular phenotype data generated by metabolomics applications.</p>
            <%--<a href="http://www.cosmos-fp7.eu/"><img src="img/cosmosSmall_0.png" style = "height: 60px;" alt="COSMOS"/></a>--%>
            <%--<p><spring:message code="msg.about.cosmos"/></p>--%>

        </div>
        <div class="col-md-6">
            <a class="noLine" href="<spring:message code="url.isatools"/>"><img src="img/softwaresuitelogo2.png" style = "height: 60px;" alt="isatab"/></a>
            <p><spring:message code="msg.metabolightsAbout6" /></p>
        </div>
    </div>
</div>
<%--<hr>--%>
<%--<div class="row">--%>
    <%--<div class="col-md-12">--%>
        <%--<div class="col-md-12">--%>
            <%--<p><spring:message code="msg.metabolightsAbout10" /></p>--%>
            <%--<a class="noLine" href="http://github.com/ISA-tools/OntoMaton" target="_blank"><img src="http://isatools.files.wordpress.com/2012/07/ontomaton.png?w=250"/></a>--%>
            <%--<a class="noLine" href="<spring:message code="url.isatools"/>" target="_blank"><img src="http://isatab.sourceforge.net/assets/img/tools/tools-table-images/isacreator.png"/></a>--%>
            <%--<p><spring:message code="msg.metabolightsAbout15" /></p>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>
<hr>
<div class="row" id="support-journals" href="#publishers">
    <div class="col-md-12">
        <div class="col-md-12">
                <p><spring:message code="msg.metaboLightsAbout17"/></p><br>
                <div>
                    <div class="col-md-2 vc" style="padding: 0">
                        <a class="noLine" href="http://www.nature.com/sdata/policies/repositories"><img src="img/support-journals/scientificData.png" class="img-responsive" style="width: 100%; height: auto" alt="Nature Scientific Data"/></a>
                    </div>
                    <div class="col-md-2 vc" style="padding: 0">
                        <a class="noLine" href="https://www.biomedcentral.com/"><img src="img/support-journals/biomedCentral.png" class="img-responsive" style="width: 100%; height: auto" alt="BioMed Central"/></a>
                    </div>
                    <div class="col-md-2 vc" style="padding: 0">
                        <a class="noLine" href="http://journals.plos.org/plosbiology/s/data-availability#loc-recommended-repositories"><img src="img/support-journals/Plos-biology.png" class="img-responsive" style="width: 100%; height: auto" alt="PLOS Biology"/></a>
                    </div>
                    <div class="col-md-2 vc" style="padding: 0">
                        <a class="noLine" href="http://msb.embopress.org/authorguide#datadeposition"><img src="img/support-journals/EMBO-press.png" class="img-responsive" style="width: 100%; height: auto" alt="EMBO press"/></a>
                    </div>
                    <div class="col-md-2 vc" style="padding: 0">
                        <a class="noLine" href="https://f1000research.com/for-authors/data-guidelines"><img src="img/support-journals/F1000-research.png" class="img-responsive" style="width: 100%; height: auto" alt="F1000 Research"/></a>
                    </div>
                    <div class="col-md-2 vc" style="padding: 0">
                        <a class="noLine" href="http://link.springer.com/journal/11306"><img src="img/support-journals/metabolomics.png" class="img-responsive" style="width: 100%; height: auto" alt="Springer Link - Metabolomics"/></a>
                    </div>
                    <div class="col-md-2 vc" style="padding: 0">
                        <a class="noLine" href="http://journal.frontiersin.org/journal/molecular-biosciences/section/metabolomics#author-guidelines"><img src="img/support-journals/frontiers.png" class="img-responsive" style="width: 100%; height: auto" alt="Frontiers"/></a>
                    </div>
                    <div class="col-md-2 vc" style="padding: 0">
                        <a class="noLine" href="http://www.mdpi.com/journal/metabolites/instructions#suppmaterials"><img src="img/support-journals/metabolites.png" class="img-responsive" style="width: 100%; height: auto" alt="Metabolites"/></a>
                    </div>
                    <div class="col-md-2 vc" style="padding: 0">
                        <a class="noLine" href="http://www.mdpi.com/journal/data/instructions#suppmaterials"><img src="img/support-journals/data.png" class="img-responsive" style="width: 100%; height: auto" alt="data"/></a>
                    </div>
                    <div class="col-md-2 vc" style="padding: 0">
                        <a class="noLine" href="https://wellcomeopenresearch.org/for-authors/data-guidelines"><img src="img/support-journals/wellcome.png" class="img-responsive" style="width: 100%; height: auto" alt="Wellcome Trust Open Research"/></a>
                    </div>
                </div>
                <div class="clearfix">&nbsp;</div>
                <div class="clearfix">&nbsp;</div>
                <p><spring:message code="msg.metaboLightsAbout18"/></p>
            </div>
    </div>
</div>
<hr>
<div class="row">
    <div class="col-md-12">
        <div class="col-md-12">
            <%--<a class="noLine" href="http://www.bbsrc.ac.uk/research/grants-search/AwardDetails/?FundingReference=BB%2fL024152%2f1"><img src="img/bbsrcLarge.png" alt="BBSCR"/></a>--%>
            <%--&nbsp;&nbsp;&nbsp; <img src="img/EMBL_EBI_logo_180pixels_RGB.jpg" alt="EMBL"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="http://www.metabolomics.bioc.cam.ac.uk/metabolomics/"><img src="img/camLogo.png" alt="UC"/></a>--%>
            <%--<br/>--%>
            <p>
                The Metabolomics team's activities are supported by the <b>European Molecular Biology Laboratory (EMBL), Medical Research Council grant MR/L01632X/1, BBSRC grant BB/M027635/1, Wellcome Trust grant 202952/Z/16/Z, National Institutes of Health grant 1U54GM114833-01 and EC H2020 grants 634402 and 654241</b>.
                <br><br>
                Past funding includes BBSRC grants <b>BB/L024152/1, BB/I000933/1 and BB/N02342/1 and EC H2020 grant 654021</b>.
            </p>
        </div>
    </div>
</div>
<br><br>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
