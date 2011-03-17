goog.provide('specview.io.Cmlparser');
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

specview.io.Cmlparser=function(){
	
	this.molecule;//A molecule object (empty, or should just contain the name)
	
	this.moleculeTitle="";
	this.moleculeId="";
	
	this.spectrumId="";
	this.spectrumRefMolecule="";//should be the same than moleculeId
	this.spectrumType="";
	this.spectrumNmrType="";//optional
	
	
	this.ArrayOfAtoms=new Array();//Keys are the inner atom id. Values are the atom objects
	this.ArrayOfBonds=new Array();
	this.ArrayOfPeaks=new Array();
	
	
	
};
goog.exportSymbol("specview.model.Cmlparser", specview.model.Cmlparser);
/*
 * Modifiers
 */

specview.io.Cmlparser.prototype.setMoleculId = function(moleculeId){
	this.moleculeId=moleculeId;
};

specview.io.Cmlparser.prototype.setMoleculeTitle = function(title){
	this.moleculeTitle=title;
};

specview.io.Cmlparser.prototype.setSpecId = function(arg){
	this.spectrumId=arg;
};

specview.io.Cmlparser.prototype.setSpecRM = function(arg){
	this.spectrumRefMolecule=arg;
};

specview.io.Cmlparser.prototype.setSpecType = function(arg){
	this.spectrumType=arg;	
};

specview.io.Cmlparser.prototype.setNMRtype = function(arg){
	this.spectrumNmrType=arg;
};

specview.io.Cmlparser.prototype.setAtoms = function(arg){
	this.ArrayOfAtoms=arg;
};

specview.io.Cmlparser.prototype.setBonds = function(arg){
	this.ArrayOfBonds=arg;
	
};
specview.io.Cmlparser.prototype.setPeaks = function(arg){
	this.ArrayOfPeaks=arg;
};

/*
 * Accessors
 */

specview.io.Cmlparser.prototype.getMoleculId = function(moleculeId){
	this.moleculeId;
};

specview.io.Cmlparser.prototype.getMoleculeTitle = function(title){
	this.moleculeTitle;
};

specview.io.Cmlparser.prototype.getSpecId = function(){
	return this.spectrumId;
};

specview.io.Cmlparser.prototype.getSpecRM = function(){
	return this.spectrumRefMolecule;
};

specview.io.Cmlparser.prototype.getSpecType = function(){
	return this.spectrumType;	
};

specview.io.Cmlparser.prototype.getNMRtype = function(){
	return this.spectrumNmrType;
};

specview.io.Cmlparser.prototype.getAtoms = function(){
	return this.ArrayOfAtoms;
};

specview.io.Cmlparser.prototype.getBonds = function(){
	return this.ArrayOfBonds;
	
};
specview.io.Cmlparser.prototype.getPeaks = function(){
	return this.ArrayOfPeaks;
};



specview.io.Cmlparser.prototype.createMoleculeObjectOutOfCmlParserObject=function(){
	
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
	
	return this;
	
};

specview.io.Cmlparser.prototype.createSpectrumObjectOutOfCmlParserObject=function(){
	
};




specview.io.Cmlparser.prototype.toString=function(){
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


/*
 * Main function. Read the string format of the file.
 */


specview.io.Cmlparser.prototype.ReadCmlFile=function(string){

	var cmlList=string.split("\n");
	var cmlListLength=cmlList.length;
	
	var atomNumber=0;
	var bondNumber=0;
	var peakNumber=0;
	
	for(k in cmlList){
		var tag=specview.io.Cmlparser.prototype.getTag(cmlList[k]);//atomArray or bondArray or peakList
		switch(tag){
		//Creation of the empty molecule object of the graphical object
		case "<molecule":
			var parsedString=cmlList[k].split(" ");
			this.moleculeId=specview.io.getSubTagAfterCharacter(parsedString[2],"=");	
			this.moleculeTitle=specview.io.getSubTagAfterCharacter(parsedString[1],"=");
			this.molecule=new specview.model.Molecule(this.moleculeTitle);
			break;
		//We create avery atom objects and put it in an Array. That way, we will be  able later to create the non empty molecule
		case "<atom":
			var hashTableAtom=specview.io.Cmlparser.prototype.createAtomFromCmlLine(cmlList[k]);
			var id=hashTableAtom.id;
			var atom=hashTableAtom.atom;
			this.ArrayOfAtoms[id]=atom;
			break;
		//We create every bond objects and put it in an Array. That way, we will be  able later to create the non empty molecule
		case "<bond":
			var bond=specview.io.Cmlparser.prototype.createBondFromCmlLine(cmlList[k],this.ArrayOfAtoms);
			this.ArrayOfBonds[bondNumber]=bond;
			bondNumber++;
			break;
		//We create the spectrum object. It becomes an argument of the graphical object
		case "<spectrum":
			var parsedString=cmlList[k].split(" ");
			this.spectrumId=specview.io.getSubTagAfterCharacter(parsedString[1],"=");
			this.spectrumRefMolecule=specview.io.getSubTagAfterCharacter(parsedString[2],"=");
			this.spectrumType=specview.io.getSubTagAfterCharacter(parsedString[3],"=");
			break;
		case "<metadata":
			var parsedString=cmlList[k].split(" ");
			if(specview.io.getSubTagAfterCharacter(parsedString[1],"=")=="nmr:OBSERVENUCLEUS"){
				this.spectrumNmrType=specview.io.getSubTagAfterCharacter(parsedString[2],"=");
			}
			break;
		case "<peak":
			var peak=specview.io.Cmlparser.prototype.createPeakFromCmlLine(cmlList[k],this.ArrayOfAtoms);
			this.ArrayOfPeaks[peakNumber]=peak;
			peakNumber++;
			break;
		}
	}
//	alert("this: \n\n\n"+this);
	return this;
};



/*
 * Input: a string from a cml file whose tag is "peak"
 * Output: an object Peak
 */

specview.io.Cmlparser.prototype.createPeakFromCmlLine = function(string,ArrayOfAtoms){
	var parsedString=string.split(" ");
	
	var id;
	var xValue;
	var multiplicity;
	var height;//Optional
	var atomRefs=new Array();
	
	for(k in parsedString){
		var currentString=parsedString[k];
		var attribute=specview.io.spec.getSubTagBeforeCharacter(currentString,"=");
//		alert("attribute: "+attribute);
		if(attribute==undefined){attribute=currentString;}
		switch(attribute){
		
		case "<peak" :
			break;
		case "peakShape":
			break;
		case "xUnits":
			break;
		case "id":
			id=specview.io.getSubTagAfterCharacter(currentString,"=");
			break;
		case  "atomRefs" :
//			atomRefs.push(specview.io.getSubTagAfterCharacter(currentString,"="));
			atomRefs.push(specview.io.getSubTagAfterCharacterAndBeforeCharacter(currentString,"=","/"));
			break;
		case "peakMultiplicity":
			multiplicity=specview.io.getSubTagAfterCharacter(currentString,"=");
			break;
		case "xValue":
			xValue=parseFloat(specview.io.getSubTagAfterCharacter(currentString,"="));
			break;
		case "height":
			height=specview.io.getSubTagAfterCharacter(currentString,"=");
			break;
		default://We suppose that if there is  no "=", it means that it is a reference atom. So it has to be a reference atom.
			atomRefs.push(specview.io.getSubTagAfterCharacter(currentString,"="));
			break;
		}
	}
//	alert(id+"\n\n"+xValue+"\n\n"+multiplicity+"\n\n"+atomRefs);
	
	for(k in atomRefs){
		var atomRef=atomRefs[k];
//		alert(atomRefs[k]);
		for(s in ArrayOfAtoms){
			var atom=ArrayOfAtoms[s];
			var innerIdentifier=atom.innerIdentifier;
//			alert(innerIdentifier+"\n\n"+atom.innerIdentifier);
			if(atomRef==innerIdentifier){
				atom.peakMap[atomRef].push(id);
			}
		}
	}
	
	
	new specview.model.Peak(xValue,height,id, atomRefs);
};


/*
 * Input: a string from a cml file whose tag is "atom"
 * Output: an object Atom
 */
specview.io.Cmlparser.prototype.createAtomFromCmlLine=function(string){
	var parsedString=string.split(" ");
	
	var id;
	var elementType;
	var x;
	var y;
	var charge;
	var hydrogenCount;
	var istopeNumber;
	
	var objectToReturn=new Object();
	
	for(k in parsedString){
		var currentString=parsedString[k];
		var attribute=specview.io.spec.getSubTagBeforeCharacter(currentString,"=");
		switch(attribute){
		case "id":
			id=specview.io.getSubTagAfterCharacter(currentString,"=");
			objectToReturn.id=id;
			break;
		case  "elementType" :
			elementType=specview.io.getSubTagAfterCharacter(currentString,"=");
			break;
		case "x2":
			x=parseFloat(specview.io.getSubTagAfterCharacter(currentString,"="));
			break;
		case "y2":
			y=parseFloat(specview.io.getSubTagAfterCharacter(currentString,"="));
			break;
		case "formalCharge":
			charge=specview.io.getSubTagAfterCharacter(currentString,"=");
			break;
		case "hydrogenCount":
			hydrogenCount=specview.io.getSubTagAfterCharacter(currentString,"=");
			break;
		case "istotopeNumber":
			isotopeNumber=specview.io.getSubTagAfterCharacter(currentString,"=");
			break;
		}
	}
	objectToReturn.atom=new specview.model.Atom(elementType,x,y,charge);
	objectToReturn.atom.setInnerIdentifier(objectToReturn.id);//Each atom of the molecule has its own identifier that may refer to a peak
	objectToReturn.atom.peakMap[objectToReturn.atom.innerIdentifier]=new Array();//[a1:[] , a2:[] ....]
	return objectToReturn;
};


/*
 * Input: a string from a cml file whose tag is "bond"
 * Output: an object Bond
 */

specview.io.Cmlparser.prototype.createBondFromCmlLine=function(string,ArrayOfAtoms){
	var parsedString=string.split(" ");
	
	var id;
	var type;
	var stereo=null;
	var source;
	var target;
	
	for(k in parsedString){
		var currentString=parsedString[k];
		var attribute=specview.io.spec.getSubTagBeforeCharacter(currentString,"=");
		if(attribute==undefined)(attribute=currentString);//To consider the case of the target atom "a2"
		switch(attribute){
		case "id":
			id=specview.io.getSubTagAfterCharacter(currentString,"=");
			break;
		case  "atomRefs2" :
			source=specview.io.getSubTagAfterCharacter(currentString,"=");
			break;
		case "order":
			type=specview.io.getSubTagAfterCharacter(currentString,"=");
			if(type=="S/>"){type=specview.io.mdl.SINGLE_BOND;}
			else if(type=="D/>"){type=specview.io.mdl.DOUBLE_BOND;}
			break;
		case "stereo":
			stereo=specview.io.getSubTagAfterCharacter(currentString,"=");
		default://We suppose that if there is  no "=", it means that it is a reference atom. So it has to be the target atom.
			target=attribute;
		}
		stereo=(stereo ? stereo : specview.io.mdl.NOT_STEREO);
	}
	return new specview.io.mdl.createBond(type, stereo, ArrayOfAtoms[source], ArrayOfAtoms[target]);
};


/*
 * Input: line of a CML file
 * Output: the tag
 * Can/Has to be ameliorated
 */

specview.io.Cmlparser.prototype.getTag=function(string){
	var tag="";
	var len=string.length;
	var k=0;
	for(k in string){
		var char=string[k];
		if(char==" " || (char==">")){
			return tag;
		}
		tag=tag+char;
	}
	return tag;
};





