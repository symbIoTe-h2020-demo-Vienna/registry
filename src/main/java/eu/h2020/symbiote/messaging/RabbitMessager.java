package eu.h2020.symbiote.messaging;

import com.google.gson.Gson;
import com.rabbitmq.client.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * Created by mateuszl on 22.09.2016.
 */

public class RabbitMessager {

    private static Log log = LogFactory.getLog( RabbitMessager.class );

    //TODO queues list in some form

    /**
     * Method for sending a message to specified 'queue' on RabbitMQ server. Object is converted to Json.
     *
     * @param queueName
     * @param object
     * @throws Exception
     */
    public static void sendMessage(String queueName, Object object) throws Exception {

        log.info("SendMessage request initiated");

        Gson gson = new Gson();
        String objectInJson = gson.toJson(object);

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queueName, false, false, false, null);

        String message = objectInJson;
        channel.basicPublish("", queueName, null, message.getBytes("UTF-8"));

        log.info("Message sent successfully!");

        channel.close();
        connection.close();

    }

    public static String receiveMessage(String queueName) throws Exception {

        String receivedMessage = "";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, false, false, false, null);
        log.info("Receiver waiting for messages....");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                //TODO use the message
            }
        };
        channel.basicConsume(queueName, true, consumer);

        return receivedMessage;
    }
}
