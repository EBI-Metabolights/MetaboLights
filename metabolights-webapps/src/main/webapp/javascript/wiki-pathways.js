var pathways = (function() {
	var compound = 'CHEBI:16530';
	var pathwaysDict = {};
	var uri = {};
	var ajax = {
        type: "POST",
        url: "",
        async: true,
        data: {},
        success: {},
        error: {}
    }

    function init(){
    	uri = {
			'chebi' : 'http://webservice.wikipathways.org/index.php?ids='+ compound + '&codes=Ce&method=findPathwaysByXref&format=json',
		};
    }

    function getAssociatedPathways(value){
		if (typeof value !== 'undefined') {
			compound = value;
			init();
			queryWikiPathways();
		}else{
			console.log('Compound not set : fetching synonyms for "CHEBI:16530"');
			queryWikiPathways();
		}
		return pathwaysDict;
	}

	function queryWikiPathways(){
		var pathwaysJSON = performAjax('chebi')
		pathwaysDict = extractPathwaysFromJSON(pathwaysJSON);
	}

	function displayPathwayData(chemId, domId){
		var pathways = getAssociatedPathways(chemId);
		var myDiv = document.getElementById(domId);
		$('#'+domId).empty();

		var pathwayCanvas = document.createElement("div");
		pathwayCanvas.id='pathwayCanvas';
		myDiv.appendChild(pathwayCanvas);

		var selectList = document.createElement("select");
		selectList.id = "pathwaySelect";
		pathwayCanvas.appendChild(selectList);

		for (var key in pathways) {
		    var option = document.createElement("option");
		    option.value = pathways[key];
		    option.text = key;
		    selectList.appendChild(option);
		}

		var pathwayDisplayArea = document.createElement("div");
		pathwayDisplayArea.id='pathwayDisplayArea';
		pathwayCanvas.appendChild(pathwayDisplayArea);

		registerChangeEvents();
		$("#pathwayDisplayArea").html('<iframe src ="http://www.wikipathways.org/wpi/PathwayWidget.php?id=' + selectList.options[selectList.selectedIndex].value + '&xref='+compound+',Ce&colors=green" frameborder="0" width="100%" height="500px" seamless="seamless" scrolling="no" ></iframe>')
	}

	function extractPathwaysFromJSON(data){
		var pathways = data['result']
		var pathwaysDict = {}
		for (var key in pathways) {
			pathwaysDict[pathways[key]['name']] = pathways[key]['id'];
		}	
		return pathwaysDict;
	}

	function registerChangeEvents(){
		var select = document.getElementById('pathwaySelect');
		select.onchange=function(){
			$("#pathwayDisplayArea").html('<iframe src ="http://www.wikipathways.org/wpi/PathwayWidget.php?id=' + select.options[select.selectedIndex].value + '&xref='+compound+',Ce&colors=green" frameborder="0" width="100%" height="500px" seamless="seamless" scrolling="no"></iframe>');
		};
	}

	function performAjax(resource) {
		var response_data;
		ajax.type = "GET";
		ajax.async = false;
		ajax.url = uri[resource];
		ajax.success = function(data) { response_data = data };
		ajax.error = function(){ };
		$.ajax(ajax);
		return response_data;	
	}

	init();

	return {
		getAssociatedPathways: getAssociatedPathways,
		displayPathwayData: displayPathwayData
	}
})();