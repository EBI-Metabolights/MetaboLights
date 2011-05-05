goog.require('goog.testing.jsunit');
goog.require('specview.ring.RingFinder');
goog.require('specview.io.mdl');

var testAzulene = function() {
	var mol = specview.io.mdl.readMolfile(azulene);
	var rings = specview.ring.RingFinder.findRings(mol);
	assertEquals('should find 2 rings', 2, rings.length);	
}

var testAlphaPinene = function() {
	var mol=specview.io.mdl.readMolfile(alpha_pinene);
	var rings = specview.ring.RingFinder.findRings(mol);
	assertEquals('should find 2 rings', 2, rings.length);
}

function testBiphenyl(){
	var mol = specview.io.mdl.readMolfile(biphenyl);
	var rings = specview.ring.RingFinder.findRings(mol);
	assertEquals('should find 2 rings', 2, rings.length);
}

function testSpiro45Decane(){
	var mol=specview.io.mdl.readMolfile(spiro_decane);
	var rings = specview.ring.RingFinder.findRings(mol);
	assertEquals('should find 2 rings', 2, rings.length);
}

function testIsopropylcyclopentane(){
	var mol=specview.io.mdl.readMolfile(cycloPentane);
	var rings = specview.ring.RingFinder.findRings(mol);
	assertEquals('should find 1 ring', 1, rings.length);
}

function testMergedRings(){
 	var mol=specview.io.mdl.readMolfile(dimethylNaphtalene);
	var rings = specview.ring.RingFinder.findRings(mol);
	assertEquals('should find 2 rings', 2, rings.length);
}

