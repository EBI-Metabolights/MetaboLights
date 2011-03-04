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
goog.provide('specview.controller.Plugin');
goog.require('goog.events.EventTarget');
goog.require('goog.functions');
goog.require('goog.debug.Logger');
goog.require('goog.object');
goog.require('goog.reflect');

/**
 * Abstract API for editor plugins.
 * 
 * @constructor
 * @extends {goog.events.EventTarget}
 */
specview.controller.Plugin = function() {
	goog.events.EventTarget.call(this);

	/**
	 * Whether this plugin is enabled for the registered field object.
	 * 
	 * @type {boolean}
	 * @private
	 */
	this.enabled_ = this.activeOnUneditableEditor();
};
goog.inherits(specview.controller.Plugin, goog.events.EventTarget);

/**
 * @return {boolean} If true, editor will not disable the command when the
 *         editor becomes uneditable.
 */
specview.controller.Plugin.prototype.activeOnUneditableEditor = goog.functions.FALSE;

/**
 * Registers the editor object for use with this plugin.
 * 
 * @param {specview.controller.Controller}
 *            fieldObject The editor object.
 */
specview.controller.Plugin.prototype.registerEditorObject = function(editorObject) {
	this.editorObject = editorObject;
	goog.array.forEach(this.getKeyboardShortcuts(), function(shortcut) {
		editorObject.registerShortcut(shortcut.id, shortcut.key);
	});
};

/**
 * override to return list of keyboard shortcuts
 */
specview.controller.Plugin.prototype.getKeyboardShortcuts = function() {
	return [];
}

/**
 * Enables this plugin for the specified, registered editor object. A
 * editor object should only be enabled when it is loaded.
 * 
 * @param {specview.controller.Controller}
 *            editorObject The field object.
 */
specview.controller.Plugin.prototype.enable = function(editorObject) {
	if (this.editorObject == editorObject) {
		this.enabled_ = true;
	} else {
		this.logger
				.severe('Trying to enable an unregistered field with ' + 'this plugin.');
	}
};

/**
 * Disables this plugin for the specified, registered editor object.
 * 
 * @param {specview.controller.Controller}
 *            editorObject The editor object.
 */
specview.controller.Plugin.prototype.disable = function(editorObject) {
	this.logger.info('disable');
	if (this.editorObject == editorObject) {
		this.enabled_ = false;
	} else {
		this.logger
				.severe('Trying to disable an unregistered field ' + 'with this plugin.');
	}
};

/**
 * The logger for this plugin.
 * 
 * @type {goog.debug.Logger}
 * @protected
 */
specview.controller.Plugin.prototype.logger = goog.debug.Logger
		.getLogger('specview.controller.Plugin');

/**
 * Returns whether this plugin is enabled for the editor object.
 * 
 * @param {specview.controller.Controller}
 *            editorObject The editor object.
 * @return {boolean} Whether this plugin is enabled for the editor
 *         object.
 */
specview.controller.Plugin.prototype.isEnabled = function(editorObject) {
	return this.editorObject == editorObject ? this.enabled_ : false;
};

/** @inheritDoc */
specview.controller.Plugin.prototype.disposeInternal = function() {
	if (this.editorObject) {
		this.unregisterEditorObject(this.editorObject);
	}

	specview.controller.Plugin.superClass_.disposeInternal.call(this);
};

/**
 * Unregisters and disables this plugin for the current editor object.
 * 
 */
specview.controller.Plugin.prototype.unregisterEditorObject = function() {
	if (this.editorObject) {
		this.disable(this.editorObject);
		this.editorObject = null;
	}
};

/**
 * Indicates if this plugin should be automatically disposed when the registered
 * editor is disposed. This should be changed to false for plugins used as
 * multi-editor plugins.
 * 
 * @type {boolean}
 * @private
 */
specview.controller.Plugin.prototype.autoDispose_ = true;

/**
 * Set if this plugin should automatically be disposed when the registered
 * editor is disposed.
 * 
 * @param {boolean}
 *            autoDispose Whether to autoDispose.
 */
specview.controller.Plugin.prototype.setAutoDispose = function(autoDispose) {
	this.autoDispose_ = autoDispose;
};

/**
 * @return {boolean} Whether or not this plugin should automatically be disposed
 *         when it's registered edtior is disposed.
 */
specview.controller.Plugin.prototype.isAutoDispose = function() {
	return this.autoDispose_;
};

/**
 * An enum of operations that plugins may support.
 * 
 * @enum {number}
 */
specview.controller.Plugin.Op = {
	KEYDOWN : 1,
	KEYPRESS : 2,
	KEYUP : 3,
	SELECTION : 4,
	SHORTCUT : 5,
	EXEC_COMMAND : 6,
	QUERY_COMMAND : 7,
	MOUSEDOWN : 8,
	MOUSEUP : 9,
	MOUSEOVER : 10,
	MOUSEOUT : 11,
	MOUSEMOVE : 12,
	//ATOM_MOUSEOVER : 13,
	ATOM_MOUSEOUT : 14,
	ATOM_MOUSEDOWN : 15,
	BOND_MOUSEOVER : 16,
	BOND_MOUSEOUT : 17,
	BOND_MOUSEDOWN : 18,
	ARROW_MOUSEOVER : 19,
	ARROW_MOUSEOUT : 20,
	ARROW_MOUSEDOWN : 21,
	PLUS_MOUSEOVER : 22,
	PLUS_MOUSEOUT : 23,
	PLUS_MOUSEDOWN : 24,
	PASTE : 25,
	DBLCLICK : 26
};

/**
 * @enum
 */
specview.controller.Plugin.IRREPRESSIBLE_OPS = {};

/**
 * @return {boolean} If true, editor will not disable the command when the field
 *         becomes uneditable.
 */
specview.controller.Plugin.prototype.activeOnUneditableEditors = goog.functions.FALSE;

/**
 * A map from plugin operations to the names of the methods that invoke those
 * operations.
 */
specview.controller.Plugin.OPCODE = goog.object.transpose(goog.reflect.object(
		specview.controller.Plugin, {
			handleKeyDown : specview.controller.Plugin.Op.KEYDOWN,
			handleKeyPress : specview.controller.Plugin.Op.KEYPRESS,
			handleKeyUp : specview.controller.Plugin.Op.KEYUP,
			handleSelectionChange : specview.controller.Plugin.Op.SELECTION,
			handleKeyboardShortcut : specview.controller.Plugin.Op.SHORTCUT,
			execCommand : specview.controller.Plugin.Op.EXEC_COMMAND,
			queryCommandValue : specview.controller.Plugin.Op.QUERY_COMMAND,
			handleMouseDown : specview.controller.Plugin.Op.MOUSEDOWN,
			handleMouseUp : specview.controller.Plugin.Op.MOUSEUP,
			handleMouseOver : specview.controller.Plugin.Op.MOUSEOVER,
			handleMouseOut : specview.controller.Plugin.Op.MOUSEOUT,
	handleMouseMove : specview.controller.Plugin.Op.MOUSEMOVE,
			handleAtomMouseOver : specview.controller.Plugin.Op.ATOM_MOUSEOVER,
			handleAtomMouseOut : specview.controller.Plugin.Op.ATOM_MOUSEOUT,
			handleAtomMouseDown : specview.controller.Plugin.Op.ATOM_MOUSEDOWN,
			handleBondMouseOver : specview.controller.Plugin.Op.BOND_MOUSEOVER,
			handleBondMouseOut : specview.controller.Plugin.Op.BOND_MOUSEOUT,
			handleBondMouseDown : specview.controller.Plugin.Op.BOND_MOUSEDOWN,
			handleArrowMouseOver : specview.controller.Plugin.Op.ARROW_MOUSEOVER,
			handleArrowMouseOut : specview.controller.Plugin.Op.ARROW_MOUSEOUT,
			handleArrowMouseDown : specview.controller.Plugin.Op.ARROW_MOUSEDOWN,
			handlePlusMouseOver : specview.controller.Plugin.Op.PLUS_MOUSEOVER,
			handlePlusMouseOut : specview.controller.Plugin.Op.PLUS_MOUSEOUT,
			handlePlusMouseDown : specview.controller.Plugin.Op.PLUS_MOUSEDOWN,
			handlePaste : specview.controller.Plugin.Op.PASTE,
			handleDoubleClick : specview.controller.Plugin.Op.DBLCLICK
		}));

/**
 * Handles execCommand. This default implementation handles dispatching
 * BEFORECHANGE, CHANGE, and SELECTIONCHANGE events, and calls
 * execCommandInternal to perform the actual command. Plugins that want to do
 * their own event dispatching should override execCommand, otherwise it is
 * preferred to only override execCommandInternal.
 * 
 * This version of execCommand will only work for single field plugins.
 * Multi-field plugins must override execCommand.
 * 
 * @param {string}
 *            command The command to execute.
 * @param {...*}
 *            var_args Any additional parameters needed to execute the command.
 * @return {*} The result of the execCommand, if any.
 */
specview.controller.Plugin.prototype.execCommand = function(command, var_args) {

	var silent = this.isSilentCommand(command);
//	if (!silent) {
//		this.editorObject.dispatchBeforeChange();
//	}

	try {
		this.editorObject.resetQueryablePlugins();
		var result = this.execCommandInternal.apply(this, arguments);
	} finally {

		if (!silent) {

			this.editorObject.dispatchChange();
			this.editorObject.dispatchCommandValueChange();

		}
	}

	return result;
};

/**
 * @param {string}
 *            command The command to check.
 * @return {boolean} If true, field will not dispatch change events for commands
 *         of this type.
 */
specview.controller.Plugin.prototype.isSilentCommand = goog.functions.FALSE;

/**
 * Whether the string corresponds to a command this plugin handles.
 * 
 * @param {string}
 *            command Command string to check.
 * @return {boolean} Whether the plugin handles this type of command.
 */
specview.controller.Plugin.prototype.isSupportedCommand = function(command) {
	return false;
};
