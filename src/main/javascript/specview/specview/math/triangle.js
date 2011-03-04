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
 */
goog.provide('specview.math.Triangle');

/**
 * representation of a triangle
 * @param {goog.math.Coordinate} a
 * @param {goog.math.Coordinate} b
 * @param {goog.math.Coordinate} c
 */
specview.math.Triangle = function (a, b, c){
	this.a = a;
	this.b = b;
	this.c = c;
};

/**
 * fast algorithm for determining triangle orientation
 * returns 2 x the signed area of triangle
 * positive value if a,b,c are oriented counterclockwise around a triangle
 * negative value if they are oriented clockwise
 * see http://softsurfer.com/Archive/algorithm_0101/algorithm_0101.htm 'Modern Triangles'
 * @param {goog.math.Coordinate} a
 * @param {goog.math.Coordinate} b
 * @param {goog.math.Coordinat} c
 * @return {number}
 */
specview.math.Triangle.signedArea = function(a, b, c) {
	return (b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y);
}