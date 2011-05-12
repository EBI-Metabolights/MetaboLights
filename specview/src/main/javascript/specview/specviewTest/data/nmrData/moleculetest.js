var moleculetest="";


moleculetest=moleculetest+
"<?xml version=1.0 encoding=ISO-8859-1?>\n"+
"<cml convention=nmrshiftdb-convention xmlns=http://www.xml-cml.org/schema>\n"+
 "<molecule title=ethyl 2-(2-amino-1,3-thiazol-4-yl)acetate id=nmrshiftdb20220216>\n"+
  "<atomArray>\n"+
   "<atom id=a1 elementType=C x2=-1.2698 y2=0.0276 formalCharge=0 hydrogenCount=1 isotopeNumber=12/>\n"+
   "<atom id=a2 elementType=C x2=-1.7333 y2=-1.399 formalCharge=0 hydrogenCount=0 isotopeNumber=12/>\n"+
   "<atom id=a3 elementType=C x2=-3.6969 y2=0.0276 formalCharge=0 hydrogenCount=0 isotopeNumber=12/>\n"+
   "<atom id=a4 elementType=C x2=-0.8517 y2=-2.6125 formalCharge=0 hydrogenCount=2 isotopeNumber=12/>\n"+
   "<atom id=a5 elementType=C x2=0.6483 y2=-2.6125 formalCharge=0 hydrogenCount=0 isotopeNumber=12/>\n"+
   "<atom id=a6 elementType=C x2=2.8983 y2=-3.9115 formalCharge=0 hydrogenCount=2 isotopeNumber=12/>\n"+
   "<atom id=a7 elementType=C x2=3.6483 y2=-5.2106 formalCharge=0 hydrogenCount=3 isotopeNumber=12/>\n"+
   "<atom id=a8 elementType=S x2=-2.4833 y2=0.9093 formalCharge=0 hydrogenCount=0 isotopeNumber=32/>\n"+
   "<atom id=a9 elementType=N x2=-3.2333 y2=-1.399 formalCharge=0 hydrogenCount=0 isotopeNumber=14/>\n"+
   "<atom id=a10 elementType=N x2=-5.1234 y2=0.4912 formalCharge=0 hydrogenCount=2 isotopeNumber=14/>\n"+
   "<atom id=a11 elementType=O x2=1.3983 y2=-3.9115 formalCharge=0 hydrogenCount=0 isotopeNumber=16/>\n"+
   "<atom id=a12 elementType=O x2=1.3983 y2=-1.3134 formalCharge=0 hydrogenCount=0 isotopeNumber=16/>\n"+
  "</atomArray>\n"+
  "<bondArray>\n"+
   "<bond id=b1 atomRefs2=a8 a1 order=S/>\n"+
   "<bond id=b2 atomRefs2=a1 a2 order=D/>\n"+
   "<bond id=b3 atomRefs2=a2 a9 order=S/>\n"+
   "<bond id=b4 atomRefs2=a9 a3 order=D/>\n"+
   "<bond id=b5 atomRefs2=a3 a8 order=S/>\n"+
   "<bond id=b6 atomRefs2=a3 a10 order=S/>\n"+
   "<bond id=b7 atomRefs2=a2 a4 order=S/>\n"+
   "<bond id=b8 atomRefs2=a4 a5 order=S/>\n"+
   "<bond id=b9 atomRefs2=a5 a11 order=S/>\n"+
   "<bond id=b10 atomRefs2=a5 a12 order=D/>\n"+
   "<bond id=b11 atomRefs2=a11 a6 order=S/>\n"+
   "<bond id=b12 atomRefs2=a6 a7 order=S/>\n"+
  "</bondArray>\n"+
  "<scalar dictRef=cdk:molecularProperty title=cdk:Remark dataType=xsd:string>NMRShiftDB 20220216</scalar>\n"+
 "</molecule>\n"+
 "<spectrum id=nmrshiftdb20226893 moleculeRef=nmrshiftdb20220216 type=NMR xmlns:macie=http://www.xml-cml.org/dict/macie xmlns:nmr=http://www.nmrshiftdb.org/dict xmlns:siUnits=http://www.xml-cml.org/units/siUnits xmlns:subst=http://www.xml-cml.org/dict/substDict xmlns:cmlDict=http://www.xml-cml.org/dict/cmlDict xmlns:cml=http://www.xml-cml.org/dict/cml xmlns:units=http://www.xml-cml.org/units/units>\n"+
  "<conditionList>\n"+
   "<scalar dataType=xsd:string dictRef=cml:field units=siUnits:megahertz>100.6</scalar>\n"+
   "<scalar dataType=xsd:string dictRef=cml:temp units=siUnits:k>295</scalar>\n"+
  "</conditionList>\n"+
  "<metadataList>\n"+
   "<metadata name=nmr:assignmentMethod content=1D shift positions/>\n"+
   "<metadata name=nmr:OBSERVENUCLEUS content=13C/>\n"+
  "</metadataList>\n"+
  "<substanceList>\n"+
   "<substance dictRef=cml:solvent role=subst:solvent title=Dimethylsulphoxide-D6 (DMSO-D6, C2D6SO))/>\n"+
  "</substanceList>\n"+
  "<peakList>\n"+
   "<peak xValue=103.12 xUnits=units:ppm peakShape=sharp peakMultiplicity=D id=p0 atomRefs=a1/>\n"+
   "<peak xValue=14.11 xUnits=units:ppm peakShape=sharp peakMultiplicity=Q id=p1 atomRefs=a7/>\n"+
   "<peak xValue=144.37 xUnits=units:ppm peakShape=sharp peakMultiplicity=S id=p2 atomRefs=a2/>\n"+
   "<peak xValue=168.24 xUnits=units:ppm peakShape=sharp peakMultiplicity=S id=p3 atomRefs=a3/>\n"+
   "<peak xValue=170.18 xUnits=units:ppm peakShape=sharp peakMultiplicity=S id=p4 atomRefs=a5/>\n"+
   "<peak xValue=36.93 xUnits=units:ppm peakShape=sharp peakMultiplicity=T id=p5 atomRefs=a4/>\n"+
   "<peak xValue=60.16 xUnits=units:ppm peakShape=sharp peakMultiplicity=T id=p6 atomRefs=a6/>\n"+
  "</peakList>\n"+
 "</spectrum>\n"+
"</cml>";