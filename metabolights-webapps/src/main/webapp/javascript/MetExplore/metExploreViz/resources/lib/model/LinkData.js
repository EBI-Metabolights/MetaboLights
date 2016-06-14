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
var LinkData=function(e,b,d,a,c){this.id=e;this.source=b;this.target=d;this.interaction=a;this.reversible=c};LinkData.prototype={equals:function(a){if(this.id!=a.id){return false}return true},isReversible:function(){return this.reversible},getId:function(){return this.id},setInteraction:function(a){this.interaction=a},getInteraction:function(){return this.interaction},getSource:function(){return this.source},getTarget:function(){return this.target},setSource:function(a){this.source=a},setTarget:function(a){this.target=a}};