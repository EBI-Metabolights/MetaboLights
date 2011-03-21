goog.provide('specview.model.Cmlobject');
goog.require('specview.io.mdl');
goog.require('specview.model.Spectrum');
goog.require('specview.util.Smurf');
goog.require('specview.model.Peak');
//goog.require('specview.io.spec');


/*
 * This Cmlparser object contains every information to directly create :
 * Molecule object
 * Spectrum object
 * Peak object
 */

specview.model.Cmlobject=function(){
	
	this.molecule="";//A molecule object (empty, or should just contain the name)
	
	this.moleculeTitle="";// USELESS
	this.moleculeId="";
	
	this.spectrumId="";
	this.spectrumRefMolecule="";//should be the same than moleculeId
	this.spectrumType="";
	this.spectrumNmrType="";//optional
	
	
	this.ArrayOfAtoms=new Array();//Keys are the inner atom id. Values are the atom objects
	this.ArrayOfBonds=new Array();
	this.ArrayOfPeaks=new Array();
	
	
	
};
goog.exportSymbol("specview.model.Cmlobject", specview.model.Cmlobject);
/*
 * Modifiers
 */

specview.model.Cmlobject.prototype.setMoleculId = function(moleculeId){
	this.moleculeId=moleculeId;
};

specview.model.Cmlobject.prototype.setMoleculeTitle = function(title){
	this.moleculeTitle=title;
};

specview.model.Cmlobject.prototype.setSpecId = function(arg){
	this.spectrumId=arg;
};

specview.model.Cmlobject.prototype.setSpecRM = function(arg){
	this.spectrumRefMolecule=arg;
};

specview.model.Cmlobject.prototype.setSpecType = function(arg){
	this.spectrumType=arg;	
};

specview.model.Cmlobject.prototype.setNMRtype = function(arg){
	this.spectrumNmrType=arg;
};

specview.model.Cmlobject.prototype.setAtoms = function(arg){
	this.ArrayOfAtoms=arg;
};

specview.model.Cmlobject.prototype.setBonds = function(arg){
	this.ArrayOfBonds=arg;
	
};
specview.model.Cmlobject.prototype.setPeaks = function(arg){
	this.ArrayOfPeaks=arg;
};

/*
 * Accessors
 */

specview.model.Cmlobject.prototype.getMoleculId = function(moleculeId){
	this.moleculeId;
};

specview.model.Cmlobject.prototype.getMoleculeTitle = function(title){
	this.moleculeTitle;
};

specview.model.Cmlobject.prototype.getSpecId = function(){
	return this.spectrumId;
};

specview.model.Cmlobject.prototype.getSpecRM = function(){
	return this.spectrumRefMolecule;
};

specview.model.Cmlobject.prototype.getSpecType = function(){
	return this.spectrumType;	
};

specview.model.Cmlobject.prototype.getNMRtype = function(){
	return this.spectrumNmrType;
};

specview.model.Cmlobject.prototype.getAtoms = function(){
	return this.ArrayOfAtoms;
};

specview.model.Cmlobject.prototype.getBonds = function(){
	return this.ArrayOfBonds;
	
};
specview.model.Cmlobject.prototype.getPeaks = function(){
	return this.ArrayOfPeaks;
};


/**
 * 
 * @returns {specview.model.Cmlobject}
 */
specview.model.Cmlobject.prototype.createMoleculeObjectOutOfCmlParserObject=function(){
			
	var listOfAllTheAtomsOfTheMolecule=this.ArrayOfAtoms;
	var listOfAllTheBondsOfTheMolecule=this.ArrayOfBonds;
	
	//Add the atoms to the molecule
	for(k in listOfAllTheAtomsOfTheMolecule){
		this.molecule.addAtom(listOfAllTheAtomsOfTheMolecule[k]);
	}
	
	//Add the bonds to the molecule
	for(k in listOfAllTheBondsOfTheMolecule){
		this.molecule.addBond(listOfAllTheBondsOfTheMolecule[k]);
	}
	
	return this;//THe molecule is now created
	
};


specview.model.Cmlobject.prototype.createSpectrumObjectOutOfCmlParserObject=function(){
	
};

specview.model.Cmlobject.prototype.toString=function(){
	return ("Molecule Title: "+this.moleculeTitle+
			"\nMolecule Id: "+this.moleculeId+
			"\nspectrum Id: "+this.spectrumId+
			"\nspectrum Mol: "+this.spectrumRefMolecule+
			"\nspectrum typ: "+this.spectrumType+
			"\nNMRTYPE: "+this.spectrumNmrType+
			"\nAtoms number: "+this.ArrayOfAtoms["a2"]+
			"\nBonds number:"+this.ArrayOfBonds.length
			);
};


