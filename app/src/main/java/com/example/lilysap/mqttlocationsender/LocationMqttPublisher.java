package com.example.lilysap.mqttlocationsender;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by Lily on 4/4/2017.
 */

public class LocationMqttPublisher {


    private static final String EXCHANGE_NAME = "GPSLocation";

    public void publishToAMQP(final double latitude, final double longitude){
        Thread publishThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ConnectionFactory factory= new ConnectionFactory();
                    factory.setUri("amqp://kkxglvxw:WhGhDnh9_wSe-Jf_TTjwevZkG9A9bZhv@clam.rmq.cloudamqp.com/kkxglvxw");
                    String message = longitude + " " + latitude;

                    Connection connection = factory.newConnection();
                    Channel channel = connection.createChannel();
                    channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
                    //channel.confirmSelect();

                    channel.basicPublish(EXCHANGE_NAME, "",null ,message.getBytes());

                    channel.close();
                    connection.close();

                    System.out.println(" [x] Sent: '" + message + "'");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        publishThread.run();
    }
}
