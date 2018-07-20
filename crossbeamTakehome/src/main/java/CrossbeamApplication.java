import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class CrossbeamApplication {

    public  static void main(String args[]) throws IOException {
        String baseURL = "https://s3.amazonaws.com/challenge.getcrossbeam.com/public/";
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the two data-set's");
        String input = in.nextLine();
        String[] dataSets = input.split(" ");
        String dataSet1 = dataSets[0] + ".json";
        String dataSet2 = dataSets[1] + ".json";

        //first data set
        URL url1 = new URL(baseURL + dataSet1);
        URLConnection request1 = url1.openConnection();
        request1.connect();

        JsonParser jp = new JsonParser();
        JsonElement root1 = jp.parse(new InputStreamReader((InputStream) request1.getContent())); //Convert the input stream to a json element
        JsonObject obj1 = root1.getAsJsonObject();

        JsonArray arr1 = obj1.get("companies").getAsJsonArray();
        Set<String> domains1 = new HashSet<String>();
        for (int i = 0; i < arr1.size(); i++) {
            JsonObject arrObj = arr1.get(i).getAsJsonObject();
            domains1.add(arrObj.get("domain").getAsString());
        }

        int size1 = domains1.size();

        //second data set
        URL url2 = new URL(baseURL + dataSet2);
        URLConnection request2 = url2.openConnection();
        request2.connect();

        JsonElement root2 = jp.parse(new InputStreamReader((InputStream) request2.getContent())); //Convert the input stream to a json element
        JsonObject obj2 = root2.getAsJsonObject();

        JsonArray arr2 = obj2.get("companies").getAsJsonArray();
        Set<String> domains2 = new HashSet<String>();
        for (int i = 0; i < arr2.size(); i++) {
            JsonObject arrObj = arr2.get(i).getAsJsonObject();
            domains2.add(arrObj.get("domain").getAsString());
        }

        int size2 = domains2.size();

        int overlappingCompanies = 0;

        for(String s1: domains1){
            for (String s2: domains2){
                if (s1.equals(s2)){
                    overlappingCompanies++;
                }
            }
        }

        System.out.print(size1 + " " + size2 + " " + overlappingCompanies);
    }
}
