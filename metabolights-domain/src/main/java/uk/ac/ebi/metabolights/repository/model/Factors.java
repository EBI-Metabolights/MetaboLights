package uk.ac.ebi.metabolights.repository.model;

/**
 * Created with IntelliJ IDEA.
 * User: tejasvi
 * Date: 17/09/13
 * Time: 10:37
 * To change this template use File | Settings | File Templates.
 */
public class Factors {
    private String factorKey;
    private String factorValue;

    public String getFactorValue() {
        return factorValue;
    }

    public void setFactorValue(String factorValue) {
        this.factorValue = factorValue;
    }

    public String getFactorKey() {
        return factorKey;
    }

    public void setFactorKey(String factorKey) {
        this.factorKey = factorKey;
    }
}
