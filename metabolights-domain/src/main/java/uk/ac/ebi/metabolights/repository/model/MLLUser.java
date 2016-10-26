package uk.ac.ebi.metabolights.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by venkata on 11/10/2016.
 */
public class MLLUser {

    public MLLUser() {}

    public MLLUser(User mlUser){

        email = mlUser.getEmail();

        userName = mlUser.getUserName();

        joinDate = mlUser.getJoinDate();

        firstName = mlUser.getFirstName();

        lastName = mlUser.getLastName();

        address = mlUser.getAddress();

        affiliation = mlUser.getAffiliation();

        affiliationUrl = mlUser.getAffiliationUrl();

        role = mlUser.getRole();

        apiToken = mlUser.getApiToken();

        orcid = mlUser.getOrcid();

    }

    private String email;

    private String userName;

    private Date joinDate;

    private String firstName;

    private String lastName;

    private String address;

    private String affiliation;

    private String affiliationUrl;

    private AppRole role;

    @JsonIgnore
    private String apiToken;

    private String orcid;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getAffiliationUrl() {
        return affiliationUrl;
    }

    public void setAffiliationUrl(String affiliationUrl) {
        this.affiliationUrl = affiliationUrl;
    }

    public AppRole getRole() {
        return role;
    }

    public void setRole(AppRole role) {
        this.role = role;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getOrcid() {
        return orcid;
    }

    public void setOrcid(String orcid) {
        this.orcid = orcid;
    }

    @JsonIgnore
    public String getAsJSON() {

        ObjectMapper mapper = new ObjectMapper();

        try {

            return mapper.writeValueAsString(this) ;

        } catch (JsonProcessingException e) {

            e.printStackTrace();

        }

        return null;
    }

}
