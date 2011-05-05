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


specview.model.NMRdata.prototype.setCoordinatesWithPixels = function(editorSpectrum,zoomX){
    //Set the coordinates to the pixel
    
	var molecule=this.molecule;
	var spectrum=this.spectrum;
	var editor=editorSpectrum;
	
	/*
	TO THIS STAGE NO SETMODELS HAS NOT BEEN CALLED. IT WAS AT THIS TIME THAT THE PIXEL COORDINATES WERE SETTLED ACCORDING
	TO THE COORDINATES OF THE  MOLECULES. SO NOW WHAT WE DO IS  THAT AFTER CREATING THE METASPEC OBJECT WE INDEPENDANTLY(FROM THE
	SETMODEL CALL) CREATE THE BOUNDINGBOX WHICH ALLOW TO ALREADY KNOW WHAT WILL BE THE PIXEL COORDINATES OF EACH OBJECT.
	THE BOX.
	*/
	
    var molBox = molecule.getBoundingBox();//CREATE THE MOLECULE BOX. THIS WILL ALLOW TO SET THE PARAMETER FOR EVERY OTHER OBJECTS
    if(this.experienceType=="ms"){
    	this.mainMolBox=molBox;
    }
    
//    this.logger.info("limit of the molecule(nmrdata.js): "+molBox)
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

    
    //--------------------------------FIRST WE SET THE PIXEL COORDINATES OF THE ATOM----------------------------\\
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
  	    goog.array.forEach(molecule.atoms,
  	     function(atom){
  	    	var point = trans.transformCoords([ atom.coord ])[0];//point is the coordinates with pixelS
  	    	atom.setPixelCoordinates(point.x, point.y);
  	    });	
  	}
  	
  	var samyBox=this.getSamyBox();
//  	alert("nmrdata.js\n\nsamybox: \n"+samyBox)
  	this.mainSpecBox=samyBox;
    
    //----------------------------NOW WE SET TE PIXEL COORDINATES OF THE PEAKS------------------------------\\
  var minX=spectrum.peakList[0].xValue;
  var maxX=spectrum.peakList[0].xValue;
  goog.array.forEach(spectrum.peakList,
  function(peak) {
      if (peak.xValue < minX)
          minx=peak.xValue;
      if (peak.xValue > maxX)
          maxX=peak.xValue;
  },
  this);
    
  var maxHeightOfPeak=spectrum.getMaxHeightPeak();
  var maxValueOfPeak;
  var adjustXvalue;
  var adjustYvalue;  
  var sortedArray=spectrum.sortXvalues();
  var arrayOfPeakSorted=spectrum.mapPeakToxValue(sortedArray);

  if(zoomX==0){
	  maxValueOfPeak=spectrum.getMaxValuePeak();
	  var bottomBoxLimit;
	  var upperBoxLimit;
	  var ecart=samyBox[1].x-samyBox[0].x;
	  var valueToAdd=samyBox[0].x;
	  goog.array.forEach(spectrum.peakList,
		  function(peak) {
		    /*
		     * We adapt the height of the peaks according to their relative values
		     */
			if(peak.intensity==maxHeightOfPeak){
				  adjustYvalue=20;
				  upperBoxLimit=adjustYvalue-10;
			}else{
				adjustYvalue=20/(peak.intensity/maxHeightOfPeak);
			}
			/*
			 * We adapt the xValue of the peak according to their relative value
			 */
			 if(peak.xValue==maxValueOfPeak){
				adjustXvalue=ecart-4;
			}else{
				adjustXvalue=(peak.xValue*(ecart-4))/maxValueOfPeak;
			}
			var whereAllThePeakStartFrom=280;
			peak.setCoordinates(adjustXvalue+valueToAdd,whereAllThePeakStartFrom,adjustXvalue+valueToAdd,adjustYvalue);  
			this.logger.info("peak at(nmrdata.js) : "+adjustXvalue);
		  },
		  this);
	  spectrum.setExtremePixelValues();
	  bottomBoxLimit=280;
  }else if(zoomX>1){
	  var ecart=spectrum.maxXpixel-spectrum.minXpixel;
	  var array=[];
	  var valueToComputeTheRapport=arrayOfPeakSorted[0].xPixel;
	  var newMinXvalue=arrayOfPeakSorted[0].xPixel+(ecart*zoomX/100)
	  arrayOfPeakSorted[0].setCoordinates(newMinXvalue,arrayOfPeakSorted[0].yPixel,newMinXvalue,arrayOfPeakSorted[0].yTpixel);
	  for(var k=1;k<arrayOfPeakSorted.length;k++){
		  var rapp=arrayOfPeakSorted[k].xPixel/valueToComputeTheRapport;
		  var newXvalue=arrayOfPeakSorted[k].xPixel*rapp;
		  arrayOfPeakSorted[k].setCoordinates(newXvalue,arrayOfPeakSorted[k].yPixel,newXvalue,arrayOfPeakSorted[k].yTpixel);
		  array.push(newXvalue);
	  }
  }
  var maxPeakToDisplay=0;
 
}; 


/**
 * Build a box for the spectra out of the max cooridnates of the molecule
 */ 
specview.model.NMRdata.prototype.getSamyBox = function(){
	var molecule=this.molecule;
	var maxY=0;
	var maxX=0;
	goog.array.forEach(molecule.atoms,
	  function(atom){
//		specview.model.NMRdata.logger.info(atom.symbol+": "+atom.xPixel+","+atom.yPixel);
		if(atom.xPixel>maxX){maxX=atom.xPixel;}
		if(atom.yPixel>maxY){maxY=atom.yPixel;}
	  });
	var box = new goog.math.Box(5,1100,280,maxX+40);
	var topLeftBox=new goog.math.Coordinate(box.left,box.top);
	var topRightBox=new goog.math.Coordinate(box.right,box.top);
	var bottomRightBox=new goog.math.Coordinate(box.right,box.bottom);
	var bottomLeftBox=new goog.math.Coordinate(box.left,box.bottom);
	return new Array(topLeftBox,topRightBox,bottomLeftBox,bottomRightBox);
	
//	alert(maxX+" "+maxY)
};





