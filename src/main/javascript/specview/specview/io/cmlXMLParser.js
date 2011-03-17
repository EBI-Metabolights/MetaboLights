goog.provide('specview.io.CmlXMLparser');
goog.require('goog.dom.xml');
goog.require('goog.string');
goog.require('goog.testing.jsunit');
goog.require('goog.userAgent');


specview.io.CmlXMLparser = function() {
	alert("hi");
};

specview.io.CmlXMLparser.prototype.parseCML=function(cmlString) {
	var document = goog.dom.xml.loadXml(cmlString);
	//alert(cmlDocument.getElementsByTagName().length);

	var allAtoms = document.getElementsByTagName("atom");
	var allAtomCount = allAtoms.length;
	alert("There are " + allAtomCount + " <atom> elements in this document");
	return "OKAY";
};
