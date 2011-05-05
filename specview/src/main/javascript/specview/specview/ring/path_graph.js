/** 
 * Copyright 2010 Paul Novak (paul@wingu.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 * limitations under the License.
 */
goog.provide('specview.ring.PathGraph');
/**
 * @param {specview.model.Molecule}
 *            molecule
 * @constructor
 */
specview.ring.PathGraph = function(molecule) {

	/** @type{Array.<specview.ring.PathEdge>} */
	this.edges = new Array();

	/** @type{Array.<specview.model.Atom>} */
	this.atoms = new Array();

	// load edges
	for ( var i = 0, il = molecule.countBonds(); i < il; i++) {
		bond = molecule.getBond(i);
		var edge = [ bond.source, bond.target ];
		this.edges.push(new specview.ring.PathEdge(edge));
	}
	// load atoms
	for ( var i = 0, il = molecule.countAtoms(); i < il; i++) {
		this.atoms.push(molecule.getAtom(i));
	}
}

/**
 * @param {specview.model.Atom} atom
 * @param {number} maxLen
 * @return {Array.<specview.ring.PathEdge>}
 */
specview.ring.PathGraph.prototype.remove = function(atom, maxLen) {
	/** @type {Array.<specview.ring.PathEdge>} */
	var oldEdges = this.getEdges(atom);
	/** @type {Array.<specview.ring.PathEdge>} */
	var result = new Array();
	for ( var i = 0, il = oldEdges.length; i < il; i++) {
		if (oldEdges[i].isCycle()) {
			result.push(oldEdges[i]);
		}
	}

	for ( var i = 0, il = result.length; i < il; i++) {
		if (goog.array.contains(oldEdges, result[i])) {
			goog.array.remove(oldEdges, result[i]);
		}
		if (goog.array.contains(this.edges, result[i])) {
			goog.array.remove(this.edges, result[i]);
		}
	}

	/** @type {Array.<specview.ring.PathEdge>} */
	var newEdges = this.spliceEdges(oldEdges);

	for ( var i = 0, il = oldEdges.length; i < il; i++) {
		if (goog.array.contains(this.edges, oldEdges[i])) {
			goog.array.remove(this.edges, oldEdges[i]);
		}
	}

	/*
	 * for (Path newPath : newPaths) { if (maxPathLen == null || newPath.size() <=
	 * (maxPathLen+1)) { paths.add(newPath); } }
	 */

	for ( var i = 0, il = newEdges.length; i < il; i++) {
		if (!goog.array.contains(this.edges, newEdges[i])
				&& (newEdges[i].atoms.length <= maxLen + 1)) {
			this.edges.push(newEdges[i]);

		}
	}
	goog.array.remove(this.atoms, atom);
	return result;
}

/**
 * @param {specview.model.Atom} atom
 * @return {Array.<specview.ring.PathEdge>}
 */
specview.ring.PathGraph.prototype.getEdges = function(atom) {

	/** @type {Array.<specview.ring.PathEdge>} */
	var result = new Array();

	for ( var i = 0, il = this.edges.length; i < il; i++) {

		/** @type {specview.ring.PathEdge} */
		var edge = this.edges[i];

		if (edge.isCycle()) {
			if (goog.array.contains(edge.atoms, atom)) {
				result.push(edge);
			}
		} else {
			var lastAtomPos = edge.atoms.length - 1;
			if ((edge.atoms[0] == atom) || (edge.atoms[lastAtomPos] == atom)) {
				result.push(edge);
			}
		}
	}
	return result;
}

/**
 * @param {Array.<specview.ring.PathEdge>} _edges
 * @return {Array.<specview.ring.PathEdge>}
 */

specview.ring.PathGraph.prototype.spliceEdges = function(_edges) {
	var result = new Array();

	for ( var i = 0, il = _edges.length; i < il; i++) {
		for ( var j = i + 1; j < il; j++) {
			var spliced = _edges[j].splice(_edges[i]);
			if (spliced != null) {
				result.push(spliced);
			}
		}
	}
	return result;
}