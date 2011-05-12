goog.provide('specview.model.NMRdata');

goog.require('specview.model.Molecule');
goog.require('specview.model.Spectrum');
goog.require('specview.view.SpectrumRenderer');
goog.require('goog.debug.Logger');

/*
 * A class to capture spectrum data as found in NMRshiftDb.
 */

specview.model.NMRdata=function(){
	
	this.molecule=null;            // A molecule object (empty, or should just contain the name)
	this.spectrum=null;            // A molecule object (empty, or should just contain the name)

	this.secondaryMolecule=null;
	
	/**
	 * Why in mary this was uncommented ??
	 */
	this.ArrayOfAtoms=new Array(); // Keys are the inner atom id. Values are the atom objects
	this.ArrayOfBonds=new Array();
	this.ArrayOfPeaks=new Array();
	
	/*
	 * TODO Consider the case of mass spectras 
	 * Should include: 
	 * this.experienceType
	 * this.ArrayOfSecondaryMolecules
	 */
	
	this.editor=null;
	
	this.transform;
	
	this.experienceType="";
	this.ArrayOfSecondaryMolecules=new Array();//{m1:molecule1;m2:moleucle2...}
	
	/*
	 * The box of the original fragment of the ms Experiment. Every other fragment has to be drawn is that box. 
	 */
	this.mainMolBox=null;
	this.mainSpecBox=null;
	
	
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
specview.model.NMRdata.prototype.setCoordinatesWithPixels = function(editorSpectrum,zoomX){
	this.setCoordinatesPixelOfMolecule(editorSpectrum,zoomX);
  	this.mainSpecBox=this.getSpectrumBox();
  	this.setCoordinatesPixelOfSpectrum(zoomX);
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
specview.model.NMRdata.prototype.setCoordinatesPixelOfMolecule = function(editorSpectrum,zoomX){    
	var molecule=this.molecule;	var spectrum=this.spectrum;	var editor=editorSpectrum;
    var molBox = molecule.getBoundingBox();//CREATE THE MOLECULE BOX. THIS WILL ALLOW TO SET THE PARAMETER FOR EVERY OTHER OBJECTS
    if(this.experienceType=="ms"){
    	this.mainMolBox=molBox;
    }
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
  	if(!zoomX){
//    	specview.model.NMRdata.logger.info("\n\nmolecule Name: "+molecule.name)
  	    goog.array.forEach(molecule.atoms,
  	     function(atom){
  	    	var point = trans.transformCoords([ atom.coord ])[0];//point is the coordinates with pixelS
  	    	atom.setPixelCoordinates(point.x, point.y);
//  	    	specview.model.NMRdata.logger.info(point.x+","+point.y+"--->"+atom.coord.x+","+atom.coord.y+"--->"+atom.symbol)
  	    });	
  	}
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
specview.model.NMRdata.prototype.setCoordinatesPixelOfSpectrum = function(zoomX){
	var spectrum=this.spectrum;
	var minX=spectrum.getMinValue();
	var maxX=spectrum.getMaxValue();
	var maxHeightOfPeak=spectrum.getMaxHeightPeak(); var maxValueOfPeak;  var adjustXvalue; var adjustYvalue;
	var sortedArray=spectrum.sortXvalues();
	var arrayOfPeakSorted=spectrum.mapPeakToxValue(sortedArray);
	if(zoomX==0){
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
			peak.setCoordinates(adjustXvalue+valueToAdd,whereAllThePeakStartFrom,adjustXvalue+valueToAdd,adjustYvalue);  
//			this.logger.info("peak at(nmrdata.js) : "+adjustXvalue);
		},
		this);
		spectrum.setExtremePixelValues();
		bottomBoxLimit=280;
	}else if(zoomX>1){
		var ecart=spectrum.maxXpixel-spectrum.minXpixel;
		var array=[];
		var valueToComputeTheRapport=arrayOfPeakSorted[0].xPixel;
		var newMinXvalue=arrayOfPeakSorted[0].xPixel+(ecart*zoomX/100);
		arrayOfPeakSorted[0].setCoordinates(newMinXvalue,arrayOfPeakSorted[0].yPixel,newMinXvalue,arrayOfPeakSorted[0].yTpixel);
		for(var k=1;k<arrayOfPeakSorted.length;k++){
			var rapp=arrayOfPeakSorted[k].xPixel/valueToComputeTheRapport;
			var newXvalue=arrayOfPeakSorted[k].xPixel*rapp;
			arrayOfPeakSorted[k].setCoordinates(newXvalue,arrayOfPeakSorted[k].yPixel,newXvalue,arrayOfPeakSorted[k].yTpixel);
			array.push(newXvalue);
		}
	}
	var a=new Array();
	for(k in spectrum.getXpixel()){
		a.push(spectrum.getXpixel()[k]);
	}
	specview.model.NMRdata.logger.info(a);
	
	
};




