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
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">


<div class="container-fluid ml-wrapper">
    <feedback inline-template>
        <div class="col-md-12">
            <h3>User feedback</h3>
            <hr>
            <div class="">
                <div class="row">
                    <div class="col-md-6">
                        <div class="panel panel-warning">
                            <div class="panel-heading">
                                <h5>Rating overview</h5>
                            </div>
                            <div class="panel-body">
                                <br>
                                <div class="col-md-12">
                                    <div class="col-md-4 text-center">
                                        <img src="${pageContext.request.contextPath}/img/sad.png" style="width: 50px;"><br><br>
                                        <p class="text-center"> {{sadPercentage}} %</p>
                                    </div>
                                    <div class="col-md-4 text-center">
                                        <img src="${pageContext.request.contextPath}/img/neutral.png" style="width: 50px;"><br><br>
                                        <p class="text-center"> {{neutralPercentage}}%</p>
                                    </div>
                                    <div class="col-md-4 text-center">
                                        <img src="${pageContext.request.contextPath}/img/happy.png" style="width: 50px;"><br><br>
                                        <p class="text-center"> {{happyPercentage}}%</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%--<div class="panel panel-default">--%>
                            <%--<div class="panel-heading">--%>
                                <%--<h5>Overview</h5>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Select study</label>
                            <select @change="setSelectedStudy(selectedStudyId)" v-model="selectedStudyId" class="form-control">
                                <option selected value="">Please choose a study</option>
                                <option v-for="(study, $key) in studies" :value="$key">{{ $key }}</option>
                            </select>
                            <span v-if="selectedStudyId">
                            <hr>
                            <div v-if="selectedStudy" class="panel panel-default">
                                <div class="panel-heading">
                                    <h6>{{ selectedStudyId }}</h6>
                                </div>
                                <div class="panel-body">
                                    <p>
                                        <small class="text-muted"><i>Experience</i></small>
                                        <br><br>
                                        <span v-if="selectedStudy.experience === 'neutral'">
                                            <img src="${pageContext.request.contextPath}/img/neutral.png" style="width: 50px;">
                                        </span>
                                        <span v-if="selectedStudy.experience === 'sad'">
                                            <img src="${pageContext.request.contextPath}/img/neutral.png" style="width: 50px;">
                                        </span>
                                        <span v-if="selectedStudy.experience === 'happy'">
                                            <img src="${pageContext.request.contextPath}/img/happy.png" style="width: 50px;">
                                        </span>
                                    </p>
                                    <p>
                                        <small class="text-muted"><i>Comments</i></small>
                                        <br>
                                        {{ selectedStudy.comments }}
                                    </p>
                                    <p>
                                        <small class="text-muted"><i>Submitted at</i></small>
                                        <br>
                                        {{ selectedStudy.created_at }}
                                    </p>
                                </div>
                            </div>
                        </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </feedback>
</div>
<script src="https://code.jquery.com/jquery-2.1.4.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/vue/1.0.10/vue.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/vue-resource/0.1.17/vue-resource.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.18.0/axios.min.js"></script>
<script>
    Vue.component('feedback', {
        data: function () {
            return {
                studies: {},
                selectedStudy : null,
                sadPercentage: 0,
                happyPercentage: 0,
                neutralPercentage: 0
            }
        },
        created () {
            this.getFeedback();
        },
        methods: {
            getFeedback: function () {
                var that = this
                axios.post('${pageContext.request.contextPath}/webservice/study/feedback', {
                },{
                    headers: { 'user_token': '${userApiToken}' }
                }).then(function (response) {
                    if(response.data.content){
                        var data = JSON.parse(response.data.message)
                        that.studies = data
                        that.calculatePercentage()
                        that.drawChart()
                    }
                }).catch(function (error) {
                    console.log(error);
                });
            },
            setSelectedStudy(study) {
                this.selectedStudy = this.studies[study]
            },
            calculatePercentage(){
                var total = 0
                var sad = 0
                var neutral = 0
                var happy = 0

                for (var study in this.studies) {
                    var tempStudy = this.studies[study]
                    if(tempStudy.experience === 'sad') {
                        sad = sad + 1
                    }
                    if(tempStudy.experience === 'neutral') {
                        neutral = neutral + 1
                    }
                    if(tempStudy.experience === 'happy') {
                        happy = happy + 1
                    }
                    total = total + 1
                }

                this.sadPercentage = Math.round((sad / total) * 100 * 100) / 100
                this.neutralPercentage = Math.round((neutral / total) * 100 * 100) / 100
                this.happyPercentage = Math.round((happy / total) * 100 * 100) / 100
            },
            drawChart(){
            }
        }
    })
    var feedbackVM = new Vue({
        el: 'body',
        data: {
        }
    })
</script>
