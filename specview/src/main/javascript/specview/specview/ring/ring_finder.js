/*
 * Copyright 2010 Tim Vandermeersch
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
goog.provide('specview.ring.RingFinder');

goog.require('specview.ring.Hanser');
goog.require('specview.ring.SSSR');
goog.require('goog.structs.Set');
goog.require('goog.structs.Set');
goog.require('goog.array');
goog.require('specview.ring.Ring');

/**
 * The Hanser Ring Finder produces a ring as just a series of atoms. Here we
 * complete this information with the bonds and the ring center, creating a ring
 * object.
 * 
 * @param {Array.<number>} atomIndexes
 * @param {specview.model.Molecule}
 *            molecule
 * @return {specview.ring.Ring}
 */
specview.ring.RingFinder.createRing = function (atomIndexes, molecule) {
	// Translate atom indexes to atom objects.
	/** @type {Array.<specview.model.Atom>} */
	var atoms = [];
	for (var i = 0, li = atomIndexes.length; i < li; i++) {
		atoms.push(molecule.getAtom(atomIndexes[i]));
	}

	// Add the bonds for the ring.
	/** @type {Array.<specview.model.Bond>} */
	var bonds = [];
	for (var i = 0, il = atoms.length-1; i < il; i++) {
		var bond = molecule.findBond(atoms[i], atoms[i+1]);
		if(bond!=null) {
			bonds.push(bond);
		}
	}
	// Don't forget the bond between first & last atom.
	var bond = molecule.findBond(atoms[0], atoms[atoms.length-1]);
	if(bond!=null) {
		bonds.push(bond);
	}
	// Create the ring.
	return new specview.ring.Ring(atoms,bonds);
};

/**
 * Check if a candidate ring is already in the SSSR ring set.
 * 
 * @param {Array.<specview.ring.Ring>} C
 * @param {Array.<Array.<specview.ring.Ring>>} Csssr
 * @param {Array.<number>} valences
 * @param {Array.<number>} ringCount
 * @return {boolean}
 */
specview.ring.RingFinder.isCandidateInSet = function(C, Csssr, valences, ringCount) {
	for (var i = 0, li = Csssr.length; i < li; i++) {
		var sssr = Csssr[i];
		// the part from the paper
		if (C.length >= sssr.length) {
			var candidateContainsRing = true;
			for (var j = 0, lj = sssr.length; j < lj; j++) {
				if (!goog.array.contains(C, sssr[j])) {
					candidateContainsRing = false;
				}
			}
			if (candidateContainsRing)
				return true;
		}
		// updated part
		for (j = 0, lj = C.length; j < lj; j++) {
			if (goog.array.contains(sssr, C[j])) {
				ringCount[j]++;
			}
		}
	}

	// If the candidate has at least one atom with a ringCount less than the
	// valence minus one, the candidate is a new ring. You can work this out
	// on paper for tetrahedron, cube, ...
	var isNewRing = false;
	for (j = 0, lj = C.length; j < lj; j++) {
		if (ringCount[C[j]] < valences[C[j]] - 1) {
			isNewRing = true;
		}
	}

	if (isNewRing) {
		for (j = 0, lj = C.length; j < lj; j++) {
			ringCount[C[j]]++;
		}
		return false;
	}
	return true;
};


/**
 * Verify if a ring set is the SSSR ring set.
 * 
 * @param {Array.<Array.<number>>} sssr
 * @param {number} nsssr
 * @param {specview.model.Molecule}
 *            molecule
 * @return {Array.<specview.ring.Ring>}
 */
specview.ring.RingFinder.verifySSSR = function(sssr, nsssr, molecule) {
	// The final SSSR set
	var Csssr = [];
	// store the valences for all atoms
	var valences = [];
	for (var i = 0, li = molecule.countAtoms(); i < li; i++) {
		valences.push(molecule.getAtom(i).countBonds());
	}

	var ringCount = goog.array.repeat(0, molecule.countAtoms());
	for (var i = 0, li = sssr.length; i < li; i++) {
		var ring = sssr[i];
		if (!specview.ring.SSSR.isCandidateInSet(ring, Csssr, valences, ringCount)) {
			Csssr.push(ring);
			if (Csssr.length == nsssr) {
				return Csssr;
			}
		}
	}

	return Csssr;
};

specview.ring.RingFinder.detectRingAtoms = function(molecule) {
	var n = molecule.countAtoms();
	if (!n) {
		return;
	}
	var visitedAtoms = goog.array.repeat(false, n);
	var visitedBonds = goog.array.repeat(false, n);

	/** @type{Array.<specview.model.Atom>} */
	var queue = [];

	var startAtom = molecule.atoms[0];
	startAtom.depth = 0;
	queue.push(startAtom);
	// visitedAtoms.push(0);
	visitedAtoms[0] = true;

	while (true) {
		if (!queue.length) {
			break;
		}
		var atom = queue[0];
		goog.array.removeAt(queue, 0);

		var bonds = atom.bonds.getValues();
		for (var i = 0, li = bonds.length; i < li; i++) {
			var bond = bonds[i];
			var bondIndex = bond.index;
			// skip the path we're comming from
			// if (goog.array.contains(visitedBonds, bondIndex)) {
			if (visitedBonds[bondIndex]) {
				continue;
			}
			visitedBonds[bondIndex] = true;

			var neighbor = bond.otherAtom(atom);
			var neighborIndex = neighbor.index;

			// if the bond is not visited yet but the neighbor is, the bond
			// is a ring closure or chord
			if (visitedAtoms[neighborIndex]) {
				var previous = [], depth;
				if (atom.depth == neighbor.depth) {
					// odd sized ring
					previous.push(atom);
					previous.push(neighbor);
					depth = atom.depth;
				} else {
					// even sized ring
					neighbor.isInCycle = true;
					var nbrNeighbors = neighbor.getNeighbors();
					for (var j = 0, lj = nbrNeighbors.length; j < lj; j++) {
						var nbrNeighbor = nbrNeighbors[j];
						if (nbrNeighbor.depth == neighbor.depth - 1) {
							previous.push(nbrNeighbor);
						}
					}
					depth = atom.depth;
				}

				// backtrack ring and assign isInCylce to all cycle atoms
				while (true) {
					previous[0].isInCycle = true;
					previous[1].isInCycle = true;
					depth--;
					var prevNeighbors1 = previous[0].getNeighbors();
					for (var j = 0, lj = prevNeighbors1.length; j < lj; j++) {
						if (prevNeighbors1[j].depth == depth) {
							previous[0] = prevNeighbors1[j];
							break;
						}
					}
					var prevNeighbors2 = previous[1].getNeighbors();
					for (var j = 0, lj = prevNeighbors2.length; j < lj; j++) {
						if (prevNeighbors2[j].depth == depth) {
							previous[1] = prevNeighbors2[j];
							break;
						}
					}

					if (previous[0] == previous[1]) {
						previous[0].isInCycle = true;
						break;
					}

				}
			} else {
				neighbor.depth = atom.depth + 1;
				visitedAtoms[neighborIndex] = true;
				queue.push(neighbor);
			}
		}
	}
	/*
	 * debug('before: ' + molecule.countAtoms()); var after = 0; for (var i = 0,
	 * li = molecule.countAtoms(); i < li; i++) { if
	 * (molecule.atoms[i].isInCycle) { after++; } } debug('after: ' + after);
	 */
};

/**
 * Create ring systems. These are molecules containing only ring atoms. Each
 * disconnected ring system in the original molecule will result in a single
 * ring system molecule. Ring perception is done on each ring system
 * individually for optimal performance.
 * 
 * @param {specview.model.Molecule}
 *            molecule
 * @return {Array.<specview.ring.Ring>}
 */
specview.ring.RingFinder.createRingSystems = function(molecule) {

	var rings = [];

	var n = molecule.countAtoms();
	var visitedAtoms = goog.array.repeat(false, n);
	var visitedBonds = goog.array.repeat(false, n);
	var indexMap = goog.array.repeat(-1, n); // molecule -> ringSystem


	for (var k = 0, lk = molecule.countAtoms(); k < lk; k++) {
		var startAtom = molecule.atoms[k];
		// skip visited atoms
		if (visitedAtoms[startAtom.index]) {
			continue;
		}
		// skip acyclic atoms
		if (!startAtom.isInCycle) {
			continue;
		}

		// create a new ring system
		var ringSystem = new specview.model.Molecule();

		var queue = [];

		queue.push(startAtom);
		visitedAtoms[0] = true;
		var newAtom = new specview.model.Atom();
		newAtom.index2 = startAtom.index;
		indexMap[startAtom.index] = 0;
		ringSystem.addAtom(newAtom);

		while (true) {
			if (!queue.length) {
				break;
			}

			var atom = queue[0];
			goog.array.removeAt(queue, 0);

			var bonds = atom.bonds.getValues();
			for (var i = 0, li = bonds.length; i < li; i++) {
				var bond = bonds[i];
				var bondIndex = bond.index;
				// skip the path we're comming from
				if (visitedBonds[bondIndex]) {
					continue;
				}
				visitedBonds[bondIndex] = true;

				var neighbor = bond.otherAtom(atom);
				var neighborIndex = neighbor.index;

				if (!neighbor.isInCycle) {
					continue;
				}

				// if the bond is not visited yet but the neighbor is, the
				// bond
				// is a ring closure or chord
				if (visitedAtoms[neighborIndex]) {
					// create the ring closure bond
					var closureBond = molecule.findBond(atom, neighbor);
					var newBond = new specview.model.Bond(ringSystem.atoms[indexMap[atom.index]], ringSystem.atoms[indexMap[neighbor.index]]);
					newBond.index2 = closureBond.index;
					ringSystem.addBond(newBond);
				} else {
					visitedAtoms[neighborIndex] = true;
					queue.push(neighbor);
					// create the new atom
					newAtom = new specview.model.Atom();
					newAtom.index2 = neighbor.index;
					indexMap[neighbor.index] = ringSystem.atoms.length;
					ringSystem.addAtom(newAtom);
					// create the new bond
					var bond = molecule.findBond(atom, neighbor);
					var newBond = new specview.model.Bond(ringSystem.atoms[indexMap[atom.index]], newAtom);
					newBond.index2 = bond.index;
					ringSystem.addBond(newBond);
				}
			}
		}

		// assign indexes
		for (var i = 0, li = ringSystem.atoms.length; i < li; i++) {
			ringSystem.atoms[i].index = i;
		}
		for (var i = 0, li = ringSystem.bonds.length; i < li; i++) {
			ringSystem.bonds[i].index = i;
		}

		var nsssr = ringSystem.bonds.length - ringSystem.atoms.length + 1;

		var sssr;
		// Use Hanser ring finder to find all 3-6 membered rings.
		var hanser = specview.ring.Hanser.findRings(ringSystem, 6);
		if (hanser.length >= nsssr) {
			// Use the Hanser rings to make the first SSSR
			sssr = specview.ring.RingFinder.verifySSSR(hanser, nsssr, ringSystem);
			// Check the size of the first SSSR
			if (sssr.length < nsssr) {
				// Hanser rings don't contain the SSSR, do a full SSSR
				// search
				sssr = specview.ring.SSSR.findRings(ringSystem);
			}
		} else {
			// Hanser rings don't contain the SSSR, do a full SSSR search
			// (there
			// are not enough candidates so we skip the candidateSearch.
			sssr = specview.ring.SSSR.findRings(ringSystem);
		}

		// translate the indexes from the reduced ringSystem back to
		// the original indexes
		for (i = 0, li = sssr.length; i < li; i++) {
			var ring = sssr[i];
			for (var j = 0, lj = ring.length; j < lj; j++) {
				ring[j] = ringSystem.atoms[ring[j]].index2;
			}
		}

		for (var i = 0, il = sssr.length; i < il; i++) {
			rings.push(specview.ring.RingFinder.createRing(sssr[i], molecule));
		}
	}
	return rings;
};

/**
 * @param {specview.model.Molecule}
 *            molecule
 * @return {Array.<specview.ring.Ring>}
 */
specview.ring.RingFinder.findRings = function(molecule) {

	// If there are no rings, we're done
	var nsssr = molecule.countBonds() - molecule.countAtoms() + molecule.fragmentCount;
	if (!nsssr) {
		return [];
	}

	// assign indexes
	for (var i = 0, li = molecule.atoms.length; i < li; i++) {
		var atom = molecule.atoms[i];
		atom.index = i;
		atom.depth = undefined;
		atom.isInCycle = undefined;
		molecule.atoms[i].index = i;
	}
	for (var i = 0, li = molecule.bonds.length; i < li; i++) {
		molecule.bonds[i].index = i;
	}

	// detect ring atoms in O(n) time
	specview.ring.RingFinder.detectRingAtoms(molecule);
	// process the ring systems
	return specview.ring.RingFinder.createRingSystems(molecule);
};
goog.exportSymbol("specview.ring.RingFinder.findRings", specview.ring.RingFinder.findRings);

