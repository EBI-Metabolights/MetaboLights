goog.provide('specview.model.NMRdata');
goog.require('specview.io.mdl');
goog.require('specview.model.Spectrum');
goog.require('specview.util.Utilities');
goog.require('specview.model.Peak');

/*
 * A class to capture spectrum data as found in NMRshiftDb.
 */

specview.model.NMRdata=function(){
	
	this.molecule=null;            // A molecule object (empty, or should just contain the name)
	this.spectrum=null;            // A molecule object (empty, or should just contain the name)

	//this.ArrayOfAtoms=new Array(); // Keys are the inner atom id. Values are the atom objects
	//this.ArrayOfBonds=new Array();
	//this.ArrayOfPeaks=new Array();
};



