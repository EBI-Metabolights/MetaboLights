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

        if(name.contains(":")){
            splitName = name.split(":");
            this.name = splitName[1];
            return this.name;
        } else {
            return name;
        }


    }

    public String getOntology(String ontology) {
        if(ontology.contains(":")){
            splitName = ontology.split(":");
            this.ontology = splitName[0];
            return this.ontology;
        } else {
            return ontology;
        }

    }
}
