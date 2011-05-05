/** 
 * Copyright 2010 Paul Novak (paul@wingu.com)
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
goog.provide('specview.ring.PathEdge');


/**
 * @param {Array.<specview.model.Atom>} _atoms
 * @constructor
 */
specview.ring.PathEdge = function(_atoms){
	/** @type {Array.<specview.model.Atom>} */
    this.atoms = _atoms;
}

/** 
 * @return {boolean}
 */
specview.ring.PathEdge.prototype.isCycle = function(){
    var lastAtomPos = this.atoms.length - 1;
    return (this.atoms.length > 2 && this.atoms[0] == this.atoms[lastAtomPos]);
}

/**
 * @param {specview.ring.PathEdge} other
 * @return {specview.ring.PathEdge}
 */
specview.ring.PathEdge.prototype.splice = function(other){
	
	/** @type {specview.model.Atom} */
    var intersection = this.getIntersection(other.atoms);
    
    /** @type {Array.<specview.model.Atom>} */
    var newAtoms = new Array();
    
    for (var i = 0,il=this.atoms.length; i < il; i++) {
        newAtoms.push(this.atoms[i]);
    }
    
    if (this.atoms[0] == intersection) {
        newAtoms.reverse();
    }
    
    if (other.atoms[0] == intersection) {
        for (var i = 1,il=other.atoms.length; i < il; i++) {
            newAtoms.push(other.atoms[i]);
        }
    }
    else {
        for (var i = other.atoms.length - 2; i >= 0; i--) {
            newAtoms.push(other.atoms[i]);
        }
    }
    
    if (!this.isRealPath(newAtoms)) {
        return null;
    }
    
    return new specview.ring.PathEdge(newAtoms);
}

/**
 * @param {Array.<specview.model.Atom>} atoms
 * @return {boolean}
 */
specview.ring.PathEdge.prototype.isRealPath = function(atoms){
    for (var i = 1,il=atoms.length - 1; i < il; i++) {
        for (var j = 1; j < il; j++) {
            if (i == j) {
                continue;
            }
            if (atoms[i] == atoms[j]) {
                return false;
            }
        }
    }
    return true;
}

/**
 * @param {Array.<specview.model.Atom>} others
 * @return {specview.model.Atom}
 */
specview.ring.PathEdge.prototype.getIntersection = function(others){
    var lastAtomPos = this.atoms.length - 1;
    var lastOtherPos = others.length - 1;
    if (this.atoms[lastAtomPos] == others[0] || this.atoms[lastAtomPos] == others[lastOtherPos]) {
        return this.atoms[lastAtomPos];
    }
    if (this.atoms[0] == others[0] || this.atoms[0] == others[lastOtherPos]) {
        return this.atoms[0];
    }
    throw "Couldn't splice - no intersection";
}