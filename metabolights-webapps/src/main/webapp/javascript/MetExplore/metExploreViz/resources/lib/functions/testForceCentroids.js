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
 
Version: 1 Build date: 2016-01-15 12:48:37 
*/ 
d3.select("#viz").select("#D3viz").select("#graphComponent").selectAll("g.node").filter(function(node){return node.getPathways().length>1}).style("fill", 'red');

var panel = "viz";
		var h = parseInt(metExploreD3.GraphPanel.getHeight(panel));
		var w = parseInt(metExploreD3.GraphPanel.getWidth(panel));
		var linkStyle = metExploreD3.getLinkStyle();
		var generalStyle = metExploreD3.getGeneralStyle();				
		
    	var networkData = _metExploreViz.getSessionById(panel).getD3Data();
		var force2 = d3.layout.force().friction(0.90).gravity(0.08).charge(-4000).theta(0.2)
    			.linkDistance(150)
				.size([ w, h ]);

		var pathways = [];
		networkData.getPathways().forEach(function(pathway){
			pathways.push({"id":pathway,x:2,y:2});
		});

		

		var links = [];

		d3.select("#viz").select("#D3viz").select("#graphComponent").selectAll("g.node").filter(function(node){return node.getPathways().length>1})
			.each(function(node){
				node.getPathways().forEach(function(pathway){
					node.getPathways().forEach(function(pathway2){
						if(pathway!=pathway2)
							links.push({"source":node.getPathways().indexOf(pathway),"target":node.getPathways().indexOf(pathway2)});
					});				
				});

			});

		// var lien = d3.select("#viz").select("#D3viz").select("#graphComponent").selectAll("linkCentroid")
		//       .data(links)
		//     	.enter().append("line")
		//       .attr("class", "linkCentroid")
		//       .style("stroke", "red")
		//       .style("stroke-width", 1);

		d3.select("#viz").select("#D3viz").select("#graphComponent").selectAll("g.centroid")
			.data(pathways).enter()
			.append("svg:g").attr("class", "centroid")
			.style("fill", "white")
			.append("rect")
			.attr("id",function(pathway){ return pathway.id})
			.attr("width", 6)
			.attr("height", 6)
			.attr("rx", 3)
			.attr("ry", 3)
			.attr("transform", "translate(-" + 6/2 + ",-"
									+ 6/2
									+ ")")
			.style("stroke", "blue")
			.style("stroke-width", 6);

		
		force2
			.nodes(pathways)
			.links(links)
			.on("tick", function(e){
				d3.select("#"+panel).select("#D3viz").select("#graphComponent")
					.selectAll("g.centroid")
					.attr("cx", function(d) {
						return d.x;
					})
					.attr("cy", function(d) {
						return d.y;
					})
					.attr("transform", function(d) {
						//  scale("+ +")
						var scale = 1;
						if(d3.select(this)!=null){
							var transformString = d3.select(this).attr("transform");
							if(d3.select(this).attr("transform")!=null){
								var indexOfScale = transformString.indexOf("scale(");
								if(indexOfScale!=-1)
									scale = parseInt(transformString.substring(indexOfScale+6, transformString.length-1));
							}
						}
						return "translate(" + d.x + "," + d.y + ") scale("+scale+")";
					});

				// lien.attr("x1", function(d) { return d.source.x; })
			 //        .attr("y1", function(d) { return d.source.y; })
			 //        .attr("x2", function(d) { return d.target.x; })
			 //        .attr("y2", function(d) { return d.target.y; });
			})
			.start();
