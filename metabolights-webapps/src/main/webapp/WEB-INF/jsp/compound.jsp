<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/css/bootstrap-select.min.css">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/st.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/MetCompound.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/cssrl/biojs.Rheaction.css" type="text/css"/>
<link rel="stylesheet" type="text/css" href="https://cdn.rawgit.com/jmvillaveces/biojs-vis-keggviewer/master/dependencies/css/bootstrap-slider.css">

<noscript>
    <style type="text/css">
        .wrapper {display:none;}
    </style>
    <div class="container">
        <div>&nbsp;</div>
        <div class="noscriptmsg well">
            You don't have javascript enabled.  Please enable javascript and refresh the page !
        </div>
    </div>
</noscript>

<div id="content" class="grid_24">
    <div class="container-fluid">
        <div class="row">
            <div class="wrapper col">
                <div id="app">
                    <div class="col-md-3 image">
                        <div class="row met-img">
                            <div class="card">
                                <!-- Nav tabs -->
                                <ul class="nav nav-tabs" role="tablist">
                                    <li role="presentation" class="active"><a href="#2d" aria-controls="home" role="tab" data-toggle="tab">2D</a></li>
                                    <li role="presentation"><a href="#3d" aria-controls="profile" role="tab" data-toggle="tab">3D</a></li>
                                </ul>

                                <!-- Tab panes -->
                                <div class="tab-content" id="displayMol">
                                    <div role="tabpanel" class="tab-pane active" id="2d">
                                        <%--<h5>Structure</h5><br>--%>
                                        <img :src="mtblc.imageUrl" class="metabolite-image"/>
                                    </div>
                                    <div role="tabpanel" class="tab-pane" id="3d">
                                        <div id="3dDisplay" style="position: relative;"></div>
                                            <textarea style="display: none;" id="moldata_sdf">
                                                {{ mtblc.structure }}
                                            </textarea>
                                    </div>
                                    <span id='zoom'>
                                        <a data-toggle="modal" data-target="#zoomModal"><i class="fa fa-search-plus"></i></a>
                                    </span>
                                    <div class="modal fade" id="zoomModal" tabindex="-1" role="dialog" aria-labelledby="zoomModalLabel">
                                        <div class="modal-dialog zoom-dialog" role="document">
                                            <div class="modal-content">
                                                <div class="modal-body">
                                                    <div>
                                                        <!-- Nav tabs -->
                                                        <ul class="nav nav-tabs" role="tablist">
                                                            <li role="presentation" class="active"><a href="#zoom2d" aria-controls="home" role="tab" data-toggle="tab">2D</a></li>
                                                            <li role="presentation"><a href="#zoom3d" aria-controls="profile" role="tab" data-toggle="tab">3D</a></li>
                                                        </ul>
                                                        <div class="tab-content">
                                                            <div role="tabpanel" class="tab-pane active" id="zoom2d">
                                                                <%--<h5>Structure</h5><br>--%>
                                                                <img style="height: 400px; width: 450px; position: relative;" :src="mtblc.imageUrlLarge" class="metabolite-image"/>

                                                            </div>
                                                            <div role="tabpanel" class="tab-pane" id="zoom3d">
                                                                <div style="height: 400px; width: 400px; position: relative;" data-backgroundcolor="white" data-style="{&quot;stick&quot;:{}}" data-datatype="sdf" data-element="zoom_moldata_sdf" class="viewer_3Dmoljs"></div>
                                                                    <textarea style="display: none;" id="zoom_moldata_sdf">
                                                                        {{ mtblc.structure }}
                                                                    </textarea>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="text-center">
                                <span class="right">
                                        <div class="btn-group" role="group" aria-label="">
                                            <a target="_blank" class="btn btn-default btn-xs ml--clipboard" data-clipboard-text="{{ mtblc.structure }}">MOL</a>
                                            <a target="_blank" class="btn btn-default btn-xs ml--clipboard" data-clipboard-text="{{ mtblc.smiles }}">SMILES</a>
                                            <a target="_blank" class="btn btn-default btn-xs ml--clipboard" data-clipboard-text="{{ mtblc.inchiKey }}">InChIKey</a>
                                        </div>
                                </span>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-9">
                        <div class="banner">
                            <h2>{{ mtblc['name'] }}</h2>
                        </div>
                        <div class="mini--banner col-md-12">
                                <span class="left text-muted ml--identifier">
                                   {{ mtblc.id }}
                                </span>
                                <span class="right">
                                    <div class="btn-group" role="group" aria-label="">
                                        <a target="_blank" href="${pageContext.request.contextPath}/webservice/beta/compound/{{ mtblc.id }}" class="btn btn-default btn-xs"><i class="fa fa-save"></i> JSON</a>
                                        <!-- <div class="btn-group" role="group">
                                            <button type="button" class="btn btn-default btn-xs" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="fa fa-share"></i> Share</button>
                                            <ul class="dropdown-menu">
                                                <li></li>
                                                <li><a href="#"></a></li>
                                            </ul>
                                        </div> -->
                                        <a href="${pageContext.request.contextPath}/referencespectraupload?cid=${compoundId}" class="btn btn-default btn-xs"><i class="fa fa-upload"></i> Upload Spectra </a>
                                        <button type="button" class="btn btn-default btn-xs" data-toggle="modal" data-target="#discussionModal"><i class="fa fa-comment"></i> Discussion</button>
                                        <a target="_blank" href="${pageContext.request.contextPath}/contact" class="btn btn-default btn-xs"><i class="fa fa-question"></i> Help</a>
                                    </div>
                                </span>

                        </div>

                        <div class="modal fade" id="discussionModal" tabindex="-1" role="dialog" aria-labelledby="discussionModal">
                            <div class="modal-dialog disqus-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-body">
                                        <div id="disqus_thread"></div>
                                        <script>
                                            /**
                                             *  RECOMMENDED CONFIGURATION VARIABLES: EDIT AND UNCOMMENT THE SECTION BELOW TO INSERT DYNAMIC VALUES FROM YOUR PLATFORM OR CMS.
                                             *  LEARN WHY DEFINING THESE VARIABLES IS IMPORTANT: https://disqus.com/admin/universalcode/#configuration-variables
                                             */

                                            var disqus_config = function () {
                                                this.page.url = "http://www.ebi.ac.uk/metabolights/${compoundId}";  // Replace PAGE_URL with your page's canonical URL variable
                                                this.page.identifier = "${compoundId}"; // Replace PAGE_IDENTIFIER with your page's unique identifier variable
                                            };

                                            (function() {  // DON'T EDIT BELOW THIS LINE
                                                var d = document, s = d.createElement('script');

                                                s.src = '//metabolights.disqus.com/embed.js';

                                                s.setAttribute('data-timestamp', +new Date());
                                                (d.head || d.body).appendChild(s);
                                            })();
                                        </script>
                                        <noscript>Please enable JavaScript to view the <a href="https://disqus.com/?ref_noscript" rel="nofollow">comments powered by Disqus.</a></noscript>


                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="met-content">
                            <div class="card">
                                <ul class="nav nav-tabs" role="tablist">
                                    <li v-if="mtblc.name" role="presentation" class="active"><a href="#chemistry" aria-controls="chemistry" role="tab" data-toggle="tab">Chemistry</a></li>
                                    <li v-if="mtblc.flags.hasSpecies == 'true'" role="presentation"><a href="#biology" aria-controls="biology" role="tab" data-toggle="tab">Biology</a></li>
                                    <li v-if="mtblc.flags.hasPathways == 'true'" role="presentation"><a href="#pathways" aria-controls="pathways" role="tab" data-toggle="tab">Pathways</a></li>
                                    <li v-if="mtblc.flags.hasMS == 'true' || mtblc.flags.hasNMR == 'true'" role="presentation"><a href="#spectra" aria-controls="spectra" role="tab" data-toggle="tab">Spectra</a></li>
                                    <li v-if="mtblc.flags.hasReactions == 'true'" role="presentation"><a href="#reactions" aria-controls="reactions" role="tab" data-toggle="tab">Reaction</a></li>
                                    <li v-if="mtblc.flags.hasLiterature == 'true'" role="presentation"><a href="#citations" aria-controls="citations" role="tab" data-toggle="tab">Literature</a></li>
                                </ul>

                                <!-- Tab panes -->
                                <div class="tab-content cols">
                                    <div id="loading" v-if="loading">
                                        <h5>Loading...</h5>
                                    </div>
                                    <div role="tabpanel" class="tab-pane active" id="chemistry">
                                        <div class="alert alert-default" id="description">
                                            <label>Compound Description</label>
                                            <h4>
                                                {{ mtblc.definition }}
                                            </h4>

                                            {{mtblc.flags.hasSpecies}}
                                        </div>
                                        <div class="met-panel col-md-12">


                                            <div class="col-md-12  ml_tr_th">
                                                <div class="col-md-12 ml_trc"><b><h4>Identification</h4></b></div>
                                            </div>
                                            <div class="col-md-12 ml_tr">
                                                <div class="col-md-3 ml_trc ml_trh">MetaboLights Identifier</div>
                                                <div class="col-md-9 ml_trc">
                                                    <p v-for="iupac in mtblc.iupacNames" class="label-spaced">{{ mtblc.id }}</p>
                                                </div>
                                            </div>
                                            <div class="col-md-12 ml_tr">
                                                <div class="col-md-3 ml_trc ml_trh">IUPAC Names</div>
                                                <div class="col-md-9 ml_trc">
                                                    <p v-for="iupac in mtblc.iupacNames" class="label-spaced">{{ iupac }},</p>
                                                </div>
                                            </div>
                                            <div class="col-md-12 ml_tr">
                                                <div class="col-md-3 ml_trc ml_trh">Molecular Formula</div>
                                                <div class="col-md-9 ml_trc">{{ mtblc.formula }}</div>
                                            </div>
                                            <div class="col-md-12 ml_tr">
                                                <div class="col-md-3 ml_trc ml_trh">Average Mass</div>
                                                <div class="col-md-9 ml_trc">{{ mtblc.averagemass }}</div>
                                            </div>
                                            <div class="col-md-12 ml_tr">
                                                <div class="col-md-3 ml_trc ml_trh">Exact Mass</div>
                                                <div class="col-md-9 ml_trc">{{ mtblc.exactmass }}</div>
                                            </div>
                                            <div class="col-md-12 ml_tr">
                                                <div class="col-md-3 ml_trc ml_trh">Molecular Weight</div>
                                                <div class="col-md-9 ml_trc">{{ mtblc.molweight }}</div>
                                            </div>
                                            <div class="col-md-12 ml_tr">
                                                <div class="col-md-3 ml_trc ml_trh">Charge</div>
                                                <div class="col-md-9 ml_trc">{{ mtblc.charge }}</div>
                                            </div>
                                            <div class="col-md-12 ml_tr">
                                                <div class="col-md-3 ml_trc ml_trh">InChI</div>
                                                <div class="col-md-9 ml_trc">{{ mtblc.inchi }}
                                                    <button id="InChI" data-placement="left" class="btn btn-xs pull-right ml--clipboard" data-clipboard-text="{{ mtblc.inchi }}">
                                                        <i class="fa fa-clipboard" aria-hidden="true"></i>
                                                    </button>
                                                </div>
                                            </div>
                                            <div class="col-md-12 ml_tr">
                                                <div class="col-md-3 ml_trc ml_trh">InChIKey</div>
                                                <div class="col-md-9 ml_trc" data-toggle="tooltip" data-placement="top" title="Default tooltip" >{{ mtblc.inchiKey }}
                                                    <button id="InChIKey" class="btn btn-xs pull-right ml--clipboard" data-clipboard-text="{{ mtblc.inchiKey }}">
                                                        <i class="fa fa-clipboard" aria-hidden="true"></i>
                                                    </button>
                                                </div>
                                            </div>
                                            <div class="col-md-12 ml_tr">
                                                <div class="col-md-3 ml_trc ml_trh">SMILES</div>
                                                <div class="col-md-9 ml_trc">{{ mtblc.smiles }}</div>
                                            </div>
                                            <div class="col-md-12 ml_tr">
                                                <div class="col-md-3 ml_trc ml_trh">Synonymns</div>
                                                <div class="col-md-9 ml_trc">
                                                    <div id="ml--synonyms">
                                                        <p v-for="synonym in mtblc.synonyms" class="ml--syn">{{ synonym }}</p>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="clearfix"></div><div class="clearfix"></div>

                                            <div class="col-md-12  ml_tr_th">
                                                <div class="col-md-12 ml_trc"><b><h4>External Links</h4></b></div>
                                            </div>
                                            <div class="col-md-12 ml_tr" v-for="id in mtblc.externalIds">
                                                <div class="col-md-3 ml_trc ml_trh">{{ $key }}</div>
                                                <div class="col-md-9 ml_trc">{{{ id }}}</div>
                                            </div>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>
                                    <div role="tabpanel" class="tab-pane" id="biology">
                                        <div class="met-panel col-md-12">
                                            <div class="col-md-12  ml_tr_th">
                                                <div class="col-md-12 ml_trc"><b><h4>Species</h4></b></div>
                                            </div>
                                            <div class="col-md-12 ml_tr" v-for="id in mtblc.species">
                                                <div class="col-md-3 ml_trc"><b>{{ $key }}</b></div>
                                                <div class="col-md-9 ml_trc">
                                                    <p v-for="source in id">
                                                        <span>{{{ source.SpeciesAccession }}}</span>&emsp;<span>{{{ source.SourceAccession }}}</span>
                                                    </p>
                                                </div>
                                            </div>
                                            <div class="clearfix"></div>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>


                                    <div role="tabpanel" class="tab-pane" id="pathways">
                                        <div>
                                            <!-- Nav tabs -->
                                            <ul class="nav nav-tabs" role="tablist">
                                                <li v-if="mtblc.pathways.WikiPathways" role="presentation" class="active"><a href="#wiki" aria-controls="wiki" role="tab" data-toggle="tab">WikiPathways</a></li>
                                                <li v-if="mtblc.pathways.KEGGPathways.length > 0" role="presentation"><a href="#kegg" aria-controls="kegg" role="tab" data-toggle="tab">KEGG Pathways</a></li>
                                                <li v-if="mtblc.pathways.ReactomePathways" role="presentation"><a href="#reactome" aria-controls="reactome" role="tab" data-toggle="tab">Reactome Pathways</a></li>
                                            </ul>

                                            <!-- Tab panes -->
                                            <div class="tab-content">
                                                <div role="tabpanel" class="tab-pane active" id="wiki">
                                                    <div class="form-group">
                                                        <label>Select Species</label>
                                                        <select class="selectpicker form-control" v-model="selectedSpecies"  data-live-search="true">
                                                            <option v-for="(key,value) in mtblc.pathways.WikiPathways" value="{{key}}" >{{ key }}</option>
                                                        </select>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Select Pathways</label>
                                                        <select class="form-control" v-model="selectedPathway" data-live-search="true">
                                                            <option v-for="pathway in selectedPathways"  value="{{pathway.id}}">{{ pathway.name }}</option>
                                                        </select>
                                                    </div>
                                                    <div v-if="selectedWPSpeciesStudyMap.length > 0">
                                                        <div class="alert alert-info" role="alert">
                                                            This metabolite has been identified in the following MetaboLights studies.
                                                            <span v-for="map in selectedWPSpeciesStudyMap">
                                                                <a href="http://www.ebi.ac.uk/metabolights/{{ map.SpeciesAccession }}" target="_blank">{{ map.SpeciesAccession }}</a>&nbsp;
                                                            </span>
                                                        </div>
                                                    </div>
                                                    <div v-if="selectedPathway" class="well no-padding">
                                                        <iframe src="http://www.wikipathways.org/wpi/PathwayWidget.php?id={{selectedPathway}}" frameborder="0" width="98%" height="500px" seamless="seamless" scrolling="no"></iframe>
                                                    </div>
                                                </div>
                                                <div role="tabpanel" class="tab-pane" id="kegg">
                                                    <select class="form-control" v-model="selectedKEGGPathway" data-live-search="true">
                                                        <option value="" selected>Select Pathway</option>
                                                        <option v-for="pathway in mtblc.pathways.KEGGPathways" value="{{ pathway.KO_PATHWAY }}" >{{ pathway.name }}</option>
                                                    </select>
                                                    <div v-if="selectedKEGGPathway">
                                                        <br>
                                                        <span class='zoom' id="kegg--img">
                                                            <img src='http://www.kegg.jp/kegg/pathway/ko/{{selectedKEGGPathway}}.png' class="img-responsive">
                                                        </span>
                                                    </div>
                                                    <div class="clearfix"></div>
                                                </div>
                                                <div role="tabpanel" class="tab-pane" id="reactome">
                                                    <div class="form-group">
                                                        <label>Select Species</label>
                                                        <select class="selectpicker form-control" v-model="selectedReactomeSpecies"  data-live-search="true">
                                                            <option v-for="(key,value) in mtblc.pathways.ReactomePathways" value="{{key}}" >{{ key }}</option>
                                                        </select>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Select Pathways</label>
                                                        <select class="form-control" v-model="selectedReactomePathway" data-live-search="true">
                                                            <option value="" selected>Select Pathway</option>
                                                            <option v-for="pathway in selectedReactomePathways"  value="{{pathway.reactomeId}}">{{ pathway.name }}</option>
                                                        </select>
                                                    </div>
                                                    <div v-if="selectedReactomePathway">
                                                        <hr>
                                                        <div class="col-md-12 well">
                                                            <div id="diagramHolder"></div>
                                                            <div class="clearfix"></div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="clearfix"></div>
                                            </div>

                                        </div>

                                    </div>

                                    <div role="tabpanel" class="tab-pane" id="reactions">
                                        <label>Select Reaction</label>
                                        <select id="reactionsSelect" class="form-control selectpicker" v-model="selectedReaction" data-live-search="true">
                                            <option v-for="reaction in mtblc.reactions" value="{{ reaction.id }}">{{ reaction.name }}</option>
                                        </select>

                                        <div v-if="selectedReaction">
                                            <br>
                                            <p><i class="fa fa-link"></i>&nbsp;</i><i>Source: </i><a target="_blank" :href="'http://www.rhea-db.org/reaction?id=RHEA:'+selectedReaction">Rhea: {{ selectedReaction }}</a></p>
                                            <!-- <div class="ml_trc grey"><b><h4>{{ selectedReactionData.name }}</h4></b></div> -->
                                            <div class="col-md-12" id="BioJSReaction"></div>
                                            <div class="clearfix">&nbsp;</div>
                                        </div>
                                    </div>

                                    <div role="tabpanel" class="tab-pane" id="spectra">

                                        <ul class="nav nav-tabs" role="tablist">
                                            <li v-if="mtblc.flags.hasNMR == 'true'" role="presentation" class="active"><a href="#nmr" aria-controls="nmr" role="tab" data-toggle="tab">NMR Spectra</a></li>
                                            <li v-if="mtblc.flags.hasMS == 'true'" role="presentation"><a href="#ms" aria-controls="ms" role="tab" data-toggle="tab">MS Spectra</a></li>
                                        </ul>

                                        <!-- Tab panes -->
                                        <div class="tab-content">
                                            <div role="tabpanel" class="tab-pane"  v-bind:class="{ 'active' : mtblc.flags.hasNMR == 'true' }" id="nmr">
                                                <div class="form-group">
                                                    <label>Select NMR spectra</label>
                                                    <select v-model="selectedNMR" class="form-control selectFirst" size=5 multiple>
                                                        <option v-for="spectra in nmrSpectra">
                                                            {{ spectra.name }}
                                                        </option>
                                                    </select>
                                                    <div v-if="selectedNMR.length > 0">
                                                        <br>
                                                        <div class="ml_trc grey"><b><h4>Spectra Viewer</h4></b></div>
                                                        <!-- MS Spectra -->
                                                        <div id="NMRSpeckTackle" class="spectakle-container"></div>
                                                        <div class="col-md-12 well">
                                                            <div id="nmrInfo" class="specs">
                                                                <div v-for="spectra in selectedNMRSpectra" class="col-md-12 ind--spec">
                                                                        <h4 class="ml_sp_title">{{spectra.name}}</h4>
                                                                        <span class="col-md-12 ml_sp_tr" v-for="attribute in spectra.attributes">
                                                                            <div >
                                                                                <div class="col-md-3 ml_sp_trc"><b>{{ attribute.attributeName }}</b></div>
                                                                                <div class="col-md-9 ml_sp_trc">{{ attribute.attributeValue }}</div>
                                                                            </div>
                                                                        </span>
                                                                    <hr>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                </div>
                                                <div class="clearfix"></div>
                                            </div>
                                            <div role="tabpanel" class="tab-pane" v-bind:class="{ 'active': mtblc.flags.hasMS == 'true' && mtblc.flags.hasNMR == 'false' }" id="ms">
                                                <div class="form-group">
                                                    <label>Select MS spectra</label>
                                                    <select v-model="selectedMS" class="form-control selectFirst" size=5 multiple>
                                                        <option v-for="spectra in msSpectra">
                                                            {{ spectra.name }}
                                                        </option>
                                                    </select>
                                                    <div v-if="selectedMS.length > 0">
                                                        <br>
                                                        <div class="ml_trc grey"><b><h4>Spectra Viewer</h4></b></div>
                                                        <!-- MS Spectra -->
                                                        <div id="MSSpeckTackle" class="spectakle-container"></div>
                                                        <div class="col-md-12 well">
                                                            <div id="msInfo" class="specs">
                                                                <div v-for="spectra in selectedMSSpectra" class="col-md-12 ind--spec">
                                                                    <h4 class="ml_sp_title">{{spectra.name}}</h4>
                                                                    <span class="col-md-12 ml_sp_tr" v-for="attribute in spectra.attributes">
                                                                        <div>
                                                                            <div class="col-md-3 ml_sp_trc"><b>{{ attribute.attributeName }}</b></div>
                                                                            <div class="col-md-9 ml_sp_trc">{{ attribute.attributeValue }}</div>
                                                                        </div>
                                                                    </span>
                                                                    <hr>
                                                                    <h5 class="ml_sp_title">
                                                                        <a href="http://splash.fiehnlab.ucdavis.edu/">Splash - The Spectral Hash Identifier</a> <span class="pull-right" id="splash-container">{{ spectra.splash.splash }}</span>

                                                                    </h5>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                </div>
                                                <div class="clearfix"></div>
                                            </div>
                                            <div class="clearfix"></div>
                                        </div>
                                    </div>

                                    <div role="tabpanel" class="tab-pane" id="citations">
                                        <h4><b><a href="http://europepmc.org/">Europe PubMed Central results</a></b></h4>
                                        <hr>
                                        <div v-for="citation in mtblc.citations">
                                            <div class="panel panel-default" id="panel1">
                                                <div class="panel-heading" data-toggle="collapse" data-target="#citation{{$index}}">
                                                    <h4>{{ citation.title }}</h4>
                                                </div>
                                                <div class="panel-collapse collapse" id="citation{{$index}}">
                                                    <div class="panel-body">
                                                    <h6><b>Author: </b>{{ citation.author }}</h6>
                                                    <p><small><b>Abstract: </b>{{ citation.abstract }}</small></p>
                                                        <div v-if="citation.doi != 'NA'">
                                                            <hr>
                                                            <p><b>DOI: </b> <a href="http://dx.doi.org/{{citation.doi}}" target="_blank">{{ citation.doi }}</a></p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/vue/1.0.10/vue.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/vue-resource/0.1.17/vue-resource.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/js/bootstrap-select.min.js"></script>

<script src="${pageContext.request.contextPath}/javascript/Notifier.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/clipboard.js/1.5.10/clipboard.min.js"></script>


<script type="text/javascript" src="http://d3js.org/d3.v3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/Biojs.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/Biojs.Rheaction.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/st.min.js" charset="utf-8"></script>


<script type="text/javascript" language="javascript" src="http://www.reactome.org/DiagramJs/diagram/diagram.nocache.js"></script>

<script src="https://wzrd.in/bundle/biojs-vis-keggviewer@1.1.2"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/3Dmol-min.js"></script>
<script id="dsq-count-scr" src="//metabolights.disqus.com/count.js" async></script>

<script>

    jQuery.noConflict();

    var data = {
        compound: '${compoundId}',
        mtblc: {},
        selectedSpecies : "",
        selectedPathway: "",
        selectedReactomeSpecies : "",
        selectedReactomePathway: "",
        selectedKEGGPathway: "",
        selectedMS: [],
        selectedNMR: [],
        selectedMSSpectra: [],
        selectedNMRSpectra: [],
        selectedReaction: "",
        selectedReactionData: {},
        MSchart: null,
        MSData: {},
        NMRchart: null,
        NMRarray: {},
        loading: false,
        synonymsCount: 0,
        msSpectra: [],
        nmrSpectra: [],
        selectedWPSpeciesStudyMap: []
    }

    var vm = new Vue({
        el: "#app",
        data: data,
        computed: {
            selectedPathways: function () {
                var tempPathways = [];
                if(this.selectedSpecies != ""){
                    tempPathways = this.mtblc.pathways.WikiPathways[this.selectedSpecies];
                }
                return tempPathways;
            },
            selectedReactomePathways: function () {
                var tempPathways = [];
                if(this.selectedReactomeSpecies != ""){
                    tempPathways = this.mtblc.pathways.ReactomePathways[this.selectedReactomeSpecies];
                }
                return tempPathways;
            },
            nmrSpectra: function () {
                return this.mtblc.spectra['NMR']
            },
            msSpectra: function () {
                return this.mtblc.spectra['MS']
            }
        },
        methods: {
            load3DMolecule: function(){
                var width = document.getElementById('displayMol').offsetWidth - 10;
                $("#3dDisplay").width(width).height(width);
                var viewer = $3Dmol.createViewer($("#3dDisplay"));
                viewer.setBackgroundColor('white');
                viewer.clear();
                viewer.addAsOneMolecule(this.mtblc.structure, "sdf");
                viewer.setStyle({},{stick: {radius:0.2}});
                viewer.zoomTo();
                viewer.render();
            },
            initializeMSSpeckTackle: function(){
                if (this.MSchart != null) return;
                this.MSchart = st.chart.ms().xlabel("Mass-to-Charge").ylabel("Intensity").legend(true).labels(true);
                this.MSchart.render("#MSSpeckTackle");
                this.MSData = st.data.set().x("peaks.mz").y("peaks.intensity").title("spectrumId");
                this.MSchart.load(this.MSData);
            },
            initializeNMRSpeckTackle: function() {
                if ($("#NMRSpeckTackle").width() < 1 ){
                    this.NMRchart = null;
                    return
                }
                if (this.NMRchart != null) return;
                this.NMRchart = st.chart.nmr().xlabel("ppm").legend(true).margins([20, 100, 60, 0]).labels(true);
                this.NMRchart.render("#NMRSpeckTackle");

                this.NMRarray = st.data.array().xlimits(["xMin", "xMax"]).ylimits(["yMin", "yMax"]).y("data");
                this.NMRchart.load(this.NMRarray);
            },
            toggleLoading: function(){
                this.loading = !this.loading;
            }
        },
        ready: function(){
            this.loading = true;
            this.$http.get('${pageContext.request.contextPath}/webservice/beta/compound/'+ this.compound, function (data, status, request) {
                this.$set('mtblc', data);
                this.mtblc['chebiId'] = this.mtblc['id'].replace("MTBLC", "");
                this.mtblc['imageUrl'] = "http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=" + this.mtblc['chebiId'] + "&dimensions=600&transbg=true";
                this.mtblc['imageUrlLarge'] = "http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=" + this.mtblc['chebiId'] + "&dimensions=1000&transbg=true";
                this.load3DMolecule();
                this.loading = false;
                var clipboard = new Clipboard('.ml--clipboard');
                clipboard.on('success', function(e) {
                    Notifier.success("Successful", e.trigger.id + " Copied to clipboard!");
                });

                clipboard.on('error', function(e) {
                    console.error('Action:', e.action);
                    console.error('Trigger:', e.trigger);
                });

                var syn_h = this.mtblc.synonyms.length*38

                $("#ml--synonyms").click(function () {
                    $(this).animate({
                        height: ($(this).height() == 150) ?  syn_h : 150
                    }, 200);
                });

                var url = document.location.toString();
                if (url.match('#')) {
                    getHash();
                }

                //$(".selectFirst option:first").attr("selected", "selected");

            }).error(function (data, status, request) {
                console.log(data);
                console.log(status);
                console.log(request);
            });

        }
    });

    vm.$watch('selectedSpecies', function () {
        vm.selectedPathway = vm.selectedPathways[0].id;
        var selectedSpeciesLC = vm.selectedSpecies.toLowerCase();
        vm.selectedWPSpeciesStudyMap = []
        if(vm.mtblc.species[selectedSpeciesLC] != undefined){
            vm.selectedWPSpeciesStudyMap = vm.mtblc.species[selectedSpeciesLC].filter(function(species){
                return species.SpeciesAccession.indexOf('MTBLS')  > -1 ? true : false;
            });
        }else{
            vm.selectedWPSpeciesStudyMap = []
        }

    })

    vm.$watch('selectedReactomePathway', function () {

        //alert(vm.selectedReactomePathway);

        if (vm.selectedReactomePathway != ""){

            var w = document.getElementById("diagramHolder").offsetWidth;
            var diagram = Reactome.Diagram.create({
                "proxyPrefix" : "/metabolights/RheaAndReactomeProxy?url=http://www.reactome.org/",
                "placeHolder" : "diagramHolder",
                "width" : w,
                "height" : 500
            });
            //Initialising it to the "Metabolism of nucleotides" pathway
            diagram.loadDiagram(vm.selectedReactomePathway);
            //Adding different listeners
            diagram.onDiagramLoaded(function (loaded) {
                console.info("Loaded ", loaded);
                //diagram.selectItem("R-HSA-111804");
                diagram.flagItems("TXN");
            });

            diagram.onObjectHovered(function (hovered){
                console.info("Hovered ", hovered);
            });

            diagram.onObjectSelected(function (selected){
                console.info("Selected ", selected);
            });

        }

    })


    vm.$watch('selectedReactomeSpecies', function () {
        vm.selectedReactomePathway = vm.selectedReactomePathways[0].reactomeId;
    })

    vm.$watch('selectedMS', function () {

        this.initializeMSSpeckTackle();
        this.selectedMSSpectra = vm.mtblc.spectra.MS.filter(function(spec){
            return vm.selectedMS.indexOf(spec.name) > -1 ? true : false;
        });
        this.MSData.remove();

        this.MSData.add(this.selectedMSSpectra.map(function(spec){
            return spec.url;
        }));

    })

    vm.$watch('selectedNMR', function () {
        this.initializeNMRSpeckTackle();
        this.selectedNMRSpectra = vm.mtblc.spectra.NMR.filter(function(spec){
            return vm.selectedNMR.indexOf(spec.name) > -1 ? true : false;
        });
        this.NMRarray.remove();
        this.NMRarray.add(this.selectedNMRSpectra.map(function(spec){
            return spec.url;
        }));
    })

    vm.$watch('selectedReaction', function(){

        vm.selectedReactionData = vm.mtblc.reactions.filter(function(reaction){
            return vm.selectedReaction.indexOf(reaction.id) > -1 ? true : false;
        })[0];

        var bioJSReaction = new Biojs.Rheaction({
            target: 'BioJSReaction',
            id: this.selectedReaction,
            proxyUrl: '/metabolights/RheaAndReactomeProxy',
            rheaWsUrl: "http://www.rhea-db.org/rest/1.0/ws/reaction/cmlreact/"
        });

    });

    $(document).ready(function() {
        if(location.hash) {
            $('.nav-tabs a[href="' + location.hash + '"]').tab('show');
        }
        $(document.body).on("click", "a[data-toggle]", function(event) {
            location.hash = this.getAttribute("href");
            getHash();
        });
    });
    $(window).on('popstate', function() {
        var anchor = location.hash || $("a[data-toggle=tab]").first().attr("href");
        $('a[href=' + anchor + ']').tab('show');
        getHash();
    });


    function getHash(){
        var url = document.location.toString();
        if (url.match('#')) {
            loadData(url.split('#')[1]);
        }
    }


    function loadData(target){
        if (target == 'pathways') {
            for (firstWikiPathway in vm.mtblc.pathways.WikiPathways) break;
            data.selectedSpecies = firstWikiPathway;
        }else if (target == 'chemistry') {
            //console.log("chemistry")
        }else if (target == 'reactome') {
            for (firstReactomePathway in vm.mtblc.pathways.ReactomePathways ) break;
            data.selectedReactomeSpecies = firstReactomePathway;
        } else if (target == 'kegg') {
            data.selectedKEGGPathway =  vm.mtblc.pathways.KEGGPathways[0].KO_PATHWAY;
        } else if (target == 'spectra') {
            try {
                if(typeof vm.nmrSpectra != 'undefined') {
                    data.selectedNMR = [vm.nmrSpectra[0].name];
                }
            }catch (err){
                if(typeof vm.msSpectra != 'undefined') {
                    data.selectedMS = [vm.msSpectra[0].name];
                }
            }
        } else if (target == 'ms') {
            if(typeof vm.msSpectra != 'undefined') {
                data.selectedMS = [vm.msSpectra[0].name];
            }
        } else if (target == 'reactions') {
            data.selectedReaction = data.mtblc.reactions[0].id;
        }
    }

</script>