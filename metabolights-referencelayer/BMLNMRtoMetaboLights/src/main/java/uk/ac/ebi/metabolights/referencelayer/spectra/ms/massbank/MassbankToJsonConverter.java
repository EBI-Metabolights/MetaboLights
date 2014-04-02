package uk.ac.ebi.metabolights.referencelayer.spectra.ms.massbank;

import com.google.gson.Gson;
import org.apache.commons.io.FilenameUtils;
import uk.ac.ebi.metabolights.referencelayer.spectra.viewer.model.JsonSpectra;
import uk.ac.ebi.metabolights.referencelayer.spectra.viewer.model.JsonSpectraPeak;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by kalai on 09/01/2014.
 */
public class MassbankToJsonConverter {

    private File processedNMRfile = null;
    private File msFilesDirectory = null;
    private File outputJSONFile = null;
    private Gson gson = null;
    private boolean inputIsDir = false;
    private DecimalFormat decimalFormat = null;
    private HashSet<String> massBankFields = null;
    private Map<String, HashSet<String>> map = null;


    public MassbankToJsonConverter() {
        gson = new Gson();
        decimalFormat = new DecimalFormat("0.###");
        massBankFields = new HashSet<String>();
        map = new HashMap<String, HashSet<String>>();
    }

    public void convert() {
        if (inputIsDir) {
            iterate(getMSFilesDirectory());
            printFields();
        } else {
            convert(getMSfile());
        }
    }

    public void iterate(File msFilesDirectory) {
        for (File allEntries : msFilesDirectory.listFiles()) {
            if (allEntries.isDirectory()) {
                for (File spectraFile : allEntries.listFiles()) {
                    if (FilenameUtils.isExtension(spectraFile.getName(), "txt")) {
                        convert(spectraFile);
                    }

                }
            }
        }
    }


    private void convert(File spectraFile) {
        String id = FilenameUtils.getBaseName(spectraFile.getAbsolutePath());
        File outputJSON = new File(spectraFile.getParent() + File.separator + id + ".json");

        JsonSpectra msSpectra = process(spectraFile);
        if (msSpectra != null) {
            write(msSpectra, outputJSON);
        }
    }

    private JsonSpectra process(File record) {
        JsonSpectra msSpectra = new JsonSpectra();
        String id = FilenameUtils.getBaseName(record.getAbsolutePath());
        // System.out.println("processing: " + record.getAbsolutePath());
        try {
            LineNumberReader reader = new LineNumberReader(new FileReader(record));

            String line = "";

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(":");
                if (values.length > 0) {
                    addInfo(line, values[0]);
                    if (values[0].contains("$")) {
                        massBankFields.add(values[0]);
                    }
                    if (values[0].equals("PK$PEAK")) {
                        msSpectra = getJSONSpectra(reader, id);
                        break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return msSpectra;
    }

    private void addInfo(String line, String value) {
        if (value.equals("AC$INSTRUMENT") ||
                value.equals("AC$INSTRUMENT_TYPE") ||
                value.equals("AC$MASS_SPECTROMETRY") ||
                value.equals("MS$FOCUSED_ION")) {
            if (map.containsKey(value)) {
                HashSet<String> list = map.get(value);
                list.add(line);
            } else {
                HashSet<String> values = new HashSet<String>();
                values.add(line);
                map.put(value, values);
            }

        }

    }

    private JsonSpectra getJSONSpectra(LineNumberReader reader, String id) {
        String line = "";
        JsonSpectra msSpectra = new JsonSpectra();
        ArrayList<JsonSpectraPeak> peaks = new ArrayList<JsonSpectraPeak>();
        List<Double> peakValues = new ArrayList<Double>();
        try {
            while ((line = reader.readLine()) != null) {
                if (!line.contains("//")) {
                    String[] values = line.split(" ");
//                    //System.out.println(line + "$" + values.length);
//                    for(String v : values){
//                        System.out.println(v+"$");
//                    }
                    if (values.length == 5) {
                        if (!values[2].isEmpty() && !values[4].isEmpty()) {
                            peakValues.add(Double.parseDouble(values[2]));
                            peaks.add(new JsonSpectraPeak(Double.parseDouble(values[2]), Double.parseDouble(values[4])));
                        }
                    }
                    if (values.length == 4) {
                        for (String v : values) {
                            System.out.println(v + "$");
                        }

                        if (!values[1].isEmpty() && !values[3].isEmpty()) {
                            peakValues.add(Double.parseDouble(values[1]));
                            peaks.add(new JsonSpectraPeak(Double.parseDouble(values[1]), Double.parseDouble(values[3])));
                        }
                    }
                }
            }
            Collections.sort(peakValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (peaks.size() > 0) {
            msSpectra.setMzStart(peakValues.get(0));
            msSpectra.setMzStop(peakValues.get(peakValues.size() - 1));
            msSpectra.setPeaks(peaks);
            msSpectra.setSpectrumId(id);
        } else {
            System.out.println("err " + id);
            return null;
        }
        return msSpectra;
    }

    private void write(JsonSpectra data, File json) {
        try {
            FileWriter fw = new FileWriter(json);
            gson.toJson(data, fw);
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printFields() {
        // System.out.println("--------------");
        for (String field : massBankFields) {
            System.out.println(field);
        }
        System.out.println("---");
        for (String field : map.keySet()) {
            HashSet<String> vals = map.get(field);
            System.out.println(field);
            for (String s : vals) {
                System.out.println("\t" + s);
            }
        }
    }


    public File getMSfile() {
        return processedNMRfile;
    }

    public void setMSfile(File processedNMRfile) {
        this.processedNMRfile = processedNMRfile;
    }

    public File getMSFilesDirectory() {
        return msFilesDirectory;
    }

    public void setMSFilesDirectory(File msFilesDirectory) {
        this.msFilesDirectory = msFilesDirectory;
        this.inputIsDir = true;
    }

    public File getOutputJSONFile() {
        return outputJSONFile;
    }

    public void setOutputJSONFile(File outputJSONFile) {
        this.outputJSONFile = outputJSONFile;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please input MS-files dir");
            System.exit(0);
        }
        if (args.length == 1) {
            MassbankToJsonConverter massbankToJsonConverter = new MassbankToJsonConverter();
            File dir = new File(args[0]);
            if (dir.isDirectory()) {
                massbankToJsonConverter.setMSFilesDirectory(dir);
                massbankToJsonConverter.convert();
            } else {
                massbankToJsonConverter.setMSfile(dir);
                massbankToJsonConverter.convert();
            }


        }
    }
}
