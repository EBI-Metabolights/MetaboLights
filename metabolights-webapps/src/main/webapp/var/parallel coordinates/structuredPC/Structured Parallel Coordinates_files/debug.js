/* (This is the new BSD license.)
* Copyright (c) 2010, Europaeische Akademie Bozen/Accademia Euoropea Bolzano
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*     * Redistributions of source code must retain the above copyright
*       notice, this list of conditions and the following disclaimer.
*     * Redistributions in binary form must reproduce the above copyright
*       notice, this list of conditions and the following disclaimer in the
*       documentation and/or other materials provided with the distribution.
*     * Neither the name of the Europaeische Akademie Bozen/Accademia Euoropea 
*	Bolzano nor the of its contributors may be used to endorse or promote 
*	products from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY Europaeische Akademie Bozen/Accademia Euoropea
* Bolzano``AS IS'' AND ANY OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
* THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
* ARE DISCLAIMED. IN NO EVENT SHALL Europaeische Akademie Bozen/Accademia Euoropea 
* Bolzano BE LIABLE FOR ANY, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
* CONSEQUENTIAL DAMAGES INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
* GOODS OR SERVICES; OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER 
* CAUSED AND ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR 
* TORT INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF 
* THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

function multiAlert() {
	var what = "";
	for(var i=0;i<arguments.length;i++) {
		what = what + "\n" + arguments[i];
	}
	//alert(what);
	document.getElementById("debug").innerHTML = what;
}
function showObj(o, label) {
	var what = label + "\n";
	if ( o == null ) {
		what = "no object to show";
	} else {
		for(var f in o) {
			what = what + f + "\n" + o[f] + "\n-----------------\n"
		}
	}
	//alert(what);
	document.getElementById("debug").innerHTML = what;
}