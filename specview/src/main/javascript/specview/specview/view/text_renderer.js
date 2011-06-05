/**
 * Copyright 2011 Samy Deghou (deghou@polytech.unice.fr)
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
goog.provide('specview.view.TextRenderer');
goog.require('specview.view.Renderer');
goog.require('goog.debug.Logger');
goog.require('specview.graphics.ElementArray');
goog.require('goog.array');
goog.require('specview.util.Utilities');

/**
 * Class to render a spectrum, a list of peaks really
 * 
 * @param {goog.graphics.AbstractGraphics} something to draw on
 * @param {Object=} opt_config override default configuration
 * @constructor
 * @extends {specview.view.Renderer}
 */
specview.view.TextRenderer = function(graphics, opt_config, opt_box) {
	specview.view.Renderer.call(this, graphics, specview.view.TextRenderer.defaultConfig, opt_config);
    this.box= opt_box;
};
goog.inherits(specview.view.TextRenderer, specview.view.Renderer);


/**
 * The spectrum is simply the object
 * Transform is static and has been set up in specview.controller.Controller.prototype.render. 
 */
specview.view.TextRenderer.prototype.render = function(textElementObject,box,opt_color) {
	var color = opt_color==undefined ? 'black' : '#FFFFFF';
	var xStart = box[0].x+10;
	var yStart = box[3].y+10;
    var stroke = new goog.graphics.Stroke(0.1,color);
	var fill = new goog.graphics.SolidFill(color);
    var font1 = new goog.graphics.Font(18, 'Comics');
    var font2 =	new goog.graphics.Font(11.5, 'Comics');
    this.graphics.drawText("Experiment information:", xStart, yStart, 600, 200, 'left', null,font1, stroke, fill);
    yStart+=25;    
    bool=true;
	for(k in textElementObject.text){
		yStart = bool ? yStart+=25 : yStart+=15;
		var mot = textElementObject.text[k];
		if(!(mot instanceof Array)){
			mot=specview.util.Utilities.getStringAfterCharacter(textElementObject.text[k],":");
		}		
		this.graphics.drawText(k+": "+mot,xStart,yStart,0,0,'left',null,font2,stroke,fill);
		bool=false;
	}
};



specview.view.TextRenderer.logger = goog.debug.Logger.getLogger('specview.view.TextRenderer');
