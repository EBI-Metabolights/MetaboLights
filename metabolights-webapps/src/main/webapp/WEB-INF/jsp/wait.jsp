


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



