package utilities;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.CoreMatchers;
import org.json.simple.parser.ParseException;
import steps.APIServices;

import java.util.Properties;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;

import org.json.simple.parser.JSONParser;

import static org.hamcrest.MatcherAssert.assertThat;

public class GenericFunctions{

    private static APIServices apis = new APIServices();
    private static Response response;
    private static String url;
    private static String serviceStatus;
    private int petStatusCount;
    private static JSONArray jsonList;

    public static String getConfigValue(String key)throws Exception{
        FileReader reader = null;
        try {
            reader = new FileReader("./src/test/java/resources/config.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Properties prop = new Properties();
        try {
            prop.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String keyValue = prop.getProperty(key);
        return keyValue;
    }

    public static void verifyResponseStatusValue(Response response, int expectedCode) {
        assertThat(response.getStatusCode(), CoreMatchers.is(expectedCode));
    }

    public static void getAPIServiceStatus()throws Exception{
        url = null;
        String baseURL = GenericFunctions.getConfigValue("base.url");
        String swaggerEngPoint = GenericFunctions.getConfigValue("swagger.endpoint");

        url = baseURL + swaggerEngPoint;
        response = apis.getMethod(url);
        if (response.getStatusCode() == HttpStatus.SC_OK) {
            serviceStatus = "true";
        } else {
            serviceStatus = "false";
        }
    }

    public static String getServiceURL()throws Exception{
        url = null;
        String endPoint = GenericFunctions.getConfigValue("petstatus.endpoint");
        String queryParameter = GenericFunctions.getConfigValue("petstatus.query.parameter");
        if (serviceStatus.equals("true")) {
            url = GenericFunctions.getConfigValue("base.url") + endPoint + queryParameter;
        } else {
            url = "./src/test/java/resources/pets.json";
        }
        return url;
    }

    public static void getPetsByStatus(PetStatus petStatus) throws IOException {
        if (serviceStatus.equals("true")) {
            String apiURL = url + petStatus.getName();
            APIServices apiServices = new APIServices();
            response = apiServices.getMethod(apiURL);
        } else {
            JSONParser jsonParser = new JSONParser();
            try (FileReader reader = new FileReader(url))
            {
                Object obj = jsonParser.parse(reader);
                jsonList = (JSONArray) obj;

            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            } catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static int validatePETResponse(String petName, String petStat, String petCount){
        int count=0;
        if (serviceStatus.equals("true")) {
            if (response.contentType().equalsIgnoreCase("application/json")) {
                JsonArray entries = (JsonArray) new JsonParser().parse(response.asString());
                for (int i = 0; i < entries.size(); i++) {
                    JsonElement name = ((JsonObject) entries.get(i)).get("name");
                    JsonElement status = ((JsonObject) entries.get(i)).get("status");
                    if (name != null) {
                        if (name.getAsString().equalsIgnoreCase(petName) && status.getAsString().equalsIgnoreCase(petStat)) {
                            count++;
                        }
                    }
                }
            }
        }   else {
            JsonArray entries = (JsonArray) new JsonParser().parse(String.valueOf(jsonList));
            for(int i=0;i<entries.size();i++){
                JsonElement name = ((JsonObject)entries.get(i)).get("name");
                JsonElement status = ((JsonObject)entries.get(i)).get("status");
                if(name.getAsString().equalsIgnoreCase(petName) && status.getAsString().equalsIgnoreCase(petStat)){
                    count++;
                }
            }
        }
        System.out.println("**** The no. of records meeting the criteria are: "+count);
        return  count;
    }

}