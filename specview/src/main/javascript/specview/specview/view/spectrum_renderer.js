/**
 * Copyright 2011 Mark Rijnbeek (markr@ebi.ac.uk)
 * and Samy Deghou (deghou@polytech.unice.fr)
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
 * 
 */
goog.provide('specview.view.SpectrumRenderer');
goog.require('specview.view.Renderer');
goog.require('goog.debug.Logger');
goog.require('specview.graphics.ElementArray');
goog.require('goog.array');

/**
 * Class to render a spectrum, a list of peaks really
 * 
 * @param {goog.graphics.AbstractGraphics} something to draw on
 * @param {Object=} opt_config override default configuration
 * @constructor
 * @extends {specview.view.Renderer}
 */
specview.view.SpectrumRenderer = function(graphics, opt_config, opt_box) {
	specview.view.Renderer.call(this, graphics, specview.view.SpectrumRenderer.defaultConfig, opt_config);
    this.box= opt_box;
};
goog.inherits(specview.view.SpectrumRenderer, specview.view.Renderer);

specview.view.SpectrumRenderer.prototype.setBoundsBasedOnMolecule = function(molecule) {

    var molBox = molecule.getBoundingBox();
    molHeight=Math.abs(molBox.top-molBox.bottom);
    molWidth=Math.abs(molBox.left-molBox.right);
    size=Math.max(molHeight,molWidth);
    top=molBox.bottom;
    bottom=top-size;
    left= 1.1*molBox.right;
    right=left+size;
    bottom=-5.5;
    right=23.5;

    
    right=right<left ? 23.5 : right;//for the special case mass spectra
    left=left>right ? 10 : left;
    
    this.box = new goog.math.Box(top,right,bottom,left);

};


specview.view.SpectrumRenderer.prototype.setBoundsBasedOnMetaSpecmetaSpecObject=function(metaSpec){
	var molecule=metaSpec.molecule;
	var spectrum=metaSpec.spectrum;
    var specBox = spectrum.getBoundingBox();
    var molBox=molecule.getBoundingBox();
    this.logger.info("In spectrum renderer: "+molBox);
    specHeight=Math.abs(specBox.top-specBox.bottom);
    specWidth=Math.abs(specBox.left-specBox.right);
    size=Math.max(specHeight,specWidth);
    top=molBox.bottom;//THE TOP IS FUNCTION OF THE MOLECULE
    bottom=top-size;
    left= 1.1*molBox.right;//THE LEFT IS FUNCTION OF THE MOLECULE
    right=left+size;
    this.box = new goog.math.Box(top,right,bottom,left);
};


specview.view.SpectrumRenderer.prototype.test = function(a,b){
	this.logger.info(a+";"+b);
}

/**
 * Render the static spectrum
 * @param metaSpecObject
 * @param transform
 * @param opt_box
 * @param opt_peak
 * @param opt_main_molecule
 * @param opt_color
 */
specview.view.SpectrumRenderer.prototype.renderSpec = function(spectrum,metaSpecObject, transform, opt_box,opt_peak,opt_main_molecule,opt_color){
	var color = opt_color!=undefined ? opt_color : 'black';
    this.setTransform(transform);
    var peakPath = new goog.graphics.Path();
    var peakStroke = new goog.graphics.Stroke(1.05,color);
    var peakFill = null;       


    goog.array.forEach(spectrum.peakList,
    	    function(peak) {
    	    	if(peak.isVisible){
    	            peakPath.moveTo(peak.xPixel, peak.yPixel); 
    	            peakPath.lineTo(peak.xTpixel,peak.yTpixel);	
    	    	}
    	    },
    	    this);    
    this.graphics.drawPath(peakPath, peakStroke, peakFill);

    
}

/**
 * The spectrum is simply the object
 * Transform is static and has been set up in specview.controller.Controller.prototype.render. 
 */
specview.view.SpectrumRenderer.prototype.render = function(metaSpecObject, transform, opt_box,opt_peak,opt_main_molecule,opt_color) {
	var color = opt_color!=undefined ? opt_color : 'black';
	var spectrum=metaSpecObject.spectrum==undefined ? metaSpecObject : metaSpecObject.spectrum;
	if(metaSpecObject.dimension > 1){
//		alert(metaSpecObject.ArrayOfSpectrum[1].peakList.length)
//		spectrum = metaSpecObject.ArrayOfSpectrum[1]
	}
	var secondarySpectrum=metaSpecObject.secondarySpectrum;
    this.setTransform(transform);
    var peakPath = new goog.graphics.Path();
    var peakStroke = new goog.graphics.Stroke(1.05,color);
    var peakStroke2 = new goog.graphics.Stroke(2.05,"red");
    var peakFill = null;   


    /*
     * big spectrum
     */
    goog.array.forEach(spectrum.peakList,
    function(peak) {
    	if(peak.isVisible){
            peakPath.moveTo(peak.xPixel, peak.yPixel); 
            peakPath.lineTo(peak.xTpixel,peak.yTpixel);	
    	}

    	metaSpecObject.ArrayOfPrimaryMolecules[peak.arrayOfSecondaryMolecules] != undefined ? peak.parentPeak = true : peak.parentPeak = false;
    	
    	peak.parentPeak ? this.graphics.drawPath(peakPath, peakStroke2, peakFill) : this.graphics.drawPath(peakPath, peakStroke, peakFill);
    	
    },
    this);

    /*
     * small spectrum
     
    goog.array.forEach(spectrum.secondpeakList,
    	    function(peak) {
    	    	if(peak.isVisible){
    	            peakPath.moveTo(peak.xPixel, peak.yPixel); 
    	            peakPath.lineTo(peak.xTpixel,peak.yTpixel);	
    	    	}
    	    	metaSpecObject.ArrayOfPrimaryMolecules[peak.arrayOfSecondaryMolecules] != undefined ? peak.parentPeak = true : peak.parentPeak = false;

    		},
    	    this);
    */
    this.graphics.drawRect(metaSpecObject.zoomBox["left"],
			   metaSpecObject.zoomBox["top"],
			   metaSpecObject.zoomBox["right"]-metaSpecObject.zoomBox["left"],
			   metaSpecObject.zoomBox["bottom"]-metaSpecObject.zoomBox["top"],
			   new goog.graphics.Stroke(2, 'black'),
			   null);
    
    this.graphics.drawPath(peakPath, peakStroke, peakFill);    
    if(opt_peak){
        var stroke = new goog.graphics.Stroke(0.1,'black');
    	var fill = new goog.graphics.SolidFill('black');
        var font = new goog.graphics.Font(18, 'Comics');
        this.graphics.drawText("Peak information:", 
        						850,
        						5,
        						600,
        						200,
        						'left',
        						null,
        						font, stroke, fill);
        this.graphics.drawText("m/z value: "+opt_peak.xValue,
        						850,
        						30,
        						600, 200,
        						'left', null,
        						new goog.graphics.Font(11.5, 'Comics'), stroke, fill);
        this.graphics.drawText("Fragment molecule: "+opt_peak.arrayOfSecondaryMolecules,
        						850,
        						45,
        						600, 200,
        						'left', null,
        						new goog.graphics.Font(11.5, 'Comics'), stroke, fill);
        this.graphics.drawText("Parent molecule: "+opt_main_molecule,
        						850,
        						60,
        						600, 200,
        						'left', null,
        						new goog.graphics.Font(11.5, 'Comics'), stroke, fill);
        this.graphics.drawText("Mass of the molecule: So far not available",
        						850,
        						75,
        						600, 200,
        						'left', null,
        						new goog.graphics.Font(11.5, 'Comics'), stroke, fill);
        this.graphics.drawText("Pixel coordinates: "+opt_peak.pixelCoord,
        						850,
        						90,
        						600, 200, 'left', null,
        						new goog.graphics.Font(11.5, 'Comics'), stroke, fill);
    }
    
    
	var top = metaSpecObject.mainSpecBox[0].y;
	var left = metaSpecObject.mainSpecBox[0].x;
	var right = metaSpecObject.mainSpecBox[1].x;
	var bottom = metaSpecObject.mainSpecBox[2].y;
	
	var spectrumBox = new Array(top,right,bottom,left);
    if(metaSpecObject.dimension > 1 ){
        var stroke = new goog.graphics.Stroke(0.3,'black');
    	var fill = new goog.graphics.SolidFill('black');
        var font1 = new goog.graphics.Font(18, 'Comics');
        var font2 =	new goog.graphics.Font(13, 'Comics');
    	var string = "";
    	for(var k = 1 ; k < metaSpecObject.dimension+1 ; k++){
    		string += "MS_"+k+"-----";
    	}
    	this.graphics.drawText(string,
				250,
				document.metaSpecObject.secondSpecBox["top"]-55,
				600,
				200,
				'center',
				null,
				font2,
				stroke,
				fill);
    }
	
//	this.renderBoundingBox(metaSpecObject.mainSpecBox,'red')
    
};


specview.view.SpectrumRenderer.prototype.renderSecondSpectrum = function(metaSpecObject, transform, opt_box,opt_peak,opt_main_molecule,opt_color){
	var color = opt_color!=undefined ? opt_color : 'black';
	var spectrum=metaSpecObject.spectrum==undefined ? metaSpecObject : metaSpecObject.spectrum;
    var peakPath = new goog.graphics.Path();
    var peakPath = new goog.graphics.Path();
    var peakStroke = new goog.graphics.Stroke(1.05,color);
    var peakStroke2 = new goog.graphics.Stroke(2.05,"red");
    var peakFill = null; 

	/*
     * small spectrum
     */
    goog.array.forEach(spectrum.secondpeakList,
    	    function(peak) {
    	    	if(peak.isVisible){
    	            peakPath.moveTo(peak.xPixel, peak.yPixel); 
    	            peakPath.lineTo(peak.xTpixel,peak.yTpixel);	
    	    	}
    	    	metaSpecObject.ArrayOfPrimaryMolecules[peak.arrayOfSecondaryMolecules] != undefined ? peak.parentPeak = true : peak.parentPeak = false;

    		},
    	    this);
    
    this.graphics.drawRect(metaSpecObject.zoomBox["left"],
			   metaSpecObject.zoomBox["top"],
			   metaSpecObject.zoomBox["right"]-metaSpecObject.zoomBox["left"],
			   metaSpecObject.zoomBox["bottom"]-metaSpecObject.zoomBox["top"],
			   new goog.graphics.Stroke(2, 'black'),
			   null);
    
    this.graphics.drawPath(peakPath, peakStroke, peakFill);  
}

/**
 * In order to remove the spectrum element from the canvas, we draw and fill a rectangle element.
 * The rectangle element correspond to the box the spectrum has been drawn in.
 * The box is supposed to be in the same format as nmrData.mainSpecBox
 * @param box
 * @param graphics
 */
specview.view.SpectrumRenderer.prototype.clearSpectrum = function(box,graphics){
///	alert(b)
    var fill = new goog.graphics.SolidFill('#FFFFFF');
    var stroke = new goog.graphics.Stroke(2, '#FFFFFF');
	graphics.drawRect(box[0].x-15,box[0].y-5,box[1].x-box[0].x+10,box[2].y-box[1].y+15,stroke,fill);
};


specview.view.SpectrumRenderer.prototype.highlightOn = function(peak,editor) {
//	alert(peak.isVisible)
	opt_element_array = new specview.graphics.ElementArray();
	var fill = new goog.graphics.SolidFill("#55bb00", .3);
    var peakPath = new goog.graphics.Path();
    peakPath.moveTo(peak.xPixel, peak.yPixel); 
    peakPath.lineTo(peak.xTpixel,peak.yTpixel);
    opt_element_array.add(this.graphics.drawPath(peakPath,new goog.graphics.Stroke(2,document.editorObject.peakColor),null));
	return opt_element_array;
};


specview.view.SpectrumRenderer.prototype.highlightOnSerieOfPeaks = function(peaks,editor) {
//	alert("caca")
	this.clearSpectrum(editor.specObject.mainSpecBox,editor.graphics);
	this.render(editor.specObject,editor.transform);
	this.renderAxis(editor.specObject,this.box,'black');
	this.renderGrid(editor.specObject.mainSpecBox,'black',editor.specObject.spectrum);	

	opt_element_array = new specview.graphics.ElementArray();

	for(k in peaks){
		var peak = peaks[k];
		var fill = new goog.graphics.SolidFill("orange", .3);
	    var peakPath = new goog.graphics.Path();
	    peakPath.moveTo(peak.xPixel, peak.yPixel); 
	    peakPath.lineTo(peak.xTpixel,peak.yTpixel);
	    opt_element_array.add(this.graphics.drawPath(peakPath,new goog.graphics.Stroke(2,document.editorObject.peakColor),null));
	}
	
	return opt_element_array;
};

/**
 * Draw the rectangle that will be used to perform the zoom effect
 * @param rectangle
 * @param editor
 */
specview.view.SpectrumRenderer.prototype.drawRectangle = function(rectangle,editor){
    var stroke = new goog.graphics.Stroke(2, 'black');
    var peakPath = new goog.graphics.Path();
	editor.graphics.drawRect(rectangle.left-document.getElementById("moleculeContainer").offsetLeft,
							 rectangle.top-document.getElementById("moleculeContainer").offsetTop,
							 rectangle.width,
							 rectangle.height,
							 stroke,
							 null);
}


/**
 * When the user is zooming he draw a rectangle over the spectrum.
 * In order to expand te  size of the rectangle we draw several of them
 * Hence we need to delete the k-1 rectangle in order to draw the k rectangle.
 * @param rectangle
 * @param editor
 */
specview.view.SpectrumRenderer.prototype.clearRectangle = function(rectangle,editor){
    var stroke = new goog.graphics.Stroke(2.1, '#FFFFFF');
    var peakPath = new goog.graphics.Path();
	editor.graphics.drawRect(rectangle.left-20,rectangle.top-190,rectangle.width,rectangle.height,stroke,null);	
}

/**
 * Draw a rectangle on the secondary spectrum to map where the user is when zooming on the spectrum
 * @param left
 * @param width
 * @param metaSpecObject
 */
specview.view.SpectrumRenderer.prototype.mapZoomSpectrum = function(left,width,metaSpecObject,editor){

    var stroke = new goog.graphics.Stroke(0.01,'black');
	var fill = new goog.graphics.SolidFill('orange',0.3);
    var font = new goog.graphics.Font(18, 'Comics');
	editor.graphics.drawRect(metaSpecObject.zoomBox["left"],
							 metaSpecObject.zoomBox["top"],
							 metaSpecObject.zoomBox["right"]-metaSpecObject.zoomBox["left"],
							 metaSpecObject.zoomBox["bottom"]-metaSpecObject.zoomBox["top"],
							 new goog.graphics.Stroke(0.5,"orange"),
							 fill);

};



specview.view.SpectrumRenderer.logger = goog.debug.Logger.getLogger('specview.view.SpectrumRenderer');
