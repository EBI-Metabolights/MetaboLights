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
 * Modifiers of the object
 */
specview.model.Spectrum.prototype.setMoleculeRef=function(ref){
	this._moleculeRef=ref;
};
goog.exportSymbol("specview.model.Spectrum.prototype.setMoleculeRef", specview.model.Spectrum.prototype.setMoleculeRef);

specview.model.Spectrum.prototype.setMoleculeId=function(id){
	this._moleculeId=id;
};
goog.exportSymbol("specview.model.Spectrum.prototype.setMoleculeId", specview.model.Spectrum.prototype.setMoleculeId);

specview.model.Spectrum.prototype.setExperiment=function(experiment){
	this._experiment=experiment;
};
goog.exportSymbol("specview.model.Spectrum.prototype.setExperiment", specview.model.Spectrum.prototype.setExperiment);


specview.model.Spectrum.prototype.setNMRtype=function(NMRtype){
	this._opt_NMRtype=NMRtype;
};
goog.exportSymbol("specview.model.Spectrum.prototype.setNMRtype", specview.model.Spectrum.prototype.setNMRtype);

specview.model.Spectrum.setMS=function(MStype){
	this._opt_MS=MStype;
};

specview.model.Spectrum.prototype.setPeakList=function(peaksList){
	this._peaksList=peaksList;
};
goog.exportSymbol("specview.model.Spectrum.prototype.setPeakList", specview.model.Spectrum.prototype.setPeakList);

/*
 * Accessors of the object
 */

specview.model.Spectrum.prototype.getMoleculeRef=function(){
	return this._moleculeRef;
};

specview.model.Spectrum.prototype.getMoleculeId=function(){
	return this._moleculeId;
};

specview.model.Spectrum.prototype.getExperiment=function(){
	return this._experiment;
};;

specview.model.Spectrum.prototype.getNMRtype=function(){
	return this._opt_NMRtype;
};

specview.model.Spectrum.prototype.getMS=function(){
	return this._opt_MS;
};

/*
 * Return an array of objects Peak.
 */
specview.model.Spectrum.prototype.getPeakList=function(){
	return this._peaksList;
};

/*
 * Other functions
 */

specview.model.Spectrum.prototype.getTableContentOfSpectrum=function(){
	var moleculeRef=this.getMoleculeRef();
	var moleculeId=this.getMoleculeId();
	var peakObjects=this.getPeakList();
	var tableStringToFill="\n";
	//	alert("here is the list of the peak objects:\n\n"+peakObjects);
	for(peak in peakObjects){
		var currentPeak=peakObjects[peak];
		var tableStringToFill=tableStringToFill+"\n<tr id=\"tableid0\" onMouseover=\"select(0,document.Spectrum1,document.JcpViewer1); style.backgroundColor='#FF6600';\" onmouseout=\"style.backgroundColor=' white ';>"
		+"<td>"+currentPeak.getXvalue()+"</td>"
		+"<td align=\"center\">"+currentPeak.getAtomRef()+"</td>"
		+"<td nowrap>"+currentPeak.getPeakId()+"</td>"
		+"</tr>"
		+"\n\n";
	}
//	alert(tableStringToFill);
	return tableStringToFill;
};




/*
 * Description of the object
 */
specview.model.Spectrum.prototype.toString = function() {
	return "=====Object of a spectrum===\n\n " + "1-"+this._moleculeRef + "\n"+"2-"+this._moleculeId+"\n"+"3-"+this._experiment+"\n"+"4-"+this._peaksList;
};

