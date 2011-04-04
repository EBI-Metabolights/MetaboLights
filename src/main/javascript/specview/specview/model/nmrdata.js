goog.provide('specview.model.NMRdata');

goog.require('specview.model.Molecule');
goog.require('specview.model.Spectrum');
goog.require('specview.view.SpectrumRenderer');

/*
 * A class to capture spectrum data as found in NMRshiftDb.
 */

specview.model.NMRdata=function(){
	
	this.molecule=null;            // A molecule object (empty, or should just contain the name)
	this.spectrum=null;            // A molecule object (empty, or should just contain the name)

	/**
	 * Why in mary this was uncommented ??
	 */
	this.ArrayOfAtoms=new Array(); // Keys are the inner atom id. Values are the atom objects
	this.ArrayOfBonds=new Array();
	this.ArrayOfPeaks=new Array();
	
	this.editor=null;
};

specview.model.NMRdata.prototype.setEditor=function(controllerEditor){
	this.editor=controllerEditor;
};


specview.model.NMRdata.prototype.toString = function() {
	return "=Object of a spectrum==\n\n " + "1-"+this.molecule + "\n"+"4-"+this.spectrum;
};

specview.model.NMRdata.prototype.setCoordinatesWithPixels = function(){
    //Set the coordinates to the pixel
    
	var molecule=this.molecule;
	var spectrum=this.spectrum;
	var editor=this.editor;
	
	/*TO THIS STAGE NOTHING SETMODELS HAS NOT BEEN CALLED. IT WAS AT THIS TIME THAT THE PIXEL COORDINATES WERE SETTLED ACCORDING
	TO THE COORDINATES OF THE  MOLECULES. SO NOW WHAT WE DO IS  THAT AFTER CREATING THE METASPEC OBJECT WE INDEPENDANTLY(FROM THE
	SETMODEL CALL) CREATE THE BOUNDINGBOX WHICH ALLOW TO ALREADY KNOW WHAT WILL BE THE PIXEL COORDINATES OF EACH OBJECT.
	THE BOX.
	*/
	
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
    
    
    //--------------------------------FIRST WE SET THE PIXEL COORDINATES OF THE ATOM----------------------------\\
  	var atom_coords = goog.array.map(molecule.atoms,function(a) {return a.coord; });//the coords of the file. Simple array
  	var peak_coords = goog.array.map(spectrum.peakList,function(a) {return a.coord;});
  	var box = goog.math.Box.boundingBox.apply(null, atom_coords);
  	var margin = 0.3;//this.config.get("margin");
  	var ex_box = box.expand(margin, margin, margin, margin);
  	var scaleFactor = 0.90; 
  	var widthScaleLimitation = 0.4;
  	var trans = specview.graphics.AffineTransform.buildTransform(ex_box, widthScaleLimitation, editor.graphics, scaleFactor);
    
    goog.array.forEach(molecule.atoms,
    		function(atom){
    	var point = trans.transformCoords([ atom.coord ])[0];//point is the coordonates with pixelS
    	atom.setPixelCoordinates(point.x, point.y);
    });
    
    
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
  var yStart= boxxx.top;    
    
  goog.array.forEach(spectrum.peakList,
  function(peak) {
    var peakFrom =new goog.math.Coordinate(xStart+(peak.xValue*correct), yStart );// MARK
    var peakTo =new goog.math.Coordinate(xStart+(peak.xValue*correct), (boxxx.top+molBox.bottom)*peak.intensity/62); 
    var peakCoords = trans.transformCoords([peakFrom, peakTo]);
    peak.setCoordinates(peakCoords[0].x,peakCoords[0].y,peakCoords[1].x,peakCoords[1].y);
  },
  this);
}; 
  
  
  
specview.model.NMRdata.logger = goog.debug.Logger.getLogger('specview.model.NMRdata');



