package uk.ac.ebi.metabolights.referencelayer.spectra.ms.massbank;

import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by kalai on 20/03/2014.
 */
public class MetaInfoExtractor {

    public enum Fields {
        ACCESSION, AUTHORS,
        AC$INSTRUMENT, AC$INSTRUMENT_TYPE, AC$MASS_SPECTROMETRY, MS$FOCUSED_ION,
        MS$DATA_PROCESSING, AC$CHROMATOGRAPHY, CH$EXACT_MASS, PK$NUM_PEAK
    }

    BufferedWriter writer = null;
    Map<String, String> fieldValues = null;

    public MetaInfoExtractor() {
        fieldValues = new LinkedHashMap<String, String>();
        for (Fields f : Fields.values()) {
            fieldValues.put(f.toString(), "");
        }
    }

    public void iterate(File msFilesDirectory, File out) {
        try {
            writer = new BufferedWriter(new FileWriter(out));
            for (File allEntries : msFilesDirectory.listFiles()) {
                if (allEntries.isDirectory()) {
                    for (File spectraFile : allEntries.listFiles()) {
                        if (FilenameUtils.isExtension(spectraFile.getName(), "txt")) {
                            System.out.println(spectraFile.getName());
                            extract(spectraFile);
                        }

                    }
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void extract(File file, File out) {
        try {
            writer = new BufferedWriter(new FileWriter(out));
            process(file);
            write();
            reset();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void extract(File file) {
        process(file);
        write();
        reset();
    }

    private void process(File record) {

        try {
            LineNumberReader reader = new LineNumberReader(new FileReader(record));
            String line = "";
            while ((line = reader.readLine()) != null) {
                if (!line.contains("PK$PEAK:")) {
                    check(line);
                } else {
                    break;
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void check(String line) {
        String[] values = line.split(": ");
        if (values.length == 2) {
            if (fieldValues.containsKey(values[0])) {
                String value = fieldValues.get(values[0]);
                value += values[1] + ", ";
                fieldValues.put(values[0], value);
            }
        }
    }

    private void write() {
        // System.out.println("----------");
        String finalValue = "";
        for (Map.Entry e : fieldValues.entrySet()) {
            String value = (String) e.getValue();
            //System.out.println(e.getKey() + "----------" + value);
            finalValue += modify(value, ',') + ":";
        }
        finalValue = modify(finalValue, ':');
        //  System.out.println(finalValue);
        try {
            writer.write(finalValue + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String modify(String value, Character pattern) {
        value = value.trim();
        if (value.length() > 0 && value.charAt(value.length() - 1) == pattern) {
            value = value.substring(0, value.length() - 1);
        }
        return value;
    }

    private void reset() {
        fieldValues = new LinkedHashMap<String, String>();
        for (Fields f : Fields.values()) {
            fieldValues.put(f.toString(), "");
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Please input MS-file and a output file");
            System.exit(0);
        }
        if (args.length == 2) {
            File file = new File(args[0]);
            File out = new File(args[1]);
            MetaInfoExtractor extractor = new MetaInfoExtractor();
            extractor.iterate(file, out);

        }
    }
}
