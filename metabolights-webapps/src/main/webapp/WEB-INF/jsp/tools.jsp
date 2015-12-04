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
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/metabolights.css" type="text/css"/>

<div class="container-fluid ml-wrapper">
            <div class="col-md-9">
                <div id="app">
                    <div class="col-md-12 row">
                        <a name="#studyLiterature"><h2 class="row">Curator tools page</h2></a>
                        <hr>
                        <div id="el">
                            <div class="form-group">
                            <select id="ss" v-model="selected" class="form-control">
                                <option v-for="option in options" v-bind:value="option.value">
                                    {{ option.text }}
                                </option>
                            </select>
                            </div>
                            <hr>
                            <div class="section-content">
                                <table class="mltable table table-bordered">
                                    <thead>
                                    <tr>
                                        <th>Study</th>
                                        <th>Study Description</th>
                                        <th>Study Protocols</th>
                                    </tr>
                                    </thead>
                                    <tbody id="literaturebody">
                                    <tr>
                                        <td colspan="4"> <p class="text-center">Select a study</p> </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                    </div>

                    <div class="col-md-12 row">
                        <a name="studiesList"><h2 class="row">Studies List <small class="pull-right text-muted"><i><a href="#top">Back to top</a></i></small></h2></a>
                        <hr>
                        <div class="section-content">
                            <div class="col-md-12">
                                <ul class="list-group" >
                                    <div id="studieslist"></div>
                                    <li class="list-group-item"  v-for="dstudies in studiesWithDetails">{{ dstudies.id }} <br> <b>{{ dstudies.title }}</b> <hr> {{ dstudies.description }}</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        <div class="col-md-3">
            <nav class="bs-docs-sidebar hidden-print hidden-xs hidden-sm">
                <ul class="nav bs-docs-sidenav">
                    <li class="active"> <a href="#studyLiterature">Study Literature</a></li>
                    <li> <a href="#studiesList">Studies List</a></li>
                </ul>
            </nav>
        </div>
</div>
<script src="https://code.jquery.com/jquery-2.1.4.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/vue/1.0.10/vue.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/vue-resource/0.1.17/vue-resource.js"></script>

<script>


    var vm = new Vue({
        http: {
            headers: {
                'user_token' : '6996ca30-672c-4cda-9a0e-d113d640776f'
            }
        },
        el: '#app',
        data: {
            selected: '1',
            options: [],
            studies: [],
            studiesWithDetails: []
        },
        methods: {
            loadStudyLiterature: function () {
                $('#literaturebody').html('<tr><td colspan="3"><p class="text-center"><img src="${pageContext.request.contextPath}/img/beta_loading.gif"></p></td></tr>');
                this.$http.get('http://www.ebi.ac.uk/metabolights/webservice/study/MTBLS'+this.selected+'/full', function (data, status, request) {
                    var studyDescription = data['content']['description'];
                    var studyProtocols = data['content']['protocols'];
                    var sprot = '';
                    for (var j = 0; j < studyProtocols.length; j++) {
                        sprot = sprot + studyProtocols[j]['name'] + '</br>' + studyProtocols[j]['description'] + '<hr>'
                    }
                    var htmldata = '<tr><td>MTBLS'+this.selected+'</td><td>'+ studyDescription +'</td><td>'+sprot+'</td></tr>';
                    $('#literaturebody').html(htmldata);
                }).error(function (data, status, request) {
                    $('#literaturebody').html("<tr><td colspan='3'> <p class='text-center'>error loading studies literature</p></td></tr>");
                });

            },
            sortByKey: function(array, key) {
                return array.sort(function(a, b) {
                    var x = a[key]; var y = b[key];
                    return ((x < y) ? -1 : ((x > y) ? 1 : 0));
                });
            },
                loadStudyWithDetails: function(){
                    $('#studieslist').html('<tr><td colspan="3"><p class="text-center"><img src="${pageContext.request.contextPath}/img/beta_loading.gif"></p></td></tr>');
                    this.$http.get('http://ves-ebi-8d:8080/metabolights/webservice/study/listWithDetails', function (data, status, request) {
                        this.studiesWithDetails = data['content'];
                        $('#studieslist').html("");
                    }).error(function (data, status, request) {
                        $('#studieslist').html("<tr><td colspan='3'> <p class='text-center'>error loading studies list</p></td></tr>");
                    });
                }
        },

        ready: function() {
            this.$http.get('http://www.ebi.ac.uk/metabolights/webservice/study/list', function (data, status, request) {
                this.studies = data['content'];
                for(var i in this.studies) {
                    var item = parseInt(this.studies[i].replace("MTBLS", ""));
                    this.options.push({
                        "value" : item,
                        "text" : "MTBLS" + item
                    });
                }
                this.options = this.sortByKey(this.options, 'value');
            }).error(function (data, status, request) {
                alert('error loading studies list');
            });
            this.loadStudyLiterature();
            this.loadStudyWithDetails();
        }
    })

    vm.$watch('selected', function (val) {
        this.loadStudyLiterature();
    })



</script>
