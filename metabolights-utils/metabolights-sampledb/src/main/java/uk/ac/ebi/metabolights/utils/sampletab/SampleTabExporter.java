package uk.ac.ebi.metabolights.utils.sampletab;

import org.isatools.isacreator.model.Contact;
import org.isatools.isacreator.model.Investigation;
import org.isatools.isacreator.model.Publication;
import org.isatools.isacreator.model.Study;
import uk.ac.ebi.arrayexpress2.sampletab.datamodel.SampleData;
import uk.ac.ebi.arrayexpress2.sampletab.datamodel.msi.Organization;
import uk.ac.ebi.arrayexpress2.sampletab.datamodel.msi.Person;
import uk.ac.ebi.arrayexpress2.sampletab.parser.SampleTabParser;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kenneth
 * Date: 24/04/2013
 * Time: 10:07
 */
public class SampleTabExporter {

    private ISATabReader isaTabReader = new ISATabReader();
    private SampleTabParser parser = new SampleTabParser();
    private File sampletabFile = null;

    public void main (String[] args){

        if(commandLineValidation(args)){

            //export the SampleTab file
            exportSampleFile(args[0], args[1], args[2]);

        } else {

            System.out.println("Usage:");
            System.out.println("Parameter 1: The folder name of the configuration files for the study");
            System.out.println("Parameter 2: The folder name of the MTBLS study files");
            System.out.println("Parameter 3: The file name of the SampleTab file you want to export, this is recreated at runtime");

        }

    }

    /**
     * Returns the investigation from the study
     * @param configDirectory
     * @param isatabDirectory
     * @return ISAcreator Investigation
     */
    private Investigation getIsaInvestigation(String configDirectory, String isatabDirectory){
        Investigation investigation = new Investigation();

        Boolean isaFound = isaTabReader.validateISAtabFiles(configDirectory, isatabDirectory);

        if (isaFound) {
            investigation = isaTabReader.getInvestigation();
        }

        return investigation;
    }

    /**
     * Export a sampletab file based on your isatab config files and Investigation/Study data
     * @param filename
     * @param configDirectory
     * @param isatabDirectory
     * @return boolean, did it work?
     */
    public boolean exportSampleFile(String filename, String configDirectory, String isatabDirectory) {
        sampletabFile = new File(filename);
        SampleData sampleData = new SampleData();
        Investigation investigation = getIsaInvestigation(configDirectory, isatabDirectory);
        Boolean processingSuccess = false;

        //First get the study, can only be on per MetaboLights rules
        Study study = isaTabReader.getStudyFromInvestigation();

        //Start adding data to the file

        //Add Submission data
        if (!setSubmissionData(sampleData, study))
            return false;

        //Add Person and Organisation data
        if (!setPersonAndOrganisationData(sampleData, isaTabReader.getContcatsForStudy(study.getStudyId())))
            return false;

        //Add Publication data
        if (!setPublicationData(sampleData, study))
            return false;

        return true;
    }

    private Date parseDate(String date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); //ISA format for the date
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

       return null;
    }

    /**
     * This adds the SUBMISSION part of the sampletab definition
     * @param sampleData
     * @param study
     * @return true if SampleData.MSI has data
     */
    private boolean setSubmissionData(SampleData sampleData, Study study){

        if (study == null)
            return false;

        if (study.getStudyTitle() != null)
            sampleData.msi.submissionTitle = study.getStudyTitle();

        if (study.getStudyDesc() != null)
            sampleData.msi.submissionDescription = study.getStudyDesc();

        if (study.getStudyId() != null)
            sampleData.msi.submissionIdentifier = study.getStudyId();

        //I have skipped the optional field "Submission Version" as this comes from the "sample tab jar" we use

        if (study.getDateOfSubmission() != null)
            sampleData.msi.submissionUpdateDate = parseDate(study.getDateOfSubmission());

        if (study.getPublicReleaseDate() != null)
            sampleData.msi.submissionReleaseDate = parseDate(study.getPublicReleaseDate());

        if (sampleData.msi == null)
            return false;

        return true;

    }

    /**
     * This adds the PERSON and ORGANIZATION part of the sampleTab definition
     * @param sampleData
     * @param contacts
     * @return true if SampleData.MSI.person and SampleData.MSI.organization has data
     */
    private boolean setPersonAndOrganisationData(SampleData sampleData, List<Contact> contacts){
        if (sampleData == null || contacts.isEmpty())
            return false; //Must have a valid sampleData object and some contacts to add to it.

        Iterator iterator = contacts.iterator();

        while (iterator.hasNext()){
            Contact contact = (Contact) iterator.next();

            Person person = new Person(contact.getLastName(),contact.getMidInitial(), contact.getFirstName(),contact.getEmail(),contact.getRole());
            if (person != null)
                sampleData.msi.persons.add(person);

            Organization organization = new Organization(contact.getAffiliation(), contact.getAddress(), null, contact.getEmail(),contact.getRole());  //URI missing

            if (organization != null)
                sampleData.msi.organizations.add(organization);
        }

        if (sampleData.msi.persons == null || sampleData.msi.organizations == null)
            return false;

        return true;

    }

    /**
     * This adds the PUBLICATION part of the sampleTab definition
     * @param sampleData
     * @param study
     * @return true if we have managed to populate the publication list
     */
    private boolean setPublicationData(SampleData sampleData, Study study){
        if (sampleData == null || study.getPublications().isEmpty())
            return false; //Must have a valid sampleData object and some contacts to add to it.
        Iterator iterator = study.getPublications().iterator();
        while (iterator.hasNext()){
            Publication isaPublication = (Publication) iterator.next();

            uk.ac.ebi.arrayexpress2.sampletab.datamodel.msi.Publication publication =
                    new uk.ac.ebi.arrayexpress2.sampletab.datamodel.msi.Publication(isaPublication.getPubmedId(), isaPublication.getPublicationDOI());
            if (publication != null)
                sampleData.msi.publications.add(publication);

        }

        if (sampleData.msi.publications == null)
            return false;

        return true;

    }


    private static boolean commandLineValidation(String args[]){

        // If there isn't any parameter
        if (args == null || args.length== 0){
            return false;
        }

        // Check first parameter is the config folder
        File first = new File(args[0]);
        if (!first.exists()){
            System.out.println("1st parameter must be the configuration folder" + args[0]);
            return false;
        }

        // Check first parameter is the config file file
        File secound = new File(args[1]);
        if (!secound.exists()){
            System.out.println("2nd parameter must be the ISAtab (MTBLS Study) folder" + args[1]);
            return false;
        }

        if (args[2] == null){
            System.out.println("You must also give us the filename of the SampleTab file you want to export");
            return false;
        }

        return true;

    }


}
