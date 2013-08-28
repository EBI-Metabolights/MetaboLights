package uk.ac.ebi.metabolights.repository.utils;

import org.isatools.isacreator.model.StudyDesign;
import uk.ac.ebi.metabolights.repository.model.Contact;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.StudyDesignDescriptors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * User: conesa
 * Date: 28/08/2013
 * Time: 11:34
 */
public class IsaTab2MetaboLightsConverter {

    /*
    From isaTab specifications:
    "Dates
    Dates should be supplied in the ISO 8601 format “YYYY-MM-DD”."
    */
    private static final String ISA_TAB_DATE_FORMAT = "yyyy-MM-dd";
    private static SimpleDateFormat isaTabDateFormat = new SimpleDateFormat(ISA_TAB_DATE_FORMAT);

    public static Study convert( org.isatools.isacreator.model.Investigation source){


        // Convert the study...
        Study metStudy = isaTabInvestigation2MetaboLightsStudy(source);

        // Convert the authors...


        return metStudy;


    }

    private static Study isaTabInvestigation2MetaboLightsStudy(org.isatools.isacreator.model.Investigation source){

        // Instantiate new MetaboLights investigation object
        Study metStudy = new Study();


        // Get the first and unique study
        org.isatools.isacreator.model.Study isaStudy = source.getStudies().values().iterator().next();

        // Populate direct study members
        metStudy.setStudyIdentifier(isaStudy.getStudyId());
        metStudy.setTitle(isaStudy.getStudyTitle());
        metStudy.setDescription(isaStudy.getStudyDesc());
        metStudy.setStudyPublicReleaseDate(isaTabDate2Date(isaStudy.getPublicReleaseDate()));
        metStudy.setStudySubmissionDate(isaTabDate2Date(isaStudy.getDateOfSubmission()));


        // Now collections
        // Contacts
        metStudy.setContacts(isaTabContacts2MetaboLightsContacts(isaStudy));

        // Study design descriptors
        metStudy.setDescriptors(isaTabStudyDesign2MetaboLightsStudiesDesignDescriptors(isaStudy));


        return metStudy;
    }


    private static Collection<Contact> isaTabContacts2MetaboLightsContacts(org.isatools.isacreator.model.Study isaStudy){

        List<org.isatools.isacreator.model.Contact> isaContacts = isaStudy.getContacts();

        List<Contact> contacts = new LinkedList<Contact>();

        for (org.isatools.isacreator.model.Contact isaContact: isaContacts){
            Contact contact = new Contact();

            contact.setAddress(isaContact.getAddress());
            contact.setAffiliation((isaContact.getAffiliation()));
            contact.setEmail(isaContact.getEmail());
            contact.setFax(isaContact.getFax());
            contact.setFirstName(isaContact.getFirstName());
            contact.setLastName(isaContact.getLastName());
            contact.setMidInitial(isaContact.getMidInitial());
            contact.setPhone(isaContact.getPhone());
            contact.setRole(isaContact.getRole());

            contacts.add(contact);
        }


        return contacts;

    }

    private static Collection<StudyDesignDescriptors> isaTabStudyDesign2MetaboLightsStudiesDesignDescriptors(org.isatools.isacreator.model.Study isaStudy){

        List<StudyDesign> isaStudyDesigns = isaStudy.getStudyDesigns();

        List<StudyDesignDescriptors> descriptors = new LinkedList<StudyDesignDescriptors>();

        for (StudyDesign isaStudyDesign: isaStudyDesigns){


            StudyDesignDescriptors descriptor = new StudyDesignDescriptors();

            descriptor.setDescription(isaStudyDesign.getIdentifier());

            descriptors.add(descriptor);
        }


        return descriptors;

    }





    public static Date isaTabDate2Date (String isaTabDate){

        try {
            return isaTabDateFormat.parse(isaTabDate);
        } catch (ParseException e) {
            return null;
        }
    }
}
