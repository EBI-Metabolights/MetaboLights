/*
 * Copyright (c) 2010 Mark Rijnbeek (markr@ebi.ac.uk)
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
goog.provide('specview.ring.Ring');

goog.require('specview.model.Flags');
goog.require('goog.array');
goog.require('goog.structs.Map');
goog.require('goog.memoize');

/**
 * Creates a new Ring
 * 
 * @param {Array.
 *            <specview.model.Atom>} atoms
 * @param {Array.
 *            <specview.model.Bond>} bonds
 * @constructor
 */
specview.ring.Ring = function(atoms, bonds) {
	/** @type {Array.<specview.model.Atom>} */
	this.atoms = atoms;
	/** @type {Array.<specview.model.Bond>} */
	this.bonds = bonds;

	/**
	 * Array with property flags (true/false)
	 * 
	 * @type {Array.<boolean>}
	 */
	this.flags = new Array(specview.model.Flags.MAX_FLAG_INDEX + 1);

	/**
	 * @type {goog.math.Coordinate}
	 * @private
	 */
	this._center = null;
}

/**
 * Set a flag to be true or false
 * 
 * @param {specview.model.Flags}
 *            flag_type
 * @param {boolean}
 *            flag_value true or false
 */
specview.ring.Ring.prototype.setFlag = function(flag_type, flag_value) {
	this.flags[flag_type] = flag_value;
}

/**
 * get ring center
 * 
 * @return {goog.math.Coordinate}
 */
specview.ring.Ring.prototype.getCenter = function() {

	if (!this._center) {
		var avgX = 0;
		var avgY = 0;
		for ( var j = 0, jl = this.atoms.length; j < jl; j++) {
			avgX += this.atoms[j].coord.x;
			avgY += this.atoms[j].coord.y;
		}
		this._center = new goog.math.Coordinate(avgX / this.atoms.length, avgY
				/ this.atoms.length);
	}
	return this._center;
};

/** 
 * force recalc of ring center
 */
specview.ring.Ring.prototype.resetRingCenter = function(){
	this._center = undefined;
}

specview.ring.Ring.prototype.toString = function(){
	return "specview.ring.Ring " +			
	"\n\t" +
	goog.array.map(this.atoms, function(atom) {
		return " " + atom.toString();
	}, this).join("\n\t")
	+ "\n\t"
	+ goog.array.map(
			this.bonds,
			function(bond) {
				return " " +  bond.toString();
			}, this).join("\n\t") + "\n\t";
}
