/**  
 * Copyright 2010 Paul Novak (paul@wingu.com) 
 * 			 2011 Samy Deghou (deghou@polytech.unice.fr)
 * 				  and Mark Rijnbeek (markr@ebi.ac.uk)
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
 */

goog.provide('specview.model.Atom');
goog.provide('specview.model.Atom.Hybridizations');
goog.require('specview.model.Flags');
goog.require("specview.resource.ImplicitHydrogens");
goog.require('goog.structs.Set');
goog.require('goog.math.Coordinate');
goog.require('goog.debug.Logger');


/**
 * Class representing an atom
 * 
 * @param {string=}
 *            opt_symbol, Atom symbol, defaults to "C"
 * @param {number=}
 *            opt_x X-coordinate of atom, defaults to 0
 * @param {number=}
 *            opt_y, Y-coordinate of atom, defaults to 0
 * @param {number=}
 *            opt_charge, Charge of atom, defaults to 0.
 * @param {boolean=}
 *            opt_aromatic, whether atom is aromatic, defaults to false
 * @param {number=}
 *            opt_isotope, isotope, defaults to 0
 * @constructor
 */
specview.model.Atom = function(opt_symbol, opt_x, opt_y, opt_charge, opt_aromatic, opt_isotope,opt_peakMapping,opt_innerIdentifier){
	/**
	 * Atom symbol
	 * 
	 * @type {string}
	 */
	this.symbol = goog.isDef(opt_symbol) ? opt_symbol : "C";

	var x = goog.isDef(opt_x) ? opt_x : 0;

	var y = goog.isDef(opt_y) ? opt_y : 0;

	
	/**
	 * Must be an array or an object
	 * Useful for NMR/MSexperiment
	 * Each peak is referrencedbyuann id and is mapped to at  least one atom
	 * This attribute keeps relationships with the spectrum/table object which is associated to the  molecule
	 */
	this.peakMap=goog.isDef(opt_peakMapping) ? opt_peakMapping : new Array();
	this.innerIdentifier= goog.isDef(opt_innerIdentifier) ? opt_innerIdentifier : "";
	
	/**
	 * 2d coordinates
	 * 
	 * @type{goog.math.Coordinate}
	 */
	this.coord = new goog.math.Coordinate(x, y);
	/**
	 * Bonds belonging to this atom
	 * 
	 * @type{goog.structs.Set}
	 */
	this.bonds = new goog.structs.Set();
	/**
	 * charge
	 * 
	 * @type{number}
	 */
	this.charge = goog.isDef(opt_charge) ? opt_charge : 0;

	/**
	 * isotope
	 * 
	 * @type{number}
	 */
	this.isotope = goog.isDef(opt_isotope) ? opt_isotope : 0;

	/**
	 * aromatic
	 * 
	 * @type{boolean}
	 */
	this.aromatic = goog.isDef(opt_aromatic) ? opt_aromatic : false;

	this.hybridization = null;

	/**
	 * Array with property flags (true/false)
	 */
	this.flags = new Array(specview.model.Flags.MAX_FLAG_INDEX + 1);
	
	/**
	 * Coordinates with the pixel
	 */
	
	this.xPixel=0;
	this.yPixel=0;
	
	this.pixelCoordinates=new goog.math.Coordinate(this.xPixel,this.yPixel);	
	
};
goog.exportSymbol("specview.model.Atom", specview.model.Atom);


specview.model.Atom.prototype.setPixelCoordinates=function(xValue,yValue){
	this.xPixel=xValue;
	this.yPixel=yValue;
	this.pixelCoordinates=new goog.math.Coordinate(this.xPixel,this.yPixel);
};

specview.model.Atom.prototype.setPeakMap=function(array){
	this.peakMap=array;
};

specview.model.Atom.prototype.initiatePeakMap=function(){
	this.peakMap=new Array();
};

specview.model.Atom.prototype.setInnerIdentifier=function(string){
	this.innerIdentifier=string;
};

specview.model.Atom.prototype.getInnerIdentifier=function(){
	return this.innerIdentifier;
};


/** @return {string} atomic symbol */
specview.model.Atom.prototype.getSymbol = function() {
	return this.symbol;
};
goog.exportSymbol('specview.model.Atom.prototype.getSymbol', specview.model.Atom.prototype.getSymbol);

/** @return {number} atomic charge */
specview.model.Atom.prototype.getCharge = function() {
	return this.charge;
};
goog.exportSymbol('specview.model.Atom.prototype.getCharge', specview.model.Atom.prototype.getCharge);

specview.model.Atom.prototype.countBonds = function() {
	return this.bonds.getCount();
};
/**
 * Implict hydrogen count
 * 
 * @return {number}
 */
specview.model.Atom.prototype.hydrogenCount = function() {
	var hCount= specview.resource.ImplicitHydrogens[this.symbol+"."+this.valence()+"."+this.charge];
	if (hCount) 
		return hCount;
	else
		return 0;
};
goog.exportSymbol('specview.model.Atom.prototype.hydrogenCount', specview.model.Atom.prototype.hydrogenCount);

/**
 * Get an array with the neighbor atoms.
 * 
 * @return {Array.<specview.model.Atom>}
 */
specview.model.Atom.prototype.getNeighbors = function() {
	var bonds = this.bonds.getValues();
	var nbrs = [];
	for ( var i = 0, li = bonds.length; i < li; i++) {
		nbrs.push(bonds[i].otherAtom(this));
	}
	return nbrs;
};
/**
 * @param {specview.model.Atom}
 *            atom
 * @return {number} the next angle
 */
specview.model.Atom.nextBondAngle = function(atom) {
	var bonds = atom.bonds.getValues();

	var new_angle;

	if (bonds.length == 0) {
		new_angle = 0;

	} else if (bonds.length == 1) {
		var other_atom = bonds[0].otherAtom(atom);
		var existing_angle = goog.math.angle(atom.coord.x, atom.coord.y, other_atom.coord.x, other_atom.coord.y);

		var other_angles_diff = goog.array.map(other_atom.bonds.getValues(), function(b) {
			var not_other = b.otherAtom(other_atom);
			if (not_other != atom) {
				return goog.math.angleDifference(existing_angle, goog.math.angle(other_atom.coord.x,
						other_atom.coord.y, not_other.coord.x, not_other.coord.y));
			}
		});
		goog.array.sort(other_angles_diff);

		var min_angle = other_angles_diff[0];

		if (min_angle > 0) {
			new_angle = existing_angle - 120;
		} else {
			new_angle = existing_angle + 120;
		}
	} else if (bonds.length == 2) {
		var angles = goog.array.map(bonds, function(bond) {
			var other_atom = bond.otherAtom(atom);
			return goog.math.angle(atom.coord.x, atom.coord.y, other_atom.coord.x, other_atom.coord.y);
		});
		var diff = goog.math.angleDifference(angles[0], angles[1]);
		if (Math.abs(diff) < 180) {
			diff = 180 + diff / 2;
		} else {
			diff = diff / 2;
		}
		new_angle = angles[0] + diff;
	} else if (bonds.length == 3) {
		// find two bonds with least number of bonds on other end to insert
		// between
		goog.array.sort(bonds, function(b1, b2) {
			return goog.array.defaultCompare(b1.otherAtom(atom).bonds.getValues().length, b2.otherAtom(atom).bonds
					.getValues().length);
		});
		var insert_between = goog.array.slice(bonds, 0, 2);

		var angles = goog.array.map(insert_between, function(b) {
			var other_atom = b.otherAtom(atom);
			return goog.math.angle(atom.coord.x, atom.coord.y, other_atom.coord.x, other_atom.coord.y);
		});
		new_angle = angles[0] + goog.math.angleDifference(angles[0], angles[1]) / 2;
	}
	return new_angle;
};

/**
 * clones this atom
 * 
 * @return {specview.model.Atom}
 */
specview.model.Atom.prototype.clone = function() {
	return new specview.model.Atom(this.symbol, this.coord.x, this.coord.y, this.charge, this.aromatic, this.isotope);
};

/**
 * Hybridization states
 * 
 * @enum {number}
 */
specview.model.Atom.Hybridizations = {
	S : 0,
	SP1 : 1,    // linear
	SP2 : 2,    // trigonal planar (single pi-electron in pz)
	SP3 : 3,    // tetrahedral
	PLANAR3 : 4,// trigonal planar (lone pair in pz)
	SP3D1 : 5,  // trigonal planar
	SP3D2 : 6,  // octahedral
	SP3D3 : 7,  // pentagonal bipyramid
	SP3D4 : 8,  // square antiprim
	SP3D5 : 9   // tricapped trigonal prism
};

/**
 * Set a flag to be true or false
 * 
 * @param {Object}
 *            flag_type <specview.model.Flags>
 * @param {Object}
 *            flag_value true or false
 */
specview.model.Atom.prototype.setFlag = function(flag_type, flag_value) {
	this.flags[flag_type] = flag_value;
};
/**
 * @return {string}
 */
specview.model.Atom.prototype.toString = function() {
	return "specview.model.Atom [" + this.symbol + "] " + this.coord.toString();
};


/**
 * Get atom valence
 * @return {int}
 */
specview.model.Atom.prototype.valence = function() {
	var valence=0;
    goog.array.forEach(this.bonds.getValues(), function(bond){
        if (bond.order == specview.model.Bond.ORDER.SINGLE)
            valence+=1;
        else if (bond.order == specview.model.Bond.ORDER.DOUBLE)
        	valence+=2;
        else if (bond.order == specview.model.Bond.ORDER.TRIPLE)
	    	valence+=3;
        else if (bond.order == specview.model.Bond.ORDER.QUADRUPLE)
	    	valence+=4;

    });
    return valence;
};


specview.model.Atom.prototype.logger = goog.debug.Logger.getLogger('specview.model.Atom');
