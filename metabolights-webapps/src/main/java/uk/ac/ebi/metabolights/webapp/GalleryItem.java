package uk.ac.ebi.metabolights.webapp;

public class GalleryItem {

	public enum GalleryItemType{
		Study,
		Compound,
		Other
	}

	String title;
	String description;
	String url;
	String imgUrl;
	GalleryItemType type;
	
	public GalleryItem(String title, String description, String url, String imgUrl, GalleryItemType type) {
		this.title = title;
		this.description = description;
		this.url = url;
		this.imgUrl = imgUrl;
		this.type = type;
	}

	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @return the imgUrl
	 */
	public String getImgUrl() {
		return imgUrl;
	}
	/**
	 * @return the type
	 */
	public GalleryItemType getType() {
		return type;
	}
	
}
