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
};
goog.exportSymbol("specview.model.Bond", specview.model.Bond);

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
