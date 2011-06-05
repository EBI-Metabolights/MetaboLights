/**
 * Copyright 2011 Samy Deghou (deghou@polytech.unice.fr)
 * and  Mark Rijnbeek (markr@ebi.ac.uk)
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
 * 
 */

goog.provide('specview.model.TextElement');

goog.require('specview.model.Molecule');
goog.require('goog.math.Vec2');
goog.require('goog.debug.Logger');


/**
 * Class representing a TextElement
 * @constructor
 */
specview.model.TextElement=function(){
	/**
	 * Text element is presented under the form of an associative array.
	 */
	this.text = new Array();
	/**
	 * The box in which the text should be drawn.
	 */
	this.box = new goog.math.Rect();
};
goog.exportSymbol("specview.model.TextElement", specview.model.TextElement);

specview.model.TextElement.prototype.logger = goog.debug.Logger.getLogger('specview.model.TextElement');

/*
 * Description of the object
 */ 
specview.model.TextElement.prototype.toString = function() {
	var string="";
	for(k in this.text){
		string+="\n"+k+"-->"+this.text[k]+"\n\n";
	}
	return string;
};

