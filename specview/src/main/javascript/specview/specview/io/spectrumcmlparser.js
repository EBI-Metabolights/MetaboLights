
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
specview.io.SpectrumCMLParser.logger = goog.debug.Logger.getLogger('specview.io.SpectrumCMLParser');


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
specview.io.SpectrumCMLParser.parseDocument=function(NMRdataObject,XMLdoc,navigatorName){
	var molToBuild=new Array();
	
	var IE=navigatorName=="Microsoft Internet Explorer" ? true : false;
		
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
//	specview.io.SpectrumCMLParser.logger.info("metadataList: "+XMLdoc.getElementsByTagName("molecule").length)
	var experimentType=XMLdoc.getElementsByTagName("metadataList")[0].childNodes[1].attributes[0].value;
	var nmrData=NMRdataObject;
		
	var THEMOLECULENAME="";var THEMOLECULEID="";
	var listOfMolecules=new Array();
	
	
	if(specview.util.Utilities.startsWith("ms",experimentType)){
		nmrData.experienceType="ms";
//		alert(navigatorName)
//		alert(XMLdoc.getElementsByTagName("reactant")[0].childNodes[0].attributes[0].value)
		if(IE){
			THEMOLECULENAME=XMLdoc.getElementsByTagName("reactant")[0].childNodes[0].attributes[0].value;			
		}else{
			THEMOLECULENAME=XMLdoc.getElementsByTagName("reactant")[0].childNodes[1].attributes[0].value;	
		}
	}else if(specview.util.Utilities.startsWith("nmr",experimentType)){
		nmrData.experienceType="nmr";
		THEMOLECULENAME=XMLdoc.getElementsByTagName('molecule').item(0).attributes[0].value;
		THEMOLECULEID=XMLdoc.getElementsByTagName('molecule').item(0).attributes[1].value;
	}
//	specview.io.SpectrumCMLParser.logger.info("the molecule name: "+THEMOLECULENAME)
	var molecules=XMLdoc.getElementsByTagName("moleculeList");
//	alert(molecules.length);
	if(nmrData.experienceType=="ms"){
		if(molecules[0]!=undefined){//Stephen files: moleculeList is the tag holding all the information about the molecules
			listOfMolecules=molecules[0].childNodes;	
		}else{//2008 cml files: moleculeList does not exist. INstead, we have productList
//			listOfMolecules=XMLdoc.getElementsByTagName('productList')[0].childNodes;
			listOfMolecules=XMLdoc.getElementsByTagName('cml:molecule');
		}
	}else{
//		alert(XMLdoc.getElementsByTagName("molecule")[0].childNodes)
		listOfMolecules[0]=XMLdoc.getElementsByTagName("molecule");
//		alert(listOfMolecules[1])
	}
//	alert(listOfMolecules.length);
	for(var molecule=0;molecule<listOfMolecules.length;molecule++){
		var MS = listOfMolecules[molecule] instanceof Element;
		var NMR = listOfMolecules[molecule] instanceof HTMLCollection;
		var moleculeName;
		var moleculeNode;
//		alert(listOfMolecules[molecule])
		if(MS){
//			alert(moleculeName)
			moleculeNode=listOfMolecules[molecule];
//			specview.io.SpectrumCMLParser.logger.info("line 94 spec09: "+moleculeNode.nodeName)
		}else if(NMR){
			moleculeNode=listOfMolecules[molecule][0];
		}
		if(MS || NMR){
//			alert(listOfMolecules[molecule][0].childNodes.length)
			if(MS){
//				alert(moleculeName)
				moleculeName=moleculeNode.attributes[0].value;
			}else if(NMR){
				moleculeName=THEMOLECULENAME;
			}
			var moleculeBeingBuilt=new specview.model.Molecule(moleculeName);
			var atomArray=moleculeNode.childNodes[1].childNodes;
			var bondArray=moleculeNode.childNodes[3].childNodes;			
			var lenAtom=atomArray.length;var lenBond=bondArray.length;
			
			//Create the atoms and put it in the Array of Atoms of the cmlObject.
			for(var k=0;k<lenAtom;k++){
				for(attributeIndice in atomArray.item(k).attributes){
					var attributeName=atomArray.item(k).attributes[attributeIndice].name;
					var attributeValue=atomArray.item(k).attributes[attributeIndice].value;
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
//			alert(lenBon)
			//Create the bonds with the stereo and put it in the Array of bonds of the cmlObject
			for(var k=0;k<lenBond;k++){
				var tag=bondArray[k].nodeName;
				if(tag!="#text"){
					if(k<bondArray.length-2){
						var nextTag=bondArray[k+2].nodeName;// Not very smart to proceed like this. Must be a beter way.
					}else{var nextTag=null;}
					if(tag=="bond" || tag=="cml:bond"){// TO AVOID DOING USELESS OPERATIONS ON BONDSTEREO
						for(attributeIndice in bondArray[k].attributes){
							var attributeName=bondArray[k].attributes[attributeIndice].name;
							var attributeValue=bondArray[k].attributes[attributeIndice].value;
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
						stereo=bondArray[k+2].attributes[0].value;
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
//					specview.io.SpectrumCMLParser.logger.info("bond: "+currentBond);
				}
			}
			
			
			if(nmrData.experienceType=="ms"){
//				alert(moleculeName)
//				specview.io.SpectrumCMLParser.logger.info(moleculeName);
				if(moleculeName==THEMOLECULENAME){
					nmrData.molecule=new specview.model.Molecule(THEMOLECULENAME);
				}
				var secondaryMolecule=new specview.model.Molecule(moleculeName)
				for(k in ArrayOfAtoms){
					if(ArrayOfAtoms[k].coord.x!=0 && ArrayOfAtoms[k].coord.y!=0){
						if(moleculeName==THEMOLECULENAME){
							nmrData.molecule.addAtom(ArrayOfAtoms[k]);
						}
						secondaryMolecule.addAtom(ArrayOfAtoms[k]);
					}
				}
				//Add the bonds to the molecule
//				alert("how many bonds: "+ArrayOfBonds.length);
				for(k in ArrayOfBonds){
					if(moleculeName==THEMOLECULENAME){
						nmrData.molecule.addBond(ArrayOfBonds[k]);
					}
					secondaryMolecule.addBond(ArrayOfBonds[k]);
				//	specview.io.SpectrumCMLParser.logger.info(ArrayOfBonds[k]);
				}
				molecule.fragmentId=moleculeName;
				nmrData.ArrayOfSecondaryMolecules[moleculeName]=secondaryMolecule;
				
//				alert(secondaryMolecule)
				//specview.io.SpectrumCMLParser.logger.info("molecule: "+secondaryMolecule.name)
				ArrayOfAtoms=new Array();
				ArrayOfBonds=new Array();
			}
		}
	}
	
	if(nmrData.experienceType=="nmr"){
		nmrData.molecule=new specview.model.Molecule(THEMOLECULENAME);
		nmrData.molecule.molecule_id=THEMOLECULEID;
		for(k in ArrayOfAtoms){
			if(ArrayOfAtoms[k].coord.x!=0 && ArrayOfAtoms[k].coord.y!=0){
				nmrData.molecule.addAtom(ArrayOfAtoms[k]);	
			}
		}
		for(k in ArrayOfBonds){
			nmrData.molecule.addBond(ArrayOfBonds[k]);
		}
	}
	
	var peaks=XMLdoc.getElementsByTagName("peakList")[0].childNodes;//Peaks are the same for MS and NMR
	var lenPeak=peaks.length;
//	alert("peakk: "+lenPeak)
	
	for(k in peaks){
		if(peaks[k] instanceof Element){
			var peak=peaks[k];
			var moleculeRef=null;
			var isThereAmoleculeAssignedToThePeak=(peak.childNodes[1]==undefined) ? false : true;
			if(isThereAmoleculeAssignedToThePeak){
				moleculeRef=peak.childNodes[1].attributes[0].value;			
			}		
			for(attributeIndice in peak.attributes){
				var attributeName=peak.attributes[attributeIndice].name;
				var attributeValue=peak.attributes[attributeIndice].value;
//				specview.io.SpectrumCMLParser.logger.info(attributeName);
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
					moleculeRef=attributeValue;
					break;
				case "yValue":
					height=attributeValue;
					break;
				default:
					break;
				}
			}
			
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
			var peakToBuild=new specview.model.Peak(xValue,height,peakId,atomRefs,multiplicity,moleculeRef,xUnits,yUnits);
//			specview.io.SpectrumCMLParser.logger.info("spectrumcmlparser09.js: "+peakToBuild);
			molRefs=null;atomRefs=null;
			nmrData.ArrayOfPeaks[peakId]=peakToBuild;
			goog.array.insert(ArrayOfPeaks,peakToBuild);
		}
	}
	
        //Create a spectrum
       // this.logger.info("ArrayOfPeaks "+ArrayOfPeaks.length);
        var spec= new specview.model.Spectrum(nmrData.molecule, ArrayOfPeaks);
        nmrData.spectrum=spec;
        nmrData.spectrum.xUnit=xUnits;
        nmrData.spectrum.yUnit=yUnits;
	return nmrData;
	
};




