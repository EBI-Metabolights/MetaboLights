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
goog.provide('specview.view.SingleDownBondRenderer');
goog.require('specview.view.BondRenderer');

/**
 * Class to render an single down bond object to a graphics representation
 *
 *
 * @param {goog.graphics.AbstractGraphics} graphics to draw on.
 * @param {Object=} opt_config override default configuration
 * @constructor
 * @extends {specview.view.BondRenderer}
 */
specview.view.SingleDownBondRenderer = function(graphics, opt_config) {
	specview.view.BondRenderer.call(this,
			graphics,
			specview.view.SingleDownBondRenderer.defaultConfig,
			opt_config);
};
goog.inherits(specview.view.SingleDownBondRenderer, specview.view.BondRenderer);

specview.view.SingleDownBondRenderer.prototype.render = function(bond, transform, path) {

//  	var centerOfMolecule = document.metaSpecObject.getMoleculeCenter();
 // 	var xTransfer = parseInt(specview.util.Utilities.parsePixel(document.getElementById("fieldSet").style.width))/2 - centerOfMolecule.x;
  //	bond.source.coord.x += xTransfer;
  //	bond.target.coord.x += xTransfer;
	
//	alert("in single bond down renderer :\n"+transform)
	this.setTransform(transform);
	var width = this.config.get("bond")['stroke']['width'] / 10;
	var theta = specview.view.BondRenderer.getTheta(bond);

	var angle_left = theta + (Math.PI / 2);
	var angle_right = theta - (Math.PI / 2);
	var transleft = new specview.graphics.AffineTransform(1, 0, 0, 1, Math
			.cos(angle_left)
			* width, Math.sin(angle_left) * width);

	var transright = new specview.graphics.AffineTransform(1, 0, 0, 1,Math
			.cos(angle_right)
			* width, Math.sin(angle_right) * width);

	var leftside = transleft.transformCoords([bond.source.coord, bond.target.coord]);
	var rightside = transright.transformCoords([bond.source.coord, bond.target.coord]);

	var coords = transform.transformCoords( [ leftside[0],leftside[1], rightside[0], rightside[1], bond.source.coord ]);
	var lines=7;
	var multiply =Math.round(goog.math.Coordinate.distance(bond.source.coord, bond.target.coord));
	if (multiply>1) {
		lines*=multiply;
	}

	var correct=0;
	if( this.hasSymbol(bond.target))
		correct=1;
	for ( var j = 0; j < (lines-correct); j++) {
		path.moveTo( (((lines-j)*coords[4].x)+(j*coords[1].x))/lines,
				(((lines-j)*coords[4].y)+(j*coords[1].y))/lines);

		path.lineTo( (((lines-j)*coords[4].x)+(j*coords[3].x))/lines,
				(((lines-j)*coords[4].y)+(j*coords[3].y))/lines);
	}
};
