package com.kiko.ExternalServices;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherService {
    public static String GoogleAPIKey = "...";
        
    public static JsonObject getLocationCoordinates(String location) throws IOException
    {
        BufferedReader br;
        String line;
        StringBuilder responseContent = new StringBuilder();  
        
        String formatedLocation = location.toLowerCase().replace(" ", "%20");
        
        URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address="+formatedLocation+"&key="+ GoogleAPIKey);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        
        while ((line = br.readLine()) != null) 
        {
                 responseContent.append(line);
        }
        
        br.close();
        
        String response = responseContent.toString();
        JsonElement jsonElement = JsonParser.parseString(response);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        
        return jsonObject;
    }
    
    public static String getWeather(String location,String date) throws IOException
    {
        BufferedReader br;
        String line;
        StringBuilder responseContent = new StringBuilder();  
        String response;
        
        JsonObject coordinatesJsonObject = getLocationCoordinates(location);
        
        String status = coordinatesJsonObject.getAsJsonObject().get("status").getAsString();
        
        if(status.equals("OK"))
        {
            JsonElement results = coordinatesJsonObject.getAsJsonArray("results").get(0);
            JsonElement locationObject = results.getAsJsonObject().get("geometry").getAsJsonObject().get("location");      

            String locationLatitude =  locationObject.getAsJsonObject().get("lat").getAsString();
            String locationLongitude =  locationObject.getAsJsonObject().get("lng").getAsString();

            URL url = new URL("http://api.weatherapi.com/v1/forecast.json?key=2f9c998ad41640f2a7e21906221908&q="+ locationLatitude +","+locationLongitude+"&days=10");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            while ((line = br.readLine()) != null) 
            {
                     responseContent.append(line);
            }

            br.close();

            response = responseContent.toString();
            
            JsonElement responseJsonElement = JsonParser.parseString(response);
            JsonObject responseJsonObject = responseJsonElement.getAsJsonObject();
            JsonObject forecastJsonElement = responseJsonObject.getAsJsonObject().get("forecast").getAsJsonObject();
            
            JsonArray forecastDayArray = forecastJsonElement.getAsJsonArray("forecastday");
            
            int size = forecastDayArray.size();
            
            int correctDateOnArray = 0;
            
            for (int i = 0; i < size; i++)
            {
                    String dateStr = forecastDayArray.get(i).getAsJsonObject().get("date").getAsString();
                    if(dateStr.equals(date))
                    {
                        correctDateOnArray = i;
                    }
            }
            
            JsonElement dayResultsElement = forecastDayArray.get(correctDateOnArray).getAsJsonObject().get("day");
            
            String avgTemperature = dayResultsElement.getAsJsonObject().get("avgtemp_c").getAsString();
            String conditionCode = dayResultsElement.getAsJsonObject().getAsJsonObject().get("condition").getAsJsonObject().get("code").getAsString();
                  
            String conditionDescription = getCodeDescription(conditionCode);
            
            String jsonResponse = "{\"avgTemperature\": \""+avgTemperature+"\",\"conditionDescription\": \""+conditionDescription+"\"}";
            response = jsonResponse;
        }
        else
        {
            response = "ZERO_RESULTS";
        }

        return response;
    }
        
    public static String getCodeDescription(String code) throws IOException
    {
        BufferedReader br;
        String line;
        StringBuilder responseContent = new StringBuilder();  
        String response;
        
        String codeDescription = "";
        
        URL url = new URL("https://www.weatherapi.com/docs/weather_conditions.json");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

        while ((line = br.readLine()) != null) 
        {
                 responseContent.append(line);
        }

        br.close();

        response = responseContent.toString();
        
        JsonElement responseJsonElement = JsonParser.parseString(response);
        JsonArray jsonArray = responseJsonElement.getAsJsonArray();
               
        int size = jsonArray.size();
                    
        for (int i = 0; i < size; i++)
        {
            JsonElement codesFromJson = jsonArray.get(i).getAsJsonObject().get("code");
            String codeStr = new Gson().toJson(codesFromJson);
            
            if(codeStr.equals(code))
            {
                codeDescription = jsonArray.get(i).getAsJsonObject().get("day").getAsString();
            }    
        }
        
        return codeDescription;
    }
    
   /*public static void main(String args[]) throws IOException  //static method  
    {  
        String a = getWeather("london","2022-08-20");
        System.out.println(a);
    }*/
    
}
