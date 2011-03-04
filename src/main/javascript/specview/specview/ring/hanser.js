/*
 * Copyright [2010] [Mark Rijnbeek] 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License.
 * 
 * Ring finder classes, a JavaScript->Java conversion using
 * the MX Hanser ring finder classes.
 * For MX Java source see:
 * http://github.com/rapodaca/mx/tree/master/src/com/metamolecular/mx/ring/
 * http://metamolecular.com/mx
 */
goog.provide('specview.ring.Hanser');

goog.require('goog.structs.Set');
goog.require('goog.structs.Set');
goog.require('goog.array');
goog.require('specview.ring.Ring');
goog.require('specview.ring.PathEdge');
goog.require('specview.ring.PathGraph');

/**
 * Hanser ring finder.
 * 
 * For details see: Th. Hanser, Ph. Jauffret, and G. Kaufmann A New Algorithm
 * for Exhaustive Ring Perception in a Molecular Graph J. specview. Inf. Comput.
 * Sci. 1996, 36, 1146-1152
 */

/**
 * Hanser main loop, produces the rings for a given molecule.
 * 
 * @param {specview.model.Molecule} molecule
 * @param {number} maxLen
 * @return {Array.<specview.model.Atom>}
 */
specview.ring.Hanser.findRings = function(molecule, maxLen){

	/** @type {Array.<specview.model.Atom>} */
    var atomOnlyRings = [];
    
    /** @type {specview.ring.PathGraph} */
    var graph = new specview.ring.PathGraph(molecule)

    for (var i = 0,il = molecule.countAtoms(); i < il; i++) {
    	/** @type {Array.<specview.ring.PathEdge>} */
        var edges = graph.remove(molecule.getAtom(i), maxLen);
        for (var j = 0; j < edges.length; j++) {
        	/** @type {specview.ring.PathEdge} */
            var edge = edges[j];
            /** @type {Array.<specview.model.Atom>} */
            var atom_ring = edge.atoms;
            // Hanser last atom is same as first atom, remove it..
            goog.array.removeAt(atom_ring, atom_ring.length - 1);
            for (var k = 0, lk = atom_ring.length; k < lk; k++) {
                atom_ring[k] = molecule.indexOfAtom(atom_ring[k]);
            }
            atomOnlyRings.push(atom_ring);
        }
    }
    // xtra: sort array according to ring size
    goog.array.sort(atomOnlyRings);
    return atomOnlyRings;
}

/**
 * The Hanser Ring Finder produces a ring as just a series of atoms. Here we
 * complete this information with the bonds and the ring center, creating a ring
 * object.
 * 
 * @param {Array.<specview.model.Atom>} atoms
 * @param {specview.model.Molecule}  molecule
 * @return {specview.ring.Ring}
 */
specview.ring.Hanser.createRing = function(atoms,molecule){
	
    var bonds = new Array();
    for (var i = 0, il = atoms.length-1; i < il; i++) {
		bond = molecule.findBond(atoms[i],atoms[i+1]);
		if(bond!=null) {
			bonds.push(bond);
		}
	}
    // Hanser last atom is same as first atom, remove it..
    goog.array.removeAt(atoms, atoms.length - 1);
    
	var ring = new specview.ring.Ring(atoms,bonds);  
    return ring;
}





