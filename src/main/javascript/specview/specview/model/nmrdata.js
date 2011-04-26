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
	
};

specview.model.NMRdata.prototype.toString = function() {
	return "experiment type: "+this.experienceType+"\nnumber of sfragments: "+this.ArrayOfSecondaryMolecules.length;
	
};

specview.model.NMRdata.prototype.logger = goog.debug.Logger.getLogger('specview.model.NMRdata');


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
    this.logger.info("limit of the molecule(molecule_rederer): "+molBox)
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
  	    	var point = trans.transformCoords([ atom.coord ])[0];//point is the coordonates with pixelS
  	    	atom.setPixelCoordinates(point.x, point.y);
  	    });	
  	}
    
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
  
  var xAxisLen=(boxxx.right-boxxx.left)*0.8;
  var correct = xAxisLen/(maxX-minX);
  var rapport=23.5/maxX;
  var xStart= boxxx.left*1.1;    
  var yStart= boxxx.bottom;
    
  var maxHeightOfPeak=spectrum.getMaxHeightPeak();
//  var maxValueOfPeak=spectrum.getMaxValuePeak();
  var maxValueOfPeak;
  var pTo=0;
  var pFrom=0;
  
  var adjustValue;
  var adjustYvalue;
  
  boxxx.right=(boxxx.right<boxxx.left ? 2300 : boxxx.right);//ms case
  
  var boxCoords=trans.transformCoords([new goog.math.Coordinate(boxxx.left,boxxx.top),new goog.math.Coordinate(boxxx.right,boxxx.bottom)]);

  var sortedArray=spectrum.sortXvalues();
//  this.logger.info("before: "+spectrum.getXpixel());
  var arrayOfPeakSorted=spectrum.mapPeakToxValue(sortedArray);
//  var ecart=spectrum.getEcart();
//  var ecart=arrayOfPeakSorted[arrayOfPeakSorted.length-1].xPixel-arrayOfPeakSorted[0].xPixel;
  if(zoomX==0){
	  maxValueOfPeak=spectrum.getMaxValuePeak();
	  goog.array.forEach(spectrum.peakList,
		  function(peak) {
		    var peakFrom =new goog.math.Coordinate(xStart+(peak.xValue*correct), yStart );
		    var peakTo =new goog.math.Coordinate(xStart+(peak.xValue*correct), (boxxx.top+molBox.bottom)*peak.intensity/62); 
		    var peakCoords = trans.transformCoords([peakFrom, peakTo]);
		   
			if(peak.intensity==maxHeightOfPeak){
				  adjustYvalue=boxCoords[0].y-2;
			}else{
				  adjustYvalue=boxCoords[1].y-(peak.intensity/maxHeightOfPeak)*(boxCoords[1].y-boxCoords[0].y);
			}
			
			var peakFrom =new goog.math.Coordinate(xStart+(peak.xValue*correct), yStart );
			var peakTo =new goog.math.Coordinate(xStart+(peak.xValue*correct), adjustYvalue);
			//TODO intensity and multipl etc
			var peakCoords = trans.transformCoords( [peakFrom, peakTo]);
			
			if(peak.xValue==maxValueOfPeak){
				adjustValue=boxCoords[1].x-5;
			}else{
				adjustValue=boxCoords[0].x+(peak.xValue/maxValueOfPeak)*(boxCoords[1].x-boxCoords[0].x);
			}
			peakCoords[0].x=adjustValue;
			peakCoords[1].x=adjustValue;
			peak.setCoordinates(adjustValue,peakCoords[0].y,adjustValue,adjustYvalue);  
		  },
		  this);
	  spectrum.setExtremePixelValues();

  }else if(zoomX>1){
	  var ecart=spectrum.maxXpixel-spectrum.minXpixel;
	  
	  var array=[];
	  var valueToComputeTheRapport=arrayOfPeakSorted[0].xPixel;
	  var newMinXvalue=arrayOfPeakSorted[0].xPixel+(ecart*zoomX/100)
	  arrayOfPeakSorted[0].setCoordinates(newMinXvalue,arrayOfPeakSorted[0].yPixel,newMinXvalue,arrayOfPeakSorted[0].yTpixel);
//	  this.logger.info("at zoom "+zoomX+": "+newMinXvalue);
//	  array.push(newMinXvalue);
	  for(var k=1;k<arrayOfPeakSorted.length;k++){
		  var rapp=arrayOfPeakSorted[k].xPixel/valueToComputeTheRapport;
		  var newXvalue=arrayOfPeakSorted[k].xPixel*rapp;
		  arrayOfPeakSorted[k].setCoordinates(newXvalue,arrayOfPeakSorted[k].yPixel,newXvalue,arrayOfPeakSorted[k].yTpixel);
		  array.push(newXvalue);
	  }
//	  this.logger.info(array)
  }
  
  var maxPeakToDisplay=0;
 
}; 
  
 





