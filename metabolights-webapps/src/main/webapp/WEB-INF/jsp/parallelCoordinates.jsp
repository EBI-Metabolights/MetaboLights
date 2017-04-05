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


<script src="//cdnjs.cloudflare.com/ajax/libs/d3/3.4.13/d3.min.js"></script>
<script src="http://labratrevenge.com/d3-tip/javascripts/d3.tip.v0.6.3.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">


<script src="${pageContext.request.contextPath}/javascript/nv.d3.js"></script>
<script src="${pageContext.request.contextPath}/javascript/d3.parcoords.js"></script>
<script src="${pageContext.request.contextPath}/javascript/divgrid.js"></script>


<style>
    #PContainer a:hover {
        color: #f1f1f4 !important;
        cursor: pointer;
    }

    #downloadList a:hover {
        color: #000 !important;
        cursor: pointer;
    }

    #example {
        min-height: 500px;
        margin: 12px 0;
    }

    .highlight {
        background-color: transparent !important;
        color: inherit;
    }

    pre {
        color: #444;
        font-family: Ubuntu Mono, Monaco, monospace;
        padding: 4px 8px;
        background: #f2f2f2;
        border: 1px solid #ccc;
    }
    h1 small {
        font-weight: normal;
        font-size: 0.5em;
    }
    h3 {
        margin-top: 40px;
    }
    .float {
        float: left;
    }
    .centered {
        text-align: center;
    }
    .hide {
        display: none;
    }
    input {
        font-size: 16px;
    }

    /* data table styles */
    #grid { min-height: 40px; }
    .row{ clear: left; font-size: 11px; padding: 10px;}
    .header { clear: left; font-size: 11px}
    .row:nth-child(odd) { background: rgba(0,0,0,0.05); }
    .header { font-weight: bold; }
    .cell { float: left; overflow: hidden; white-space: nowrap; width: 160px; height: 18px; }
    .col-0 { width: 120px; }


    /* parcoords styles */
    .parcoords > svg, .parcoords > canvas {
        font: 11px "Source Code Pro", Consolas, monaco, monospace;
        position: absolute;
    }
    .parcoords > canvas {
        pointer-events: none;
    }

    .parcoords text.label {
        cursor: default;
    }

    .parcoords rect.background {
        fill: transparent;
    }
    .parcoords rect.background:hover {
        fill: rgba(120,120,120,0.2);
    }
    .parcoords .resize rect {
        fill: rgba(0,0,0,0.1);
    }
    .parcoords rect.extent {
        fill: rgba(255,255,255,0.25);
        stroke: rgba(0,0,0,0.6);
    }
    .parcoords .axis line, .parcoords .axis path {
        fill: none;
        stroke: #454545;
        shape-rendering: crispEdges;
    }
    .parcoords canvas {
        opacity: 1;
        -moz-transition: opacity 0.5s;
        -webkit-transition: opacity 0.5s;
        -o-transition: opacity 0.5s;
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
    }

    .label {
        font-size: 10px;
        padding-left: 10px;
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

    .header .cell{
        text-transform: capitalize;
        font-weight: bold;
        color: #337AB7;
    }

    .cell{
        text-align: center;
    }
</style>


<div id="content" class="grid_24">
    <div class="container-fluid">
        <div id="PContainer">
            <h3 class="text-center"><b><span id="studyId"></span></b> Parallel Coordinates</h3><br>
            <div class="container">
                <div class="well">
                <div id="example" class="parcoords"></div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <h3 class="panel-title">Data Table <i><a id="resetPC" class="pull-right"><i class="fa fa-retweet" aria-hidden="true"></i> Reset</a></i></h3>
                            </div>
                            <div class="panel-body">
                                <div id="grid"></div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h3 class="panel-title">Download raw data</h3>
                            </div>
                            <div class="panel-body">
                                <ul class="list-group" id="downloadList">
                                    <li class="list-group-item">No raw files selected</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h3 class="panel-title">Metabolites Identified</h3>
                            </div>
                            <div class="panel-body">
                                <ul class="list-group" id="metabolitesList">
                                    <li class="list-group-item">No metabolites identified</li>
                                </ul>
                            </div>
                        </div>
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h3 class="panel-title">MAF files</h3>
                            </div>
                            <div class="panel-body">
                                <ul class="list-group" id="downloadMAFList">
                                    <li class="list-group-item">No MAF files selected</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="factorDistributionChart">
            <h4 class="text-center">Study Factors Distribution Chart</h4>
            <br>
            <p class="text-center">
                <span><span class="legend-field nmr">&emsp;</span>NMR</span>&emsp;
                <span><span class="legend-field ms">&emsp;</span>MS</span>&emsp;
                <span><span class="legend-field both">&emsp;</span>NMR & MS</span>
            </p>
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
                    <label> <input type="radio" name="technology" value="NMR"> NMR</label>&emsp;
                    <label> <input type="radio" name="technology" value="MS"> MS</label>&emsp;
                    <label> <input type="radio" name="technology" value="both" checked> NMR & MS</label>
                </p>
                <br>
            </div>
            <div class="container well">
                <div id="graphic"></div>
            </div>
        </div>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script id="brushing">// quantitative colour scale

    var data = [];

    $( "#resetPC" ).click(function() {
        loadPC();
    });

    var color = "#f00";

    function getParameterByName(name, url) {
        if (!url) {
            url = window.location.href;
        }
        name = name.replace(/[\[\]]/g, "\\$&");
        var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, " "));
    }

    var study = getParameterByName('study');

    if (study == "" || study == undefined){

        $("#PContainer").hide();

        var data = [];

        function draw(sortValue){
            d3.selectAll("svg").remove();
            d3.json('/metabolights/webservice/study/parallelCoordinatesData?study=""', function(error, jsonData) {

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
                        var win = window.open("/metabolights/parallelCoordinates?study=" + d.Study);
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

    }else{

        $("#factorDistributionChart").hide();

        $("#studyId").text(study);

        d3.json('/metabolights/webservice/study/parallelCoordinatesData?study="'+study+'"', function(jsonData) {
            data = JSON.parse(jsonData.content)

            loadPC();

            // $('#example').height(data.length * 15);
        });

    }

    function loadPC(){

        d3.selectAll("svg").remove();

        var parcoords = d3.parcoords()("#example")
            .color(color)
            .alpha(0.7);



        var tempData = JSON.parse(JSON.stringify(data));

        tempData.forEach(function(v){ delete v.files });
        tempData.forEach(function(v){ delete v.mafFile });
        tempData.forEach(function(v){ delete v.metabolites });


        getTheDownloadableFiles(tempData);

        parcoords
            .data(tempData)
            .hideAxis(['id'])
            .render()
            .brushMode("1D-axes");  // enable brushing

        parcoords.reorderable()

        // create data table, row hover highlighting
        var grid = d3.divgrid();
        d3.select("#grid")
            .datum(tempData)
            .call(grid)
            .selectAll(".row")
            .on({
                "mouseover": function(d) { parcoords.highlight([d]); },
                "mouseout": parcoords.unhighlight
            });

        // update data table on brush event
        parcoords.on("brush", function(d) {
            getTheDownloadableFiles(d)
            d3.select("#grid")
                .datum(d)
                .call(grid)
                .selectAll(".row")
                .on({
                    "mouseover": function(d) { parcoords.highlight([d]); },
                    "mouseout": parcoords.unhighlight
                });
        });

    }

    function getTheDownloadableFiles(d) {
        var sortedRawFilesArray = []
        d.forEach(function(m){
            data.forEach(function (n) {
                if (m.id == n.id){
                    sortedRawFilesArray = sortedRawFilesArray.concat(n.files);
                }
            })
        })

        $("#downloadList").html("");
        unique(sortedRawFilesArray).forEach(function (f) {
            $("#downloadList").append('<li class="list-group-item"><a href="/metabolights/'+study+'/files/'+f+'">'+f+'</a></li>');
        })


        var sortedMAFFilesArray = []
        d.forEach(function(m){
            data.forEach(function (n) {
                if (m.id == n.id){
                    sortedMAFFilesArray = sortedMAFFilesArray.concat([n.mafFile]);
                }
            })
        })

        $("#downloadMAFList").html("");

        unique(sortedMAFFilesArray).forEach(function (f) {
            $("#downloadMAFList").append('<li class="list-group-item"><a href="/metabolights/'+study+'/files/'+f+'">'+f+'</a></li>');
        })


        var sortedMetabolitesArray = []
        d.forEach(function(m){
            data.forEach(function (n) {
                if (m.id == n.id){
                    sortedMetabolitesArray = sortedMetabolitesArray.concat(n.metabolites);
                }
            })
        })

        $("#metabolitesList").html("");

        unique(sortedMetabolitesArray).forEach(function (f) {
            $("#metabolitesList").append('<li class="list-group-item">'+f+'</li>');
        })
    }

function unique(list) {
    var result = [];
    $.each(list, function(i, e) {
        if ($.inArray(e, result) == -1) result.push(e);
    });
    return result;
}

</script>