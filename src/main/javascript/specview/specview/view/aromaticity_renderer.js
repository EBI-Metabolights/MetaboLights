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
 */
goog.provide('specview.view.AromaticityRenderer');
goog.require('specview.view.BondRenderer');


/**
 * Class to render ring aromaticity to a graphics representation.
 * 
 * @param {goog.graphics.AbstractGraphics} graphics to draw on.
 * @param {Object=} opt_config override default configuration
 * @constructor
 * @extends {specview.view.BondRenderer}
 */
specview.view.AromaticityRenderer = function(graphics, opt_config) {
    specview.view.BondRenderer.call(
    this,
    graphics,
    specview.view.AromaticityRenderer.defaultConfig,
    opt_config);
}
goog.inherits(specview.view.AromaticityRenderer, specview.view.BondRenderer);


/**
 * Renders ring aromaticity in omelet style.
 * @param {specview.ring.Ring}
 *            ring
 * @param {specview.graphics.AffineTransform}
 *            transform
 * @param {specview.graphics.ElementArray=} opt_element_array
 * @return {specview.graphics.ElementArray}
 */

specview.view.AromaticityRenderer.prototype.render = function(ring, transform, bondPath) {

    this.setTransform(transform);

    var aromaticCircle = new goog.graphics.Path();
    var ringCenter = this.transform.transformCoords([ring.getCenter()])[0];

    // Radius of the aromatic circle proportional to the ring size	
    var circleRadiusProportion = 0.5;
    //Assumption: ring is symmetrical, so pick any one of the ring atom coords
    var anyAtomCoord = this.transform.transformCoords([ring.atoms[0].coord])[0];
    var spokeSize = goog.math.Coordinate.distance(ringCenter, anyAtomCoord)
    var omeletRadius = spokeSize * circleRadiusProportion;

    aromaticCircle.moveTo(ringCenter.x + (omeletRadius), ringCenter.y);
    aromaticCircle.arcTo(omeletRadius, omeletRadius, 0, 180);
    aromaticCircle.moveTo(ringCenter.x + (omeletRadius), ringCenter.y);
    aromaticCircle.arcTo(omeletRadius, omeletRadius, 0, -180);

}
