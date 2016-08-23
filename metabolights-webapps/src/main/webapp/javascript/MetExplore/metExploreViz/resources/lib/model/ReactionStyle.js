/* 
This file is part of MetExploreViz 
 
Copyright (c) 2016 INRA 
 
Contact: http://metexplore.toulouse.inra.fr/metexploreViz/doc/contact 
 
GNU General Public License Usage 
This file may be used under the terms of the GNU General Public License version 3.0 as 
published by the Free Software Foundation and appearing in the file LICENSE included in the 
packaging of this file. 
 
Please review the following information to ensure the GNU General Public License version 3.0 
requirements will be met: http://www.gnu.org/copyleft/gpl.html. 
 
If you are unsure which license is appropriate for your use, please contact us 
at http://metexplore.toulouse.inra.fr/metexploreViz/doc/contact 
 
Version: 1 Build date: 2016-04-13 9:34:37 
*/ 
var ReactionStyle=function(a,c,g,f,b,e,d,h){this.height=a;this.width=c;this.rx=g;this.ry=f;this.label=b;this.strokeColor=d;this.fontSize=e;this.strokeWidth=h};ReactionStyle.prototype={getHeight:function(){return this.height},setHeight:function(a){this.height=a},getStrokeWidth:function(){return this.strokeWidth},setStrokeWidth:function(a){this.strokeWidth=a},getWidth:function(){return this.width},setWidth:function(a){this.width=a},getRX:function(){return this.rx},setRX:function(a){this.rx=a},getRY:function(){return this.ry},setRY:function(a){this.ry=a},getStrokeColor:function(){return this.strokeColor},setStrokeColor:function(a){this.strokeColor=a},getFontSize:function(){return this.fontSize},setFontSize:function(a){this.fontSize=a},getLabel:function(){return this.label},setLabel:function(a){this.label=a},getDisplayLabel:function(b,a){var c;switch(a){case"ec":c=b.getEC();break;case"name":c=b.getName();break;case"dbIdentifier":c=b.getDbIdentifier();break;default:c=b.getName()}if(c==undefined){c=b.getName()}return c}};