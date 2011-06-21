/**
 * Copyright 2010 Paul Novak (paul@wingu.com)
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
 * @author paul@wingu.com (Paul Novak)
 */
goog.provide('specview.view.BondRenderer');
goog.require('specview.view.Renderer');
goog.require('specview.math.Line');

/**
 * Class to render an bond object to a graphics representation
 * 
 * 
 * @param {goog.graphics.AbstractGraphics}
 *            graphics to draw on.
 * @param {Object=}
 *            opt_config override default configuration
 * @constructor
 * @extends {specview.view.Renderer}
 */
specview.view.BondRenderer = function(graphics, opt_config ) {
	specview.view.Renderer.call(
			this,
			graphics, 
			specview.view.BondRenderer.defaultConfig, 
			opt_config);
};
goog.inherits(specview.view.BondRenderer, specview.view.Renderer);

/**
 * @param {specview.model.Bond}
 *            bond
 * @param {string}
 *            opt_color
 * @param {specview.graphics.ElementArray=} opt_element_array
 * @return {specview.graphics.ElementArray}
 */
specview.view.BondRenderer.prototype.highlightOn = function(bond, opt_color, opt_element_array) {
//	alert("call to bondrenderer highlight")
	if(!opt_color){
		opt_color = this.config.get("highlight")['color'];
	}
	if (!opt_element_array) {
		opt_element_array = new specview.graphics.ElementArray();
	}

	var stroke = null;
	
	var angle = goog.math.angle(bond.source.coord.x, bond.source.coord.y, bond.target.coord.x,
			bond.target.coord.y);
	var angle_up = goog.math.standardAngle(angle + 90);
	var angle_down = goog.math.standardAngle(angle - 90);
	

	var angle_up_rads = goog.math.toRadians(angle_up);
	var angle_down_rads = goog.math.toRadians(angle_down);
	
	var scale = this.transform.getScaleX();
	var radius = this.config.get("highlight")['radius']
			* scale ;
	
	
	var coords = this.transform.transformCoords( [ bond.source.coord,
			bond.target.coord ]);

	var source_up = goog.math.Coordinate.sum(new goog.math.Coordinate(
			radius * Math.cos(angle_up_rads), -radius
					* Math.sin(angle_up_rads)), coords[0]);

	var target_up = goog.math.Coordinate.sum(new goog.math.Coordinate(
			radius * Math.cos(angle_up_rads), -radius
					* Math.sin(angle_up_rads)), coords[1]);

	var source_down = goog.math.Coordinate.sum(new goog.math.Coordinate(
			radius * Math.cos(angle_down_rads), -radius
					* Math.sin(angle_down_rads)), coords[0]);
	var target_down = goog.math.Coordinate.sum(new goog.math.Coordinate(
			radius * Math.cos(angle_down_rads), -radius
					* Math.sin(angle_down_rads)), coords[1]);

	var path_up = new goog.graphics.Path();
	path_up.moveTo(coords[0].x, coords[0].y);
	path_up.lineTo(source_up.x, source_up.y);
	path_up.lineTo(target_up.x, target_up.y);
	path_up.lineTo(coords[1].x, coords[1].y);
	path_up.close();

	var fill = new goog.graphics.SolidFill(opt_color, .15);
	opt_element_array.add(this.graphics.drawPath(path_up, stroke, fill));
	
	var path_down = new goog.graphics.Path();
	path_down.moveTo(coords[0].x, coords[0].y);
	path_down.lineTo(source_down.x, source_down.y);
	path_down.lineTo(target_down.x, target_down.y);
	path_down.lineTo(coords[1].x, coords[1].y);
	path_down.close();
	opt_element_array.add(this.graphics.drawPath(path_down, stroke, fill));
	return opt_element_array;
};


specview.view.BondRenderer.prototype.highlightOnSerieOfBonds = function(bond, opt_color, opt_element_array) {
	if(!opt_color){
		opt_color = this.config.get("highlight")['color'];
	}
	if (!opt_element_array) {
		opt_element_array = new specview.graphics.ElementArray();
	}
	opt_color = "blue"
	var stroke = null;
	
	var angle = goog.math.angle(bond.source.coord.x, bond.source.coord.y, bond.target.coord.x,
			bond.target.coord.y);
	var angle_up = goog.math.standardAngle(angle + 90);
	var angle_down = goog.math.standardAngle(angle - 90);
	

	var angle_up_rads = goog.math.toRadians(angle_up);
	var angle_down_rads = goog.math.toRadians(angle_down);
	
	var scale = this.transform.getScaleX();
	var radius = this.config.get("highlight")['radius']
			* scale ;
	
	
	var coords = this.transform.transformCoords( [ bond.source.coord,
			bond.target.coord ]);

	var source_up = goog.math.Coordinate.sum(new goog.math.Coordinate(
			radius * Math.cos(angle_up_rads), -radius
					* Math.sin(angle_up_rads)), coords[0]);

	var target_up = goog.math.Coordinate.sum(new goog.math.Coordinate(
			radius * Math.cos(angle_up_rads), -radius
					* Math.sin(angle_up_rads)), coords[1]);

	var source_down = goog.math.Coordinate.sum(new goog.math.Coordinate(
			radius * Math.cos(angle_down_rads), -radius
					* Math.sin(angle_down_rads)), coords[0]);
	var target_down = goog.math.Coordinate.sum(new goog.math.Coordinate(
			radius * Math.cos(angle_down_rads), -radius
					* Math.sin(angle_down_rads)), coords[1]);

	var path_up = new goog.graphics.Path();
	path_up.moveTo(coords[0].x, coords[0].y);
	path_up.lineTo(source_up.x, source_up.y);
	path_up.lineTo(target_up.x, target_up.y);
	path_up.lineTo(coords[1].x, coords[1].y);
	path_up.close();

	var fill = new goog.graphics.SolidFill(opt_color, .15);
//	opt_element_array.add(this.graphics.drawPath(path_up, stroke, fill));
	
	var path_down = new goog.graphics.Path();
	path_down.moveTo(coords[0].x, coords[0].y);
	path_down.lineTo(source_down.x, source_down.y);
	path_down.lineTo(target_down.x, target_down.y);
	path_down.lineTo(coords[1].x, coords[1].y);
	path_down.close();
	//opt_element_array.add(this.graphics.drawPath(path_down, stroke, fill));
	//return opt_element_array;
	var h = new Object();
	h.pathUp = this.graphics.drawPath(path_up, stroke, fill)
	h.pathDown = this.graphics.drawPath(path_down, stroke, fill);
	return h;
};

/**
 * 
 * @return{number} bond angle of elevation
 */
specview.view.BondRenderer.getTheta = function(bond) {
	return new specview.math.Line(bond.source.coord, bond.target.coord)
			.getTheta();
};


specview.view.BondRenderer.prototype.hasSymbol = function(atom) {
    return (atom.symbol != "C" || atom.countBonds() == 1); // TODO should take end carbon rendering config into account though
};


/**
 * A default configuration for renderer
 */
specview.view.BondRenderer.defaultConfig = {
	'bond' : {
		'stroke' : {
			'width' : 1.5,
			'color' : 'black'
		},
		'fill' : {
			'color' : 'black'
		}, 
		'width-ratio' : 6,
		'symbol-space' : 0.25,
		'triple-dist' : 0.15,
		'quad-dist' : 0.13

	},
	'highlight' : {
		'radius' : .15,
		'color' : 'blue'
	}
};



