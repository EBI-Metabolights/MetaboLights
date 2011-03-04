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
 * @author paul@wingu.com (Paul Novak)
 */
goog.provide('specview.view.ArrowRenderer');
goog.require('specview.view.Renderer');
goog.require('goog.graphics');
goog.require('goog.math.Vec2');

/**
 * Class to render an Arrow object to a graphics representation
 * 
 * 
 * @param {goog.graphics.AbstractGraphics}
 *            graphics to draw on.
 * @param {Object=}
 *            opt_config override default configuration
 * @constructor
 * @extends {specview.view.Renderer}
 */
specview.view.ArrowRenderer = function(graphics, opt_config) {
    specview.view.Renderer.call(this, graphics,
    specview.view.ArrowRenderer.defaultConfig, opt_config);
}
goog.inherits(specview.view.ArrowRenderer, specview.view.Renderer);

/**
 * @param {specview.model.Arrow}
 *            arrow
 * @param {string}
 *            reagents_text
 * @param {string}
 *            conditions_text
 * @param {specview.graphics.AffineTransform}
 *            transform
 */
specview.view.ArrowRenderer.prototype.render = function(arrow, transform) {
    this.setTransform(transform);
    // TTD this does not belong on arrow, neighborlist should track graphic elements not just model objects
    if (!arrow._elements) {
        /** @type {specview.graphics.ElementArray} 
			 * @private
			*/
        arrow._elements = new specview.graphics.ElementArray();
    } else {
        arrow._elements.clear();
    }
    var h = this.config.get('arrow')['height'];
    var l = goog.math.Coordinate.distance(arrow.target, arrow.source);
    var angle = goog.math.angle(arrow.source.x, arrow.source.y, arrow.target.x,
    arrow.target.y);
    var angle_up = goog.math.standardAngle(angle + 90);
    var angle_down = goog.math.standardAngle(angle - 90);
    var center = new goog.math.Coordinate(
    (arrow.source.x + arrow.target.x) / 2,
    (arrow.source.y + arrow.target.y) / 2);
    var center_above = new goog.math.Coordinate(center.x, center.y + h);
    var center_below = new goog.math.Coordinate(center.x, center.y - h);
    // coordinates for horizontal arrow
    var nock = new goog.math.Coordinate(center.x - l / 2, center.y);
    var tip = new goog.math.Coordinate(center.x + l / 2, center.y);
    var head1 = new goog.math.Coordinate(tip.x - h, tip.y + h / 2);
    var head2 = new goog.math.Coordinate(tip.x - h, tip.y - h / 2);

    var path = new goog.graphics.Path();
    var arrowStroke = new goog.graphics.Stroke(
    this.config.get("arrow")['stroke']['width'], this.config
    .get("arrow")['stroke']['color']);
    var textStroke = new goog.graphics.Stroke(
    this.config.get("arrow")['font']['stroke']['width'], this.config
    .get("arrow")['font']['stroke']["color"]);
    var fill = new goog.graphics.SolidFill(
    this.config.get("arrow")['font']['stroke']["color"]);

    var scale = transform.getScaleX();

    var fontSize = (scale / 1.8) > 12 ? 15: (scale / 1.8);

    var font = new goog.graphics.Font(fontSize,
    this.config.get("arrow")['font']['name']);

    // rotate horizontal arrow to position
    var rotate_transform = specview.graphics.AffineTransform.getRotateInstance(
    goog.math.toRadians(angle), center.x, center.y);
    rotate_transform.preConcatenate(transform);

    var coords = rotate_transform.transformCoords([nock, tip, head1, head2,
    center_above, center_below]);

    path.moveTo(coords[0].x, coords[0].y);
    path.lineTo(coords[1].x, coords[1].y);
    path.lineTo(coords[2].x, coords[2].y);
    path.moveTo(coords[1].x, coords[1].y);
    path.lineTo(coords[3].x, coords[3].y);

    var angle_up_rads = goog.math.toRadians(angle_up);
    var angle_down_rads = goog.math.toRadians(angle_down);

    if (arrow.reaction && arrow.reaction.reagentsText && !arrow.reaction.isHiddenReagentsText) {
        var reagents_nock = goog.math.Coordinate.sum(new goog.math.Coordinate(
        fontSize * Math.cos(angle_up_rads), -fontSize
        * Math.sin(angle_up_rads)), coords[0]);

        var reagents_tip = goog.math.Coordinate.sum(new goog.math.Coordinate(
        fontSize * Math.cos(angle_up_rads), -fontSize
        * Math.sin(angle_up_rads)), coords[1]);

        arrow._elements.add(this.graphics.drawTextOnLine(arrow.reaction.reagentsText, reagents_nock.x,
        reagents_nock.y, reagents_tip.x, reagents_tip.y, 'center', font,
        textStroke, fill));
    }

    if (arrow.reaction && arrow.reaction.conditionsText && !arrow.reaction.isHiddenConditionsText) {
        var conditions_nock = goog.math.Coordinate.sum(new goog.math.Coordinate(
        fontSize * Math.cos(angle_down_rads), -fontSize
        * Math.sin(angle_down_rads)), coords[0]);
        var conditions_tip = goog.math.Coordinate.sum(new goog.math.Coordinate(
        fontSize * Math.cos(angle_down_rads), -fontSize
        * Math.sin(angle_down_rads)), coords[1]);
        arrow._elements.add(this.graphics.drawTextOnLine(arrow.reaction.conditionsText, conditions_nock.x,
        conditions_nock.y, conditions_tip.x, conditions_tip.y, 'center',
        font, textStroke, fill));
    }

    // visible arrow
    arrow._elements.add(this.graphics.drawPath(path, arrowStroke));
}
/**
 * @param {specview.model.Arrow}
 *            arrow
 * @param {string=}
 *            opt_color
 * @param {goog.graphics.Group=}
 *            opt_group
 */
specview.view.ArrowRenderer.prototype.highlightOn = function(arrow, opt_color,
opt_group) {
    if (!opt_color) {
        opt_color = this.config.get("arrow")['highlight']["color"];
    }
    if (!opt_group) {
        opt_group = this.graphics.createGroup();
    }

    var stroke = null;
    var angle = goog.math.angle(arrow.source.x, arrow.source.y, arrow.target.x,
    arrow.target.y);
    var angle_up = goog.math.standardAngle(angle + 90);
    var angle_down = goog.math.standardAngle(angle - 90);

    var angle_up_rads = goog.math.toRadians(angle_up);
    var angle_down_rads = goog.math.toRadians(angle_down);

    var scale = this.transform.getScaleX();
    var radius = this.config.get("arrow")['highlight']['radius']
    * scale;

    var coords = this.transform.transformCoords([arrow.source,
    arrow.target]);

    var source_up = goog.math.Coordinate.sum(new goog.math.Coordinate(
    radius * Math.cos(angle_up_rads), -radius
    * Math.sin(angle_up_rads)), coords[0]);

    var target_up = goog.math.Coordinate.sum(new goog.math.Coordinate(
    radius * Math.cos(angle_up_rads), -radius
    * Math.sin(angle_up_rads)), coords[1]);

    var source_down = goog.math.Coordinate.sum(new goog.math.Coordinate(
    radius * Math.cos(angle_down_rads), -radius
    * Math.sin(angle_down_rads)), coords[0]);
    var target_down = goog.math.Coordinate.sum(new goog.math.Coordinate(
    radius * Math.cos(angle_down_rads), -radius
    * Math.sin(angle_down_rads)), coords[1]);

    var path_up = new goog.graphics.Path();
    path_up.moveTo(coords[0].x, coords[0].y);
    path_up.lineTo(source_up.x, source_up.y);
    path_up.lineTo(target_up.x, target_up.y);
    path_up.lineTo(coords[1].x, coords[1].y);
    path_up.close();
    var fill = new goog.graphics.SolidFill(opt_color, .15);
    this.graphics.drawPath(path_up, stroke, fill, opt_group);

    var path_down = new goog.graphics.Path();
    path_down.moveTo(coords[0].x, coords[0].y);
    path_down.lineTo(source_down.x, source_down.y);
    path_down.lineTo(target_down.x, target_down.y);
    path_down.lineTo(coords[1].x, coords[1].y);
    path_down.close();
    //	var fill = new goog.graphics.LinearGradient(coords[0].x, coords[0].y, source_down.x, source_down.y, opt_color, 'white');
    this.graphics.drawPath(path_down, stroke, fill, opt_group);
    return opt_group;
}

/**
 * Logging object.
 * 
 * @type {goog.debug.Logger}
 * @protected
 */
specview.view.ArrowRenderer.prototype.logger = goog.debug.Logger
.getLogger('specview.view.ArrowRenderer');
/**
 * A default configuration for renderer
 */
specview.view.ArrowRenderer.defaultConfig = {
    'arrow': {
        'height': .5,
        'stroke': {
            'width': 2,
            'color': "black"
        },
        'fill': {
            'color': 'black'
        },
        'font': {
            'name': "Arial",
            'stroke': {
                'width': .1,
                'color': 'grey'
            }
        },
        'highlight': {
            'radius': .75,
            'color': 'grey'
        }
    }
}
