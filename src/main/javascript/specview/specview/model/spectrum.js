goog.provide('specview.model.Spectrum');

goog.require('specview.model.Molecule');
goog.require('goog.math.Vec2');


/**
 * Class representing a spectrum
 * @constructor
 */

specview.model.Spectrum=function(optMolecule, optPeaklist)
{
        
        if (optMolecule)
            this.molecule=optMolecule;
        else
            this.molecule=null;


        if (optPeaklist)
            this.peakList=optPeaklist;
        else
            this.peakList=new Array();

        
    
	this.experiment="";
	this.NMRtype="";
	this.MS="";

	};
goog.exportSymbol("specview.model.Spectrum", specview.model.Spectrum);



specview.model.Spectrum.prototype.setXvalues=function(maxValue){
	var limitOfEditor=23.5;
	for(var k=0;k<this.peakList.length;k++){
		var peak=this.peakList[k];
		var newXvalueForPeak=(peak.xValue/maxValue)*limitOfEditor;
		peak.setXvalue(newXvalueForPeak);
	}
};


/**
 * returns bounding box of spectrum peaks
 * 
 * @return {goog.math.Box}
 */
specview.model.Spectrum.prototype.getBoundingBox = function() {
	return goog.math.Box.boundingBox.apply(null, goog.array.map(this.peakList,
			function(a) {
				return a.coord;
			}));
};


specview.model.Spectrum.prototype.getMaxPeak=function(){
	var max=0;
	goog.array.forEach(this.peakList,function(peak){
		if(peak.xValue>max){
			max=peak.intensity;
		}
	});
	return max;
}



/*
 * Description of the object
 */
specview.model.Spectrum.prototype.toString = function() {
	return "=====Object of a spectrum===\n\n " + "1-"+this.molecule + "\n"+"4-"+this.peakList;
};

