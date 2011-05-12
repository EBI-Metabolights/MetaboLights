goog.provide("specview.graphics.ElementArray");
goog.require('goog.array');
goog.require("goog.debug.Logger");

/**
 * convenience class to contain group of goog.graphics.Elements since nested vml groups do not seem to work for IE8 in standards mode
 * 
 * @constructor
 */
specview.graphics.ElementArray = function() {
    /** @type{Array.<goog.graphics.Element>}
     * @private
	 */
    this._elements = [];
};

/**
 * Logging object.
 * 
 * @type {goog.debug.Logger}
 * @protected
 */
specview.graphics.ElementArray.prototype.logger = goog.debug.Logger
.getLogger('specview.graphics.ElementArray');

/**
 * add a graphics element
 * @param {goog.graphics.Element} element the element to add
 */
specview.graphics.ElementArray.prototype.add = function(element) {
    goog.asserts.assert(element instanceof goog.graphics.Element);
    this._elements.push(element);
    return this;
};

/** 
 * remove all elements
 */
specview.graphics.ElementArray.prototype.clear = function() {
    goog.array.forEach(this._elements,
    function(element) {
        element.getGraphics().removeElement(element);
    },
    this);
    this._elements.length = 0;
};

/**
 * Set the transformation of the elements.
 * @param {number} x The x coordinate of the translation transform.
 * @param {number} y The y coordinate of the translation transform.
 * @param {number} rotate The angle of the rotation transform.
 * @param {number} centerX The horizontal center of the rotation transform.
 * @param {number} centerY The vertical center of the rotation transform.
 */
specview.graphics.ElementArray.prototype.setTransformation = function(x, y, rotate,
centerX, centerY) {
    goog.array.forEach(this._elements,
    function(element) {
        element.setTransformation(x, y, rotate, centerX, centerY);
    });
};


