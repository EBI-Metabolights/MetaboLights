/**
 * @license Copyright 2010 Paul Novak (paul@wingu.com)
 *                    2011 Mark Rijnbeek (markr@ebi.ac.uk)
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

goog.provide("specview.controller.Controller");
goog.provide("specview.controller.Controller.EventType");
goog.require("specview.view.MoleculeRenderer");
goog.require("specview.view.SpectrumRenderer");
goog.require("goog.graphics");
goog.require('goog.events');
goog.require('goog.editor.BrowserFeature');
goog.require('goog.async.Delay');
goog.require('specview.controller.Plugin');
goog.require('specview.model.NeighborList');
goog.require('goog.ui.Prompt');
goog.require('goog.debug.Console');
goog.require('goog.ui.KeyboardShortcutHandler');
goog.require('specview.model.NMRdata');

/**
 * Controller for events on chemistry objects. 
 * 
 * @constructor
 * @extends {goog.events.EventTarget}
 */
specview.controller.Controller = function(element, opt_config) {
	goog.events.EventTarget.call(this);
	this.originalElement = element;
	this.id = element.id;
	this.editableDomHelper = goog.dom.getDomHelper(element);
	this.models = [];
	this.plugins_ = {};
	this.indexedPlugins_ = {};

	this.specObject=null;//the meta spec object
	
	for ( var op in specview.controller.Plugin.OPCODE) {
		this.indexedPlugins_[op] = [];
	}
	this.config = new goog.structs.Map(
			specview.controller.Controller.defaultConfig);
	if (opt_config) {
		this.config.addAll(opt_config); // merge optional config into defaults
	}

	this.graphics = goog.graphics.createGraphics(element.clientWidth,element.clientHeight);
	this.graphics.render(this.originalElement);
	this.moleculeRenderer = new specview.view.MoleculeRenderer(this.graphics,this.config);
    this.spectrumRenderer = new specview.view.SpectrumRenderer(this.graphics, this.config);
	
	this.isModified_ = false;
	this.isEverModified_ = false;

	/**
	 * @type {goog.events.EventHandler}
	 * @protected
	 */
	this.eventRegister = new goog.events.EventHandler(this);
	this.shortcutHandler = new goog.ui.KeyboardShortcutHandler(document);

	// Wrappers around this controller, to be disposed when the controller is disposed.
	this.wrappers_ = [];
	this.handleControllerLoad();
	this.loadState_ = specview.controller.Controller.LoadState_.EDITABLE;
	this.isModified_ = false;
	this.isEverModified_ = false;
	// currently selected model objects
	this.selected = [];
	this.neighborList = [];

};
goog.inherits(specview.controller.Controller, goog.events.EventTarget);
goog.exportSymbol('specview.controller.Controller',
		specview.controller.Controller);
/**
 * Sets the active controller id.
 * 
 * @param {?string} controllerId The active controller id.
 */
specview.controller.Controller.setActiveControllerId = function(controllerId) {
	specview.controller.Controller.activeControllerId_ = controllerId;
};

specview.controller.Controller.prototype.clearSelected = function() {
	this.selected.length = 0;
}

specview.controller.Controller.prototype.getSelected = function(){
	return this.selected;	
}

specview.controller.Controller.prototype.addSelected = function(obj){
	this.selected.push(obj);
}

specview.controller.Controller.prototype.setSpecObject=function(specObject){
	this.specObject=specObject;
}


/**
 * @return {goog.dom.DomHelper?} The dom helper for the editable node.
 */

specview.controller.Controller.prototype.getSpecObject=function(){
	return this.specObject;
}


specview.controller.Controller.prototype.getEditableDomHelper = function() {
	return this.editableDomHelper;
};

/**
 * @return {?string} The id of the active controller.
 */
specview.controller.Controller.getActiveControllerId = function() {
	return specview.controller.Controller.activeControllerId_;
};

/*
 * Only called to clear a molecule from the canvas
 */
specview.controller.Controller.prototype.clear = function() {
//	this.logger.info("Clearing graphics");
	this.graphics.clear();

	this.models = []; 
	this.neighborList = new specview.model.NeighborList( [], 1, .3);
	var fill = new goog.graphics.SolidFill(this.config.get("background").color);
	this.graphics.drawRect(0, 0, this.graphics.getSize().width, this.graphics.getSize().height, null, fill);
}

specview.controller.Controller.prototype.getScaleFactor = function() {
	return this.moleculeRenderer.scale_factor;
}

specview.controller.Controller.prototype.setScaleFactor = function(scale) {
	this.moleculeRenderer.transform = undefined; // to force new transform
	this.moleculeRenderer.setScaleFactor( scale);
}



/*
specview.controller.Controller.prototype.setModelsSilently = function(models) {
	this.clear();
	this.models = models; // the model objects we wand to put on canvas
	var objects = goog.array.flatten(goog.array.map(models, function(model) {
		if (model instanceof specview.model.Molecule) {
			return specview.model.NeighborList.moleculesToNeighbors( [ model ]);
		}else if(model instanceof  specview.model.NMRdata){
            //TODO, neighb list for spectrum thing spectrumToNeighBours
			alert(specview.model.NeighborList.moleculesToNeighbors( [ model.molecule ]))
			return specview.model.NeighborList.moleculesToNeighbors( [ model.molecule ]);
		}
	}));

	if (objects.length > 0) {
		this.neighborList = new specview.model.NeighborList(objects, 1, .3);
	}
	this.render();
};
*/

specview.controller.Controller.prototype.setModelsSilently = function(models) {
	this.clear();
	this.models = models; // the model objects we wand to put on canvas
	var objects = goog.array.flatten(goog.array.map(models, function(model) {
		if (model instanceof specview.model.Molecule) {
			return specview.model.NeighborList.moleculesToNeighbors( [ model ]);
		}else if(model instanceof  specview.model.NMRdata){
			return specview.model.NeighborList.metaSpecToNeighbors([model]); 
		}
	}));

	if (objects.length > 0) {
		this.neighborList = new specview.model.NeighborList(objects, 1, .3);
//		this.logger.info("Here is the neighborlist: "+this.neighborList);
	}
	this.render();
};


specview.controller.Controller.prototype.setModels = function(models){
	this.setModelsSilently(models);
}
goog.exportSymbol('specview.controller.Controller.prototype.setModels',	specview.controller.Controller.prototype.setModels);

specview.controller.Controller.prototype.render = function() {
    goog.array.forEach(this.models, function(model) {
        if (model instanceof specview.model.Molecule) {
            var margin = this.config.get("margin");
            molecule=model;
            atom_coords = goog.array.map(molecule.atoms,function(a) {return a.coord; });
            box = goog.math.Box.boundingBox.apply(null, atom_coords);
            ex_box = box.expand(margin, margin, margin, margin);
            scaleFactor = 0.95; 
            widthScaleLimitation = 1;
            trans = specview.graphics.AffineTransform.buildTransform(ex_box, widthScaleLimitation, this.graphics, scaleFactor);
            this.moleculeRenderer.render(molecule,trans);
        }
        
        
        if (model instanceof specview.model.NMRdata) {
            molecule=model.molecule;
            spectrum=model.spectrum;
            
            this.spectrumRenderer.setBoundsBasedOnMolecule(molecule);
//            this.logger.info("HERE IS THE SPECTRUM BOX\n:"+ this.spectrumRenderer.box);
//          this.spectrumRenderer.setBoundsBasedOnMetaSpecmetaSpecObject(model);
            
            
            
            atom_coords = goog.array.map(molecule.atoms,function(a) {return a.coord; });//the coords of the file. Simple array
            peak_coords = goog.array.map(spectrum.peakList,function(a) {return a.coord;});
//            alert(peak_coords);
            box = goog.math.Box.boundingBox.apply(null, atom_coords);
            margin = 0.3;//this.config.get("margin");
            ex_box = box.expand(margin, margin, margin, margin);
            scaleFactor = 0.90; 
            widthScaleLimitation = 0.4;
            trans = specview.graphics.AffineTransform.buildTransform(ex_box, widthScaleLimitation, this.graphics, scaleFactor);
            this.moleculeRenderer.render(molecule,trans);
            this.spectrumRenderer.render(spectrum,trans);
        }
    }, this);
};

goog.exportSymbol('specview.controller.Controller.prototype.render', specview.controller.Controller.prototype.render);


/**
 * Calls all the plugins of the given operation, in sequence, with the given
 * arguments. This is short-circuiting: once one plugin cancels the event, no
 * more plugins will be invoked.
 * 
 * @param {specview.controller.Plugin.Op}
 *            op A plugin op.
 * @param {...*}
 *            var_args The arguments to the plugin.
 * @return {boolean} True if one of the plugins cancel the event, false
 *         otherwise.
 * @private
 */
specview.controller.Controller.prototype.invokeShortCircuitingOp_ = function(
		op, var_args) {
	var plugins = this.indexedPlugins_[op];
	var argList = goog.array.slice(arguments, 1);
	for ( var i = 0; i < plugins.length; ++i) {
		// If the plugin returns true, that means it handled the event and
		// we shouldn't propagate to the other plugins.
		var plugin = plugins[i];
		if ((plugin.isEnabled(this) || specview.controller.Plugin.IRREPRESSIBLE_OPS[op])
				&& plugin[specview.controller.Plugin.OPCODE[op]].apply(plugin,
						argList)) {
			// Only one plugin is allowed to handle the event. If for some
			// reason
			// a plugin wants to handle it and still allow other plugins to
			// handle
			// it, it shouldn't return true.
			return true;
		}
	}

	return false;
};

specview.controller.Controller.prototype.disablePlugins = function (){
	this.logger.info('disablePlugins');
	for ( var key in this.plugins_) {
		var plugin = this.plugins_[key];
		plugin.disable(this);
	}
}
goog.exportSymbol('specview.controller.Controller.prototype.disablePlugins', specview.controller.Controller.prototype.disablePlugins);

specview.controller.Controller.prototype.enablePlugins = function (){
	this.logger.info('enablePlugins');
	for ( var key in this.plugins_) {
		var plugin = this.plugins_[key];
		plugin.enable(this);
	}
}
goog.exportSymbol('specview.controller.Controller.prototype.enablePlugins', specview.controller.Controller.prototype.enablePlugins);

/**
 * Handle a change in the controller. Marks the controller as modified, dispatches the change event on the editable field
 */
specview.controller.Controller.prototype.handleChange = function() {
	this.isModified_ = true;
	this.isEverModified_ = true;

};


/**
 * returns first target within tolerance in preferential order first Atom, then
 * Bond, then Molecule, then Arrow, then Plus In other words, if a Bond and
 * Molecule are both returned by findTargetList, then the Bond will be preferred
 * and returned.
 */
specview.controller.Controller.prototype.findTarget = function(e) {
//	this.logger.info(e.clientX+","+e.clientY);
	var targets = this.findTargetList(e);
	
	var target= this.findTargetListPixel(e);
	
	//The molecule to which the atom belongs to.
	var atom_targets = goog.array.filter(targets, function(t) {
		return t instanceof specview.model.Atom;
	});
	if (atom_targets.length > 0) {
		return atom_targets[0];
	}
	
	var peak_targets = goog.array.filter(targets,function(t){
		return t instanceof specview.model.Peak;
	});
	if(peak_targets.length>0){
		return peak_targets[0];
	}
	

	var bond_targets = goog.array.filter(targets, function(t) {
		return t instanceof specview.model.Bond;
	});
	if (bond_targets.length > 0) {
		return bond_targets[0];
	}

	var molecule_targets = goog.array.filter(targets, function(t) {
		return t instanceof specview.model.Molecule;
	});
	if (molecule_targets.length > 0) {
		return molecule_targets[0];
	}

	var arrow_targets = goog.array.filter(targets, function(t) {
		return t instanceof specview.model.Arrow;
	});
	if (arrow_targets.length > 0) {
		return arrow_targets[0];
	}

	var plus_targets = goog.array.filter(targets, function(t) {
		return t instanceof specview.model.Plus;
	});
	if (plus_targets.length > 0) {
		return plus_targets[0];
	}

}


specview.controller.Controller.prototype.findTarget = function(e) {
//	this.logger.info(e.clientX+","+e.clientY);
	var targets = this.findTargetList(e);
	
	var target= this.findTargetListPixel(e);//return the object
	
	return target;
	
	//The molecule to which the atom belongs to.
	var atom_targets = goog.array.filter(targets, function(t) {
		return t instanceof specview.model.Atom;
	});
	if (atom_targets.length > 0) {
		return atom_targets[0];
	}
	
	var peak_targets = goog.array.filter(targets,function(t){
		return t instanceof specview.model.Peak;
	});
	if(peak_targets.length>0){
		return peak_targets[0];
	}
	

	var bond_targets = goog.array.filter(targets, function(t) {
		return t instanceof specview.model.Bond;
	});
	if (bond_targets.length > 0) {
		return bond_targets[0];
	}

	var molecule_targets = goog.array.filter(targets, function(t) {
		return t instanceof specview.model.Molecule;
	});
	if (molecule_targets.length > 0) {
		return molecule_targets[0];
	}

	var arrow_targets = goog.array.filter(targets, function(t) {
		return t instanceof specview.model.Arrow;
	});
	if (arrow_targets.length > 0) {
		return arrow_targets[0];
	}

	var plus_targets = goog.array.filter(targets, function(t) {
		return t instanceof specview.model.Plus;
	});
	if (plus_targets.length > 0) {
		return plus_targets[0];
	}

}




specview.controller.Controller.prototype.getAtomicCoords = function(
		graphicsCoord) {
	var trans;
	if (this.moleculeRenderer.transform){
		trans = this.moleculeRenderer.transform
		.createInverse();
	} else {
		trans = this.moleculeRenderer.transform.createInverse();
	}
	return trans.transformCoords( [  sCoord ])[0];
};

specview.controller.Controller.prototype.getGraphicsCoords = function(
		atomicCoords) {
	var trans;
	if (this.moleculeRenderer.transform){
		trans = this.moleculeRenderer.transform
		.createInverse();
	} else {
		trans = this.moleculeRenderer.transform.createInverse();
	}
	return trans.transformCoords( [ atomicCoords ])[0];
}

specview.controller.Controller.getMouseCoords = function(e) {
	var elem = e.currentTarget;
	var posx = e.clientX + document.body.scrollLeft
	+ document.documentElement.scrollLeft;
	var posy = e.clientY + document.body.scrollTop
	+ document.documentElement.scrollTop;
	return specview.controller.Controller.getOffsetCoords(elem, posx, posy);
}

specview.controller.Controller.getOffsetCoords = function(elem, posx, posy) {

	posx -= elem.offsetLeft;
	posy -= elem.offsetTop;

	while (elem = elem.offsetParent) {
		posx -= elem.offsetLeft;
		posy -= elem.offsetTop;
	}
	return new goog.math.Coordinate(posx, posy);
}

specview.controller.Controller.prototype.findTargetListPixel=function(e){
	var pos=specview.controller.Controller.getMouseCoords(e);
	return this.neighborList.getObjectFromCoord(pos);
}

specview.controller.Controller.prototype.findTargetList = function(e) {
	var trans;
	if (this.moleculeRenderer.transform){
//		this.logger.info("HERE IS THE TRANSFORM: "+this.moleculeRenderer.transform);
		trans = this.moleculeRenderer.transform
		.createInverse();
	} else {
		trans = this.moleculeRenderer.transform.createInverse();
	}

	var pos = specview.controller.Controller.getMouseCoords(e)
//	this.logger.info(pos);
	//trans = affine object
	//target=(24,8). Relative coordinates
	var target = trans.transformCoords( [ pos ])[0];//POS= pixel position of the mouse
//	this.logger.info("target: "+target);
	
//	var ob=this.neighborList.getObjectFromCoord(pos);
//	alert("ob: "+ob)
	
	return this.neighborList.getNearestList( {
		x : target.x,
		y : target.y
	});
	this.logger.info(pos);
//	return this.neighborList.getNearestList({//HERE WE ARE PASSING THE PIXEL COORDINATES
//		x : pos.x,
//		y : pos.y
//	});	
};

specview.controller.Controller.prototype.handleMouseOver_ = function(e) {
	this.invokeShortCircuitingOp_(specview.controller.Plugin.Op.MOUSEOVER, e);
};

specview.controller.Controller.prototype.handleMouseOut_ = function(e) {
	this.invokeShortCircuitingOp_(specview.controller.Plugin.Op.MOUSEOUT, e);
};

specview.controller.Controller.prototype.handleMouseMove_ = function(e) {
	try{
		this.invokeShortCircuitingOp_(specview.controller.Plugin.Op.MOUSEMOVE, e);
	} catch (e) {
//		this.logger.info(e);
	}
};

specview.controller.Controller.prototype.handleMouseUp_ = function(e) {
	this.invokeShortCircuitingOp_(specview.controller.Plugin.Op.MOUSEUP, e);
}

specview.controller.Controller.prototype.handleMouseDown_ = function(e) {
};

specview.controller.Controller.prototype.handleMouseUp_ = function(e) {
	this.invokeShortCircuitingOp_(specview.controller.Plugin.Op.MOUSEUP, e);
};

specview.controller.Controller.prototype.handleKeyboardShortcut_ = function(e) {
//	this.logger.info('handelKeyboardShortcut_ ' + e.identifier);
	this.invokeShortCircuitingOp_(specview.controller.Plugin.Op.SHORTCUT, e);
}

specview.controller.Controller.prototype.handlePaste_ = function(e) {
	this.invokeShortCircuitingOp_(specview.controller.Plugin.Op.PASTE, e);
};

/**
 * Gets the value of this command.
 * 
 * @param {string}
 *            command The command to check.
 * @param {boolean}
 *            isEditable Whether the field is currently editable.
 * @return {string|boolean|null} The state of this command. Null if not handled.
 *         False if the field is uneditable and there are no handlers for
 *         uneditable commands.
 * @private
 */
specview.controller.Controller.prototype.queryCommandValueInternal_ = function(
		command, isEditable) {
	var plugins = this.indexedPlugins_[specview.controller.Plugin.Op.QUERY_COMMAND];
	for ( var i = 0; i < plugins.length; ++i) {
		var plugin = plugins[i];
		if (plugin.isEnabled(this) && plugin.isSupportedCommand(command)
				&& (isEditable || plugin.activeOnUneditableController())) {
			return plugin.queryCommandValue(command);
		}
	}
	return isEditable ? null : false;
};

/**
 * resets state of queryable plugins used to make plugins exclusive
 */
specview.controller.Controller.prototype.resetQueryablePlugins = function() {
	var plugins = this.indexedPlugins_[specview.controller.Plugin.Op.QUERY_COMMAND];
	goog.array.forEach(plugins, function(p) {
		if (p.resetState) {
			p.resetState();
		}
	});
}

/**
 * Gets the value of command(s).
 * 
 * @param {string|Array.
 *            <string>} commands String name(s) of the command.
 * @return {*} Value of each command. Returns false (or array of falses) if
 *         designMode is off or the controller is otherwise uneditable, and there
 *         are no activeOnUneditable plugins for the command.
 */
specview.controller.Controller.prototype.queryCommandValue = function(commands) {
	var isEditable = this.isLoaded();
	if (goog.isString(commands)) {
		return this.queryCommandValueInternal_(commands, isEditable);
	} else {
		var state = {};
		for ( var i = 0; i < commands.length; i++) {
			state[commands[i]] = this.queryCommandValueInternal_(commands[i],
					isEditable);
		}
		return state;
	}
};

/**
 * Dispatches the appropriate set of change events.
 * 
 */
specview.controller.Controller.prototype.dispatchChange = function() {
	this.handleChange();
};

/**
 * Dispatches a command value change event.
 * 
 * @param {Array.
 *            <string>=} opt_commands Commands whose state has changed.
 */
specview.controller.Controller.prototype.dispatchCommandValueChange = function(
		opt_commands) {
	if (opt_commands) {
		this
		.dispatchEvent( {
			type : specview.controller.Controller.EventType.COMMAND_VALUE_CHANGE,
			commands : opt_commands
		});
	} else {
		this
		.dispatchEvent(specview.controller.Controller.EventType.COMMAND_VALUE_CHANGE);
	}
};

/**
 * Executes an editing command as per the registered plugins.
 * 
 * @param {string}
 *            command The command to execute.
 * @param {...*}
 *            var_args Any additional parameters needed to execute the command.
 * @return {Object|boolean} False if the command wasn't handled, otherwise, the
 *         result of the command.
 */
specview.controller.Controller.prototype.execCommand = function(command,
		var_args) {
	var args = arguments;
	var result;

	var plugins = this.indexedPlugins_[specview.controller.Plugin.Op.EXEC_COMMAND];
	for ( var i = 0; i < plugins.length; ++i) {
		// If the plugin supports the command, that means it handled the
		// event and we shouldn't propagate to the other plugins.
		var plugin = plugins[i];
		if (plugin.isEnabled(this) && plugin.isSupportedCommand(command)) {
			result = plugin.execCommand.apply(plugin, args);
			break;
		}
	}

	return result;
};

/**
 * Registers the plugin with the controller.
 * 
 * @param {specview.controller.Plugin}
 *            plugin The plugin to register.
 */
specview.controller.Controller.prototype.registerPlugin = function(plugin) {
	var classId = plugin.getTrogClassId();

	if (this.plugins_[classId]) {
		this.logger
		.severe('Cannot register the same class of plugin twice [' + classId + ']');
	}
	this.plugins_[classId] = plugin;

	// Only key events and execute should have these has* functions with a
	// custom
	// handler array since they need to be very careful about performance.
	// The rest of the plugin hooks should be event-based.
	for ( var op in specview.controller.Plugin.OPCODE) {
		var opcode = specview.controller.Plugin.OPCODE[op];
		if (plugin[opcode]) {
			this.indexedPlugins_[op].push(plugin);
		}
	}
	plugin.registerController(this);

	// By default we enable all plugins for controllers that are currently loaded.
	if (this.isLoaded()) {
		plugin.enable(this);
	}
};
goog.exportSymbol('specview.controller.Controller.prototype.registerPlugin',
		specview.controller.Controller.prototype.registerPlugin);

/**
 * Unregisters the plugin with this controller.
 * 
 * @param {specview.controller.Plugin}
 *            plugin The plugin to unregister.
 */
specview.controller.Controller.prototype.unregisterPlugin = function(plugin) {
	var classId = plugin.getTrogClassId();
	if (!this.plugins_[classId]) {
		this.logger
		.severe('Cannot unregister a plugin that isn\'t registered.');
	}
	delete this.plugins_[classId];

	for ( var op in specview.controller.Plugin.OPCODE) {
		var opcode = specview.controller.Plugin.OPCODE[op];
		if (plugin[opcode]) {
			goog.array.remove(this.indexedPlugins_[op], plugin);
		}
	}

	plugin.unregisterController(this);
};

/**
 * @return {boolean} Whether the controller has finished loading.
 */
specview.controller.Controller.prototype.isLoaded = function() {
	return this.loadState_ == specview.controller.Controller.LoadState_.EDITABLE;
};

/**
 * The load state of the controller.
 * 
 * @enum {number}
 * @private
 */
specview.controller.Controller.LoadState_ = {
		UNEDITABLE : 0,
		LOADING : 1,
		EDITABLE : 2
};

/**
 * Logging object.
 * 
 * @type {goog.debug.Logger}
 * @protected
 */
specview.controller.Controller.prototype.logger = goog.debug.Logger
.getLogger('specview.controller.Controller');

/**
 * Event types that can be stopped/started.
 * 
 * @enum {string}
 */
specview.controller.Controller.EventType = {
		/**
		 * Dispatched when the command state of the selection may have changed. This
		 * event should be listened to for updating toolbar state.
		 */
		COMMAND_VALUE_CHANGE : 'cvc',
		/**
		 * Dispatched when the controller is loaded and ready to use.
		 */
		LOAD : 'load',
		/**
		 * Dispatched when the controller is fully unloaded and uneditable.
		 */
		UNLOAD : 'unload',
		/**
		 * Dispatched before the controller contents are changed.
		 */
		BEFORECHANGE : 'beforechange',
		/**
		 * Dispatched when the controller contents change, in FF only. Used for internal
		 * resizing, please do not use.
		 */
		CHANGE : 'change'
};

/**
 * Removes all listeners and destroys the eventhandler object.
 * 
 * @override
 */
specview.controller.Controller.prototype.disposeInternal = function() {
	if (this.isLoading() || this.isLoaded()) {
		this.logger.warning('Disposing an controller that is in use.');
	}

	if (this.getOriginalElement()) {
		this.execCommand(specview.controller.Command.CLEAR);
	}

	this.tearDownController();
	this.clearListeners_();
	this.originalDomHelper = null;

	if (this.eventRegister) {
		this.eventRegister.dispose();
		this.eventRegister = null;
	}

	this.removeAllWrappers();

	if (specview.controller.Controller.getActiveControllerId() == this.id) {
		specview.controller.Controller.setActiveControllerId(null);
	}

	for ( var classId in this.plugins_) {
		var plugin = this.plugins_[classId];
		if (plugin.isAutoDispose()) {
			plugin.dispose();
		}
	}
	delete (this.plugins_);

	specview.controller.Controller.superClass_.disposeInternal.call(this);
};

/**
 * Returns the registered plugin with the given classId.
 * 
 * @param {string}
 *            classId classId of the plugin.
 * @return {specview.controller.Plugin} Registered plugin with the given classId.
 */
specview.controller.Controller.prototype.getPluginByClassId = function(classId) {
	return this.plugins_[classId];
};

/**
 * Help make the controller not editable by setting internal data structures to
 * null, and disabling this controller with all registered plugins.
 * 
 * @private
 */
specview.controller.Controller.prototype.tearDownController = function() {
	for ( var classId in this.plugins_) {
		var plugin = this.plugins_[classId];
		if (!plugin.activeOnUneditableController()) {
			plugin.disable(this);
		}
	}

	this.loadState_ = specview.controller.Controller.LoadState_.UNEDITABLE;

};

/**
 * @return {boolean} Whether the controller has finished loading.
 */
specview.controller.Controller.prototype.isLoaded = function() {
	return this.loadState_ == specview.controller.Controller.LoadState_.EDITABLE;
};

/**
 * @return {boolean} Whether the controller is in the process of loading.
 */
specview.controller.Controller.prototype.isLoading = function() {
	return this.loadState_ == specview.controller.Controller.LoadState_.LOADING;
};

/**
 * Returns original DOM element for the controller null if that element has not yet
 * been found in the appropriate document.
 * 
 * @return {Element} The original element.
 */
specview.controller.Controller.prototype.getOriginalElement = function() {
	return this.originalElement;
};

/**
 * Stops all listeners and timers.
 * 
 * @private
 */
specview.controller.Controller.prototype.clearListeners_ = function() {
	if (this.eventRegister) {
		this.eventRegister.removeAll();
	}

};

/**
 * Removes all wrappers and destroys them.
 */
specview.controller.Controller.prototype.removeAllWrappers = function() {
	var wrapper;
	while (wrapper = this.wrappers_.pop()) {
		wrapper.dispose();
	}
};

/**
 * Handle the loading of the controller (e.g. once  is ready to setup).
 * @protected
 */
specview.controller.Controller.prototype.handleControllerLoad = function() {

	this.setupChangeListeners_();
	this.dispatchLoadEvent_();

	// Enabling plugins after we fire the load event so that clients have a
	// chance to set initial field contents before we start mucking with
	// everything.
	for ( var classId in this.plugins_) {
		this.plugins_[classId].enable(this);
	}
};

/**
 * Signal that the controller is loaded and ready to use. Change events now are in
 * effect.
 * 
 * @private
 */
specview.controller.Controller.prototype.dispatchLoadEvent_ = function() {
	this.installStyles();

	this.dispatchEvent(specview.controller.Controller.EventType.LOAD);
};

/**
 * Registers a keyboard event listener on the controller. This is necessary for
 * Gecko since the fields are contained in an iFrame and there is no way to
 * auto-propagate key events up to the main window.
 * 
 * @param {string|Array.
 *            <string>} type Event type to listen for or array of event types,
 *            for example goog.events.EventType.KEYDOWN.
 * @param {Function}
 *            listener Function to be used as the listener.
 * @param {boolean=}
 *            opt_capture Whether to use capture phase (optional, defaults to
 *            false).
 * @param {Object=}
 *            opt_handler Object in whose scope to call the listener.
 */
specview.controller.Controller.prototype.addListener = function(type,
		listener, opt_capture, opt_handler) {
	var elem = this.getOriginalElement();
	// Opera won't fire events on <body> in whitebox mode because we make
	// <html> contentEditable to work around some visual issues.
	// So, if the parent node is contentEditable, listen to events on it
	// instead.
	// if (!goog.editor.BrowserFeature.FOCUSES_EDITABLE_BODY_ON_HTML_CLICK
	// && elem.parentNode.contentEditable) {
	// elem = elem.parentNode;
	// }
	// On Gecko, keyboard events only reliably fire on the document element.
	// if (elem && goog.editor.BrowserFeature.USE_DOCUMENT_FOR_KEY_EVENTS) {
	// elem = elem.ownerDocument;
	// }

	this.eventRegister.listen(elem, type, listener, opt_capture, opt_handler);
};

/**
 * Initialize listeners on the controller.
 * 
 * @private
 */
specview.controller.Controller.prototype.setupChangeListeners_ = function() {

	this.addListener(goog.events.EventType.KEYDOWN, this.handleKeyDown_);
	this.addListener(goog.events.EventType.KEYPRESS, this.handleKeyPress_);
	this.addListener(goog.events.EventType.KEYUP, this.handleKeyUp_);
	this.addListener(goog.events.EventType.MOUSEDOWN, this.handleMouseDown_);
	this.addListener(goog.events.EventType.MOUSEMOVE, this.handleMouseMove_);
	this.addListener(goog.events.EventType.MOUSEUP, this.handleMouseUp_);
	// this.addListener(goog.events.EventType.DBLCLICK, this.handleDblclick_);
	this.addListener('paste', this.handlePaste_);

	goog.events.listen(this.shortcutHandler,
			goog.ui.KeyboardShortcutHandler.EventType.SHORTCUT_TRIGGERED,
			this.handleKeyboardShortcut_, undefined, this);

};
/*
 * Registers a keyboard shortcut. @param {string} identifier Identifier for the
 * task performed by the keyboard combination. Multiple shortcuts can be
 * provided for the same task by specifying the same identifier. @param
 * {...(number|string|Array.<number>)} var_args See below.
 * 
 * param {number} keyCode Numeric code for key param {number=} opt_modifiers
 * Bitmap indicating required modifier keys.
 * goog.ui.KeyboardShortcutHandler.Modifiers.SHIFT, CONTROL, ALT, or META.
 */
specview.controller.Controller.prototype.registerShortcut = function(id, key) {
	this.shortcutHandler.registerShortcut(id, key);
};

/**
 * Installs styles if needed. Only writes styles when they can't be written
 * inline directly into the field.
 * 
 * @protected
 */
specview.controller.Controller.prototype.installStyles = function() {
	if (this.cssStyles && this.shouldLoadAsynchronously()) {
		goog.style.installStyles(this.cssStyles, this.getElement());
	}
};

/**
 * A default configuration.
 */
specview.controller.Controller.defaultConfig = {
		background : {
			color : '#F0FFF0'
		},
		margin : {
			left : 1, right : 1, top : 1, bottom : 1
		}
};

specview.controller.Controller.prototype.toString=function(){
	return "This is an controller editor object";
}

