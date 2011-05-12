"<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?> \n"+
"<cml convention=\"nmrshiftdb-convention\" xmlns=\"http://www.xml-cml.org/schema\"> \n"+
 "<molecule title=\"Benzene\" id=\"nmrshiftdb7901\"> \n"+
  "<atomArray> \n"+
   "<atom id=\"a1\" elementType=\"C\" x2=\"-1.4063\" y2=\"0.7625\" formalCharge=\"0\" hydrogenCount=\"1\" isotopeNumber=\"12\"/> \n"+
   "<atom id=\"a2\" elementType=\"C\" x2=\"-2.1207\" y2=\"0.35\" formalCharge=\"0\" hydrogenCount=\"1\" isotopeNumber=\"12\"/> \n"+
   "<atom id=\"a3\" elementType=\"C\" x2=\"-2.1207\" y2=\"-0.475\" formalCharge=\"0\" hydrogenCount=\"1\" isotopeNumber=\"12\"/> \n"+
   "<atom id=\"a4\" elementType=\"C\" x2=\"-1.4063\" y2=\"-0.8875\" formalCharge=\"0\" hydrogenCount=\"1\" isotopeNumber=\"12\"/> \n"+
   "<atom id=\"a5\" elementType=\"C\" x2=\"-0.6918\" y2=\"-0.475\" formalCharge=\"0\" hydrogenCount=\"1\" isotopeNumber=\"12\"/> \n"+
   "<atom id=\"a6\" elementType=\"C\" x2=\"-0.6918\" y2=\"0.35\" formalCharge=\"0\" hydrogenCount=\"1\" isotopeNumber=\"12\"/> \n"+
  "</atomArray> \n"+
  "<bondArray> \n"+
   "<bond id=\"b1\" atomRefs2=\"a1 a2\" order=\"S\"/> \n"+
   "<bond id=\"b2\" atomRefs2=\"a2 a3\" order=\"D\"/> \n"+
   "<bond id=\"b3\" atomRefs2=\"a3 a4\" order=\"S\"/> \n"+
   "<bond id=\"b4\" atomRefs2=\"a4 a5\" order=\"D\"/> \n"+
   "<bond id=\"b5\" atomRefs2=\"a5 a6\" order=\"S\"/> \n"+
   "<bond id=\"b6\" atomRefs2=\"a1 a6\" order=\"D\"/> \n"+
  "</bondArray> \n"+
  "<scalar dictRef=\"cdk:molecularProperty\" title=\"cdk:Remark\" dataType=\"xsd:string\"> NMRShiftDB 7901</scalar> \n"+
 "</molecule> \n"+
 "<spectrum id=\"nmrshiftdb15502\" moleculeRef=\"nmrshiftdb7901\" type=\"NMR\" xmlns:macie=\"http://www.xml-cml.org/dict/macie\" xmlns:nmr=\"http://www.nmrshiftdb.org/dict\" xmlns:siUnits=\"http://www.xml-cml.org/units/siUnits\" xmlns:subst=\"http://www.xml-cml.org/dict/substDict\" xmlns:cmlDict=\"http://www.xml-cml.org/dict/cmlDict\" xmlns:cml=\"http://www.xml-cml.org/dict/cml\" xmlns:units=\"http://www.xml-cml.org/units/units\"> \n"+
  "<conditionList> \n"+
   "<scalar dataType=\"xsd:string\" dictRef=\"cml:temp\" units=\"siUnits:k\">298</scalar> \n"+
   "<scalar dataType=\"xsd:string\" dictRef=\"cml:field\" units=\"siUnits:megahertz\"> Unreported</scalar> \n"+
  "</conditionList> \n"+
  "<metadataList> \n"+
   "<metadata name=\"nmr:OBSERVENUCLEUS\" content=\"13C\"/> \n"+
  "</metadataList> \n"+
  "<substanceList> \n"+
   "<substance dictRef=\"cml:solvent\" role=\"subst:solvent\" title=\"Chloroform-D1 (CDCl3)\"/> \n"+
  "</substanceList> \n"+
  "<peakList> \n"+
   "<peak xValue=\"128.5\" xUnits=\"units:ppm\" peakShape=\"sharp\" peakMultiplicity=\"D\" id=\"p0\" atomRefs=\"a1 a2 a3 a4 a5 a6\"/> \n"+
  "</peakList> \n"+
 "</spectrum> \n"+
"</cml> \n";