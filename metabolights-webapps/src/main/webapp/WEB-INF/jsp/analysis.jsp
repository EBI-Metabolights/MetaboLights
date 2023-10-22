<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 2014-Dec-05
  ~ Modified by:   kenneth
  ~
  ~ Copyright 2014 EMBL - European Bioinformatics Institute
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~      http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
  --%>
<div class="row">
    <div class="col-md-12">
        <h3>Integrated Analysis Tools</h3>
        <p><small>*We are currently developing this section</small></p>
        <br>
        <div class="row">
            <div class="col-md-6">
                <div class="panel nbr panel-default">
                    <div class="panel-body">
                        <h3 class="nmt"><a target="_blank" href="${pageContext.request.contextPath}/metaboanalyst"></a></h3>
                        <p style="font-weight: normal">
                            <a href="http://www.ebi.ac.uk${pageContext.request.contextPath}/metaboanalyst/" target="_blank"><b>MetaboAnalyst</b></a> is a user-friendly, comprehensive web-based analytical pipeline for high-throughput metabolomics studies. Our version of MetaboAnalyst 3.0 is coupled with EBI's in-house R Cloud to faciliate
                            compute intensive data analysis.<br><br><br><br>
                        </p>
                    </div>
                    <div class="panel-footer">
                        <p><b>References: </b></p>
                        <p class="references"><a target="_blank" href="http://www.ncbi.nlm.nih.gov/pubmed/25897128">Xia, J., Sinelnikov, I., Han, B., and Wishart, D.S. (2015). MetaboAnalyst 3.0 - making metabolomics more meaningful . Nucl. Acids Res. (DOI: 10.1093/nar/gkv380). </a><br></p>
                        <p class="references"><a target="_blank" href="http://www.ncbi.nlm.nih.gov/pubmed/22553367">Xia, J., Mandal, R., Sinelnikov, I., Broadhurst, D., and Wishart, D.S. (2012). MetaboAnalyst 2.0 - a comprehensive server for metabolomic data analysis . Nucl. Acids Res. 40, W127-W133.</a><br></p>
                        <p class="references"><a target="_blank" href="http://www.ncbi.nlm.nih.gov/pubmed/19429898">Xia, J., Psychogios, N., Young, N. and Wishart, D.S. (2009). MetaboAnalyst: a web server for metabolomic data analysis and interpretation. Nucl. Acids Res. 37, W652-660</a></p>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="panel nbr panel-default">
                    <div class="panel-body">
                        <h3 class="nmt"><a target="_blank" href="${pageContext.request.contextPath}/lipidhome"></a></h3>
                        <p style="font-weight: normal">
                            <a href="http://www.ebi.ac.uk${pageContext.request.contextPath}/lipidhome/" target="_blank"><b>LipidHome</b></a> is a database of theoretical lipid structures derived from a seed set of lipid sub classes and some lipid chemical space constraints agreed upon with experts in the field. Lipids are organised according to a combination of the LIPID MAPS ontology and the recent publication by the LipidomicNet consortium.
                        </p>
                    </div>
                    <div class="panel-footer">
                        <p><b>References: </b></p>
                        <p class="references"><a href="http://www.ncbi.nlm.nih.gov/pubmed/19098281">Fahy, E., Subramaniam, S., Murphy, R. C., Nishijima, M., Raetz, C. R. H., Shimizu, T., et al. (2009). Update of the LIPID MAPS comprehensive classification system for lipids. Journal of Lipid Research, 50 Suppl, S9–14.</a></p>
                        <p class="references"><a href="http://www.ncbi.nlm.nih.gov/pubmed/23667450">Foster, J. M., Moreno, P., Fabregat, A., Hermjakob, H., Steinbeck, C., Apweiler, R., et al. (2013). LipidHome: a database of theoretical lipids optimized for high throughput mass spectrometry lipidomics. PLoS ONE, 8(5), e61951.</a></p>
                        <p class="references"><a href="http://www.ncbi.nlm.nih.gov/pubmed/23549332">Liebisch G, Vizcaíno JA, Koefeler H, Troztmueller M, Griffiths WJ, Schmitz G, Spener F, Wakelam MJO (2013). Shorthand notation for lipid structures derived from mass spectrometry. J Lipid Res, 54(6):1523-30.</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<hr>
<div class="alert alert-warning nbr">
    <h4 class="nmt"><a href="parallelCoordinates">Study Factor Distribution</a>&nbsp;<sup><small>BETA*</small></sup></h4>
    <p>Parallel coordinates are a common way of visualizing high-dimensional study factors / analysing multivariate study data.<br>
        <i><small>* may be unstable</small></i>
    </p>
</div>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
