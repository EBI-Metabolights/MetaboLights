package uk.ac.ebi.metabolights.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

import org.apache.lucene.document.Document;

import uk.ac.ebi.bioinvindex.search.hibernatesearch.StudyBrowseField;

/**
 * Stores a BII Lucene Document and wraps it with accessors.
 * @author markr
 */
public class LuceneSearchResult {

	private List<Assay> assays;
	private List<String> factors;
	private List<String> properties;

	
	public LuceneSearchResult(Document doc, float score) {
		this.doc=doc;
		this.score=score;
		this.assays=parseAssays();
		this.factors=getValues(StudyBrowseField.FACTOR_NAME.getName());
		this.properties=getValues("property_value");
	}
	
	private Document doc;
	private float score;
	
	public Document getDoc() {
		return doc;
	}
	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public String getAccInvestigation() {
		return doc.get(StudyBrowseField.INVESTIGATION_ACC.getName());
	}

	public String getAccStudy() {
		return doc.get(StudyBrowseField.STUDY_ACC.getName());
	}
	
	public String getTitle() {
		return doc.get(StudyBrowseField.TITLE.getName());
	}

	public String getOrganism() {
		return doc.get(StudyBrowseField.ORGANISM.getName());
	}
	
	public List<String> getProperties() {
		return properties;
	}
	
	public List<String> getFactors() {
		return factors;
	}

	public List<Assay> getAssays() {
		return assays;
	}

	public String getPubAuthors() {
		return doc.get("publication_authorList");
	}

	public String getPubId() {
		return doc.get("publication_id");
	}

	public String getUserName() {
		return doc.get("user_userName");
	}

	public String getUserId() {
		return doc.get("user_id");
	}

	public String getDescription() {
		return doc.get("description");
	}
	
	public String getDesign() {
		return doc.get("design_value");
	}	
	
	/**
	 * Holds assay information related to a study.
	 * @author markr
	 *
	 */
	public class Assay {
		
		public String getMeasurement() {
			return measurement;
		}
		public void setMeasurement(String measurement) {
			this.measurement = measurement;
		}
		public String getTechnology() {
			return technology;
		}
		public void setTechnology(String technology) {
			this.technology = technology;
		}
		public String getCount() {
			return count;
		}
		public void setCount(String count) {
			this.count = count;
		}
		private String measurement;
		private String technology;
		private String count;
		
		public Assay() {
		}

		public Assay(String measurement, String technology, String count) {
			this.measurement=measurement;
			this.technology=technology;
			this.count=count;
		}
	}
	
	
	/**
	 * Why is a Set necessary? Because there are duplicates in the result. Like the same factor over and over.. 
	 * @param fieldName
	 * @return
	 */
	private List<String> getValues (String fieldName) {
		SortedSet<String> set = new TreeSet<String>(); 
		String[] values = doc.getValues(fieldName);
		for (String val :values) {
			set.add(val);
		}
		return Arrays.asList(set.toArray(new String[set.size()]));
		
	}

	/**
	 * Parses an assay info string (pipe separated) into a list of Assays.
	 * @return
	 */
	private List<Assay> parseAssays() {
		List<Assay> assays = new ArrayList<Assay>();
		String[] assayStrings = doc.getValues(StudyBrowseField.ASSAY_INFO.getName());
		for (String assayString :assayStrings) {
			StringTokenizer tokzr = new StringTokenizer(assayString, "|");
			int idx=0;
			String measurement="";
			String technology="";
			String count="";

			while (tokzr.hasMoreElements()) {
				String token = (String) tokzr.nextElement();
				switch (idx) {
				case 0 : measurement=token; break;
				case 1 : technology=token; break;
				case 2 : count=token; break;
				}
				idx++;
			}
			Assay assay = new Assay(measurement,technology,count);
			assays.add(assay);
		}
		return assays;
	}


	private String lineSep = System.getProperty("line.separator");
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Score: "+getScore()+lineSep);
		sb.append("Study: "+getAccStudy()+lineSep);
		sb.append("Inv: "+ getAccInvestigation()+lineSep);
		sb.append("Title:"+getTitle()+lineSep);
		sb.append("Organism:"+getOrganism()+lineSep);
		for (String factor : getFactors()) {
			sb.append (" -factor: "+factor+lineSep  );
		}
		for (LuceneSearchResult.Assay ass : getAssays()) {
			sb.append(" -assay: "+ass.getMeasurement()+" - "+ass.getTechnology()+" - "+ass.getCount()+lineSep);
		}
		return sb.toString();
	}
	public void setScore(float score) {
		this.score = score;
	}
	public float getScore() {
		return score;
	}
}
