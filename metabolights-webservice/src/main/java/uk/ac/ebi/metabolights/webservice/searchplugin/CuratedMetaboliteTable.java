package uk.ac.ebi.metabolights.webservice.searchplugin;

import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.webservice.controllers.GenericCompoundWSController;
import uk.ac.ebi.metabolights.webservice.utils.PropertiesUtil;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kalai on 01/08/2016.
 */
public class CuratedMetaboliteTable {
    private static Logger logger = LoggerFactory.getLogger(CuratedMetaboliteTable.class);
    private String curatedMetaboliteListLocation = PropertiesUtil.getProperty("curatedMetaboliteListLocation");
    private List<String[]> curatedMetaboliteList;

    public CuratedMetaboliteTable() {
        this.curatedMetaboliteList = initialiseCuratedList();
    }

    private List<String[]> initialiseCuratedList() {
        TsvParserSettings settings = new TsvParserSettings();
        settings.getFormat().setLineSeparator("\r");
        TsvParser parser = new TsvParser(settings);

// parses all rows in one go.
        try {
            List<String[]> curatedMetabolites = parser.parseAll(new FileReader(curatedMetaboliteListLocation));
            logger.info("Total " + curatedMetabolites + " Loaded from " + curatedMetaboliteListLocation);
            return removeQoutesFrom(curatedMetabolites);
        } catch (FileNotFoundException e) {
            logger.error("Something went wrong while parsing the curated metabolights list", e);
            e.printStackTrace();
        }
        return new ArrayList<String[]>();

    }

    private List<String[]> removeQoutesFrom(List<String[]> curatedMetabolites) {
        List<String[]> qoutesRemovedList = new ArrayList<>();
        for (String[] row : curatedMetabolites) {
            try {
                String compoundName = row[CuratedMetabolitesFileColumnIdentifier.COMPOUND_NAME.getID()];
                if (compoundName != null || !compoundName.isEmpty()) {
                    compoundName = compoundName.replaceAll("\"", "");
                    row[CuratedMetabolitesFileColumnIdentifier.COMPOUND_NAME.getID()] = compoundName;
                }
                String inchi = row[CuratedMetabolitesFileColumnIdentifier.INCHI.getID()];
                if (inchi != null || !inchi.isEmpty()) {
                    inchi = inchi.replaceAll("\"", "");
                    row[CuratedMetabolitesFileColumnIdentifier.INCHI.getID()] = inchi;
                }
                qoutesRemovedList.add(row);
            } catch (Exception e) {
                logger.error("Something went wrong while removing qoutes: " + e);
                continue;
            }
        }
        return qoutesRemovedList;
    }

    public String[] getRow(int index) {
        return curatedMetaboliteList.get(index);
    }

    public boolean containsInColumn(int index, String value) {
        for (String[] row : curatedMetaboliteList) {
            if (row[index].equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    public String[] getMatchingRow(int index, String value) {
        value = value.replaceAll("\\s", "");
        List<String[]> matchingRows = new ArrayList<>();
        for (String[] row : curatedMetaboliteList) {
            try {
                if (row[index] != null || !row[index].isEmpty()) {
                    if (row[index].contains("|")) {
                        String[] split = row[index].split("\\|");
                        for (String s : split) {
                            if (s.replaceAll("\\s", "").equalsIgnoreCase(value)) {
                                matchingRows.add(row);
                            }
                        }
                    } else {
                        if (row[index].replaceAll("\\s", "").equalsIgnoreCase(value)) {
                            matchingRows.add(row);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("Something went wrong while finding a match in the list: " + e);
                continue;
            }
        }
        return prioritizedOneFromThe(matchingRows);
    }

    private String[] prioritizedOneFromThe(List<String[]> matchingRows) {
        if (matchingRows.isEmpty()) {
            return new String[0];
        }
        if (matchingRows.size() == 1) {
            return matchingRows.get(0);
        }
        for (String[] row : matchingRows) {
            try {
                String priorityIndex = row[CuratedMetabolitesFileColumnIdentifier.PRIORITY.getID()];
                if (priorityIndex != null || !priorityIndex.isEmpty()) {
                    if (Integer.parseInt(priorityIndex) == 1) {
                        return row;
                    }
                }
            } catch (Exception e) {
                logger.error("Something went wrong while extracting priority row: " + e);
                continue;
            }
        }
        return matchingRows.get(0);
    }

    public static class CuratedMetaboliteTableGeneratorHolder {

        private static final CuratedMetaboliteTable INSTANCE = new CuratedMetaboliteTable();
    }

    public static CuratedMetaboliteTable getInstance() {
        logger.info("Creating instance of CuratedMetaboliteTable");
        return CuratedMetaboliteTableGeneratorHolder.INSTANCE;
    }
}
