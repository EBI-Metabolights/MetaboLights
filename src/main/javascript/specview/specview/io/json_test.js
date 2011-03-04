goog.require('specview.io.json');
goog.require('goog.testing.jsunit');
goog.require('goog.json.Serializer');
goog.require('goog.debug.Console');
goog.require('goog.debug.Logger');

function setUp() {
	var c = new goog.debug.Console();
	c.setCapturing(true);
	logger = goog.debug.Logger.getLogger('JsonTest');
	logger.setLevel(goog.debug.Logger.Level.ALL);
}

function testReadWriteMolecule() {
	var mol = specview.io.json.readMolecule(jmol);
	assertEquals('test', mol.name);
	assertEquals(26, mol.countAtoms());
	assertEquals(27, mol.countBonds());

	var moljson = specview.io.json.moleculeToJson(mol);
	var mol2 = specview.io.json.readMolecule(moljson);
	assertEquals(mol.name, "test");
	assertEquals(mol.countAtoms(), 26);
	assertEquals(mol.countBonds(), 27);
}

function testReadMoleculeAromatic() {
	var mol = specview.io.json.readMolecule(jmol2);
	assertEquals(mol.name, "test");
	assertEquals(mol.countAtoms(), 26);
	assertEquals(mol.countBonds(), 27);
}

function test1ExportMol() {
	var mol = specview.io.json.readMolecule(jmol);
	var jmolstr = specview.io.json.writeMolecule(mol);
	var mol2 = specview.io.json.readMolecule(jmolstr);
	assertEquals('test', mol2.name);
	assertEquals(26, mol2.countAtoms());
	assertEquals(27, mol2.countBonds());

}

