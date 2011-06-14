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
    
    //this.box = new goog.math.Box(top,right,bottom,left);//THIS IS THE GOOD BOX FOR INVERTING
//   alert("top"+this.box.top+"\nleft"+this.box.left+"\nright:"+this.box.right+"\nbottom:"+this.box.bottom)
    
    /*
    var gSize=graphics.getSize();
    b = new goog.math.Box();
    b.left = gSize.width/2;
    b.right = gSize.width*(2/3);
    b.top = gSize.height*(5/6);
    b.bottom = gSize.height*(1/6);
    this.box=b;
    this.logger.info(this.box.top+" "+this.box.bottom+" "+this.box.left+" "+this.box.right+" ");
    */
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
//	var spectrum=metaSpecObject.spectrum==undefined ? metaSpecObject : metaSpecObject.spectrum;
//	var secondarySpectrum=metaSpecObject.secondarySpectrum;
	alert(spectrum.displayXpixelNice())
    this.setTransform(transform);
    var peakPath = new goog.graphics.Path();
    var peakStroke = new goog.graphics.Stroke(1.05,color);
    var peakFill = null;       
    
//    this.graphics.drawRect(metaSpecObject.secondSpecBox["left"],
//			   metaSpecObject.secondSpecBox["top"],
//			   metaSpecObject.secondSpecBox["right"]-metaSpecObject.secondSpecBox["left"],
//			   metaSpecObject.secondSpecBox["bottom"]-metaSpecObject.secondSpecBox["top"],
//			   new goog.graphics.Stroke(2, 'black'),
//			   null);

    goog.array.forEach(spectrum.peakList,
    	    function(peak) {
    	    	if(peak.isVisible){
    	            peakPath.moveTo(peak.xPixel, peak.yPixel); 
    	            peakPath.lineTo(peak.xTpixel,peak.yTpixel);	
    	    	}
//    	    	this.logger.info(color+"  "+peak.isVisible)
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
	var secondarySpectrum=metaSpecObject.secondarySpectrum;
    this.setTransform(transform);
    var peakPath = new goog.graphics.Path();
    var peakStroke = new goog.graphics.Stroke(1.05,color);
    var peakFill = null;   
//    this.logger.info("new")
//    this.logger.info(metaSpecObject.spectrum==metaSpecObject.secondarySpectrum);
    
//    this.logger.info("primary spectrum")
    goog.array.forEach(spectrum.peakList,
    function(peak) {
    	if(peak.isVisible){
            peakPath.moveTo(peak.xPixel, peak.yPixel); 
            peakPath.lineTo(peak.xTpixel,peak.yTpixel);	
    	}
//    	this.logger.info(color+"  "+peak.isVisible)
    },
    this);

    goog.array.forEach(spectrum.secondpeakList,
    	    function(peak) {
    	    	if(peak.isVisible){
    	            peakPath.moveTo(peak.xPixel, peak.yPixel); 
    	            peakPath.lineTo(peak.xTpixel,peak.yTpixel);	
    	    	}
//    	    	this.logger.info(color+"  "+peak.isVisible)
    	    },
    	    this);
    
//    this.logger.info("\n\n"+spectrum.displayXpixelNice()+"\n"+secondarySpectrum.displayXpixelNice())
    
    
    this.graphics.drawRect(400,600,200,200,new goog.graphics.Stroke(2, 'black'),null)
    this.graphics.drawRect(metaSpecObject.secondSpecBox["left"],
    					   metaSpecObject.secondSpecBox["top"],
    					   metaSpecObject.secondSpecBox["right"]-metaSpecObject.secondSpecBox["left"],
    					   metaSpecObject.secondSpecBox["bottom"]-metaSpecObject.secondSpecBox["top"],
    					   new goog.graphics.Stroke(2, 'black'),
    					   null);
    this.graphics.drawPath(peakPath, peakStroke, peakFill);    
    if(opt_peak){
        var stroke = new goog.graphics.Stroke(0.1,'black');
    	var fill = new goog.graphics.SolidFill('black');
        var font = new goog.graphics.Font(18, 'Comics');
        this.graphics.drawText("Peak information:", 720, 291, 600, 200, 'left', null,
                font, stroke, fill);
        this.graphics.drawText("m/z value: "+opt_peak.xValue, 720, 315, 600, 200, 'left', null,
        		new goog.graphics.Font(11.5, 'Comics'), stroke, fill);
        this.graphics.drawText("Fragment molecule: "+opt_peak.arrayOfSecondaryMolecules, 720, 330, 600, 200, 'left', null,
        		new goog.graphics.Font(11.5, 'Comics'), stroke, fill);
        this.graphics.drawText("Parent molecule: "+opt_main_molecule, 720, 345, 600, 200, 'left', null,
        		new goog.graphics.Font(11.5, 'Comics'), stroke, fill);
        this.graphics.drawText("Mass of the molecule: So far not available", 720, 360, 600, 200, 'left', null,
        		new goog.graphics.Font(11.5, 'Comics'), stroke, fill);
        this.graphics.drawText("Pixel coordinates: "+opt_peak.pixelCoord, 720, 375, 600, 200, 'left', null,
        		new goog.graphics.Font(11.5, 'Comics'), stroke, fill);
    }
    
};

/**
 * In order to remove the spectrum element from the canvas, we draw and fill a rectangle element.
 * The rectangle element correspond to the box the spectrum has been drawn in.
 * The box is supposed to be in the same format as nmrData.mainSpecBox
 * @param box
 * @param graphics
 */
specview.view.SpectrumRenderer.prototype.clearSpectrum = function(box,graphics){
    var fill = new goog.graphics.SolidFill('#FFFFFF');
    var stroke = new goog.graphics.Stroke(2, '#FFFFFF');
	graphics.drawRect(box[0].x-15,box[0].y-7,box[3].x,box[0].x,stroke,fill);
};


specview.view.SpectrumRenderer.prototype.highlightOn = function(peak,editor) {
	opt_element_array = new specview.graphics.ElementArray();
	var fill = new goog.graphics.SolidFill("#55bb00", .3);
    var peakPath = new goog.graphics.Path();
    peakPath.moveTo(peak.xPixel, peak.yPixel); 
    peakPath.lineTo(peak.xTpixel,peak.yTpixel);
    opt_element_array.add(this.graphics.drawPath(peakPath,new goog.graphics.Stroke(2,'red'),null));
//    opt_element_array.add(this.graphics.drawText(50, 150, 100, 500, 600,'center', null, font, stroke, fill));
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
//	this.logger.info("in clearing rectangle: "+rectangle.left-20+";"+rectangle.top-190+";"+rectangle.width+";"+rectangle.height)
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
	var percentageLeft=(left-metaSpecObject.mainSpecBox[0].x)/(metaSpecObject.mainSpecBox[1].x-metaSpecObject.mainSpecBox[0].x);
	var percentageRight=((left+width)-metaSpecObject.mainSpecBox[0].x)/(metaSpecObject.mainSpecBox[1].x-metaSpecObject.mainSpecBox[0].x);
	var newLeft = percentageLeft*(metaSpecObject.secondSpecBox["right"]-metaSpecObject.secondSpecBox["left"]);
	newLeft = newLeft+metaSpecObject.secondSpecBox["left"];
	var newRight = percentageRight*(metaSpecObject.secondSpecBox["right"]-metaSpecObject.secondSpecBox["left"]);
	newRight = newRight+metaSpecObject.secondSpecBox["left"];
    var stroke = new goog.graphics.Stroke(0.01,'black');
	var fill = new goog.graphics.SolidFill('orange',0.3);
    var font = new goog.graphics.Font(18, 'Comics');
	
	editor.graphics.drawRect(newLeft,
							metaSpecObject.secondSpecBox["top"],
							 newRight-newLeft,
							 metaSpecObject.secondSpecBox["bottom"]-metaSpecObject.secondSpecBox["top"],
							 new goog.graphics.Stroke(0.5,"orange"),
							 fill);
};



specview.view.SpectrumRenderer.logger = goog.debug.Logger.getLogger('specview.view.SpectrumRenderer');
