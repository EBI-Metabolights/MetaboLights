/**
 * @license Copyright 2010 Paul Novak (paul@wingu.com)
 *                    2011 Mark Rijnbeek (markr@ebi.ac.uk)
 *                    	   and Samy Deghou (deghou@polytech.unice.fr)
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
specview.controller.plugins.Highlight.prototype.HIGHLIGHT_COLOR = '#4E9600';
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
	/*
	 * If the user has clicked in the canvas, it means that he wants to zoom
	 */
	if(specview.controller.plugins.Highlight.zoomObject!=null){
		if(specview.controller.plugins.Highlight.zoomObject.rectangle instanceof goog.math.Rect){
//			this.logger.info("about to clear the rectangle: "+specview.controller.plugins.Highlight.zoomObject.rectangle)
			this.clearSpectrum();
			this.clearZoomRectangle(specview.controller.plugins.Highlight.zoomObject.rectangle);
			this.reDrawSpectrum();
		}
		specview.controller.plugins.Highlight.zoomObject.finalCoordinates = new goog.math.Coordinate(e.clientX,e.clientY)
		specview.controller.plugins.Highlight.zoomObject.rectangle=new goog.math.Rect(
				specview.controller.plugins.Highlight.zoomObject.initialCoordinates.x,
				specview.controller.plugins.Highlight.zoomObject.initialCoordinates.y,
				specview.controller.plugins.Highlight.zoomObject.finalCoordinates.x-specview.controller.plugins.Highlight.zoomObject.initialCoordinates.x,
				specview.controller.plugins.Highlight.zoomObject.finalCoordinates.y-specview.controller.plugins.Highlight.zoomObject.initialCoordinates.y);
//		this.logger.info(specview.controller.plugins.Highlight.zoomObject.rectangle);
		this.drawZoomRectangle(specview.controller.plugins.Highlight.zoomObject.rectangle);
	}
	else{
//		this.logger.info("ready to the highlight");
		var newMoleculeToDisplay;
		if(this.editorObject.findTarget(e)!=undefined) {
			this.editorObject.clearSelected();
			//this.editorObject.getOriginalElement().style.cursor = 'default';
			var target=this.editorObject.findTarget(e);//SAMY. NOT SURE IF THIS IS THE BEST

			var peakTarget=null;
			if (target instanceof specview.model.Atom) {
				if(this.lastAtomHighlighted==null || target!=this.lastAtomHighlighted) {
					if (this.lastT!=null) {
						this.lastT.highlightGroup.clear();
						if(this.lastT.highlightPeak!=undefined){
							this.lastT.highlightPeak.clear();
						}
					}
					this.lastAtomHighlighted=target;
					this.lastT=e.currentTarget;
					this.editorObject.addSelected(target);
					e.currentTarget.highlightGroup = this.highlightAtom(target);
					//LOOKING FOR THE PEAK
					var currentMetaSpecObject=this.editorObject.getSpecObject();//The metaSpec object
					var currentAtomInnerIdentifier= target.innerIdentifier;//the atom Id
					var hypotheticalPeakIdToWhichTheCurrentAtomIsLinked=target.peakMap[currentAtomInnerIdentifier];//its peak Id
//					this.logger.info(hypotheticalPeakIdToWhichTheCurrentAtomIsLinked);
//					this.logger.info("in highlight.hs there is exactle "+specview.util.Utilities.getAssoArrayLength(currentMetaSpecObject.ArrayOfPeaks)+" peaks in ArrayOFPeaks");
					var peakObjectCorrespondingToThePeakId=currentMetaSpecObject.ArrayOfPeaks[hypotheticalPeakIdToWhichTheCurrentAtomIsLinked];//Peak
					
					//HIGHLIGHT THE PEAK PROVIDED IT EXISTS
					if(this.editorObject.peakInfoRenderer.box.height!=undefined){
						this.clearTextInformation(this.editorObject.peakInfoRenderer.box);
					}
					if(peakObjectCorrespondingToThePeakId){
//						alert("haaaaaaaaaaaaaa")
						this.lastPeakHighlighted=peakObjectCorrespondingToThePeakId;
						e.currentTarget.highlightPeak=this.highlightPeak(peakObjectCorrespondingToThePeakId);
						this.drawTextInformation(peakObjectCorrespondingToThePeakId, currentMetaSpecObject)
					}
				}
				return true;
			}
			//Peaks
			//CAREFUL: MIGHT BE multiple ATOMS FOR ONE PEAK
			else if (target instanceof specview.model.Peak){
		//		this.logger.info("@@@@@@@@@");
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
					//LOOKING FOR THE ATOM(S)
					var currentMetaSpecObject=this.editorObject.getSpecObject();
					var currentPeakIdentifier=target.peakId;
					if(currentMetaSpecObject.experienceType=="MS"){
						newMoleculeToDisplay=currentMetaSpecObject.ArrayOfSecondaryMolecules[currentMetaSpecObject.ArrayOfPeaks[currentPeakIdentifier].arrayOfSecondaryMolecules];
					}
					if(currentMetaSpecObject.experienceType=="MS" && newMoleculeToDisplay!=undefined){
						currentMetaSpecObject.molecule=newMoleculeToDisplay;
						currentMetaSpecObject.setCoordinatesPixelOfMolecule(this.editorObject);
						this.drawNewMolecule(currentMetaSpecObject,this.editorObject,target);
						this.editorObject.spectrumRenderer.renderAxis(currentMetaSpecObject,editor.spectrumRenderer.box,'black');
						this.editorObject.spectrumRenderer.renderGrid(editor.specObject.mainSpecBox,'black',spectrumData.spectrum);
					}else if(currentMetaSpecObject.experienceType!="MS"){
						if(this.editorObject.peakInfoRenderer.box.height!=undefined){
							this.clearTextInformation(this.editorObject.peakInfoRenderer.box);
						}
						this.drawTextInformation(target, currentMetaSpecObject)
						var arrayOfAtomToWhichThePeakIsRelated=target.atomMap[target.peakId];//Array of atom identifier: ["a1","a4" ...]
					//	this.logger.info("atom to which the peak is related: "+target.atomMap[target.peakId]);
						//NOW HIGHLIGHT THE CORRESPONDING ATOMS(CAREFUL THERE MIGHT BE MULTIPLE)
						var arrayOfAtomObjectToWhichThePeakIsRelated=new Array();
						for(var k=0;k<arrayOfAtomToWhichThePeakIsRelated.length;k++){
							var atomObject=currentMetaSpecObject.ArrayOfAtoms[arrayOfAtomToWhichThePeakIsRelated[k]];
//							this.logger.info(atomObject);
							arrayOfAtomObjectToWhichThePeakIsRelated.push(atomObject);
						}
						var atomIdentifier=arrayOfAtomToWhichThePeakIsRelated[0];
						var atom=currentMetaSpecObject.ArrayOfAtoms[atomIdentifier];					
						this.lastArrayOfAtomHighlighted=arrayOfAtomObjectToWhichThePeakIsRelated;
						e.currentTarget.highlightGroup=this.highlightSeriesOfAtom(arrayOfAtomObjectToWhichThePeakIsRelated);
					}
				}
			}		
			else 
			if (target instanceof specview.model.Bond) {
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

			/*
			else if (target instanceof specview.model.Molecule) {
				//this.editorObject.getOriginalElement().style.cursor = 'move';
				this.editorObject.addSelected(target);
				e.currentTarget.highlightGroup = this.highlightMolecule(target);
				return true;
			}
			*/
		}
		/*
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

document.onmousedown = function(e){
	if(specview.controller.Controller.isInSpectrum(e,document.metaSpecObject)){
		specview.controller.plugins.Highlight.logger2.info("true");
//		specview.controller.plugins.Highlight.logger2.info("Call to document.onmousedown: zoonObject = "+specview.controller.plugins.Highlight.zoomObject);//
		specview.controller.plugins.Highlight.zoomObject = new specview.controller.plugins.Zoom();
		var initialCoordinates = new goog.math.Coordinate(e.clientX,e.clientY);
		specview.controller.plugins.Highlight.zoomObject.initialCoordinates = initialCoordinates;
//		specview.controller.plugins.Highlight.logger2.info("InitCoord of ZoomObject have been set: "+
//				specview.controller.plugins.Highlight.zoomObject.initialCoordinates);	
	}else{
		specview.controller.plugins.Highlight.logger2.info("false")
	}
};
specview.controller.plugins.Highlight.prototype.handleClick = function(e){
	alert("IN THE PROPER HANDLEMOUSEDOWN OF THE PLUGIN HIGHLIGHT");
};

specview.controller.plugins.Highlight.prototype.handleMouseUp = function(e){
	this.clearZoomRectangle(specview.controller.plugins.Highlight.zoomObject.rectangle, this.editorObject)
	specview.controller.plugins.Highlight.zoomObject = null;
	this.reDrawGrid();
	this.reDrawAxis();
};


	
specview.controller.plugins.Highlight.prototype.reDrawAxis = function(){
	this.editorObject.spectrumRenderer.renderAxis(this.editorObject.specObject,this.editorObject.spectrumRenderer.box,'black');
};

specview.controller.plugins.Highlight.prototype.reDrawGrid = function(){
	this.editorObject.spectrumRenderer.renderGrid(this.editorObject.specObject.mainSpecBox,'black',this.editorObject.specObject.spectrum);
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
//	alert("dawing the rectangle "+rectangle);
	return this.editorObject.spectrumRenderer.drawRectangle(rectangle,this.editorObject);
};

specview.controller.plugins.Highlight.prototype.highlightPeak=function(peak,editor){
//	this.logger.info("in function highlightPeak of the plugin highlight");
	return this.editorObject.spectrumRenderer.highlightOn(peak,editor);
};

specview.controller.plugins.Highlight.prototype.highlightAtom = function(atom) {
//	this.logger.info("in function highlightAtom of the plugin highlight");
	return this.editorObject.moleculeRenderer.atomRenderer.highlightOn(atom,this.HIGHLIGHT_COLOR);
};

specview.controller.plugins.Highlight.prototype.highlightSeriesOfAtom=function(arrayOfAtom){
//	this.logger.info("in function highlightSeriesOfAtom of the plugin highlight");
	return this.editorObject.moleculeRenderer.atomRenderer.highlightOnSeriesOfAtom(arrayOfAtom,this.HIGHLIGHT_COLOR);
};

specview.controller.plugins.Highlight.prototype.highlightBond = function(bond) {
//	this.logger.info("in function highlightBond of the plugin highlight");
	return this.editorObject.moleculeRenderer.bondRendererFactory.get(bond).highlightOn(bond,this.HIGHLIGHT_COLOR);
 
};

specview.controller.plugins.Highlight.prototype.drawNewMolecule = function(currentMetaSpecObject,editor,opt_peak) {
//	this.logger.info("in function drawNewMolecule of the plugin highlight");
	return this.editorObject.setModels([currentMetaSpecObject],opt_peak);
};

specview.controller.plugins.Highlight.prototype.drawTextInformation = function(peak,currentMetaSpecObject){
//	this.logger.info("in function drawTextInformation of the plugin highlight");
	return this.editorObject.renderText(peak,currentMetaSpecObject);
};

specview.controller.plugins.Highlight.prototype.clearTextInformation = function(boxToClearThePeakInformation){
//	this.logger.info("in function clearTextInformationof the plugin highlight");
	return this.editorObject.clearPeakInfo(boxToClearThePeakInformation);
};




//specview.controller.plugins.Highlight.prototype.drawText = function(currentMetatSpecObject,editor){
 //   var stroke = new goog.graphics.Stroke(0.4,opt_color);
//	var fill = new goog.graphics.SolidFill('black');
 //   var font = new goog.graphics.Font(10, 'Times');
//	return this.editorObject.grahics.drawText("ceci est un pic",800,450,600,200,'left',null,font,stroke,fill);
//}


/*
specview.controller.plugins.Highlight.prototype.highlightMolecule = function(
		molecule) {
	 return this.editorObject.moleculeRenderer.highlightOn(
	 molecule, this.HIGHLIGHT_COLOR);
}
*/
/*
specview.controller.plugins.Highlight.prototype.handleMouseDown = function(e) {
	alert("in handlemous down plugin highligh line 240")
	
	if (e.currentTarget.highlightGroup) {
		e.currentTarget.highlightGroup.clear();
	}
	e.currentTarget.highlightGroup = undefined;
	var target = this.editorObject.findTarget(e);
	return false;

};
*/
