/**
 * Copyright 2011 Mark Rijnbeek (markr@ebi.ac.uk)
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
goog.provide('specview.view.SpectrumRenderer');
goog.require('specview.view.Renderer');
goog.require('goog.debug.Logger');
goog.require('specview.graphics.ElementArray');
goog.require('goog.array');

/**
 * Class to render a spectrum, a list of peaks really
 * 
 * @param {goog.graphics.AbstractGraphics} something to draw on
 * @param {Object=} opt_config override default configuration
 * @constructor
 * @extends {specview.view.Renderer}
 */
specview.view.SpectrumRenderer = function(graphics, opt_config, opt_box) {
	specview.view.Renderer.call(this, graphics, specview.view.SpectrumRenderer.defaultConfig, opt_config);
    this.box= opt_box;
}
goog.inherits(specview.view.SpectrumRenderer, specview.view.Renderer);

specview.view.SpectrumRenderer.prototype.setBoundsBasedOnMolecule = function(molecule) {

    var molBox = molecule.getBoundingBox();
    molHeight=Math.abs(molBox.top-molBox.bottom);
    molWidth=Math.abs(molBox.left-molBox.right);
    size=Math.max(molHeight,molWidth);
    top=molBox.bottom;
    bottom=top-size;
    left= 1.1*molBox.right;
    right=left+size;
    this.box = new goog.math.Box(top,right,bottom,left);
    
    /*
    var gSize=graphics.getSize();
    b = new goog.math.Box();
    b.left = gSize.width/2;
    b.right = gSize.width*(2/3);
    b.top = gSize.height*(5/6);
    b.bottom = gSize.height*(1/6);
    this.box=b;
    this.logger.info(this.box.top+" "+this.box.bottom+" "+this.box.left+" "+this.box.right+" ");
    */

}

/**
 * TODO doc
 */
specview.view.SpectrumRenderer.prototype.render = function(spectrum, transform) {
    this.logger.info("rendering spectrum inside "+this.box)

    this.setTransform(transform);
    this.renderBoundingBox(this.box,'red'); 

    var minX=spectrum.peakList[0].xValue;
    var maxX=spectrum.peakList[0].xValue;
    goog.array.forEach(spectrum.peakList,
    function(peak) {
        if (peak.xValue < minX)
            minx=peak.xValue;
        if (peak.xValue > maxX)
            maxX=peak.xValue;
    },
    this);
    
    var xAxisLen=(this.box.right-this.box.left)*0.8;
    this.logger.info(minX+" "+maxX +  "  "+xAxisLen);

    var correct = xAxisLen/(maxX-minX);
    
    var xStart= this.box.left*1.1;    
    var yStart= this.box.top;    

    var peakPath = new goog.graphics.Path();
    var peakStroke = new goog.graphics.Stroke(1,'black');
    var peakFill = null;
    
    goog.array.forEach(spectrum.peakList,
    function(peak) {

    var peakFrom =new goog.math.Coordinate(box.left,box.top);
        var peakFrom =new goog.math.Coordinate(xStart+(peak.xValue*correct), yStart );
        var peakTo =new goog.math.Coordinate(xStart+(peak.xValue*correct), (this.box.top+this.box.bottom)/2 );
        //TODO intensity and multipl etc
        this.logger.info("peak from "+(xStart+(peak.xValue*correct)));
        var peakCoords = this.transform.transformCoords( [peakFrom, peakTo]);
        peakPath.moveTo(peakCoords[0].x, peakCoords[0].y); 
        peakPath.lineTo(peakCoords[1].x,peakCoords[1].y);


    },
    this);

    this.graphics.drawPath(peakPath, peakStroke, peakFill);

}

specview.view.SpectrumRenderer.logger = goog.debug.Logger.getLogger('specview.view.SpectrumRenderer');

