/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 9/6/13 2:24 PM
 * Modified by:   conesa
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.utils.isatab;

import org.apache.commons.io.filefilter.RegexFileFilter;

import javax.naming.ConfigurationException;
import java.io.*;

public class IsaTabUtils {

    private final static String DEFAULT_INVESTIGATION_PATTERN = "i_.*\\.txt";
    private static final String FIELD_NAME_WITH_CONFIGURATION = "Comment [Created With Configuration]";

    public static String getInvestigationFileName (String folder) throws FileNotFoundException {
        return getInvestigationFileName(folder, DEFAULT_INVESTIGATION_PATTERN);
    }

    public static String getInvestigationFileName(String folder, String investigationPattern) throws FileNotFoundException {

        File isaTabFolder = new File (folder);
        return getInvestigationFileName(isaTabFolder, investigationPattern);

    }

    public static String getInvestigationFileName (File folder) throws FileNotFoundException {
        return getInvestigationFileName(folder, DEFAULT_INVESTIGATION_PATTERN);
    }

    public static String getInvestigationFileName(File isaTabFolder, String investigationPattern) throws FileNotFoundException {

        File inv = getInvestigationFile(isaTabFolder, investigationPattern);
        return inv.getAbsolutePath();
    }

    public static File getInvestigationFile (File folder) throws FileNotFoundException {
        return getInvestigationFile(folder, DEFAULT_INVESTIGATION_PATTERN);
    }

    public static File getInvestigationFile(String folder, String investigationPattern) throws FileNotFoundException {

        File isaTabFolder = new File (folder);
        File inv =  getInvestigationFile(isaTabFolder, investigationPattern);
        return inv;

    }

    public static File getInvestigationFile(File isaTabFolder, String investigationPattern) throws FileNotFoundException {

        //Search for the investigation file
        File[] fileList;

        FileFilter filter = new RegexFileFilter(investigationPattern);


        //Get the file list filtered
        fileList = isaTabFolder.listFiles(filter);

        //If there is not an investigation file...
        if (fileList.length ==0 || fileList == null) {
            throw new FileNotFoundException("File with Ids (" + investigationPattern + ") not found");
        }

        //There must be only one, so take the first
        return fileList[0];
    }


    public static File getConfigurationFolderFromStudy (String isaTabFolder, String rootConfigFolder) throws IOException, ConfigurationException {

        return getConfigurationFolderFromStudy(new File(isaTabFolder), new File(rootConfigFolder));
    }

    /**
     * Gets the config files from the current study archive folder
     * @return Name from the line "[Last Opened With Configuration]" in the i_Investigation.txt file, but only if we have the same config, otherwise return the string "default"
     * @throws IOException
     */
    public static File getConfigurationFolderFromStudy(File isaTabFolder, File  rootConfigFolder) throws IOException, ConfigurationException {

        File investigationFile = getInvestigationFile(isaTabFolder);

        BufferedReader br = new BufferedReader(new FileReader(investigationFile));
        String configFileFolder = "default";    //Just in case we Could not find the config files used in the investigation file

        try {
            String line = br.readLine();
            String lastPart = null;

            while (line != null) {
                if (line.contains(FIELD_NAME_WITH_CONFIGURATION)){
                    String[] lineParts = line.split("\""); //The config file name is always separated with "
                    configFileFolder = lineParts[1];   //Should be the name of the *submitters* config file directory

                    //Only use the last part of the path, split on both / and \
                    if (configFileFolder.contains("/")){
                        lastPart = configFileFolder.substring(configFileFolder.lastIndexOf("/"));
                        lastPart = lastPart.replace("/","");
                    }

                    if (configFileFolder.contains("\\")){
                        lastPart = configFileFolder.substring(configFileFolder.lastIndexOf("\\"));
                        lastPart = lastPart.replace("\\","");
                    }

                    if (lastPart != null)
                        configFileFolder = lastPart;

                    break; //Nothing more to do in this loop

                }
                line = br.readLine();
            }
        } finally {
            br.close();
        }

        //Have to try to fix Windows path problems, example: C:softwareISAcreatorMetaboLightsConfigurationsMetaboLightsConfig20120129
        if (configFileFolder.contains(":")){           //Cannot just check for C:, what about D: ??
            //So some Windows paths have been used
            String[] winPath = configFileFolder.split("Configurations");    //Not elegant, but normally configs would be under the ISAcreator folder "/Configurations/"
            configFileFolder = winPath[1];
        }

        // Build the final config folder
        File configFolder = new File (rootConfigFolder.getAbsoluteFile() +  File.separator + configFileFolder);

        if (configFolder.exists()) {
            return  configFolder;
        } else {
            throw new ConfigurationException("Isatab configuration folder not found for the study (" + isaTabFolder.getName() + "). Configuration found: " + configFileFolder);
        }

    }

}
