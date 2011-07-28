/**
 * @license Copyright 2010 Paul Novak (paul@wingu.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * @author paul@wingu.com (Paul Novak)
 */
goog.provide('specview.controller.plugins.Zoom');
goog.require('goog.debug.Logger');

// goog.exportSymbol('specview.controller.plugins.Zoom.COMMAND.ZOOM_IN',
// specview.controller.plugins.Zoom.COMMAND.ZOOM_IN);
// goog.exportSymbol('specview.controller.plugins.Zoom.COMMAND.ZOOM_OUT',
// specview.controller.plugins.Zoom.COMMAND.ZOOM_OUT);

/**
 * @constructor
 * @extends{specviewn.controller.Plugin}s
 */
specview.controller.plugins.Zoom = function() {
	this.initialCoordinates = null;
	this.finalCoordinates = null;
	this.rectangle = null;
	this.zooming_on_spectrum = false;
	this.zooming_on_molecule = false;
	
	specview.controller.Plugin.call(this);
}
goog.inherits(specview.controller.plugins.Zoom, specview.controller.Plugin);
goog.exportSymbol('specview.controller.plugins.Zoom',
		specview.controller.plugins.Zoom);

/**
 * Commands implemented by this plugin.
 * 
 * @enum {string}
 */
specview.controller.plugins.Zoom.COMMAND = {
	ZOOM_IN : 'zoomIn',
	ZOOM_OUT : 'zoomOut'
};




/**
 * 
 * @param initialCoordinates of the zoom rectangle (where the mouse clicked)
 * @param finalCoordinates of the zoom rectangle where the mouse is dragging
 * @param box
 * @returns {Array}
 */
specview.controller.plugins.Zoom.setRectangleZoom = function(initialCoordinates , finalCoordinates , box, b1, b2){

	var lower_boundary = null;
	var upper_boundary = null;
	var left_boundary = null;
	var right_boundary = null;
	
	if(b1){
			specview.controller.plugins.Zoom.logger2.info("zooming in molecule")
		 lower_boundary = document.metaSpecObject.mainMolBox[0].y;
		 upper_boundary = document.metaSpecObject.mainMolBox[3].y;
		 left_boundary = document.metaSpecObject.mainMolBox[0].x;
		 right_boundary = document.metaSpecObject.mainMolBox[1].x;		
		}else if(b2){
		specview.controller.plugins.Zoom.logger2.info("zooming in spectrum : "+document.editorObject.mainSpecBox)
		 lower_boundary = document.metaSpecObject.mainSpecBox[3].y;
		 upper_boundary = document.metaSpecObject.mainSpecBox[0].y;
		 left_boundary = document.metaSpecObject.mainSpecBox[0].x;
		 right_boundary = document.metaSpecObject.mainSpecBox[1].x;
		 	}


	
	var too_bellow = (finalCoordinates.y - document.getElementById("moleculeContainer").offsetTop) > lower_boundary;
	var too_left = (finalCoordinates.x - document.getElementById("moleculeContainer").offsetLeft) < left_boundary;
	var too_top = (finalCoordinates.y - document.getElementById("moleculeContainer").offsetTop) < upper_boundary;
	var too_right = (finalCoordinates.x - document.getElementById("moleculeContainer").offsetLeft) > right_boundary;
	

	var rect = null;
	
	var ar = new Array();
	
	var left;
	var top;
	var height;
	var width;

	var bottomLeft = finalCoordinates.x < initialCoordinates.x && finalCoordinates.y > initialCoordinates.y;
	var topLeft = finalCoordinates.x < initialCoordinates.x && finalCoordinates.y < initialCoordinates.y;
	var bottomRight = finalCoordinates.x > initialCoordinates.x && finalCoordinates.y > initialCoordinates.y;
	var topRight = finalCoordinates.x > initialCoordinates.x && finalCoordinates.y < initialCoordinates.y;

	if(bottomLeft){
			left = finalCoordinates.x + 15;
			top = initialCoordinates.y;
			width = initialCoordinates.x - finalCoordinates.x;
			height = finalCoordinates.y - initialCoordinates.y - 15;
	}else if(topLeft){
		left = finalCoordinates.x + 15;
		top = finalCoordinates.y + 15;
		width = initialCoordinates.x - finalCoordinates.x;
		height = initialCoordinates.y - finalCoordinates.y;
	}else if(bottomRight){
		left = initialCoordinates.x - 15;
		top = initialCoordinates.y - 15;
		width = finalCoordinates.x - initialCoordinates.x;
		height = finalCoordinates.y - initialCoordinates.y;		
	}else if(topRight){
		left = initialCoordinates.x;
		top = finalCoordinates.y + 15;
		width = finalCoordinates.x - initialCoordinates.x - 15;
		height = initialCoordinates.y - finalCoordinates.y ;
	}
	
	if(too_bellow){
		height = lower_boundary - top - 1;
//		alert("too below")
//		specview.controller.plugins.Zoom.logger2.info(width)
	}else if(too_right){
		width = right_boundary - left - 1;
//		alert("right")
//		specview.controller.plugins.Zoom.logger2.info("too right")
	}else if(too_left){
		left = left_boundary + 1;
//		alert("left")
//		specview.controller.plugins.Zoom.logger2.info("too left")
	}else if(too_top){
//		specview.controller.plugins.Zoom.logger2.info(top+ " ; "+upper_boundary)
		top = upper_boundary + 1 + document.getElementById("moleculeContainer").offsetTop;
//		alert("top")
//		specview.controller.plugins.Zoom.logger2.info("too top")
	}
//	specview.controller.plugins.Zoom.logger2.info(eid)
	ar["left"] = left + "px";
	ar["top"] = top + "px";
	ar["width"] = width + "px";
	ar["height"] = height + "px";
	
	return ar;
	
};



/**
 * Inverse map of execCommand strings to
 * {@link specview.controller.plugins.Zoom.COMMAND} constants. Used to determine
 * whether a string corresponds to a command this plugin handles
 * 
 * @type {Object}
 * @private
 */
specview.controller.plugins.Zoom.SUPPORTED_COMMANDS_ = goog.object
		.transpose(specview.controller.plugins.Zoom.COMMAND);

/** @inheritDoc */
specview.controller.plugins.Zoom.prototype.getTrogClassId = goog.functions
		.constant(specview.controller.plugins.Zoom.COMMAND);

/** @inheritDoc */
specview.controller.plugins.Zoom.prototype.isSupportedCommand = function(command) {
	return command in specview.controller.plugins.Zoom.SUPPORTED_COMMANDS_;
};

/** @inheritDoc */
specview.controller.plugins.Zoom.prototype.execCommand = function(command,
		var_args) {
	try {
		var current = this.editorObject.getScaleFactor();
		if (command == specview.controller.plugins.Zoom.COMMAND.ZOOM_IN) {
			this.editorObject.setScaleFactor(current * 1.1);
		} else if (command == specview.controller.plugins.Zoom.COMMAND.ZOOM_OUT) {
			this.editorObject.setScaleFactor(current * 0.9);
		}
		this.editorObject.setModelsSilently(this.editorObject.getModels());
	} catch (e) {
		this.logger.info(e);
	}
};

/**
 * The logger for this class.
 * 
 * @type {goog.debug.Logger}
 * @protected
 */
specview.controller.plugins.Zoom.prototype.logger = goog.debug.Logger
		.getLogger('specview.controller.plugins.Zoom');

specview.controller.plugins.Zoom.logger2 = goog.debug.Logger
.getLogger('specview.controller.plugins.Zoom');
