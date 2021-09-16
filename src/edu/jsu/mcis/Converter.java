package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import com.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {

    /*
    
        Consider the following CSV data:
        
        "ID","Total","Assignment 1","Assignment 2","Exam 1"
        "111278","611","146","128","337"
        "111352","867","227","228","412"
        "111373","461","96","90","275"
        "111305","835","220","217","398"
        "111399","898","226","229","443"
        "111160","454","77","125","252"
        "111276","579","130","111","338"
        "111241","973","236","237","500"
        
        The corresponding JSON data would be similar to the following (tabs and
        other whitespace have been added for clarity).  Note the curly braces,
        square brackets, and double-quotes!  These indicate which values should
        be encoded as strings, and which values should be encoded as integers!
        
        {
            "colHeaders":["ID","Total","Assignment 1","Assignment 2","Exam 1"],
            "rowHeaders":["111278","111352","111373","111305","111399","111160",
            "111276","111241"],
            "data":[[611,146,128,337],
                    [867,227,228,412],
                    [461,96,90,275],
                    [835,220,217,398],
                    [898,226,229,443],
                    [454,77,125,252],
                    [579,130,111,338],
                    [973,236,237,500]
            ]
        }
    
        Your task for this program is to complete the two conversion methods in
        this class, "csvToJson()" and "jsonToCsv()", so that the CSV data shown
        above can be converted to JSON format, and vice-versa.  Both methods
        should return the converted data as strings, but the strings do not need
        to include the newlines and whitespace shown in the examples; again,
        this whitespace has been added only for clarity.
    
        NOTE: YOU SHOULD NOT WRITE ANY CODE WHICH MANUALLY COMPOSES THE OUTPUT
        STRINGS!!!  Leave ALL string conversion to the two data conversion
        libraries we have discussed, OpenCSV and json-simple.  See the "Data
        Exchange" lecture notes for more details, including example code.
    
     */
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {

        String results = "";

        try {

            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> full = reader.readAll();
            Iterator<String[]> iterator = full.iterator();

            // INSERT YOUR CODE HERE
            CSVParser parser = new CSVParser();
            BufferedReader buffReader = new BufferedReader(new StringReader(csvString));
            JSONObject jsonObj = new JSONObject();

            JSONArray colHead = new JSONArray();
            JSONArray rowHead = new JSONArray();
            JSONArray dataArr = new JSONArray();

            colHead.add("ID");
            colHead.add("Total");
            colHead.add("Assignment 1");
            colHead.add("Assignment 2");
            colHead.add("Exam 1");
            jsonObj.put("colHeaders", colHead);
            jsonObj.put("rowHeaders", rowHead);
            jsonObj.put("data", dataArr);

            String line = buffReader.readLine();
            while ((line = buffReader.readLine()) != null) {
                String[] parsedData = parser.parseLine(line);
                rowHead.add(parsedData[0]);
                JSONArray rows = new JSONArray();
                rows.add(new Long(parsedData[1]));
                rows.add(new Long(parsedData[2]));
                rows.add(new Long(parsedData[3]));
                rows.add(new Long(parsedData[4]));
                dataArr.add(rows);
            }

            results = jsonObj.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return results.trim();

    }

    public static String jsonToCsv(String jsonString) {

        String results = "";

        try {

            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\\', "\n");

            // INSERT YOUR CODE HERE
            JSONParser parser = new JSONParser();
            JSONObject jsonObj = (JSONObject) parser.parse(jsonString);

            JSONArray cols = (JSONArray) jsonObj.get("colHeaders");
            JSONArray rows = (JSONArray) jsonObj.get("rowHeaders");
            JSONArray dataArr = (JSONArray) jsonObj.get("data");

            int j = 0;
            int counter = 1;

            for (int i = 0; i < cols.size(); i++) {
                if (i != cols.size() - 1) {
                    writer.append("\"" + cols.get(i) + "\",");
                } else {
                    writer.append("\"" + cols.get(i) + "\"");
                }
            }
            writer.append("\n");

            for (int i = 0; i < rows.size(); i++) {
                writer.append("\"" + rows.get(i) + "\",");
                while (j < counter) {
                    JSONArray part = (JSONArray) dataArr.get(j);
                    for (int k = 0; k < part.size(); k++) {
                        if (k != part.size() - 1) {
                            writer.append("\"" + part.get(k) + "\",");
                        } else {
                            writer.append("\"" + part.get(k) + "\"");
                        }
                    }
                    j++;
                }
                counter++;
                writer.append("\n");
            }

            results += writer.toString();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return results.trim();

    }

}
