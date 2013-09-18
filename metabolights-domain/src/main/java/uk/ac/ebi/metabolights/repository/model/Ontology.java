package uk.ac.ebi.metabolights.repository.model;

/**
 * Created with IntelliJ IDEA.
 * User: tejasvi
 * Date: 18/09/13
 * Time: 10:06
 * To change this template use File | Settings | File Templates.
 */
public class Ontology {

    private String splitName[];

    private String name;
    private String ontology;


    public String getName(String name) {
        splitName = name.split(":");
        this.name = splitName[1];

        return this.name;
    }

    public String getOntology() {
        splitName = name.split(":");
        this.ontology = splitName[0];

        return this.ontology;
    }
}
