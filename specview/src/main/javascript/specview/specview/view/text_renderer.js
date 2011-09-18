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


specview.view.TextRenderer.prototype.renderZoomInfo = function(){

    var stroke = new goog.graphics.Stroke(0.3,'black');
	var fill = new goog.graphics.SolidFill('black');
    var font1 = new goog.graphics.Font(18, 'Comics');
    var font2 =	new goog.graphics.Font(13, 'Comics');
	this.graphics.drawText("Which part of the spectrum are you currently zooming in ?",
							250,
							document.metaSpecObject.secondSpecBox["top"]-15,
							600,
							200,
							'center',
							null,
							font2,
							stroke,
							fill);

}

/**
 * The spectrum is simply the object
 * Transform is static and has been set up in specview.controller.Controller.prototype.render. 
 */
specview.view.TextRenderer.prototype.render = function(textElementObject,box,opt_color,textIntro) {
//	alert(textElementObject)
	var textAreaElement = textIntro == "Peak information:" ? document.getElementById("peakInformation") : document.getElementById("experimentInformation"); 
	
	var innerHTML = "";
	
	var xStart = (box==undefined ? 850 : box["left"]);
//	var yStart = (box==undefined ? document.metaSpecObject.mainMolBox[3].y+2 : box["top"]);
//	var yStart = document.getElementById("moleculeContainer").offsetTop;
	var yStart = 20;
	/*
	 * It means that we are dealing with the peakInformation
	 */
	if(box==undefined){
		textElementObject.box.left = 850;
		textElementObject.box.top = document.metaSpecObject.mainMolBox[3].y+5;
		textElementObject.box.width = 200;
		textElementObject.box.height = 100;
	}
	var color = opt_color;
    var stroke = new goog.graphics.Stroke(0,color);
	var fill = new goog.graphics.SolidFill(color);
    var font1 = new goog.graphics.Font(18, 'Comics');
    var font2 =	new goog.graphics.Font(11.5, 'Comics');
    this.graphics.drawText(textIntro, xStart, yStart, 600, 200, 'left', null,font1, stroke, fill);
    yStart+=25;
//alert(document.getElementById("moleculeContainer").offsetTop)
   // var yIntro = yStart + parseInt(document.getElementById("moleculeContainer").offsetTop);
    
//alert(yStart)    
//    textAreaElement.style.top = yIntro + "px";
    
    for(k in textElementObject.text){
    	yStart+=15;
    	
    	innerHTML += k + ": "+textElementObject.text[k]+"\n\n";

//    	this.graphics.drawText("-"+k+": "+textElementObject.text[k],xStart,yStart,0,0,'left',null,font2,stroke,fill)
    }
//	document.getElementById("experimentInformation").innerHTML = innerHTML
//    alert("caca")
    textAreaElement.innerHTML = innerHTML;
  //  this.graphics.drawRect(box["left"],box["top"],box["width"],box["height"],stroke,null)
};

specview.view.TextRenderer.prototype.clearTextWithBox = function(box){
    var fill = new goog.graphics.SolidFill('white');
    var stroke = new goog.graphics.Stroke(2, 'white');
    var stroke2 = new goog.graphics.Stroke(0, 'grey');
//    alert(box)
	this.graphics.drawRect(box.left,box.top,box.width,box.height,stroke,fill);
	this.graphics.drawRect(box.left,box.top,box.width,box.height,stroke2,null);
}

specview.view.TextRenderer.logger = goog.debug.Logger.getLogger('specview.view.TextRenderer');
