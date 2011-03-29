goog.provide('specview.model.NMRdata');

goog.require('specview.model.Molecule');
goog.require('specview.model.Spectrum');

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
};



specview.model.NMRdata.prototype.toString = function() {
	return "=Object of a spectrum==\n\n " + "1-"+this.molecule + "\n"+"4-"+this.spectrum;
};


