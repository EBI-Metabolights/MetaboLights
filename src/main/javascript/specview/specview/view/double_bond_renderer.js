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
 */
goog.provide('specview.view.DoubleBondRenderer');
goog.require('specview.view.BondRenderer');

/**
 * Class to render an double bond object to a graphics representation
 * 
 * 
 * @param {goog.graphics.AbstractGraphics}
 *            graphics to draw on.
 * @param {Object=}
 *            opt_config override default configuration
 * @constructor
 * @extends {specview.view.BondRenderer}
 */
specview.view.DoubleBondRenderer = function(graphics, opt_config) {
	specview.view.BondRenderer.call(this, graphics,
			specview.view.DoubleBondRenderer.defaultConfig, opt_config);
}
goog.inherits(specview.view.DoubleBondRenderer, specview.view.BondRenderer);

function triangleSign(a, b, c) {
	return (a.x - c.x) * (b.y - c.y) - (a.y - c.y) * (b.x - c.x);
}

function isOnSameSide(bond, p1, p2) {
	return (triangleSign(bond.source.coord, bond.target.coord, p1)
			* triangleSign(bond.source.coord, bond.target.coord, p2) > 0);
}
/**
 * tests if two points are on the same side of a line returns: false if both
 * points are on same side true if both points are on opposite sides. or if at
 * least one is on the line
 * 
 * @param {goog.math.Vec2}
 *            p1 first point
 * @param {goog.math.Vec2}
 *            p2 second point
 * @param {goog.math.Vec2}
 *            p_line a point on the line
 * @param {goog.math.Vec2}
 *            normalized line vector
 * @return {boolean}
 */
specview.view.DoubleBondRenderer.pointsOnSameSideOfLine = function(p1, p2, p_line,
		normal) {
	return goog.math.Vec2.dot(normal, goog.math.Vec2.difference(p1, p_line))
			* goog.math.Vec2.dot(normal, goog.math.Vec2.difference(p2, p_line)) > 0;
}

specview.view.DoubleBondRenderer.prototype.render = function(bond, transform,
		bondPath) {

	this.setTransform(transform);

	var ring = specview.view.DoubleBondRenderer.getFirstRing(bond);

	// create the bondvector
	var bv = goog.math.Vec2.fromCoordinate(goog.math.Coordinate.difference(
			bond.target.coord, bond.source.coord));

	bv.scale(1 / this.config.get("bond")['width-ratio']);

	// create a vector orthogonal to the bond vector
	var orthogonal = new goog.math.Vec2(-bv.y, bv.x);

	var space = bv.clone().normalize().scale(
			this.config.get('bond')['symbol-space']);

	if (ring) {
		// check the side, invert orthogonal if needed
		var side = goog.math.Coordinate.sum(bond.source.coord, orthogonal);
		var line = new specview.math.Line(bond.source.coord, bond.target.coord);
		if (!line.isSameSide(ring.getCenter(), side)) {
			orthogonal.invert();
		}
		// goog.asserts.assert(goog.math.Coordinate.distance(side,
		// ring.getCenter()) < goog.math.Coordinate.distance(bond.source.coord,
		// ring.getCenter()));
		// inner line coords
		var coord1 = goog.math.Coordinate.sum(bond.source.coord, orthogonal);
		var coord2 = goog.math.Coordinate.sum(bond.target.coord, orthogonal);
		// outer line coords
		var coord3 = bond.source.coord;
		var coord4 = bond.target.coord;

		// adjust for symbols if needed
		if (specview.view.BondRenderer.hasSymbol(bond.source)) {
			coord1 = goog.math.Coordinate.sum(coord1, space);
			coord3 = goog.math.Coordinate.sum(coord3, space)
		} else {
			coord1 = goog.math.Coordinate.sum(coord1, bv);
		}
		if (specview.view.BondRenderer.hasSymbol(bond.target)) {
			coord2 = goog.math.Coordinate.difference(coord2, space);
			coord4 = goog.math.Coordinate.difference(coord4, space)
		} else {
			coord2 = goog.math.Coordinate.difference(coord2, bv);
		}

	} else {
		orthogonal.scale(0.5);
		orthogonal=this.limitDoubleBondLinesDistance(orthogonal);

		var coord1 = goog.math.Coordinate.sum(bond.source.coord, orthogonal);
		var coord2 = goog.math.Coordinate.sum(bond.target.coord, orthogonal);
		var coord3 = goog.math.Coordinate.difference(bond.source.coord,
				orthogonal);
		var coord4 = goog.math.Coordinate.difference(bond.target.coord,
				orthogonal);

		// adjust for symbols if needed
		if (specview.view.BondRenderer.hasSymbol(bond.source)) {
			coord1 = goog.math.Coordinate.sum(coord1, space);
			coord3 = goog.math.Coordinate.sum(coord3, space)
		}
		if (specview.view.BondRenderer.hasSymbol(bond.target)) {
			coord2 = goog.math.Coordinate.difference(coord2, space);
			coord4 = goog.math.Coordinate.difference(coord4, space)
		}

	}
	var coords = transform.transformCoords( [ coord1, coord2, coord3, coord4 ]);

	bondPath.moveTo(coords[0].x, coords[0].y);
	bondPath.lineTo(coords[1].x, coords[1].y);
	bondPath.moveTo(coords[2].x, coords[2].y);
	bondPath.lineTo(coords[3].x, coords[3].y);

};

/**
 * 
 * @return{specview.ring.Ring} first ring that contains this bond
 */
specview.view.DoubleBondRenderer.getFirstRing = function(bond) {
	return goog.array.find(bond.molecule.getRings(), function(ring) {
		return goog.array.contains(ring.bonds, bond);
	});
}

/**
 * Double bond lines distance should not increase unlimitedly.
 * @return{goog.math.Vec2} limited orthogonal
 */
specview.view.DoubleBondRenderer.prototype.limitDoubleBondLinesDistance = function(orthogonal) {
	var limit=0.08;
	if (Math.abs(orthogonal.x)>limit) {
		var corr=orthogonal.x/limit;
		orthogonal.x = orthogonal.x/corr;
		orthogonal.y = orthogonal.y/corr;
	}
	if (Math.abs(orthogonal.y)>limit) {
		var corr=orthogonal.y/limit;
		orthogonal.x = orthogonal.x/corr;
		orthogonal.y = orthogonal.y/corr;
	}

	return orthogonal;
}
