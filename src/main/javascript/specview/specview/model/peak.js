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
};
goog.exportSymbol("specview.model.Peak", specview.model.Peak);


/*
 * Description of the object
 */
specview.model.Peak.prototype.toString = function() {
	return "===Object of type Peak====\n " + "--"+this.xValue + "\n"+"--"+this.intensity+"\n"+"--"+this.peakId+"\n"+"--"+this.atomRef+"\n\n";
};

