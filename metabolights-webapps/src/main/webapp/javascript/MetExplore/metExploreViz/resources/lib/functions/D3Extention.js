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
d3.selection.enter.prototype=d3.selection.prototype.addNodeForm=function(c,a,f,d,e,b){this.append("rect").attr("class",function(g){return g.getBiologicalType()}).attr("id",function(g){return g.getId()}).attr("identifier",function(g){return g.getId()}).attr("width",c).attr("height",a).attr("rx",f).attr("ry",d).attr("transform","translate(-"+c/2+",-"+a/2+")").style("stroke",e).style("stroke-width",b);this.append("rect").attr("class","fontSelected").attr("width",c).attr("height",a).attr("rx",f).attr("ry",d).attr("transform","translate(-"+c/2+",-"+a/2+")").style("fill-opacity","0").style("fill","#000")};d3.selection.prototype.setNodeForm=function(c,a,f,d,e,b){this.select("rect").attr("width",c).attr("height",a).attr("rx",f).attr("ry",d).attr("transform","translate(-"+c/2+",-"+a/2+")").style("stroke",e).style("stroke-width",b);this.select(".fontSelected").attr("width",c).attr("height",a).attr("rx",f).attr("ry",d).attr("transform","translate(-"+c/2+",-"+a/2+")").style("stroke-width",b)};