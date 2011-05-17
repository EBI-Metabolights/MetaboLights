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
};
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
};
specview.view.DoubleBondRenderer.prototype.render = function(bond, transform,
		bondPath) {
	this.logger.info("double bond coord: "+bond.source.coord+" other: "+bond.source.pixelCoordinates)

	this.setTransform(transform);

	var ring = specview.view.DoubleBondRenderer.getFirstRing(bond);

	// create the bondvector
	var bv = goog.math.Vec2.fromCoordinate(goog.math.Coordinate.difference(bond.target.coord, bond.source.coord));

//	this.logger.info("bond: "+bv);
//	this.logger.info(goog.math.Coordinate.difference(bond.target.coord, bond.source.coord));
	bv.scale(1 / this.config.get("bond")['width-ratio']);

	//this.logger.info("before: "+bv);
	
	// create a vector orthogonal to the bond vector
	var orthogonal = new goog.math.Vec2(-bv.y, bv.x);

	var space = bv.clone().normalize().scale(this.config.get('bond')['symbol-space']);

	
	if (ring) {
		// check the side, invert orthogonal if needed
		var side = goog.math.Coordinate.sum(bond.source.coord, orthogonal);
		var line = new specview.math.Line(bond.source.coord, bond.target.coord);
//		this.logger.info("Longueur: "+line.getLineLength());
		if (!line.isSameSide(ring.getCenter(), side)) {
			orthogonal.invert();
		}
		var coord1 = goog.math.Coordinate.sum(bond.source.coord, orthogonal);
		var coord2 = goog.math.Coordinate.sum(bond.target.coord, orthogonal);
		// outer line coords
		var coord3 = bond.source.coord;
		var coord4 = bond.target.coord;

		// adjust for symbols if needed
		if (this.hasSymbol(bond.source)) {
			coord1 = goog.math.Coordinate.sum(coord1, space);
			coord3 = goog.math.Coordinate.sum(coord3, space);
		} else {
			coord1 = goog.math.Coordinate.sum(coord1, bv);
		}
		if (this.hasSymbol(bond.target)) {
			coord2 = goog.math.Coordinate.difference(coord2, space);
			coord4 = goog.math.Coordinate.difference(coord4, space);
		} else {
			coord2 = goog.math.Coordinate.difference(coord2, bv);
		}

	} else {
//		this.logger.info(space)
		orthogonal.scale(0.5);
		orthogonal=this.limitDoubleBondLinesDistance(orthogonal);

		var coord1 = goog.math.Coordinate.sum(bond.source.coord, orthogonal);
		var coord2 = goog.math.Coordinate.sum(bond.target.coord, orthogonal);
		var coord3 = goog.math.Coordinate.difference(bond.source.coord,
				orthogonal);
		var coord4 = goog.math.Coordinate.difference(bond.target.coord,
				orthogonal);

		// adjust for symbols if needed
		if (this.hasSymbol(bond.source)) {
			coord1 = goog.math.Coordinate.sum(coord1, space);
			coord3 = goog.math.Coordinate.sum(coord3, space);
		}
		if (this.hasSymbol(bond.target)) {
			coord2 = goog.math.Coordinate.difference(coord2, space);
			coord4 = goog.math.Coordinate.difference(coord4, space);
		}


	
//		this.logger.info(transform.transformCoords([coord2]));
		
	}
	var arrayOfCoordinates = [ coord1, coord2, coord3, coord4 ];
	coords = transform.transformCoords( arrayOfCoordinates );

	var line1 = new specview.math.Line(coords[0],coords[1]);
	var line2 = new specview.math.Line(coords[2],coords[3]);
//	this.logger.info("longueur de ligne 1 = "+line1.getLineLength()+"  longueur de ligne 2 = "+line2.getLineLength());
	
	var ecart1 = new specview.math.Line(line1.source,line2.source);
	var ecart2 = new specview.math.Line(line1.target,line2.target);
//	this.logger.info("ecart1 : "+parseInt(ecart1.getLineLength())+"  ecart2 : "+parseInt(ecart2.getLineLength())+" rapport : "+ecart2.getLineLength()/line1.getLineLength());
//	this.logger.info(coords)
	var orthogonal1 = line1.getOrthogonalLine(line2);

	bondPath.moveTo(coords[0].x, coords[0].y);//inside the ring
	bondPath.lineTo(coords[1].x, coords[1].y);//inside the ring
	bondPath.moveTo(coords[2].x, coords[2].y);//outside the ring
	bondPath.lineTo(coords[3].x, coords[3].y);//outside the ring	
};
//

/*
specview.view.DoubleBondRenderer.prototype.render = function(bond, transform,
		bondPath) {
 
	var shouldBeTransformed = true ;
	this.setTransform(transform);

	var ring = specview.view.DoubleBondRenderer.getFirstRing(bond);

	// create the bondvector
	
	var bv = goog.math.Vec2.fromCoordinate(goog.math.Coordinate.difference(bond.target.pixelCoordinates, bond.source.pixelCoordinates));
	
	bv.scale(1 / this.config.get("bond")['width-ratio']);

	// create a vector orthogonal to the bond vector
	var orthogonal = new goog.math.Vec2(-bv.y, bv.x);
//	this.logger.info(orthogonal);

	var space = bv.clone().normalize().scale(this.config.get('bond')['symbol-space']);
//	this.logger.info("space: "+ this.config.get('bond')['symbol-space']);

	if (ring) {
		// check the side, invert orthogonal if needed
		var side = goog.math.Coordinate.sum(bond.source.pixelCoordinates, orthogonal);
//		this.logger.info("coordinates: "+bond.source.pixelCoordinates+" side: "+side+"orthogonal: "+orthogonal)
		var line = new specview.math.Line(bond.source.pixelCoordinates, bond.target.pixelCoordinates);
	//	this.logger.info(ring.getCenter()+"   "+transform.transformCoords([ring.getCenter()])[0]);
		//this.logger.info("center : "+ring.getCenter()+" side : "+side)
		if (!line.isSameSide(transform.transformCoords([ring.getCenter()])[0], side)) {
//			alert("invertion before :"+orthogonal)
//			this.logger.info("invert: "+orthogonal.invert())
			orthogonal.invert();
//			alert("invertion after :"+orthogonal)
		}
		var coord1 = goog.math.Coordinate.sum(bond.source.pixelCoordinates, orthogonal);
		var coord2 = goog.math.Coordinate.sum(bond.target.pixelCoordinates, orthogonal);
		// outer line coords
		var coord3 = bond.source.pixelCoordinates;
		var coord4 = bond.target.pixelCoordinates;

		// adjust for symbols if needed
		if (this.hasSymbol(bond.source)) {
			coord1 = goog.math.Coordinate.sum(coord1, space);
			coord3 = goog.math.Coordinate.sum(coord3, space);
		} else {
			coord1 = goog.math.Coordinate.sum(coord1, bv);
		}
		if (this.hasSymbol(bond.target)) {
			coord2 = goog.math.Coordinate.difference(coord2, space);
			coord4 = goog.math.Coordinate.difference(coord4, space);
		} else {
			coord2 = goog.math.Coordinate.difference(coord2, bv);
		}

	} else {
		shouldBeTransformed = false;
		orthogonal.scale(0.5);
		orthogonal=this.limitDoubleBondLinesDistance(orthogonal);

		var coord1 = goog.math.Coordinate.sum(bond.source.pixelCoordinates, orthogonal);
		var coord2 = goog.math.Coordinate.sum(bond.target.pixelCoordinates, orthogonal);
		var coord3 = goog.math.Coordinate.difference(bond.source.pixelCoordinates,
				orthogonal);
		var coord4 = goog.math.Coordinate.difference(bond.target.pixelCoordinates,
				orthogonal);

		// adjust for symbols if needed
		if (this.hasSymbol(bond.source)) {
			coord1 = goog.math.Coordinate.sum(coord1, space);
			coord3 = goog.math.Coordinate.sum(coord3, space);
		}
		if (this.hasSymbol(bond.target)) {
			coord2 = goog.math.Coordinate.difference(coord2, space);
			coord4 = goog.math.Coordinate.difference(coord4, space);
		}

		var line = new specview.math.Line(coord1,coord2);
		var ecart = line.getLineLength()/4.8;
		this.logger.info(coord1+" ; "+coord2+" ; "+coord3+" ; "+coord4)
		this.logger.info(ecart)
//		coord1 = transform.transformCoords([coord1])[0];
//		coord2 = transform.transformCoords([coord2])[0];
//		coord3 = transform.transformCoords([coord3])[0];
//		coord4 = transform.transformCoords([coord4])[0];
		if(coord1.x==coord2.x){
			this.logger.info("first case");
			coord1.x=coord1.x+ecart/2;
			coord2.x=coord2.x+ecart/2;
			coord3.x=coord3.x=-ecart/2;
			coord4.x=coord4.x=-ecart/2;
		}else if(coord1.y==coord2.y){
			this.logger.info("second case");
			coord1.y=coord1.y+ecart/2;
			coord2.y=coord2.y+ecart/2;
			coord3.y=coord3.y=-ecart/2;
			coord4.y=coord4.y=-ecart/2;
		}else{
			this.logger.info("third case");
			this.logger.info(parseInt(coord1.x)+","+parseInt(coord1.y)+"  ;  "+parseInt(coord2.x)+","+parseInt(coord2.y)+"  ;  "+parseInt(coord3.x)+","+parseInt(coord3.y)+"  ;  "+parseInt(coord4.x)+","+parseInt(coord4.y));
			coord1.x=coord1.x+ecart/3;coord1.y=coord1.y+ecart/3;
			coord2.x=coord2.x+ecart/3;coord2.y=coord2.y+ecart/3;
			coord3.x=coord3.x-ecart/3;coord3.y=coord3.y-ecart/3;
			coord2.x=coord4.x-ecart/4;coord4.y=coord4.y-ecart/4;
			this.logger.info(parseInt(coord1.x)+","+parseInt(coord1.y)+"  ;  "+parseInt(coord2.x)+","+parseInt(coord2.y)+"  ;  "+parseInt(coord3.x)+","+parseInt(coord3.y)+"  ;  "+parseInt(coord4.x)+","+parseInt(coord4.y))
		}
		
	}	
	
	var arrayOfCoordinates = [ coord1, coord2, coord3, coord4 ];
//	var coords = shouldBeTransformed ? transform.transformCoords( arrayOfCoordinates ) : arrayOfCoordinates ;
	
//	var line1 = new specview.math.Line(coords[0],coords[1]);
//	var line2 = new specview.math.Line(coords[2],coords[3]);
//	var ecart1 = new specview.math.Line(line1.source,line2.source);
//	var ecart2 = new specview.math.Line(line1.target,line2.target);
///	this.logger.info(coords[0]+" "+coords[1]+" "+coords[2]+" "+coords[3]);
//	this.logger.info("ecart1 : "+ecart1.getLineLength()+"  ecart2 : "+ecart2.getLineLength()+" rapport : "+ecart2.getLineLength()/line1.getLineLength());
	bondPath.moveTo(coord1.x, coord1.y);
	bondPath.lineTo(coord2.x, coord2.y);
//	bondPath.moveTo(coord3.x, coord3.y);
//	bondPath.lineTo(coord4.x, coord4.y);
//	this.logger.info(arrayOfCoordinates)
//	bondPath.moveTo(coords[0].x, coords[0].y);
//	bondPath.lineTo(coords[1].x, coords[1].y);
//	bondPath.moveTo(coords[2].x, coords[2].y);
//	bondPath.lineTo(coords[3].x, coords[3].y);

};
*/

/**
 * 
 * @return{specview.ring.Ring} first ring that contains this bond
 */


specview.view.DoubleBondRenderer.getFirstRing = function(bond) {
	return goog.array.find(bond.molecule.getRings(), function(ring) {
		return goog.array.contains(ring.bonds, bond);
	});
};

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
};
