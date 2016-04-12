<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/MetCompound.css" type="text/css"/>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/css/bootstrap-select.min.css">

<div id="fb-root"></div>
<script>(function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s); js.id = id;
    js.src = "//connect.facebook.net/en_GB/sdk.js#xfbml=1&version=v2.5&appId=1535102133478127";
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>

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
                                        <a><i class="fa fa-search-plus"></i></a>
                                    </span>
                                </div>

                            </div>
                            <div class="text-center">
                                &nbsp;
                            </div>
                        </div>
                    </div>
                    <div class="col-md-9">
                        <div class="banner">
                            <h2>{{ mtblc['name'] }}
                            </h2>
                                <span class="met-id pull-right">
                                    <div class="btn-group" role="group" aria-label="">
                                        <button type="button" class="btn btn-default btn-xs" onclick="downloadJSONFile()"><i class="fa fa-save"></i> JSON</button>
                                        <div class="btn-group" role="group">
                                            <button type="button" class="btn btn-default btn-xs" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="fa fa-share"></i> Share</button>
                                            <ul class="dropdown-menu">
                                                <li><a href="#"><div class="fb-share-button" data-href="http://localhost:8080/metabolights/beta/${compoundId}" data-layout="button"></div></a></li>
                                                <li><a href="#">Dropdown link</a></li>
                                            </ul>
                                        </div>
                                        <button type="button" class="btn btn-default btn-xs"><i class="fa fa-upload"></i> Upload</button>
                                        <button type="button" class="btn btn-default btn-xs"><i class="fa fa-comment"></i> Discussion</button>
                                        <button type="button" class="btn btn-default btn-xs"><i class="fa fa-question"></i> Help</button>
                                    </div>
                                </span>
                        </div>
                        <div class="met-content">
                            <div class="card">
                                <ul class="nav nav-tabs" role="tablist">
                                    <li role="presentation" class="active"><a href="#chemistry" aria-controls="chemistry" role="tab" data-toggle="tab">Chemistry</a></li>
                                    <li v-if="mtblc.species" role="presentation"><a href="#biology" aria-controls="biology" role="tab" data-toggle="tab">Biology</a></li>
                                    <li v-if="mtblc.pathways" role="presentation"><a href="#pathways" aria-controls="pathways" role="tab" data-toggle="tab">Pathways</a></li>
                                    <li v-if="mtblc.spectra" role="presentation"><a href="#spectra" aria-controls="spectra" role="tab" data-toggle="tab">Spectra</a></li>
                                    <li v-if="mtblc.citations" role="presentation"><a href="#citations" aria-controls="citations" role="tab" data-toggle="tab">Literature</a></li>
                                </ul>

                                <!-- Tab panes -->
                                <div class="tab-content cols">
                                    <div role="tabpanel" class="tab-pane active" id="chemistry">
                                        <div class="alert alert-default" id="description">
                                            <label>Compound Description</label>
                                            <h4>
                                                {{ mtblc.definition }}
                                            </h4>
                                        </div>
                                        <div class="met-panel col-md-12">
                                            <div class="col-md-12  ml_tr_th">
                                                <div class="col-md-12 ml_trc"><b><h4>Identification</h4></b></div>
                                            </div>
                                            <div class="col-md-12 ml_tr">
                                                <div class="col-md-3 ml_trc">MetaboLights Identifiewe</div>
                                                <div class="col-md-9 ml_trc">
                                                    <p v-for="iupac in mtblc.iupacNames" class="label-spaced">{{ mtblc.id }}</p>
                                                </div>
                                            </div>
                                            <div class="col-md-12 ml_tr">
                                                <div class="col-md-3 ml_trc">IUPAC Names</div>
                                                <div class="col-md-9 ml_trc">
                                                    <p v-for="iupac in mtblc.iupacNames" class="label-spaced">{{ iupac }},</p>
                                                </div>
                                            </div>
                                            <div class="col-md-12 ml_tr">
                                                <div class="col-md-3 ml_trc">Inchikey</div>
                                                <div class="col-md-9 ml_trc">{{ mtblc.inchiKey }}</div>
                                            </div>
                                            <div class="col-md-12 ml_tr">
                                                <div class="col-md-3 ml_trc">Inchi</div>
                                                <div class="col-md-9 ml_trc">{{ mtblc.inchi }}</div>
                                            </div>
                                            <div class="col-md-12 ml_tr">
                                                <div class="col-md-3 ml_trc">Smiles</div>
                                                <div class="col-md-9 ml_trc">{{ mtblc.smiles }}</div>
                                            </div>
                                            <div class="col-md-12 ml_tr">
                                                <div class="col-md-3 ml_trc">Exact Mass</div>
                                                <div class="col-md-9 ml_trc">{{ mtblc.mass }}</div>
                                            </div>
                                            <div class="col-md-12 ml_tr">
                                                <div class="col-md-3 ml_trc">Charge</div>
                                                <div class="col-md-9 ml_trc">{{ mtblc.charge }}</div>
                                            </div>

                                            <div class="col-md-12 ml_tr">
                                                <div class="col-md-3 ml_trc">Synonymns</div>
                                                <div class="col-md-9 ml_trc">
                                                    <p v-for="synonym in mtblc.synonyms" class="label-spaced">{{ synonym }},</p>
                                                </div>
                                            </div>
                                            <div class="clearfix"></div><div class="clearfix"></div>

                                            <div class="col-md-12  ml_tr_th">
                                                <div class="col-md-12 ml_trc"><b><h4>External Links</h4></b></div>
                                            </div>
                                            <div class="col-md-12 ml_tr" v-for="id in mtblc.externalIds">
                                                <div class="col-md-3 ml_trc">{{ $key }}</div>
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
                                                <li v-if="mtblc.pathways.KEGGPathways.length > 0" role="presentation"><a href="#kegg" aria-controls="settings" role="tab" data-toggle="tab">KEGG Pathways</a></li>
                                            </ul>

                                            <!-- Tab panes -->
                                            <div class="tab-content">
                                                <div role="tabpanel" class="tab-pane active" id="wiki">

                                                    <div class="form-group">
                                                        <label>Select Species</label>
                                                        <select class="selectpicker form-control" v-model="selectedSpecies"  data-live-search="true">
                                                            <option value="" >Select Species</option>
                                                            <option v-for="(key,value) in mtblc.pathways.WikiPathways" value="{{key}}" >{{ key }}</option>
                                                        </select>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Select Pathways</label>
                                                        <select class="form-control" v-model="selectedPathway" data-live-search="true">
                                                            <option value="" selected>Select Pathway</option>
                                                            <option v-for="pathway in selectedPathways"  value="{{pathway.id}}">{{ pathway.name }}</option>
                                                        </select>
                                                    </div>
                                                    <div v-if="selectedPathway" class="well">
                                                        <iframe src ="http://www.wikipathways.org/wpi/PathwayWidget.php?id={{selectedPathway}}" frameborder="0" width="100%" height="500px" seamless="seamless" scrolling="no"></iframe>
                                                    </div>

                                                </div>
                                                <div role="tabpanel" class="tab-pane" id="kegg">
                                                    <select class="selectpicker form-control" data-live-search="true">
                                                        <option v-for="pathway in mtblc.pathways.KEGGPathways" >{{ pathway.name }}</option>
                                                    </select>
                                                </div>
                                            </div>

                                        </div>

                                    </div>

                                    <div role="tabpanel" class="tab-pane" id="spectra">
                                        <h1>Spectra</h1>
                                    </div>

                                    <div role="tabpanel" class="tab-pane" id="citations">
                                        <h1>Citations</h1>
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

<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/3Dmol-min.js"></script>


<script>

    var data = {

        compound: '${compoundId}',

        mtblc: {},

        selectedSpecies : ""

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

            initialisePathways:function(){

                console.log(this.mtblc.pathways.WikiPathways);

            }
        },

        ready: function(){

            this.$http.get('${pageContext.request.contextPath}/webservice/beta/compound/'+ this.compound, function (data, status, request) {

                this.$set('mtblc', data);

                this.mtblc['chebiId'] = this.mtblc['id'].replace("MTBLC", "");
                this.mtblc['imageUrl'] = "http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=" + this.mtblc['chebiId'] + "&dimensions=600&transbg=true";

                this.load3DMolecule();
                this.initialisePathways();

            }).error(function (data, status, request) {

                console.log(data);
                console.log(status);
                console.log(status);

            });

        }

    });

</script>
