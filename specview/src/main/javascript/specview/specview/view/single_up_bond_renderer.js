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
goog.provide('specview.view.SingleUpBondRenderer');
goog.require('specview.view.BondRenderer');

/**
 * Class to render an single up bond object to a graphics representation
 * 
 *
 * @param {goog.graphics.AbstractGraphics} graphics to draw on.
 * @param {Object=} opt_config override default configuration
 * @constructor
 * @extends {specview.view.BondRenderer}
 */
specview.view.SingleUpBondRenderer = function(graphics, opt_config) {
	specview.view.BondRenderer.call(this,
			graphics, 
			specview.view.SingleUpBondRenderer.defaultConfig,
			opt_config);
}
goog.inherits(specview.view.SingleUpBondRenderer, specview.view.BondRenderer);

specview.view.SingleUpBondRenderer.prototype.render = function(bond, transform, bondPath) {

	this.setTransform(transform);
	
	var strokeWidth = this.config.get("bond")['stroke']['width'] / 10;

	var theta = specview.view.BondRenderer.getTheta(bond);
	var angle_left = theta + (Math.PI / 3);
	var angle_right = theta - (Math.PI/ 3);

	var trans1 = new specview.graphics.AffineTransform(1, 0, 0, 1, Math.cos(angle_left)
			* strokeWidth, Math.sin(angle_left) * strokeWidth);
	var target1 = trans1.transformCoords( [ bond.target.coord ])[0];
	
	var trans2 = new specview.graphics.AffineTransform(1, 0, 0, 1, Math.cos(angle_right)
			* strokeWidth, Math.sin(angle_right) * strokeWidth);
	var target2 = trans2.transformCoords( [ bond.target.coord ])[0];

  //make target1 and target2 drop short of the target (prettier)
	if (this.hasSymbol(bond.target) ) { 
      var dist=goog.math.Coordinate.distance(target1, bond.source.coord); 
	  	var correct=3*dist;
	    target1.x= ((target1.x*(correct-1))+(bond.source.coord.x)) / correct;
	    target1.y= ((target1.y*(correct-1))+(bond.source.coord.y)) / correct;
	
	    target2.x= ((target2.x*(correct-1))+(bond.source.coord.x)) / correct;
	    target2.y= ((target2.y*(correct-1))+(bond.source.coord.y)) / correct;
    }

	var coords = transform.transformCoords( [ bond.source.coord, target1,
			target2 ]);

	bondPath.moveTo(coords[0].x, coords[0].y);
	bondPath.lineTo(coords[1].x, coords[1].y);
	bondPath.lineTo(coords[2].x, coords[2].y);



}
