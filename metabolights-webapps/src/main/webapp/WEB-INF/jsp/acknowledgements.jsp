<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 7/1/14 9:28 AM
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

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">

<div class="container">
    <h2 class="text-center">Acknowledgements</h2>
    <h4 class="text-center">MetaboLights use the following resources/services and software components</h4>
    <div>&nbsp;</div>
    <div class="col-md-12">
        <div class="col-md-10 col-md-offset-1">
            <div class="col-md-2">
                <a href="http://www.bml-nmr.org/" target="_blank"><img src="${pageContext.request.contextPath}/img/acknowledgements/logo3.png" class="img-responsive"></a>
            </div>
            <div class="col-md-2">
                <a href="http://www.bmrb.wisc.edu/metabolomics/" target="_blank"><img src="${pageContext.request.contextPath}/img/acknowledgements/logo1.png" class="img-responsive"></a>
            </div>
            <div class="col-md-2">
                <a href="http://www.massbank.jp/" target="_blank"><img src="${pageContext.request.contextPath}/img/acknowledgements/logo2.png" class="img-responsive"></a>
            </div>
            <div class="col-md-2">
                <a href="http://www.metaboanalyst.ca/" target="_blank"><img src="${pageContext.request.contextPath}/img/acknowledgements/logo4.png" class="img-responsive"></a>
            </div>
            <div class="col-md-2">
                <a href="http://www.reactome.org/" target="_blank"><img src="${pageContext.request.contextPath}/img/acknowledgements/logo5.png" class="img-responsive"></a>
            </div>

            <div class="col-md-2">
                <a href="http://3dmol.csb.pitt.edu/" target="_blank"><img src="${pageContext.request.contextPath}/img/acknowledgements/logo6.png" class="img-responsive"></a>
            </div>

            <div class="col-md-2">
                <a href="https://bitbucket.org/sbeisken/specktackle/wiki/Home" target="_blank"><img src="${pageContext.request.contextPath}/img/acknowledgements/logo7.png" class="img-responsive"></a>
            </div>

            <div class="col-md-2">
                <a href="http://wikipathways.org/" target="_blank"><img src="${pageContext.request.contextPath}/img/acknowledgements/logo8.png" class="img-responsive"></a>
            </div>

            <div class="col-md-2">
                <a href="http://europepmc.org/" target="_blank"><img src="${pageContext.request.contextPath}/img/acknowledgements/logo19.png" class="img-responsive"></a>
            </div>

            <div class="col-md-2">
                <a href="http://www.genome.jp/kegg/pathway.html" target="_blank"><img src="${pageContext.request.contextPath}/img/acknowledgements/logo20.png" class="img-responsive"></a>
            </div>

            <div class="col-md-2">
                <a href="http://www.ebi.ac.uk/chebi/" target="_blank"><img src="${pageContext.request.contextPath}/img/acknowledgements/logo21.png" class="img-responsive"></a>
            </div>

            <div class="col-md-2">
                <a href="cts.fiehnlab.ucdavis.edu" target="_blank"><img src="${pageContext.request.contextPath}/img/acknowledgements/logo22.png" class="img-responsive"></a>
            </div>

            <div class="col-md-2">
                <a href="https://cactus.nci.nih.gov/chemical/structure" target="_blank"><img src="${pageContext.request.contextPath}/img/acknowledgements/logo23.png" class="img-responsive"></a>
            </div>

            <div class="col-md-2">
                <a href="https://www.jetbrains.com/idea/" target="_blank"><img src="${pageContext.request.contextPath}/img/acknowledgements/logo9.png" class="img-responsive"></a>
            </div>

            <div class="col-md-2">
                <a href="http://sbw.kgi.edu/sbwWiki/sbw/autolayout" target="_blank"><img src="${pageContext.request.contextPath}/img/acknowledgements/logo10.png" class="img-responsive"></a>
            </div>

            <div class="col-md-2">
                <a href="http://pivotaltracker.com/" target="_blank"><img src="${pageContext.request.contextPath}/img/acknowledgements/logo11.png" class="img-responsive"></a>
            </div>

            <div class="col-md-2">
                <a href="https://clipboardjs.com/" target="_blank"><img src="${pageContext.request.contextPath}/img/acknowledgements/logo12.png" class="img-responsive"></a>
            </div>

            <div class="col-md-2">
                <a href="http://fontawesome.io/" target="_blank"><img src="${pageContext.request.contextPath}/img/acknowledgements/logo13.png" class="img-responsive"></a>
            </div>

            <div class="col-md-2">
                <a href="https://disqus.com/" target="_blank"><img src="${pageContext.request.contextPath}/img/acknowledgements/logo14.png" class="img-responsive"></a>
            </div>

            <div class="col-md-2">
                <a href="https://vuejs.org/" target="_blank"><img src="${pageContext.request.contextPath}/img/acknowledgements/logo15.png" class="img-responsive"></a>
            </div>

            <div class="col-md-2">
                <a href="https://jquery.com/" target="_blank"><img src="${pageContext.request.contextPath}/img/acknowledgements/logo16.png" class="img-responsive"></a>
            </div>

            <div class="col-md-2">
                <a href="http://getbootstrap.com/" target="_blank"><img src="${pageContext.request.contextPath}/img/acknowledgements/logo17.png" class="img-responsive"></a>
            </div>
            <div class="col-md-2">
                <a href="http://github.com/" target="_blank"><img src="${pageContext.request.contextPath}/img/acknowledgements/logo18.png" class="img-responsive"></a>
            </div>

        </div>
    </div>
    <div>&nbsp;</div>
    <div>&nbsp;</div>
    <div class="col-md-10 col-md-offset-1">
        <div class="well">
            <h4>MetaboLights would especially like to thank the following people</h4>
            <ul id="ackn">
                <li><a href="http://isatab.sourceforge.net/support.html">The ISA team at Oxford e-Research Centre</a></li>
                <ul>
                    <li><a href="http://www.oerc.ox.ac.uk/people/philippe-rocca-serra">Particularly Dr Philippe Rocca-Serra</a></li>
                </ul>
                <li><a href="http://www.ipb-halle.de/en/research/stress-and-developmental-biology/research-groups/bioinformatics-mass-spectrometry/">Dr. Steffen Neumann at IPB-Halle</a></li>
                <li><a href="https://uofa.ualberta.ca/biological-sciences/faculty-and-staff/academic-staff/david-wishart">Dr. David Wishart at University of Alberta</a></li>
                <li><a href="http://www.mcgill.ca/parasitology/faculty/xia">Dr. Jeff Xia at McGill University</a></li>
            </ul>
        </div>
    </div>
</div>



