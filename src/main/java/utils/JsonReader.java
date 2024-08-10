package utils;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;

public class JsonReader {


    public static String getTestData(String key) throws IOException, ParseException {
        String testDataValue;
        return testDataValue = (String) getJsonData().get(key);
    }

    public static JSONObject getJsonData() throws IOException, ParseException {

        //pass the path of the testdata.json file
        File fileName = new File("resources//TestData//testdata.json");

        //convert json file to string (directly we convert into object for file reader, that's why we are converting to string)
        String json = FileUtils.readFileToString(fileName, "UTF-8");

        //parse the string into object
        Object obj = new JSONParser().parse(json);

        //give jsonobject so that i can return it to the function everytime it get called
        JSONObject jsonObject = (JSONObject) obj;
        return jsonObject;
    }

    public static JSONArray getJsonArray(String key) throws IOException, ParseException {
        JSONObject jsonObject = getJsonData();
        JSONArray jsonArray = (JSONArray) jsonObject.get(key);
        return jsonArray;
    }

    public static Object getJsonArrayData(String key, int index) throws IOException, ParseException {
        JSONArray languages = getJsonArray(key);
        return languages.get(index);
    }
}
