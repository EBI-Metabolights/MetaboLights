package uk.ac.ebi.metabolights.repository.utils.validation;


import java.io.*;
import java.util.*;

/**
 * Created by kalai on 16/10/15.
 */
public class StrangeCharactersDetector {
    public static void main(String[] args) {
       // if (args.length != 2) {
       //     System.out.println("Please input a studies dir (arg0) and a file (arg1) to write the detected characters");
       //     System.exit(0);
       // }
        //String dir = args[0];
        //String outFile = args[1];
        String dir = "/Users/kenneth/kkk/";
        String outFile = "/Users/kenneth/kkk/characters.txt";
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
                strangeCharacters.addAll(getNonUnicodeCharacters(line));
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

    public HashSet<String> getNonUnicodeCharacters(String s) {
        final HashSet<String> result = new HashSet<String>();
        for (int i = 0, n = s.length(); i < n; i++) {
            String character = s.substring(i, i + 1);
            int other_Symbol = Character.OTHER_SYMBOL;
            int this_ = Character.getType(character.charAt(0));

            if (other_Symbol == this_ || Arrays.equals(character.toCharArray(), Character.toChars(0x003F))) {
                result.add(character);
            }

        }
        return result;
    }


}
