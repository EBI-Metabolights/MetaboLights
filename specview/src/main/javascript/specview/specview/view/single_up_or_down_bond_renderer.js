/** 
 * Copyright 2010 Mark Rijnbeek (markr@ebi.ac.uk)
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
 * @author paul@wingu.com (Paul Novak)
 */
goog.provide('specview.view.SingleUpOrDownBondRenderer');
goog.require('specview.view.BondRenderer');

goog.require('goog.debug');
goog.require('goog.debug.FancyWindow');
goog.require('goog.debug.Logger');

/**
 * Class to render an single up or down bond object to a graphics representation,
 * a zig zag line.
 *
 * @param {goog.graphics.AbstractGraphics} graphics to draw on.
 * @param {Object=} opt_config override default configuration
 * @constructor
 * @extends {specview.view.BondRenderer}
 */
specview.view.SingleUpOrDownBondRenderer = function(graphics, opt_config) {
	specview.view.BondRenderer.call(this, 
			graphics, 
			specview.view.SingleUpOrDownBondRenderer.defaultConfig, 
			opt_config);
};
goog.inherits(specview.view.SingleUpOrDownBondRenderer,
		specview.view.BondRenderer);


specview.view.SingleUpOrDownBondRenderer.prototype.render = function(bond,transform, bondPath) {
//	alert(bond+"\n"+transform+"\n"+bondPath);

	this.setTransform(transform);
    var zigZags = Math.round(goog.math.Coordinate.distance(bond.source.coord, bond.target.coord)*12);
	if (zigZags<8)
	   zigZags=8;

    this.logger.fine('# zigZags '+zigZags);

    var strokeWidth = this.config.get("bond")['stroke']['width']/10 ;
    var theta = specview.view.BondRenderer.getTheta(bond);
    var angle_left = theta + (Math.PI / 2);
    var angle_right = theta - (Math.PI / 2);

    zzCoords = new Array();
	zzCoords.push(bond.source.coord);
    this.logger.fine('# coords '+zzCoords.length);

    var trans=null;
	var inc=1/zigZags;
	var corr=0;

    //Loop one: create the points to zig zag to, increasingly away from the middle line by using "corr"
    for(z=1; z <= zigZags; z++) {
        corr += inc;

		if (z%2==0)
            trans = new specview.graphics.AffineTransform(1, 0, 0, 1, Math.cos(angle_left)* (strokeWidth*corr), Math.sin(angle_left) * (strokeWidth*corr));
		else
            trans = new specview.graphics.AffineTransform(1, 0, 0, 1, Math.cos(angle_right) * (strokeWidth*corr), Math.sin(angle_right) * (strokeWidth*corr));
        
        var zX = ( ((zigZags-z)*bond.source.coord.x) + (bond.target.coord.x * z ))/zigZags; 
        var zY = ( ((zigZags-z)*bond.source.coord.y) + (bond.target.coord.y * z ))/zigZags; 

		var intermediateTarget = trans.transformCoords( [new goog.math.Coordinate(zX,zY) ])[0];    
        zzCoords.push(intermediateTarget);
        
	}
    var coords = transform.transformCoords( zzCoords);

    //Loop two: draw lines between zig zag points    
    bondPath.moveTo(coords[1].x, coords[1].y);
	for (i=2, max=coords.length-1; i<max; i++) {
        bondPath.lineTo(coords[i].x, coords[i].y);
        bondPath.moveTo(coords[i].x, coords[i].y);
	}
};
