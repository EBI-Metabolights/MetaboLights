goog.provide('specview.model.NMRSpec');
goog.require('specview.model.Molecule');
goog.require('specview.model.Peak');
goog.require('goog.array');
goog.require('goog.debug.Logger');
/**
 * TEMP class for demo purpose
 * Constructor
 */
specview.model.NMRSpec = function(mol, peekaboo) {
	this.molecule = mol;
	this.peaks=peekaboo;
	this.logger.info("#peaks "+this.peaks.length)
}
specview.model.NMRSpec.prototype.logger = goog.debug.Logger.getLogger('specview.model.NMRSpec');