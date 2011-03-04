goog.provide('specview.view.BondRendererFactory');
goog.require('specview.view.SingleBondRenderer');
goog.require('specview.view.DoubleBondRenderer');
goog.require('specview.view.TripleBondRenderer');
goog.require('specview.view.QuadrupleBondRenderer');
goog.require('specview.view.SingleUpBondRenderer');
goog.require('specview.view.SingleDownBondRenderer');
goog.require('specview.view.SingleUpOrDownBondRenderer');
goog.require('specview.model.Bond');
goog.require('goog.object');
goog.require('goog.reflect');

/**
 * factory class for BondRenderers
 * 
 * @param {goog.graphics.AbstractGraphics}
 *            graphics
 * @param {Object=}
 *            opt_config
 * @constructor
 */
specview.view.BondRendererFactory = function( graphics, opt_config) {

	this.graphics = graphics;
	this.config = new goog.structs.Map();
	if (opt_config) {
		this.config.addAll(opt_config); // merge optional config
	}
};

/**
 * The logger for this class.
 * 
 * @type {goog.debug.Logger}
 * @protected
 */
specview.view.BondRendererFactory.prototype.logger = goog.debug.Logger
		.getLogger('specview.view.BondRendererFactory');

/**
 * a map from bond order to renderer getter method
 */
specview.view.BondRendererFactory.ORDER_RENDERER = goog.object
		.transpose(goog.reflect
				.object(
						specview.view.BondRendererFactory,
						{
			getSingleBondRenderer : specview.model.Bond.ORDER.SINGLE,
			getDoubleBondRenderer : specview.model.Bond.ORDER.DOUBLE,
			getTripleBondRenderer : specview.model.Bond.ORDER.TRIPLE,
			getQuadrupleBondRenderer : specview.model.Bond.ORDER.QUADRUPLE
		}));

/**
 * a map from bond stereo type to renderer getter method
 */
specview.view.BondRendererFactory.STEREO_RENDERER = goog.object
		.transpose(goog.reflect
				.object(
						specview.view.BondRendererFactory,
						{
							getSingleBondNotStereoRenderer : specview.model.Bond.STEREO.NOT_STEREO,
							getSingleBondUpRenderer : specview.model.Bond.STEREO.UP,
							getSingleBondDownRenderer : specview.model.Bond.STEREO.DOWN,
							getSingleBondUpOrDownRenderer : specview.model.Bond.STEREO.UP_OR_DOWN
						}));

/**
 * factory method to get appropriate renderer for a bond based on its order and
 * stereo type
 * 
 * @param {specview.model.Bond}
 *            bond, the bond in need of a renderer
 * 
 * @return {specview.view.BondRenderer}
 */
specview.view.BondRendererFactory.prototype.get = function(bond) {
	return this[specview.view.BondRendererFactory.ORDER_RENDERER[bond.order]].apply(this, [bond.stereo]);
};

/**
 * helper method to factory to get appropriate renderer based on stereo type
 * 
 * @param {specview.model.Bond.STEREO}
 *            stereo type of bond in need of a renderer
 * 
 * @return {specview.view.BondRenderer}
 */
specview.view.BondRendererFactory.prototype.getSingleBondRenderer = function(
		stereo) {
	return this[specview.view.BondRendererFactory.STEREO_RENDERER[stereo]].apply(this);
};

specview.view.BondRendererFactory.prototype.getSingleBondNotStereoRenderer = function()
{
	if (!this.singleNotStereoBondRenderer) {
		this.singleNotStereoBondRenderer = new specview.view.SingleBondRenderer(
				this.graphics, this.config);
	}
	return this.singleNotStereoBondRenderer;
};

specview.view.BondRendererFactory.prototype.getSingleBondUpRenderer = function()
{
	if (!this.singleUpBondRenderer) {
		this.singleUpBondRenderer = new specview.view.SingleUpBondRenderer(
				this.graphics, this.config);
	}
	return this.singleUpBondRenderer;
};

specview.view.BondRendererFactory.prototype.getSingleBondDownRenderer = function()
{
	if (!this.singleDownBondRenderer) {
		this.singleDownBondRenderer = new specview.view.SingleDownBondRenderer(
				this.graphics, this.config);
	}
	return this.singleDownBondRenderer;
};

specview.view.BondRendererFactory.prototype.getSingleBondUpOrDownRenderer = function()
{
	if (!this.singleUpOrDownBondRenderer) {
		this.singleUpOrDownBondRenderer = new specview.view.SingleUpOrDownBondRenderer(
				this.graphics, this.config);
	}
	return this.singleUpOrDownBondRenderer;
};

specview.view.BondRendererFactory.prototype.getDoubleBondRenderer = function()
{
	if (!this.doubleBondRenderer) {
		this.doubleBondRenderer = new specview.view.DoubleBondRenderer(
				this.graphics, this.config);
	}
	return this.doubleBondRenderer;
};

specview.view.BondRendererFactory.prototype.getTripleBondRenderer = function()
{
	if (!this.tripleBondRenderer) {
		this.tripleBondRenderer = new specview.view.TripleBondRenderer(
				this.graphics, this.config);
	}
	return this.tripleBondRenderer;
};

specview.view.BondRendererFactory.prototype.getQuadrupleBondRenderer = function()
{
	if (!this.quadrupleBondRenderer) {
		this.quadrupleBondRenderer = new specview.view.QuadrupleBondRenderer(
				this.graphics, this.config);
	}
	return this.quadrupleBondRenderer;
};

