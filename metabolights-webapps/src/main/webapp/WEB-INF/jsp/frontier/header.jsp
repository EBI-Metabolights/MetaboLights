<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 11/1/12 10:09 AM
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

<%--${frontierheader}--%>
<div class="masthead-black-bar masthead-black-bbar hidden-sm hidden-xs">
    <nav class="navbar navbar-inverse" style="z-index: 10000">
        <div class="container">
            <ul class="nav navbar-nav">
                <li class="services"><a href="//www.ebi.ac.uk/services">Services</a></li>
                <li class="research"><a href="//www.ebi.ac.uk/research">Research</a></li>
                <li class="training"><a href="//www.ebi.ac.uk/training">Training</a></li>
                <li class="about"><a href="//www.ebi.ac.uk/about">About us</a></li>
                <li><a href="//www.ebi.ac.uk"><i class="icon icon-generic" data-icon="H"></i> EMBL-EBI</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown dropdown-large">
                    <a class="dropdown-toggle button" data-toggle="dropdown" href="#">
                       <span class="pull-right"> Hinxton
                            <span class="caret"></span>
                       </span>
                    </a>
                    <div class="dropdown-menu dropdown-menu-large row dark">
                        <div class="col-md-9">
                            <div class="row">
                                <div class="col-md-8">
                                    <div class="row">
                                        <p>EMBL-EBI in Hinxton is one of six EMBL locations across europe.</p>
                                        <a href="//www.ebi.ac.uk/about" class="small readmore np">More about EMBL-EBI</a>
                                        <h6>Connect to another EMBL location</h6>
                                    </div>
                                </div>
                                    <div class="col-md-5 np">
                                        <div class="columns small-5 padding-bottom-medium">
                                            <a class="np" href="http://www.embl.de/">Heidelberg</a>
                                            <div class="small">Main laboratory</div>
                                        </div>
                                        <div class="columns small-5 padding-bottom-medium">
                                            <a class="np" href="http://www.embl.fr/">Grenoble</a>
                                            <div class="small">Structural biology</div>
                                        </div>
                                        <div class="columns small-5 padding-bottom-medium">
                                            <a class="np" href="http://www.embl.it/">Monterotondo</a>
                                            <div class="small">Mouse biology</div>
                                        </div>
                                    </div>
                                    <div class="col-md-7 np">
                                        <div class="columns small-7 padding-bottom-medium">
                                            <a class="np" href="http://www.embl-barcelona.es/">Barcelona</a>
                                            <div class="small">Tissue biology and disease <br> modelling</div>
                                        </div>
                                        <div class="columns small-7 padding-bottom-medium">
                                            <a class="np" href="http://www.embl-hamburg.de/">Hamburg</a>
                                            <div class="small">Structural biology</div>
                                        </div>
                                        <div class="columns small-7 padding-bottom-medium">
                                            <a class="np readmore" href="http://embl.org/" class="readmore">More about EMBL</a>
                                        </div>
                                    </div>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </nav>
</div>
<div  class="masthead-black-bar hidden-lg hidden-md">
    <div class="container">
        <div class="row">
            <a href="//www.ebi.ac.uk">
                <img class="ebi-logo" src="//www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.2/images/logos/EMBL-EBI/EMBL_EBI_Logo_white.svg">
            </a>
            <span class="pull-right">
                <ul class="nav navbar-nav">
                    <li class="services"><a href="//www.ebi.ac.uk/services"><span class="clearfix">
                        Services
                    </span></a></li>
                    <li class="research"><a href="//www.ebi.ac.uk/research"><span class="clearfix">Research</span></a></li>
                    <li class="training"><a href="//www.ebi.ac.uk/training"><span class="clearfix">Training</span></a></li>
                    <li class="about"><a href="//www.ebi.ac.uk/about"><span class="clearfix">About us</span></a></li>
                </ul>
            </span>
        </div>
    </div>
</div>
<div class="ml-header clearfix">
    <div class="container">
        <div class="ml-header-section-top">
            <div class="col-md-12">
                <div class="row">
                    <div class="col-md-7 col-sm-6">
                        <div class="alpha logo-title" id="local-title">
                            <a id="mlLogo" class="ml-logo" href="index" title="Back to MetaboLights homepage">
                                <img src="/metabolights/img/MetaboLightsLogo.png" alt="MetaboLights" width="64" height="64" />MetaboLights
                            </a>
                        </div>
                    </div>
                    <div class="col-md-5 col-sm-6">
                        <form id="local-search" name="local-search" action="/metabolights/search" method="post">
                            <div class="input-group">
                                <input type="text" name="freeTextQuery" id="local-searchbox" class="form-control">
                                <span class="input-group-btn">
                                    <input type="submit" name="submit" value="Search" class="btn btn-secondary">
                                </span>
                            </div>
                            <small class="hidden-xs">
                                <span class="examples">Examples: <a href="/metabolights/search?freeTextQuery=alanine">Alanine</a>, <a href="/metabolights/search?freeTextQuery=Homo sapiens">Homo sapiens</a>, <a href="/metabolights/search?freeTextQuery=urine">Urine</a>, <a href="/metabolights/search?freeTextQuery=MTBLS1">MTBLS1</a>
                                <%--<br><a href="/metabolights/advancedsearch" target="_blank">Advanced Search</a></span>--%>
                            </small>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="ml-header-section-bottom">
                <nav class="navbar navbar-dark">
                    <!-- Brand and toggle get grouped for better mobile display -->
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                            <span class="icon icon-functional" data-icon="M"> Menu</span>
                        </button>
                    </div>

                    <!-- Collect the nav links, forms, and other content for toggling -->
                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                            <span class="hidden-sm hidden-md">
                                <ul class="nav navbar-nav cnav">
                                <li><a href="/metabolights/index">Home</a></li>
                                <li><a href="/metabolights/studies">Browse Studies</a></li>
                                <li><a href="/metabolights/compounds">Browse Compounds</a></li>
                                <li><a href="/metabolights/species">Browse Species</a></li>
                                <%--<li><a href="/metabolights/analysis">Analysis</a></li>--%>
                                <li><a href="/metabolights/download">Download</a></li>
                                <li><a href="/metabolights/guides">Help</a></li>
                                <li><a href="/metabolights/contact">Give us feedback</a></li>
                                <li><a href="/metabolights/about">About</a></li>
                                    </ul>
                                <ul class="nav navbar-nav cnav navbar-right">
                                    <li><a href="/metabolights/editor">Submit Study</a></li>
                                    <li><a href="/metabolights/login">Login</a></li>
                                </ul>
                            </span>
                            <span class="hidden-xs hidden-lg">
                                <ul class="nav navbar-nav cnav">
                                    <li><a href="/metabolights/index">Home</a></li>
                                    <li><a href="/metabolights/studies">Browse Studies</a></li>
                                    <li><a href="/metabolights/compounds">Browse Compounds</a></li>
                                    <li><a href="/metabolights/species">Browse Species</a></li>
                                    <li class="dropdown">
                                      <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">More <span class="caret"></span></a>
                                      <ul class="dropdown-menu">
                                        <%--<li><a href="/metabolights/analysis">Analysis</a></li>--%>
                                        <li><a href="/metabolights/download">Download</a></li>
                                        <li><a href="/metabolights/guides">Help</a></li>
                                        <li><a href="/metabolights/contact">Give us feedback</a></li>
                                        <li><a href="/metabolights/about">About</a></li>
                                      </ul>
                                    </li>
                                </ul>
                                <ul class="nav navbar-nav cnav navbar-right">
                                    <li><a href="/metabolights/editor">Submit Study</a></li>
                                    <li><a href="/metabolights/login">Login</a></li>
                                </ul>
                            </span>
                    </div><!-- /.navbar-collapse -->
                </nav>
                <script>
                    $('[href="analysis"]').hide();
                </script>
            <%--<nav class="navbar navbar-dark">--%>
                <%--<ul class="nav navbar-nav">--%>

                <%--</ul>--%>
                <%--<ul class="nav navbar-nav navbar-right">--%>
                    <%--<li><a href="//www.ebi.ac.uk/about">Submit Study</a></li>--%>
                    <%--<li><a href="//www.ebi.ac.uk/about">Login</a></li>--%>
                <%--</ul>--%>
            <%--</nav>--%>
        </div>
    </div>
</div>
<script>
    var subDomain = window.location.host.split('.')[0]
    if(subDomain != 'www'){
        var brand = document.getElementById("mlLogo")
        brand.innerHTML = "MetaboLights DEV";
        brand.style.color = "yellow";
        brand.style.fontWeight = "300";
    }
</script>