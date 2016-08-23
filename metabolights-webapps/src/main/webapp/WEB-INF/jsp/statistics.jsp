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


<link rel="stylesheet" href="${pageContext.request.contextPath}/css/nv.d3.css" type="text/css"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.2/d3.min.js" charset="utf-8"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="${pageContext.request.contextPath}/javascript/nv.d3.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/metabolights.css" type="text/css"/>


<style>
    text {
        font: 12px sans-serif;
    }

    svg {
        display: block;
    }
    /* html, body, #chart1, svg {
         margin: 0px;
         padding: 0px;
         height: 100%;
         width: 100%;
     }*/
    #chart1 svg {
        height: 400px;
    }

</style>

<div id="content" class="grid_24">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <h2><spring:message code="msg.statistics"/></h2>
                <hr>
                <div class="col-md-12">
                    <h3 class="well"><spring:message code="msg.statistics.graph"/></h3>
                    <div id="chart1" class='with-3d-shadow with-transitions well'>
                        <svg> </svg>
                    </div>
                </div>
                <div class="clearfix">&nbsp;</div>
                <hr>
                <div class="col-md-6">


                    <c:choose>
                        <c:when test="${not empty dataList}">
                            <div>
                            <h4  class="well"><spring:message code="msg.statistics.data"/></h4>
                            <table class="table table-bordered table-striped mltable">
                                <tbody>
                                    <c:forEach var="dataEntries" items="${dataList}">
                                        <tr>
                                            <th scope="row">${dataEntries.displayName}</th>
                                            <td>${dataEntries.displayValue}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            </div>
                        </c:when>
                    </c:choose>

                    <c:choose>
                        <c:when test="${not empty submittersList}">
                            <h4 class="well"><spring:message code="msg.statistics.users"/></h4>
                            <table class="table table-bordered table-striped mltable">
                                <tbody>
                                <c:forEach var="submitters" items="${submittersList}">
                                    <tr>
                                        <th scope="row">${submitters.displayName}</th>
                                        <td>${submitters.displayValue}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </c:when>
                    </c:choose>

                </div>
                <div class="col-md-6">
                    <c:choose>
                        <c:when test="${not empty topSubList}">
                            <h4 class="well"><spring:message code="msg.statistics.topSub"/></h4>
                            <table class="table table-bordered table-striped mltable">
                                <tbody>
                                <c:forEach var="topsubmitters" items="${topSubList}">
                                    <tr>
                                        <%--<th scope="row">${topsubmitters.displayName}</th>--%>
                                        <th scope="row">
                                            <a href="search?users.fullName=${topsubmitters.displayName}">${topsubmitters.displayName}</a>
                                        </th>
                                        <td>${topsubmitters.displayValue}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </c:when>
                    </c:choose>
                </div>
                <div class="clearfix">&nbsp;</div>
                <hr>
                <div class="col-md-12">
                    <c:choose>
                        <c:when test="${not empty infoList}">
                            <h4 class="well"><spring:message code="msg.statistics.info"/></h4>
                            <table class="table table-bordered table-striped mltable">
                                <c:forEach var="info" items="${infoList}">
                                        <tr>
                                            <th scope="row">${info.displayName}</th>
                                            <td>${info.displayValue}</td>
                                        </tr>
                                </c:forEach>
                            </table>
                        </c:when>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script>

    var testdata = [
        {
            "key" : "Number of studies" ,
            "bar": true,
            "values" : [ [ 1136005200000 , 1271000.0] , [ 1138683600000 , 1271000.0] , [ 1141102800000 , 1271000.0] , [ 1143781200000 , 0] , [ 1146369600000 , 0] , [ 1149048000000 , 0] , [ 1151640000000 , 0] , [ 1154318400000 , 0] , [ 1156996800000 , 0] , [ 1159588800000 , 3899486.0] , [ 1162270800000 , 3899486.0] , [ 1164862800000 , 3899486.0] , [ 1167541200000 , 3564700.0] , [ 1170219600000 , 3564700.0] , [ 1172638800000 , 3564700.0] , [ 1175313600000 , 2648493.0] , [ 1177905600000 , 2648493.0] , [ 1180584000000 , 2648493.0] , [ 1183176000000 , 2522993.0] , [ 1185854400000 , 2522993.0] , [ 1188532800000 , 2522993.0] , [ 1191124800000 , 2906501.0] , [ 1193803200000 , 2906501.0] , [ 1196398800000 , 2906501.0] , [ 1199077200000 , 2206761.0] , [ 1201755600000 , 2206761.0] , [ 1204261200000 , 2206761.0] , [ 1206936000000 , 2287726.0] , [ 1209528000000 , 2287726.0] , [ 1212206400000 , 2287726.0] , [ 1214798400000 , 2732646.0] , [ 1217476800000 , 2732646.0] , [ 1220155200000 , 2732646.0] , [ 1222747200000 , 2599196.0] , [ 1225425600000 , 2599196.0] , [ 1228021200000 , 2599196.0] , [ 1230699600000 , 1924387.0] , [ 1233378000000 , 1924387.0] , [ 1235797200000 , 1924387.0] , [ 1238472000000 , 1756311.0] , [ 1241064000000 , 1756311.0] , [ 1243742400000 , 1756311.0] , [ 1246334400000 , 1743470.0] , [ 1249012800000 , 1743470.0] , [ 1251691200000 , 1743470.0] , [ 1254283200000 , 1519010.0] , [ 1256961600000 , 1519010.0] , [ 1259557200000 , 1519010.0] , [ 1262235600000 , 1591444.0] , [ 1264914000000 , 1591444.0] , [ 1267333200000 , 1591444.0] , [ 1270008000000 , 1543784.0] , [ 1272600000000 , 1543784.0] , [ 1275278400000 , 1543784.0] , [ 1277870400000 , 1309915.0] , [ 1280548800000 , 1309915.0] , [ 1283227200000 , 1309915.0] , [ 1285819200000 , 1331875.0] , [ 1288497600000 , 1331875.0] , [ 1291093200000 , 1331875.0] , [ 1293771600000 , 1331875.0] , [ 1296450000000 , 1154695.0] , [ 1298869200000 , 1154695.0] , [ 1301544000000 , 1194025.0] , [ 1304136000000 , 1194025.0] , [ 1306814400000 , 1194025.0] , [ 1309406400000 , 1194025.0] , [ 1312084800000 , 1194025.0] , [ 1314763200000 , 1244525.0] , [ 1317355200000 , 475000.0] , [ 1320033600000 , 475000.0] , [ 1322629200000 , 475000.0] , [ 1325307600000 , 690033.0] , [ 1327986000000 , 690033.0] , [ 1330491600000 , 690033.0] , [ 1333166400000 , 514733.0] , [ 1335758400000 , 514733.0]]
        },
        {
            "key" : "Study size" ,
            "bar" : false,
            "values" : [ [ 1136005200000 , 71.89] , [ 1138683600000 , 75.51] , [ 1141102800000 , 68.49] , [ 1143781200000 , 62.72] , [ 1146369600000 , 70.39] , [ 1149048000000 , 59.77] , [ 1151640000000 , 57.27] , [ 1154318400000 , 67.96] , [ 1156996800000 , 67.85] , [ 1159588800000 , 76.98] , [ 1162270800000 , 81.08] , [ 1164862800000 , 91.66] , [ 1167541200000 , 84.84] , [ 1170219600000 , 85.73] , [ 1172638800000 , 84.61] , [ 1175313600000 , 92.91] , [ 1177905600000 , 99.8] , [ 1180584000000 , 121.191] , [ 1183176000000 , 122.04] , [ 1185854400000 , 131.76] , [ 1188532800000 , 138.48] , [ 1191124800000 , 153.47] , [ 1193803200000 , 189.95] , [ 1196398800000 , 182.22] , [ 1199077200000 , 198.08] , [ 1201755600000 , 135.36] , [ 1204261200000 , 125.02] , [ 1206936000000 , 143.5] , [ 1209528000000 , 173.95] , [ 1212206400000 , 188.75] , [ 1214798400000 , 167.44] , [ 1217476800000 , 158.95] , [ 1220155200000 , 169.53] , [ 1222747200000 , 113.66] , [ 1225425600000 , 107.59] , [ 1228021200000 , 92.67] , [ 1230699600000 , 85.35] , [ 1233378000000 , 90.13] , [ 1235797200000 , 89.31] , [ 1238472000000 , 105.12] , [ 1241064000000 , 125.83] , [ 1243742400000 , 135.81] , [ 1246334400000 , 142.43] , [ 1249012800000 , 163.39] , [ 1251691200000 , 168.21] , [ 1254283200000 , 185.35] , [ 1256961600000 , 188.5] , [ 1259557200000 , 199.91] , [ 1262235600000 , 210.732] , [ 1264914000000 , 192.063] , [ 1267333200000 , 204.62] , [ 1270008000000 , 235.0] , [ 1272600000000 , 261.09] , [ 1275278400000 , 256.88] , [ 1277870400000 , 251.53] , [ 1280548800000 , 257.25] , [ 1283227200000 , 243.1] , [ 1285819200000 , 283.75] , [ 1288497600000 , 300.98] , [ 1291093200000 , 311.15] , [ 1293771600000 , 322.56] , [ 1296450000000 , 339.32] , [ 1298869200000 , 353.21] , [ 1301544000000 , 348.5075] , [ 1304136000000 , 350.13] , [ 1306814400000 , 347.83] , [ 1309406400000 , 335.67] , [ 1312084800000 , 390.48] , [ 1314763200000 , 384.83] , [ 1317355200000 , 381.32] , [ 1320033600000 , 404.78] , [ 1322629200000 , 382.2] , [ 1325307600000 , 405.0] , [ 1327986000000 , 456.48] , [ 1330491600000 , 542.44] , [ 1333166400000 , 599.55] , [ 1335758400000 , 583.98] ]
        }
    ].map(function(series) {
        series.values = series.values.map(function(d) { return {x: d[0], y: d[1] } });
        return series;
    });

    var testdata2 = ${statsJson}. map(function(series) {
        series.values = series.values.map(function(d) { return {x: d[0], y: d[1] } });
        return series;
    });

    var chart;
    nv.addGraph(function() {
        chart = nv.models.linePlusBarChart()
                .margin({top: 50, right: 80, bottom: 30, left: 80})
                .legendRightAxisHint(' [Using Right Axis]')
                .color(d3.scale.category10().range());

        chart.xAxis.tickFormat(function(d) {
            return d3.time.format('%x')(new Date(d))
        }).showMaxMin(false);

        chart.y1Axis.axisLabel('Size in GB').axisLabelDistance(5);
        chart.y2Axis.axisLabel('Number of studies');
        chart.y2Axis.tickFormat(function(d) { return d3.format(',f')(d) });       //.axisLabel('Size in MB')
        chart.bars.forceY([0]).padData(false);

        chart.x2Axis.tickFormat(function(d) {
            return d3.time.format('%x')(new Date(d))
        }).showMaxMin(false);

        d3.select('#chart1 svg')
                .datum(testdata2)
                .transition().duration(500).call(chart);
        chart.focusEnable(!chart.focusEnable());
        chart.update();

        nv.utils.windowResize(chart.update);

        chart.dispatch.on('stateChange', function(e) { nv.log('New State:', JSON.stringify(e)); });

        return chart;
    });

</script>