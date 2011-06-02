/**
 * Copyright 2011 Samy Deghou (deghou@polytech.unice.fr)
 * and  Mark Rijnbeek (markr@ebi.ac.uk)
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

goog.provide('specview.model.Peak');
goog.require('goog.math.Coordinate');

/*
 * Constructeur of the peak
 */
specview.model.Peak=function(opt_xValue,opt_intensity,opt_peakId,opt_atomRef,opt_multiplicity,opt_molRefs,opt_Xunit,opt_Yunit,opt_isVisible){

	/**
	 * xValue is the value of the shift of the peak as found in the cml file. Unit is in ppm
	 */
	this.xValue=opt_xValue;
	
	/**
	 * intensity is the intensity of the peak.
	 * For cml files holding Mass Spectrometry experiment, the intensity is an information provided.
	 * For cml files holding NMR experiment two possible cases:
	 * 		(i) 	The NMR experiment is a C-NMR experiment: in that case, every peak has the same height and thence,
	 * 				the height is not a an information provided.
	 * 		(ii)	The NMR experiment is a H-NMR experiment: in that case, not every peak has the same height. In that
	 * 				case, two possible situations:
	 * 					(i)  Sometimes, the information on the height is available in the file.
	 * 					(ii) If not, the height information should be computed on the fly.
	 */
	this.intensity=opt_intensity;
	
	/**
	 * peakId is a peak identifier that is provided within the cml file
	 */
	this.peakId=opt_peakId;
	
	/**
	 * If the cml file holds NMR experiment data, and if it is an annotated file, then the peak are linked to one or
	 * multiple atoms. atomref is an atom identifier or an array of atom identifier found in the file.
	 */
	this.atomRef=opt_atomRef;// Should be an array
	
	/**
	 * If the cml file holds NMR experiment data, the peak can have different shape: singulet, doublet, triplet etc.
	 * This information is provided in the file.
	 */
	this.multiplicity=opt_multiplicity;
	
	/**
	 * coord are the coordinate of the peak based on its xValue
	 */
	this.coord=new goog.math.Coordinate(this.xValue,-0.16);
	
	/**
	 * 
	 */
	this.atomMap=new Array();
	this.atomMap[this.peakId]=this.atomRef;// Hence it is an array of array. Ex : ["p1":["a1","a2",...,"ai"]]
	
	/**
	 * xPixel and yPixel the the transformed coordinate of the peak, the "real" coordinate in pixel unit to which it is displayed
	 * xPixel and yPixel are the "starting" coordinates of the peak, its origine
	 */
	this.xPixel=0;
	this.yPixel=0;
	
	/**
	 * xTpixel and yTpixel the the transformed coordinate of the peak, the "real" coordinate in pixel unit to which it is displayed
	 * xTpixel and yTpixel are the "ending" coordinates of the peak.
	 * xTpixel==xPixel
	 * Math.abs(yTpixel-yPixel)=height of the peak
	 */	
	this.xTpixel=0;
	this.yTpixel=0;
	
	/**
	 * The unit of the shift and of the intensity.
	 * These are displayed on the axis
	 */
	this.peakXunit=opt_Xunit;
	this.peakYunit=opt_Yunit;
	
	/**
	 * Represent the coordinate object of the pixel coordinates(the one used in the constructor of neighborlist object
	 * in order to retrieve the peak on the canvas editor);
	 */
	this.pixelCoord=null;
	
	/**
	 * isVisible is a boolean.
	 * If the xPixel value is inside the box of the spectrum (mainSpecBox of the nmrdata object) isVisble = true and 
	 * the the peak is displayed to the screen. If isVIsible=false, the coordinate of the peak are outside the box 
	 * (useful when zooming for instance) and the peak is not displayed.
	 */
	this.isVisible=opt_isVisible;
	
	/**
	 * If the file holds Mass Spec experiment, and if the file is annotated, then some peaks are related to one or many
	 * molecules.
	 * arrayOfSecondary molecules contains the molecule identifiers that refers to the molecules.
	 */
	this.arrayOfSecondaryMolecules=opt_molRefs;//normally just one molecule
	
	
};
goog.exportSymbol("specview.model.Peak", specview.model.Peak);

/*
 * This one is wrong and should not be like that.
 */
specview.model.Peak.prototype.setCoord = function(){
	this.coord=new goog.math.Coordinate(this.xPixel,this.yPixel);
};

/*
 * Set the coordinate object of the pixel coordinates
 */
specview.model.Peak.prototype.setPixelCoordinates = function(){
	this.pixelCoord = new goog.math.Coordinate(this.xPixel,this.yPixel);
}


/*
 * This is about pixels
 */
specview.model.Peak.prototype.setCoordinates=function(xb,yb,xt,yt){
	this.xPixel=xb;
	this.yPixel=yb;
	this.xTpixel=xt;
	this.yTpixel=yt;
	this.pixelCoord=new goog.math.Coordinate(this.xPixel,this.yPixel);
//	alert("pixel coordinates have been set: "+parseInt(xb))
};

specview.model.Peak.prototype.setXvalue=function(value){
	this.xValue=value;
};

/*
 * Description of the object
 */
specview.model.Peak.prototype.toString = function() {
	return "===Object of type Peak====\n " + "--"+this.xValue + "\n"+"--"+this.intensity+"\n"+"--"+this.peakId+"\n"+"--"+this.atomRef+"\n\n"+"--"+this.arrayOfSecondaryMolecules;
};



