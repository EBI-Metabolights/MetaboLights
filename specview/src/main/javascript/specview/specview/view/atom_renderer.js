/**
 * Copyright 2010 Paul Novak (paul@wingu.com)
             2011 Mark Rijnbeek (markr@ebi.ac.uk)
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
goog.provide('specview.view.AtomRenderer');
goog.require('specview.view.Renderer');
goog.require('goog.debug.Logger');
goog.require('specview.graphics.ElementArray');

/**
 * Class to render an Atom object to a graphics representation
 * 
 * @param {goog.graphics.AbstractGraphics}
 *            graphics to draw on
 * @param {Object=}
 *            opt_config override default configuration
 * @constructor
 * @extends {specview.view.Renderer}
 */
specview.view.AtomRenderer = function(graphics, opt_config) {
	specview.view.Renderer.call(this, graphics,
			specview.view.AtomRenderer.defaultConfig, opt_config);
};
goog.inherits(specview.view.AtomRenderer, specview.view.Renderer);

/**
 * 
 * @param {specview.model.Atom}
 *            atom
 * @param {specview.graphics.AffineTransform}
 *            transform
 * @param {specview.graphics.ElementArray=}
 *            opt_element_array
 * @return {specview.graphics.ElementArray}
 */
specview.view.AtomRenderer.prototype.render = function(atom, transform,
		opt_element_array) {
	this.setTransform(transform);
	if (!opt_element_array) {
		opt_element_array = new specview.graphics.ElementArray();
	}
	var atom_config = this.config.get("atom");
	var color = this.config.get(atom.symbol) ? this.config.get(atom.symbol)['color']
			: atom_config['color'];

	var scale = transform.getScaleX();
	var fontSize = (scale / 3.5) > 16 ? 16 : Math.round((scale / 3.5)); //TODO in config file o.i.d.

	var font = new goog.graphics.Font(fontSize, atom_config['fontName']);
	var stroke = new goog.graphics.Stroke(atom_config['stroke']['width'],
			"black");

	var fill = new goog.graphics.SolidFill(color);

	var point = transform.transformCoords([ atom.coord ])[0];//point is the coordonates with pixelS
//	alert(point.x+" "+point.y)
	atom.setPixelCoordinates(point.x, point.y);
	
    //this.logger.info("> atom point ="+point)
	var displayElem = this.displaySettings(atom);
	var graphics = this.graphics;
	var w = displayElem.text.length * 0.55 * font.size;
	var h = font.size;


	
	if (displayElem.text) {
		opt_element_array.add(graphics.drawText(displayElem.text, point.x - w / 2,
				point.y - h / 2, w, h, displayElem.justification, null, font,
				stroke, fill));
		if (displayElem.justification == 'left') {
			if (displayElem.subscript || displayElem.superscript) {
				var subSize = this.config.get("subscriptSize");
				if (displayElem.subscript) {
//					alert("left: "+point.x)

					opt_element_array.add(graphics.drawText(displayElem.subscript,
							point.x + w * 0.9, point.y, subSize, subSize,
							'center', null, font, stroke, fill));

				}
			}
		} else if (displayElem.justification == 'right') {
			if (displayElem.subscript || displayElem.superscript) {
				var subSize = this.config.get("subscriptSize");
				if (displayElem.subscript) {
		//			alert("right: "+point.x)
					opt_element_array.add(graphics.drawText('H', point.x - w
							* 3, point.y - h / 2, w, h, 'center', null, font,
							stroke, fill));
					opt_element_array.add(graphics.drawText(displayElem.subscript,
							point.x - w * 1.8, point.y, subSize, subSize,
							'center', null, font, stroke, fill));
				}
			}
		}

		if (displayElem.superscript) {
			if(displayElem.justification == 'center') {
				w=w*0.8;
			}
		//	alert("ceter: "+point.x);
			opt_element_array.add(graphics.drawText(displayElem.superscript,
					point.x + w, point.y - h * 0.8, subSize, subSize,
					'left', null, font, stroke, fill));
		}

	}
	return opt_element_array;
};

/**
 * Return the way the an element symbol should be drawn (text, justification etc)
 * 
 * @param{jchemhun.model.Atom} atom
 * @return {Object}
 */
specview.view.AtomRenderer.prototype.displaySettings = function(atom) {
	var retval = {
		text : "",
		justification : 'center',
		superscript : '',
		subscript : ''
	};
	if (atom.symbol != "C" || atom.countBonds() < 1 || atom.charge
		|| (atom.countBonds() == 1 && this.config.get("showTerminalCarbons"))) {
		var hydrogen_count = atom.hydrogenCount();
		if (hydrogen_count == 0) {
			retval.text = atom.symbol;

		} else {
			retval.text = atom.symbol;
			var leftRight = this.hPosition(atom);

			if (leftRight=="L") {
				if (hydrogen_count > 1) {
					retval.subscript = String(hydrogen_count);
					retval.justification = 'right';
				}
				else
					retval.text = "H"+retval.text;
			}
			else {
				retval.text +="H";
				if (hydrogen_count > 1) {
					retval.subscript = String(hydrogen_count);
					retval.justification = 'left';
				}
			} 
		}

		if (atom.charge) {
			if (atom.charge > 1) {
				retval.superscript += '+' + atom.charge;
			} else if (atom.charge < -1) {
				retval.superscript += atom.charge;
			} else if (atom.charge == -1) {
				retval.superscript = '-';
			} else if (atom.charge == 1) {
				retval.superscript = '+';
			}
		}

	} else {
		retval.text = "";
	}
	return retval;
};

/**
 * Decides where the H should be drawn, left or right (so HN or NH) depending on
 * connected bond directions of the atom.
 * 
 * @param {specview.model.Atom}
 *            atom
 * @return R,L meaning Right or Left
 */
specview.view.AtomRenderer.prototype.hPosition = function(atom) {
	var rightAvailable = true;
	var leftAvailable = true;

	if (atom.countBonds() == 0) {
		return atom.symbol + "H";
	}

	for (j = 0, lj = atom.countBonds(); j < lj; j++) {
		var bond = atom.bonds.getValues()[j];
		var target = bond.target.coord;
		var source = bond.source.coord;

		var dy = source.y - target.y;
		var dx = source.x - target.x;
		if (atom == bond.source) {
			dx = -dx;
			dy = -dy;
		}
		var angle = Math.atan2(dy, dx) * 180 / Math.PI;
		if (angle < 0) {
			angle = 360 + angle;
		}
		if (angle < 45 || angle > 315)
			rightAvailable = false;
		else if (angle > 135 && angle < 225)
			leftAvailable = false;
	}

	if (!rightAvailable && leftAvailable)
		return "L"; //return "H" + atom.symbol;

	else
		return "R"; //return atom.symbol + "H";

};

/**
 * @param {specview.model.Atom}
 *            atom
 * @param {string=}
 *            opt_color
 * @param {specview.graphics.ElementArray=}
 *            opt_element_array
 * @return {specview.graphics.ElementArray}
 */
specview.view.AtomRenderer.prototype.highlightOn = function(atom, opt_color,
		opt_element_array) {
//	this.logger.info(atom)
	var atom_config = this.config.get("atom");
	var strokeWidth = atom_config['stroke']['width'] * 24;
	if (!opt_color) {
		opt_color = this.config.get(atom.symbol) ? this.config.get(atom.symbol)['color']
				: atom_config['color'];
	}
	if (!opt_element_array) {
		opt_element_array = new specview.graphics.ElementArray();
	}
	var fill = new goog.graphics.SolidFill(opt_color, .3);
//	alert("fill: "+fill);
	var radius = atom_config['highlight']['radius']
			* this.transform.getScaleX() * 0.7;
	var coords = this.transform.transformCoords([ atom.coord ])[0];
//	this.logger.info("x: "+coords.x+"\ny: "+coords.y)
	opt_element_array.add(this.graphics.drawCircle(coords.x, coords.y, radius,
			null, fill));
	return opt_element_array;
};


specview.view.AtomRenderer.prototype.highlightOnSeriesOfAtom = function(arrayOfAtom, opt_color,
		opt_element_array){
	var atom_config = this.config.get("atom");
	var strokeWidth = atom_config['stroke']['width'] * 24;
	if (!opt_element_array) {
		opt_element_array = new specview.graphics.ElementArray();
	}
	var fill = new goog.graphics.SolidFill(opt_color, .3);
	var radius = atom_config['highlight']['radius']
			* this.transform.getScaleX() * 0.7;
	for(var k=0;k<arrayOfAtom.length;k++){
		var coords = this.transform.transformCoords([ arrayOfAtom[k].coord ])[0];
		opt_element_array.add(this.graphics.drawCircle(coords.x, coords.y, radius,
				null, fill));
	}
	
	return opt_element_array;
	
	
	
};


/**
 * Logging object.
 * 
 * @type {goog.debug.Logger}
 * @protected
 */
specview.view.AtomRenderer.prototype.logger = goog.debug.Logger
		.getLogger('specview.view.AtomRenderer');

/**
 * A default configuration for renderer
 */
specview.view.AtomRenderer.defaultConfig = {
	'atom' : {
		'color' : '#FF9999',
		'diameter' : .05,
		'highlight' : {
			'radius' : .53
		},
		'stroke' : {
			'width' : .10
		// font width ..?
		},
		'fontName' : "Arial"
	},
	'showTerminalCarbons' : true,
	'margin' : 20,
	'subscriptSize' : 5,
	'N' : {
		'color' : '#2233ff'
	},
	'O' : {

		'color' : '#ff2200'

	},
	'S' : {

		'color' : '#dddd00'

	},
	'P' : {

		'color' : '#ff9900'

	},
	'Cl' : {

		'color' : '#55bb00'

	},
	'F' : {

		'color' : '#55bb00'

	},
	'Br' : {

		'color' : '#992200'

	},
	'I' : {

		'color' : '#6600bb'

	},
	'C' : {

		'color' : '#222222'

	},
	'R1' : {

		'color' : 'black'

	},
	'R2' : {

		'color' : 'black'

	},
	'H' : {

		'color' : '#CCCCCC'
	},
	'B' : {

		'color' : '#ffaa77'
	},

	'Li' : {

		'color' : '#7700ff'
	},
	'Na' : {

		'color' : '#7700ff'
	},
	'K' : {

		'color' : '#7700ff'
	},
	'Rb' : {

		'color' : '#7700ff'
	},
	'Cs' : {

		'color' : '#7700ff'
	}
	,
	'Fe' : {

		'color' : '#dd7700'
	}

};
