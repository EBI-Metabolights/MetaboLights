<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 7/8/14 4:27 PM
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
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
    /* data table styles */
    #grid { min-height: 40px; }
    /*.row{ clear: left; font-size: 11px; padding: 10px;}*/
    /*.header { clear: left; font-size: 11px}*/
    /*.row:nth-child(odd) { background: rgba(0,0,0,0.05); }*/
    /*.header { font-weight: bold; }*/
    .header .cell { text-transform: capitalize;  padding: 10px 10px; text-align: center}
    .cell { float: left; overflow: hidden; white-space: nowrap; padding: 10px 10px; }
    /*.col-0 { width: 120px; }*/
    .table{
        margin: 0;
        margin-bottom: 0 !important;
    }
    th {
        background-color: #8e8e8e;
        color: #fff;
    }
    th, td {
        border: #8e8e8e 0.3px solid !important;
         vertical-align: middle;
    }
    .table-row:hover{
        cursor: pointer;
    }
    .parcoords > canvas {
        font: 14px sans-serif;
        position: absolute;
    }
    .parcoords > canvas {
        pointer-events: none;
    }
    .parcoords text.label {
        cursor: default;
    }
    .parcoords rect.background:hover {
        fill: rgba(120,120,120,0.2);
    }
    .parcoords canvas {
        opacity: 1;
        transition: opacity 0.3s;
        -moz-transition: opacity 0.3s;
        -webkit-transition: opacity 0.3s;
        -o-transition: opacity 0.3s;
    }
    .parcoords canvas.faded {
        opacity: 0.25;
    }
    .parcoords {
        -webkit-touch-callout: none;
        -webkit-user-select: none;
        -khtml-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
        background-color: white;
    }

    .highlight {
        background-color: transparent !important;
        color: #000000;
    }
    .tableWrapper {
        overflow-x: scroll;
    }

    .label {
        font-size: 10px;
        padding-left: 10px;
    }

    form{
        padding: 0 !important;
    }

    .d3-tip {
        line-height: 1;
        padding: 12px;
        background: rgba(0, 0, 0, 0.8);
        color: #fff;
        border-radius: 2px;
        margin-left: 20px;
        font-size: 0.8em;
    }

    .warning-wrapper{
        height: 40vh;
        display: flex;
        justify-content: center;
        align-items: center;
    }


    #loading-wrapper{
        height: 40vh;
        display: flex;
        justify-content: center;
        align-items: center;
    }


    .factorsListWrapper{
        max-height: 600px;
        min-height: 20px;
        overflow-y: scroll;
    }
</style>
<div id="content" class="grid_24">
    <div class="container-fluid" id="parallelCoordinatesApp">
        <div class="collapse" id="dataDoesntExist">
           <div class="panel panel-default">
               <div class="panel-heading">
                   Parallel Coordinates
               </div>
               <div class="panel-body">
                   <div class="warning-wrapper">
                       <span>
                           <h4 class="text-center">
                               <i class="fa fa-warning"></i> {{ study }} parallel coordinates data doesnt exist
                           </h4>
                       </span>
                   </div>
               </div>
           </div>
        </div>
        <div id="studyParallelCoordinates" class="row">
            <div class="col-md-9">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        Parallel Coordinates {{study}}<i><a style="cursor: pointer;" @click="reset" class="pull-right"><i class="fa fa-retweet" aria-hidden="true"></i> Reset</a></i>
                    </div>
                    <div class="panel-body">
                        <div id="example" class="parcoords" style="width:100%;height:600px;">
                            <div id="loading">
                               <div id="loading-wrapper">
                                   <h4 class="text-center">
                                       <img src="${pageContext.request.contextPath}/img/beta_loading.gif" alt="Loading....">
                                   </h4>
                               </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        Study factors list
                    </div>
                    <div class="panel-body">
                        <div class="factorsListWrapper">
                            <ul>
                                <li v-if="key != 'files' && key != 'mafFile' && key != 'metabolites' && key != 'name' && key != 'id'" v-for="factor, key in pCoordData[0]">
                                    {{ key }}
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="row">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">Data Table</h3>
                        </div>
                        <div class="tableWrapper">
                        <table id="grid" class="table table-bordered table-hover">
                        </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="row">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                           <div class="row">
                               <h3 class="panel-title">
                                <span v-if="selectedFiles.length == rawFiles.length">
                                    <a @click="selectAll" style="cursor: pointer"><i class="fa fa-toggle-on"></i></a>
                                </span>
                                   <span v-else>
                                    <a @click="selectAll" style="cursor: pointer"><i class="fa fa-toggle-off"></i></a>
                                </span>
                                   &nbsp;Raw files
                                   <div class="pull-right">
                                       <a @click="download" style="cursor: pointer"><small><i class="fa fa-download"></i> Download files</small></a>
                                   </div>
                               </h3>
                           </div>
                        </div>
                        <div  v-if="rawFiles.length > 0">
                            <form id="downloadForm" :action="'${pageContext.request.contextPath}/'+ study +'/files/downloadSelFiles/'" method="post">
                                <table class="table table-bordered table-condensed table-hover">
                                <tr v-for="file, key in rawFiles">
                                    <td>
                                        <label>
                                            <input :selected="selectedFiles[key] == file" v-model="selectedFiles" class="fileCheckbox" :value="file" name="file" type="checkbox">
                                        </label>
                                        <a :href="'${pageContext.request.contextPath}/' +study + '/files/' + file">{{ file }}</a>
                                    </td>
                                </tr>
                                </table>
                            </form>
                        </div>
                        <div v-else class="panel-body">
                            No data available
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">MAFF files</h3>
                        </div>
                        <div class="tableWrapper">
                            <table v-if="mafFiles.length > 0" class="table table-bordered table-condensed table-hover">
                                <tr v-for="file in mafFiles">
                                    <td><a>{{ file }}</a></td>
                                </tr>
                            </table>
                            <div v-else class="panel-body">
                                No data available
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="row">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">Metabolites</h3>
                        </div>
                        <table v-if="metabolites.length > 0" class="table table-bordered table-condensed table-hover">
                            <tr  v-for="metabolite in metabolites">
                                <td v-html="metabolite"></td>
                            </tr>
                        </table>
                        <div v-else class="panel-body">
                            No data available
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="factorDistributionChart">
            <h4 class="text-center">Study Factors Distribution Chart</h4>
            <br>
            <div class="container">
                <hr>
                <p class="text-center">
                    Sort:
                    <select id="sortChart">
                        <option value="studyId">Study Id</option>
                        <option value="ascending">Factors Ascending</option>
                        <option value="descending">Factors Descending</option>
                    </select>
                    &emsp;
                    <label> <span style="min-width: 20px; padding: 0 5px;" class="legend-field nmr"><input type="radio" name="technology" value="NMR"> NMR</span></label>&emsp;
                    <label> <span style="min-width: 20px; color: #fff; margin-left: 10px; padding: 0 5px;" class="legend-field ms"><input type="radio" name="technology" value="MS"> MS</span></label>&emsp;
                    <label> <span style="min-width: 20px; color: #fff; margin-left: 10px; padding: 0 5px;" class="legend-field both"><input type="radio" name="technology" value="both" checked> NMR & MS</span></label>
                </p>
                <br>
            </div>
            <div class="container well">
                <div id="graphic"></div>
            </div>
        </div>
    </div>
</div>
<script src="//d3js.org/d3.v3.min.js"></script>
<script src="//syntagmatic.github.io/parallel-coordinates/examples/lib/d3.svg.multibrush.js"></script>
<script src="//syntagmatic.github.io/parallel-coordinates/d3.parcoords.js"></script>
<script src="//unpkg.com/vue@2.3.4" type="application/javascript"></script>
<script src="//labratrevenge.com/d3-tip/javascripts/d3.tip.v0.6.3.js"></script>
<script src="${pageContext.request.contextPath}/javascript/divgrid.js"></script>

<script>
    var app = new Vue({
        el: '#parallelCoordinatesApp',
        data: {
            study: null,
            pCoordData: [],
            rawFiles: [],
            mafFiles: [],
            metabolites: [],
            selectedFiles: []
        },
        mounted: function() {
            this.getParameterByName('study');
        },
        methods: {
            download: function () {
                if(this.selectedFiles.length > 0 ) {
                    document.getElementById("downloadForm").submit();
                }else{
                    alert("Please select the files to download");
                }
            },
            selectAll: function(){
                if(this.selectedFiles.length == this.rawFiles.length){
                    this.selectedFiles = [];
                }else{
                    this.selectedFiles = this.rawFiles;
                }
            },
            reset:function(){
                this.loadParallelCoordinates();
            },
            getParameterByName: function(name, url) {
                if (!url) {
                    url = window.location.href;
                }
                name = name.replace(/[\[\]]/g, "\\$&");
                var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
                    results = regex.exec(url);

                if (results){
                    this.study = decodeURIComponent(results[2].replace(/\+/g, " "));
                    if (this.study){
                        $("#factorDistributionChart").hide();
                        this.loadParallelCoordinates();
                    }
                }else{
                    this.loadBarChart();
                }

            },

            loadBarChart: function(){
                $("#studyParallelCoordinates").hide();

                var data = [];

                function draw(sortValue){
                    d3.selectAll("svg").remove();
                    d3.json('${pageContext.request.contextPath}/webservice/study/parallelCoordinatesData?study=""', function(error, jsonData) {

                        data = JSON.parse(jsonData.content);

                        filterValue = document.querySelector('input[name="technology"]:checked').value;

                        if(filterValue == 'NMR'){
                            data = data.filter(function (entry) {
                                return entry.Technology.indexOf("NMR spectroscopy") > -1;
                            });
                        }else if(filterValue == "MS"){
                            data  = data.filter(function(entry){
                                return entry.Technology.indexOf("mass spectrometry") > -1;
                            });
                        }

                        if(sortValue == "studyId"){
                            data.sort(function(x, y){
                                return d3.descending(parseInt(x.Study.replace("MTBLS", "")),parseInt(y.Study.replace("MTBLS", "")));
                            });
                        }else if(sortValue == "ascending"){
                            data.sort(function(x, y){
                                return d3.descending(x.TotalFactors,y.TotalFactors);
                            });
                        }else{
                            data.sort(function(x, y){
                                return d3.ascending(x.TotalFactors,y.TotalFactors);
                            });
                        }

                        //set up svg using margin conventions - we'll need plenty of room on the left for labels
                        var margin = {
                            top: -90,
                            right: 25,
                            bottom: -90,
                            left: 100
                        };

                        var width = 960 - margin.left - margin.right,
                            height = (data.length * 20) - margin.top - margin.bottom;

                        var svg = d3.select("#graphic").append("svg")
                            .attr("width", width + margin.left + margin.right)
                            .attr("height", height + margin.top + margin.bottom)
                            .append("g")
                            .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

                        var tip = d3.tip()
                            .attr('class', 'd3-tip')
                            .offset([-10, 0])
                            .html(function(d) {
                                var tempString = "";
                                for (var key in d.Factors){
                                    tempString =  tempString + "<span style='color:red; padding: 10px 0'>" + key + "</span><small style='color:#f1f1f4;'> " + d.Factors[key]['values'] + "</small><br>"
                                }
                                return "<strong>" + d.Study + "&nbsp;Factors:</strong><br><br>" + tempString;
                            })

                        tip.direction('e')

                        svg.call(tip);

                        var x = d3.scale.linear()
                            .range([0, width])
                            .domain([0, d3.max(data, function (d) {
                                return d.TotalFactors;
                            })]);

                        var y = d3.scale.ordinal()
                            .rangeRoundBands([height, 0], .1)
                            .domain(data.map(function (d) {
                                return d.Study;
                            }));

                        //make y axis to show bar names
                        var yAxis = d3.svg.axis()
                            .scale(y)
                            .tickSize(0)
                            .orient("left");


                        var gy = svg.append("g")
                            .attr("class", "y axis")
                            .call(yAxis)
                            .attr("transform", "translate(-10,0)");

                        var bars = svg.selectAll(".bar")
                            .data(data)
                            .enter()
                            .append("g")

                        //append rects
                        bars.append("rect")
                            .attr("y", function (d) {
                                return y(d.Study);
                            })
                            .attr("fill", function(d) {
                                if ((d.Technology).indexOf("NMR spectroscopy") > -1 && ((d.Technology).indexOf("mass spectrometry") > -1)) {
                                    return "#fe446d";
                                } else if(((d.Technology).indexOf("NMR spectroscopy") > -1)){
                                    return "#FFB520";
                                }else{
                                    return "#216BD6";
                                }
                            })
                            .attr("height", y.rangeBand())
                            .attr("x", 0)
                            .attr("width", function (d) {
                                return x(d.TotalFactors);
                            })
                            .style("cursor", "pointer")
                            .on("click", function(d){
                                var win = window.open("${pageContext.request.contextPath}/parallelCoordinates?study=" + d.Study);
                                win.focus();
                            })
                            .on('mouseover', tip.show)
                            .on('mouseout', tip.hide)

                        //add a value label to the right of each bar
                        bars.append("text")
                            .attr("class", "label")
                            //y position of the label is halfway down the bar
                            .attr("y", function (d) {
                                return y(d.Study) + y.rangeBand() / 2 + 4;
                            })
                            //x position is 3 pixels to the right of the bar
                            .attr("x", function (d) {
                                return x(d.TotalFactors) + 3;
                            })
                            .html(function (d) {
                                return d.TotalFactors;
                            });

                    });
                }

                d3.select("select").on("change", change);

                d3.selectAll("input").on("change", change);

                draw("studyId");

                function change() {
                    var e = document.getElementById("sortChart");
                    var sortValue = e.options[e.selectedIndex].value;
                    draw(sortValue);
                }
            },

            loadParallelCoordinates: function(){

                d3.selectAll("svg").remove();

                var blue_to_brown = d3.scale.linear()
                    .domain([9, 50])
                    .range(["steelblue", "brown"])
                    .interpolate(d3.interpolateLab);

                var color = function(d) { return blue_to_brown(d['economy (mpg)']); };

                var parcoords = d3.parcoords()("#example")
                    .color(color)
                    .alpha(0.4);

                var that = this;

                // load csv file and create the chart
                d3.json('${pageContext.request.contextPath}/webservice/study/parallelCoordinatesData?study="' + this.study +'"', function(data) {

                    if(data.content == ""){
                        $("#studyParallelCoordinates").hide();
                        $("#dataDoesntExist").toggle();
                        return
                    }

                    $('#loading').empty();

                    data = JSON.parse(data.content)

                    that.pCoordData = data;

                    var tempData = JSON.parse(JSON.stringify(data));

                    that.getTheDownloadableFiles(tempData);

                    tempData.forEach(function(v){ delete v.files });
                    tempData.forEach(function(v){ delete v.mafFile });
                    tempData.forEach(function(v){ delete v.metabolites });

                    parcoords
                        .data(tempData)
                        .hideAxis(["name", "id"])
                        .composite("darker")
                        .render()
                        .shadows()
                        .reorderable()
                        .color("rgba(0,200,0,0.5)")
                        .brushMode("1D-axes-multi");  // enable brushing


                    var grid = d3.divgrid();
                    d3.select("#grid")
                        .datum(tempData)
                        .call(grid)
                        .selectAll(".table-row")
                        .on({
                            "mouseover": function(d) { parcoords.highlight([d]); },
                            "mouseout": parcoords.unhighlight
                        });

                    // update data table on brush event
                    parcoords.on("brush", function(d) {
                        that.getTheDownloadableFiles(d)
                        d3.select("#grid")
                            .datum(d)
                            .call(grid)
                            .selectAll(".table-row")
                            .on({
                                "mouseover": function(d) { parcoords.highlight([d]); },
                                "mouseout": parcoords.unhighlight
                            });
                    });

                });

                var sltBrushMode = d3.select('#sltBrushMode')

                sltBrushMode.selectAll('option')
                    .data(parcoords.brushModes())
                    .enter()
                    .append('option')
                    .text(function(d) { return d; });

                sltBrushMode.on('change', function() {
                    parcoords.brushMode(this.value);
                    switch(this.value) {
                        case 'None':
                            d3.select("#pStrums").style("visibility", "hidden");
                            d3.select("#lblPredicate").style("visibility", "hidden");
                            d3.select("#sltPredicate").style("visibility", "hidden");
                            d3.select("#btnReset").style("visibility", "hidden");
                            break;
                        case '2D-strums':
                            d3.select("#pStrums").style("visibility", "visible");
                            break;
                        default:
                            d3.select("#pStrums").style("visibility", "hidden");
                            d3.select("#lblPredicate").style("visibility", "visible");
                            d3.select("#sltPredicate").style("visibility", "visible");
                            d3.select("#btnReset").style("visibility", "visible");
                            break;
                    }
                });
            },

            getTheDownloadableFiles: function (d) {

                var that = this;
                var sortedRawFilesArray = []
                d.forEach(function(m){
                    that.pCoordData.forEach(function (n) {
                        if (m.id == n.id){
                            sortedRawFilesArray = sortedRawFilesArray.concat(n.files);
                        }
                    })
                })


                this.rawFiles = this.unique(sortedRawFilesArray);

                var sortedMAFFilesArray = []
                d.forEach(function(m){
                    that.pCoordData.forEach(function (n) {
                        if (m.id == n.id){
                            sortedMAFFilesArray = sortedMAFFilesArray.concat([n.mafFile]);
                        }
                    })
                })

                this.mafFiles = this.unique(sortedMAFFilesArray);


                var sortedMetabolitesArray = []
                d.forEach(function(m){
                    that.pCoordData.forEach(function (n) {
                        if (m.id == n.id){
                            sortedMetabolitesArray = sortedMetabolitesArray.concat( n.metabolites );
                        }
                    })
                })

                this.metabolites = this.unique(sortedMetabolitesArray);

                this.metabolites = this.annotateChEBIIds(this.metabolites);

            },

            annotateChEBIIds: function(metabolites) {
                var annotatedMetabolites = []
                metabolites.forEach(function (metabolite) {
                    var ChEBIMatch = metabolite.match(/\(([^)]+)\)/);
                    if (ChEBIMatch){
                        var ChebiId = metabolite.match(/\(([^)]+)\)/)[1];
                        if (ChebiId.indexOf("CHEBI") !== -1){
                            annotatedMetabolites.push("<a target='_blank' href='//www.ebi.ac.uk/chebi/searchId.do?chebiId="+ChebiId+"'>" + metabolite + "</a>");
                        }else{
                            annotatedMetabolites.push( metabolite );
                        }
                    }else{
                        annotatedMetabolites.push( metabolite );
                    }
                })

                return annotatedMetabolites;
            },

            unique: function(list) {
                var result = [];
                $.each(list, function(i, e) {
                    if ($.inArray(e, result) == -1) result.push(e);
                });
                return result;
            }
        }
    })
</script>