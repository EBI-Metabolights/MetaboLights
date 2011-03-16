goog.provide('specview.io.spec');
goog.require('specview.model.Spectrum');
goog.require('specview.util.Smurf');
goog.require('specview.model.Peak');
goog.require('specview.model.Molecule');
goog.require('specview.model.Bond');
goog.require('specview.model.Atom');


/*
 * This file contains the parser of a spectrum CML file. Not very very generic but can be easily extended.
 * It also contains some functions that are useful to deal with strings. However they should be placed in another file(under the 
 * folder util for instance)
 */

/*
 * Input: CML file representing a spectrum
 * Output: An object of type Spectrum
 */
specview.io.spec.readSpecfile=function(specfile)
{
	var cmlList=specfile.split("\n");
	var cmlListLength=cmlList.length;
	var parsedString;
	var spectrum=new specview.model.Spectrum();
	var peaksList=new Array();
	var k=0;
	
	while(k<cmlListLength)
		{
			tag=specview.io.spec.getSubTagBeforeCharacter(cmlList[k].substr(1)," ");
	 		if(tag=="spectrum"){
				var specInfo=specview.io.getSpecInfo(cmlList[k]);
				spectrum.setMoleculeRef(specInfo.molRef);
				spectrum.setMoleculeId(specInfo.id);
				spectrum.setExperiment(specInfo.type);
				}else if(tag=="metadata"){
					var NMRtype=specview.io.getMetaInfo(cmlList[k]);
					spectrum.setNMRtype(NMRtype);
			}else if(tag=="peak"){
				peaksList.push(specview.io.getPeakInfo(cmlList[k]));
			}
			k=k+1;
		}
	spectrum.setPeakList(peaksList);
	return spectrum;
};

/*
 * Get the relevant information for the NMR peaks.
 * Input: string to the CML format containing the information on a peak
 * Output: Object Peak containing the information of a peak: its Id, its related atom and its xValue.
 */
specview.io.getPeakInfo=function(string){
	var peak=new specview.model.Peak();
	parsedString=string.split(" ");
	var len=parsedString.length;
	for(var k=0;k<len;k++){
		var currentString=parsedString[k];
		switch(specview.io.spec.getSubTagBeforeCharacter(currentString,"=")){
		case "xValue":
			peak.setXvalue(specview.io.getSubTagAfterCharacter(currentString,"="));
			break;
		case "id":
			peak.setPeakId(specview.io.getSubTagAfterCharacter(currentString,"="));
		break;
		case "atomRefs":
			peak.setAtomRef(specview.io.getSubTagAfterCharacter(currentString,"="));
			break;
		}
		peak.setIntensity(100);
	}
	return peak;
};

/*
 * Get the specific information of the NMR experiment
 * Input: string to the CML format containing the information on the experiment
 * Output: String of the NMR type(1H,13C)
 */
specview.io.getMetaInfo=function(string){
	var parsedString=string.split(" ");
	var nmrType;
//	alert(specview.io.getSubTagAfterCharacter(parsedString[1],":"));
	if(specview.io.getSubTagAfterCharacter(parsedString[1],":")=="OBSERVENUCLEUS\""){
		nmrType=specview.io.getSubTagAfterCharacter(parsedString[2],"=");
		}
	return nmrType;
};

/*
 * Get the specific information of the spectrum
 * Input: string to the CML format containing the information on the spectrum
 * Output: Object Object with the minimum information of a  spectrum: spectrum Id, molecule reference and experiment type
 */
specview.io.getSpecInfo=function(string){
	var hash = new Object();
	var parsedString=string.split(" ");
	var l=parsedString.length;
	for(var k=0;k<l;k++){
		if(specview.io.startsWith("id",parsedString[k])){
			hash.id=specview.io.getSubTagAfterCharacter(parsedString[k]);
		}
		if(specview.io.startsWith("moleculeRef",parsedString[k])){
			hash.molRef=specview.io.getSubTagAfterCharacter(parsedString[k]);
		}
		if(specview.io.startsWith("type",parsedString[k])){
			hash.type=specview.io.getSubTagAfterCharacter(parsedString[k]);
		}
	}
//	alert(hash.id+"\n\n"+hash.molRef+"\n\n"+hash.type);
	return hash;
};


/*
 * Check if a string start with a specific word. Should be in Util
 */
specview.io.startsWith=function(word,string){
	return (word==string.substr(0,word.length) ? true : false);
};

/*
 * Return the substring after a specified character. Should be in Util
 */
specview.io.getSubTagAfterCharacter=function(string,startCharacter){
//	alert(string);
	return string.substr(string.indexOf(startCharacter)+1);
};

/*
 * Return the substring before a specified character. Should be in Util
 */
specview.io.spec.getSubTagBeforeCharacter=function(string,endCharacter){
//	alert(string);
	var returnWord="";
	if(string==undefined || string.length==0)
		{
		return string;
		}
	for(k in string)
		{
			if(string[k]!=endCharacter)
				{
//				alert(string[k]+" k is different than a space");
				returnWord=returnWord+string[k];
				}
			else
				{
//					alert("k is a space");
					return returnWord;
				}
		}
};


specview.io.spec.getInfOfTag=function(tag,string){
//	alert(string);
	var parsedString;
	if(string instanceof Array){
//		alert("special case");
		parsedString=string;
	}else{
	//	alert("not special case");
		parsedString=string.split(" ");
	}
	for(k in parsedString){
		if(specview.io.startsWith(tag,parsedString[k])){
			return specview.io.getSubTagAfterCharacter(parsedString[k],"=");
		}
	}
	return null;
};


specview.io.spec.parsedCmlWithSpace=function(string){
	var tab=new Array();
	var l=string.length;
	var i=0;
	for(var k=0;k<l;k++){
		var char=string[k];
		if(char==" "){
			if(string[k-1]=="\"" || string[k-2]=="\""){
				i++;	
			}else{tab[i]=tab[i]+char;
				}
		}else if(!tab[i]){
			tab[i]="";
			tab[i]=tab[i]+char;
		}else{
			tab[i]=tab[i]+char;
		}
	}
//	alert("initial string: "+string+"\n\n"+"parsed string: "+tab);
	return tab;
};


/**
 * Parses molecule from a CML file
 * TODO ja revise this
 *  
 * @param {string} molfile string to convert
 * @return {specview.model.Molecule}
 */
specview.io.spec.readMolFromCmlFile= function(cmlfile){
	this.logger.info("molecule in the make ")
	var cmlList=cmlfile.split("\n");
	var atomRef=new Array();
	var cmlListLength=cmlList.length;
	var parsedString;
	var molecule;
	for(var k=0;k<cmlListLength;k++){
		var currentString=cmlList[k];
		var tag=specview.io.spec.getSubTagBeforeCharacter(cmlList[k].substr(1)," ");
		switch(tag){
		case "molecule":
			molecule = new specview.model.Molecule(specview.io.spec.getInfOfTag("title",currentString));
			break;
		case "atom":
			var id=specview.io.spec.getInfOfTag("id",currentString);
			var xCoord=specview.io.spec.getInfOfTag("x2",currentString).replace("\"","").replace("\"","");
			var yCoord=specview.io.spec.getInfOfTag("y2",currentString).replace("\"","").replace("\"","");
			var symbol=specview.io.spec.getInfOfTag("elementType",currentString).replace("\"","").replace("\"","");
			var chg=specview.io.spec.getInfOfTag("formalCharge",currentString).replace("\"","").replace("\"","");
			this.logger.info("symbol '"+symbol+"'")
			var atom=new specview.model.Atom(symbol, parseFloat(xCoord),parseFloat(yCoord), chg);
			atomRef[id]=atom;
			molecule.addAtom(atom);
			break;
		case "bond":
			var bondParsedString= specview.io.spec.parsedCmlWithSpace(currentString);
			var atomes=specview.io.spec.getInfOfTag("atomRefs2",bondParsedString).split(" ");
			var source=atomRef[atomes[0]+"\""];
			var target=atomRef["\""+atomes[1]];
			var type=specview.io.spec.getInfOfTag("order",currentString);
			var bondType;
			switch(type){
			case "\"S\"/>":
				bondType=specview.model.Bond.ORDER.SINGLE;
				break;
			case "\"D\"/>" :
				bondType=specview.model.Bond.ORDER.DOUBLE;
				break;
			}
			
			var newBond=new specview.model.Bond(source, target, bondType);
			molecule.addBond(newBond);
			break;
		}
	}
	this.logger.info("molecule made "+molecule.name+"  "+molecule.bonds.length)
	this.logger.info("__________________________________");
	return molecule;
};


specview.io.spec.logger = goog.debug.Logger.getLogger('specview.io.spec');



