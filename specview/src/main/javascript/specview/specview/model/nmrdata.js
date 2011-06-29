/**
 * Copyright 2011 Samy Deghou (deghou@polytech.unice.fr)
 * and  Mark Rijnbeek (markr@ebi.ac.uk)
 *  
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 */

goog.provide('specview.model.NMRdata');

goog.require('specview.model.Molecule');
goog.require('specview.model.Spectrum');
goog.require('specview.view.SpectrumRenderer');
goog.require('goog.debug.Logger');

/*
 * A class to capture spectrum data as found in NMRshiftDb. But it is simply better ;-)
 */

specview.model.NMRdata=function(){
	
	/**
	 * Metadata information about the experiment
	 * Object of type TextElement
	 */
	this.metadata = null;
	/**
	 * THe experience type that the file holds, e.g MS or NMR.
	 */
	this.experienceType="";
	/**
	 * The main molecule of the experiment. In case of NMR, it would be the only molecule. In case of MS, it would 
	 * be the reactant molecule.
	 */
	this.molecule=null;
	/**
	 * The only spectrum of the experiment as found in the file
	 */
	this.spectrum=null;
	
	/**
	 * Secondary spectrum. WHen we zoom on a spectrum, this one will give the informations on which part of the spectrum
	 * we are on.
	 */
	this.spectrum=null;
	
	/**
	 * The box of the secondary spectrum. It is an object box to :
	 * {top:..;right:...;bottom:...;left:...}
	 */
	this.secondSpecBox=null;
	
	
	/**
	 * Only available with MS data. Array holding all the fragment molecule of the reactant molecule as found in the file
	 */
	this.secondaryMolecule=null;
	/**
	 * These arrays are useful when dealing with NMR data.IN NMR data, each peak is associated with at least one atom
	 */
	this.ArrayOfAtoms=new Array(); // Keys are the inner atom id. Values are the atom objects
	this.ArrayOfBonds=new Array();
	this.ArrayOfPeaks=new Array();
	this.ArrayOfSecondaryMolecules=new Array();//{m1:molecule1;m2:moleucle2...}
	/**
	 * The box of the original fragment of the MS Experiment. Every other fragment has to be drawn in that box. 
	 * (24, 425),(301, 425),(24, 24),(301, 24)
	 * (left,bottom)(right,bottom)(left,top)(right,top)
	 */
	this.mainMolBox=null;
	/**
	 * The box of the spectrum. Calculated according the the mainMolBox
	 * mainSpecBox is an array of coordinates:
	 * (left,top)(right,top)(left,bottom)(right,bottom)
	 */
	this.mainSpecBox=null;
	/**
	 * transform object.
	 * When an atom is parsed from the file, it contains relative coordinates that usually and preferably are in the 
	 * range [-10;10]. In ordered to be displayed to the screen, these coordinates has to be transformed according to
	 * the parameter of the controller object.
	 * These coordinates are transformed in the method:
	 *  {@see specview.model.nmrData.prototype.setCoordinatesWithPixelOfMolecule}.
	 * There we create a transform object and use it to transform the coordinate,
	 * It is however to keep track of that transform object that we creates, because it is still used in other classes,
	 * for instance in {@see double_bond_renderer.js}. And in order to make sure that we use make the same
	 * transformations (e.g use the same transform object) we shall make an attribute out of it of the object nmrData.
	 */
    this.transform=null;
    
    
    /**
     * Useful
     */
    this.mainMoleculeName;
    
    
    
    /**
     * When the user is zooming by dragging a rectangle over the spectrum, a new rectangle appear in the secondary spectrum
     * to help him localise the focused area of the spectrum in the whole one.
     * This new rectangle drawn on the secondary spectrum needs to be registered. As if we do a second zoom, another
     * new recatngle will be drawn and thus, it is helpful to have it registered
     * zoomRectangle = goog.math.Box(top,right,bottom,left)
     */
    this.zoomBox = null;
    
    /**
     * When the user drag a rectangle over a specific are of the molecule, then the corresponding gets highlighted together.
     * This box is the one used to draw the rectangle over the molecule
     * goog.math.Box(top,right,left,bottom)
     */
    this.moleculeZoomBox = null;
    
    
    /**
     * Test attribute
     */
    
    this.molBoxBox;
    this.specBoxBox;
    
    /**
     * When a new experiment is displayed, the metadata infromation if the experiment must be rendered inside the 
     * experimentInformationBox
     */
    this.experimentInformationBox = null;

    /**
     * When the user mouse over a peak or an atom, the information available about the peak must be rendered inside 
     * the peakInformationBox
     */
    this.peakInformationBox = null;
    
    /**
     * In case of MS experiment, it can hold multiple dimension (MS,MS/MS, MS/MS.../MS)
     * This will allow to generate the graph MS_1---MS_2---....---MS_k that will help the user to navigate between
     * the dimensions
     */
    this.dimension=null;
    
    /**
     * In case of multidimentional MS, multiple molecules can be fragmented
     */
    this.ArrayOfPrimaryMolecules = new Array();
    
    /**
     * In case of multimentional MS, their are multiple spectrum
     */
    this.ArrayOfSpectrum = new Array();
    
    /**
     * NOT USED , NOT SURE THIS IS USEFUL
     */
    this.ArrayOfSecondarySpecBox = new Array();
    
    
    /**
     * The box that holds the metadata
     * type goog.math.Rect
     */
    this.informationExperimentBox
    
    /**
     * bool
     * if true, then the whole spectrum box is used to draw the peaks
     */
    this.expand = false;
 
    
    /**
     * Arguments to keep track of the hierarchy of molecule and spectrum for a multi dimensional MS experiment
     */
    this.motherMolecule;
    this.parentMolecule;
    this.childMolecule;
    this.motherSpectrum;
    this.parentSpectrum;
    this.childSpectrum;
    

    
    
    
	
};

specview.model.NMRdata.prototype.toString = function() {
	return "experiment type: "+this.experienceType+"\nnumber of sfragments: "+this.ArrayOfSecondaryMolecules.length;
	
};

specview.model.NMRdata.prototype.logger = goog.debug.Logger.getLogger('specview.model.NMRdata');
specview.model.NMRdata.logger = goog.debug.Logger.getLogger('specview.model.NMRdata');

specview.model.NMRdata.prototype.setEditor=function(controllerEditor){
	this.editor=controllerEditor;
};



/**
 * Setting the zoomRectangle
 * @param left
 * @param width
 * @param metaSpecObject
 */
specview.model.NMRdata.prototype.setZoomBox = function(left,width){
	var percentageLeft=(left-this.mainSpecBox[0].x)/(this.mainSpecBox[1].x-this.mainSpecBox[0].x);
	var percentageRight=((left+width)-this.mainSpecBox[0].x)/(this.mainSpecBox[1].x-this.mainSpecBox[0].x);
	var newLeft = percentageLeft*(this.zoomBox["right"]-this.zoomBox["left"]);
	newLeft = newLeft+this.zoomBox["left"];
	var newRight = percentageRight*(this.zoomBox["right"]-this.zoomBox["left"]);
	newRight = newRight+this.zoomBox["left"];
	this.zoomBox = new goog.math.Box(this.zoomBox["top"],
									 newRight,
									 this.zoomBox["bottom"],
									 newLeft); 
};



/**
 * Set the pixel coordinate of the molecule
 * Create a box or the specturm according to the pixel coordinates of the molecule
 * Set the pixel coordinate of the spectrum according the the spectrum box that has been created
 * @param editorSpectrum
 * @param zoomX
 */
specview.model.NMRdata.prototype.setCoordinatesWithPixels = function(editorSpectrum){
	this.mainMolBox=this.getMoleculeBox(editorSpectrum);
	this.setCoordinatesPixelOfMolecule(editorSpectrum);
  	
  	this.mainSpecBox=this.getSpectrumBox();
  	this.setCoordinatesPixelOfSpectrum();

  	if(this.dimension > 1){
  		for(k in this.ArrayOfSpectrum){
  			this.setCoordinatesPixelOfAllSpectrum();
//  			alert(this.ArrayOfSpectrum[k].peakList.length)
  		}
  	}
  	
  	this.secondSpecBox=this.getSecondSpectrumBox();
  	this.zoomBox = this.secondSpecBox;
  	this.setCoordinatesPixelOfSecondSpectrum();
	 
	this.molBoxBox = new goog.math.Box(this.mainMolBox[0].y,this.mainMolBox[0].x,this.mainMolBox[0].y,this.mainMolBox[0].x);
	this.specBoxBox = new goog.math.Box(this.mainSpecBox[0].y,this.mainSpecBox[1].x,this.mainSpecBox[2].y,this.mainSpecBox[0].x)
	
	
  	this.informationExperimentBox = new goog.math.Rect(parseInt(specview.util.Utilities.parsePixel(document.getElementById("fieldSet").style.width))/2,
  													   this.mainMolBox[3].y,
  													   200,
  													   this.mainSpecBox[1].y-20);
	

}; 


specview.model.NMRdata.prototype.reSetCoordinatesOfMolecule = function(){
  	var centerOfMolecule = this.getMoleculeCenter();
  	var xTransfer = parseInt(specview.util.Utilities.parsePixel(document.getElementById("fieldSet").style.width))/2 - centerOfMolecule.x;
	goog.array.forEach(this.molecule.atoms,function(atom){
		atom.xPixel += xTransfer
	});
	goog.array.forEach(this.molecule.bonds,function(bond){
		bond.source.xPixel += xTransfer;
		bond.target.xPixel += xTransfer;
	});
}

/**
 * Build a box for the molecule.
 * We first need to get a box of relative coordinates out of the relative coordinates of the molecule(coordinates found
 * in the file). That is Step 1.
 * Then, we need to transform these coordinates into pixel coordinates in order to render them. To do so, we need the 
 * object transform. That is Step 2
 * 
 */ 
specview.model.NMRdata.prototype.getMoleculeBox = function(editorSpectrum,opt_molecule){
   	/*
	 * Step 1
	 */
	var molecule=opt_molecule == undefined ? this.molecule : opt_molecule;
	var box=molecule.getBoundingBox();
    var boxTopLeftCoord =new goog.math.Coordinate(box.left,box.top);
    var boxTopRightCoord =new goog.math.Coordinate(box.right,box.top);
    var boxBotLeftCoord =new goog.math.Coordinate(box.left,box.bottom);
    var boxBotRightCoord =new goog.math.Coordinate(box.right,box.bottom);
    boxTopRightCoord=(boxTopRightCoord.x<boxTopLeftCoord.x ? new goog.math.Coordinate(1200,box.top) : boxTopRightCoord);
    boxBotRightCoord=(boxBotRightCoord.x<boxBotLeftCoord.x ? new goog.math.Coordinate(1200,box.bottom) : boxBotRightCoord);
	/*
	 * Step 2
	 */    
	var atom_coords=goog.array.map(molecule.atoms,function(a) {return a.coord; });//the coords of the file. Simple array
	var relative_box=goog.math.Box.boundingBox.apply(null, atom_coords);
  	var scaleFactor = 0.90; 
  	var widthScaleLimitation = 0.4;
  	var margin = 0.3;
	var editor=editorSpectrum;
	var ex_box=relative_box.expand(margin,margin,margin,margin);
	editorSpectrum.staticTransform = specview.graphics.AffineTransform.buildTransform(ex_box,widthScaleLimitation,editorSpectrum.staticGraphics,scaleFactor)
//	var transform = specview.graphics.AffineTransform.buildTransform(ex_box,widthScaleLimitation,document.staticGraphics,scaleFactor)
	var transform = specview.graphics.AffineTransform.buildTransform(ex_box,widthScaleLimitation,editorSpectrum.graphics,scaleFactor);
//	alert(editoSpectrum.staticGraphics)
    return editorSpectrum.staticTransform.transformCoords( [boxTopLeftCoord,boxTopRightCoord,boxBotLeftCoord,boxBotRightCoord]);
};



/**
 * Set the pixel coordinates of the molecule.
 * Use the relative coordinate of the molecule found in the file.
 * Use the transform object of the google closure library. This object is an attribute of the editor(the controller)
 * and is dependant on the canvas created. Hence, this function must have an editor as an argument to extract the transform object
 * that will transform the relative coordinates into pixel coordinates.
 * @param editorSpectrum
 * @param zoomX
 */
specview.model.NMRdata.prototype.setCoordinatesPixelOfMolecule = function(editorSpectrum,opt_molecule){  
//	alert(this.spectrum)
	var molecule= opt_molecule == undefined  ? this.molecule : opt_molecule;	
	var spectrum= opt_molecule == undefined  ? this.spectrum : this.ArrayOfSpectrum[opt_molecule.name];	
	var editor=editorSpectrum;
    var molBox = molecule.getBoundingBox();//CREATE THE MOLECULE BOX. THIS WILL ALLOW TO SET THE PARAMETER FOR EVERY OTHER OBJECTS
    var molHeight=Math.abs(molBox.top-molBox.bottom);
    var molWidth=Math.abs(molBox.left-molBox.right);
    var size=Math.max(molHeight,molWidth);
    var top=molBox.bottom;
    var bottom=top-size;
    var left= 1.1*molBox.right;
    var right=left+size;
    var bottom=-5.5;
    var right=23.5;
  	var boxxx=new goog.math.Box(top,right,bottom,left);//CREATE THE SPECTRUM BOX ACCORDING TO THE MOLECULE BOX
  	var atom_coords = goog.array.map(molecule.atoms,function(a) {return a.coord; });//the coords of the file. Simple array
  	var peak_coords = goog.array.map(spectrum.peakList,function(a) {return a.coord;});
  	var box = goog.math.Box.boundingBox.apply(null, atom_coords);
  	var margin = 0.3;//this.config.get("margin");
  	var ex_box = box.expand(margin, margin, margin, margin);
  	var scaleFactor = 0.90; 
  	var widthScaleLimitation = 0.4;
  	var trans = specview.graphics.AffineTransform.buildTransform(ex_box, widthScaleLimitation, editor.graphics, scaleFactor);
  	this.transform=trans;
  	var SecondTransformToHaveFixCoordinatesRegardlessOfTheSizeOfTheCanvas = null;
// 	alert(editorSpectrum.staticTransform)
  	var centerOfMolecule = this.getMoleculeCenter();
 //	alert(specview.util.Utilities.parsePixel(document.getElementById("fieldSet").style.width))
  	    goog.array.forEach(molecule.atoms,
  	     function(atom){
  	    	var point = editorSpectrum.staticTransform.transformCoords([ atom.coord ])[0]
//  	    	alert(point);
//  	    	this.logger.info("before: "+point.x);
//  	    	var c= point.x + parseInt(specview.util.Utilities.parsePixel(document.getElementById("fieldSet").style.width))/2 - centerOfMolecule.x;
//  	    	point = new goog.math.Coordinate(c,point.y);
//  	    	this.logger.info("after: "+point.x);
//  	    	alert(point)
  	    	atom.setPixelCoordinates(point.x, point.y);
  	    });	
};



/**
 * Build a box for the spectra out of the max coordinates of the molecule.
 * @param downLimit,rightLimit,upperLimit
 * These constant parameter are the limit of the canvas. They should be more flexible. To do so,
 * we should pass the editor as an argument and extract its width and height.
 */ 
specview.model.NMRdata.prototype.getSpectrumBox = function(){	
	var molecule=this.molecule;
	var maxY=0;
	var maxX=0;
//	alert(document.getElementById("moleculeContainer").style.height)
	var downLimit=parseInt(specview.util.Utilities.parsePixel(document.getElementById("moleculeContainer").style.height))-250;
	var rightLimit=parseInt(specview.util.Utilities.parsePixel(document.getElementById("fieldSet").style.width))-20;
	var upperLimit=this.mainMolBox[0].y+10;
	goog.array.forEach(molecule.atoms,
	  function(atom){
		if(atom.xPixel>maxX){maxX=atom.xPixel;}
		if(atom.yPixel>maxY){maxY=atom.yPixel;}
	  });
	var box = new goog.math.Box(upperLimit,rightLimit,downLimit,this.mainMolBox[0].x);
	var topLeftBox=new goog.math.Coordinate(box.left,box.top);
	var topRightBox=new goog.math.Coordinate(box.right,box.top);
	var bottomRightBox=new goog.math.Coordinate(box.right,box.bottom);
	var bottomLeftBox=new goog.math.Coordinate(box.left,box.bottom);
	return new Array(topLeftBox,topRightBox,bottomLeftBox,bottomRightBox);
};


specview.model.NMRdata.prototype.getSecondSpectrumBox = function(){
	var downLimit=parseInt(specview.util.Utilities.parsePixel(document.getElementById("moleculeContainer").style.height))-10;
	var rightLimit=parseInt(specview.util.Utilities.parsePixel(document.getElementById("fieldSet").style.width))-20;
	var upperLimit = downLimit-130; var leftLimit = this.mainSpecBox[0].x;
	var box = new goog.math.Box(upperLimit,rightLimit,downLimit,leftLimit);
	return box;
}

/**
 * The spectrum coordinates(coordinates of the peaks) are simply calculated on the basis of its spectra box.
 * @param zoomX
 */
specview.model.NMRdata.prototype.setCoordinatesPixelOfSpectrum = function(){

	var spectrum=this.spectrum;
	var minX=this.spectrum.getMinValue();
	var maxX=this.spectrum.getMaxValue();
	var maxHeightOfPeak=this.spectrum.getMaxHeightPeak(); var maxValueOfPeak;  var adjustXvalue; var adjustYvalue;
	var sortedArray=this.spectrum.sortXvalues();
	var arrayOfPeakSorted=this.spectrum.mapPeakToxValue(sortedArray);
	var maxValueOfPeak=this.spectrum.getMaxValuePeak();
	var minValueOfPeak=this.spectrum.getMinValuePeak();
	var bottomBoxLimit;
	var upperBoxLimit;
//	this.logger.info(this.mainSpecBox[1].y+";"+this.mainSpecBox[2].y);
	var heightSquare = this.mainSpecBox[2].y-this.mainSpecBox[1].y;
	var ecart=this.mainSpecBox[1].x-this.mainSpecBox[0].x;
	var valueToAdd=this.mainSpecBox[0].x;
	var minPeakStartAt = valueToAdd + 5;
	var LESPEC = this.spectrum.getPeakList();	
//	alert(minValueOfPeak)
	goog.array.forEach(LESPEC,
		function(peak) {
		/*
		 * Compute the yValue
		 */
		if(peak.intensity==maxHeightOfPeak){
			adjustYvalue=this.mainSpecBox[2].y-(0.8*heightSquare);
			upperBoxLimit=adjustYvalue-10;
		}else{
			adjustYvalue = this.mainSpecBox[2].y-(peak.intensity/maxHeightOfPeak)*(this.mainSpecBox[2].y-(this.mainSpecBox[2].y-(0.8*heightSquare)));
		}
		/*
		 * Compute the xValue
		 */
		var whereAllThePeakStartFrom=this.mainSpecBox[2].y;
			switch(this.experienceType){
			case "NMR" :
//				adjustXvalue = (peak.xValue == minValueOfPeak) ? 5 : ecart * ((peak.xValue-minValueOfPeak)/(maxValueOfPeak-minValueOfPeak));
				if(peak.xValue==maxValueOfPeak){
					adjustXvalue=ecart-4;
				}else{
					adjustXvalue=(peak.xValue*(ecart-4))/maxValueOfPeak;
				}

//				alert(peak.xValue)
				peak.isVisible=(adjustXvalue+valueToAdd<this.mainSpecBox[1].x &&
						adjustXvalue+valueToAdd>this.mainSpecBox[0].x) ? true : false;
				peak.setCoordinates(this.mainSpecBox[0].x+ecart-adjustXvalue,
									whereAllThePeakStartFrom,
									this.mainSpecBox[0].x+ecart-adjustXvalue,
									adjustYvalue);
				
				break;
			case "MS" :
//				adjustXvalue = (peak.xValue == minValueOfPeak) ? 5 : ecart * ((peak.xValue-minValueOfPeak)/(maxValueOfPeak-minValueOfPeak));			
				if(peak.xValue==maxValueOfPeak){
					adjustXvalue=ecart-4;
				}else{
					adjustXvalue=(peak.xValue*(ecart-4))/maxValueOfPeak;
				}
//				alert("min: "+minValueOfPeak+"\n\ncurrent: "+peak.xValue
//						+"\n\nrapport"+peak.xValue/maxValueOfPeak
//						+"\n\nmaxValueOfPeak: "+maxValueOfPeak
//						+"\n\nxPixel: "+adjustXvalue
//						+"\n\nvalue to add: "+valueToAdd
//						+"\n\necart: "+ecart)
//				alert(adjustXvalue)
				peak.isVisible=(adjustXvalue+valueToAdd<this.mainSpecBox[1].x &&
						adjustXvalue+valueToAdd>this.mainSpecBox[0].x) ? true : false;
				peak.setCoordinates(adjustXvalue+valueToAdd,
									whereAllThePeakStartFrom,
									adjustXvalue+valueToAdd,
									adjustYvalue);
				break;
			default :
				alert("The experience type is not known. Hence we cannot fix the coordinates of the spectrum");

			}  

		},
		this);
		this.spectrum.setExtremePixelValues();
};


specview.model.NMRdata.prototype.expandSpectrum = function(){

	var spectrum=this.spectrum;
	var minX=this.spectrum.getMinValue();
	var maxX=this.spectrum.getMaxValue();
	var maxHeightOfPeak=this.spectrum.getMaxHeightPeak(); var maxValueOfPeak;  var adjustXvalue; var adjustYvalue;
	var sortedArray=this.spectrum.sortXvalues();
	var arrayOfPeakSorted=this.spectrum.mapPeakToxValue(sortedArray);
	var maxValueOfPeak=this.spectrum.getMaxValuePeak();
	var minValueOfPeak=this.spectrum.getMinValuePeak();
	var bottomBoxLimit;
	var upperBoxLimit;
//	this.logger.info(this.mainSpecBox[1].y+";"+this.mainSpecBox[2].y);
	var heightSquare = this.mainSpecBox[2].y-this.mainSpecBox[1].y;
	var ecart=this.mainSpecBox[1].x-this.mainSpecBox[0].x;
	var valueToAdd=this.mainSpecBox[0].x;
	var minPeakStartAt = valueToAdd + 5;
	var LESPEC = this.spectrum.getPeakList();	
//	alert(minValueOfPeak)
	
	/**
	 * The first spectrum
	 */
	goog.array.forEach(LESPEC,
		function(peak) {
		/*
		 * Compute the yValue
		 */
		if(peak.intensity==maxHeightOfPeak){
			adjustYvalue=this.mainSpecBox[2].y-(0.8*heightSquare);
			upperBoxLimit=adjustYvalue-10;
		}else{
			adjustYvalue = this.mainSpecBox[2].y-(peak.intensity/maxHeightOfPeak)*(this.mainSpecBox[2].y-(this.mainSpecBox[2].y-(0.8*heightSquare)));
		}
		/*
		 * Compute the xValue
		 */
		var whereAllThePeakStartFrom=this.mainSpecBox[2].y;
			switch(this.experienceType){
			case "NMR" :
				adjustXvalue = (peak.xValue == minValueOfPeak) ? 5 : ecart * ((peak.xValue-minValueOfPeak)/(maxValueOfPeak-minValueOfPeak));
				peak.isVisible=(adjustXvalue+valueToAdd<this.mainSpecBox[1].x &&
						adjustXvalue+valueToAdd>this.mainSpecBox[0].x) ? true : false;
				peak.setCoordinates(this.mainSpecBox[0].x+ecart-adjustXvalue,
									whereAllThePeakStartFrom,
									this.mainSpecBox[0].x+ecart-adjustXvalue,
									adjustYvalue);
				
				break;
			case "MS" :
				adjustXvalue = (peak.xValue == minValueOfPeak) ? 5 : ecart * ((peak.xValue-minValueOfPeak)/(maxValueOfPeak-minValueOfPeak));			
				peak.isVisible=(adjustXvalue+valueToAdd<this.mainSpecBox[1].x &&
						adjustXvalue+valueToAdd>this.mainSpecBox[0].x) ? true : false;
				peak.setCoordinates(adjustXvalue+valueToAdd,
									whereAllThePeakStartFrom,
									adjustXvalue+valueToAdd,
									adjustYvalue);
				break;
			default :
				alert("The experience type is not known. Hence we cannot fix the coordinates of the spectrum");

			}  

		},
		this);
		this.spectrum.setExtremePixelValues();
		
		
		
		/**
		 * The second spectrum
		 */
		var heightSquare = this.secondSpecBox["bottom"]-this.secondSpecBox["top"];
		ecart=this.secondSpecBox["right"]-this.secondSpecBox["left"];
		valueToAdd=this.secondSpecBox["left"];
		goog.array.forEach(spectrum.secondpeakList,
			function(peak) {
			/*
			 * yValue
			 */
			if(peak.intensity==maxHeightOfPeak){
				adjustYvalue=this.secondSpecBox["bottom"]-(0.8*heightSquare);
				upperBoxLimit=adjustYvalue-10;
			}else{
				adjustYvalue = this.secondSpecBox["bottom"]-(peak.intensity/maxHeightOfPeak)*(this.secondSpecBox["bottom"]-(this.secondSpecBox["bottom"]-(0.8*heightSquare)));
			}

			/*
			 * xValue
			 */
			
			switch(this.experienceType){
			case "NMR" :
				adjustXvalue = (peak.xValue == minValueOfPeak) ? 5 : ecart * ((peak.xValue-minValueOfPeak)/(maxValueOfPeak-minValueOfPeak));
				var whereAllThePeakStartFrom=this.secondSpecBox["bottom"];
				peak.isVisible=(adjustXvalue+valueToAdd<this.secondSpecBox["right"]&&
								adjustXvalue+valueToAdd>this.secondSpecBox["left"]) ?
								true : false;
				peak.setCoordinates(this.secondSpecBox["left"]+ecart-adjustXvalue,
									whereAllThePeakStartFrom,
									this.secondSpecBox["left"]+ecart-adjustXvalue,
									adjustYvalue);
				break;
			case "MS" :
				adjustXvalue = (peak.xValue == minValueOfPeak) ? 5 : ecart * ((peak.xValue-minValueOfPeak)/(maxValueOfPeak-minValueOfPeak));			

				var whereAllThePeakStartFrom=this.secondSpecBox["bottom"];
				peak.isVisible=(adjustXvalue+valueToAdd<this.secondSpecBox["right"]&&
									adjustXvalue+valueToAdd>this.secondSpecBox["left"]) ? true : false;
				peak.setCoordinates(adjustXvalue+valueToAdd,
									whereAllThePeakStartFrom,
									adjustXvalue+valueToAdd,
									adjustYvalue);
				break;
			default :
				alert("The experience type is not known. Hence we cannot fix the coordinates of the spectrum");
		}  
		},this);
		this.spectrum.setExtremePixelValues();
		
		
		
		
		
};

specview.model.NMRdata.prototype.setCoordinatesPixelOfAllSpectrum = function(){
	
	for(k in this.ArrayOfSpectrum){
		

		var spectrum=this.ArrayOfSpectrum[k];
		var minX=spectrum.getMinValue();
		var maxX=spectrum.getMaxValue();
		var maxHeightOfPeak=spectrum.getMaxHeightPeak(); var maxValueOfPeak;  var adjustXvalue; var adjustYvalue;
		var sortedArray=spectrum.sortXvalues();
		var arrayOfPeakSorted=spectrum.mapPeakToxValue(sortedArray);
			maxValueOfPeak=spectrum.getMaxValuePeak();
			var bottomBoxLimit;
			var upperBoxLimit;
//			this.logger.info(this.mainSpecBox[1].y+";"+this.mainSpecBox[2].y);
			var heightSquare = this.mainSpecBox[2].y-this.mainSpecBox[1].y;
			var ecart=this.mainSpecBox[1].x-this.mainSpecBox[0].x;
			var valueToAdd=this.mainSpecBox[0].x;
			var LESPEC = spectrum.getPeakList();		
			goog.array.forEach(LESPEC,
				function(peak) {
				/*
				 * Compute the yValue
				 */
				if(peak.intensity==maxHeightOfPeak){
					adjustYvalue=this.mainSpecBox[2].y-(0.8*heightSquare);
					upperBoxLimit=adjustYvalue-10;
				}else{
					adjustYvalue = this.mainSpecBox[2].y-(peak.intensity/maxHeightOfPeak)*(this.mainSpecBox[2].y-(this.mainSpecBox[2].y-(0.8*heightSquare)));
				}
				/*
				 * Compute the xValue
				 */
				var whereAllThePeakStartFrom=this.mainSpecBox[2].y;

				switch(this.experienceType){
					case "NMR" :
						if(peak.xValue==maxValueOfPeak){
							adjustXvalue=ecart-4;
						}else{
							adjustXvalue=(peak.xValue*(ecart-4))/maxValueOfPeak;
						}
						peak.isVisible=(adjustXvalue+valueToAdd<this.mainSpecBox[1].x &&
								adjustXvalue+valueToAdd>this.mainSpecBox[0].x) ? true : false;
						peak.setCoordinates(this.mainSpecBox[0].x+ecart-adjustXvalue,
											whereAllThePeakStartFrom,
											this.mainSpecBox[0].x+ecart-adjustXvalue,
											adjustYvalue);
						
						break;
					case "MS" :
						if(peak.xValue==maxValueOfPeak){
							adjustXvalue=ecart-4;
						}else{
							adjustXvalue=(peak.xValue*(ecart-4))/maxValueOfPeak;
						}
						peak.isVisible=(adjustXvalue+valueToAdd<this.mainSpecBox[1].x &&
								adjustXvalue+valueToAdd>this.mainSpecBox[0].x) ? true : false;
						peak.setCoordinates(adjustXvalue+valueToAdd,
											whereAllThePeakStartFrom,
											adjustXvalue+valueToAdd,
											adjustYvalue);
						break;
					default :
						alert("The experience type is not known. Hence we cannot fix the coordinates of the spectrum");
				}  

			},
			this);
			spectrum.setExtremePixelValues();
		
		
	}
}


/**
 * The secondarySpectrum coordinates(coordinates of the peaks) are simply calculated on the basis of its spectra box.
 * @param zoomX
 */
specview.model.NMRdata.prototype.setCoordinatesPixelOfSecondSpectrum = function(){
	var spectrum=this.spectrum;
	var minX=this.spectrum.getMinValue();
	var maxX=this.spectrum.getMaxValue();
	var maxHeightOfPeak=this.spectrum.getMaxHeightPeak(); var maxValueOfPeak;  var adjustXvalue; var adjustYvalue;
	var sortedArray=this.spectrum.sortXvalues();
	var arrayOfPeakSorted=this.spectrum.mapPeakToxValue(sortedArray);
	maxValueOfPeak=this.spectrum.getMaxValuePeak();
	var bottomBoxLimit;
	var upperBoxLimit;
	var heightSquare = this.secondSpecBox["bottom"]-this.secondSpecBox["top"];
	var ecart=this.secondSpecBox["right"]-this.secondSpecBox["left"];
	var valueToAdd=this.secondSpecBox["left"];
	goog.array.forEach(spectrum.secondpeakList,
		function(peak) {
		/*
		 * yValue
		 */
		if(peak.intensity==maxHeightOfPeak){
			adjustYvalue=this.secondSpecBox["bottom"]-(0.8*heightSquare);
			upperBoxLimit=adjustYvalue-10;
		}else{
			adjustYvalue = this.secondSpecBox["bottom"]-(peak.intensity/maxHeightOfPeak)*(this.secondSpecBox["bottom"]-(this.secondSpecBox["bottom"]-(0.8*heightSquare)));
		}

		/*
		 * xValue
		 */
		
		switch(this.experienceType){
		case "NMR" :
			if(peak.xValue==maxValueOfPeak){
				adjustXvalue=ecart-4;
			}else{
				adjustXvalue=(peak.xValue*(ecart-4))/maxValueOfPeak;
			}
			var whereAllThePeakStartFrom=this.secondSpecBox["bottom"];
			peak.isVisible=(adjustXvalue+valueToAdd<this.secondSpecBox["right"]&&
							adjustXvalue+valueToAdd>this.secondSpecBox["left"]) ?
							true : false;
			peak.setCoordinates(this.secondSpecBox["left"]+ecart-adjustXvalue,
								whereAllThePeakStartFrom,
								this.secondSpecBox["left"]+ecart-adjustXvalue,
								adjustYvalue);
			break;
		case "MS" :
			if(peak.xValue==maxValueOfPeak){
				adjustXvalue=ecart-4;
			}else{
				adjustXvalue=(peak.xValue*(ecart-4))/maxValueOfPeak;
			}
			var whereAllThePeakStartFrom=this.secondSpecBox["bottom"];
			peak.isVisible=(adjustXvalue+valueToAdd<this.secondSpecBox["right"]&&
								adjustXvalue+valueToAdd>this.secondSpecBox["left"]) ? true : false;
			peak.setCoordinates(adjustXvalue+valueToAdd,
								whereAllThePeakStartFrom,
								adjustXvalue+valueToAdd,
								adjustYvalue);
			break;
		default :
			alert("The experience type is not known. Hence we cannot fix the coordinates of the spectrum");
	}  
	},this);
	this.spectrum.setExtremePixelValues();
}



specview.model.NMRdata.prototype.translateMolecule = function(xValue){
	goog.array.forEach(this.molecule.atoms,function(atom){
		atom.xPixel += xValue;
	});
	goog.array.forEach(this.molecule.bonds,function(bond){
		bond.source.xPixel += xValue;
		bond.target.xPixel += xValue;
	});
	return this.molecule;
};



/**
 * Reset the coordinates of the molecule box according to a xValue (move the box horizontaly)
 * @param xValue
 * @returns
 */
specview.model.NMRdata.prototype.translateMolBox = function(xValue){
	this.mainMolBox[0].x += xValue;
	this.mainMolBox[1].x += xValue;
	this.mainMolBox[2].x += xValue;
	this.mainMolBox[3].x += xValue;
	return this.mainMolBox;
};


/**
 * Get the center of the molecule in pixel.
 * @returns {goog.math.Coordinate}
 */
specview.model.NMRdata.prototype.getMoleculeCenter = function(){
	var box = this.mainMolBox;
	return new goog.math.Coordinate((this.mainMolBox[0].x+this.mainMolBox[1].x)/2,
									(this.mainMolBox[1].y+this.mainMolBox[2].y)/2);
};
