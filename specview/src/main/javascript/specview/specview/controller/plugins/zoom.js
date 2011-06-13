/**
 * @license Copyright 2010 Paul Novak (paul@wingu.com)
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
 * @author paul@wingu.com (Paul Novak)
 */
goog.provide('specview.controller.plugins.Zoom');
goog.require('goog.debug.Logger');

// goog.exportSymbol('specview.controller.plugins.Zoom.COMMAND.ZOOM_IN',
// specview.controller.plugins.Zoom.COMMAND.ZOOM_IN);
// goog.exportSymbol('specview.controller.plugins.Zoom.COMMAND.ZOOM_OUT',
// specview.controller.plugins.Zoom.COMMAND.ZOOM_OUT);

/**
 * @constructor
 * @extends{specviewn.controller.Plugin}s
 */
specview.controller.plugins.Zoom = function() {
	this.initialCoordinates = null;
	this.finalCoordinates = null;
	this.rectangle = null;
	
	specview.controller.Plugin.call(this);
}
goog.inherits(specview.controller.plugins.Zoom, specview.controller.Plugin);
goog.exportSymbol('specview.controller.plugins.Zoom',
		specview.controller.plugins.Zoom);

/**
 * Commands implemented by this plugin.
 * 
 * @enum {string}
 */
specview.controller.plugins.Zoom.COMMAND = {
	ZOOM_IN : 'zoomIn',
	ZOOM_OUT : 'zoomOut'
};

/**
 * Inverse map of execCommand strings to
 * {@link specview.controller.plugins.Zoom.COMMAND} constants. Used to determine
 * whether a string corresponds to a command this plugin handles
 * 
 * @type {Object}
 * @private
 */
specview.controller.plugins.Zoom.SUPPORTED_COMMANDS_ = goog.object
		.transpose(specview.controller.plugins.Zoom.COMMAND);

/** @inheritDoc */
specview.controller.plugins.Zoom.prototype.getTrogClassId = goog.functions
		.constant(specview.controller.plugins.Zoom.COMMAND);

/** @inheritDoc */
specview.controller.plugins.Zoom.prototype.isSupportedCommand = function(command) {
	return command in specview.controller.plugins.Zoom.SUPPORTED_COMMANDS_;
};

/** @inheritDoc */
specview.controller.plugins.Zoom.prototype.execCommand = function(command,
		var_args) {
	try {
		var current = this.editorObject.getScaleFactor();
		if (command == specview.controller.plugins.Zoom.COMMAND.ZOOM_IN) {
			this.editorObject.setScaleFactor(current * 1.1);
		} else if (command == specview.controller.plugins.Zoom.COMMAND.ZOOM_OUT) {
			this.editorObject.setScaleFactor(current * 0.9);
		}
		this.editorObject.setModelsSilently(this.editorObject.getModels());
	} catch (e) {
		this.logger.info(e);
	}
};

/**
 * The logger for this class.
 * 
 * @type {goog.debug.Logger}
 * @protected
 */
specview.controller.plugins.Zoom.prototype.logger = goog.debug.Logger
		.getLogger('specview.controller.plugins.Zoom');
