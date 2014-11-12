/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2014-Nov-11
 * Modified by:   conesa
 *
 *
 * Copyright 2014 EMBL-European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

var metLinkTimer = 0; // 0 is a safe "no timer" value
var chebiInfoDiv;

function scanCompoundLinks(){


    if (!chebiInfoDiv){
        // Add the placeholder div, and initialize compound

        $("body").append('<div id="chebiInfo"></div>');
        chebiInfoDiv = new Biojs.ChEBICompound({target: 'chebiInfo',width:'500px', height:'400px',proxyUrl:undefined, chebiDetailsUrl: './ebi/webservices/chebi/2.0/test/getCompleteEntity?chebiId='});
        $('#chebiInfo').hide();
    }


    $('.metLink').live('mouseenter', function(e) {
        // I'm assuming you don't want to stomp on an existing timer
        if (!metLinkTimer) {
            metLinkTimer = setTimeout(function(){loadMetabolite(e);}, 500); // Or whatever value you want
        }
    }).live('mouseleave', function() {
        // Cancel the timer if it hasn't already fired
        if (metLinkTimer) {
            clearTimeout(metLinkTimer);
            metLinkTimer = 0;

        }
        $('#chebiInfo').fadeOut('slow');
    });
}

function loadMetabolite(e) {
    // Clear this as flag there's no timer outstanding
    metLinkTimer = 0;

    var metlink;
    metlink = $(e.target);
    var metaboliteId = metlink.attr('identifier');
    var displayId = metaboliteId;

    // If it's a MTBLC....change it to chebi
    if (metaboliteId.indexOf("MTBLC")==0) metaboliteId = metaboliteId.replace("MTBLC", "CHEBI:");

    // If its a chebi id
    if (metaboliteId.indexOf("CHEBI:")==0){

        var offset = metlink.offset();
        var mouseX = offset.left + metlink.outerWidth() + 20;
        var mouseY = offset.top;

        //chebiId = metaboliteId;

        $('#chebiInfo img:last-child').remove;

        $('#chebiInfo').css({'top':mouseY,'left':mouseX,'float':'left','position':'absolute','z-index':10});
        $('#chebiInfo').fadeIn('slow');

        //chebiInfoDiv.setId(chebiId);
        chebiInfoDiv.setDisplayIdAndLoad(displayId,metaboliteId);
    }
}
