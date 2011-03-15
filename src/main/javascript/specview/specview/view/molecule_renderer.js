goog.provide('specview.view.MoleculeRenderer');
goog.require('specview.view.BondRenderer');
goog.require('specview.view.BondRendererFactory');
goog.require('specview.view.AtomRenderer');
goog.require('specview.view.AromaticityRenderer');
goog.require('specview.graphics.ElementArray');
goog.require('goog.asserts');

/**
 * Class to render a molecule object to a graphics object
 * 
 * @constructor
 * @param graphics
 *            {goog.graphics.AbstractGraphics} graphics to draw on.
 * @extends {specview.view.Renderer}
 */
specview.view.MoleculeRenderer = function(graphics, opt_config) {
    specview.view.Renderer.call(this, graphics,
    specview.view.MoleculeRenderer.defaultConfig, opt_config);
    this.scale_factor = 0.9;

    this.bondRendererFactory = new specview.view.BondRendererFactory(graphics,
    this.config);

    this.atomRenderer = new specview.view.AtomRenderer(graphics, this.config);

    this.aromaticityRenderer = new specview.view.AromaticityRenderer(graphics, this.config);


}
goog.inherits(specview.view.MoleculeRenderer, specview.view.Renderer);

/**
 * The logger for this class.
 * 
 * @type {goog.debug.Logger}
 * @protected
 */
specview.view.MoleculeRenderer.prototype.logger = goog.debug.Logger
.getLogger('specview.view.MoleculeRenderer');

specview.view.MoleculeRenderer.prototype.setScaleFactor = function(scale) {
    this.scale_factor = scale;
}

specview.view.MoleculeRenderer.prototype.render = function(molecule, trans) {
	this.logger.info("rendering")
// TTD this does not belong on molecule, neighborlist should track graphic elements not just model objects
	if (!molecule._elements){
		/** @type {specview.graphics.ElementArray} 
		 * @private
		*/
	    molecule._elements = new specview.graphics.ElementArray();
	} else {
		molecule._elements.clear();
	}

    molecule.mustRecalcSSSR = true;

    var atom_coords = goog.array.map(molecule.atoms,
    function(a) {
        return a.coord;
    });
    var box = goog.math.Box.boundingBox.apply(null, atom_coords);

    if (!trans) {
        // if not part of a reaction, we need to create a transform
        var m = this.config.get("margin");
        var ex_box = box.expand(m, m, m, m);
        trans = this.buildTransform(ex_box);
    }
    this.setTransform(trans);

    var center = new goog.math.Coordinate((box.left + box.right) / 2,
    (box.top + box.bottom) / 2);
    var t_center = this.transform.transformCoords([center])[0];
    var rx = Math.abs(this.transform.getScaleX() * (box.right - box.left) / 2);
    var ry = Math.abs(this.transform.getScaleY() * (box.bottom - box.top) / 2);

    var bondStroke = new goog.graphics.Stroke(
    this.config.get("bond")['stroke']['width'],
    this.config.get("bond")['stroke']['color']);
    var bondFill = new goog.graphics.SolidFill(
    this.config.get("bond")['fill']['color']);
    var bondPath = new goog.graphics.Path();

    goog.array.forEach(molecule.bonds,
    function(bond) {
        this.bondRendererFactory.get(bond).render(bond, trans, bondPath);
    },
    this);

    var aromRingRenderer = this.aromaticityRenderer;
    goog.array.forEach(molecule.getRings(),
    function(ring) {
        var aromatic_bonds = goog.array.filter(ring.bonds,
        function(b) {
            return b.aromatic;
        });
        if (aromatic_bonds.length == ring.bonds.length) {
            this._elements.add(
            aromRingRenderer.render(ring, trans, bondPath));
        }
    });

    molecule._elements.add(this.graphics.drawPath(bondPath, bondStroke, bondFill));


    // this.logger.info("molecule has " + molecule.atoms.length + " atoms");
    goog.array.forEach(molecule.atoms,
    function(atom) {
        this.atomRenderer.render(atom, trans, molecule._elements);
    },
    this);

};
/**
 * @param {specview.model.Molecule}
 *            molecule
 * @param {string=}
 *            opt_color
 * @param {specview.graphics.ElementArray=} opt_element_array
 * @return {specview.graphics.ElementArray}
 */
specview.view.MoleculeRenderer.prototype.highlightOn = function(molecule,
opt_color, opt_element_array) {

    if (!opt_color) {
        opt_color = this.config.get("highlight")['color'];
    }
    if (!opt_element_array) {
        opt_element_array = new specview.graphics.ElementArray();
    }

    goog.array.forEach(molecule.bonds,
    function(bond) {
        this.bondRendererFactory.get(bond).highlightOn(bond, opt_color, opt_element_array);
    },
    this);

    goog.array.forEach(molecule.atoms,
    function(atom) {
        this.atomRenderer.highlightOn(atom, opt_color, opt_element_array);
    },
    this);

    return opt_element_array;
}

/**
 * A default configuration for renderer
 */
specview.view.MoleculeRenderer.defaultConfig = {
    'bond': {
        'stroke': {
            'width': 1.5,
            'color': 'black'
        },
        'fill': {
            'color': 'black'
        }
    },
    'highlight': {
        'radius': .2,
        'color': 'blue'
    }
};
