goog.provide('specview.model.NMRdata');
goog.require('specview.io.mdl');
goog.require('specview.model.Spectrum');
goog.require('specview.util.Utilities');
goog.require('specview.model.Peak');


/*
 * A class to cpature spectrum data as found in NMRshiftDb
 */

specview.model.NMRdata=function(){
	
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
goog.exportSymbol("specview.model.NMRdata", specview.model.NMRdata);
/*
 * Modifiers
 */

specview.model.NMRdata.prototype.setMoleculId = function(moleculeId){
	this.moleculeId=moleculeId;
};

specview.model.NMRdata.prototype.setMoleculeTitle = function(title){
	this.moleculeTitle=title;
};

specview.model.NMRdata.prototype.setSpecId = function(arg){
	this.spectrumId=arg;
};

specview.model.NMRdata.prototype.setSpecRM = function(arg){
	this.spectrumRefMolecule=arg;
};

specview.model.NMRdata.prototype.setSpecType = function(arg){
	this.spectrumType=arg;	
};

specview.model.NMRdata.prototype.setNMRtype = function(arg){
	this.spectrumNmrType=arg;
};

specview.model.NMRdata.prototype.setAtoms = function(arg){
	this.ArrayOfAtoms=arg;
};

specview.model.NMRdata.prototype.setBonds = function(arg){
	this.ArrayOfBonds=arg;
	
};
specview.model.NMRdata.prototype.setPeaks = function(arg){
	this.ArrayOfPeaks=arg;
};

/*
 * Accessors
 */

specview.model.NMRdata.prototype.getMoleculId = function(moleculeId){
	this.moleculeId;
};

specview.model.NMRdata.prototype.getMoleculeTitle = function(title){
	this.moleculeTitle;
};

specview.model.NMRdata.prototype.getSpecId = function(){
	return this.spectrumId;
};

specview.model.NMRdata.prototype.getSpecRM = function(){
	return this.spectrumRefMolecule;
};

specview.model.NMRdata.prototype.getSpecType = function(){
	return this.spectrumType;	
};

specview.model.NMRdata.prototype.getNMRtype = function(){
	return this.spectrumNmrType;
};

specview.model.NMRdata.prototype.getAtoms = function(){
	return this.ArrayOfAtoms;
};

specview.model.NMRdata.prototype.getBonds = function(){
	return this.ArrayOfBonds;
	
};
specview.model.NMRdata.prototype.getPeaks = function(){
	return this.ArrayOfPeaks;
};


/**
 * 
 * @returns {specview.model.NMRdata}
 */
specview.model.NMRdata.prototype.createMoleculeObjectOutOfCmlParserObject=function(){
			
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


specview.model.NMRdata.prototype.createSpectrumObjectOutOfCmlParserObject=function(){
	
};

specview.model.NMRdata.prototype.toString=function(){
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


