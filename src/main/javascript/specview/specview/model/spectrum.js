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
specview.model.Spectrum=function()
{
	this._moleculeRef="";
	this._moleculeId="";
	this._experiment="";
	this._opt_NMRtype="";
	this._opt_MS="";
	this._peaksList=new Array();
	};
goog.exportSymbol("specview.model.Spectrum", specview.model.Spectrum);

/*
 * Return an array of objects Peak.
 */
specview.model.Spectrum.prototype.getPeakList=function(){
	return this._peaksList;
};


/*
 * Description of the object
 */
specview.model.Spectrum.prototype.toString = function() {
	return "=====Object of a spectrum===\n\n " + "1-"+this._moleculeRef + "\n"+"2-"+this._moleculeId+"\n"+"3-"+this._experiment+"\n"+"4-"+this._peaksList;
};

