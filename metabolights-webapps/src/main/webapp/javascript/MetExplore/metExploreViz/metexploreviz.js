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
var MetExploreViz={initFrame:function(q){var o=document.createElement("iframe");o.id="iFrameMetExploreViz",o.height="100%",o.width="100%",o.border=0,o.setAttribute("style","border: none;top: 0; right: 0;bottom: 0; left: 0; width: 100%;height: 100%;");var p=document.getElementsByTagName("script");var m;for(var n=0;n<p.length;n++){if(p[n].src.search("metExploreViz/metexploreviz.js")!=-1){m=p[n].src}}var k=document.location.href;var l=0;while(m[l]==k[l]&&l!=k.length&&l!=m.length){l++}var m=m.substr(l,m.length-1);m=m.split("/metexploreviz.js");res=k.substr(l,k.length-1);res=res.split("/");var i="";for(var n=0;n<res.length-1;n++){i+="../"}var r=i+m[0]+"/index.html";document.getElementById(q).insertBefore(o,document.getElementById(q).firstChild);o.src=r},launchMetexploreFunction:function(c){if(typeof metExploreViz!=="undefined"){c();return}var d=this;setTimeout(function(){d.launchMetexploreFunction(c)},1000)},onloadMetExploreViz:function(b){this.launchMetexploreFunction(b)}};