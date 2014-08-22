/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 8/21/14 12:20 PM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

/**
 * Created by conesa on 21/08/2014.
 */

//    Declare an array to store the Pahtways
var pathwaysInfo = new Array();
var currentMTBLC = new String();

// Function to invoke once object is loaded
function objectLoaded(){

    // get the svg element
    var svg = $("#pathwayContainer").find("svg");

    $(svg).find('ellipse[id^=MTBLC]').each(function () {

        var id = this.getAttribute('id');

        if (id == currentMTBLC) {
            this.setAttribute("class", "svgSpeciesCurrent");
        }
    });


    $(svg).find('text[id^=MTBLC]').each(function () {

        var id = this.getAttribute('id');

        if (id != currentMTBLC) {

            this.setAttribute("class", "svgTextLink");

            $(this).mouseup(function () {

                window.location.href = id;

            });
        }
    });

};

function bindPathways(){
    $("#pathwayList").change(function (e) {
        e.preventDefault();

        /* Display the image */
        var pathwayId = $(this).val();

        /* Show info in the info div*/
        var pathwayInfoDiv = $('#pathwayInfo');
        /* Get the selected option */
        var option = $(this).find(":selected");


        /* Get the pathway object (json element)*/
        var pathway = pathwaysInfo[$(this)[0].selectedIndex];

        if (pathway) {

            var html = "generated from " + pathway.source + "<br/>for  " + pathway.species;

            $.each(pathway.properties, function () {

                html = html + "<br/>" + this.name + ":"

                if (this.value.indexOf("http:") == 0) {
                    html = html + "<a href=\"" + this.value + "\">" + this.value + "</a>"
                } else {
                    html = html + this.value;
                }

            });
            pathwayInfoDiv.html(html);

        }

        //        var object = $('#pathwayContainer');
        ////        (object).attr('data', 'pathway/' + pathwayId + '/svg');
        //        (object).attr('src', 'pathway/' + pathwayId + '/svg');
        loadPathway('pathway/' + pathwayId + '/svg');
    });

    // And now fire change event when the DOM is ready
    $('#pathwayList').trigger('change');

}

function loadPathway(url){

    d3.xml(url,
        function(error, documentFragment) {

            if (error) {console.log(error); return;}

            var svgNode = documentFragment
                .getElementsByTagName("svg")[0];
            //use plain Javascript to extract the node

            var pathwayContainer = $("#pathwayContainer")[0];

            // Remove all children
            pathwayContainer.innerHTML = "";
            pathwayContainer.appendChild(svgNode);

            objectLoaded();

        });

}
