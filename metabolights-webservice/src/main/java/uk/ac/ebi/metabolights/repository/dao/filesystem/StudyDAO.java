package uk.ac.ebi.metabolights.repository.dao.filesystem;

import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.utils.IsaTab2MetaboLightsConverter;

/**
 * User: conesa
 * Date: 28/08/2013
 * Time: 14:41
 */
public class StudyDAO {

    private IsaTabInvestigationDAO isaTabInvestigationDAO;

    public StudyDAO(String isaTabConfigurationFolder){
        this.isaTabInvestigationDAO = new IsaTabInvestigationDAO(isaTabConfigurationFolder);

    }

    public Study getInvestigation(String investigationFolder){

        org.isatools.isacreator.model.Investigation isaInvestigation = isaTabInvestigationDAO.getInvestigation(investigationFolder);

        Study study = IsaTab2MetaboLightsConverter.convert(isaInvestigation);

        return study;

    }
}
