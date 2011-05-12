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
goog.provide('specview.view.QuadrupleBondRenderer');
goog.require('specview.view.BondRenderer');

/**
 * Class to render an quadruple bond object to a graphics representation
 * 
 *
 * @param {goog.graphics.AbstractGraphics} graphics to draw on.
 * @param {Object=} opt_config override default configuration
 * @constructor
 * @extends {specview.view.BondRenderer}
 */
specview.view.QuadrupleBondRenderer = function( graphics, opt_config) {
	specview.view.BondRenderer.call(
			this, 
			graphics, 
			specview.view.QuadrupleBondRenderer.defaultConfig, 
			opt_config);
};
goog.inherits(specview.view.QuadrupleBondRenderer, specview.view.BondRenderer);

/**
 * 
 */
specview.view.QuadrupleBondRenderer.prototype.render = function(bond,
		transform, bondPath) {
	
	this.setTransform(transform);

	var strokeWidth = this.config.get("bond")['stroke']['width'];
	var bondStroke = new goog.graphics.Stroke(strokeWidth, this.config.get("bond")['stroke']['color']);
	var bondFill = null;

	var theta = specview.view.BondRenderer.getTheta(bond);

	var angle_left = theta + (Math.PI / 2);
	var angle_right = theta - (Math.PI / 2);

	var bondWidth=this.config.get("bond")['quad-dist'];

	var transleft1 = new specview.graphics.AffineTransform(1, 0, 0, 1, Math
			.cos(angle_left)
			* bondWidth / 3, Math.sin(angle_left) * bondWidth / 3);

	var transright1 = new specview.graphics.AffineTransform(1, 0, 0, 1, Math
			.cos(angle_right)
			* bondWidth / 3, Math.sin(angle_right) * bondWidth / 3);
	var transleft2 = new specview.graphics.AffineTransform(1, 0, 0, 1, Math
			.cos(angle_left)
			* bondWidth, Math.sin(angle_left) * bondWidth);

	var transright2 = new specview.graphics.AffineTransform(1, 0, 0, 1, Math
			.cos(angle_right)
			* bondWidth, Math.sin(angle_right) * bondWidth);

	var leftside1 = transleft1.transformCoords( [ bond.source.coord,
			bond.target.coord ]);
	var rightside1 = transright1.transformCoords( [ bond.source.coord,
			bond.target.coord ]);

	var leftside2 = transleft2.transformCoords( [ bond.source.coord,
			bond.target.coord ]);
	var rightside2 = transright2.transformCoords( [ bond.source.coord,
			bond.target.coord ]);

	var coords = transform.transformCoords( [ leftside1[0], leftside1[1],
			rightside1[0], rightside1[1], leftside2[0], leftside2[1],
			rightside2[0], rightside2[1] ]);

	bondPath.moveTo(coords[0].x, coords[0].y);
	bondPath.lineTo(coords[1].x, coords[1].y);

	bondPath.moveTo(coords[2].x, coords[2].y);
	bondPath.lineTo(coords[3].x, coords[3].y);

	 bondPath.moveTo(coords[4].x, coords[4].y);
	 bondPath.lineTo(coords[5].x, coords[5].y);
	 
	 bondPath.moveTo(coords[6].x, coords[6].y);
	 bondPath.lineTo(coords[7].x, coords[7].y);

	this.graphics.drawPath(bondPath, bondStroke, bondFill);
};
