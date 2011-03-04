goog.require('goog.debug.Console');
goog.require('goog.debug.Logger');
goog.require('goog.testing.jsunit');
goog.require('specview.io.mdl');


function setUp() {
	c = new goog.debug.Console();
	c.setCapturing(true);
	logger = goog.debug.Logger.getLogger('SssrTest');
};

function testBenzene() {
	findRings(specview.io.mdl.readMolfile(benzene), [ 6 ]);
}

function testCubane() {
	findRings(specview.io.mdl.readMolfile(cubane), [ 4, 4, 4, 4, 4 ]);

}

function testTetraPhosporus() {
	findRings(specview.io.mdl.readMolfile(tetraPhosphorus), [ 3, 3, 3 ]);
}

function testNaphthalene() {
	findRings(specview.io.mdl.readMolfile(dimethylNaphtalene), [ 6, 6 ]);
}

function findRings(mol, ringSizes) {
	var rings = specview.ring.SSSR.findRings(mol);
	assertEquals(ringSizes.length, rings.length);
	for ( var i = 0; i < ringSizes.length; i++) {
		assertEquals(ringSizes[i], rings[i].length);
	}
}


function testFig1() {
	// fig. 1 from the paper
	var atom1 = new specview.model.Atom("C", 0, 0);
	var atom2 = new specview.model.Atom("C", 0, 0);
	var atom3 = new specview.model.Atom("C", 0, 0);
	var atom4 = new specview.model.Atom("C", 0, 0);
	var atom5 = new specview.model.Atom("C", 0, 0);
	var atom6 = new specview.model.Atom("C", 0, 0);
	var atom7 = new specview.model.Atom("C", 0, 0);
	var bond1 = new specview.model.Bond(atom1, atom2);
	var bond2 = new specview.model.Bond(atom2, atom3);
	var bond3 = new specview.model.Bond(atom3, atom4);
	var bond4 = new specview.model.Bond(atom4, atom5);
	var bond5 = new specview.model.Bond(atom5, atom6);
	var bond6 = new specview.model.Bond(atom6, atom7);
	var bond7 = new specview.model.Bond(atom7, atom1);
	var bond8 = new specview.model.Bond(atom6, atom2);

	var molecule = new specview.model.Molecule();
	molecule.addAtom(atom1);
	molecule.addAtom(atom2);
	molecule.addAtom(atom3);
	molecule.addAtom(atom4);
	molecule.addAtom(atom5);
	molecule.addAtom(atom6);
	molecule.addAtom(atom7);
	molecule.addBond(bond1);
	molecule.addBond(bond2);
	molecule.addBond(bond3);
	molecule.addBond(bond4);
	molecule.addBond(bond5);
	molecule.addBond(bond6);
	molecule.addBond(bond7);
	molecule.addBond(bond8);

	// matrices from the paper
	D = [ [ 0, 1, 2, 3, 3, 2, 1 ], [ 1, 0, 1, 2, 2, 1, 2 ],
			[ 2, 1, 0, 1, 2, 2, 3 ], [ 3, 2, 1, 0, 1, 2, 3 ],
			[ 3, 2, 2, 1, 0, 1, 2 ], [ 2, 1, 2, 2, 1, 0, 1 ],
			[ 1, 2, 3, 3, 2, 1, 0 ] ];

	Pe1 = [
			[ [], [ [ 0 ] ], [ [ 0, 1 ] ], [ [ 0, 1, 2 ] ],
					[ [ 0, 7, 4 ], [ 6, 5, 4 ] ], [ [ 0, 7 ], [ 6, 5 ] ],
					[ [ 6 ] ] ],
			[ [ [ 0 ] ], [], [ [ 1 ] ], [ [ 1, 2 ] ], [ [ 7, 4 ] ], [ [ 7 ] ],
					[ [ 0, 6 ], [ 7, 5 ] ] ],
			[ [ [ 1, 0 ] ], [ [ 1 ] ], [], [ [ 2 ] ], [ [ 2, 3 ] ],
					[ [ 1, 7 ] ], [ [ 1, 0, 6 ], [ 1, 7, 5 ] ] ],
			[ [ [ 2, 1, 0 ] ], [ [ 2, 1 ] ], [ [ 2 ] ], [], [ [ 3 ] ],
					[ [ 3, 4 ] ], [ [ 3, 4, 5 ] ] ],
			[ [ [ 4, 7, 0 ], [ 4, 5, 6 ] ], [ [ 4, 7 ] ], [ [ 3, 2 ] ],
					[ [ 3 ] ], [], [ [ 4 ] ], [ [ 4, 5 ] ] ],
			[ [ [ 7, 0 ], [ 5, 6 ] ], [ [ 7 ] ], [ [ 7, 1 ] ], [ [ 4, 3 ] ],
					[ [ 4 ] ], [], [ [ 5 ] ] ],
			[ [ [ 6 ] ], [ [ 6, 0 ], [ 5, 7 ] ], [ [ 6, 0, 1 ], [ 5, 7, 1 ] ],
					[ [ 5, 4, 3 ] ], [ [ 5, 4 ] ], [ [ 5 ] ], [] ] ];

	Pe2 = [
			[ [], [], [], [ [ 0, 7, 4, 3 ], [ 6, 5, 4, 3 ] ],
					[ [ 0, 1, 2, 3 ] ], [], [] ],
			[ [], [], [], [ [ 7, 4, 3 ] ], [ [ 1, 2, 3 ] ], [], [] ],
			[ [], [], [], [], [ [ 1, 7, 4 ] ], [ [ 2, 3, 4 ] ],
					[ [ 2, 3, 4, 5 ] ] ],
			[ [ [ 3, 4, 7, 0 ], [ 3, 4, 5, 6 ] ], [ [ 3, 4, 7 ] ], [], [], [],
					[ [ 2, 1, 7 ] ], [ [ 5, 7, 1, 2 ], [ 6, 0, 2, 1 ] ] ],
			[ [ [ 3, 2, 1, 0 ] ], [ [ 3, 2, 1 ] ], [ [ 4, 7, 1 ] ], [], [], [],
					[] ],
			[ [], [], [ [ 4, 3, 2 ] ], [ [ 7, 1, 2 ] ], [], [], [] ],
			[ [], [], [ [ 5, 4, 3, 2 ] ], [ [ 2, 1, 0, 6 ], [ 2, 1, 7, 5 ] ],
					[], [], [] ] ];

	// var rings = specview.ring._makePIDMatrixes(molecule);
	// var rings = specview.ring._makeCandidateSet(D, Pe1, Pe2);

	var rings = specview.ring.SSSR.findRings(molecule);

	// print the rings
	// for (var i = 0; i < rings.length; i++) {
	// var C = rings[i];
	// debug("ring: " + JSON.stringify(C));
	// }

	assertEquals(2, rings.length);
	assertEquals(4, rings[0].length);
	assertEquals(5, rings[1].length);
}
