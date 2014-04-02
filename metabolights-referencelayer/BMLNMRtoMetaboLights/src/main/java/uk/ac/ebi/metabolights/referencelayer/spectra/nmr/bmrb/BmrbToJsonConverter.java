package uk.ac.ebi.metabolights.referencelayer.spectra.nmr.bmrb;

import com.google.gson.Gson;
import org.apache.commons.io.FilenameUtils;
import uk.ac.ebi.metabolights.referencelayer.spectra.viewer.model.SimpleNMRSpectraData;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kalai on 09/01/2014.
 */
public class BmrbToJsonConverter {

    private File processedNMRfile = null;
    private File nmrFilesDirectory = null;
    private File outputJSONFile = null;
    private Gson gson = null;
    private boolean inputIsDir = false;
    private DecimalFormat decimalFormat = null;


    public BmrbToJsonConverter() {
        gson = new Gson();
        decimalFormat = new DecimalFormat("0.###");
    }

    public void convert() {
        if (inputIsDir) {
            iterate(getNmrFilesDirectory());
        } else {
            convert(getProcessedNMRfile(), getOutputJSONFile());
        }
    }

    public void iterate(File nmrFilesDirectory) {
        if (nmrFilesDirectory.isDirectory()) {
            for (File allEntries : nmrFilesDirectory.listFiles()) {
                if (allEntries.isDirectory()) {
                    for (File eachEntry : allEntries.listFiles()) {
                        if (eachEntry.isDirectory()) {
                            for (File allSpectra : eachEntry.listFiles()) {
                                if (FilenameUtils.isExtension(allSpectra.getName(), "txt") &
                                        FilenameUtils.getBaseName(eachEntry.getParent()).equals(FilenameUtils.getBaseName(allSpectra.getAbsolutePath()))) {
                                    String id = FilenameUtils.getBaseName(allSpectra.getAbsolutePath());
                                    File outputJSON = new File(allSpectra.getParent() + File.separator + id + ".JSON");
                                    //System.out.println("output: " + outputJSON.getAbsolutePath());
                                    convert(allSpectra, outputJSON);
                                }
                            }
                        }

                    }
                }
            }

        }
    }


    private void convert(File txt, File json) {
        SimpleNMRSpectraData nmrSpectraData = process(txt);
        write(nmrSpectraData, json);
    }

    private void write(SimpleNMRSpectraData data, File json) {
        try {
            FileWriter fw = new FileWriter(json);
            gson.toJson(data, fw);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SimpleNMRSpectraData process(File processedNMRfile) {
        List<Double> intensities = new ArrayList<Double>();
        List<Float> ppms = new ArrayList<Float>();
        SimpleNMRSpectraData nmrSpectraData = null;
        System.out.println("processing: " + processedNMRfile.getAbsolutePath());
        try {
            LineNumberReader reader = new LineNumberReader(new FileReader(processedNMRfile));

            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                ppms.add(Float.valueOf(values[1]));
                intensities.add(Double.valueOf(values[2]));
                // System.out.println("Logged: " + Float.valueOf(values[2]) + " " + "Formatted intensity: " + decimalFormat.format(Float.valueOf(values[2])));
            }
            Collections.reverse(intensities);
            Collections.sort(ppms);
            Double[] intensitiesArray = intensities.toArray(new Double[intensities.size()]);
            nmrSpectraData = new SimpleNMRSpectraData(intensitiesArray, ppms.get(ppms.size() - 1), ppms.get(0));
//            System.out.println("Max value: " + ppms.get(ppms.size() - 1));
//            System.out.println("Min value: " + ppms.get(0));
//            System.out.println("Intensities logged: " + intensities.size());


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return nmrSpectraData;
    }

    public File getProcessedNMRfile() {
        return processedNMRfile;
    }

    public void setProcessedNMRfile(File processedNMRfile) {
        this.processedNMRfile = processedNMRfile;
    }

    public File getNmrFilesDirectory() {
        return nmrFilesDirectory;
    }

    public void setNmrFilesDirectory(File nmrFilesDirectory) {
        this.nmrFilesDirectory = nmrFilesDirectory;
        inputIsDir = true;
    }

    public File getOutputJSONFile() {
        return outputJSONFile;
    }

    public void setOutputJSONFile(File outputJSONFile) {
        this.outputJSONFile = outputJSONFile;
    }
}
