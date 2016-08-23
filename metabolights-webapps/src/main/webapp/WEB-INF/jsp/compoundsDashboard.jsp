<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 5/21/14 12:39 PM
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

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/MetCompound.css" type="text/css"/>
<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css">

<div class="ml-wrapper">
    <div class="container-fluid" id="app">
            <div class="col-md-12">
                <h2 class="text-center title"><b>Compounds Status</b></h2>
            </div>
        <div class="row">
            <div class="col-md-5">
                <div class="col-md-6">
                    <div class="board">
                        <div class="col-md-4 board-icon">
                            <i class="fa fa-2x fa-list"></i>
                        </div>
                        <a href="/">
                            <div class="col-md-8 board-content">
                                <small><span class="category"><b>Total<br>Compounds</b></span></small>
                                <span class="number">{{ totalCompounds }}</span>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="board">
                        <div class="col-md-4 board-icon">
                            <i class="fa fa-2x fa-times"></i>
                        </div>
                        <a href="/">
                            <div class="col-md-8 board-content">
                                <small><span class="category"><b>Missing<br>Compounds</b></span></small>
                                <span class="number">{{ failedCompoundsCount }}</span>
                            </div>
                        </a>
                    </div>
                </div>
            </div>



            <div class="col-md-7 row">
                <div class="board">
                    <a href="/">
                        <div class="col-md-2 board-content2">
                                <span class="category"><span class="label label-success">Structure</span></span>
                                <span class="number1">{{ has3dCount }}</span>
                        </div>
                    </a>
                    <a href="/">
                        <div class="col-md-2 board-content2">
                            <span class="category"><span class="label label-success">Pathways</span></span>
                            <span class="number1">{{ hasPathwaysCount }}</span>
                        </div>
                    </a>
                    <a href="/">
                        <div class="col-md-2 board-content2">
                            <span class="category"><span class="label label-success">MS</span></span>
                            <span class="number1">{{ hasMSCount }}</span>
                        </div>
                    </a>
                    <a href="/">
                        <div class="col-md-2 board-content2">
                            <span class="category"><span class="label label-success">NMR</span></span>
                            <span class="number1">{{ hasNMRCount }}</span>
                        </div>
                    </a>
                    <a href="/">
                        <div class="col-md-2 board-content2">
                            <span class="category"><span class="label label-success">Reactions</span></span>
                            <span class="number1">{{ hasReactionsCount }}</span>
                        </div>
                    </a>
                    <a href="/">
                        <div class="col-md-2 board-content2">
                            <span class="category"><span class="label label-success">Citations</span></span>
                            <span class="number1">{{ hasLiteratureCount }}</span>
                        </div>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-2.1.4.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/vue/1.0.10/vue.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/vue-resource/0.1.17/vue-resource.js"></script>

<script>
    var app = new Vue({
        el: '#app',
        data: {
            globalReport: {},
            totalCompounds: 0,
            failedCompoundsCount: 0,
            hasPathwaysCount: 0,
            hasReactionsCount:0,
            hasLiteratureCount:0,
            hasSpeciesCount:0,
            hasMSCount:0,
            hasNMRCount:0,
            has3dCount:0,
            failedCompounds: {},
            hasPathways: {},
            hasReactions: {},
            hasLiterature: {},
            hasSpecies: {},
            hasMS: {},
            hasInchiKey: {},
            hasNMR: {},
            has3d:{}
        },
        methods: {
            getFailingCompounds: function(){
                for (let key in this.globalReport) {
                    //console.log(key)
                    if (!this.globalReport.hasOwnProperty(key)) { continue; }
                    if (this.globalReport[key]['rating'] == 0){
                        this.failedCompounds[key] = (this.globalReport[key])
                    }
                    this.failedCompoundsCount = Object.keys(this.failedCompounds).length
                    if (this.globalReport[key]['flags']['hasPathways'] == "true"){
                        this.hasPathways[key] = (this.globalReport[key])
                    }
                    this.hasPathwaysCount = Object.keys(this.hasPathways).length

                    if (this.globalReport[key]['flags']['hasReactions'] == "true"){
                        this.hasReactions[key] = (this.globalReport[key])
                    }
                    this.hasReactionsCount = Object.keys(this.hasReactions).length

                    if (this.globalReport[key]['flags']['hasMS'] == "true"){
                        this.hasMS[key] = (this.globalReport[key])
                    }
                    this.hasMSCount = Object.keys(this.hasMS).length

                    if (this.globalReport[key]['flags']['hasNMR'] == "true"){
                        this.hasNMR[key] = (this.globalReport[key])
                    }
                    this.hasNMRCount = Object.keys(this.hasNMR).length

                    if (this.globalReport[key]['flags']['hasLiterature'] == "true"){
                        this.hasLiterature[key] = (this.globalReport[key])
                    }
                    this.hasLiteratureCount = Object.keys(this.hasLiterature).length

                    if (this.globalReport[key]['flags']['has3d'] == "true" || this.globalReport[key]['flags']['has3d'] == true){
                        this.has3d[key] = (this.globalReport[key])
                        //console.log(this.globalReport[key]['flags']['has3d'])
                    }
                    this.has3dCount = Object.keys(this.has3d).length

                }

            }
        },
        ready: function(){
            this.$http.get('/metabolights/webservice/beta/compoundReport', function (data, status, request) {
                this.$set('globalReport', data);
                this.totalCompounds = Object.keys(this.globalReport).length;
                this.getFailingCompounds();
            }).error(function (data, status, request) {
                console.log(data);
                console.log(status);
                console.log(request);
            });
        }
    })
</script>