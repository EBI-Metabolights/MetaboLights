goog.require('goog.testing.jsunit');
goog.require('specview.ring.RingPartitioner');
goog.require('specview.io.mdl');

function testDirectlyConnectedRings() {
	var a1 = new specview.model.Atom("C");
	var a2 = new specview.model.Atom("N");
	var a3 = new specview.model.Atom("O");
    var a4 = new specview.model.Atom("P");

	var ring1 = new specview.ring.Ring( [ a1, a2 ]);
	var ring2 = new specview.ring.Ring( [ a2, a3 ]);
	var ring3 = new specview.ring.Ring( [ a3 ]);
    var ring4 = new specview.ring.Ring( [ a4 ]);

    var direct_connect = specview.ring.RingPartitioner.getPartitionedRings([ ring1, ring2, ring3, ring4 ]);
	assertEquals('should be one ring', 2, direct_connect.length);
}

function testAlphaPinene() {
	var mol = specview.io.mdl.readMolfile(alpha_pinene);
	var sssr = mol.getRings();
	var ring_set = specview.ring.RingPartitioner.getPartitionedRings(sssr);
	assertEquals('one ring set', 1, ring_set.length);
	assertEquals('two rings in ring set', 2, ring_set[0].length);
	assertEquals(4, ring_set[0][0].atoms.length);
	assertEquals(6, ring_set[0][1].atoms.length);
}

function testAzulene() {
	var mol = specview.io.mdl.readMolfile(azulene);
	var sssr = mol.getRings();
	var ring_set = specview.ring.RingPartitioner.getPartitionedRings(sssr);
	assertEquals('one ring set', 1, ring_set.length);
}

function testBiphenyl() {
	var mol = specview.io.mdl.readMolfile(biphenyl);
	var sssr = mol.getRings();
	var ring_set = specview.ring.RingPartitioner.getPartitionedRings(sssr);
	assertEquals('two ring sets', 2, ring_set.length);
}

function testSpiroRings() {
	var mol = specview.io.mdl.readMolfile(spiro_decane);
	var sssr = mol.getRings();
	var ring_set = specview.ring.RingPartitioner.getPartitionedRings(sssr);
	assertEquals('one ring set', 1, ring_set.length);
	assertEquals('two rings', 2, ring_set[0].length);
}
