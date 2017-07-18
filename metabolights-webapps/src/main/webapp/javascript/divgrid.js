/**
 * Created by CS76Bot on 22/03/2017.
 */
d3.divgrid = function(config) {
    var columns = [];

    var dg = function(selection) {
        if (columns.length == 0) columns = d3.keys(selection.data()[0][0]);

        // header
        selection.selectAll(".header")
            .data([true])
            .enter().append("tr")
            .attr("class", "header")

        var header = selection.select(".header")
            .selectAll(".cell")
            .data(columns);

        header.enter().append("th")
            .attr("class", function(d,i) { return "col-" + i; })
            .classed("cell", true)

        selection.selectAll(".header .cell")
            .text(function(d) { return d; });

        header.exit().remove();

        // rows
        var rows = selection.selectAll(".table-row")
            .data(function(d) { return d; })

        rows.enter().append("tr")
            .attr("class", "table-row")

        rows.exit().remove();

        var cells = selection.selectAll(".table-row").selectAll(".cell")
            .data(function(d) { return columns.map(function(col){return d[col];}) })

        // cells
        cells.enter().append("td")
            .attr("class", function(d,i) { return "col-" + i; })
            .classed("cell", true)

        cells.exit().remove();

        selection.selectAll(".cell")
            .text(function(d) { return d; });

        return dg;
    };

    dg.columns = function(_) {
        if (!arguments.length) return columns;
        columns = _;
        return this;
    };

    return dg;
};