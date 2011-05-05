goog.provide('specview.model.Bond');
goog.require('specview.model.Atom');

/**
 * Base class representing a Bond
 * 
 * @param {specview.model.Atom}
 *            source, Atom at one end of bond.
 * @param {specview.model.Atom}
 *            target, Atom at other end of bond.
 * @param {specview.model.Bond.ORDER=}
 *            opt_order, order of bond
 * 
 * @param {specview.model.Bond.STEREO=}
 *            opt_stereo, stereochemistry of bond
 * 
 * @param {boolean=}
 *            opt_aromatic, true if aromatic
 * @param {specview.model.Molecule=} opt_molecule, parent molecule            
 * 
 * @constructor
 */
specview.model.Bond = function(source, target, opt_order, opt_stereo,
		opt_aromatic, opt_molecule) {
	/**
	 * source Atom
	 * 
	 * @type {specview.model.Atom}
	 */
	this.source = source;
	/**
	 * target Atom
	 * 
	 * @type{specview.model.Atom}
	 */
	this.target = target;

	/**
	 * The bond order.
	 * 
	 * @type {specview.model.Bond.ORDER}
	 */
	this.order = goog.isDef(opt_order) ? opt_order : specview.model.Bond.ORDER.SINGLE;

	/**
	 * Stereochemistry
	 * 
	 * @type {specview.model.Bond.STEREO}
	 */
	this.stereo = goog.isDef(opt_stereo) ? opt_stereo : specview.model.Bond.STEREO.NOT_STEREO;

	/**
	 * Aromatic flag.
	 * 
	 * @type {boolean}
	 */
	this.aromatic = goog.isDef(opt_aromatic) ? opt_aromatic : false;

	/**
	 * parent molecule
	 * 
	 * @type {specview.model.Molecule}
	 */
	this.molecule = goog.isDef(opt_molecule) ? opt_molecule : null;
	
	/**
	 * Set of coordinates of a bond is defined by the coordinates of its source and target atom
	 * @type {array}
	 */
	this.coordinatesArray=null;
	
};
goog.exportSymbol("specview.model.Bond", specview.model.Bond);



specview.model.Bond.prototype.setCoordinatesArray=function(){
	var arrayOfCoordinates=new Array();
	var coordinate=null;
	var coordSource=new goog.math.Coordinate(parseInt(this.source.pixelCoordinates.x),parseInt(this.source.pixelCoordinates.y));
	var coordTarget=new goog.math.Coordinate(parseInt(this.target.pixelCoordinates.x),parseInt(this.target.pixelCoordinates.y));
	var minY=Math.min(coordSource.y,coordTarget.y);
	var maxY=Math.max(coordSource.y,coordTarget.y);
	var minX=Math.min(coordSource.x,coordTarget.x);
	var maxX=Math.max(coordSource.x,coordTarget.x);
	if(coordSource.x==coordTarget.x){
		for(var k=minY+1;k<maxY-1;k+=3){
			var coordinate=new goog.math.Coordinate(coordSource.x,k);
			arrayOfCoordinates.push(coordinate);
		}
	}else if(coordSource.y=coordTarget.y){
		for(var k=minX+1;k<maxX-1;k+=3){
			var coordinate=new goog.math.Coordinate(k,coordSource.y);
			arrayOfCoordinates.push(coordinate);
		}
	}else{
		var distanceX=maxX-minX;
		var distanceY=maxY-minY;
		var maxDistance=Math.max(distanceX,distanceY);
		var minDistance=Math.min(distanceX,distanceY);
		if(minDistance==distanceX){
			var factor=1;
			for(var k=minX+1;k<maxX-1;k+=3){
				var coordinate=new goog.math.Coordinate(k,minY+(3*factor));
				factor++;
			}
		}
	}
	this.coordinatesArray=arrayOfCoordinates;
};

/**
 * Get the atom at the other end of the bond from the subject atom
 * 
 * @param {specview.model.Atom}
 *            atom, the subject atom
 * 
 * @return {specview.model.Atom} The other bond atom or null if the specified atom
 *         is not part of the bond.
 */
specview.model.Bond.prototype.otherAtom = function(atom) {
	if (atom === this.source) {
		return this.target;
	}
	if (atom === this.target) {
		return this.source
	}
	return null;
};

specview.model.Bond.prototype.getLength = function(){
	return goog.math.Coordinate.distance(this.source.coord, this.target.coord);
}

/**
 * clones this bond
 * 
 * @return {specview.model.Bond}
 */
specview.model.Bond.prototype.clone = function() {
	return new specview.model.Bond(this.source, this.target, this.order,
			this.stereo, this.aromatic, this.molecule);
}

specview.model.Bond.prototype.deepClone = function(){
	return new specview.model.Bond(this.source.clone(), this.target.clone(), this.order,
			this.stereo, this.aromatic, this.molecule);
	
}

/**
 * enum for bond order
 * 
 * @enum {number}
 */
specview.model.Bond.ORDER = {
	SINGLE : 1,
	DOUBLE : 2,
	TRIPLE : 3,
	QUADRUPLE : 4
}

/**
 * enum for bond stereochemistry
 * 
 * @enum {number}
 */
specview.model.Bond.STEREO = {
	NOT_STEREO : 10,
	UP : 11,
	UP_OR_DOWN : 12,
	DOWN : 13
}

specview.model.Bond.prototype.toString = function(){
	var molname = this.molecule ? this.molecule.name : "no molecule"
	return "specview.model.Bond[" + 
		this.order + ", " + 
		this.stereo + "]  " + 
		this.source.toString() + " -- " + 
		this.target.toString() + " mol: " + 
		molname;
		
};

