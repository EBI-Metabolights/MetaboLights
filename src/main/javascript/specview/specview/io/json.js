/**
 * @license Copyright 2010 Paul Novak (paul@wingu.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * @author paul@wingu.com (Paul Novak)
 */

/**
 * @fileoverview io utility functions and factory methods for JSON formats
 */
goog.provide('specview.io.json');

goog.require('specview.model.Molecule');
goog.require('specview.model.Bond');
goog.require('specview.model.Atom');
goog.require('goog.math.Coordinate');
goog.require('goog.json');
goog.require('goog.array');


/**
 * uses JSON.parse and .stringify; needs def for IE and ?? This allows for a
 * JSON external representation that uses bond atom-index instead of atom
 * objects. So, there are 3 types of things of import here: 1. The actual
 * Molecule object (typically mol here) 2. The object (typically jmol here) in
 * which bond's atom objects are recast as atom indexes 3. The string
 * representaion of jmol There is not use for the JSON string represention of
 * the actual Molecule object.
 */

/**
 * enum for bond types
 * 
 * @enum {string}
 */ 
specview.io.json.BondType = {
		SINGLE_BOND:"SINGLE_BOND",
		DOUBLE_BOND:"DOUBLE_BOND",		
		TRIPLE_BOND:"TRIPLE_BOND",
		QUADRUPLE_BOND:"QUADRUPLE_BOND",
		AROMATIC:"AROMATIC",
		SINGLE_OR_DOUBLE:"SINGLE_OR_DOUBLE",
		SINGLE_OR_AROMATIC:"SINGLE_OR_AROMATIC",
		DOUBLE_OR_AROMATIC:"DOUBLE_OR_AROMATIC",
		ANY:"ANY"
};

/**
 * enum for stereo types
 * 
 * @enum {string}
 */ 
specview.io.json.StereoType = {
		NOT_STEREO:"NOT_STEREO",
		SINGLE_BOND_UP:"SINGLE_BOND_UP",
		SINGLE_BOND_UP_OR_DOWN:"SINGLE_BOND_UP_OR_DOWN",
		SINGLE_BOND_DOWN:"SINGLE_BOND_DOWN"
};

/**
 * maps bond class to bond type code
 * 
 * @param{specview.model.Bond} bond
 * @return{specview.io.json.BondType}
 */
specview.io.json.getTypeCode = function(bond){
	if (bond.order == specview.model.Bond.ORDER.SINGLE){
		return specview.io.json.BondType.SINGLE_BOND;
	}
	if (bond.order == specview.model.Bond.ORDER.DOUBLE){
		return specview.io.json.BondType.DOUBLE_BOND;
	}
	if (bond.order == specview.model.Bond.ORDER.TRIPLE){
		return specview.io.json.BondType.TRIPLE_BOND;
	}
	if (bond.order == specview.model.Bond.ORDER.QUADRUPLE){
		return specview.io.json.BondType.QUADRUPLE_BOND;
	}
	throw new Error("Invalid bond type [" + bond.toString() + "]");
	
};

/**
 * maps bond class to stereo type code
 * 
 * @param{specview.model.Bond} bond
 * @return{specview.io.json.StereoType}
 */
specview.io.json.getStereoCode = function(bond){
	if (bond.stereo == specview.model.Bond.STEREO.UP){
		return specview.io.json.StereoType.SINGLE_BOND_UP;
	}
	if (bond.stereo == specview.model.Bond.STEREO.DOWN){
		return specview.io.json.StereoType.SINGLE_BOND_DOWN;
	}
	if (bond.stereo == specview.model.Bond.STEREO.UP_OR_DOWN){
		return specview.io.json.StereoType.SINGLE_BOND_UP_OR_DOWN;
	}
	return specview.io.json.StereoType.NOT_STEREO;
}


/**
 * factory method for bonds
 * 
 * @param{specview.io.json.BondType}type bond-type code
 * @param{specview.io.json.StereoType}stereo stereo-type code
 * @param{specview.model.Atom} source atom at source end of bond
 * @param{specview.model.Atom} target atom at target end of bond
 * 
 * @return{specview.model.Bond}
 */
specview.io.json.createBond = function(type, stereo, source, target) {
	switch (type) {
	case specview.io.json.BondType.SINGLE_BOND:
		switch (stereo) {
		case specview.io.json.StereoType.NOT_STEREO:
			return new specview.model.Bond(source, target);
		case specview.io.json.StereoType.SINGLE_BOND_UP:
			return new specview.model.Bond(source, target, specview.model.Bond.ORDER.SINGLE, specview.model.Bond.STEREO.UP);
		case specview.io.json.StereoType.SINGLE_BOND_UP_OR_DOWN:
			return new specview.model.Bond(source, target, specview.model.Bond.ORDER.SINGLE, specview.model.Bond.STEREO.UP_OR_DOWN);
		case specview.io.json.StereoType.SINGLE_BOND_DOWN:
			return new specview.model.Bond(source, target, specview.model.Bond.ORDER.SINGLE, specview.model.Bond.STEREO.DOWN);
		default:
			throw new Error("invalid bond type/stereo [" + type + "]/["
					+ stereo + "]");
		};
	case specview.io.json.BondType.DOUBLE_BOND:
		return new specview.model.Bond(source, target, specview.model.Bond.ORDER.DOUBLE);
	case specview.io.json.BondType.TRIPLE_BOND:
		return new specview.model.Bond(source, target, specview.model.Bond.ORDER.TRIPLE);
	case specview.io.json.BondType.QUADRUPLE_BOND:
		return new specview.model.Bond(source, target, specview.model.Bond.ORDER.QUADRUPLE);
	case specview.io.json.BondType.AROMATIC:
		return new specview.model.Bond(source, target, undefined, undefined, true);
	case specview.io.json.BondType.SINGLE_OR_DOUBLE:
	case specview.io.json.BondType.SINGLE_OR_AROMATIC:
	case specview.io.json.BondType.DOUBLE_OR_AROMATIC: 
	case specview.io.json.BondType.ANY: 
	default:
		throw new Error("invalid bond type/stereo [" + type + "]/[" + stereo
				+ "]");
	};
};


/**
 * convert jmol JSON object or string to molecule
 * 
 * @param{specview.io.json.Molecule|string} arg
 * @return{specview.model.Molecule}
 */
specview.io.json.readMolecule = function(arg) {
	/** @type {specview.io.json.Molecule} */
	var jmol;
	if (arg.constructor == String) {
		jmol = /** @type {specview.io.json.Molecule} */(goog.json.parse(arg));
	} else {
		jmol =  /** @type {specview.io.json.Molecule} */(arg);
	}
	var mol = new specview.model.Molecule();
	mol.name = jmol['name'];
	goog.array.forEach(jmol['atoms'], function(a){
		mol.addAtom(new specview.model.Atom(a['symbol'], a['coord']['x'], a['coord']['y'], a['charge']));
	});
	goog.array.forEach(jmol['bondindex'], function(b){
		mol.addBond(specview.io.json.createBond(b['type'], b['stereo'], mol.getAtom(b['source']), mol.getAtom(b['target'])));
	});
	return mol;
};
goog.exportSymbol('specview.io.json.readMolecule', specview.io.json.readMolecule);

/** @return {string} */
specview.io.json.writeMolecule = function(mol) {
	return new goog.json.Serializer().serialize(specview.io.json.moleculeToJson(mol));
};
goog.exportSymbol('specview.io.json.writeMolecule', specview.io.json.writeMolecule);


specview.io.json.readArrow = function(arrow_json){
	return new specview.model.Arrow(new goog.math.Coordinate(arrow_json['source']['x'], arrow_json['source']['y']), 
			new goog.math.Coordinate(arrow_json['target']['x'], arrow_json['target']['y']));
}

specview.io.json.readPlus = function(plus_json){
	return new specview.model.Plus(new goog.math.Coordinate(plus_json['x'], plus_json['y']));
}


/** @typedef {{symbol: string, coord: specview.io.json.Coordinate, charge: number}} */
specview.io.json.Atom;
/** @typedef {{x:number,y:number}} */
specview.io.json.Coordinate;
/** @typedef {{source: number, target: number, type: string, stereo: string}} */
specview.io.json.Bond;
/**
 * @typedef {{ name: string, atoms: Array.<specview.io.json.Atom>, bondindex:
 *          Array.<specview.io.json.Bond>}}
 */
specview.io.json.Molecule;

/**
 * convert molecule object to json representation
 * 
 * @param {specview.model.Molecule}
 *            mol the molecule to convert
 * @returns {specview.io.json.Molecule} in json molecule format
 */
specview.io.json.moleculeToJson = function(mol) {
	/** @type {Array.<specview.io.json.Atom>} */
	var atoms = goog.array.map(mol.atoms, function(a){
		return {
			'symbol': a.symbol, 
			'coord':{
				'x': a.coord.x, 
				'y': a.coord.y}, 
			'charge': a.charge
			};
	});
	/** @type {Array.<specview.io.json.Bond>} */
	var bonds = goog.array.map(mol.bonds, function(b){
		var btype =   specview.io.json.getTypeCode(b);
		var bstereo = specview.io.json.getStereoCode(b);
		var source_index = mol.indexOfAtom(b.source);
		var target_index = mol.indexOfAtom(b.target);
		goog.asserts.assert(source_index>-1);
		goog.asserts.assert(target_index>-1);
		return { 
			'source' : mol.indexOfAtom(b.source), 
			'target' : mol.indexOfAtom(b.target), 
			'type' : btype, 
			'stereo' : bstereo
		}
	});

	return {
		'name' : mol.name,
		'atoms' : atoms,
		'bondindex' : bonds
	};
};

specview.io.json.arrowToJson = function (arrow){
	return {'source': { 'x': arrow.source.x,
						'y': arrow.source.y},
			'target': { 'x': arrow.target.x,
						'y': arrow.target.y}};
}

specview.io.json.plusToJson = function (plus){
	return {'x': plus.coord.x,
			'y': plus.coord.y};
}


specview.io.json.logger = goog.debug.Logger.getLogger('specview.io.json');


