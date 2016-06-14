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
var NodeData=function(a,k,f,m,c,o,e,l,b,j,i,p,h,g,q,n,d){this.name=a;this.dbIdentifier=f;this.ec=m;this.id=c;this.identifier=n;this.reactionReversibility=o;this.isSideCompound=e;this.biologicalType=l;this.selected=b;this.duplicated=q;this.labelVisible=j;this.svg=i;this.svgWidth=p;this.svgHeight=h;if(d==undefined){this.pathways=[]}else{this.pathways=d}this.compartment=k;if(g==undefined){g=[]}this.mappingDatas=g};NodeData.prototype={equals:function(a){var b=true;if(this.id!=a.id){b=false}return b},toString:function(){return this.id},isSelected:function(){return this.selected},setIsSelected:function(a){this.selected=a},isDuplicated:function(){return this.duplicated},setIsDuplicated:function(a){this.duplicated=a},getIsSideCompound:function(){return this.isSideCompound},setIsSideCompound:function(a){this.isSideCompound=a},getId:function(){return this.id},getIdentifier:function(){return this.identifier},getDbIdentifier:function(){return this.dbIdentifier},getBiologicalType:function(){return this.biologicalType},getLabelVisible:function(){return this.labelVisible},getReactionReversibility:function(){return this.reactionReversibility},getName:function(){return this.name},getId:function(){return this.id},getSvg:function(){return this.svg},getSvgHeight:function(){return this.svgHeight},getSvgWidth:function(){return this.svgWidth},getCompartment:function(){return this.compartment},getEC:function(){return this.ec},resetMapping:function(){this.mappingDatas=[]},addMappingData:function(a){this.mappingDatas.push(a)},removeMappingData:function(a){var b=[];this.mappingDatas.forEach(function(c){if(c.getMappingName()==a){b.push(c)}});b.forEach(function(d){var c=b.indexOf(d);if(c!=-1){b.splice(c,1)}})},getMappingDatasLength:function(){return this.mappingDatas.length},getMappingDatas:function(){return this.mappingDatas},getMappingDataByNameAndCond:function(a,b){var c=null;this.mappingDatas.forEach(function(d){if(d.getMappingName()==a&&d.getConditionName()==b){c=d}});return c},getPathways:function(){return this.pathways},setPathways:function(a){this.pathways=a},addPathway:function(a){if(this.pathways.indexOf(a)==-1){this.pathways.push(a)}}};