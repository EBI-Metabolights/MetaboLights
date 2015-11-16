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
<div class="container">
    <h2 class="row">Curator tools page</h2>
    <hr>
    <div>

        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active"><a href="#studyliterature" aria-controls="studyliterature" role="tab" data-toggle="tab">Study Literature</a></li>
        </ul>

        <!-- Tab panes -->
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="studyliterature">
                <div class="row">
                    <div class="col-sm-12">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <table class="mltable table table-bordered">
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Study</th>
                                        <th>Study Description</th>
                                        <th>Study Protocols</th>
                                    </tr>
                                    </thead>
                                    <tbody id="study-wrapper">
                                    </tbody>
                                </table>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<script>
    $('#myTabs a').click(function (e) {
        e.preventDefault()
        $(this).tab('show')
    })

    var studies = [];
    $.ajax({
        url: 'http://www.ebi.ac.uk/metabolights/webservice/study/list',
        async: false,
        headers: { 'user_token': '2bdb7fb7-bcb6-4e2b-a254-5969904d62de' },
        error: function() {
            $('#info').html('<div class="alert alert-danger alert-dismissible fade in" role="alert"><button class="close" aria-label="Close" data-dismiss="alert" type="button"><span aria-hidden="true">×</span></button><strong>Error has occured</strong></div>');
        },
        success: function(data) {
            studies = String(data['content']).split(',');
        },
        type: 'POST'
    });

    function getStudiesData(){
        var studieswop = [];
        $('#studieswoploading').css("display", "block");
        var nos = studies.length;
        for (var i = 0; i<50; i++) {
            try {

                console.log(studies[i] + '--');
                var id_string = '<tr><th scope="row">' + (i + 1) + '</th><td>' + studies[i] + '</td><td id="' + studies[i] + '_description" >&nbsp;</td><td class="col-md-6 wrap-content" id="' + studies[i] + '_protocol" class="protocol_div">&nbsp;</td></tr>';
                $('#study-wrapper').append(id_string);
                $.ajax({
                    url: 'http://www.ebi.ac.uk/metabolights/webservice/study/' + studies[i] + '/full',
                    headers: {'user_token': '2bdb7fb7-bcb6-4e2b-a254-5969904d62de'},
                    async: false,
                    error: function () {
                        $('#info').html('<div class="alert alert-danger alert-dismissible fade in" role="alert"><button class="close" aria-label="Close" data-dismiss="alert" type="button"><span aria-hidden="true">×</span></button><strong>Error has occured</strong></div>');
                    },
                    success: function (data) {
                        study = data;
                        var studyDescription = study['content']['description'];
                        var studyProtocols = study['content']['protocols'];
                        var sprot = '';
                        for (var j = 0; j < studyProtocols.length; j++) {
                            sprot = sprot + studyProtocols[j]['name'] + '</br>' + studyProtocols[j]['description'] + '<hr>'
                        }
                        $('#' + studies[i] + '_description').append(studyDescription);
                        $('#' + studies[i] + '_protocol').append(sprot);

                    },
                    type: 'POST'
                });
            }
            catch (err) {
                $('#info').html('<div class="alert alert-danger alert-dismissible fade in" role="alert"><button class="close" aria-label="Close" data-dismiss="alert" type="button"><span aria-hidden="true">×</span></button><strong>Error has occured</strong></div>');
            }
        }

    }
    getStudiesData();
</script>