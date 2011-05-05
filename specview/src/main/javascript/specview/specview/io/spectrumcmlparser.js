
goog.provide('specview.io.SpectrumCMLParser');

goog.require('goog.dom.xml');
goog.require('goog.debug.Logger');

goog.require('specview.model.Peak');
goog.require('specview.model.Molecule');
goog.require('specview.model.Atom');
goog.require('specview.model.Bond');
goog.require('specview.model.Spectrum');
goog.require('specview.model.NMRdata');
goog.require('specview.util.Utilities');

specview.io.SpectrumCMLParser=function(){}



/**
 * Logging object.
 */
specview.io.SpectrumCMLParser.prototype.logger = goog.debug.Logger.getLogger('specview.io.SpectrumCMLParser');


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
specview.io.SpectrumCMLParser.parseDocument=function(NMRdataObject,XMLdoc){
	var molToBuild=new Array();
		
    //Attributes of an Atom object
	var atomId; var elementType; var x; var y; var charge; var hydrogenCount; 
    //Attributes of a Peak object
	var peakId; var xValue; var multiplicity; var height; var atomRefs; var peakShape; var maxPValue=0; var molRefs; var xUnits; var yUnits;
    //Attributes of a Bond object
	var bondId; var type; var source; var target; 
	var stereo=null;
	
    var ArrayOfAtoms=new Array(); // Keys are the inner atom id. Values are the atom objects
	var ArrayOfBonds=new Array();
	var ArrayOfPeaks=new Array();
	var mol="";
	var experimentType=XMLdoc.getElementsByTagName("metadataList")[0].childNodes[1].attributes[0].value;
	var nmrData=NMRdataObject;

	var peaks=XMLdoc.getElementsByTagName("peak");//Peaks are the same for MS and NMR
	var lenPeak=peaks.length;

	if(specview.util.Utilities.startsWith("nmr",experimentType)){
		nmrData.experienceType="nmr";
		mol=XMLdoc.getElementsByTagName("molecule");// The information regarding the molecule
		molToBuild[0]=new Array();
		molToBuild[0]["atoms"]=XMLdoc.getElementsByTagName("atom");
		molToBuild[0]["bonds"]=XMLdoc.getElementsByTagName("bondArray")[0].childNodes;
	}else if(specview.util.Utilities.startsWith("ms",experimentType)){
		nmrData.experienceType="ms";

		reactantList=XMLdoc.getElementsByTagName("reactantList")[0].childNodes[1].childNodes[1].childNodes;//node list of Atom and Bond
		molToBuild[0]=new Array();
		molToBuild[0]["atoms"]=reactantList[1].childNodes;
		molToBuild[0]["bonds"]=reactantList[3].childNodes;
		molToBuild[0]["id"]="m0";
		
		productList=XMLdoc.getElementsByTagName("productList")[0].childNodes;//reactantList;HTMLcollection; //contains all the products
		for(var product=0;product<productList.length;product++){
			if(productList[product] instanceof Element){
				var indice=molToBuild.length;
				molToBuild[indice]=new Array();
				molToBuild[indice]["atoms"]=productList[product].childNodes[1].childNodes[1].childNodes
				molToBuild[indice]["bonds"]=productList[product].childNodes[1].childNodes[3].childNodes
				molToBuild[indice]["id"]=productList[product].childNodes[1].attributes[0].value;				
//				alert("last entered: "+indice+"\natoms: "+molToBuild[indice]["atoms"].length+"\n bonds: "+molToBuild[indice]["bonds"].length+"\nid: "+productList[product].childNodes[1].attributes[0].value);
			}			
		}
	}

	for(var molecule=0;molecule<molToBuild.length;molecule++){
		var atoms=molToBuild[molecule]["atoms"]; var cNodes=molToBuild[molecule]["bonds"];
		var moleculeId="m0";//only if MS
		if(nmrData.experienceType=="ms"){moleculeId=molToBuild[molecule]["id"];}
		var lenAtom=atoms.length;var lenBond=cNodes.length;
		
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

	        if (charge=="0")
	            charge=null; // prevents charge rendering on zero charge
	            
			var currentAtom=new specview.model.Atom(elementType,x,y,charge);//Create the atom
			currentAtom.setInnerIdentifier(atomId);//Set its inner identifier..a1,a2...
			currentAtom.peakMap[currentAtom.getInnerIdentifier()]=new Array();//Prepare the array for the peaks..[a1=[] ; a2=[] ...]
	        //this.logger.info("atom id "+currentAtom.getInnerIdentifier())
			/**
			 * THAT IS WEIRD
			 */
			ArrayOfAtoms[currentAtom.getInnerIdentifier()]=currentAtom;//Set the ArrayOfAtoms attribute of the graphical object
			nmrData.ArrayOfAtoms[currentAtom.getInnerIdentifier()]=currentAtom;
			
		}
		
		//Create the bonds with the stereo and put it in the Array of bonds of the cmlObject
		for(var k=0;k<lenBond;k++){
			var tag=cNodes[k].nodeName;
			if(tag!="#text"){
				if(k<cNodes.length-2){
					var nextTag=cNodes[k+2].nodeName;// Not very smart to proceed like this. Must be a beter way.
				}else{var nextTag=null;}
				if(tag=="bond" || tag=="cml:bond"){// TO AVOID DOING USELESS OPERATIONS ON BONDSTEREO
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
							if(type=="S"){type=specview.model.Bond.ORDER.SINGLE;}
							else if(type=="D"){type=specview.model.Bond.ORDER.DOUBLE;}				
							else if(type=="T"){type=specview.model.Bond.ORDER.TRIPLE;}
							break;
						}
					}
				}
				if(nextTag=="bondStereo"){//DO NOT FORGET TO ADD THE REMAING STEREOSPECIFITIY
					stereo=cNodes[k+2].attributes[0].value;

					if(stereo=="CML:W"){
						stereo=specview.model.Bond.STEREO.UP;
					}else if(stereo="CML:H"){
						stereo=specview.model.Bond.STEREO.DOWN;
					}
				}
				stereo=(stereo ? stereo : specview.model.Bond.NOT_STEREO);
	            var currentBond=new specview.model.Bond (ArrayOfAtoms[source],ArrayOfAtoms[target],type,stereo );
	            /**
	             * THAT IS WEIRD
	             */
	            ArrayOfBonds[bondId]=currentBond;
				nmrData.ArrayOfBonds[bondId]=currentBond;//Set the ArrayOfBonds of the graphical object
				stereo=null;//Reset the value of stereo!
//				specview.io.SpectrumCMLParser.logger.info("bond: "+currentBond);
			}
		}
		
		if(nmrData.experienceType=="ms"){
			if(molToBuild[molecule]["id"]=="m0"){
				nmrData.molecule=new specview.model.Molecule("m0");
			}
			var secondaryMolecule=new specview.model.Molecule(molToBuild[molecule]["id"])
			for(k in ArrayOfAtoms){
				if(molToBuild[molecule]["id"]=="m0"){
					nmrData.molecule.addAtom(ArrayOfAtoms[k]);
				}
				secondaryMolecule.addAtom(ArrayOfAtoms[k]);
			}
			//Add the bonds to the molecule
//			alert("how many bonds: "+ArrayOfBonds.length);
			for(k in ArrayOfBonds){
				if(molToBuild[molecule]["id"]=="m0"){
					nmrData.molecule.addBond(ArrayOfBonds[k]);
				}
				secondaryMolecule.addBond(ArrayOfBonds[k]);
			//	specview.io.SpectrumCMLParser.logger.info(ArrayOfBonds[k]);
			}
			molecule.fragmentId=molToBuild[molecule]["id"];
			nmrData.ArrayOfSecondaryMolecules[molToBuild[molecule]["id"]]=secondaryMolecule;
//			alert(secondaryMolecule)
			ArrayOfAtoms=new Array();
			ArrayOfBonds=new Array();
		}

	}

	
	if(nmrData.experienceType=="nmr"){
		//Create the molecule name : Set the name and id.
		var moleculeTitle; var molecId;
		moleculeTitle=mol.item(0).attributes[0].value;
		molecId=mol.item(0).attributes[1].value;
		nmrData.molecule=new specview.model.Molecule(moleculeTitle);
		nmrData.molecule.molecule_id=molecId;
//		alert(nmrData.molecule.name+" "+nmrData.molecule.molecule_id);
		//Add the atoms to the molecule
		for(k in ArrayOfAtoms){
			nmrData.molecule.addAtom(ArrayOfAtoms[k]);
		}
		//Add the bonds to the molecule
		for(k in ArrayOfBonds){
			nmrData.molecule.addBond(ArrayOfBonds[k]);
		}
	}

//	alert(nmrData.molecule.countBonds())

	
	//create the peaks and put it in the Array of object of the cmlObject
	for(var k=0;k<lenPeak;k++){
		for(attributeIndice in peaks.item(k).attributes){
			var attributeName=peaks.item(k).attributes[attributeIndice].name;
			var attributeValue=peaks.item(k).attributes[attributeIndice].value;
			switch(attributeName){
			case "xValue":
				xValue=parseFloat(attributeValue);
				maxPValue=(xValue>maxPValue ? xValue : maxPValue);
				break;
			case "xUnits":
				xUnits=specview.util.Utilities.getStringAfterCharacter(attributeValue,":");
				break;
			case "yUnits":
				yUnits=specview.util.Utilities.getStringAfterCharacter(attributeValue,":");
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
			case "moleculeRefs" :
				molRefs=attributeValue;
				break;
			case "yValue":
				height=attributeValue;
				break;
			default:
				break;
			}
		}
		//refer the atoms to the peaks
		if(nmrData.experienceType=="nmr"){
			for(at in atomRefs){
				var atomRef=atomRefs[at];
				for(s in ArrayOfAtoms){
					var atom=ArrayOfAtoms[s];
					var innerIdentifier=atom.innerIdentifier;
					if(atomRef==innerIdentifier){
						atom.peakMap[atomRef].push(peakId);
					}
				}
			}
		}
		height = height ? height : 50 ; // Should be more precise on the height	
		/**
		 * THAT IS WEIRD
		 */
		var maxL=23.5;
		var peak=new specview.model.Peak(xValue,height,peakId,atomRefs,multiplicity,molRefs,xUnits,yUnits);
		molRefs=null;
		atomRefs=null;
		nmrData.ArrayOfPeaks[peakId]=peak;
		goog.array.insert(ArrayOfPeaks,peak);
	}

        //Create a spectrum
       // this.logger.info("ArrayOfPeaks "+ArrayOfPeaks.length);
        var spec= new specview.model.Spectrum(nmrData.molecule, ArrayOfPeaks);
//        spec.setXvalues(maxPValue);
        nmrData.spectrum=spec;
      
    for(k in nmrData.ArrayOfPeaks){
    //	alert(nmrData.ArrayOfPeaks[k])
    }
	return nmrData;
	
};




