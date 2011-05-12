/** 
 * Copyright 2010 Paul Novak (paul@wingu.com)
 *           2011 Mark Rijnbeek (markr@ebi.ac.uk)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @fileoverview io utility functions and factory methods for MDL formats
 */

goog.provide('specview.io.mdl');

goog.require('goog.i18n.DateTimeFormat');
goog.require('goog.string');
goog.require('specview.model.Molecule');
goog.require('specview.model.Bond');
goog.require('specview.model.Atom');
goog.require('goog.debug.Logger');

/** @const */ specview.io.mdl.SINGLE_BOND = 1;
/** @const */ specview.io.mdl.DOUBLE_BOND = 2;
/** @const */ specview.io.mdl.TRIPLE_BOND = 3;
/** @const */ specview.io.mdl.AROMATIC_BOND = 4;
/** @const */ specview.io.mdl.SINGLE_OR_DOUBLE = 5;
/** @const */ specview.io.mdl.SINGLE_OR_AROMATIC = 6;
/** @const */ specview.io.mdl.DOUBLE_OR_AROMATIC = 7;
/** @const */ specview.io.mdl.ANY = 8;
/** @const */ specview.io.mdl.TRIPLE_BOND = 3;

/** @const */ specview.io.mdl.NOT_STEREO = 0;
/** @const */ specview.io.mdl.SINGLE_BOND_UP = 1;
/** @const */ specview.io.mdl.SINGLE_BOND_UP_OR_DOWN = 4;
/** @const */ specview.io.mdl.SINGLE_BOND_DOWN = 6;

/**
 * maps bond class to mdl molfile bond type code
 * @param{specview.model.Bond} bond
 * @return{number}
 */
specview.io.mdl.getTypeCode = function(bond){
	if (bond.aromatic){
		return specview.io.mdl.AROMATIC_BOND;
	}
	if (bond.order == specview.model.Bond.ORDER.SINGLE){
		return specview.io.mdl.SINGLE_BOND;
	}
	if (bond.order == specview.model.Bond.ORDER.DOUBLE){
		return specview.io.mdl.DOUBLE_BOND;
	}
	if (bond.order == specview.model.Bond.ORDER.TRIPLE){
		return specview.io.mdl.TRIPLE_BOND;
	}
        throw new Error("Invalid bond type [" + bond + "]");
};

/**
 * maps bond class to mdl molfile stereo type code
 * @param {specview.model.Bond} bond
 * @return {number}
 */
specview.io.mdl.getStereoCode = function(bond){
	if (bond.stereo == specview.model.Bond.STEREO.UP){
		return specview.io.mdl.SINGLE_BOND_UP;
	}
	if (bond.stereo == specview.model.Bond.STEREO.DOWN){
		return specview.io.mdl.SINGLE_BOND_DOWN;
	}
	if (bond.stereo == specview.model.Bond.STEREO.UP_OR_DOWN){
		return specview.io.mdl.SINGLE_BOND_UP_OR_DOWN;
	}
	return specview.io.mdl.NOT_STEREO;
};

/**
 * factory method for bonds
 * 
 * @param{number}type mdl type code
 * @param{number}stereo mdl stereo type code
 * @param{specview.model.Atom} source atom at source end of bond
 * @param{specview.model.Atom} target atom at target end of bond
 * 
 * @return{specview.model.Bond}
 */
specview.io.mdl.createBond = function(type, stereo, source, target) {
//	alert("in create bond : "+type+" "+stereo+" "+source+" "+target);
	switch (type) {
	case specview.io.mdl.SINGLE_BOND:
		switch (stereo) {
		case specview.io.mdl.NOT_STEREO:
//			alert("NO STEREO")
			return new specview.model.Bond(source, target);
		case specview.io.mdl.SINGLE_BOND_UP:
//			alert("UP STEREO: "+stereo)
			return new specview.model.Bond(source, target, specview.model.Bond.ORDER.SINGLE, specview.model.Bond.STEREO.UP);
		case specview.io.mdl.SINGLE_BOND_UP_OR_DOWN:
//			alert("UP DOWN STEREO: "+stereo)
			return new specview.model.Bond(source, target, specview.model.Bond.ORDER.SINGLE, specview.model.Bond.STEREO.UP_OR_DOWN);
		case specview.io.mdl.SINGLE_BOND_DOWN:
//			alert("DOWN STEREO: "+stereo+"\n"+ new specview.model.Bond(source, target, specview.model.Bond.ORDER.SINGLE, specview.model.Bond.STEREO.DOWN));
			return new specview.model.Bond(source, target, specview.model.Bond.ORDER.SINGLE, specview.model.Bond.STEREO.DOWN);
		default:
			throw new Error("invalid bond type/stereo [" + type + "]/["
					+ stereo + "]");
		};
	case specview.io.mdl.DOUBLE_BOND:
		return new specview.model.Bond(source, target, specview.model.Bond.ORDER.DOUBLE);
	case specview.io.mdl.TRIPLE_BOND:
		return new specview.model.Bond(source, target, specview.model.Bond.ORDER.TRIPLE);
	case specview.io.mdl.AROMATIC_BOND:
		var bond = new specview.model.Bond(source, target);
                bond.aromatic = true;
                return bond;
	case specview.io.mdl.SINGLE_OR_DOUBLE:
		throw new Error("type not implemented [" + type + "]");
	case specview.io.mdl.SINGLE_OR_AROMATIC:
		throw new Error("type not implemented [" + type + "]");
	case specview.io.mdl.DOUBLE_OR_AROMATIC: 
		throw new Error("type not implemented [" + type + "]");
	case specview.io.mdl.ANY: 
		throw new Error("type not implemented [" + type + "]");
	default:
		throw new Error("invalid bond type/stereo [" + type + "]/[" + stereo
				+ "]");
	};
};


/**
 * convert a mdl block string to a molecule
 *  
 * @param {string} molfile string to convert
 * @return {specview.model.Molecule}
 */
specview.io.mdl.readMolfile = function(molfile) {

	
//	alert(molfile);
	var lineDelimiter = molfile.indexOf("\r\n") > 0 ? "\r\n" : "\n";
	var mol_lines = molfile.split(lineDelimiter);
	var name = mol_lines[0];
	var mol = new specview.model.Molecule(name);
	var atom_count = parseInt(mol_lines[3].substr(0, 3), 10);//MANDATORY
	var bond_count = parseInt(mol_lines[3].substr(3, 3), 10);//MANDATORY
	for ( var i = 1; i <= atom_count; i++) {
		//            1         2         3         4         5         6  
		//  01234567890123456789012345678901234567890123456789012345678901234567890
		// "xxxxx.xxxxyyyyy.yyyyzzzzz.zzzz aaaddcccssshhhbbbvvvHHHrrriiimmmnnneee"
		var line = mol_lines[i + 3];
		var x=parseFloat(line.substr(0,10));
		var y=parseFloat(line.substr(10,10));
		var symbol = line.substr(31,3).replace(/(^\s*)|(\s*$)/g, "");
		var ctfile_ccc=parseInt(line.substr(36,3), 10);
		var charge = 0;
		if (ctfile_ccc == 0) {
		} else if (ctfile_ccc == 1) {
			charge = 3;
		} else if (ctfile_ccc == 2) {
			charge = 2;
		} else if (ctfile_ccc == 3) {
			charge = 1;
		} else if (ctfile_ccc == 4) {
			// TODO support doublet radical
		} else if (ctfile_ccc == 5) {
			charge = -1;
		} else if (ctfile_ccc == 6) {
			charge = -2;
		} else if (ctfile_ccc == 7) {
			charge = -3;
		}
		//this.logger.info("atom "+symbol+" charge "+charge)
		var atom = new specview.model.Atom(symbol, x, y, charge);
		mol.addAtom(atom);
	}

	for ( var i = 1; i <= bond_count; i++) {//MANDATORY
		var line = mol_lines[i + 3 + atom_count];

		//            1         2         3         4         5         6  
		//  01234567890123456789012345678901234567890123456789012345678901234567890
		//  111222tttsssxxxrrrccc
		var sourceAtom = mol.getAtom(parseInt(line.substr(0,3), 10) - 1);
		var targetAtom = mol.getAtom(parseInt(line.substr(3,3), 10) - 1);
		var bondType = bondType = parseInt(line.substr(6,3), 10);
		var bondStereoType = parseInt(line.substr(9,3), 10);

		var targetAtom = mol.getAtom(parseInt(line.substr(3, 3), 10) - 1);

		var bondType = parseInt(line.substr(6, 3), 10);
		var bondStereoType = parseInt(line.substr(9, 3), 10);
		//this.logger.info("bond type="+bondType+" stereo="+bondStereoType)
    	var bond = specview.io.mdl.createBond(bondType, bondStereoType,sourceAtom, targetAtom);
		mol.addBond(bond);

	}

	// Read M CHG
	var i = 4 + atom_count + bond_count, il = mol_lines.length;
	var superseded = false;
	
	//For the charge.
	while (true) {
		var line = mol_lines[i++];
		if (i == il || line.indexOf("M  END") >= 0) {
			break;
		}
		if (line.indexOf("M  CHG") >= 0) {
			/*
			 * TODO Charge [Generic] M CHGnn8 aaa vvv ... vvv: -15 to +15.
			 * Default of 0 = uncharged atom. When present, this property
			 * supersedes all charge and radical values in the atom block,
			 * forcing a 0 charge on all atoms not listed in an M CHG or M RAD
			 * line.
			 * 
			 */
			if (!superseded) {
				for ( var j = 0, jl = mol.countAtoms(); j < jl; j++) {
					mol.getAtom(j).charge = 0;
				}
				superseded = true;
			}
			var nn = parseInt(line.substr(6, 3), 10);
			for ( var k = 0; k < nn; k++) {
				var atomNum = parseInt(line.substr(10 + 8 * k, 3), 10);
				// console.debug(atomNum);
				var charge = parseInt(line.substr(14 + 8 * k, 3), 10);
				// console.debug(charge);
				mol.getAtom(atomNum - 1).charge = charge;
			}

		}
		// console.debug(line);
	}

	// TODO parse Charge, SuperAtom groups, Properties....
	return mol;

};
goog.exportSymbol('specview.io.mdl.readMolfile', specview.io.mdl.readMolfile);


/**
 * convert Molecule to molfile string
 * 
 * @param {specview.model.Molecule} mol molecule to write
 * 
 * @return{string}
 */
specview.io.mdl.writeMolfile = function(mol) {
	var molFile = "";
	var headerBlock = "";
	var countsLine = "";
	var atomBlock = "";
	var bondBlock = "";

	// Header block
	// Line 1: Molecule name
	// Line 2: This line has the format:
	// IIPPPPPPPPMMDDYYHHmmddSSssssssssssEEEEEEEEEEEERRRRRR
	// Line 3: A line for comments. If no comment is entered, a blank line must
	// be present.
	var now = new Date();
	var line1 = mol.name + "\n";
	var fmt = new goog.i18n.DateTimeFormat('mmddyyHHMM');
	var line2 = " " + "JChemHub" + fmt.format(now) + "\n";
	var line3 = "\n";
	var headerBlock = line1 + line2 + line3;

	// Counts line
	var atomCount = (goog.string.repeat(" ", 3) + mol.countAtoms()).slice(-3);
	var bondCount = (goog.string.repeat(" ", 3) + mol.countBonds()).slice(-3);

	// TODO complete lll, fff, ccc, sss
	countsLine = atomCount + bondCount + "  0  0  0  0            999 V2000\n";

	// Atom block
	for (i = 0; i < mol.countAtoms(); i++) {
		var atom = mol.getAtom(i);
		var xPos = (goog.string.repeat(" ", 10) + atom.coord.x.toFixed(4)).slice(-10);
		var yPos = (goog.string.repeat(" ", 10) + atom.coord.y.toFixed(4)).slice(-10);
		var zPos = (goog.string.repeat(" ", 10) + (0).toFixed(4)).slice(-10);
		var atomSymbol = (goog.string.repeat(" ", 3) + atom.symbol).slice(-3);

		// TODO: fill in more details on rest of atom line
		var filler = "  0  0  0  0  0  0  0  0  0  0  0  0";
		atomBlock += xPos + yPos + zPos + atomSymbol + "\n";
	}

	// Bond Block
	for (i = 0; i < mol.countBonds(); i++) {
		var bond = mol.getBond(i);
		var firstAtomNumber = mol.indexOfAtom(bond.source) + 1;
		var firstAtomString = (goog.string.repeat(" ", 3) + firstAtomNumber)
				.slice(-3);
		var secondAtomNumber = mol.indexOfAtom(bond.target) + 1;
		var secondAtomString = (goog.string.repeat(" ", 3) + secondAtomNumber)
				.slice(-3);
		var bondTypeString = (goog.string.repeat(" ", 3) + specview.io.mdl.getTypeCode(bond))
				.slice(-3);
		var stereoTypeString = (goog.string.repeat(" ", 3) + specview.io.mdl.getStereoCode(bond))
				.slice(-3);
		bondBlock += firstAtomString + secondAtomString + bondTypeString
				+ stereoTypeString + "\n";
	}

	molFile = headerBlock + countsLine + atomBlock + bondBlock + "M  END\n";;
	// alert(molFile);
	return molFile;

};

goog.exportSymbol('specview.io.mdl.writeMolfile', specview.io.mdl.writeMolfile);


specview.io.mdl.logger = goog.debug.Logger.getLogger('specview.io.mdl');
