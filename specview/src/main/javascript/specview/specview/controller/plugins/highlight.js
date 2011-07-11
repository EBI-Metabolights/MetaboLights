/**
 * @license Copyright 2010 Paul Novak (paul@wingu.com)
 *                    2011 Mark Rijnbeek (markr@ebi.ac.uk)
 *                    	   and Samy Deghou (deghou@polytech.unice.fr)
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License ati
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * @author paul@wingu.com (Paul Novak)
 */

goog.provide('specview.controller.plugins.Highlight');

goog.require('specview.controller.Plugin');
goog.require('goog.debug.Logger');
goog.require('specview.model.Atom');

/**
 * @constructor
 * @extends{specview.controller.Plugin}
 */
specview.controller.plugins.Highlight = function() {
	specview.controller.Plugin.call(this);
};
goog.inherits(specview.controller.plugins.Highlight,specview.controller.Plugin);
goog.exportSymbol('specview.controller.plugins.Highlight',specview.controller.plugins.Highlight);
specview.controller.plugins.Highlight.prototype.getTrogClassId = goog.functions.constant('highlight');
specview.controller.plugins.Highlight.prototype.HIGHLIGHT_COLOR = "orange";
specview.controller.plugins.Highlight.prototype.logger = goog.debug.Logger.getLogger('specview.controller.plugins.Highlight');
specview.controller.plugins.Highlight.logger2 = goog.debug.Logger.getLogger('specview.controller.plugins.Highlight');


specview.controller.plugins.Highlight.zoomObject = null;

/*
 * These lastXX variables keep track of the last object highlighted. 
 * This is done for performance: IE (8) is sluggish at updating
 * the graphics, so re-highlighting is prevented. 
 */
specview.controller.plugins.Highlight.prototype.lastAtomHighlighted=null;
specview.controller.plugins.Highlight.prototype.lastBondHighlighted=null;
specview.controller.plugins.Highlight.prototype.lastPeakHighlighted=null;

specview.controller.plugins.Highlight.prototype.lastArrayOfAtomHighlighted=null;

specview.controller.plugins.Highlight.prototype.lastT=null;

specview.controller.plugins.Highlight.prototype.handleMouseMove = function(e) {	
	
	/**
	 * If the user has MOUSED DOWN in the canvas, it means that he wants to zoom
	 */
	if(specview.controller.plugins.Highlight.zoomObject!=null && document.getElementById("floatingBox").style.display == "none"){
		var isInSpectrum = specview.controller.Controller.isInSpectrum(e, this.editorObject.specObject);
		var isInMolecule = specview.controller.Controller.isInMolecule(e, this.editorObject.specObject);
		if(specview.controller.plugins.Highlight.zoomObject.rectangle instanceof goog.math.Rect){
			if(isInSpectrum || specview.controller.plugins.Highlight.zoomObject.zooming_on_spectrum){
				specview.controller.plugins.Highlight.zoomObject.zooming_on_spectrum = true;
				this.clearSpectrum();
				this.clearZoomRectangle(specview.controller.plugins.Highlight.zoomObject.rectangle);
				this.reDrawSpectrum();	
			}else if(isInMolecule){
				this.clearMolecule();
				this.clearZoomRectangle(specview.controller.plugins.Highlight.zoomObject.rectangle);
				this.reDrawMolecule();
			}
		}
		specview.controller.plugins.Highlight.zoomObject.finalCoordinates = specview.controller.Controller.getMouseCoords(e);
		specview.controller.plugins.Highlight.zoomObject.rectangle = 
			specview.controller.plugins.Zoom.setRectangle(specview.controller.plugins.Highlight.zoomObject.initialCoordinates,
														  specview.controller.plugins.Highlight.zoomObject.finalCoordinates,
														  this.editorObject.specObject.mainSpecBox);
		this.drawZoomRectangle(specview.controller.plugins.Highlight.zoomObject.rectangle);
	}
	
	/**
	 * The highlight shall only be allowed if the user is NOT trying to draw a rectangle.(Efficiency matter)
	 */
	else{
//		alert(e.button)
		var newMoleculeToDisplay;
		/**
		 * If the mouse is in the canvas
		 */
		if(this.editorObject.findTarget(e)!=undefined) {
			this.editorObject.clearSelected();
			/**
			 * Check if the mouse is hovering an object
			 */
			var target=this.editorObject.findTarget(e);
			var peakTarget=null;
			/**
			 * If this is an atom
			 */
//			alert(target)
			if (target instanceof specview.model.Atom) {
				if(this.lastAtomHighlighted==null || target!=this.lastAtomHighlighted) {
					if (this.lastT!=null) {
//						alert(this.lastT.highlightGroup)
						this.lastT.highlightGroup.clear();
						if(this.lastT.highlightPeak!=undefined){
							this.lastT.highlightPeak.clear();
						}
					}
					this.lastAtomHighlighted=target;
					this.lastT=e.currentTarget;
//					alert(e.currentTarget)
					this.editorObject.addSelected(target);
					e.currentTarget.highlightGroup = this.highlightAtom(target);
					/**
					 * After highlighting the atom, we check if the atom is related to a Peak
					 */
					var currentMetaSpecObject=this.editorObject.getSpecObject();//The metaSpec object
					var currentAtomInnerIdentifier= target.innerIdentifier;//the atom Id
					var hypotheticalPeakIdToWhichTheCurrentAtomIsLinked=target.peakMap[currentAtomInnerIdentifier];//its peak Id
					var peakObjectCorrespondingToThePeakId=currentMetaSpecObject.ArrayOfPeaks[hypotheticalPeakIdToWhichTheCurrentAtomIsLinked];//Peak
					/**
					 * If the text information about the peak is written, then we have to delete it to let the place free
					 * for the new peak.
					 */
					if(this.editorObject.peakInfoRenderer.box.height!=undefined){
						this.clearTextInformation(this.editorObject.peakInfoRenderer.box);
					}
					/**
					 * If a related peak exists, then we highlight it.
					 */
					if(peakObjectCorrespondingToThePeakId){
						alert(peakObjectCorrespondingToThePeakId.isVisible)
						this.lastPeakHighlighted=peakObjectCorrespondingToThePeakId;
						e.currentTarget.highlightPeak=this.highlightPeak(peakObjectCorrespondingToThePeakId);
						this.drawTextInformation(peakObjectCorrespondingToThePeakId, currentMetaSpecObject)
					}
				}
				return true;
			}
			/**
			 * If it is a Peak
			 */
			else if (target instanceof specview.model.Peak && specview.controller.Controller.isInSpectrum(e, document.metaSpecObject)){
				var currentMetaSpecObject=this.editorObject.getSpecObject();
				if(this.lastPeakHighlighted==null || target!=this.lastPeakHighlighted){
					if(this.lastT!=null){
						this.lastT.highlightPeak.clear();
						if(this.lastT.highlightGroup!=undefined){
							this.lastT.highlightGroup.clear();
						}
					}
					this.lastPeakHighlighted=target;
					this.lastT=e.currentTarget;
					this.editorObject.addSelected(target);
					e.currentTarget.highlightPeak=this.highlightPeak(target,currentMetaSpecObject.editor);
					/**
					 * After highlighting the peaks, we check if one or multiple atoms is/are related to the peak
					 */
					var currentMetaSpecObject=this.editorObject.getSpecObject();
					var currentPeakIdentifier=target.peakId;
					if(currentMetaSpecObject.experienceType=="MS"){
						newMoleculeToDisplay=currentMetaSpecObject.ArrayOfSecondaryMolecules
											 [currentMetaSpecObject.ArrayOfPeaks[currentPeakIdentifier].
											  arrayOfSecondaryMolecules];
					}
					if(currentMetaSpecObject.experienceType=="MS" && newMoleculeToDisplay!=undefined){
						var arrayOfAtomIds = this.getSerieOfAtoms(newMoleculeToDisplay);
						var arrayOfAtoms = new Array();
						var intersect = specview.util.Utilities.intersect(arrayOfAtomIds,
								this.getSerieOfAtoms(currentMetaSpecObject.ArrayOfSecondaryMolecules[currentMetaSpecObject.mainMoleculeName]))
						if(intersect){
//							alert("caca")
							var arrayOfBonds = newMoleculeToDisplay.bonds;
							for(var k=0;k<arrayOfAtomIds.length;k++){
								var atomObject=currentMetaSpecObject.ArrayOfAtoms[arrayOfAtomIds[k]];
								arrayOfAtoms.push(atomObject);
							}
							e.currentTarget.highlightGroup = this.highlightSubMolecule(arrayOfBonds, arrayOfAtoms)
							if(currentMetaSpecObject.dimension > 1 && target.parentPeak){
								//alert("caca")
								//Set the actual molecule and spectrum as being the parent
								currentMetaSpecObject.parentMolecule = currentMetaSpecObject.molecule;
								currentMetaSpecObject.parentSpectrum = currentMetaSpecObject.spectrum;
								//Set the child molecule and spectrum
								currentMetaSpecObject.childMolecule = newMoleculeToDisplay;
								currentMetaSpecObject.childSpectrum = currentMetaSpecObject.ArrayOfSpectrum[currentMetaSpecObject.ArrayOfSecondaryMolecules[target.arrayOfSecondaryMolecules].name];


								document.ShowContent("floatingBox")
							}
						}else{
							currentMetaSpecObject.molecule=newMoleculeToDisplay;
							currentMetaSpecObject.setCoordinatesPixelOfMolecule(this.editorObject);
							this.drawNewMolecule(currentMetaSpecObject,this.editorObject,target);
							this.clearTextInformation()
							this.drawTextInformation(target, currentMetaSpecObject)
							this.editorObject.spectrumRenderer.renderAxis(currentMetaSpecObject,editor.spectrumRenderer.box,'black');
							this.editorObject.spectrumRenderer.renderGrid(editor.specObject.mainSpecBox,'black',spectrumData.spectrum);	
						}
					}else if(currentMetaSpecObject.experienceType!="MS"){
						var arrayOfAtomToWhichThePeakIsRelated=target.atomMap[target.peakId];//Array of atom identifier: ["a1","a4" ...]
						/**
						 * If the atoms exist, we highlight them.
						 */
						var arrayOfAtomObjectToWhichThePeakIsRelated=new Array();
						for(var k=0;k<arrayOfAtomToWhichThePeakIsRelated.length;k++){
							var atomObject=currentMetaSpecObject.ArrayOfAtoms[arrayOfAtomToWhichThePeakIsRelated[k]];
							arrayOfAtomObjectToWhichThePeakIsRelated.push(atomObject);
						}
						var atomIdentifier=arrayOfAtomToWhichThePeakIsRelated[0];
						var atom=currentMetaSpecObject.ArrayOfAtoms[atomIdentifier];					
						this.lastArrayOfAtomHighlighted=arrayOfAtomObjectToWhichThePeakIsRelated;
						e.currentTarget.highlightGroup=this.highlightSeriesOfAtom(arrayOfAtomObjectToWhichThePeakIsRelated);
						this.clearTextInformation()
						this.drawTextInformation(target, currentMetaSpecObject)
					}
				}
			}
			/**
			 * If this is a bond
			 */
			else if (target instanceof specview.model.Bond) {
				if(this.lastBondHighlighted==null || target!=this.lastBondHighlighted) {
					if (this.lastT!=null) {
						this.lastT.highlightGroup.clear();
					}
					this.lastBondHighlighted=target;
					this.lastT=e.currentTarget;
					this.editorObject.addSelected(target);
					e.currentTarget.highlightGroup = this.highlightBond(target);
				}
				return true;

			}			
		}
		/**
		 * When the mouse is off an atom/bond, then we withdraw the highlight.
		 */
		else { 
			if (this.lastT!=null) {
				if(this.lastT.highlightGroup!=undefined){
					this.lastT.highlightGroup.clear();
				}
				if(this.lastT.highlightPeak!=undefined){
					this.lastT.highlightPeak.clear();
				}

				this.lastAtomHighlighted=null;
				this.lastBondHighlighted=null;
			}
		}

		return false;
	}

};


/**
 * Note :
 *  specview.controller.plugins.Highlight.prototype.handleMouseUp is FUNCTIONAL.
 *  specview.controller.plugins.Highlight.prototype.handleMouseDown is NOT FUNCTIONAL.
 *  Hence, we use document.onmousedown ...
 * @param e
 */


/**
 * When the user click it is to zoom on the spectrum.
 * Hence :
 * 		-We shall only allow to draw a rectangle in the spectrum area
 * 		-Or eventually in the molecule area
 */
document.onmousedown = function(e){
	if(specview.controller.Controller.isInSpectrum(e,document.metaSpecObject)){
		var initialCoordinates = specview.controller.Controller.getMouseCoords(e);
		specview.controller.plugins.Highlight.zoomObject = new specview.controller.plugins.Zoom();
		specview.controller.plugins.Highlight.zoomObject.initialCoordinates = initialCoordinates;
	}else if(specview.controller.Controller.isInMolecule(e,document.metaSpecObject)){
		var initialCoordinates = specview.controller.Controller.getMouseCoords(e);
		if(document.editorObject.neighborList.getObjectFromCoord(e,document.editorObject.specObject) == undefined){
			specview.controller.plugins.Highlight.zoomObject = new specview.controller.plugins.Zoom();
			specview.controller.plugins.Highlight.zoomObject.initialCoordinates = initialCoordinates;
		}
	}else if (specview.controller.Controller.isInSecondarySpectrum(e)){
	}else{
	}
};	


/**
 * When the mouse is up it means that the user has drawn a rectangle.
 * (i)   Get all the peaks that are present in that area
 * (ii)  Set the peakList of the spectrum to this one
 * (iii) Set the coordinatePixel but in the SAME specBox
 * (iv)  Clear the Spectrum
 * (v)   Render everything
 * @param e
 */
specview.controller.plugins.Highlight.prototype.handleMouseUp = function(e){
	var isInSpectrum = specview.controller.Controller.isInSpectrum(e, this.editorObject.specObject);
	var isInMolecule = specview.controller.Controller.isInMolecule(e, this.editorObject.specObject);
	var isZooming = specview.controller.plugins.Highlight.zoomObject == null ? false : true;
	if(!isZooming && this.editorObject.specObject.isDraggingToolSelected){
		document.getElementById("draggingBarSpectrum").style.backgroundColor = "#E49319";
//		alert(document.getElementById("draggingBarSpectrum").style.left)
	}else if(isInMolecule){
		if(specview.controller.plugins.Highlight.zoomObject == null){
			var object = this.editorObject.neighborList.getObjectFromCoord(e,this.editorObject.specObject)
			object.isSelected ? this.unselectObject(object) : this.selectObject(object,e);
			this.editorObject.specObject.selected.push(object);
			document.ShowContent("floatingBoxSelectMultiplePeaks")
		}else{
			var type = "molecule"
				var listOfAtoms = this.getObjects(type,
						specview.controller.plugins.Highlight.zoomObject.rectangle.left,
						specview.controller.plugins.Highlight.zoomObject.rectangle.left+
						specview.controller.plugins.Highlight.zoomObject.rectangle.width);
			for(k in listOfAtoms){
				listOfAtoms[k].isSelected = true;
				this.editorObject.specObject.selected.push(listOfAtoms[k]);
			}
			this.editorObject.moleculeRenderer.clearMolecule(this.editorObject.specObject.mainMolBox,this.editorObject.graphics);
			this.reDrawMolecule();
			specview.controller.plugins.Highlight.zoomObject = null;
			specview.controller.plugins.Highlight.highlightSerieOfPeaks(this.editorObject)
		}
	}else if(specview.controller.plugins.Highlight.zoomObject.rectangle instanceof goog.math.Rect){
		var isInMolecule = specview.controller.Controller.isInMolecule(e, this.editorObject.specObject);
		if(isInSpectrum || specview.controller.plugins.Highlight.zoomObject.zooming_on_spectrum){
			var type = "spectrum";
			var listOfPeaks = this.getObjects(type,
					specview.controller.plugins.Highlight.zoomObject.rectangle.left,
					specview.controller.plugins.Highlight.zoomObject.rectangle.left+
					specview.controller.plugins.Highlight.zoomObject.rectangle.width);
			this.zoomOnSpectrum(listOfPeaks);
	
		}
	}
	/*
	 * It means that the user has MOUSE DOWN and immediately MOUSE UP without drawing a rectangle.
	 * Hence, we must set the zoomObject to null, otherwise the rectangle will be drawn even if the user has MOUSE UP
	 */
	else{
			specview.controller.plugins.Highlight.zoomObject.zooming_on_molecule = false;
			specview.controller.plugins.Highlight.zoomObject.zooming_on_spectrum = false;
			specview.controller.plugins.Highlight.zoomObject = null;	
		}
};




specview.controller.plugins.Highlight.prototype.cacacaca = function(){
	return "cacacaca";
}

/**
 * zooming
 * @param type
 */
specview.controller.plugins.Highlight.prototype.zoomOnSpectrum = function(listOfPeaks){
	var type="spectrum";
	this.editorObject.zoom(listOfPeaks);
//	this.editorObject.setNewSpectrum(listOfPeaks);
	/*
	this.editorObject.specObject.zoomLevel += 1;
	this.editorObject.specObject.zoomMap[this.editorObject.specObject.zoomMap.length] = listOfPeaks ; 
	this.editorObject.specObject.spectrum.peakList = listOfPeaks;
	this.editorObject.specObject.setCoordinatesPixelOfSpectrum();
	this.editorObject.spectrumRenderer.clearSpectrum(this.editorObject.specObject.mainSpecBox,this.editorObject.graphics);
//	this.editorObject.setModels([this.editorObject.specObject]);
	
	this.editorObject.neighborList = this.editorObject.setNeighborListTrue([this.editorObject.specObject]);
	this.editorObject.spectrumRenderer.clearSpectrum(this.editorObject.specObject.mainSpecBox,this.editorObject.graphics);
	this.editorObject.spectrumRenderer.render(this.editorObject.specObject,
								 this.editorObject.specObject.transform,
								 this.editorObject.specObject.mainSpecBox);
	
	
	this.clearZoomRectangle(specview.controller.plugins.Highlight.zoomObject.rectangle, this.editorObject);
	this.mapZoomSpectrum(specview.controller.plugins.Highlight.zoomObject.rectangle.left,
			 specview.controller.plugins.Highlight.zoomObject.rectangle.width);
	this.setDraggingTool(specview.controller.plugins.Highlight.zoomObject.rectangle.left,
			 specview.controller.plugins.Highlight.zoomObject.rectangle.width);
	
	specview.controller.plugins.Highlight.zoomObject = null;
	
	this.reDrawGrid();
	this.reDrawAxis();
	*/
	specview.controller.plugins.Highlight.zoomObject = null;

};


specview.controller.plugins.Highlight.prototype.zoomOnSpectrum2 = function(listOfPeaks){
	this.editorObject = this.editorObject != undefined ? this.editorObject : document.editorObject;
	var type="spectrum"
	this.editorObject.specObject.spectrum.peakList = listOfPeaks;
	this.editorObject.specObject.setCoordinatesPixelOfSpectrum();
	this.editorObject.spectrumRenderer.clearSpectrum(this.editorObject.specObject.mainSpecBox,this.editorObject.graphics);
	this.editorObject.setModels([this.editorObject.specObject]);
	this.reDrawGrid();
	this.reDrawAxis();
};
/**
 * method called when the user click the option unselect everything. HEnce we must unselect every atom
 * @param editorObject
 */
specview.controller.plugins.Highlight.reDrawMolecule = function(editorObject){
	for(k in editorObject.specObject.molecule.atoms){
		editorObject.specObject.molecule.atoms[k].isSelected = false;
	}
	for(k in editorObject.specObject.molecule.bonds){
		editorObject.specObject.molecule.bonds[k].isSelected = false;
	}
	editorObject.moleculeRenderer.render(editorObject.specObject.molecule,
											  editorObject.staticTransform,
											  editorObject.specObject.mainMolBox);
};

specview.controller.plugins.Highlight.prototype.reDrawMolecule = function(){
	this.editorObject.moleculeRenderer.render(this.editorObject.specObject.molecule,
											  this.editorObject.staticTransform,
											  this.editorObject.specObject.mainMolBox);
};

specview.controller.plugins.Highlight.prototype.clearMolecule = function(){
	this.editorObject.moleculeRenderer.clearMolecule(this.editorObject.specObject.mainMolBox,this.editorObject.graphics);
};

specview.controller.plugins.Highlight.prototype.getSerieOfAtoms = function(molecule){
	return molecule.getAtomIds();
}

specview.controller.plugins.Highlight.prototype.mapZoomSpectrum = function(left,width){
	return this.editorObject.mapZoomSpectrum(left,width,this.editorObject);
};

specview.controller.plugins.Highlight.prototype.setDraggingTool = function(left,width){
	return this.editorObject.setDraggingTool(left,width,this.editorObject);
};


specview.controller.plugins.Highlight.prototype.getObjects = function(x1,x2,opt_y1,opt_y2){
	return this.editorObject.neighborList.getObjects(x1,x2,opt_y1,opt_y2);
};
	
specview.controller.plugins.Highlight.prototype.reDrawAxis = function(){
	return this.editorObject.spectrumRenderer.renderAxis(this.editorObject.specObject,this.editorObject.spectrumRenderer.box,'black');
};

specview.controller.plugins.Highlight.prototype.reDrawGrid = function(){
	return this.editorObject.spectrumRenderer.renderGrid(this.editorObject.specObject.mainSpecBox,'black',this.editorObject.specObject.spectrum);
};
	
specview.controller.plugins.Highlight.clearZoomRectangle = function(rectangle,editorObject){
	return specview.view.spectrumRenderer.clearRectangle(rectangle,editorObject);
};

specview.controller.plugins.Highlight.prototype.reDrawHalfSpectrum = function(){
	return this.editorObject.renderHalfSpectrum();
}

specview.controller.plugins.Highlight.prototype.reDrawSpectrum = function(){
	return this.editorObject.renderSpectrum();
};

specview.controller.plugins.Highlight.prototype.reDrawCompleteSpectrum = function(){
	return this.editorObject.renderCompleteSpectrum();
}

specview.controller.plugins.Highlight.prototype.clearSpectrum = function(box){
	return this.editorObject.spectrumRenderer.clearSpectrum(this.editorObject.specObject.mainSpecBox,this.editorObject.graphics);
};

specview.controller.plugins.Highlight.prototype.clearZoomRectangle = function(rectangle){
	return this.editorObject.spectrumRenderer.clearRectangle(rectangle,this.editorObject);
} 

specview.controller.plugins.Highlight.prototype.drawZoomRectangle = function(rectangle){
	return this.editorObject.spectrumRenderer.drawRectangle(rectangle,this.editorObject);
};

specview.controller.plugins.Highlight.prototype.highlightPeak=function(peak,editor){
	return this.editorObject.spectrumRenderer.highlightOn(peak,editor);
};


specview.controller.plugins.Highlight.highlightSerieOfPeaks = function(editor){
//	alert(editor)
	
	var ArrayOfPeaks = new Array();
	//alert(specview.util.Utilities.getAssoArrayLength(editor.specObject.selected))
	for(k in editor.specObject.selected){
//		alert(k)
		var o = editor.specObject.selected[k]
		if(o instanceof specview.model.Atom ){
			var currentAtomInnerIdentifier= o.innerIdentifier;//the atom Id
			var hypotheticalPeakIdToWhichTheCurrentAtomIsLinked=o.peakMap[currentAtomInnerIdentifier];//its peak Id
			var peakObjectCorrespondingToThePeakId=editor.specObject.ArrayOfPeaks[hypotheticalPeakIdToWhichTheCurrentAtomIsLinked];//Peak
			if(peakObjectCorrespondingToThePeakId){
				ArrayOfPeaks.push(peakObjectCorrespondingToThePeakId);
			}			
		}		

	}
		editor.spectrumRenderer.highlightOnSerieOfPeaks(ArrayOfPeaks,editor);
}

specview.controller.plugins.Highlight.prototype.highlightAtom = function(atom) {
	return this.editorObject.moleculeRenderer.atomRenderer.highlightOn(atom,this.HIGHLIGHT_COLOR);
};

specview.controller.plugins.Highlight.prototype.selectObject = function(object,e){
	return (object instanceof specview.model.Atom ? this.editorObject.moleculeRenderer.atomRenderer.selectAtom(object) :
		this.editorObject.moleculeRenderer.bondRendererFactory.get(bond).selectBond(object));
};

specview.controller.plugins.Highlight.prototype.unselectObject = function(object){
	object.isSelected = false;
	this.editorObject.moleculeRenderer.clearMolecule(this.editorObject.specObject.mainMolBox,this.editorObject.graphics)
	this.editorObject.moleculeRenderer.render(this.editorObject.specObject.molecule,this.editorObject.staticTransform,this.editorObject.specObject.mainMolBox)
};


specview.controller.plugins.Highlight.prototype.highlightSeriesOfAtom=function(arrayOfAtom){
	return this.editorObject.moleculeRenderer.atomRenderer.highlightOnSeriesOfAtom(arrayOfAtom,this.HIGHLIGHT_COLOR);
};


specview.controller.plugins.Highlight.prototype.highlightBond = function(bond) {
	return this.editorObject.moleculeRenderer.bondRendererFactory.get(bond).highlightOn(bond,this.HIGHLIGHT_COLOR);
};

specview.controller.plugins.Highlight.prototype.highlightOnSerieOfBonds = function(arrayOfBonds){
	var opt_element_array = new specview.graphics.ElementArray();
	var bond = null;
	for(k in arrayOfBonds){
		bond = arrayOfBonds[k];
		var h = this.editorObject.moleculeRenderer.bondRendererFactory.get(bond).highlightOnSerieOfBonds(bond,this.HIGHLIGHT_COLOR);
		opt_element_array.add(h.pathUp);
		opt_element_array.add(h.pathDown);
	}
	return opt_element_array;
};

specview.controller.plugins.Highlight.prototype.highlightSubMolecule = function(arrayOfBonds, arrayOfAtoms){
	var opt_element_array = new specview.graphics.ElementArray();

	for(k in arrayOfBonds){
		var h = this.editorObject.moleculeRenderer.bondRendererFactory.get(arrayOfBonds[k]).highlightOnSerieOfBonds(arrayOfBonds[k],this.HIGHLIGHT_COLOR);
		opt_element_array.add(h.pathUp);
		opt_element_array.add(h.pathDown);
	}
	
	for(a in arrayOfAtoms){
		var circle = this.editorObject.moleculeRenderer.atomRenderer.highlightOnAtom2(arrayOfAtoms[a]);
		opt_element_array.add(circle);
	}
	
	return opt_element_array;
}


specview.controller.plugins.Highlight.prototype.drawNewMolecule = function(currentMetaSpecObject,editor,opt_peak) {
	return this.editorObject.setModels([currentMetaSpecObject],opt_peak);
};

specview.controller.plugins.Highlight.prototype.drawTextInformation = function(peak,currentMetaSpecObject){
//	alert("renderTextInfo in hightight")
	return this.editorObject.renderText(peak,currentMetaSpecObject);
};

specview.controller.plugins.Highlight.prototype.clearTextInformation = function(){
//	alert("caca")
	return this.editorObject.clearPeakInfo();
};
