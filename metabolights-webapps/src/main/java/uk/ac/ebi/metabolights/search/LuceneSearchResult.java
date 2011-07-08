package uk.ac.ebi.metabolights.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
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
	private HashMap<String,Set<String>> factors;
	private HashMap<String,Set<String>> properties;
	private List<Publication> publications;
	private List<String> technologies;
	private List<String> platforms;
	
	public LuceneSearchResult(Document doc, float score) {
		this.doc=doc;
		this.score=score;
		//Call version 13 this.assays=parseAssays();
		this.assays = parseAssays();
		
		//Field is called factors in the 1.3 version (not in the enum..yet)
		this.factors=parseKeyValue("factors");
		this.properties=parseKeyValue("characteristics");
		//No longer exist in 1.3 index version:this.technologies=getValues("assay_technology_name");
		
		this.publications = parsePublications();
		
		this.platforms=getValues("assay_platform");
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
	
	public HashMap<String,Set<String>> getProperties() {
		return properties;
	}
	
	public List<String> getTechnologies() {
		return technologies;
	}
	
	public List<String> getPlatforms() {
		return platforms;
	}
	public HashMap<String,Set<String>> getFactors() {
		return factors;
	}

	public List<Assay> getAssays() {
		return assays;
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
	
	public void setScore(float score) {
		this.score = score;
	}
	
	public float getScore() {
		return score;
	}	
	
	public List<Publication> getPublications() {
		return publications;
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
	
	/*
	 * Publication information for a study, for Lucene parsing
	 * @author kenneth
	 */
	public class Publication {
		
		private String title;
		private String authors;
		private String pubmedId;
		private String doi;
		
		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getAuthors() {
			return authors;
		}

		public void setAuthors(String authors) {
			this.authors = authors;
		}

		public String getPubmedId() {
			return pubmedId;
		}

		public void setPubmedId(String pubmedId) {
			this.pubmedId = pubmedId;
		}

		public String getDoi() {
			return doi;
		}

		public void setDoi(String doi) {
			this.doi = doi;
		}

		public Publication() {
		}
		
		public Publication(String title, String authors, String pubmedId, String doi) {
			this.title = title;
			this.authors = authors;
			this.pubmedId = pubmedId;
			this.doi = doi;
		}
	}
	
	private List<Publication> parsePublications() {

		List<Publication> pubList = new ArrayList<Publication>();
		String pubTitle = "", pubAuthors = "", pubmedId ="", doi="";
		int idx = 0;

		String publicationString = doc.get("publication");
		if (publicationString==null)
			return pubList;
		
		publicationString = publicationString.replaceAll("title:", "").replaceAll("authors:", "").replaceAll("pubmed:", "").replaceAll("doi:doi:", "doi:");
		// Lucene field publication contains "title:zxc:as:vxzvc|authors:lots, of, authors|pubmed:123123|doi:doi:123.1232/asdf"

		StringTokenizer tokzr = new StringTokenizer(publicationString, "|");

		while (tokzr.hasMoreElements()) {
			String token = (String) tokzr.nextElement();
			switch (idx) {
				case 0: pubTitle = token; break; 	
				case 1: pubAuthors = token; break; 
				case 2: pubmedId = token; break; 	
				case 3:	doi = token; break; 		
			}
			idx++;
		}
		
		Publication publication = new Publication(pubTitle, pubAuthors, pubmedId, doi);
		pubList.add(publication);

		return pubList;

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
	 * Parse ASSAY field based in the 1.3 version of the lucene index implementation in BII:
	 * Sample: assay(transcription profiling|DNA microarray|14):?xref(E-MAXD-4->AE:RAW):?xref(E-MAXD-4->AE:WEB)
	 * @return
	 */
	private List<Assay> parseAssays() {
		List<Assay> assays = new ArrayList<Assay>();
		String[] assayStrings = doc.getValues(StudyBrowseField.ASSAY_INFO.getName());
		for (String assayString :assayStrings) {
			StringTokenizer tokzr = new StringTokenizer(assayString, "()|");
			int idx=0;
			String measurement="";
			String technology="";
			String count="";

			while (tokzr.hasMoreElements()) {
				String token = (String) tokzr.nextElement();
				switch (idx) {
				case 1 : measurement=token; break;
				case 2 : technology=token; break;
				case 3 : count=token; break;
				}
				idx++;
			}
			Assay assay = new Assay(measurement,technology,count);
			assays.add(assay);
		}
		return assays;
	}
	private HashMap<String,Set<String>> parseKeyValue(String fieldName){

		HashMap<String,Set<String>> keyValue = new HashMap<String,Set<String>>();
		String[] keyValuesString = doc.getValues(fieldName);
		for (String keyValueString :keyValuesString) {
			StringTokenizer tokzr = new StringTokenizer(keyValueString, "[]");
			int idx=0;
			String key="";
			String value="";

			while (tokzr.hasMoreElements()) {
				String token = (String) tokzr.nextElement();
				switch (idx) {
				case 0 : key=token; break;
				case 1 : value=token; break;
				}
				idx++;
			}
			
			//If there is a key already
			if (keyValue.containsKey(key)){
				
				//Concatenate the value
				keyValue.get(key).add(value);
			}else{
				//New factor...
				Set<String> newSet = new HashSet<String>();
				newSet.add(value);
				keyValue.put(key, newSet);
			}
			
		}
		return keyValue;

		
	}
	
	private String lineSep = System.getProperty("line.separator");
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Score: "+getScore()+lineSep);
		sb.append("Study: "+getAccStudy()+lineSep);
		sb.append("Inv: "+ getAccInvestigation()+lineSep);
		sb.append("Title:"+getTitle()+lineSep);
		sb.append("Organism:"+getOrganism()+lineSep);
		
		for (Entry<String,Set<String>> property : getProperties().entrySet()) {
			sb.append (" -property: "+property.getKey() + ": ");
			for (String value : property.getValue()){
				sb.append(value + ",");
			}
			sb.append (lineSep);
		}
		
		for (Entry<String,Set<String>> factor : getFactors().entrySet()) {
			sb.append (" -factor: "+factor.getKey() + ": ");
			for (String value : factor.getValue()){
				sb.append(value + ",");
			}
			sb.append (lineSep);
		}
		for (LuceneSearchResult.Assay ass : getAssays()) {
			sb.append(" -assay: "+ass.getMeasurement()+" - "+ass.getTechnology()+" - "+ass.getCount()+lineSep);
		}
		return sb.toString();
	}

}
