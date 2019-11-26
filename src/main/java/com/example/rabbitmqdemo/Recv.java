package com.example.rabbitmqdemo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv {
    private final static String QUEUE_NAME = "hello";
    public static void main(String[] args) throws Exception{
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

        Connection con = cf.newConnection();
             Channel channel = con.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println("Waiting for messages. To exit, press Ctrl+C");

            DeliverCallback deliverCallback = ((consumerTag, message) -> {
                String msg = new String(message.getBody(), "UTF-8");
                System.out.println("Revieved '" + msg +"'");

            });
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});

    }
}
