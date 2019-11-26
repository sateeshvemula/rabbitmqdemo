package com.example.rabbitmqdemo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class Send {
    private final static String QUEUE_NAME = "hello";


    public static void main(String[] args) {
        ConnectionFactory cf = new ConnectionFactory();
        try {
            //cf.setUri("amqp://admin:admin@172.16.100.180:15672/#/");
            cf.setHost("172.16.100.180");
            cf.setPort(5672);
            cf.setVirtualHost("/");
            cf.setUsername("admin");
            cf.setPassword("admin");
        } catch (Exception n) {
            n.printStackTrace();
        }

        try (Connection con = cf.newConnection();
             Channel channel = con.createChannel()
        ) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello WOrld!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("Sent '"+message+"'");
        }

        catch (TimeoutException t) {
            t.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
