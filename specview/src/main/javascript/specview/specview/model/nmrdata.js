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

goog.provide('specview.model.NMRdata');

goog.require('specview.model.Molecule');
goog.require('specview.model.Spectrum');
goog.require('specview.view.SpectrumRenderer');
goog.require('goog.debug.Logger');

/*
 * A class to capture spectrum data as found in NMRshiftDb.
 */

specview.model.NMRdata=function(){
	/**
	 * THe experience type that the file holds, e.g MS or NMR.
	 */
	this.experienceType="";
	/**
	 * The main molecule of the experiment. In case of NMR, it would be the only molecule. In case of MS, it would 
	 * be the reactant molecule.
	 */
	this.molecule=null;
	/**
	 * The only spectrum of the experiment as found in the file
	 */
	this.spectrum=null;
	/**
	 * Only available with MS data. Array holding all the fragment molecule of the reactant molecule as found in the file
	 */
	this.secondaryMolecule=null;
	/**
	 * These arrays are useful when dealing with NMR data.IN NMR data, each peak is associated with at least one atom
	 */
	this.ArrayOfAtoms=new Array(); // Keys are the inner atom id. Values are the atom objects
	this.ArrayOfBonds=new Array();
	this.ArrayOfPeaks=new Array();
	this.ArrayOfSecondaryMolecules=new Array();//{m1:molecule1;m2:moleucle2...}
	/**
	 * The box of the original fragment of the ms Experiment. Every other fragment has to be drawn in that box. 
	 */
	this.mainMolBox=null;
	/**
	 * The box of the spectrum. Calculated according the the mainMolBox
	 */
	this.mainSpecBox=null;
	/**
	 * transform object.
	 * When an atom is parsed from the file, it contains relative coordinates that usually and preferably are in the 
	 * range [-10;10]. In ordered to be displayed to the screen, these coordinates has to be transformed according to
	 * the parameter of the controller object.
	 * These coordinates are transformed in the method:
	 *  {@see specview.model.nmrData.prototype.setCoordinatesWithPixelOfMolecule}.
	 * There we create a transform object and use it to transform the coordinate,
	 * It is however to keep track of that transform object that we creates, because it is still used in other classes,
	 * for instance in {@see double_bond_renderer.js}. And in order to make sure that we use make the same
	 * transformations (e.g use the same transform object) we shall make an attribute out of it of the object nmrData.
	 */
    this.transform=null;
	
	
};

specview.model.NMRdata.prototype.toString = function() {
	return "experiment type: "+this.experienceType+"\nnumber of sfragments: "+this.ArrayOfSecondaryMolecules.length;
	
};

specview.model.NMRdata.prototype.logger = goog.debug.Logger.getLogger('specview.model.NMRdata');
specview.model.NMRdata.logger = goog.debug.Logger.getLogger('specview.model.NMRdata');

specview.model.NMRdata.prototype.setEditor=function(controllerEditor){
	this.editor=controllerEditor;
};


/**
 * Set the pixel coordinate of the molecule
 * Create a box or the specturm according to the pixel coordinates of the molecule
 * Set the pixel coordinate of the spectrum according the the spectrum box that has been created
 * @param editorSpectrum
 * @param zoomX
 */
specview.model.NMRdata.prototype.setCoordinatesWithPixels = function(editorSpectrum){
	this.mainMolBox=this.getMoleculeBox(editorSpectrum);
	this.setCoordinatesPixelOfMolecule(editorSpectrum);
  	this.mainSpecBox=this.getSpectrumBox();
  	this.setCoordinatesPixelOfSpectrum();
}; 

/**
 * Build a box for the molecule.
 * We first need to get a box of relative coordinates out of the relative coordinates of the molecule(coordinates found
 * in the file). That is Step 1.
 * Then, we need to transform these coordinates into pixel coordinates in order to render them. To do so, we need the 
 * object transform. That is Step 2
 * 
 */ 
specview.model.NMRdata.prototype.getMoleculeBox = function(editorSpectrum){
   	/*
	 * Step 1
	 */
	var molecule=this.molecule;
	var box=molecule.getBoundingBox();
    var boxTopLeftCoord =new goog.math.Coordinate(box.left,box.top);
    var boxTopRightCoord =new goog.math.Coordinate(box.right,box.top);
    var boxBotLeftCoord =new goog.math.Coordinate(box.left,box.bottom);
    var boxBotRightCoord =new goog.math.Coordinate(box.right,box.bottom);
    boxTopRightCoord=(boxTopRightCoord.x<boxTopLeftCoord.x ? new goog.math.Coordinate(1200,box.top) : boxTopRightCoord);
    boxBotRightCoord=(boxBotRightCoord.x<boxBotLeftCoord.x ? new goog.math.Coordinate(1200,box.bottom) : boxBotRightCoord);
	/*
	 * Step 2
	 */    
	var atom_coords=goog.array.map(this.molecule.atoms,function(a) {return a.coord; });//the coords of the file. Simple array
	var relative_box=goog.math.Box.boundingBox.apply(null, atom_coords);
  	var scaleFactor = 0.90; 
  	var widthScaleLimitation = 0.4;
  	var margin = 0.3;
	var editor=editorSpectrum;
	var ex_box=relative_box.expand(margin,margin,margin,margin);
	var transform = specview.graphics.AffineTransform.buildTransform(ex_box,widthScaleLimitation,editorSpectrum.graphics,scaleFactor);
    return transform.transformCoords( [boxTopLeftCoord,boxTopRightCoord,boxBotLeftCoord,boxBotRightCoord]);
};



/**
 * Set the pixel coordinates of the molecule.
 * Use the relative coordinate of the molecule found in the file.
 * Use the transform object of the google closure library. This object is an attribute of the editor(the controller)
 * and is dependant on the canvas created. Hence, this function must have an editor as an argument to extract the transform object
 * that will transform the relative coordinates into pixel coordinates.
 * @param editorSpectrum
 * @param zoomX
 */
specview.model.NMRdata.prototype.setCoordinatesPixelOfMolecule = function(editorSpectrum){    
	var molecule=this.molecule;	var spectrum=this.spectrum;	var editor=editorSpectrum;
    var molBox = molecule.getBoundingBox();//CREATE THE MOLECULE BOX. THIS WILL ALLOW TO SET THE PARAMETER FOR EVERY OTHER OBJECTS
    var molHeight=Math.abs(molBox.top-molBox.bottom);
    var molWidth=Math.abs(molBox.left-molBox.right);
    var size=Math.max(molHeight,molWidth);
    var top=molBox.bottom;
    var bottom=top-size;
    var left= 1.1*molBox.right;
    var right=left+size;
    var bottom=-5.5;
    var right=23.5;
  	var boxxx=new goog.math.Box(top,right,bottom,left);//CREATE THE SPECTRUM BOX ACCORDING TO THE MOLECULE BOX
  	var atom_coords = goog.array.map(molecule.atoms,function(a) {return a.coord; });//the coords of the file. Simple array
  	var peak_coords = goog.array.map(spectrum.peakList,function(a) {return a.coord;});
  	var box = goog.math.Box.boundingBox.apply(null, atom_coords);
  	var margin = 0.3;//this.config.get("margin");
  	var ex_box = box.expand(margin, margin, margin, margin);
  	var scaleFactor = 0.90; 
  	var widthScaleLimitation = 0.4;
  	var trans = specview.graphics.AffineTransform.buildTransform(ex_box, widthScaleLimitation, editor.graphics, scaleFactor);
  	this.transform=trans;
  	    goog.array.forEach(molecule.atoms,
  	     function(atom){
  	    	var point = trans.transformCoords([ atom.coord ])[0];//point is the coordinates with pixelS
  	    	atom.setPixelCoordinates(point.x, point.y);
  	    //	specview.model.NMRdata.logger.info(point.x+"  ;  "+point.y);
  	    });	
};

/**
 * Build a box for the spectra out of the max coordinates of the molecule.
 * @param downLimit,rightLimit,upperLimit
 * These constant parameter are the limit of the canvas. They should be more flexible. To do so,
 * we should pass the editor as an argument and extract its width and height.
 */ 
specview.model.NMRdata.prototype.getSpectrumBox = function(){	
	var molecule=this.molecule;
	var maxY=0;
	var maxX=0;
	var downLimit=280;var rightLimit=1100;var upperLimit=5;
	goog.array.forEach(molecule.atoms,
	  function(atom){
		if(atom.xPixel>maxX){maxX=atom.xPixel;}
		if(atom.yPixel>maxY){maxY=atom.yPixel;}
	  });
	var box = new goog.math.Box(upperLimit,rightLimit,downLimit,maxX+40);
	var topLeftBox=new goog.math.Coordinate(box.left,box.top);
	var topRightBox=new goog.math.Coordinate(box.right,box.top);
	var bottomRightBox=new goog.math.Coordinate(box.right,box.bottom);
	var bottomLeftBox=new goog.math.Coordinate(box.left,box.bottom);
	return new Array(topLeftBox,topRightBox,bottomLeftBox,bottomRightBox);
};

/**
 * The spectrum coordinates(coordinates of the peaks) are simply calculated on the basis of its spectra box.
 * @param zoomX
 */
specview.model.NMRdata.prototype.setCoordinatesPixelOfSpectrum = function(){
	var spectrum=this.spectrum;
	var minX=spectrum.getMinValue();
	var maxX=spectrum.getMaxValue();
	var maxHeightOfPeak=spectrum.getMaxHeightPeak(); var maxValueOfPeak;  var adjustXvalue; var adjustYvalue;
	var sortedArray=spectrum.sortXvalues();
	var arrayOfPeakSorted=spectrum.mapPeakToxValue(sortedArray);
		maxValueOfPeak=spectrum.getMaxValuePeak();
		var bottomBoxLimit;
		var upperBoxLimit;
		var ecart=this.mainSpecBox[1].x-this.mainSpecBox[0].x;
		var valueToAdd=this.mainSpecBox[0].x;
		goog.array.forEach(spectrum.peakList,
			function(peak) {
			if(peak.intensity==maxHeightOfPeak){
				adjustYvalue=20;
				upperBoxLimit=adjustYvalue-10;
			}else{
				adjustYvalue=20/(peak.intensity/maxHeightOfPeak);
			}
			if(peak.xValue==maxValueOfPeak){
				adjustXvalue=ecart-4;
			}else{
				adjustXvalue=(peak.xValue*(ecart-4))/maxValueOfPeak;
			}
			var whereAllThePeakStartFrom=280;
			peak.isVisible=(adjustXvalue+valueToAdd<this.mainSpecBox[1].x &&
								adjustXvalue+valueToAdd>this.mainSpecBox[0].x) ? true : false;
			peak.setCoordinates(adjustXvalue+valueToAdd,whereAllThePeakStartFrom,adjustXvalue+valueToAdd,adjustYvalue);  
		},
		this);
		spectrum.setExtremePixelValues();
		bottomBoxLimit=280;
};