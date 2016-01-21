/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 8/21/14 1:34 PM
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

// MS spectackle variables
var MSchart;
var MSData;
var msInfo = new Array();

//NMR spectackle variables
var NMRchart;
var NMRarray;
var nmrInfo = new Array();

function initializeMSSpeckTackle() {

    if (MSchart != undefined) return;
    if (!msInfo.length) return;

    MSchart = st.chart.ms().xlabel("Mass-to-Charge").ylabel("Intensity").legend(true).labels(true);

    MSchart.render("#MSSpeckTackle");

    MSData = st.data.set().x("peaks.mz").y("peaks.intensity").title("spectrumId");
    MSchart.load(MSData);

    loadSpectralist("#msSpectraList", msInfo);


}

function initializeNMRSpeckTackle() {

    if (NMRchart != undefined) return;
    if (!nmrInfo.length) return;

    NMRchart = st.chart.nmr().xlabel("ppm").legend(true).margins([20, 100, 60, 0]).labels(true);

    NMRchart.render("#NMRSpeckTackle");

    NMRarray = st.data.array().xlimits(["xMin", "xMax"]).ylimits(["yMin", "yMax"]).y("data");
    NMRchart.load(NMRarray);

    loadSpectralist("#nmrSpectraList", nmrInfo);

}

function loadSpectralist(listSelector, spectraList) {

    // Get the Div with the spectra list
    var list = $(listSelector)[0];

    // Loop through the spectraList...
    $.each(spectraList, function (i, item) {
        $(list).append($('<label />', {
            'for': item.id, text: item.name}));
        $(list).append($('<input>', {
            id: item.id,
            type: "checkbox"
        }));

        $(list).append($('<br/>'));
    });

    $(list).change({"spectraList": spectraList}, function (event) {

        spectraChangeHandler(event);

    })

    // Get the first spectra and load it
    //loadSpectraAndInfo([spectraList[0]], $(list).next().next());


}

function spectraChangeHandler(event) {

    /* Get the selected spectra*/
    var selectedSpectra = getSelectedSpectra(event.target, event.data.spectraList);

    var infoDiv = $(event.target).parent().next().next();

    loadSpectraAndInfo(selectedSpectra, infoDiv);
}

function getSelectedSpectra(select, spectraList) {
    var spectra = new Array();

    var children = $(select).parent().children().filter("input");

    for (var index = 0;index < children.size();index++){

        var checkbox = children[index];

        if (checkbox.checked){
            spectra.push(spectraList[index]);
        }

    }

    return spectra;

}

function loadSpectrumAndInfo(spectrum, infoDiv) {

    loadSpectrum(spectrum);
    loadSpectraInfo(spectrum, infoDiv);

}

function loadSpectraAndInfo(spectra, infoDiv) {

    loadSpectra(spectra);

    if (spectra.length == 1) {
        loadSpectraInfo(spectra[0], infoDiv);
        $(infoDiv).show();

    } else {

        $(infoDiv).hide();
    }


}
function loadSpectra(spectra) {

//        if (spectra.length > 0)
//        {

    // Take the first one to know the type
    var spectrum = spectra[0];

    if (spectrum.type == "NMR") {
        data = NMRarray;
    } else {
        data = MSData;
    }


    /* Remove all the spectra */
    data.remove();

    var urls = new Array();

    // Loop through the spectra to create a list of urls
    for (index = 0; index < spectra.length; ++index) {
        urls.push(spectra[index].url);
    }

    data.add(urls);

}

function loadSpectrum(spectrum) {

    if (spectrum.type == "NMR") {
        data = NMRarray;
    } else {
        data = MSData;
    }

    data.remove(0);
    data.add(spectrum.url);

}

function loadSpectraInfo(spectra, infoDiv) {
    var html = spectra.name + "<br/> A " + spectra.type + " spectrum.";

    $.each(spectra.properties, function () {

        html = html + "<br/>" + this.name + ": "

        if (this.value.indexOf("http:") == 0) {
            html = html + "<a href=\"" + this.value + "\">" + this.value + "</a>"
        } else {
            html = html + this.value;
        }

    });
    infoDiv.html(html);
}


