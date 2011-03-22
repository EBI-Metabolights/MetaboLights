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
}
goog.inherits(specview.controller.plugins.Highlight,specview.controller.Plugin);
goog.exportSymbol('specview.controller.plugins.Highlight',specview.controller.plugins.Highlight);
specview.controller.plugins.Highlight.prototype.getTrogClassId = goog.functions.constant('highlight');
specview.controller.plugins.Highlight.prototype.HIGHLIGHT_COLOR = '#4E9600';
specview.controller.plugins.Highlight.prototype.logger = goog.debug.Logger.getLogger('specview.controller.plugins.Highlight');

/*
 * These lastXX variables keep track of the last object highlighted. 
 * This is done for performance: IE (8) is sluggish at updating
 * the graphics, so re-highlighting is prevented. 
 */
specview.controller.plugins.Highlight.prototype.lastAtomHighlighted=null;
specview.controller.plugins.Highlight.prototype.lastBondHighlighted=null;
specview.controller.plugins.Highlight.prototype.lastT=null;


specview.controller.plugins.Highlight.prototype.handleMouseMove = function(e) {
	

	if(this.editorObject.findTarget(e)) {
		this.editorObject.clearSelected();
		//this.editorObject.getOriginalElement().style.cursor = 'default';
		var target = this.editorObject.findTarget(e);
		if (target instanceof specview.model.Atom) {
			if(this.lastAtomHighlighted==null || target!=this.lastAtomHighlighted) {
				//this.logger.info("higlight atom")
				if (this.lastT!=null) {
					this.lastT.highlightGroup.clear();
				}
				this.lastAtomHighlighted=target;
				this.lastT=e.currentTarget;
				this.editorObject.addSelected(target);
				e.currentTarget.highlightGroup = this.highlightAtom(target);
			}
			return true;
		}
		
		
		else 
		if (target instanceof specview.model.Bond) {
			if(this.lastBondHighlighted==null || target!=this.lastBondHighlighted) {
				//this.logger.info("higlight bond")
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
//			alert("going to clear");
			this.lastT.highlightGroup.clear();
			this.lastAtomHighlighted=null;
			this.lastBondHighlighted=null;
		}
	}

	return false;
}


specview.controller.plugins.Highlight.prototype.highlightAtom = function(atom) {
//	alert("blablabla: "+this.editorObject);
	return this.editorObject.moleculeRenderer.atomRenderer.highlightOn(atom,this.HIGHLIGHT_COLOR);
};

specview.controller.plugins.Highlight.prototype.highlightBond = function(bond) {

	return this.editorObject.moleculeRenderer.bondRendererFactory.get(bond)
			.highlightOn(bond,this.HIGHLIGHT_COLOR);
 
};


/*
specview.controller.plugins.Highlight.prototype.highlightMolecule = function(
		molecule) {
	 return this.editorObject.moleculeRenderer.highlightOn(
	 molecule, this.HIGHLIGHT_COLOR);
}

specview.controller.plugins.Highlight.prototype.handleMouseDown = function(e) {
	if (e.currentTarget.highlightGroup) {
		e.currentTarget.highlightGroup.clear();
	}
	e.currentTarget.highlightGroup = undefined;
	var target = this.editorObject.findTarget(e);
	return false;

};
*/
