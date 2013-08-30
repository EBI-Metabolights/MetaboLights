package uk.ac.ebi.metabolights.repository.model;

/**
 * User: conesa
 * Date: 29/08/2013
 * Time: 14:21
 */
public class Protocol {
    private String name;
    private String Description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
