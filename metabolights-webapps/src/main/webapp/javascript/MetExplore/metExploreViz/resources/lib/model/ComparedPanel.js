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
var ComparedPanel=function(a,d,b,c){this.panel=a;this.visible=d;this.parent=b;this.title=c};ComparedPanel.prototype={getPanel:function(){return this.panel},setPanel:function(a){this.panel=a},isVisible:function(){return this.visible},setVisible:function(a){this.visible=a},getParent:function(){return this.parent},setParent:function(a){this.parent=a},getTitle:function(){return this.title},setTitle:function(a){this.title=a}};