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
	
	this.maxXpixel=0;
	this.minXpixel=0;

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

/**
 * Return the maximum intensity of all the peaks
 * @returns {Number}
 */
specview.model.Spectrum.prototype.getMaxHeightPeak=function(){
	var max=0;
	goog.array.forEach(this.peakList,function(peak){
		if(peak.intensity>max){
			max=peak.intensity;
		}
	});
	return max;
};


specview.model.Spectrum.prototype.getMaxValuePeak=function(){
	var max=0;
	goog.array.forEach(this.peakList,function(peak){
		if(peak.xValue>max){
			max=peak.xValue;
		}
	});
	return max;
};


specview.model.Spectrum.prototype.getXvalues=function(){
	var array=[];
	goog.array.forEach(this.peakList,function(peak){
		array.push(parseFloat(peak.xValue));
	});
	return array;
};

specview.model.Spectrum.prototype.sortXvalues=function(){
	var array=this.getXvalues();
//	array=array.sort().reverse();
	array=array.sort(function(a,b){return a - b});
	return array;
};

specview.model.Spectrum.prototype.getPeakFromxValue=function(xValue){
	goog.array.forEach(this.peakList,function(peak){
		if(peak.xValue==xValue){
			return peak;
		}
	},this);
	return 0;
};

specview.model.Spectrum.prototype.mapPeakToxValue=function(array){
	var arrayOfPeaks=[];
	for(k in array){
		for(p in this.peakList){
			if(this.peakList[p].xValue==array[k]){
				arrayOfPeaks.push(this.peakList[p]);
			}
		}
	}
	return arrayOfPeaks;
};

specview.model.Spectrum.prototype.getNthMaxValueOfPeak=function(zoom,sortedArrayOfPeak){
	var l=this.peakList.length;
	if(zoom<=l){
		return sortedArrayOfPeak[zoom].xValue;
	}
};

specview.model.Spectrum.prototype.getXpixel=function(){
	var array=[];
	goog.array.forEach(this.peakList,function(peak){
		array.push(peak.xPixel);
	});
	return array;
};

specview.model.Spectrum.prototype.getMaxAndMinXpixelValue=function(){
	var array=new Object();
	var arrayOfPixel=this.getXpixel();
	var max=0;
	var min=1.7976931348623157E+10308;
	for(k in arrayOfPixel){
		
		var value=arrayOfPixel[k];
		max=(arrayOfPixel[k]>max) ? arrayOfPixel[k] : max;
		min=(arrayOfPixel[k]<min) ? arrayOfPixel[k] : min;
	}
	array.maxPixel=max;
	array.minPixel=min;
	return array;
}

specview.model.Spectrum.prototype.setExtremePixelValues=function(){
	var extremeValue=this.getMaxAndMinXpixelValue();
	this.maxXpixel=extremeValue.maxPixel;
	this.minXpixel=extremeValue.minPixel;
}


specview.model.Spectrum.prototype.reScaleCoordinates=function(value,direction){

	goog.array.forEach(this.peakList,function(peak){
		if(direction=="right"){
			peak.xPixel+=10;
			peak.xTpixel+=10;
		}else if(direction=="left"){
			peak.xPixel-=10;
		}
	});
};



/*
 * Description of the object
 */
specview.model.Spectrum.prototype.toString = function() {
	return "=====Object of a spectrum===\n\n " + "1-"+this.molecule + "\n"+"4-"+this.peakList;
};

