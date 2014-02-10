/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 28/01/14 09:39
 * Modified by:   kenneth
 *
 * Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

var speciesData;

// Get JSON data
treeJSON = d3.json("species/json", function(error, treeData) {

    // Share data with the page.
    speciesData = treeData;

    configAutocomplete();

    // Calculate total nodes, max label length
    var totalNodes = 0;
    var maxLabelLength = 0;
    // variables for drag/drop
    var selectedNode = null;
    var draggingNode = null;
    // panning variables
    var panSpeed = 200;
    var panBoundary = 20; // Within 20px from edges will pan when dragging.
    // Misc. variables
    var i = 0;
    var duration = 750;
    var root;

    // size of the diagram
    //var viewerWidth = $(document).width();
    //var viewerHeight = $(document).height();
    // Pconesa adjust to width
    var placeHolderId = "#tree-container"
    var viewerWidth = $(placeHolderId).width();
    var viewerHeight = $(placeHolderId).height();

    $(window).on("resize", function() {
        d3.select('g').attr("width", $(placeHolderId).width());

    }).trigger("resize");

    var tree = d3.layout.tree()
        .size([viewerHeight, viewerWidth]);

    // define a d3 diagonal projection for use by the node paths later on.
    var diagonal = d3.svg.diagonal()
        .projection(function(d) {
            return [d.y, d.x];
        });

    // A recursive helper function for performing some setup by walking through all nodes
    function visit(parent, visitFn, childrenFn) {
        if (!parent) return;

        visitFn(parent);

        var children = childrenFn(parent);
        if (children) {
            var count = children.length;
            for (var i = 0; i < count; i++) {
                visit(children[i], visitFn, childrenFn);
            }
        }
    }

    // Call visit function to establish maxLabelLength
    visit(treeData, function(d) {
        totalNodes++;
        maxLabelLength = Math.max(d.name.length, maxLabelLength);

    }, function(d) {
        return d.children && d.children.length > 0 ? d.children : null;
    });


    // sort the tree according to the node names
    function sortTree() {
        tree.sort(function(a, b) {
            return b.name.toLowerCase() < a.name.toLowerCase() ? 1 : -1;
        });
    }

    // Sort the tree initially incase the JSON isn't in a sorted order.
    sortTree();

    // TODO: Pan function, can be better implemented.
    function pan(domNode, direction) {
        var speed = panSpeed;
        if (panTimer) {
            clearTimeout(panTimer);
            translateCoords = d3.transform(svgGroup.attr("transform"));
            if (direction == 'left' || direction == 'right') {
                translateX = direction == 'left' ? translateCoords.translate[0] + speed : translateCoords.translate[0] - speed;
                translateY = translateCoords.translate[1];
            } else if (direction == 'up' || direction == 'down') {
                translateX = translateCoords.translate[0];
                translateY = direction == 'up' ? translateCoords.translate[1] + speed : translateCoords.translate[1] - speed;
            }
            scaleX = translateCoords.scale[0];
            scaleY = translateCoords.scale[1];
            scale = zoomListener.scale();
            svgGroup.transition().attr("transform", "translate(" + translateX + "," + translateY + ")scale(" + scale + ")");
            d3.select(domNode).select('g.node').attr("transform", "translate(" + translateX + "," + translateY + ")");
            zoomListener.scale(zoomListener.scale());
            zoomListener.translate([translateX, translateY]);
            panTimer = setTimeout(function() {
                pan(domNode, speed, direction);
            }, 50);
        }
    }

    // Define the zoom function for the zoomable tree
    function zoom() {
        svgGroup.attr("transform", "translate(" + d3.event.translate + ")scale(" + d3.event.scale + ")");
    }

    // define the zoomListener which calls the zoom function on the "zoom" event constrained within the scaleExtents
    var zoomListener = d3.behavior.zoom().scaleExtent([0.1, 3]).on("zoom", zoom);




    // define the baseSvg, attaching a class for styling and the zoomListener
    var baseSvg = d3.select(placeHolderId).append("svg")
        .attr("width", "100%")//viewerWidth)
        .attr("height", "50%")//viewerHeight)
        .attr("viewBox", "0 0 " + viewerWidth + " " + viewerHeight )
        .attr("preserveAspectRatio", "xMidYMid meet")
        .attr("class", "overlay")
        .call(zoomListener);

//    // Define the drag listeners for drag/drop behaviour of nodes.
//    dragListener = d3.behavior.drag()
//        .on("dragstart", function(d) {
//            if (d == root) {
//                return;
//            }
//            dragStarted = true;
//            nodes = tree.nodes(d);
//            d3.event.sourceEvent.stopPropagation();
//            // it's important that we suppress the mouseover event on the node being dragged. Otherwise it will absorb the mouseover event and the underlying node will not detect it d3.select(this).attr('pointer-events', 'none');
//        })
//        .on("drag", function(d) {
//            if (d == root) {
//                return;
//            }
//            if (dragStarted) {
//                domNode = this;
//                initiateDrag(d, domNode);
//            }
//
//            // get coords of mouseEvent relative to svg container to allow for panning
//            relCoords = d3.mouse($('svg').get(0));
//            if (relCoords[0] < panBoundary) {
//                panTimer = true;
//                pan(this, 'left');
//            } else if (relCoords[0] > ($('svg').width() - panBoundary)) {
//
//                panTimer = true;
//                pan(this, 'right');
//            } else if (relCoords[1] < panBoundary) {
//                panTimer = true;
//                pan(this, 'up');
//            } else if (relCoords[1] > ($('svg').height() - panBoundary)) {
//                panTimer = true;
//                pan(this, 'down');
//            } else {
//                try {
//                    clearTimeout(panTimer);
//                } catch (e) {
//                }
//            }
//
//            d.x0 += d3.event.dy;
//            d.y0 += d3.event.dx;
//            var node = d3.select(this);
//            node.attr("transform", "translate(" + d.y0 + "," + d.x0 + ")");
//            updateTempConnector();
//        }).on("dragend", function(d) {
//            if (d == root) {
//                return;
//            }
//            domNode = this;
//            if (selectedNode) {
//                // now remove the element from the parent, and insert it into the new elements children
//                var index = draggingNode.parent.children.indexOf(draggingNode);
//                if (index > -1) {
//                    draggingNode.parent.children.splice(index, 1);
//                }
//                if (typeof selectedNode.children !== 'undefined' || typeof selectedNode._children !== 'undefined') {
//                    if (typeof selectedNode.children !== 'undefined') {
//                        selectedNode.children.push(draggingNode);
//                    } else {
//                        selectedNode._children.push(draggingNode);
//                    }
//                } else {
//                    selectedNode.children = [];
//                    selectedNode.children.push(draggingNode);
//                }
//                // Make sure that the node being added to is expanded so user can see added node is correctly moved
//                expand(selectedNode);
//                sortTree();
//                endDrag();
//            } else {
//                endDrag();
//            }
//        });
//
//    function endDrag() {
//        selectedNode = null;
//        d3.selectAll('.ghostCircle').attr('class', 'ghostCircle');
//        d3.select(domNode).attr('class', 'node');
//        // now restore the mouseover event or we won't be able to drag a 2nd time
//        d3.select(domNode).select('.ghostCircle').attr('pointer-events', '');
//        updateTempConnector();
//        if (draggingNode !== null) {
//            update(root);
//            centerNode(draggingNode);
//            draggingNode = null;
//        }
//    }

    // Helper functions for collapsing and expanding nodes.
    function collapse(d) {
        if (d.children) {
            d._children = d.children;
            d._children.forEach(collapse);
            d.children = null;
        }
    }

    function expand(d) {
        if (d._children) {
            d.children = d._children;
            d.children.forEach(expand);
            d._children = null;
        }
    }

    var overCircle = function(d) {
        selectedNode = d;
        updateTempConnector();
    };

    var outCircle = function(d) {
        selectedNode = null;
        updateTempConnector();
    };

//    // Function to update the temporary connector indicating dragging affiliation
//    var updateTempConnector = function() {
//        var data = [];
//        if (draggingNode !== null && selectedNode !== null) {
//            // have to flip the source coordinates since we did this for the existing connectors on the original tree
//            data = [{
//                source: {
//                    x: selectedNode.y0,
//                    y: selectedNode.x0
//                },
//                target: {
//                    x: draggingNode.y0,
//                    y: draggingNode.x0
//                }
//            }];
//        }
//        var link = svgGroup.selectAll(".templink").data(data);
//
//        link.enter().append("path")
//            .attr("class", "templink")
//            .attr("d", d3.svg.diagonal())
//            .attr('pointer-events', 'none');
//
//        link.attr("d", d3.svg.diagonal());
//
//        link.exit().remove();
//    };

    // Function to center node when clicked/dropped so node doesn't get lost when collapsing/moving with large amount of children.
    function centerNode(source) {
        scale = zoomListener.scale();
        x = -source.y0;
        y = -source.x0;
        // Ken Haug - Want to move the node closer to the left
        x = x * scale + viewerWidth / 6;       //Changed from 2
        y = y * scale + viewerHeight / 2;
        d3.select('g').transition()
            .duration(duration)
            .attr("transform", "translate(" + x + "," + y + ")scale(" + scale + ")");
        zoomListener.scale(scale);
        zoomListener.translate([x, y]);
    }

    // Toggle children function
    function toggleChildren(d) {
        if (d.children) {
            d._children = d.children;
            d.children = null;
        } else if (d._children) {
            d.children = d._children;
            d._children = null;
        }
        return d;
    }

    // Toggle children on click.
    function click(d) {
        if (d3.event.defaultPrevented) return; // click suppressed

        if (!d._children){
            $(location).attr('href', "reference?organisms=" + d.name);
            return;
        }

        d = toggleChildren(d);
        update(d);
        centerNode(d);
    }

    function update(source) {
        // Compute the new height, function counts total children of root node and sets tree height accordingly.
        // This prevents the layout looking squashed when new nodes are made visible or looking sparse when nodes are removed
        // This makes the layout more consistent.
        var levelWidth = [1];
        var childCount = function(level, n) {

            if (n.children && n.children.length > 0) {
                if (levelWidth.length <= level + 1) levelWidth.push(0);

                levelWidth[level + 1] += n.children.length;
                n.children.forEach(function(d) {
                    childCount(level + 1, d);
                });
            }
        };

        childCount(0, root);
        var newHeight = d3.max(levelWidth) * 25; // 25 pixels per line
        tree = tree.size([newHeight, viewerWidth]);

        // Compute the new tree layout.
        var nodes = tree.nodes(root).reverse(),
            links = tree.links(nodes);

        // Set widths between levels based on maxLabelLength.
        nodes.forEach(function(d) {
            // d.y = (d.depth * (maxLabelLength * 10)); //maxLabelLength * 10px
            // alternatively to keep a fixed scale one can set a fixed depth per level
            // Normalize for fixed-depth by commenting out below line
            d.y = (d.depth * 300); //500px per level.
        });

        // Update the nodes…
        node = svgGroup.selectAll("g.node")
            .data(nodes, function(d) {
                return d.id || (d.id = ++i);
            });

        // Enter any new nodes at the parent's previous position.
        var nodeEnter = node.enter().append("g")
//            .call(dragListener)
            .attr("class", "node")
            .attr("transform", function(d) {
                return "translate(" + source.y0 + "," + source.x0 + ")";
            })
            .on('click', click);

        nodeEnter.append("circle")
            .attr('class', function(d) {
                return d._children ? "nodeCircle branch" : "nodeCircle leaf";
            })
            .attr("r", 0);

		// Ken added hyperlink on the name.  //TODO, only for the child node, not parent
//        nodeEnter.append("a").attr("xlink:href", function (d) { return d._children ? "#" : "reference?organisms=" + d.name; })

         nodeEnter.append("text")
            .attr("x", function(d) {
                return d.children || d._children ? -10 : 10;
            })
            .attr("dy", ".35em")
            .attr('class', 'nodeText')
            .attr("text-anchor", function(d) {
                return d.children || d._children ? "end" : "start";
            })
            .text(function(d) {
                return d.name;
            })
            .style("fill-opacity", 0);


//        // phantom node to give us mouseover in a radius around it
//        nodeEnter.append("circle")
//            	.attr('class', 'ghostCircle')
//            	.attr("r", 30)
//            	.attr("opacity", 0.2) // change this to zero to hide the target area
//        	.style("fill", "red")
//            .attr('pointer-events', 'mouseover')
//            .on("mouseover", function(node) {
//                overCircle(node);
//            })
//            .on("mouseout", function(node) {
//                outCircle(node);
//            });


        // Update the text to reflect whether node has children or not.
        node.select('text')
            .attr("x", function(d) {
                return d.children || d._children ? -10 : 10;
            })
            .attr("text-anchor", function(d) {
                return d.children || d._children ? "end" : "start";
            })
            .text(function(d) {
                return d.name;
            });

    var outCircle = function(d) {
        selectedNode = null;
        updateTempConnector();
    };

        // Change the circle fill depending on whether it has children and is collapsed
        node.select("circle.nodeCircle")
            .attr("r", 4.5)
//            .attr("fill", function(d) {
//                return d._children ? "green" : "#fff";
//            })
        ;


        // Transition nodes to their new position.
        var nodeUpdate = node.transition()
            .duration(duration)
            .attr("transform", function(d) {
                return "translate(" + d.y + "," + d.x + ")";
            });

        // Fade the text in
        nodeUpdate.select("text")
            .style("fill-opacity", 1);

        // Transition exiting nodes to the parent's new position.
        var nodeExit = node.exit().transition()
            .duration(duration)
            .attr("transform", function(d) {
                return "translate(" + source.y + "," + source.x + ")";
            })
            .remove();

        nodeExit.select("circle")
            .attr("r", 0);

        nodeExit.select("text")
            .style("fill-opacity", 0);

        // Update the links…
        var link = svgGroup.selectAll("path.link")
            .data(links, function(d) {
                return d.target.id;
            });

        // Enter any new links at the parent's previous position.
        link.enter().insert("path", "g")
            .attr("class", "link")
            .attr("d", function(d) {
                var o = {
                    x: source.x0,
                    y: source.y0
                };
                return diagonal({
                    source: o,
                    target: o
                });
            });

        // Transition links to their new position.
        link.transition()
            .duration(duration)
            .attr("d", diagonal);

        // Transition exiting nodes to the parent's new position.
        link.exit().transition()
            .duration(duration)
            .attr("d", function(d) {
                var o = {
                    x: source.x,
                    y: source.y
                };
                return diagonal({
                    source: o,
                    target: o
                });
            })
            .remove();

        // Stash the old positions for transition.
        nodes.forEach(function(d) {
            d.x0 = d.x;
            d.y0 = d.y;
        });
    }


    // Append a group which holds all nodes and which the zoom Listener can act upon.
    var svgGroup = baseSvg.append("g");

    // Define the root
    root = treeData;
    root.x0 = viewerHeight / 2;
    root.y0 = 0;

    // Layout the tree initially and center on the root node.

    // Ken Haug - First collaps all children
    root.children.forEach(collapse)

    update(root);
    centerNode(root);
});
