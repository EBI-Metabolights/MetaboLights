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
var GeneralStyle=function(c,e,i,g,h,f,b,d,a){this.websiteName=c;this.colorMinMappingContinuous=e;this.colorMaxMappingContinuous=i;this.maxReactionThreshold=g;this.displayLabelsForOpt=h;this.displayLinksForOpt=f;this.displayConvexhulls=b;this.displayCaption=a;this.eventForNodeInfo=false;this.loadButtonHidden=false;this.windowsAlertDisable=false;this.clustered=d};GeneralStyle.prototype={loadButtonIsHidden:function(){return this.loadButtonHidden},setLoadButtonIsHidden:function(a){this.loadButtonHidden=a;metExploreD3.fireEvent("graphPanel","setLoadButtonHidden")},windowsAlertIsDisable:function(){return this.windowsAlertDisable},setWindowsAlertDisable:function(a){this.windowsAlertDisable=a},getColorMinMappingContinuous:function(){return this.colorMinMappingContinuous},getColorMaxMappingContinuous:function(){return this.colorMaxMappingContinuous},setMaxColorMappingContinuous:function(a){this.colorMaxMappingContinuous=a},setMinColorMappingContinuous:function(a){this.colorMinMappingContinuous=a},getWebsiteName:function(){return this.websiteName},getReactionThreshold:function(){return this.maxReactionThreshold},setReactionThreshold:function(a){this.maxReactionThreshold=a},hasEventForNodeInfo:function(){return this.eventForNodeInfo},setEventForNodeInfo:function(a){this.eventForNodeInfo=a},isDisplayedLabelsForOpt:function(){return this.displayLabelsForOpt},setDisplayLabelsForOpt:function(a){this.displayLabelsForOpt=a},isDisplayedLinksForOpt:function(){return this.displayLinksForOpt},setDisplayLinksForOpt:function(a){this.displayLinksForOpt=a},isDisplayedConvexhulls:function(){return this.displayConvexhulls},setDisplayConvexhulls:function(a){this.displayConvexhulls=a},isDisplayedCaption:function(){return this.displayCaption},setDisplayCaption:function(a){this.displayCaption=a},useClusters:function(){return this.clustered},setUseClusters:function(a){this.clustered=a}};