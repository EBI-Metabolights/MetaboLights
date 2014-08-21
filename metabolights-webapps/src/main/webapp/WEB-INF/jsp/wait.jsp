


<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 4/4/14 12:20 PM
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

<div id="wait" class="center">

    <img src="img/MetaboLightsLogo.gif">
    <h3 id="info" class="center"></h3>
</div>

<script type="text/javascript">

    var url = GetURLParameter("goto");

    if(url == undefined){
        url = "index";
    }

    info = $( "#info" );
    info.text("Loading " + url + ", please wait.");

    $(window).load(function() {

        //pulse(false);
        window.location.replace(url);

    })

    function GetURLParameter(sParam)
    {
        var sPageURL = window.location.search.substring(1);
        var sURLVariables = sPageURL.split('&');
        for (var i = 0; i < sURLVariables.length; i++)
        {
            var sParameterName = sURLVariables[i].split('=');
            if (sParameterName[0] == sParam)
            {
                return sParameterName[1];
            }
        }
    };

//    function pulse(back) {
//        $('#wait').animate(
//                {
//                    //'font-size': (back) ? '100%' : '105%',
//                    opacity: (back) ? 1 : 0.7
//                }, 800, function(){pulse(!back)});
//        $('#wait img').animate(
//                {
//                    'width': (back) ? '100px' : '97px'
//                }, 800);
//    };

</script>



