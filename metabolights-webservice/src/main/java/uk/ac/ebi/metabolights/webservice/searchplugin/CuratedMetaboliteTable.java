package uk.ac.ebi.metabolights.webservice.searchplugin;

import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import uk.ac.ebi.metabolights.webservice.utils.PropertiesUtil;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kalai on 01/08/2016.
 */
public class CuratedMetaboliteTable {
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
            return parser.parseAll(new FileReader(curatedMetaboliteListLocation));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<String[]>();

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
        for (String[] row : curatedMetaboliteList) {
            try {
                if (row[index].contains("|")) {
                    String[] split = row[index].split("\\|");
                    for (String s : split) {
                        if (s.replaceAll("\\s", "").replaceAll("\"","").equalsIgnoreCase(value)) {
                            return row;
                        }
                    }
                } else {
                    if (row[index].replaceAll("\\s", "").replaceAll("\"","").equalsIgnoreCase(value)) {
                        return row;
                    }
                }
            } catch (Exception e) {
                continue;
            }
        }
        return new String[0];
    }

    public static class CuratedMetaboliteTableGeneratorHolder {

        private static final CuratedMetaboliteTable INSTANCE = new CuratedMetaboliteTable();
    }

    public static CuratedMetaboliteTable getInstance() {
        return CuratedMetaboliteTableGeneratorHolder.INSTANCE;
    }
}
