<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--${frontierfooter}--%>
<div id="elixir-banner" data-color="grey" data-name="This service" data-description="MetaboLights is an ELIXIR Recommended Deposition Database" data-more-information-link="https://www.elixir-europe.org/about-us/who-we-are/nodes/embl-ebi" data-use-basic-styles="true"></div>
<script defer="defer" src="https://ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/js/elixirBanner.js"></script>
<div id="data-protection-message-configuration"
     data-message='This website requires cookies, and the limited processing of your personal data in order to function. By using the site you are agreeing to this as outlined in our <a target="_blank" href="https://www.ebi.ac.uk/data-protection/privacy-notice/metaboflow" class="white-color">Privacy Notice</a>.'
     data-service-id="MetaboFlow" data-data-protection-version="0.1"></div>
<script>
    var localFrameworkVersion = 'other';
    var newDataProtectionNotificationBanner = document.createElement('script');
    newDataProtectionNotificationBanner.src = 'https://ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/js/ebi-global-includes/script/5_ebiFrameworkNotificationBanner.js?legacyRequest='+localFrameworkVersion;
    document.head.appendChild(newDataProtectionNotificationBanner);
    newDataProtectionNotificationBanner.onload = function() {
        ebiFrameworkRunDataProtectionBanner(); // invoke the banner
    };
</script>
<footer class='clearfix'>
    <div class= "global-footer col-md-12 ">
        <div class="container">
            <nav id= "global-nav-expanded " class= "global-nav-expanded row ">
                <div class= "col-md-2 col-sm-6 ">
                    <a href= "//www.ebi.ac.uk " title= "EMBL-EBI ">
                        <span class= "ebi-logo "></span>
                    </a>
                </div>
                <div class= "col-md-2 col-sm-6 ">
                    <h5 class= "services ">
                        <a class= "services-color " href= "//www.ebi.ac.uk/services ">Services</a>
                    </h5>
                    <ul>
                        <li class= "first "><a href= "//www.ebi.ac.uk/services ">By topic</a></li>
                        <li><a href= "//www.ebi.ac.uk/services/all ">By name (A-Z)</a></li>
                        <li class= "last "><a href= "//www.ebi.ac.uk/support ">Help &amp; Support</a></li>
                    </ul>
                </div>
                <div class= "col-md-2 col-sm-6 ">
                    <h5 class= "research ">
                        <a class= "research-color " href= "//www.ebi.ac.uk/research ">Research</a>
                    </h5>
                    <ul>
                        <li>
                            <a href= "//www.ebi.ac.uk/research/publications ">Publications</a>
                        </li>
                        <li>
                            <a href= "//www.ebi.ac.uk/research/groups ">Research groups</a>
                        </li>
                        <li class= "last ">
                            <a href= "//www.ebi.ac.uk/research/postdocs ">Postdocs</a> &amp; <a href= "//www.ebi.ac.uk/research/eipp ">PhDs</a>
                        </li>
                    </ul>
                </div>
                <div class= "col-md-2 col-sm-6 ">
                    <h5 class= "training ">
                        <a class= "training-color " href= "//www.ebi.ac.uk/training ">Training</a>
                    </h5>
                    <ul>
                        <li><a href= "//www.ebi.ac.uk/training/handson ">Train at EBI</a></li>
                        <li><a href= "//www.ebi.ac.uk/training/roadshow ">Train outside EBI</a></li>
                        <li><a href= "//www.ebi.ac.uk/training/online ">Train online</a></li>
                        <li class= "last "><a href= "//www.ebi.ac.uk/training/contact-us ">Contact organisers</a></li>
                    </ul>
                </div>
                <div class= "col-md-2 col-sm-6 ">
                    <h5 class= "industry "><a class= "industry-color " href= "//www.ebi.ac.uk/industry ">Industry</a></h5>
                    <ul>
                        <li><a href= "//www.ebi.ac.uk/industry/private ">Members Area</a></li>
                        <li><a href= "//www.ebi.ac.uk/industry/workshops ">Workshops</a></li>
                        <li><a href= "//www.ebi.ac.uk/industry/sme-forum "><abbr title= "Small Medium Enterprise ">SME</abbr> Forum</a></li>
                        <li class= "last "><a href= "//www.ebi.ac.uk/industry/contact ">Contact Industry programme</a></li>
                    </ul>
                </div>
                <div class= "col-md-2 col-sm-6 ">
                    <h5 class= "about "><a class= "ebi-color " href= "//www.ebi.ac.uk/about ">About EMBL-EBI</a></h5>
                    <ul>
                        <li><a href= "//www.ebi.ac.uk/about/contact ">Contact us</a></li>
                        <li><a href= "//www.ebi.ac.uk/about/events ">Events</a></li>
                        <li><a href= "//www.ebi.ac.uk/about/jobs " title= "Jobs, postdocs, PhDs... ">Jobs</a></li>
                        <li class= "first "><a href= "//www.ebi.ac.uk/about/news ">News</a></li>
                        <li><a href= "//www.ebi.ac.uk/about/people ">People &amp; groups</a></li>
                    </ul>
                </div>
            </nav>
            <section id= "ebi-footer-meta " class= "ebi-footer-meta ">
                <p class= "address">EMBL-EBI, Wellcome Genome Campus, Hinxton, Cambridgeshire, CB10 1SD, UK. +44 (0)1223 49 44 44</p>
                <p class= "legal">Copyright © EMBL-EBI 2017 | EMBL-EBI is <a href= "http://www.embl.org/ ">part of the European Molecular Biology Laboratory</a> | <a href= "//www.ebi.ac.uk/about/terms-of-use ">Terms of use</a>
                    <a class= "readmore pull-right " href= "http://intranet.ebi.ac.uk ">Intranet</a></p>
            </section>

        </div>
    </div>
</footer>
<%--
~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
~ Cheminformatics and Metabolism group
~
~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
~
~ Last modified: 12/12/12 2:29 PM
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

<!-- JavaScript at the bottom for fast page loading -->
<!-- from http://wwwdev.ebi.ac.uk/web_guidelines/html/compliance/ebi-level2-boilerplate-blank-fluid.txt -->

<!-- Grab Google CDN's jQuery, with a protocol relative URL; fall back to local if offline -->
<!--
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="../js/libs/jquery-1.6.2.min.js"><\/script>')</script>
-->


<!-- Your custom JavaScript file scan go here... change names accordingly -->
<%--   <c:if test="${pageContext.request.serverName == 'www.ebi.ac.uk'}" >
	<script defer="defer" src="//www.ebi.ac.uk/web_guidelines/js/cookiebanner.js"></script>
  </c:if>
  <script defer="defer" src="//www.ebi.ac.uk/web_guidelines/js/foot.js"></script>
 --%>