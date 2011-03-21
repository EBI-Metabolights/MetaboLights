goog.provide('specview.model.Spectrum');


/**
 * Class representing a spectrum
 * 
 * @param {number=} molecule id. The molecule to which the spectrum belongs to
 * @param {string=} which type of experiment(NMR/MS)
 * @param {string=} opt_specNMR which type of NMR
 * @param {string=} opt_specMS which type of MS
 * @PARAM {array=} list of the peaks
 * @constructor
 */

//We suppose there exist a function that extract all the subsequent information from a file.
specview.model.Spectrum=function(optMolecule, optPeaklist)
{
	this.molecule=null;
	this.peakList=new Array();

	this.experiment="";
	this.NMRtype="";
	this.MS="";

	};
goog.exportSymbol("specview.model.Spectrum", specview.model.Spectrum);


/*
 * Description of the object
 */
specview.model.Spectrum.prototype.toString = function() {
	return "=====Object of a spectrum===\n\n " + "1-"+this.molecule + "\n"+"4-"+this.peakList;
};

