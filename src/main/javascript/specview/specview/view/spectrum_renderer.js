/**
 * Copyright 2011 Mark Rijnbeek (markr@ebi.ac.uk)
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
goog.provide('specview.view.SpectrumRenderer');
goog.require('specview.view.Renderer');
goog.require('goog.debug.Logger');
goog.require('specview.graphics.ElementArray');
goog.require('goog.array');

/**
 * Class to render a spectrum, a list of peaks really
 * 
 * @param {goog.graphics.AbstractGraphics} something to draw on
 * @param {Object=} opt_config override default configuration
 * @constructor
 * @extends {specview.view.Renderer}
 */
specview.view.SpectrumRenderer = function(graphics, opt_config, opt_box) {
	specview.view.Renderer.call(this, graphics, specview.view.SpectrumRenderer.defaultConfig, opt_config);

    this.box= opt_box;
}
goog.inherits(specview.view.SpectrumRenderer, specview.view.Renderer);

/**
 * TODO doc
 */
specview.view.SpectrumRenderer.prototype.setBoundsBasedOnMolecule = function(molecule) {
    this.box = molecule.getBoundingBox();
    //this.box.top+=5;
    //this.box.bottom+=5;
    this.box.right+=5;
    this.box.left+=5;


}

/**
 * TODO doc
 */
specview.view.SpectrumRenderer.prototype.render = function(spectrum, transform) {
    this.logger.info("rendering spectrum")

    this.setTransform(transform);
    //this.renderBoundingBox(this.box); 

}

specview.view.SpectrumRenderer.logger = goog.debug.Logger.getLogger('specview.view.SpectrumRenderer');

