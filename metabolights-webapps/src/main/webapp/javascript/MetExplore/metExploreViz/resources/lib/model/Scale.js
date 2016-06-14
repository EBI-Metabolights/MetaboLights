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
var Scale=function(a){this.graphName=a};Scale.prototype={setScale:function(a,g,b,f,c,e,d){this.xScale=a;this.yScale=g;this.zoomScale=b;this.xScaleCompare=f;this.yScaleCompare=c;this.zoomScaleCompare=e;this.zoom=d},getGraphName:function(){return this.graphName},setGraphName:function(a){this.graphName=a},getXScale:function(){return this.xScale},setXScale:function(a){this.xScale=a},getYScale:function(){return this.yScale},setYScale:function(a){this.yScale=a},getZoomScale:function(){return this.zoomScale},setZoomScale:function(a){this.zoomScale=a},getXScaleCompare:function(){return this.xScaleCompare},setXScaleCompare:function(a){this.xScaleCompare=a},getYScaleCompare:function(){return this.yScaleCompare},setYScaleCompare:function(a){this.yScaleCompare=a},getZoomScaleCompare:function(){return this.zoomScaleCompare},setZoomScaleCompare:function(a){this.zoomScaleCompare=a},getZoom:function(){return this.zoom},setZoom:function(a){this.zoom=a}};