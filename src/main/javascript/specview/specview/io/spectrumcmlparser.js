goog.provide('specview.io.SpectrumCMLParser');
goog.require('goog.dom.xml');
goog.require('goog.string');
goog.require('goog.userAgent');
goog.require('goog.debug.Logger');

/**
 * Parses CML with spectrum data such as can be found in NMR Shift DB.
**/

/**
 * Converts CML string to DOM
 * @param cmlString
 * @returns XMLdocument
 */
specview.io.SpectrumCMLParser.getDocument=function(cmlString) {
	return goog.dom.xml.loadXml(cmlString);
};


/**
 * Parses CML XMl document into NMRData
 * @param XMLdocument
 * @returns {specview.model.NMRdata()}
 */
specview.io.SpectrumCMLParser.parseDocument=function(XMLdoc){

	var nmrData= new specview.model.NMRdata();// The cmlObject that holds atoms,bonds,molecule and spectrum
		
	var atoms=XMLdoc.getElementsByTagName("atom");// All of its atoms
	var peaks=XMLdoc.getElementsByTagName("peak");// All of its peaks
	var mol=XMLdoc.getElementsByTagName("molecule");// The information regarding the molecule
	var cNodes=XMLdoc.getElementsByTagName("bondArray")[0].childNodes;// All of the bonds including the stereo informations
	
	var lenAtom=atoms.length;
	var lenBond=cNodes.length;
	var lenPeak=peaks.length;
		
    //Attributes of an Atom object
	var atomId; var elementType; var x; var y; var charge; var hydrogenCount; 
    //Attributes of a Peak object
	var peakId; var xValue; var multiplicity; var height; var atomRefs; var peakShape; 
    //Attributes of a Bond object
	var bondId; var type; var source; var target; 
	var stereo=null;
	
	
	//Create the atoms and put it in the Array of Atoms of the cmlObject.
	for(var k=0;k<lenAtom;k++){
		for(attributeIndice in atoms.item(k).attributes){
			var attributeName=atoms.item(k).attributes[attributeIndice].name;
			var attributeValue=atoms.item(k).attributes[attributeIndice].value;
			switch(attributeName){
			case "id":
				atomId=attributeValue;
				break;
			case "elementType":
				elementType=attributeValue;
				break;
			case "x2":
				x=parseFloat(attributeValue);
				break;
			case "y2":
				y=parseFloat(attributeValue);
				break;
			case "formalCharge":
				charge=attributeValue;
				break;
			case "hydrogenCount":
				hydrogenCount=attributeValue;
				break;
			case "isotopeNumber":
				isotopeNumber=attributeValue;
				break;
			}
		}
		var currentAtom=new specview.model.Atom(elementType,x,y,charge);//Create the atom
		currentAtom.setInnerIdentifier(atomId);//Set its inner identifier..a1,a2...
		currentAtom.peakMap[currentAtom.getInnerIdentifier()]=new Array();//Prepare the array for the peaks..[a1=[] ; a2=[] ...]
        //this.logger.info("atom id "+currentAtom.getInnerIdentifier())
		nmrData.ArrayOfAtoms[currentAtom.getInnerIdentifier()]=currentAtom;//Set the ArrayOfAtoms attribute of the graphical object
	}

	
	//Create the bonds with the stereo and put it in the Array of bonds of the cmlObject
	for(var k=0;k<lenBond;k++){
		var tag=cNodes[k].nodeName;
		if(tag!="#text"){//IN ORDER TO AVOID DOING USELESS OPERQTIONS ON TEXT BUT WHAT THE  FUCK IS THAT??
			if(k<cNodes.length-2){
				var nextTag=cNodes[k+2].nodeName;// Not very smart to proceed like this. Must be a beter way.
			}else{var nextTag=null;}
			if(tag=="bond"){// TO AVOID DOING USELESS OPERATIONS ON BONDSTEREO
				for(attributeIndice in cNodes[k].attributes){
					var attributeName=cNodes[k].attributes[attributeIndice].name;
					var attributeValue=cNodes[k].attributes[attributeIndice].value;
					switch(attributeName){
					case "id":
						bondId=attributeValue;
						break;
					case "atomRefs2":
						source=attributeValue.split(" ")[0];
						target=attributeValue.split(" ")[1];
						break;
					case "order":
						type=attributeValue;
						if(type=="S"){type=specview.io.mdl.SINGLE_BOND;}
						else if(type=="D"){type=specview.io.mdl.DOUBLE_BOND;}				
						else if(type=="T"){type=specview.io.mdl.TRIPLE_BOND;}
						break;
					}
				}
			}
			if(nextTag=="bondStereo"){//DO NOT FORGET TO ADD THE REMAING STEREOSPECIFITIY
				stereo=cNodes[k+2].attributes[0].value;
				if(stereo=="CML:W"){
					stereo=specview.io.mdl.SINGLE_BOND_UP;
				}else if(stereo="CML:H"){
					stereo=specview.io.mdl.SINGLE_BOND_DOWN;
				}
			}
			stereo=(stereo ? stereo : specview.io.mdl.NOT_STEREO);
			var currentBond=new specview.io.mdl.createBond(type, stereo, nmrData.ArrayOfAtoms[source],nmrData.ArrayOfAtoms[target]);
			nmrData.ArrayOfBonds[bondId]=currentBond;//Set the ArrayOfBonds of the graphical object
			stereo=null;//Reset the value of stereo!
		}

	}

	
	//create the peaks and put it in the Array of object of the cmlObject
	for(var k=0;k<lenPeak;k++){
		for(attributeIndice in peaks.item(k).attributes){
			var attributeName=peaks.item(k).attributes[attributeIndice].name;
			var attributeValue=peaks.item(k).attributes[attributeIndice].value;
			switch(attributeName){
			case "xValue":
				xValue=parseFloat(attributeValue);
				break;
			case "xUnits":
				xUnits=attributeValue;
				break;
			case "peakShape":
				peakShape=attributeValue;
				break;
			case "peakMultiplicity":
				multiplicity=attributeValue;
				break;
			case "id":
				peakId=attributeValue;
				break;
			case "atomRefs":
				atomRefs=attributeValue.split(" ");
				break;
			case "height":
				height=attributeValue;
				break;
			default:
				break;
			}
		}
		//refer the atoms to the peaks
		for(at in atomRefs){
			var atomRef=atomRefs[at];
			for(s in nmrData.ArrayOfAtoms){
				var atom=nmrData.ArrayOfAtoms[s];
				var innerIdentifier=atom.innerIdentifier;
				if(atomRef==innerIdentifier){
					atom.peakMap[atomRef].push(peakId);
				}
			}
		}
		height = height ? height : 50 ; // Should be more precise on the height
		nmrData.ArrayOfPeaks[peakId]=new specview.model.Peak(xValue,height,peakId,atomRefs,multiplicity);
	}

	var moleculeTitle; var moleculeId;
	//Create the molecule name : Set the name and id.
	moleculeTitle=mol.item(0).attributes[0].value;
	moleculeId=mol.item(0).attributes[1].value;
	nmrData.molecule=new specview.model.Molecule(moleculeTitle);
	
	//Add the atoms to the molecule
	for(k in nmrData.ArrayOfAtoms){
		nmrData.molecule.addAtom(nmrData.ArrayOfAtoms[k]);
	}
	//Add the bonds to the molecule
	for(k in nmrData.ArrayOfBonds){
		nmrData.molecule.addBond(nmrData.ArrayOfBonds[k]);
	}
	
	return nmrData;
	
};

/**
 * Logging object.
 */
specview.io.SpectrumCMLParser.logger = goog.debug.Logger.getLogger('specview.io.SpectrumCMLParser');
