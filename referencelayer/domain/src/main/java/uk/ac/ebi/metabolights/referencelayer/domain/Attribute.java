package uk.ac.ebi.metabolights.referencelayer.domain;

import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author conesa
 *
 * Species reference table
 */
public class Attribute {

	private long id;
    private String value;
    private AttributeDefinition attributeDefinition;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public AttributeDefinition getAttributeDefinition() {
        return attributeDefinition;
    }

    public void setAttributeDefinition(AttributeDefinition attributeDefinition) {
        this.attributeDefinition = attributeDefinition;
    }

    @Override
    public boolean equals(Object obj){

        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof Attribute))
            return false;

        Attribute attribute = (Attribute)obj;

        return new EqualsBuilder().
                // if deriving: appendSuper(super.equals(obj)).
                append(value, attribute.value).
                append(attributeDefinition, attribute.attributeDefinition).
                isEquals();

    }
}