package uk.ac.ebi.metabolights.referencelayer.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * User: conesa
 * Date: 28/07/15
 * Time: 12:41
 */
public class Citation {

	private static final Logger logger = LoggerFactory.getLogger(Citation.class);

	private String title;
	private String id;
	private String abstracT;
	private ArrayList<String> authors = new ArrayList<>();
	private String authorsText;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAbstracT() {
		return abstracT;
	}

	public void setAbstracT(String abstracT) {
		this.abstracT = abstracT;
	}

	public ArrayList<String> getAuthors() {
		return authors;
	}

	public void setAuthors(ArrayList<String> authors) {
		this.authors = authors;
	}

	public void setAuthorsText(String authorsText) {
		this.authorsText = authorsText;
	}

	public String getAuthorsText() {
		return authorsText;
	}
}