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

goog.provide("specview.view.Renderer");
goog.require("goog.structs.Map");
goog.require("goog.debug.Logger");

/**
 * Abstract Class to render a model object to a graphics representation
 * 
 * @param graphics
 *            {goog.graphics.AbstractGraphics} graphics to draw on.
 * @param {Object=} opt_default_config holding default values
 * @param opt_config
 *            {Object=} opt_config to override defaults
 * @constructor
 */
specview.view.Renderer = function(graphics, opt_default_config, opt_config) {

	this.graphics = graphics;
	var default_config = goog.isDef(opt_default_config) ? opt_default_config : {};
	this.config = new goog.structs.Map(default_config);
	
	if (goog.isDef(opt_config)) {
		// merge optional config into defaults
		this.config.addAll(opt_config); 
	}
	
};
specview.view.Renderer.prototype.render = goog.abstractMethod;

specview.view.Renderer.prototype.logger = goog.debug.Logger.getLogger('specview.view.Renderer');

/**
 * @param {specview.graphics.AffineTransform} transform
 */
specview.view.Renderer.prototype.setTransform = function(transform){
	this.transform = transform;
};

specview.view.Renderer.prototype.renderGrid = function(box, opt_color){
	boxCoords=box;
	var topLeft=box[0];
	var topRight=box[1];
	var bottomLeft=box[2];
	var bottomRight=box[3];
    var boxPath = new goog.graphics.Path();
    var scaleX=(bottomRight.x-bottomLeft.x)/21;
    var scaleY=(bottomLeft.y-topLeft.y)/9;
    //Vertical grid
    for(var k=bottomLeft.x+100;k<bottomRight.x;k+=scaleX){
    	boxPath.moveTo(k,bottomRight.y);
    	boxPath.lineTo(k,topRight.y+10);
    }
    //Horizontal grid
    for(var k=topRight.y+scaleY;k<bottomRight.y;k+=scaleY){
    	boxPath.moveTo(topLeft.x-10,k);
    	boxPath.lineTo(topRight.x,k);
    }
    if(!opt_color){
    	opt_color='black';	
    }
    var boxStroke = new goog.graphics.Stroke(0.32,opt_color);
    var boxFill = null;
    
    this.graphics.drawPath(boxPath, boxStroke, boxFill);
    
};


/**
 * @param metaSpecObject
 * @param boxo
 * @param opt_color
 * @param opt_expand type bool. spectrumData.expand set to false by default. When set to true, 
 */
specview.view.Renderer.prototype.renderAxis = function(metaSpecObject,boxo,opt_color){
	
	var maxHeight=metaSpecObject.spectrum.getMaxHeightPeak();
	var maxValue=metaSpecObject.spectrum.getMaxValuePeak();

	var boxCoords=metaSpecObject.mainSpecBox;
	var topLeft=boxCoords[0];
	var topRight=boxCoords[1];
	var bottomLeft=boxCoords[2];
	var bottomRight=boxCoords[3];

	var peakList=metaSpecObject.ArrayOfPeaks;
	
    var scaleX=(boxCoords[1].x-boxCoords[0].x)/21;
    var scaleY=(boxCoords[1].y-boxCoords[0].y)/9;
  
    var stroke = new goog.graphics.Stroke(0.4,opt_color);
	var fill = new goog.graphics.SolidFill(opt_color);
    var font = new goog.graphics.Font(10, 'Times');

    var boxPath = new goog.graphics.Path();
    var boxStroke = new goog.graphics.Stroke(1.5,opt_color);
    var boxFill = null;
    /*
     * Draw the proper axis
     */
    boxPath.moveTo(bottomLeft.x, bottomLeft.y);
    boxPath.lineTo(bottomRight.x, bottomRight.y);
    boxPath.moveTo(bottomLeft.x, bottomLeft.y);
    boxPath.lineTo(topLeft.x, topLeft.y);
    boxPath.moveTo(bottomRight.x, bottomRight.y);
    boxPath.lineTo(topRight.x,topLeft.y);
    
    this.graphics.drawPath(boxPath, boxStroke, boxFill);
    var scaleX=(bottomRight.x-bottomLeft.x)/21;

    /**
     * Write the value on the x axis
     * If NMR : the origin of the xAxis is on the bottom RIGHT corner....
     * If MS :  the origin of the xAxis is on the bottom Left corner.....
     */
    var ecartNouveau = metaSpecObject.spectrum.getMaxValuePeak() - metaSpecObject.spectrum.getMinValuePeak();
    
    var valueToStart = metaSpecObject.expand ? metaSpecObject.spectrum.getMinValuePeak() : 0;
    
    switch(metaSpecObject.experienceType){
    case "NMR" :
        var ty =0;
        for(var k=bottomRight.x-scaleX;k>bottomLeft.x;k-=scaleX){
            ty++
            if(count!=0){
            	var increase = metaSpecObject.expand ? ecartNouveau*(ty/21) : maxValue*(ty/21)
                this.graphics.drawText(specview.util.Utilities.parseOneDecimal((valueToStart + increase)),
                				       k,
                				       bottomLeft.y,
                				       600, 200,
                				       'left', null,
                				       font, stroke, fill);    	  
            }  
      }
    	break;
    case "MS" :
        var ty =0;
        for(var k=bottomLeft.x+scaleX;k<bottomRight.x;k+=scaleX){
            ty++
            if(count!=0){
            	var increase = metaSpecObject.expand ? ecartNouveau*(ty/21) : maxValue*(ty/21)
                this.graphics.drawText(specview.util.Utilities.parseOneDecimal((valueToStart + increase)),
                				       k,
                				       bottomLeft.y,
                				       600, 200,
                				       'left', null,
                				       font, stroke, fill);    	  
            }  
      }
    	break;
    	
    default :
    	alert("No experiment specified. Hence, cannot set the value of the xAxis");
    	break;
    }
    
    /**
     * Right the xUnit on the x Axis
     */
    this.graphics.drawText(metaSpecObject.spectrum.xUnit, bottomRight.x+20, bottomRight.y, 600, 200, 'left', null,
            font, stroke, fill);
    this.graphics.drawText(metaSpecObject.spectrum.xUnit, bottomLeft.x-20, bottomLeft.y, 600, 200, 'left', null,
            font, stroke, fill);

    var count=7;
    var t = metaSpecObject.spectrum.getMaxYvalue();
    var b = metaSpecObject.mainSpecBox[2].y;
    scaleY=(b-t)/7;
    /**
     * Write the value on the y axis at regular steps
     */
    for(var k=b-scaleY;k>t-10;k-=scaleY){
    	count-=1;
    	if(count!=-1){
    		var tyui = maxHeight-(maxHeight*(count)/7);
            this.graphics.drawText(parseInt(tyui), bottomLeft.x-14, k, 600, 200, 'left', null,
                    font, stroke, fill);
            
            this.graphics.drawText(parseInt(tyui), bottomRight.x+10, k, 600, 200, 'left', null,
                    font, stroke, fill);
    	}
    }
  /*
   * Right the yUnit on the y Axis if it is available.
   */
    if(metaSpecObject.spectrum.yUnit!=undefined){
    	this.graphics.drawText(metaSpecObject.spectrum.yUnit, topLeft.x-14, topLeft.y, 600, 200, 'left', null,
                font, stroke, fill);	
    }
};

/**
 * Convenience method to see where elements are rendered by
 * drawing the bounding box
 * @param {goog.math.Box } bounding box
 */
specview.view.Renderer.prototype.renderBoundingBox = function(box, opt_color){
	var boxPath = new goog.graphics.Path();
	var boxCoords=box;
    boxPath.moveTo(boxCoords[0].x, boxCoords[0].y); 
    boxPath.lineTo(boxCoords[1].x,boxCoords[1].y);

    boxPath.moveTo(boxCoords[1].x,boxCoords[1].y);
    boxPath.lineTo(boxCoords[3].x,boxCoords[3].y);

    boxPath.moveTo(boxCoords[3].x,boxCoords[3].y);
    boxPath.lineTo(boxCoords[2].x,boxCoords[2].y);

    boxPath.moveTo(boxCoords[2].x,boxCoords[2].y);
    boxPath.lineTo(boxCoords[0].x, boxCoords[0].y); 
    
    if(!opt_color)
        opt_color='black';
        
    var boxStroke = new goog.graphics.Stroke(3,opt_color);
    var boxFill = null;
    
    this.graphics.drawPath(boxPath, boxStroke, boxFill);
};



