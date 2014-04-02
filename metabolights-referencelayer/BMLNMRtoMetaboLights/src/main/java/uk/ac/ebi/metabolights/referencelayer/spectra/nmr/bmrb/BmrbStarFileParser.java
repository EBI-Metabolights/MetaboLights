package uk.ac.ebi.metabolights.referencelayer.spectra.nmr.bmrb;

import EDU.bmrb.starlibj.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by kalai on 26/02/2014.
 */
public class BmrbStarFileParser {
    StarParser aParser;
    StarFileNode aStarTree;
    VectorCheckType searchResult;
    //StarUnparser myUnparser = new StarUnparser(System.out);
    BufferedWriter fileWriter = null;
    String pH = "";
    String organization = "";
    String data_authors = "";
    String bmseID = "";
    String temperature = "";
    String nmr_spectromter = "";
    String solute_conc = "";
    String solvent = "";
    String cytocide = "";
    String buffer = "";
    String reference = "";

    public enum SolventClass {SOLVENT, REFERENCE, BUFFER, CYTOCIDE, SOLUTE}


    public void parseFile(String fileName, String outFile) {
        try {
            fileWriter = new BufferedWriter(new FileWriter(new File(outFile)));
            extractBlockFrom(fileName);
            process();
            writeInfo();
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    public void parseFile(String fileName) {
        extractBlockFrom(fileName);
        process();
        printInfo();

    }

    private void extractBlockFrom(String inFile) {
        try {
            aParser = new StarParser(
                    new java.io.FileInputStream(inFile));
            aParser.StarFileNodeParse(aParser);
            aStarTree = (StarFileNode) aParser.popResult();
        } catch (Exception e) {

        }
    }

    private void process() {
        extractpH();
        extractBrukerInfo();
        extractSampleInfo();
        extractOrganizationInfo();
        extractAuthorInfo();
    }

    private void writeInfo() {
        try {
            fileWriter.write(bmseID + ":" + organization + ":" + data_authors +
                    ":" + nmr_spectromter + ":" + solute_conc + ":" + pH + ":" + temperature + ":" + solvent + ":" + buffer + ":"
                    + cytocide + ":" + reference + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printInfo() {
        System.out.println(bmseID + ":" + organization + ":" + data_authors +
                ":" + nmr_spectromter + ":" + solute_conc + ":" + pH + ":" + temperature + ":" + solvent + ":" + buffer + ":"
                + cytocide + ":" + reference);
    }


    private void extractOrganizationInfo() {
        try {
            searchResult = aStarTree.searchForTypeByName(
                    Class.forName(StarValidity.pkgName() + ".DataLoopNode"),
                    "_Entry_src.Organization_full_name");
            if (searchResult.size() == 0) return;
            DataLoopNode dataLoopNode = (DataLoopNode) searchResult.elementAt(0);

            DataLoopNameListNode dataLoopNameListNode = dataLoopNode.getNames();
            LoopTableNode table = dataLoopNode.getVals();

            for (int i = 0; i < dataLoopNameListNode.size(); i++) {
                LoopNameListNode namelistnode = dataLoopNameListNode.elementAt(i);
                for (int j = 0; j < namelistnode.size(); j++) {
                    DataNameNode nameNode = namelistnode.elementAt(j);
                    if (nameNode.getValue().equals("_Entry_src.Organization_full_name")) {
                        this.organization = table.elementAt(0).elementAt(j).getValue();
                    }
                    if (nameNode.getValue().equals("_Entry_src.Entry_ID")) {
                        this.bmseID = table.elementAt(0).elementAt(j).getValue();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void extractpH()

    {
        Integer variableType = null;
        Integer variableValue = null;
        Integer variable_units = null;

        try {

            searchResult = aStarTree.searchForTypeByName(
                    Class.forName(StarValidity.pkgName() + ".DataLoopNode"),
                    "_Sample_condition_variable.Type");
            if (searchResult.size() == 0) return;

            DataLoopNode loopNode = (DataLoopNode) searchResult.elementAt(0);
            /*
            The STAR file does not maintain the same order while including the Tags. So, we make sure, we
             pick the right tag from name_node_list (Tag list), and use its index to pick the tag's value from
             table_node (Value list). At least, the order in which values are coded exactly match the order in which
             the tags are coded.
             */
            DataLoopNameListNode dataLoopNameListNode = loopNode.getNames();
            LoopTableNode table = loopNode.getVals();

            /*
            picking tag index from names list
             */
            for (int i = 0; i < dataLoopNameListNode.size(); i++) {
                LoopNameListNode namelistnode = dataLoopNameListNode.elementAt(i);
                for (int j = 0; j < namelistnode.size(); j++) {
                    DataNameNode nameNode = namelistnode.elementAt(j);
                    if (nameNode.getValue().equals("_Sample_condition_variable.Type")) {
                        variableType = j;
                    }
                    if (nameNode.getValue().equals("_Sample_condition_variable.Val")) {
                        variableValue = j;
                    }
                    if (nameNode.getValue().equals("_Sample_condition_variable.Val_units")) {
                        variable_units = j;
                    }

                }
            }
             /*
             picking corresponding tag value from values list
              */

            for (int j = 0; j < table.size(); j++) {
                LoopRowNode row = table.elementAt(j);
                if (row.elementAt(variableType.intValue()).getValue().equals("pH")) {
                    this.pH = row.elementAt(variableValue.intValue()).getValue();
                } else if (row.elementAt(variableType.intValue()).getValue().equals("temperature")) {
                    this.temperature = row.elementAt(variableValue.intValue()).getValue() + " " +
                            row.elementAt(variable_units.intValue()).getValue();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void extractBrukerInfo() {
        try {
            searchResult = aStarTree.searchForTypeByName(
                    Class.forName(StarValidity.pkgName() + ".SaveFrameNode"),
                    "_NMR_spectrometer.Sf_framecode");
            if (searchResult.size() == 0) return;
            SaveFrameNode saveFrame = (SaveFrameNode) searchResult.elementAt(0);
            for (int i = 0; i < saveFrame.size(); i++) {
                DataItemNode itemNode = (DataItemNode) saveFrame.elementAt(i);
                if (itemNode.getNameNode().getValue().equals("_NMR_spectrometer.Sf_framecode")) {
                    this.nmr_spectromter = itemNode.getValueNode().getValue();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void extractSampleInfo() {

        Integer molecule_name = null;
        Integer concentration = null;
        Integer concentration_unit = null;

        try {
            searchResult = aStarTree.searchForTypeByName(
                    Class.forName(StarValidity.pkgName() + ".DataLoopNode"),
                    "_Sample_component.ID");
            if (searchResult.size() == 0) return;
            DataLoopNode dataLoopNode = (DataLoopNode) searchResult.elementAt(0);

            DataLoopNameListNode dataLoopNameListNode = dataLoopNode.getNames();
            LoopTableNode table = dataLoopNode.getVals();

            for (int i = 0; i < dataLoopNameListNode.size(); i++) {
                LoopNameListNode namelistnode = dataLoopNameListNode.elementAt(i);
                for (int j = 0; j < namelistnode.size(); j++) {
                    DataNameNode nameNode = namelistnode.elementAt(j);
                    if (nameNode.getValue().equals("_Sample_component.Mol_common_name")) {
                        molecule_name = j;
                    }
                    if (nameNode.getValue().equals("_Sample_component.Concentration_val")) {
                        concentration = j;
                    }
                    if (nameNode.getValue().equals("_Sample_component.Concentration_val_units")) {
                        concentration_unit = j;
                    }

                }
            }


            for (int j = 0; j < table.size(); j++) {
                LoopRowNode row = table.elementAt(j);
                String solventType = row.elementAt(6).getValue();
                if (solventType.equalsIgnoreCase(SolventClass.SOLUTE.toString())) {
                    this.solute_conc = row.elementAt(7).getValue() + " " + row.elementAt(10).getValue();
                }
                if (solventType.equalsIgnoreCase(SolventClass.SOLVENT.toString())) {
                    this.solvent = row.elementAt(concentration).getValue() + " " + row.elementAt(concentration_unit).getValue() + " " + row.elementAt(molecule_name).getValue();
                }
                if (solventType.equalsIgnoreCase(SolventClass.BUFFER.toString())) {
                    this.buffer = row.elementAt(concentration).getValue() + " " + row.elementAt(concentration_unit).getValue() + " " + row.elementAt(molecule_name).getValue();
                }
                if (solventType.equalsIgnoreCase(SolventClass.CYTOCIDE.toString())) {
                    this.cytocide = row.elementAt(concentration).getValue() + " " + row.elementAt(concentration_unit).getValue() + " " + row.elementAt(molecule_name).getValue();
                }
                if (solventType.equalsIgnoreCase(SolventClass.REFERENCE.toString())) {
                    this.reference = row.elementAt(concentration).getValue() + " " + row.elementAt(concentration_unit).getValue() + " " + row.elementAt(molecule_name).getValue();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void extractAuthorInfo() {
        try {
            searchResult = aStarTree.searchForTypeByName(
                    Class.forName(StarValidity.pkgName() + ".DataLoopNode"),
                    "_Entry_author.Given_name");
            if (searchResult.size() == 0) return;

            DataLoopNode dataLoopNode = (DataLoopNode) searchResult.elementAt(0);
            LoopTableNode table = dataLoopNode.getVals();
            String full_authors = "";
            for (int j = 0; j < table.size(); j++) {
                LoopRowNode row = table.elementAt(j);
                String auth_name = "";
                for (int i = 1; i < row.size() - 1; i++) {
                    String val = row.elementAt(i).getValue();
                    if (!val.equals("?")) {
                        auth_name += val + " ";
                    }
                }
                full_authors += auth_name.trim() + ",";
            }
            if (full_authors.length() > 0 && full_authors.charAt(full_authors.length() - 1) == ',') {
                full_authors = full_authors.substring(0, full_authors.length() - 1);
            }
            this.data_authors = full_authors;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
//        String infile = "/Users/kalai/Develop/projects/Metabogaps/Projects/BMRB-import/assignment/NMR-files/bmse000148/bmse000148/bmse000148.str";
//        String outFile = "/Users/kalai/Develop/projects/Metabogaps/Projects/BMRB-import/assignment/NMR-files/bmse000148/bmse000148/bmse000148.mif";
//
        BmrbStarFileParser parser = new BmrbStarFileParser();
        if (args.length > 2) {
            System.out.println("input .str file or .str and o/p file");
            System.exit(0);
        }
        if (args.length == 1) {
            parser.parseFile(args[0]);

        }
        if (args.length == 2) {
            parser.parseFile(args[0], args[1]);
        }
    }

}
