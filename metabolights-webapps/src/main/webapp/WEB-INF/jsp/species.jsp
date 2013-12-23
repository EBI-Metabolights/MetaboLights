<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script src="http://d3js.org/d3.v3.min.js"></script>

	<h2>
    	<spring:message code="menu.speciespageheader" />
    </h2>
    <p>
        <spring:message code="menu.speciespagedescription" />
    </p>
    <div class="grid_8">
        <h3><spring:message code="menu.speciesmodeltitle"/></h3>
        <ul class="species">
            <li class="icon icon-species" data-icon="H"><A href="reference?organisms=Homo sapiens (Human)">Homo sapiens (Human)</a></li>
            <li class="icon icon-species" data-icon="M"><a href="reference?organisms=Mus musculus (Mouse)">Mus musculus (Mouse)</a></li>
            <%--<li class="icon icon-species" data-icon="B"><a href="reference?Arabidopsis thaliana (thale cress)">Arabidopsis thaliana (thale cress)</a></li>--%>
            <%--<li class="icon icon-species" data-icon="L"><a href="reference?organisms=">E. coli</li>--%>
            <li class="icon icon-species" data-icon="Y"><a href="reference?organisms=Saccharomyces cerevisiae">Yeast</a></li>
            <li class="icon icon-species" data-icon="W"><a href="reference?organisms=Caenorhabditis elegans">Caenorhabditis elegans</a></li>
            <%--<li class="icon icon-species" data-icon="F"><a href="reference?organisms=Drosophila">Drosophila</li>--%>
        </ul>
    </div>
    <div class="grid_16">
        <h3><spring:message code="menu.speciesbrowsetitle"/></h3>
        <div id="speciescope"></div>
    </div>

<script>
    var margin = 10,
            outerDiameter = 600,
            innerDiameter = outerDiameter - margin - margin;

    var x = d3.scale.linear()
            .range([0, innerDiameter]);

    var y = d3.scale.linear()
            .range([0, innerDiameter]);

    var color = d3.scale.linear()
            .domain([-1, 5])
            .range(["hsl(30,56%,74%)", "hsl(30,67%,30%)"])
            .interpolate(d3.interpolateHcl);

    var pack = d3.layout.pack()
            .padding(2)
            .size([innerDiameter, innerDiameter])
            .value(function(d) { return d.size; })

    var svg = d3.select("#speciescope").append("svg")
            .attr("width", outerDiameter)
            .attr("height", outerDiameter)
            .append("g")
            .attr("transform", "translate(" + margin + "," + margin + ")");

    d3.json("static/species2.json", function(error, root) {
        var focus = root,
                nodes = pack.nodes(root);

        svg.append("g").selectAll("circle")
                .data(nodes)
                .enter().append("circle")
                .attr("class", function(d) { return d.parent ? d.children ? "node" : "node node--leaf" : "node node--root"; })
                .attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; })
                .attr("r", function(d) { return d.r; })
                .attr("level", function(d) { return d.level; })
                .style("fill", function(d) { return d.children ? color(d.depth) : null; })
                .style("display", function(d) { return d.level < 2 ? null : "none"; })
                .on("click", function(d) { return zoom(focus == d ? root : d); });

        svg.append("g").selectAll("text")
                .data(nodes)
                .enter().append("text")
                .attr("class", "label")
                .attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; })
                .style("fill-opacity", function(d) { return d.parent === root ? 1 : 0; })
                .style("display", function(d) { return d.parent === root ? null : "none"; })
                .text(function(d) { return d.name; });

        d3.select(window)
                .on("click", function() { zoom(root); });

        function zoom(d, i) {
            var focus0 = focus;
            focus = d;

            var k = innerDiameter / d.r / 2;
            x.domain([d.x - d.r, d.x + d.r]);
            y.domain([d.y - d.r, d.y + d.r]);
            d3.event.stopPropagation();

            var transition = d3.selectAll("text,circle").transition()
                    .duration(d3.event.altKey ? 7500 : 750)
                    .attr("transform", function(d) { return "translate(" + x(d.x) + "," + y(d.y) + ")"; });

            transition.filter("circle")
                    .attr("r", function(d) { return k * d.r; })
                    .style("fill-opacity", function(d) { return (d.level <= (focus.level+1)? 1 : 0); })
//                    .each("start", function(d) { if (d.level == (focus.level+1)) this.style.display = "none"; })
                    .each("end", function(d) { this.style.display =  (d.level <= (focus.level+1))?"inLine":"none"; });


            transition.filter("text")
                    .filter(function(d) { return d.parent === focus || d.parent === focus0; })
                    .style("fill-opacity", function(d) { return d.parent === focus ? 1 : 0; })
                    .each("start", function(d) { if (d.parent === focus) this.style.display = "inline"; })
                    .each("end", function(d) { if (d.parent !== focus) this.style.display = "none"; });
        }
    });

    d3.select(self.frameElement).style("height", outerDiameter + "px");

</script>
