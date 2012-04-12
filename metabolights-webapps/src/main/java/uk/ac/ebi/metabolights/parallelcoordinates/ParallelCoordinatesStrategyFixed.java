package uk.ac.ebi.metabolights.parallelcoordinates;

import java.util.HashMap;
import java.util.Map;

import uk.ac.ebi.bioinvindex.model.AssayGroup;
import uk.ac.ebi.bioinvindex.model.Study;

public class ParallelCoordinatesStrategyFixed implements ParallelCoordinatesStrategy{

	@Override
	public ParallelCoordinatesDataSet Proccess(AssayGroup ag, Study study) {
		
		ParallelCoordinatesDataSet ds = new ParallelCoordinatesDataSet();
		
		ParallelCoordinatesSeries serie = new ParallelCoordinatesSeries("MET1");	serie.values.add( new ParallelCoordinatesSerieValue("cF",0.0127118289285714));	serie.values.add( new ParallelCoordinatesSerieValue("cM",0.00562077678571429));	serie.values.add( new ParallelCoordinatesSerieValue("TF",0.0107149134615385));	serie.values.add( new ParallelCoordinatesSerieValue("TM",0.00491675454545454));	ds.series.add(serie);
		serie = new ParallelCoordinatesSeries("MET10");	serie.values.add( new ParallelCoordinatesSerieValue("cF",0.00165475071428571));	serie.values.add( new ParallelCoordinatesSerieValue("cM",0.000927636785714286));	serie.values.add( new ParallelCoordinatesSerieValue("TF",0.00151920115384615));	serie.values.add( new ParallelCoordinatesSerieValue("TM",0.00131282545454545));	ds.series.add(serie);
		serie = new ParallelCoordinatesSeries("MET11");	serie.values.add( new ParallelCoordinatesSerieValue("cF",0.000896626071428571));	serie.values.add( new ParallelCoordinatesSerieValue("cM",0.000759446964285714));	serie.values.add( new ParallelCoordinatesSerieValue("TF",0.00153873423076923));	serie.values.add( new ParallelCoordinatesSerieValue("TM",0.000778347727272727));	ds.series.add(serie);
		serie = new ParallelCoordinatesSeries("MET12");	serie.values.add( new ParallelCoordinatesSerieValue("cF",0.000887983214285714));	serie.values.add( new ParallelCoordinatesSerieValue("cM",0.000774774107142857));	serie.values.add( new ParallelCoordinatesSerieValue("TF",0.00136791846153846));	serie.values.add( new ParallelCoordinatesSerieValue("TM",0.000914176818181818));	ds.series.add(serie);
		serie = new ParallelCoordinatesSeries("MET2");	serie.values.add( new ParallelCoordinatesSerieValue("cF",0.00206058214285714));	serie.values.add( new ParallelCoordinatesSerieValue("cM",0.00114161928571429));	serie.values.add( new ParallelCoordinatesSerieValue("TF",0.00214918346153846));	serie.values.add( new ParallelCoordinatesSerieValue("TM",0.000803964545454545));	ds.series.add(serie);
		serie = new ParallelCoordinatesSeries("MET3");	serie.values.add( new ParallelCoordinatesSerieValue("cF",0.00937391964285714));	serie.values.add( new ParallelCoordinatesSerieValue("cM",0.00507137821428572));	serie.values.add( new ParallelCoordinatesSerieValue("TF",0.0107297807692308));	serie.values.add( new ParallelCoordinatesSerieValue("TM",0.00522289136363636));	ds.series.add(serie);
		serie = new ParallelCoordinatesSeries("MET4");	serie.values.add( new ParallelCoordinatesSerieValue("cF",0.00161206428571429));	serie.values.add( new ParallelCoordinatesSerieValue("cM",0.00066607125));	serie.values.add( new ParallelCoordinatesSerieValue("TF",0.00205266192307692));	serie.values.add( new ParallelCoordinatesSerieValue("TM",0.000808535454545455));	ds.series.add(serie);
		serie = new ParallelCoordinatesSeries("MET5");	serie.values.add( new ParallelCoordinatesSerieValue("cF",0.00131519285714286));	serie.values.add( new ParallelCoordinatesSerieValue("cM",0.00112694196428571));	serie.values.add( new ParallelCoordinatesSerieValue("TF",0.00365610769230769));	serie.values.add( new ParallelCoordinatesSerieValue("TM",0.000963568181818182));	ds.series.add(serie);
		serie = new ParallelCoordinatesSeries("MET6");	serie.values.add( new ParallelCoordinatesSerieValue("cF",0.00151993392857143));	serie.values.add( new ParallelCoordinatesSerieValue("cM",0.000688504464285714));	serie.values.add( new ParallelCoordinatesSerieValue("TF",0.00184195538461538));	serie.values.add( new ParallelCoordinatesSerieValue("TM",0.00119498727272727));	ds.series.add(serie);
		serie = new ParallelCoordinatesSeries("MET7");	serie.values.add( new ParallelCoordinatesSerieValue("cF",0.00126676785714286));	serie.values.add( new ParallelCoordinatesSerieValue("cM",0.000890559821428572));	serie.values.add( new ParallelCoordinatesSerieValue("TF",0.00110192923076923));	serie.values.add( new ParallelCoordinatesSerieValue("TM",0.00108852818181818));	ds.series.add(serie);
		serie = new ParallelCoordinatesSeries("MET8");	serie.values.add( new ParallelCoordinatesSerieValue("cF",0.010838035));	serie.values.add( new ParallelCoordinatesSerieValue("cM",0.0101326089285714));	serie.values.add( new ParallelCoordinatesSerieValue("TF",0.0210866884615385));	serie.values.add( new ParallelCoordinatesSerieValue("TM",0.0191487090909091));	ds.series.add(serie);
		serie = new ParallelCoordinatesSeries("MET9");	serie.values.add( new ParallelCoordinatesSerieValue("cF",0.00431836071428572));	serie.values.add( new ParallelCoordinatesSerieValue("cM",0.00254798732142857));	serie.values.add( new ParallelCoordinatesSerieValue("TF",0.00487993076923077));	serie.values.add( new ParallelCoordinatesSerieValue("TM",0.00420011954545455));	ds.series.add(serie);
	


		
		ParallelCoordinatesUnit unit = new ParallelCoordinatesUnit("cF", "control - Female", "");
		ds.units.add(unit);
		
		unit = new ParallelCoordinatesUnit("cM", "control - Male", "");
		ds.units.add(unit);
		
		unit = new ParallelCoordinatesUnit("TF", "T2DM - Female", "");
		ds.units.add(unit);

		unit = new ParallelCoordinatesUnit("TM", "T2DM - Male", "");
		ds.units.add(unit);

		
		
		// Return the fake fixed
		return ds;	
		

	}

}
