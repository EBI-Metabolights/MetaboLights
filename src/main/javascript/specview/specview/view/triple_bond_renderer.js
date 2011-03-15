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
 * @author paul@wingu.com (Paul Novak)
 */
goog.provide('specview.view.TripleBondRenderer');
goog.require('specview.view.BondRenderer');
goog.require('goog.debug.Logger');


/**
 * Class to render a triple bond object to a graphics representation
 * 
 * @param {goog.graphics.AbstractGraphics} graphics to draw on.
 * @param {Object=} opt_config override default configuration
 * @constructor
 * @extends {specview.view.BondRenderer}
 */
specview.view.TripleBondRenderer = function(graphics, opt_config) {
	specview.view.BondRenderer.call(
			this,
			graphics, 
			specview.view.TripleBondRenderer.defaultConfig, 
			opt_config);
}
goog.inherits(specview.view.TripleBondRenderer, specview.view.BondRenderer);

specview.view.TripleBondRenderer.prototype.render = function(bond, transform, bondPath) {

	this.setTransform(transform);
	
	var strokeWidth = this.config.get("bond")['stroke']['width'];
	var bondStroke = new goog.graphics.Stroke(strokeWidth, this.config.get("bond")['stroke']['color']);
	var bondFill = null;

	var theta = specview.view.BondRenderer.getTheta(bond);

	var angle_left = theta + (Math.PI / 2);
	var angle_right = theta - (Math.PI / 2);

	var bondWidth=this.config.get("bond")['triple-dist'];
	
	var transleft = new specview.graphics.AffineTransform(1, 0, 0, 1, Math
			.cos(angle_left)
			* bondWidth, Math.sin(angle_left) * bondWidth);

	var transright = new specview.graphics.AffineTransform(1, 0, 0, 1, Math
			.cos(angle_right)
			* bondWidth, Math.sin(angle_right) * bondWidth);

	var sourceCoord = bond.source.coord;
	var targetCoord = bond.target.coord;

	if (this.hasSymbol(bond.source)||this.hasSymbol(bond.target)) {
		var bv = goog.math.Vec2.fromCoordinate(goog.math.Coordinate.difference(bond.target.coord, bond.source.coord));
		bv.scale(1 / this.config.get("bond")['width-ratio']);
		var space = bv.normalize().scale(this.config.get('bond')['symbol-space']);
		if (this.hasSymbol(bond.source)) {
			sourceCoord = goog.math.Coordinate.sum(sourceCoord, space);
		}
		if (this.hasSymbol(bond.target)) {
			targetCoord = goog.math.Coordinate.difference(targetCoord, space);
		}
	}

	var leftside = transleft.transformCoords  ( [ sourceCoord,targetCoord ]);
	var rightside = transright.transformCoords( [ sourceCoord,targetCoord ]);

	var coords = transform.transformCoords( [ sourceCoord, targetCoord, leftside[0], leftside[1], rightside[0], rightside[1] ]);

	bondPath.moveTo(coords[0].x, coords[0].y);
	bondPath.lineTo(coords[1].x, coords[1].y);

	bondPath.moveTo(coords[2].x, coords[2].y);
	bondPath.lineTo(coords[3].x, coords[3].y);

	bondPath.moveTo(coords[4].x, coords[4].y);
	bondPath.lineTo(coords[5].x, coords[5].y);

	this.graphics.drawPath(bondPath, bondStroke, bondFill);
}

specview.view.TripleBondRenderer.prototype.logger = goog.debug.Logger.getLogger('specview.view.TripleBondRenderer');
