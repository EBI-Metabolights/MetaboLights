goog.provide('specview.model.Peak');
goog.require('goog.math.Coordinate');

/*
 * Constructeur of the peak
 */
specview.model.Peak=function(opt_xValue,opt_intensity,opt_peakId,opt_atomRef,opt_multiplicity){
	this.xValue=opt_xValue;
	this.intensity=opt_intensity;
	this.peakId=opt_peakId;
	this.atomRef=opt_atomRef;// Should be an array
	this.multiplicity=opt_multiplicity;
	this.coord=new goog.math.Coordinate(this.xValue,-0.16);
	this.atomMap=new Array();
	this.atomMap[this.peakId]=this.atomRef;// Hence it is an array of array. Ex : ["p1":["a1","a2",...,"ai"]]
	this.xPixel=0;
	this.yPixel=0;
	
	this.xTpixel=0;
	this.yTpixel=0;
	

	this.pixelCoord=null;
	
};
goog.exportSymbol("specview.model.Peak", specview.model.Peak);




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
}

/*
 * Description of the object
 */
specview.model.Peak.prototype.toString = function() {
	return "===Object of type Peak====\n " + "--"+this.xValue + "\n"+"--"+this.intensity+"\n"+"--"+this.peakId+"\n"+"--"+this.atomRef+"\n\n";
};



