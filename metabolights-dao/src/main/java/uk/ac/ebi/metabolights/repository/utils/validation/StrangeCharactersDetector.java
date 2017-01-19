package uk.ac.ebi.metabolights.repository.utils.validation;


import java.io.*;
import java.util.*;

/**
 * Created by kalai on 16/10/15.
 */
public class StrangeCharactersDetector {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Please input a studies dir (arg0) and a file (arg1) to write the detected characters");
            System.exit(0);
        }
        String dir = args[0];
        String outFile = args[1];
        new StrangeCharactersDetector().processStudiesFolder(dir, outFile);

    }

    public void processStudiesFolder(String dir, String outFile) {
        Map<String, HashSet<String>> codePoint_study_map = new HashMap<String, HashSet<String>>();

        File studyDirectory = new File(dir);
        File[] directoryListing = studyDirectory.listFiles();
        if (directoryListing != null) {
            for (File study : directoryListing) {
                if (study.isDirectory()) {
                    File[] studyFiles = study.listFiles();
                    for (File file : studyFiles) {
                        if (file.getName().equals("i_Investigation.txt")) {
                            addto(codePoint_study_map, detectCharacters(file), study.getName());
                        }

                    }

                } else {
                    if (study.getName().equals("i_Investigation.txt")) {
                        addto(codePoint_study_map, detectCharacters(study), study.getName());
                    }
                }
            }
        }
        print(outFile, codePoint_study_map);
    }

    public HashSet<String> detectCharacters(File investigationFile) {
        String line = "";
        HashSet<String> strangeCharacters = new HashSet<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(investigationFile));
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    List<String> nonUnicodeCharacters = Utilities.getUnAcceptableCharacters(word);
                    if (!nonUnicodeCharacters.isEmpty()) {
                        for (String s : nonUnicodeCharacters) {
                            System.out.println("string -- " + s);
                        }
                        System.out.println(word);
                    }
                    strangeCharacters.addAll(nonUnicodeCharacters);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strangeCharacters;

    }

    public void addto(Map<String, HashSet<String>> map, HashSet<String> strangeChars, String studyDir) {
        for (String s : strangeChars) {
            if (!map.containsKey(s)) {
                HashSet<String> studyValues = new HashSet<>();
                studyValues.add(studyDir);
                map.put(s, studyValues);
            } else {
                HashSet<String> studyValues = map.get(s);
                studyValues.add(studyDir);
            }
        }
    }

    public void print(String outFile, Map<String, HashSet<String>> codePoint_study_map) {

        PrintWriter writer = null;
        try {

            writer = new PrintWriter(outFile, "UTF-8");
            for (Map.Entry<String, HashSet<String>> entry : codePoint_study_map.entrySet()) {
                String toWrite = entry.getKey() + " - " + entry.getKey().codePointAt(0) + " - " + studiesString(entry.getValue());
                System.out.println(toWrite);
                writer.println(toWrite);
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    public String studiesString(HashSet<String> studies) {
        String studiesString = "";
        for (String s : studies) {
            studiesString += s + ";";
        }
        return studiesString;
    }
}
