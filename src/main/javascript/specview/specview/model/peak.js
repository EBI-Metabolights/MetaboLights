goog.provide('specview.model.Peak');
goog.require('goog.math.Coordinate');

/*
 * Constructeur of the peak
 */
specview.model.Peak=function(opt_xValue,opt_intensity,opt_peakId,opt_atomRef,opt_multiplicity){
	this.xValue=opt_xValue;
	this.intensity=opt_intensity;
	this.peakId=opt_peakId;
	this.atomRef=opt_atomRef;
	this.multiplicity=opt_multiplicity;
};
goog.exportSymbol("specview.model.Peak", specview.model.Peak);

/*
 * Modifiers
 */
specview.model.Peak.prototype.setXvalue=function(xValue){
	this.xValue=xValue;
};
goog.exportSymbol("specview.model.Peak.prototype.setXvalue", specview.model.Peak.prototype.setXvalue);


specview.model.Peak.prototype.setIntensity=function(intensity){
	this.intensity=intensity;
};
goog.exportSymbol("specview.model.Peak.prototype.setIntensity", specview.model.Peak.prototype.setIntensity);

specview.model.Peak.prototype.setPeakId=function(peakId){
	this.peakId=peakId;
};
goog.exportSymbol("specview.model.Peak.prototype.setPeakId", specview.model.Peak.prototype.setPeakId);

specview.model.Peak.prototype.setAtomRef=function(atomRef){
	this.atomRef=atomRef;
};
goog.exportSymbol("specview.model.Peak.prototype.setAtomRef", specview.model.Peak.prototype.setAtomRef);

/*
 * Accessors
 */

specview.model.Peak.prototype.getXvalue=function(){
	return this.xValue;
};

specview.model.Peak.prototype.getIntensity=function(){
	return this.intensity;
};

specview.model.Peak.prototype.getPeakId=function(){
	return this.peakId;
};

specview.model.Peak.prototype.getAtomRef=function(){
	return this.atomRef;
};

/*
 * Description of the object
 */
specview.model.Peak.prototype.toString = function() {
	return "===Object of type Peak====\n " + "--"+this.xValue + "\n"+"--"+this.intensity+"\n"+"--"+this.peakId+"\n"+"--"+this.atomRef+"\n\n";
};

