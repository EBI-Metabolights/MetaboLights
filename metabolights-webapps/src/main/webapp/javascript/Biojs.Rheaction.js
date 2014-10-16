Biojs.Rheaction = Biojs.extend({constructor: function(a) {
        Biojs.console.enable();
        this.setId(this.opt.id);
    }, setId: function(c) {
        this._clearContent();
        var a = this;
        var b = c.replace("RHEA:", "");
        this._rheaIdLabel = "RHEA_" + b;
        if ("string" == (typeof this.opt.target)) {
            this._container = jQuery("#" + this.opt.target);
        } else {
            this.opt.target = "biojs_Rheaction_" + b;
            this._container = jQuery('<div id="' + this.opt.target + '"></div>');
        }
        this._container.addClass("scrollpane");
        this._reactionRow = jQuery("<div/>", {"class": "reactionRow"});
        this._container.append(this._reactionRow);
        this._getCml(b);
    }, opt: {target: undefined, id: undefined, dimensions: "200", proxyUrl: "../biojs/dependencies/proxy/proxy.php", rheaWsUrl: "http://www.ebi.ac.uk/rhea/rest/1.0/ws/reaction/cmlreact/", chebiUrl: "http://www.ebi.ac.uk/chebi/searchId.do?chebiId=", compoundImgUrl: "http://www.ebi.ac.uk/rhea/compoundImage.xhtml?", showCompoundAccession: false, showChebiId: false, showFormulaAndCharge: false}, _clearContent: function() {
        jQuery("#" + this.opt.target).html("");
    }, _displayNoDataMessage: function() {
        jQuery("#" + this.opt.target + "").html(Biojs.Rheaction.MESSAGE_NODATA);
    }, toggleAccession: function() {
        jQuery(".accession").toggle();
    }, toggleChebiId: function() {
        jQuery(".chebiId").toggle();
    }, toggleFormulaAndCharge: function() {
        jQuery(".formula").toggle();
        jQuery(".charge").toggle();
    }, _getCml: function(d) {
        var a = this;
        var c = this.opt.rheaWsUrl + d;
        var b = {
            url: c,
            method: "GET",
            success: function(e) {

                a._dataReceived(e);


            }, error: function(f, g, e) {
                Biojs.console.log("ERROR requesting reaction. Response: " + g);
                a._displayNoDataMessage();
            }};
        if (this.opt.proxyUrl != undefined) {
            b.url = this.opt.proxyUrl;
            b.data = [{name: "url", value: c}];
            b.dataType = "text";

        }
        jQuery.ajax(b);
    }, _dataReceived: function(b) {
        var a = this;
        var f = {};
        var d = "";
        if (b.length > 0) {
            try {

                d = jQuery.parseXML(b.trim());


                var xmlResult = jQuery(d).find("reaction");
                var j = xmlResult.find("reactant");



                for (var c = 0;
                        c < j.length;
                        c++) {
                    if (c > 0) {
                        a._addPlus();
                    }
                    a._addParticipant(j[c]);
                }
                a._addDirection(xmlResult.attr("convention"));
                var h = xmlResult.find("product");
                for (var c = 0;
                        c < h.length;
                        c++) {
                    if (c > 0) {
                        a._addPlus();
                    }
                    a._addParticipant(h[c]);
                }
            } catch (g) {
                Biojs.console.log("ERROR decoding ");
                Biojs.console.log(g);
                a._displayNoDataMessage();
            }
        }
    }, _addPlus: function() {
        jQuery("<div/>", {"class": "direction", html: "+"}).appendTo(this._reactionRow);
    }, _addDirection: function(a) {
        var c = a.replace("rhea:direction.", "");
        var b = undefined;
        switch (c) {
            case"UN":
                //b = "&lt;?&gt;";
                b = '<span class="icon icon-chemistry forReactChemistry" data-icon="U"></span>';
                break;
            case"BI":

                b = "&lt;=&gt;";
                break;
            default:
                b = "=&gt;";
                break;
        }
        jQuery("<div/>", {"class": "direction", html: b}).appendTo(this._reactionRow);
    }, _getCoefNameElement: function(e, a, b, c) {
        var d = jQuery("<div/>", {"class": "coefName"});
        if (e > 1) {
            d.append(jQuery("<span/>", {"class": "stoichCoef", html: e}));
        }
        var f;
        if (c) {
            f = jQuery("<a/>", {href: this.opt.chebiUrl + c, html: a});
        } else {
            f = jQuery("<span/>", {"class": "compoundName", html: a, title: b});
        }
        d.append(f);
        return d;
    }, _getCompoundAccessionElement: function(a, b) {
        var c;
        if (b) {
            c = jQuery("<div/>", {"class": "chebiId", css: {display: this.opt.showChebiId ? "inline" : "none"}}).append(jQuery("<a/>", {href: this.opt.chebiUrl + b, html: a}));
        } else {
            c = jQuery("<div/>", {"class": "accession", html: a, css: {display: this.opt.showCompoundAccession ? "inline" : "none"}});
        }
        return c;
    }, _getCompoundImage: function(b, c, a) {
        var d = this.opt.compoundImgUrl + "dimensions=" + this.opt.dimensions;
        if (c) {
            d += "&polymerId=" + c;
        } else {
            if (b) {
                d += "&chebiId=" + b;
            }
        }
        return jQuery("<img/>",
                {src: d,
                    "class": "compoundStructure",
                    title: a,
                    css: {minWidth: this.opt.dimensions + "px"}
                }).error(function() {
                    $(this).hide();
                });

    }, _getFormulaElement: function(a) {
        return jQuery("<div/>", {"class": "formula", html: "<i>Formula:</i> " + (a ? a.replace(/ 1 | 1$| (?!1 )/g, "") : "N/A"), css: {display: this.opt.showFormulaAndCharge ? "inline" : "none"}});
    }, _getChargeElement: function(a) {
        return jQuery("<div/>", {"class": "charge", html: "<i>Charge:</i> " + (a ? a : "N/A"), css: {display: this.opt.showFormulaAndCharge ? "inline" : "none"}});
    }, _getPositionElement: function(a) {
        return jQuery("<div/>", {"class": "position", html: "<i>Position:</i> " + (a ? a : "N/A")});
    }, _getResidueElement: function(c) {
        var b = c.attr("formula");
        var f = c.attr("formalCharge");
        var a = c.find("name").contents()[0].data;
        var h = c.find("identifier").attr("value");
        var g = h.replace("CHEBI:", "");
        var d = c.find('label[objectClass="location"]').attr("value");
        var e = jQuery("<div/>", {"class": "residue"}).append(this._getCompoundImage(h, null, h), this._getCoefNameElement(1, a, h, g), this._getCompoundAccessionElement(h, g), this._getFormulaElement(b), this._getChargeElement(f), this._getPositionElement(d));
        return e;
    }, _addParticipant: function(l) {
        var m = this;
        var i = parseInt(l.attributes.count.value);
        var f = jQuery(l).find("molecule");
        var h = f.attr("formula");
        var g = f.attr("formalCharge");
        var k = f.find("name").contents()[0].data;
        var d = f.find("identifier").attr("value");
        var c = undefined;
        var e = undefined;
        if (d.lastIndexOf("CHEBI:", 0) === 0) {
            c = d.replace("CHEBI:", "");
        } else {
            if (d.lastIndexOf("POLYMER:", 0) === 0) {
                e = d.replace("POLYMER:", "");
            }
        }
        var b = jQuery("<div/>", {"class": "compound"});
        this._reactionRow.append(b);
        b.append(this._getCoefNameElement(i, k, d, c), this._getCompoundAccessionElement(d, c), this._getFormulaElement(h), this._getChargeElement(g));
        if (c) {
            b.append(this._getCompoundImage(d, null, d));
        } else {
            if (e) {
                var j = f.find("molecule").find("identifier").attr("value");
                b.append(this._getCompoundAccessionElement(j, j), this._getCompoundImage(null, e, d));
            } else {
                var a = jQuery("<div>", {"class": "residues"});
                b.append(a);
                f.find("molecule").each(function(n, o) {
                    a.append(m._getResidueElement(jQuery(o)));
                });
            }
        }
    }}, {MESSAGE_NODATA: "Sorry, no results for your request", });
