package com.kiko.WS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

//Functions
import com.kiko.RabbitMQ.MessagePublisher;
import com.kiko.RabbitMQ.MessageSubscriber;
import com.kiko.ExternalServices.RandomIDService;
import com.kiko.ExternalServices.WeatherService;
import com.kiko.ExternalServices.User;

//DTOs
import com.kiko.DTOs.TravelOfferDTO;
import com.kiko.DTOs.TravelIntentDTO;
import static com.kiko.ExternalServices.WeatherService.getLocationCoordinates;




@Path("orchestrator")
@Produces("application/json")
@Consumes("application/json")
public class OrchestratorService {

    @Context
    private UriInfo context;

    @GET
    public Response getHtml() {
        return Response.status(400).entity("Travellers API").build();
    }
    
    @GET
    @Path("/getRandomID")
    public Response getRandomID(@QueryParam("purpose") String purpose, @QueryParam("password") String password) throws Exception 
    {    
        String rID;
        
        purpose = purpose.toUpperCase();
        
        switch (purpose) 
        {
            case "USER":
                if(password.isEmpty())
                {
                    password = "pass";
                }
                rID = RandomIDService.getNewUserUID(password);
                break;
                
            case "MESSAGE":
                rID = RandomIDService.getNewMessageUID();
                break;
                
            default:
                rID = "Invalid Purpose";
                break;
        }
        
        if(rID.equals("Invalid Purpose"))
        {
            return Response.status(400).entity(rID).build();
        }
        else
        {
            return Response.status(Response.Status.OK).entity(rID).build();
        }
    }
    
    @GET
    @Path("/loadUser")
    public Response loadUser(@QueryParam("id") String id, @QueryParam("password") String password) throws Exception
    {
        boolean checkUser = User.checkUserID(id,password);
        String response = "" + checkUser;
        
        return Response.status(Response.Status.OK).entity(response).build();
    }
    
    @GET
    @Path("/checkIfAddressExists")
    public Response checkIfAddressExists(@QueryParam("address") String address) throws Exception {
        
        JsonObject coordinatesJsonObject = getLocationCoordinates(address);
        
        String status = coordinatesJsonObject.getAsJsonObject().get("status").getAsString();
        
        if(status.equals("ZERO_RESULTS"))
        {
            return Response.status(Response.Status.OK).entity("false").build();
        }
        else
        {
            return Response.status(Response.Status.OK).entity("true").build();
        }        
    }
    
    @GET
    @Path("/getAddressWeather")
    public Response getAddressWeather(@QueryParam("address") String address) throws Exception {
        String addressWeather = WeatherService.getWeather(address, "asd");
        
        if(addressWeather.equals("ZERO_RESULTS"))
        {
            return Response.status(400).entity("Invalid Address").build();
        }
        else
        {
            return Response.status(Response.Status.OK).entity(addressWeather).build();
        }        
    }
    
    @POST
    @Path("/submitTravelOffer")
    public Response submitTravelOffer(TravelOfferDTO travelOffer) throws Exception 
    {            
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        String message = gson.toJson(travelOffer);
        
        MessageSubscriber.subscribe("TRAVEL_OFFERS");
        String response = MessagePublisher.publish(message, "TRAVEL_OFFERS");
                
        return Response.status(Response.Status.OK).entity(response).build();
    }
    
    @GET
    @Path("/queryTravelOffers")
    public Response queryTravelOffers() throws Exception 
    {
        String travelOffersJsonFile = "./webapps/DATA/travelOffers.json";
        
        File f = new File(travelOffersJsonFile);
        
        if(!f.exists())
        {
            return Response.status(Response.Status.OK).entity("No Travel Offers!").build();
        }
        else
        {
            String jsonFile = new String(Files.readAllBytes(Paths.get(travelOffersJsonFile)), StandardCharsets.UTF_8); 
        
            JsonElement jsonElement = JsonParser.parseString(jsonFile);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray jsonArray = jsonObject.getAsJsonArray("travelOffers");
        
            int size = jsonArray.size();
                    
            for (int i = 0; i < size; i++)
            {
                JsonElement offer = jsonArray.get(i);
                String location = offer.getAsJsonObject().get("location").getAsString();
                String tripStartDate = offer.getAsJsonObject().get("tripStartDate").getAsString();
                
                String weatherDataStr = WeatherService.getWeather(location, tripStartDate);
                JsonElement weatherDataElement = JsonParser.parseString(weatherDataStr);
                
                String weatherCondition = weatherDataElement.getAsJsonObject().get("conditionDescription").getAsString();
                String averageTemperature = weatherDataElement.getAsJsonObject().get("avgTemperature").getAsString();
                
                JsonObject weatherDetails = new JsonObject();
                weatherDetails.addProperty("weatherCondition", weatherCondition);
                weatherDetails.addProperty("averageTemperature", averageTemperature);
                
                offer.getAsJsonObject().add("weather", weatherDetails);
            }
            
            String updatedJson = new Gson().toJson(jsonObject);
                
            return Response.status(Response.Status.OK).entity(updatedJson).build();
        }     
        
    }
    
    @POST
    @Path("/submitTravelIntent")
    public Response submitTravelIntent(TravelIntentDTO travelIntent) throws Exception 
    {            
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        String message = gson.toJson(travelIntent);
        
        MessageSubscriber.subscribe("TRAVEL_INTENT");
        String response = MessagePublisher.publish(message, "TRAVEL_INTENT");
                
        return Response.status(Response.Status.OK).entity(response).build();
    }
    
    @GET
    @Path("/queryTravelIntents")
    public Response queryTravelIntent(@QueryParam("msgId") String msgId) throws Exception 
    {
        String travelIntentsJsonFile = "./webapps/DATA/travelIntents.json";

        File f = new File(travelIntentsJsonFile);
        
        if(!f.exists())
        {
            return Response.status(Response.Status.OK).entity("No Travel Intents!").build();
        }
        else
        {
            String jsonFile = new String(Files.readAllBytes(Paths.get(travelIntentsJsonFile)), StandardCharsets.UTF_8); 
        
            JsonElement jsonElement = JsonParser.parseString(jsonFile);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray jsonArray = jsonObject.getAsJsonArray("travelIntents");
        
            int size = jsonArray.size();
                    
            JsonObject travelIntentsObject = new JsonObject();
            JsonArray travelIntentsArray = new JsonArray();
            
            String a = "";
            
            for (int i = 0; i < size; i++)
            {
                String messageId = jsonArray.get(i).getAsJsonObject().get("msgId").getAsString();
                
                if(messageId.equals(msgId))
                {
                    JsonObject intents = new JsonObject();
                    
                    String userId = jsonArray.get(i).getAsJsonObject().get("userId").getAsString();
                    String intentMessage = jsonArray.get(i).getAsJsonObject().get("intentMessage").getAsString();
                    
                    intents.addProperty("userId" , userId);
                    intents.addProperty("intentMessage" , intentMessage);
                    
                    travelIntentsArray.add(intents);
                }
            }
                        
            String response = new Gson().toJson(travelIntentsArray);
                
            return Response.status(Response.Status.OK).entity(response).build();
        }     
        
    }
}
