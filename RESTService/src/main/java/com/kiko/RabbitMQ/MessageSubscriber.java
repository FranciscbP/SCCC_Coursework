package com.kiko.RabbitMQ;

import com.kiko.ServerConfigs;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.io.File;
import java.io.FileWriter; 
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageSubscriber {
    
    private static enum EXCHANGE_TYPE {DIRECT, FANOUT, TOPIC, HEADERS};
    private final static String QUEUE_NAME = "Travellers";
    private final static String TOPIC_KEY_NAME = "";
        
    public static final String triavelOffersJsonFile = "./webapps/DATA/travelOffers.json";
    public static final String travelIntentsJsonFile = "./webapps/DATA/travelIntents.json";
    
    public static void subscribe(String EXCHANGE_NAME) throws Exception
    {    
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ServerConfigs.HOST);
        factory.setUsername(ServerConfigs.AMQP_USER);
        factory.setPassword(ServerConfigs.AMQP_PASSWORD);
        
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        
        channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE.TOPIC.toString().toLowerCase());
        
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, TOPIC_KEY_NAME);
        
        System.out.println(" [*] Waiting for " + TOPIC_KEY_NAME +  " messages. To exit press CTRL+C");
                
        if(EXCHANGE_NAME.equals("TRAVEL_OFFERS"))
        {
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                saveTravelOffers(message);
                connection.close();
            };

            // Consume messages from the queue by using the callback
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
        }
        else if (EXCHANGE_NAME.equals("TRAVEL_INTENT")) 
        {
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                saveTravelIntent(message);
                connection.close();
            };

            // Consume messages from the queue by using the callback
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
        }
     }
    
    public static void saveTravelOffers(String message) throws IOException 
    {
        String fileName = triavelOffersJsonFile;
        String fileContent;
        
        File f = new File(fileName);
        
        if(!f.exists())
        {
            try(PrintWriter pw = new PrintWriter(new FileWriter(fileName)))
            {
                fileContent = "{\"travelOffers\":[" + message + "]}";
                pw.write(fileContent);
                pw.close();
            }
            catch (IOException e) {}
        }
        else
        {   
           fileContent = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
            
           try(PrintWriter pw = new PrintWriter(new FileWriter(fileName)))
            {
                fileContent = fileContent.replace("]",","+message+"]");
                pw.write(fileContent);
                pw.close();
            }
            catch (IOException e) {}
        }
    }
    
        public static void saveTravelIntent(String message) throws IOException 
    {
        String fileName = travelIntentsJsonFile;
        String fileContent;
        
        File f = new File(fileName);
        
        if(!f.exists())
        {
            try(PrintWriter pw = new PrintWriter(new FileWriter(fileName)))
            {
                fileContent = "{\"travelIntents\":[" + message + "]}";
                pw.write(fileContent);
                pw.close();
            }
            catch (IOException e) {}
        }
        else
        {   
           fileContent = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
            
           try(PrintWriter pw = new PrintWriter(new FileWriter(fileName)))
            {
                fileContent = fileContent.replace("]",","+message+"]");
                pw.write(fileContent);
                pw.close();
            }
            catch (IOException e) {}
        }
    }
}
