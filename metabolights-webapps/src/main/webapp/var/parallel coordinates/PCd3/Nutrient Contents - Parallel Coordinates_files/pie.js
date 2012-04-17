(function(d3) {

  window.piegroups = function(data, keys, colors, group) {
    var self = {};

    // var keys = _(data).chain().groupBy(group).keys().value();

    var w = 100,
        h = 80,
        r = Math.min(w, h) / 2,
        donut = d3.layout.pie().sort(null),
        arc = d3.svg.arc().innerRadius(r - 28).outerRadius(r - 6);

    var svg = d3.select("#pie").append("svg:svg")
        .attr("width", w)
        .attr("height", h)
      .append("svg:g")
        .attr("transform", "translate(" + w / 2 + "," + h / 2 + ")");

    var arcs = svg.selectAll("path")
        .data(donut(count(data)))
      .enter().append("svg:path")
        .attr("fill", function(d, i) { return colors[keys[i]]; })
        .attr("d", arc)
        .each(function(d, i) {
          d3.select(this).append("svg:title").text(keys[i]);
        });

    self.update =  function(data) {
      if (_.isEmpty(data)) return;
      arcs = arcs.data(donut(count(data)));
      arcs.attr("d", arc);
    };

    function count(data) {
      var counts = {};
      _(data)
        .chain()
        .groupBy(group)
        .each(function(v,k) {
          counts[k] = v.length;
        });

      return _(keys).map(function(k) {
        if (k in counts) {
          return counts[k];
        } else {
          return 0;
        }
      });
    };

    return self;
  };

  window.pietotals = function(keys, data) {
    var self = {};

    var w = 100,
        h = 80,
        r = Math.min(w, h) / 2,
        donut = d3.layout.pie().sort(null),
        arc = d3.svg.arc().innerRadius(r - 28).outerRadius(r - 6);

    var svg = d3.select("#totals").append("svg:svg")
        .attr("width", w)
        .attr("height", h)
      .append("svg:g")
        .attr("transform", "translate(" + w / 2 + "," + h / 2 + ")");

    var arcs = svg.selectAll("path")
        .data(donut(data))
      .enter().append("svg:path")
        .attr("class", function(d, i) { return keys[i]; })
        .attr("d", arc)
        .each(function(d, i) {
          d3.select(this).append("svg:title").text(keys[i]);
        });

    self.update =  function(data) {
      arcs = arcs.data(donut(data));
      arcs.attr("d", arc);
    };

    return self;
  }; 
})(d3);
