package uk.ac.ebi.metabolights.referencelayer.domain;

import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author conesa
 *
 * Attribute definition for generic Attributes (it doesn't hod any values, just name and description)
 */
public class AttributeDefinition implements Comparable<AttributeDefinition>{

	private long id;
	private String name;
    private String description;
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj){

        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof AttributeDefinition))
            return false;

        AttributeDefinition ad1 = (AttributeDefinition)obj;

        return new EqualsBuilder().
                // if deriving: appendSuper(super.equals(obj)).
                        append(this.name, ad1.name).
                isEquals();

    }

    @Override
    public int compareTo(AttributeDefinition attributeDefinition) {
        return name.compareTo(attributeDefinition.name);
    }
}
