package com.kiko.RabbitMQ;

import com.kiko.ServerConfigs;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.nio.charset.StandardCharsets;

public class MessagePublisher {
    
    private static enum EXCHANGE_TYPE{DIRECT,FANOUT,TOPIC,HEADERS}
    private final static String TOPIC_KEY_NAME = "";
    
    public static String publish(String message, String EXCHANGE_NAME) throws Exception 
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ServerConfigs.HOST);
        factory.setUsername(ServerConfigs.AMQP_USER);
        factory.setPassword(ServerConfigs.AMQP_PASSWORD);
        
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel();) 
        {
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE.TOPIC.toString().toLowerCase());
            channel.basicPublish(EXCHANGE_NAME,TOPIC_KEY_NAME,
                    new AMQP.BasicProperties.Builder()
                        .contentType("application/json")
                        .deliveryMode(2)
                        .priority(1)
                        .userId("kiko")
                        .build(),
                    message.getBytes(StandardCharsets.UTF_8));
            return "Message sent: '" + message + "' to Exchange:"+ EXCHANGE_NAME;
        }
    }
}
