<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%--
~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
~ Cheminformatics and Metabolism group
~
~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
~
~ Last modified: 2014-Dec-17
~ Modified by:   kenneth
~
~ Copyright 2015 EMBL - European Bioinformatics Institute
~
~ Licensed under the Apache License, Version 2.0 (the "License");
~ you may not use this file except in compliance with the License.
~ You may obtain a copy of the License at
~
~      http://www.apache.org/licenses/LICENSE-2.0
~
~ Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
--%>


<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>

<!-- Aspera JS files -->
<script type="text/javascript" src="javascript/aspera/asperaweb-4.js" charset="utf-8"></script>
<script type="text/javascript" src="javascript/aspera/connectinstaller-4.js" charset="utf-8"></script>

<script type="text/javascript" src="javascript/aspera/jquery-ui.js" charset="utf-8"></script>

<script type="text/javascript" src="javascript/aspera/jquery-namespace.js" charset="utf-8"></script>
<script type="text/javascript" src="javascript/aspera/ml-aspera-config.js" charset="utf-8"></script>

<script type="text/javascript" src="javascript/aspera/ml-aspera.js" charset="utf-8"></script>

<script type="text/javascript" src="javascript/aspera/install.js" charset="utf-8"></script>

<div class="wrapper" id="wrapper">
    <h2>Aspera Connect test page</h2>
    <div id="downloadWrapper"></div>
    <div id="transferDiv" class="transferDiv"></div>
    <div id="noAspera" class="noAspera"></div>
</div>


<script>
    var fc = new METABOLIGHTS.FileControl( { sessionId: 'metabolights-download',
        transferContainer: '#transferDiv',
        messageContainer: '#noAspera',
        id: '0' });


    // Adds an input element download button that uses Aspera
    var downloadButton = $('<input/>',
            {
                type: "button",
                id: 'downloadButton',
                value: 'Aspera Download'
            });

    $('#downloadWrapper').append(downloadButton);
    var downloadButtonClick = function (e) {
        source = "studies/public/MTBLS1";
        fc.asperaWeb.showSelectFolderDialog( { success: function(dataTransferObj) { if (dataTransferObj.dataTransfer.files[0]) fc.download(source, dataTransferObj.dataTransfer.files[0].name); } });
    };
    downloadButton.on("click", downloadButtonClick);

</script>