package uk.ac.ebi.metabolights.referencelayer.spectra.nmr.bmrb;

import org.apache.commons.cli.*;

import java.io.File;
import java.util.Arrays;

/**
 * Created by kalai on 09/01/2014.
 */
public class InputParser {

    private CommandLineParser parser = null;
    private Options options = null;
    private HelpFormatter formatter;
    private static final String HELP_DESCRIPTION = "\t Input dir or file, of processed NMR bruker files to convert into JSON file. " +
            "If both dir&file is input only directory will be considered for processing. At the moment, only bmrb specific directory structure is handled";
    private BmrbToJsonConverter bmrbToJsonConverter = null;

    public InputParser() {
        parser = new BasicParser();
        options = new Options();
        formatter = new HelpFormatter();
        bmrbToJsonConverter = new BmrbToJsonConverter();
        options.addOption("h", "help", false, "Usage information");
        options.addOption(OptionBuilder.withArgName(".txt")
                .hasArg()
                .withDescription("Input .txt file to convert to JSON")
                .create("inFile"));
        options.addOption(OptionBuilder.withArgName(".json")
                .hasArg()
                .withDescription("Output JSON file")
                .create("outFile"));
        options.addOption(OptionBuilder.withArgName("in DIR")
                .hasArg()
                .withDescription("Input directory with all processed NMR file location. Output JSON will be written to the same directory.")
                .create("inDir"));
    }

    public void parseUserInput(String[] args) {
        try {
            CommandLine commandLine = parser.parse(options, args);
            if (commandLine.hasOption("help")) {
                formatter.printHelp(HELP_DESCRIPTION + "\n", options);
                System.exit(0);
            }
            if (commandLine.hasOption("inFile")) {
                bmrbToJsonConverter.setProcessedNMRfile(new File((commandLine.getOptionValue("inFile"))));
            }
            if (commandLine.hasOption("outFile")) {
                bmrbToJsonConverter.setOutputJSONFile(new File((commandLine.getOptionValue("outFile"))));
            }
            if (commandLine.hasOption("inDir")) {
                bmrbToJsonConverter.setNmrFilesDirectory(new File((commandLine.getOptionValue("inDir"))));
            }

        } catch (UnrecognizedOptionException ure) {
            System.out.println("Unrecognised input option. Please Check the input options using -help");
            System.exit(0);
        } catch (ParseException ex) {
            System.out.println("Unrecognised input option. Please Check the input options using -help");
            System.exit(0);
        }
    }

    public void startConversion() {
        bmrbToJsonConverter.convert();
    }

    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                System.out.println("Run with -help for usage information");
                System.exit(0);
            }
            long start = System.currentTimeMillis();
            System.out.println(Arrays.asList(args));
            InputParser parser = new InputParser();
            parser.parseUserInput(args);
            parser.startConversion();
            long end = System.currentTimeMillis();
            System.out.println("Finished in " + (end - start) / 1000 + " s");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
