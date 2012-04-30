/* (This is the new BSD license.)
* Copyright (c) 2010-2012, Europaeische Akademie Bozen/Accademia Europea Bolzano
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*     * Redistributions of source code must retain the above copyright
*       notice, this list of conditions and the following disclaimer.
*     * Redistributions in binary form must reproduce the above copyright
*       notice, this list of conditions and the following disclaimer in the
*       documentation and/or other materials provided with the distribution.
*     * Neither the name of the Europaeische Akademie Bozen/Accademia Europea 
*	Bolzano nor the of its contributors may be used to endorse or promote 
*	products from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY Europaeische Akademie Bozen/Accademia Europea
* Bolzano``AS IS'' AND ANY OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
* THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
* ARE DISCLAIMED. IN NO EVENT SHALL Europaeische Akademie Bozen/Accademia Europea
* Bolzano BE LIABLE FOR ANY, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
* CONSEQUENTIAL DAMAGES INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
* GOODS OR SERVICES; OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER 
* CAUSED AND ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR 
* TORT INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF 
* THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

/**
 *
 * 
 * &copy; Copyright 2010-2011 Accademia Europea Bolzano<br>
 * Software: Structured Parallel Coordinates
 * Original: 20100823<br>
 * Revised: 20120215<br>
 *
 * Greatly modified from the parallel coordinates example at http://vis.stanford.edu/protovis/ex/cars.html
 *
 * @author Chris Culy, modifications Verena Lyding
 * @version 1.34
 *
 */
 
 
///////////// General information ////////////////
// StructuredParallelCoordinates provides two functions for basic use:
//
//			resetVis(htmlContainer, dataName)
//				htmlContainer is the id of the html element that will contain the diagram
//				dataName is the name of the variable containing the data to be visualized [this is not an ideal approach]
//				The data itself is an array of objects, where each object has either:
//					* an "ng" (ngram) attribute whose value is an array of the continguous elements (i.e. elements that must be together)
//					* any other attributes with numeric values to be visualized as a dimension (axis) in the diagram
//						e.g. [{"ng":["a","b","c"], "count":333, "percent":33.3}, {"ng":["1","2","3"], "count":667, "percent":66.7]}]
//					OR
//					* an "cdim" (corpus dimensions) attribute whose value is an array of attribute:value pairs, where the attribute defines 
//						the name of the dimension (axis label)
//					* any other attributes with numeric values to be visualized as a dimension (axis) in the diagram
//						e.g. [{"cdim":[{registers:"A"},{disciplines:"1 Linguistics"},{verb:"help+INF"}],"1970/1980s":69,"2000s":18},
//								{"cdim":[{registers:"A"},{disciplines:"2 Biology"},{verb:"help+INF"}],"1970/1980s":69,"2000s":18}]
//
//			setDisplaySize(diagramWidth, diagramHeight)
//				sets the width and height of the diagram
//
// Here are a couple examples. See also the demo page, ngrams.html or subcorpusComparison.html
//mySPC.setDisplaySize(1020,720);  
//mySPC.resetVis("pc", "dngrams");

//The following functions provide useful functionality:
//selectAllVals(); //select all values on each axis
//selectAllValsOnOne(theDim); ////select all values on one axis
//selectNoVals(); //unselect all values on each axis
//selectNoValsOnOne(theDim); //unselect all values on one axis
//selectAllButLowest(); //select all but the lowest value on each axis
//selectAllButLowestOnOne(theDim); //select all but the lowest value on one axis
//selectAllButHighest(); //select all but the highest value on each axis
//selectAllButHighestOnOne(theDim); //select all but the highest value on one axis

//In addition, any of the functions of StructuredParallelCoordinates can be overridden to provide specialized functionality.
//The function most typically overridden in applications is:
//
//showVal(theDim, thisRec) ; //show the information about thisRec (the current record, where theDim is the selected dimension)
//
//There are also handlers for some mouse actions:
//onActiveLineMouseOver, onActiveLineMouseOut, onInactiveLineMouseOver, onInactiveLineMouseOut 
//
//Line coloring is done by the functions in the map c (i.e. spc.c)
//
//The labels on the axes (not the labels for the axes on top), are customizable by itemText, itemFont, and itemColor
//  
//
//Other functions may be usefully overridden by extensions of StructuredParallelCoordinates. 
//For example, RankComparison overrides:
//	getSortOrder, resetVis, scaleFloors, scaleCeils, showVal, updateSelectionView

///////////////////////////////////////////////////////////////////////////////////////////////////
 
	//CC data is in variable called "ngrams"
	//CC it is a hash/map where "ng" is an array of the ngram elements and
	//LyV in subcorpusComparisons it is a hash/map where "cdims" is an array of attribute:value pairs of corpus characteristics
	//CC any other (numeric) information is associated with an appropriate label
	//CC for the time being, the numeric range should be > 1 (because of the original examples fudge factor) 
	//CC the axes will have the ngram elements (labeled p1 ... pn) 
	//LyV in subcorpusComparisons the corpus characteristics specified unter "cdims" are labeled by the indicated attribute names
	//CC followed by a light red line, followed by the other fields labeled with their names
	
	//CC in our sample data we have, e.g.
	//CC original data: {"ng":["20","-","jähriger"], "count":71, "percentP1":63},
	//CC convert to: 	{"p1":"20", "p2":"-", "p3":"jähriger", "count":71, "percentP1":63},
	//CC we add p1Order, p2Order, p3Order, etc (which is really the sorted positions)
	
	//LyV in the sample data for "cdim" we have, e.g.
	//LyV original data: {"cdim":[{registers:"A"},{disciplines:"1 Linguistics"},{verb:"help+INF"}],"1970/1980s":69,"2000s":18}
	//LyV convert to:	 {"registers":"A","disciplines":"1 Linguistics",verb:"help+INF","1970/1980s":69,"2000s":18}
	
	//CC non-contiguous selection
	//CC to do it, make the first selection without shift, then hold down shift to add to the selection
	//CC implementation
	//CC			added isShift, isCtrl, isAlt global variables, set in pure javascript, since protovis doesn't provide access to shiftKey (since it doesn't pass the event to the function callback in X.event() ) (odd, since SVG does)
	//CC			needed to change handle so that its data is such that each dim has an array of the current data object {dim, min, max}
	//CC			the array will be initialized with one element, which is the current initial object
	//CC			filter is now array 
	//CC			update adds a new element to the array if isCtrl is true; otherwise makes an array with one element
	//CC			selectAll will reset array to single element 
	//CC			the Bar in handle, has array elements as data, so we get one bar per array elt
	//CC some other ideas:
	//CC		maybe use ordinal scales for the ngrams instead of p1Order etc ?

//CC new
/**
 * Constructs an empty StructuredParallelCoordinates (aka SPC).
 *
 * @class This is an encapsulation of the underlying parallel coordinates visualization
 *
 * <p>The data is set in {@link #resetVis}. 
 *
*/
function StructuredParallelCoordinates() {
	//We'll return spc rather than using StructuredParallelCoordinates directly.
	//The reason for this has to do with scope issues with protovis and the use of "this"
	//The drawback as that we can't use the javascript prototype technique for extending StructuredParallelCoordinates
	var spc = {}

	//CC new, max width, height
	/**
	* Maximum width of SPC
	* @private
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.ww as ww
	* @type Integer
	*/
	spc.ww = null;
	/**
	* Maximum height of SPC
	* @private
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.hh as hh
	* @type Integer
	*/
	spc.hh = null;

	//LyV new, 
	/**
	* Comparable ceiling for axes
	* @private
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.cc as cc
	* @type Integer
	*/
	spc.cc = 0;
	
	//var numExtraFields; //CC new: how many extra information fields there are in front of the elements in orderArray[i], so we can strip them off before displaying the label
	/**
	* how many extra information fields there are in front of the elements in orderArray[i], so we can strip them off before displaying the label (ugly)
	* @private
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.numExtraFields as numExtraFields
	* @type Integer
	*/
	spc.numExtraFields = null;

	/** 
	* Resets the visualizaton with new data
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.resetVis as resetVis 
	* @param {String} containerID the id of the web page to contain the diagram, usually a div
	* @param {String} dataName the <em>name</em> of the variable holding the new data
	*/
	spc.resetVis = function(containerID, dataName) { 
			document.getElementById(containerID).innerHTML = ""; //get rid of the previous diagram
			if (dataName == "") {
				return;
			}
			
			var theData;
			eval("theData = " + dataName);
			spc.startVis(containerID, document.body.clientWidth, 800, theData); //800 is just the default height
	}
	


	

	/** 
	* Does the real work of making the diagram
	* @private
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.startVis as startVis 
	* @param {String} containerID the id of the web page to contain the diagram, usually a div
	* @param {Integer} maxW the maximum width of the visualization
	* @param {Integer} maxH the maximum height of the visualization
	* @param {Array of Object} theData the data to be visualized, see {@link #ngrams}
	* @param {Integer} numExtras the number of extra fields containing information cf. {@link #numExtraFields}
	*/
	spc.startVis = function(containerID, maxW, maxH, theData, numExtras) {
		spc.ww = maxW;
		spc.hh = maxH;
		if (numExtras != null) {
			spc.numExtraFields = numExtras;
		} else {
			spc.numExtraFields = 0;
		}
		spc.setUpData(theData);
		spc.setupScales();
		spc.setupInteractionState();
		spc.setupVis(containerID, numExtras);
	}

	/**
	* the actual width of the visualization
	* @private
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.w as w
	* @type Integer
	*/
	spc.w = null;
	/**
	* the actual height of the visualization
	* @private
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.h as h
	* @type Integer
	*/
	spc.h = null;

	/** 
	* Sets the maximum size of the visualization area
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.setDisplaySize as setDisplaySize 
	* @param {number} wd the width
	* @param {number} ht the height
	*/
	spc.setDisplaySize = function(wd,ht) {
		spc.w = wd;
		spc.h = ht;
		if (spc.vis != null) {
			spc.vis.render();
		}
	}

/** 
	* Was the shift key pressed when the mouse was pressed
	* @private
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.isShift as isShift
	* @type Boolean
	*/
	spc.isShift = false;
	/** 
	* Was the control key pressed when the mouse was pressed
	* @private
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.isCtrl as isCtrl
	* @type Boolean
	*/
	spc.isCtrl = false;
	/** 
	* Was the "alt" key pressed when the mouse was pressed
	* @private
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.isAlt as isAlt
	* @type Boolean
	*/
	spc.isAlt = false;
	
	/** 
	* records the modifier keys when the mouse is pressed
	* @private
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.doMouseDown as doMouseDown
	* @param {Event} e the event
	* @returns true
	*/
	spc.doMouseDown = function(e) {
		spc.isShift = e.shiftKey;
		spc.isCtrl = e.ctrlKey;
		spc.isAlt = e.altKey;
		return true;
	}

	/**
	* Holds the ordering information for each dimension cf {@link #getSortOrder}
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.allOrders as allOrders
	* @type Array of Array of Integer
	*/
	spc.allOrders = null;
	
	/** 
	* Sets up all data information, including the ordering
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.setUpData as setUpData 
	* @param {Object} ngramData the data to visualize
	*/
	spc.setUpData = function(ngramData) {
		//ngrams = ngramData; //this gets us in trouble if we're switching back and forth between data and we've already flattened the data
		//TBD would be nicer to flatten data only once, but complicated by calculating the units -- would have to modify initUnits to skip p1Order, ... pnOrder
		//clone ngramData into ngrams
		var n = ngramData.length;
		/**
		* The ngram data objects, where the objects minimally have a field "ng" with is an array of ngrams (or "cdim" which is an array of attribute:value pairs
		* for corpus characteristics), and then any other fields to be visualized e.g. one such Object might be
		* {"ng":["a","b","c"], "count":71, "percentP1":63}, which represents the ngram "abc", which has a "count" of 71 and a "percentP1" of 63. Each ngram 
		* should be the same length.
		* @fieldOf StructuredParallelCoordinates.prototype
		* @exports spc.ngrams as ngrams
		* @type Array of Objects
		*/
		spc.ngrams = new Array(n);
		spc.cc = 1;
		for(var i=0;i<n;i++) {
			var oldGram = ngramData[i];
			var newGram = new Object();
			for(var f in oldGram) {
				newGram[f] = oldGram[f];
				
				if(oldGram[f] > spc.cc){	// LyV new
				
				spc.cc = oldGram[f];
					
				}
				
			}
			spc.ngrams[i] = newGram;
		}
		
		
		spc.initUnits(); //we do this before we flatten the data, so that we don't have the positional fields to worry about
		spc.flattenNG();
		
		//CC we now create the sort orders for the ngram pieces; this would allow reordering a position axis if we wanted to
		spc.allOrders = new Array();

		for(var i=1;i<=spc.numGrams;i++) {
			spc.allOrders[i] = spc.getSortOrder(i, spc.sortFunction);
		}
	}

	// The units and dimensions to visualize, in order.
	//we do this before we flatten the data, so that we don't have the positional fields to worry about
	/**
	* the units for each dimension
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.units as units
	* @type Object
	*/
	spc.units = null;
	/** 
	* Sets up the units
	* @private
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.initUnits as initUnits 
	*/
	spc.initUnits = function() {
		spc.units = new Object();
		var dataPt = spc.ngrams[0]; //ngrams[0] is exemplar
		
		if('ng' in dataPt){
		//ngrams first
			for(var i=0;i<dataPt["ng"].length;i++) {
				var posn = "f" + (i+1);
				var posnname = "f";
							
				//spc.units[posn + "Order"] = {name: posn, unit:""};
				spc.units[posn + "Order"] = {name: posn, lname: posn, unit:""};
			}
		}
		else if('cdim' in dataPt){	//LyV new
		
			for(var i=0;i<dataPt["cdim"].length;i++) {
				var posn = "f" + (i+1);
				var ent = dataPt["cdim"][i];
				var posnname="xx";
				for(var k in ent){
					posnname = k;
				}
						
				//spc.units[posn + "Order"] = {name: posnname, unit:""};
				spc.units[posn + "Order"] = {name: posn, lname: posnname, unit:""};
			}
		}

		//now the rest		
		for(var fld in dataPt) { 
			if (fld == "ng" || fld == "cdim") {
				continue;
			}
			//spc.units[fld] = {name: fld, unit:""};
			spc.units[fld] = {name: fld, lname: fld, unit:""};
		}
	}

	/**
	* the length of the ngrams (all ngrams should be the same length)
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.numGrams as numGrams
	* @type Integer
	*/
	spc.numGrams = null;
	/** 
	* Flattens the original "ng" (or "cdim") field in {@link #ngrams}, bringing element j of "ng" to the value of "pj" in ngrams. "ng" is not deleted.
	* @private
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.flattenNG as flattenNG 
	*/
	spc.flattenNG = function() { //CC TBD:this does not currently delete  "ng" (we could, though, to save memory; shouldn't though, if swapping variables)
		if("ng" in spc.ngrams[0]){
			spc.numGrams = spc.ngrams[0]["ng"].length;
			for(var i in spc.ngrams) {
				for(var n in spc.ngrams[i]["ng"]) {
					spc.ngrams[i]["f" + (1*n+1)] = spc.ngrams[i]["ng"][n];
				}
			}
		}
		else if("cdim" in spc.ngrams[0]){		// LyV new
			spc.numGrams = spc.ngrams[0]["cdim"].length;
			for(var i in spc.ngrams) {
				for(var n in spc.ngrams[i]["cdim"]) {
					for(var j in spc.ngrams[i]["cdim"][n]){
						spc.ngrams[i]["f" + (1*n+1)] = spc.ngrams[i]["cdim"][n][j];
					}
				}
			}
		}
	}

	//CC we now create the sort orders for the ngram pieces; this would allow reordering a position axis if we wanted to
	/** 
	* Creates a sort order for the ngram position by assigning an Integer to each element in the position corresponding to its rank order cf. {@link #allOrders} These are stored in the fields "p1Order", "p2Order" etc., corresponding to the position 1, position 2, etc.
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.getSortOrder as getSortOrder 
	* @param {Integer} posn the ngram position whose sort order we're getting
	* @param {Function} sortFun an optional sort function for the position. The default is to use {@link #sortFunction}
	*/
	spc.getSortOrder = function(posn, sortFun) {
		var pFld = "f" + posn;
		var pOrderFld = pFld + "Order";
		
		var grams = new Object();
		for(var i in spc.ngrams) {
			grams[ spc.ngrams[i][pFld] ] = true;
		}
		
		var tmpArray = pv.keys(grams);
		var orderArray;
		if (sortFun != null) {
			orderArray = tmpArray.sort(sortFun);
		} else {
			orderArray = tmpArray.sort();
		}

		//add the sort order to ngrams
		/* pv.search isn't general enough for us
		for(var i in spc.ngrams) {
			var item = spc.ngrams[i][pFld];
			var idx = pv.search(orderArray, spc.ngrams[i][pFld]);
			spc.ngrams[i][pOrderFld] = pv.search(orderArray, spc.ngrams[i][pFld] );
		}
		*/
		var orderN = orderArray.length;
		for(var i in spc.ngrams) {
			var item = spc.ngrams[i][pFld];
			for(var j=0;j<orderN;j++) {
				if (item == orderArray[j]) {
					spc.ngrams[i][pOrderFld] = j;
				}
			}
		}
		
		return orderArray;
	}

	/** 
	* The default sort function for (all of) the dimensions
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.sortFunction as sortFunction 
	* @param {String} a first element to compare
	* @param {String} b second element to compare
	* @returns {Integer} -1,0,1
	*/
	spc.sortFunction = function(a,b) {
		//ascending, case insensitive
		var item1 = a.toLowerCase();
		var item2 = b.toLowerCase();
		if (item1 > item2) {
			return -1;
		} else if (item1 < item2) {
			return 1;
		}
		return 0;
	}
	
	//CC end new
		
	//CC new: moved into function setupScales, and moved variable declarations out
	/**
	* The dimension names
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.dims as dims
	* @type Array of String
	*/
	spc.dims = null;
	/**
	* Fudge factor for positioning on the axes
	* @private
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.fudge as fudge
	* @type Number
	*/
	spc.fudge = null;
	/**
	* The horizontal positions of the axes
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.x as x
	* @type pv.Scale
	*/
	spc.x = null;
	/**
	* The vertical positions of the axes, by dimension name
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.y as y
	* @type Object: pv.Scale hashed by dimension name
	*/
	spc.y = null;
	/**
	* Line coloring functions, by dimension name. The idea (from the protovis example) is that one dimension is selected, and the corresponding function is used to color the lines. In other words, line coloring is based on which dimension is selected.
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.c as c
	* @type Object: pv.Scale hashed by dimension name
	*/
	spc.c = null;
	
	//LyV new
	/**
	* Discrete color
	* @private
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.cd as cd
	* @type Boolean
	*/
	spc.cd = false;
		
	//LyV new
	/** 
	* Set color scheme for polylines
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.setColorScheme as setColorScheme 
	*/
	spc.setColorScheme = function() {
		if(spc.cd){
			spc.cd = false;
		} else{
			spc.cd = true;
		}
	  
	  spc.setupScales(); 
	  spc.vis.render();
	}
	
	//LyV new
	/**
	* Comparable numerical axes
	* @private
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.a as a
	* @type Boolean
	*/
	spc.a = false;
	
	//LyV new
	/** 
	* Set comparable scales for numerical axes
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.setScalesComparability as setScalesComparability 
	*/
	spc.setScalesComparability = function() {
		if(spc.a){
			spc.a = false;
		} else{
			spc.a = true;
		}
	  
	  spc.setupScales();
	  spc.updateInteractionState();
	  spc.vis.render();
	  
	}
	
	/** 
	* Sets up all the scales for the visualization
	* @private
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.setupScales as setupScales 
	*/
	spc.setupScales = function() {

		spc.dims = pv.keys(spc.units);

		/* Sizing and scales. */
		spc.fudge = 0;//pconesa:0.5; //CC orig
		spc.x = pv.Scale.ordinal(spc.dims).splitFlush(0, spc.w),
		spc.y = pv.dict(spc.dims, function(t) {
			return pv.Scale.linear(
				spc.ngrams.filter(function(d) {return spc.filterFun(d,t)}), //CC new, for clarity
				function(d) {return spc.scaleFloors(d,t)}, //CC new, for clarity
				function(d) {return spc.scaleCeils(d,t)} //CC new, for clarity
			).range(0, spc.h)}
		);
		//CC spc.c is a map from the dimensions to functions that color the lines. 
		//CC The idea (from the protovis example) is that one dimension is selected, and the corresponding function is used to color the lines.
		//CC In other words, line coloring is based on which dimension is selected
		spc.c = pv.dict(spc.dims, function(t) {
			if(spc.cd){			// LyV new
			return pv.Colors.category10(
				spc.ngrams.filter(function(d) {return spc.filterFun(d,t)}), //CC new, for clarity
				function(d) {return spc.scaleFloors(d,t)}, //CC new, for clarity
				function(d) {return spc.scaleCeils(d,t)} //CC new, for clarity
			)}
			else{
			return pv.Scale.linear(
				spc.ngrams.filter(function(d) {return spc.filterFun(d,t)}), //CC new, for clarity
				function(d) {return spc.scaleFloors(d,t)}, //CC new, for clarity
				function(d) {return spc.scaleCeils(d,t)} //CC new, for clarity
			).range("steelblue", "brown")}			
		}
		
		);  //CC i.e., this is the color range for the connecting lines according to the selected dimension
		
	}

	//CC new: moved into function setupInteractionState, and moved variable declarations out
	/**
	* The selected values, by dimension
	* @private
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.filter as filter
	* @type Object of Array of min/max/dim Objects
	*/
	spc.filter = null;
	/**
	* The selected dimension, the one the governs line coloring
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.active as active
	* @type String
	*/
	spc.active = null;

	/** 
	* Sets up the interaction state with empty filters and sets the active axis to the one for the first ngram element
	* @private
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.setupInteractionState as setupInteractionState 
	* @returns initial (empty) filters
	*/
	spc.setupInteractionState = function() {
	/* Interaction state. */ //CC this is just the initial state here
		spc.filter = pv.dict(spc.dims, function(t) {
	//    return {min: y[t].domain()[0], max: y[t].domain()[1]}; //CC orig
		return [{min: spc.y[t].domain()[0], max: spc.y[t].domain()[1], dim:t}]; //CC new, the values are now arrays, instead of just min/max
	  }); 

		spc.active = "f1Order";

	}

	//CC new
	/** @ignore */
	spc.filterFun = function(rec, fld) {
		return ( !isNaN(rec[fld]) );
	}

	/** 
	* The  bottom of a vertical interval for a given element (rec) in a given dimension (fld), calculated using the full range of the axis
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.fullScaleFloors as fullScaleFloors 
	* @param {Object} rec the data information, from {@link #ngrams}
	* @param {String} fld the dimension
	* @returns {Number}
	*/
	spc.fullScaleFloors = function(rec, fld) {
		if (fld.indexOf("Order") > -1) {  // for n-gram data
			return rec[fld];
		} else if (fld.match(/percent/i) != null || fld.indexOf("%") > -1) {
			return 0;
		} else if (Math.floor(rec[fld]) == 0){	// LyV new - keep zero as floor, if it is the smallest value
			return 0;
		} else if (spc.a == true){		// LyV new - for comparable numerical axes the floor is set to 0
			return 0;
		} else {
			return rec[fld];//Math.floor(rec[fld])-spc.fudge;
		}
	}

	/** 
	* The  bottom of a vertical interval for a given element (rec) in a given dimension (fld), calculated using the full range of the axis
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.fullScaleCeils as fullScaleCeils 
	* @param {Object} rec the data information, from {@link #ngrams}
	* @param {String} fld the dimension
	* @returns {Number}
	*/
	spc.fullScaleCeils = function(rec, fld) {
		if (fld.indexOf("Order") > -1) {  // for n-gram data
			return rec[fld];
		} else if (fld.match(/percent/i) != null || fld.indexOf("%") > -1) {
			return 100;
		} else if (spc.a == true){		// LyV new - for comparable numerical axes a comparable ceiling is set
			return spc.cc + 0.5;
		} else {
			return rec[fld];//Math.ceil(rec[fld])+spc.fudge;
		}
	}

	/** 
	* A function returning a the constant. Useful for constant intervals in {@link #scaleFloors} and {@link #scaleCeils}
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.scaleToConst as scaleToConst 
	* @param {Number} c the constant to return
	* @returns {Number}
	*/
	spc.scaleToConst = function(c) {
		return function() {return c;};
	}

	/** 
	* The  top of a vertical interval for a given element (rec) in a given dimension (fld), default is {@link #fullScaleFloors}
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.scaleFloors as scaleFloors 
	* @param {Object} rec the data information, from {@link #ngrams}
	* @param {String} fld the dimension
	* @returns {Number}
	*/
	spc.scaleFloors = spc.fullScaleFloors;
	/** 
	* The  bottom of a vertical interval for a given element (rec) in a given dimension (fld), default is {@link #fullScaleCeils}
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.fullScaleCeils as fullScaleCeils 
	* @param {Object} rec the data information, from {@link #ngrams}
	* @param {String} fld the dimension
	* @returns {Number}
	*/
	spc.scaleCeils = spc.fullScaleCeils;

	//CC end new

			
	//CC new: move defs into startVis and pull variables out
	/**
	* The container for the StructuredParallelCoordinates
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.vis as vis
	* @type pv.Panel
	*/
	spc.vis = null;
	/**
	* The dividing line between ngrams and other dimensions
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.divider as divider
	* @type pv.Bar
	*/
	spc.divider = null;
	/**
	* The pv.Rule(s) for the dimensions
	* @private
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.drule as drule
	* @type pv.Rule
	*/
	spc.drule  = null; 
	/**
	* The container(s) for the labels for the ngrams on the axes
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.gpanel as gpanel
	* @type pv.Panel
	*/
	spc.gpanel = null;
	/**
	* The container for the selected parallel coordinates polylines (on top of the all the polylines) 
	* @private
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.change as change
	* @type pv.Panel
	*/
	spc.change  = null;
	/**
	* The selected polyline(s) 
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.line as line
	* @type pv.Line
	*/
	spc.line  = null;
	/**
	* The selection area around each axis 
	* @private
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.handle as handle
	* @type pv.Panel
	*/
	spc.handle  = null;
	/**
	* The <em>selected</em> portion of the axis 
	* @private
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.handleBar as handleBar
	* @type pv.Panel
	*/
	spc.handleBar = null; ;
	/**
	* The axis that the user is doing selecting on
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.gSelecting as gSelecting
	* @type String
	*/
	spc.gSelecting = null; //CC new: the axis that we are doing the selecting on -- so we can remove the color when we're done
	/**
	* The label(s) of the dimension axes
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.dLabel as dLabel
	* @type String
	*/
	spc.dLabel = null; //CC new
	//spc.maxItemHt = 12; //CC new: maximum height of the item labels on the axes

	/** 
	* Instantiates the visualization
	* @private
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.setupVis as setupVis 
	* @param {String} containerID the id of the html container for the visualization
	*/
	spc.setupVis = function(containerID) {
		//CC new

		document.getElementById(containerID).addEventListener("mousedown", spc.doMouseDown, false); //CC new, nicer style

		var rtVis = new pv.Panel()
			.canvas(containerID) //CC new we specify this explicitly so that we can clear it out when we reset the data
			.width(spc.ww)
			.height(spc.hh)
		;	
			
		//CC end new
		/* The root panel. */
		//vis = new pv.Panel() //CC orig
		spc.vis = rtVis.add(pv.Panel) //CC new; we're doing this so we can zoom in and not get clipped
			//.canvas(containerID) //CC new; we specify this explicitly so that we can clear it out when we reset the data
			.width(spc.w)
			.height(spc.h)
			.left(30)
			.right(30)
			.top(30)
			.bottom(20)
			.events("all") //CC new, for zooming, panning
			.event("mousewheel", pv.Behavior.zoom()) //CC new, but a bit screwy
			.event("mousedown", pv.Behavior.pan()) //CC new, but a bit screwy
		//	.event("mousemove", pv.Behavior.point()) // CC new; point(n) is radius in pixels to look for a mark, but it isn't working right (it might now that I added the events("all"), but I haven't tested it
		;

		//CC new
		//divider line, decorative only
		if (spc.dims.length > spc.numGrams) {
			spc.divider = spc.vis.add(pv.Bar)
				.data([""]) //CC dummy -- do we we need this?
				.left( (spc.x( spc.dims[spc.numGrams-1] ) + spc.x( spc.dims[spc.numGrams] ))/2) 
				.width(3)
				//.fillStyle("rgb(255,182,193") //light pink
				.fillStyle(spc.dividerColor);
			;
		}

		// The parallel coordinates display. //CC see also below, especially for the line, where there is a lot of duplication
		//CC The lines here are the gray lines
		/**
		* The container for the (unselected) parallel coordinates polylines 
		* @private
		* @fieldOf StructuredParallelCoordinates.prototype
		* @exports spc.inactivePanel as inactivePanel
		* @type pv.Panel
		*/
		spc.inactivePanel = spc.vis.add(pv.Panel)
			.data(spc.ngrams)
		//    .visible(function(d) dims.every(function(t)
		//        (d[t] >= filter[t].min) && (d[t] <= filter[t].max))) //CC orig
		;
		
		/**
		* The unselected polyline(s) 
		* @fieldOf StructuredParallelCoordinates.prototype
		* @exports spc.inactiveLine as inactiveLine
		* @type pv.Line
		*/
		spc.inactiveLine = spc.inactivePanel.add(pv.Line)
			.data(spc.dims)
			.left(function(t, d) {return spc.x(t)})
			.bottom(function(t, d) {return spc.y[t](d[t])})
			.strokeStyle("#ddd")
			//.lineWidth(1)
			.lineWidth(spc.inactiveLineWidth)
			.antialias(false)
			.title(spc.valToString) //CC new
/*
			.event("mouseout", function() {
				this.lineWidth(1);
				this.strokeStyle("#ddd");
				this.render();
				}) //CC new
			.event("mouseover", function(t,d
			) {
				this.lineWidth(3);
				this.strokeStyle("#343");
				this.render();
				spc.showVal(t,d);
				}) //CC new
*/
			.event("mouseout", spc.onInactiveLineMouseOut)
			.event("mouseover", spc.onInactiveLineMouseOver)
		//	.event("mousemove", pv.Behavior.point()) //CC new, point(n) is radius in pixels to look for a mark, but this doesn't work here "k.children is undefined"
		//	.event("point", showVal) //CC new, but doesn't work right
		;
		
		// Rule per dimension.
		spc.drule = spc.vis.add(pv.Rule)
			.data(spc.dims)
			.left(spc.x);

		// Dimension label
		spc.dLabel = spc.drule.anchor("top").add(pv.Label)
			.top(-12)
			.font(spc.dLabelFont)
			.text(spc.dLabelText)
			.textStyle(spc.dLabelStyle)
			.events("mousedown")
			.event("mousedown", spc.dLabelOnMouseDown)
		;



		// The parallel coordinates display [CC of the selected records]
		spc.change = spc.vis.add(pv.Panel);

		//CC these are the active lines
		spc.line = spc.change.add(pv.Panel)
			.data(spc.ngrams)
		//    .visible(function(d) dims.every(function(t)
		//        (d[t] >= filter[t].min) && (d[t] <= filter[t].max))) //CC orig
			  .visible(function(d) { return spc.isVisible(d);}) //CC new

		  .add(pv.Line)
			.data(spc.dims)
			.left(function(t, d) {return spc.x(t)})
			.bottom(function(t, d) {return spc.y[t](d[t])})
			.strokeStyle(function(t, d) {return spc.c[spc.active](d[spc.active])})
			.lineWidth(spc.activeLineWidth)
			.title(spc.valToString) //CC new
			.event("mouseout", spc.onActiveLineMouseOut)
			.event("mouseover", spc.onActiveLineMouseOver)
		;

		/* Handle select and drag */
		spc.handle = spc.change.add(pv.Panel)
			.data(spc.dims.map(function(dim) { return {y:0, dy:spc.h, dim:dim}; })) //CC orig
			.left(function(t) {return spc.x(t.dim) - 13}) //CC - half the width
			.width(26) //CC was 60, but this is a bit too wide for good mousing over the lines
			.fillStyle("rgba(0,0,0,.001)")
			.cursor("crosshair")
			.event("mousedown", pv.Behavior.select())
			.event("select", spc.update)
			.event("selectend", spc.selectAll); //CC but we really only do selectAll on no or small movement
		
		spc.handleBar = spc.handle.add(pv.Bar)
		//	.data(function(dh) {return dh;}) //CC orig
			.data(function(dh) {return spc.filter[dh.dim];}) //CC new
			.left(8) //CC was 25; half the parent width - half of this width
		//    .top(function(d) d.y) //CC orig
			.top(function(d) {
					var tp;
					if (d.y != null) {
						tp = d.y; //CC I don't think we ever get here now
					} else {
						tp = spc.h-spc.y[d.dim](d.max);
					}
					if (isNaN(tp)) {
						//CC we do need this
						tp = 0;
					}
					
					return tp;
				})
			.width(10)
		//	.height(function(d) {return d.dy;}) //CC orig
			.height(function(d) {
				var bt;
				if (d.dy != null) {
					//multiAlert("height", d.dim, "d.dy", d.dy);
					bt = d.dy; //CC I don't think we ever get here now
				} else {
					//bt = spc.y[d.dim](d.max-d.min); //CC WRONG
					bt = spc.y[d.dim](d.max) - spc.y[d.dim](d.min);
				}
				if (isNaN(bt)) {
					//CC we do need this
					bt = spc.h;
				}

				return bt;
				
				})
		 /* CC orig: colors the selection bars according to their mid range
			.fillStyle(function(t) t.dim == active
				? c[t.dim]((filter[t.dim].max + filter[t.dim].min) / 2)
				: "hsla(0,0,50%,.5)")
		*/
			.fillStyle(function(t) {
				var dim = t.dim;
				if (t.dim == null) {
					dim = t[0].dim;
				}
				
				if (spc.gSelecting == dim) { //CC changed to gSelecting
					//return "hsla(80%,100%,50%,.5)";
					return spc.handleBarSelectingColor(dim);
				} else {
					//return "hsla(0,0,50%,.5)";
					return spc.handleBarStaticColor(dim);
				}
				})
			.strokeStyle("white")
			.cursor("crosshair")
		/* CC orig -- used to be able to move selection bar around in original, but doesn't make sense with multi select, and it's broken now anyway
			.cursor("move")
			.event("mousedown", pv.Behavior.drag())
			.event("dragstart", update)
			.event("drag", update)
		*/
		;

		//CC new
		spc.handleBar.anchor("bottom").add(pv.Label)
			.textBaseline("top")
			.text(function(d) {
				var dim = d.dim;
				if (dim.indexOf("Order") > -1) {
					return "";
				} else {
					if (d.min == spc.y[dim].domain()[0]) return ""; //don't show at bottom
					return d.min.toFixed(0); //CC new
				}
			})
		;


		spc.handleBar.anchor("top").add(pv.Label)
			.textBaseline("bottom")
			.text(function(d) {
				var dim = d.dim;
				if (dim.indexOf("Order") > -1) {
					return "";
				} else {
					if (d.max == spc.y[dim].domain()[1]) return ""; //don't show at top
					return d.max.toFixed(0); //CC new
				}
			})
		;
		//CC end new

		spc.handle.anchor("bottom").add(pv.Label)
			.textBaseline("top")
			.text(function(d) {
				var dim = d.dim;
				if (dim == null) {
					dim = d[0].dim; //CC TBD: I don't think we get here anymore
				}
				if (dim.indexOf("Order") > -1) {
					return "";
				} else {
					return Math.floor(spc.y[dim].domain()[0]) + spc.units[dim].unit; //CC new -- put max and min on handle
				}
			})
		;

		spc.handle.anchor("top").add(pv.Label)
			.textBaseline("bottom")
			.text(function(d) {
				var dim = d.dim;
				if (dim == null) {
					dim = d[0].dim; //CC TBD: I don't think we get here anymore
				}
				if (dim.indexOf("Order") > -1) {
					return "";
				} else {
					return Math.ceil(spc.y[dim].domain()[1]) + spc.units[dim].unit; //CC new -- put max and min on handle
				}
			})
		;

		//CC new
		//CC want each ngram dimension to have labels of grams (moved after the change panel, so that the labels are on top of the lines and get the mouseover event to show the title
		spc.gpanel  = spc.vis.add(pv.Panel)
				.data(spc.dims)
				.left(spc.x)
		;
		spc.gramRule = spc.gpanel.add(pv.Rule)
				.data(function(d) {
					var idxOrd = d.indexOf("Order");
					if (idxOrd > -1) {
						var dn = d.substring(1, idxOrd);
						return spc.allOrders[dn];
					} else {
						return new Array();
					}
				})
				.strokeStyle("transparent")
				.def("myDim", function(d) {return d})
				.def("rowHt", function(d) {return spc.y[d](1) - spc.y[d](0);})
				.left( parent.left )
				.add(pv.Label)
					.textAlign("center")
					.font(spc.itemFont)
					.top(function(t) {  //this doesn't work right when zoom in a lot (i.e. when making it bigger)
						var offset = 6; //CC 6 is half of the default font size; it would be nice to calculate it as half of the actual font size, but I don't see a way
						if ( isNaN( spc.y[this.myDim()](this.index)) ) return offset+spc.h; //for case when we only have one elt
						return offset+spc.h-spc.y[this.myDim()](this.index);
					})
					.text(spc.itemText)
					.textStyle(spc.itemColor)
					///.font("normal 12px sans-serif")
					.title(function(t) {return t})
					.events("all") //need mouseover for title
					.event("mouseover", spc.onItemMouseOver)
					.event("mouseout", spc.onItemMouseOut)
		;
		//CC end new

		spc.vis.render();

	}


	//is this ngram to be shown in color or grayed out (NB: ! isVisible(g) means g will be represented as a gray line
	/** 
	* Is the data item (ngram) to be shown as selected or not. I.e. isVisible(g) is true if every dimension of g satisfies the filters. If isVisible(g) is true, then the polyline ngram will be shown as selected
	* @private
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.isVisible as isVisible 
	* @param {string} gram the data item to check 
	* @returns Boolan
	*/
	spc.isVisible = function(gram) {
		return spc.dims.every(function(dim) {
			var grVal = gram[dim];
			for (var fi in spc.filter[dim]) {
				var filterRange = spc.filter[dim][fi];
				if (grVal >= filterRange.min && grVal <= filterRange.max) {
					return true;
				}
			}
			return false;
		});
	}

	/** 
	* The label(s) of the ngram elements
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.itemText as itemText 
	* @param {Array of String} rawItem the underlying data
	* @param {String} theDim the dimension we are getting the label for ("p1Order", "p2Order" etc.)
	* @returns String 
	*/
	//CC item functions
	spc.itemText = function(rawItem, theDim) {
		var what = rawItem;
		if (rawItem != null) {
			var items = rawItem.split(/\t/);
			
			if (items.length > 1+spc.numExtraFields) {
				what = items[spc.numExtraFields] + " ... ";
			} else {
				what = items[spc.numExtraFields];
			}
		}
		return what;
	}
	/** 
	* The color for the label(s) of the ngram elements. Default is black.
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.itemColor as itemColor 
	* @param {Array of String} theItem the underlying data
	* @param {String} theDim the dimension we are getting the label for ("p1Order", "p2Order" etc.)
	* @returns Color 
	*/
	spc.itemColor = function(theItem,theDim) { //CC theDim is "p1Order", "p2Order" etc.
		return "black";
	}
	/** 
	* The font specification for the label(s) of the ngram elements. Default is "normal 12px sans-serif"
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.itemFont as itemFont 
	* @param {Array of String} theItem the underlying data
	* @param {String} theDim the dimension we are getting the label for ("p1Order", "p2Order" etc.)
	* @returns CSS font specification 
	*/
	spc.itemFont = function(theItem,theDim) {
		return "normal 12px sans-serif";
	}
	
	//CC now some mouse handlers for the items
	/** 
	* Hook for mouseOver events on the item labels.
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.onItemMouseOver as onItemMouseOver 
	* @param {Array of String} theItem the underlying data
	* @param {String} theDim the dimension we are getting the label for ("p1Order", "p2Order" etc.)
	*/
	spc.onItemMouseOver = function(theItem, theDim) {}
	/** 
	* Hook for mouseOut events on the item labels.
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.onItemMouseOut as onItemMouseOut 
	* @param {Array of String} theItem the underlying data
	* @param {String} theDim the dimension we are getting the label for ("p1Order", "p2Order" etc.)
	*/
	spc.onItemMouseOut = function(theItem, theDim) {}
	
	//CC end item functions
	
	//CC line functions
	/** 
	* The width of a highlighted (e.g. on mouseover) selected polyline. Default is 4.
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.hilitedActiveLineWidth as hilitedActiveLineWidth 
	* @param {String} theDim the dimension we are getting the label for ("p1Order", "p2Order" etc.)
	* @param {Array of String} theRec the underlying data
	+ @returns Number
	*/
	spc.hilitedActiveLineWidth = function(theDim, theRec) {
		return 4;
	}
	/** 
	* The width of a highlighted (e.g. on mouseover) unselected polyline. Default is 3.
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.hilitedInactiveLineWidth as hilitedInactiveLineWidth 
	* @param {String} theDim the dimension we are getting the label for ("p1Order", "p2Order" etc.)
	* @param {Array of String} theRec the underlying data
	+ @returns Number
	*/
	spc.hilitedInactiveLineWidth = function(theDim, theRec) {
		return 3;
	}
	/** 
	* The width of a non-highlighted selected polyline. Default is 1.
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.activeLineWidth as activeLineWidth 
	* @param {String} theDim the dimension we are getting the label for ("p1Order", "p2Order" etc.)
	* @param {Array of String} theRec the underlying data
	+ @returns Number
	*/
	spc.activeLineWidth = function(theDim, theRec) {
		if(spc.cd){
			return 2;	// LyV: colored axes can better be distinguished when lines are wider
		} else{
			return 1;
		}
	
	}
	/** 
	* The width of a non-highlighted unselected polyline. Default is 1.
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.inactiveLineWidth as inactiveLineWidth 
	* @param {String} theDim the dimension we are getting the label for ("p1Order", "p2Order" etc.)
	* @param {Array of String} theRec the underlying data
	* @returns Number
	*/
	spc.inactiveLineWidth = function(theDim, theRec) {
		if(spc.cd){
			return 2;	// LyV: colored axes can better be distinguished when lines are wider
		} else{
			return 1;
		}
	}
	
	//CC now some mouse handlers for the lines
	/** 
	* Handler for mouseover for a selected polyine. Default is to hilite it and show the value of the underlying data.
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.onActiveLineMouseOver as onActiveLineMouseOver 
	* @param {String} theDim the dimension we are getting the label for ("p1Order", "p2Order" etc.)
	* @param {Array of String} theRec the underlying data
	*/
	spc.onActiveLineMouseOver = function(theDim, thisRec) {
		spc.line.lineWidth(spc.hilitedActiveLineWidth);
		spc.line.render();
		spc.showVal(theDim, thisRec);
	}
	/** 
	* Handler for mouseout for a selected polyine. Default is to unhilite it.
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.onActiveLineMouseOut as onActiveLineMouseOut 
	* @param {String} theDim the dimension we are getting the label for ("p1Order", "p2Order" etc.)
	* @param {Array of String} theRec the underlying data
	*/
	spc.onActiveLineMouseOut = function(theDim, thisRec) {
		spc.line.lineWidth(spc.activeLineWidth);
		spc.line.strokeStyle(function(theDim, thisRec) {return spc.c[spc.active](thisRec[spc.active])});
		spc.line.render();
	}
	/** 
	* Handler for mouseover for a nonselected polyine. Default is to hilite it and show the value of the underlying data.
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.onInactiveLineMouseOver as onInactiveLineMouseOver 
	* @param {String} theDim the dimension we are getting the label for ("p1Order", "p2Order" etc.)
	* @param {Array of String} theRec the underlying data
	*/
	spc.onInactiveLineMouseOver = function(theDim, thisRec) {
		spc.inactiveLine.lineWidth(spc.hilitedInactiveLineWidth);
		spc.inactiveLine.strokeStyle("#343");
		spc.inactiveLine.render();
		spc.showVal(theDim, thisRec);
	}
	/** 
	* Handler for mouseout for a snonelected polyine. Default is to unhilite it.
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.onInactiveLineMouseOut as onInactiveLineMouseOut 
	* @param {String} theDim the dimension we are getting the label for ("p1Order", "p2Order" etc.)
	* @param {Array of String} theRec the underlying data
	*/
	spc.onInactiveLineMouseOut = function(theDim, thisRec) {
		spc.inactiveLine.lineWidth(spc.inactiveLineWidth);
		spc.inactiveLine.strokeStyle("#ddd");
		spc.inactiveLine.render();
	}
	
	//CC end line functions
	
	//CC dimension label functions
	/** 
	* The font specification for the label(s) of the axes. Default is "bold 12px sans-serif"
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.dLabelFont as dLabelFont 
	* @param {String} theDim the dimension we are setting the font for ("p1Order", "p2Order" etc.)
	* @returns CSS font specification 
	*/
	spc.dLabelFont = function(theDim) {
		return "bold 12px sans-serif";
	}
	/** 
	* The text for the label(s) of the axes. Default is the name of the unit of the dimension.
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.dLabelText as dLabelText 
	* @param {String} theDim the dimension we are getting the text for ("p1Order", "p2Order" etc.)
	* @returns String 
	*/
	spc.dLabelText = function(theDim) {
		return spc.units[theDim].lname;
	}
	/** 
	* The color for the label(s) of the axes. Default is green for the active axis and black for the others.
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.dLabelStyle as dLabelStyle 
	* @param {String} theDim the dimension we are getting the color for ("p1Order", "p2Order" etc.)
	* @returns Color 
	*/
	spc.dLabelStyle = function(theDim) {
		//CC new highlight label of active dimension
		if (theDim == spc.active) {
			return "rgb(0,255,0)";
		} else {
			return "rgb(0,0,0)"; //black
		}
	}
	/** 
	* Handler for mouseDown on an axis lable. Default is to make the the dimension active.
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.dLabelOnMouseDown as dLabelOnMouseDown 
	* @param {String} theDim the dimension we are handling ("p1Order", "p2Order" etc.)
	* @returns String 
	*/
	spc.dLabelOnMouseDown = function(theDim) {
		spc.active = theDim;
		spc.updateSelectionView();
	}
	
	//CC end dimension label functions
	
	//various color supplying functions
	/** 
	* The color of the axis selection bar(s) as it is selecting. default is hsla(80%,100%,50%,.5) (yellow) for all axes
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.handleBarSelectingColor as handleBarSelectingColor 
	* @param {String} theDim the dimension we are handling ("p1Order", "p2Order" etc.)
	* @returns Color 
	*/
	spc.handleBarSelectingColor = function(theDim) {
		return "hsla(15%,50%,50%,.7)";
	}
	/** 
	* The color of the axis selection bar(s) while it is not selecting. default is hsla(0,0,50%,.5) (medium gray) for all axes
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.handleBarStaticColor as handleBarStaticColor 
	* @param {String} theDim the dimension we are handling ("p1Order", "p2Order" etc.)
	* @returns Color 
	*/
	spc.handleBarStaticColor = function(theDim) {
		return "hsla(25%,70%,70%,.5)";
	}
	
	/** 
	* The color of the dividing line. Default is light pink.
	* @fieldOf StructuredParallelCoordinates.prototype
	* @exports spc.dividerColor as dividerColor 
	* @type Color
	*/
	spc.dividerColor = "rgb(255,255,255)";
	
	//end of color supplying functions
	
	/** 
	* Converts an underlying data item to a String
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.valToString as valToString 
	* @param {String} theDim (ignored)
	* @param {Array of String} thisRec the underlying data
	* @returns String
	*/
	spc.valToString = function(theDim, thisRec) { //CC theDim isn't really used, but we need it because of how .title calls its function; thisRec is the active record (e.g. from a mouseOver)

		var what = "";
		var delim = " | "; //was "\t";
		var i=1;
		while(true) {
			var posn = "f" + i;
			if (thisRec[posn] == null) {
				break;
			}
			if (i>1) {
				what = what + delim;
			}
			what = what + thisRec[posn];
			i++;
		}
		
		for (var d in thisRec) {
			if (d.indexOf("Order") > -1 || d == "ng" || d == "cdim") {
				continue;
			}
			if (d.match(/^p[0-9]+$/)) {
				continue;
			} 
			what = what + " " + thisRec[d];
		}
		return what;
	}
	
	//CC showVal is a hook, to be overridden. theDim, thisRec are to pass through to valToString
	//CC showVal is used within the protovis visualization, when mousing over lines
	/** 
	* A hook to be overridden. By default used when mousing over polylines. 
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.valToString as valToString 
	* @param {String} theDim the dimension we are handling ("p1Order", "p2Order" etc.)
	* @param {Array of String} thisRec the underlying data
	*/
	spc.showVal = function(theDim, thisRec) {  
	}
	//CC end new

	
	// Updater for slider and resizer.
	/** 
	* Function description
	* @private
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.update as update 
	* @param {Array} d interaction state data
	* @returns false
	*/
	spc.update = function(d) {
		
	/* CC orig
	  var t = d.dim;
	  filter[t].min = Math.max(y[t].domain()[0], y[t].invert(h - d.y - d.dy));
	  filter[t].max = Math.min(y[t].domain()[1], y[t].invert(h - d.y));
	*/
	 //CC new
		if (d[0] == null) {
			var t = d.dim;
		} else {
			t = d[0].dim;
		}


		var thisFilter = new Object();
		thisFilter.dim = t;
		thisFilter.min = Math.max(spc.y[t].domain()[0], spc.y[t].invert(spc.h - d.y - d.dy));
		thisFilter.max = Math.min(spc.y[t].domain()[1], spc.y[t].invert(spc.h - d.y));
		
		if (spc.isCtrl) {	
		//collapse coinciding filters as dragging
			var topFilter = spc.filter[t][ spc.filter[t].length -1];
			if (thisFilter.min == topFilter.min) {
				topFilter.max = Math.max(thisFilter.max, topFilter.max);
			} else if (thisFilter.max == topFilter.max) {
				topFilter.min = Math.min(thisFilter.min, topFilter.min);
			} else { //completely new area
				spc.filter[t].push( thisFilter );
			}
			//still TBD: collapse overlapping filters (doesn't really do any harm as is)
		} else {
			spc.filter[t] = [thisFilter];
		}

	  spc.gSelecting = t;
	  //change.render(); //CC orig  
	  spc.updateSelectionView(); //CC new
	  return false;
	}

	/** 
	* Update the presentation of the selection(s)
	* @private
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.updateSelectionView as updateSelectionView 
	*/
	spc.updateSelectionView = function() {
		spc.change.render();
		spc.gramRule.render(); //update item labels, just in case we're doing anything with them
		spc.dLabel.render(); //update label highlighting
	}

	// Updater for slider and resizer.
	/** 
	* Updates the slider
	* @private
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.selectAll as selectAll
	* @return false
	*/
	spc.selectAll = function(d) {

	  if (d.dy < 3) { //CC i.e., only really do select all when no or small movement, not drag
		var t = d.dim; //CC orig

	/* CC orig
		filter[t].min = Math.max(y[t].domain()[0], y[t].invert(0));
		filter[t].max = Math.min(y[t].domain()[1], y[t].invert(h));
	*/
	//CC new
		spc.filter[t] = [{dim:t}];
		spc.filter[t][0].min = Math.max(spc.y[t].domain()[0], spc.y[t].invert(0));
		spc.filter[t][0].max = Math.min(spc.y[t].domain()[1], spc.y[t].invert(spc.h));
	//CC end new
		d.y = 0; d.dy = spc.h; //CC doesn't seem to be needed
		
		//spc.active = t;  //this makes the axis active, but can be annoying
		//change.render(); //CC moved outside conditional
	  }
	  //CC new: remove highlighting after done selecting
	  spc.gSelecting = null; //CC new
	  //change.render(); //CC orig  
	  spc.updateSelectionView(); //CC new
	  //CC end new
	  return false;
	}

	//CC new
	/** 
	* Select all values on each axis
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.selectAllVals as selectAllVals 
	*/
	spc.selectAllVals = function() {
		var origActive = spc.active
		for (var d in spc.filter) {
			spc.selectAll({dim:d, dy:0}); //CC pass artificial value with right properties
			//selectAllValsOnOne(d); //CC much faster to render only once
		}
		spc.active = origActive;
		spc.gSelecting = null;
	  //change.render(); //CC orig  
	  spc.updateSelectionView(); //CC new
	}
	/** 
	* Select all values on a single axis
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.selectAllValsOnOne as selectAllValsOnOne 
	*/
	spc.selectAllValsOnOne = function(theDim) {
		var origActive = spc.active
		spc.selectAll({dim:theDim, dy:0}); //CC pass artificial value with right properties
		spc.active = origActive;
		spc.gSelecting = null;
	  //change.render(); //CC orig  
	  spc.updateSelectionView(); //CC new
	}

	/** 
	* Unselect all values on each axis
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.selectNoVals as selectNoVals 
	*/
	spc.selectNoVals = function() {
		var origActive = spc.active
		for (var d in spc.filter) {
			spc.update({dim:d, dy:0, y:spc.h+1}); //CC pass artificial value with right properties
			//selectNoValsOnOne(d); // much faster to render only once
		}
		spc.active = origActive;
		spc.gSelecting = null;
	  //change.render(); //CC orig  
	  spc.updateSelectionView(); //CC new
	}
	/** 
	* Unselect all values on a single axis
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.selectNoValsOnOne as selectNoValsOnOne 
	*/
	spc.selectNoValsOnOne = function(theDim) {
		var origActive = spc.active
		spc.update({dim:theDim, dy:0, y:spc.h+1}); //CC pass artificial value with right properties
		spc.active = origActive;
		spc.gSelecting = null;
	  //change.render(); //CC orig  
	  updateSelectionView(); //CC new
	}

	//select all but the lowest value on each axis
	/** 
	* Select all but the lowest value on each axis
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.selectAllButLowest as selectAllButLowest 
	*/
	spc.selectAllButLowest = function() {
		var origActive = spc.active
		for (var d in spc.filter) {
			//cf. update()
			spc.filter[d] = [{dim:d}];
			spc.filter[d][0].min = spc.y[d].domain()[0] + 0.5; //1 didn't work right for %
			spc.filter[d][0].max = spc.y[d].domain()[1];
			//d.y = 0; d.dy = h; //CC doesn't seem to be needed
			//active = d;
			//change.render();
			//selectAllButLowestOnOne(d); //CC much faster to render only once
		}
		spc.active = origActive;
		spc.gSelecting = null;
	  //change.render(); //CC orig  
	  spc.updateSelectionView(); //CC new
	}
	/** 
	* Select all but the lowest value on a single axis
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.selectAllButLowestOnOne as selectAllButLowestOnOne 
	*/
	spc.selectAllButLowestOnOne = function(theDim) {
		var origActive = spc.active

		//cf. update()
		spc.filter[theDim] = [{dim:theDim}];
		spc.filter[theDim][0].min = spc.y[theDim].domain()[0] + 0.5; //1 didn't work right for %
		spc.filter[theDim][0].max = spc.y[theDim].domain()[1];
	//	active = theDim;
	//	change.render();
		spc.active = origActive;
		spc.gSelecting = null;
	  //change.render(); //CC orig  
	  spc.updateSelectionView(); //CC new
	}
	/** 
	* Select all but the highest value on each axis
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.selectAllButHighest as selectAllButHighest 
	*/
	spc.selectAllButHighest = function() {
		var origActive = spc.active
		for (var d in spc.filter) {
			//cf. update()
			spc.filter[d] = [{dim:d}];
			spc.filter[d][0].min = spc.y[d].domain()[0];
			spc.filter[d][0].max = spc.y[d].domain()[1] -1; //O.5 didn't work right for %
			//d.y = 0; d.dy = h; //CC doesn't seem to be needed
			spc.active = d;
			//change.render(); //CC orig  
			spc.updateSelectionView(); //CC new
		}
		spc.active = origActive;
		spc.gSelecting = null;
	  //change.render(); //CC orig  
	  spc.updateSelectionView(); //CC new
	}
	/** 
	* Select all but the highest value on a single axis
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.selectAllButHighestOnOne as selectAllButHighestOnOne 
	*/
	spc.selectAllButHighestOnOne = function(theDim) {
		var origActive = spc.active
		
		//cf. update()
		spc.filter[theDim] = [{dim:theDim}];
		spc.filter[theDim][0].min = spc.y[theDim].domain()[0];
		spc.filter[theDim][0].max = spc.y[theDim].domain()[1] -1; //O.5 didn't work right for %

		spc.active = origActive;
		spc.gSelecting = null;
	  //change.render(); //CC orig  
	  spc.updateSelectionView(); //CC new
	}

	// Lyv new
	/** 
	* Update the selection to the new scale on each axis
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.updateInteractionState as updateInteractionState 
	*/
	spc.updateInteractionState = function() {
		var origFilter = spc.filter
		for (var d in origFilter) {
			spc.filter[d] = origFilter[d];
			//spc.filter[d][0].min = origFilter[d][0].min;
			if(origFilter[d][0].max > spc.y[d].domain()[1]){
				spc.filter[d][0].max = spc.y[d].domain()[1];
			}
			else{
				spc.filter[d][0].max = origFilter[d][0].max;
			}
			if(origFilter[d][0].min < spc.y[d].domain()[0]){
				spc.filter[d][0].min = spc.y[d].domain()[0];
			}
			else{
				spc.filter[d][0].min = origFilter[d][0].min;
			}
			
			//change.render(); //CC orig  
			spc.updateSelectionView(); //CC new
		}
		spc.gSelecting = null;
		spc.updateSelectionView(); //CC new
	}
	
	/** 
	* return array of ngrams that are selected by the filters 
	* @methodOf StructuredParallelCoordinates.prototype
	* @exports spc.getSelected as getSelected 
	* @returns Array of Object (ngrams)
	*/
	spc.getSelected = function() {
		return spc.ngrams.filter( function(g) {
			return spc.isVisible(g);
		});
	}
	
	
	/**
	 * TO select a line...
	 */
	spc.selectLine = function ( dim, value ){

		spc.filter[ dim ] = [{ "dim": dim }];

		spc.filter[ dim ][0].min = value;

		spc.filter[ dim ][0].max = value;

		spc.line.lineWidth(spc.hilitedActiveLineWidth);

		spc.updateSelectionView();

	}
	
	return spc;
} //end StructuredParallelCoordinates

