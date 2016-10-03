package eu.h2020.symbiote.messaging;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Mael on 07/09/2016.
 *
 * for future use
 *
 */
public class MessagingSubscriptions {

    private static String PLATFORM_REGISTER_REQUEST_RECEIVED = "PlatformReceived";
    private static String SENSOR_REGISTER_REQUEST_RECEIVED = "ResourceReceived";

    private static Log log = LogFactory.getLog(MessagingSubscriptions.class);

    /**
     * Use that method if you want to subscribe to receive messages
     *
     * @throws IOException
     * @throws TimeoutException
     */
    public static void subscribeForResourceAndPlatformRegistrationRequests() throws IOException, TimeoutException {
        subscribePlatformCreated(PLATFORM_REGISTER_REQUEST_RECEIVED);
        subscribeResourceCreated(SENSOR_REGISTER_REQUEST_RECEIVED);
    }

    /**
     * Basic consumer of messages thrown into queue named queueName, related to Platform object.
     *
     * @param queueName
     * @throws IOException
     * @throws TimeoutException
     */
    public static void subscribePlatformCreated( String queueName ) throws IOException, TimeoutException {
        Channel channel = getChannel(queueName);
        PlatformCreatedConsumer consumer = new PlatformCreatedConsumer(channel);
        channel.basicConsume(queueName, true, consumer);
    }

    /**
     * Basic consumer of messages thrown into queue named queueName, related to Resource (Sensor) object.
     *
     * @param queueName
     * @throws IOException
     * @throws TimeoutException
     */
    public static void subscribeResourceCreated(String queueName ) throws IOException, TimeoutException {
        Channel channel = getChannel(queueName);
        ResourceCreatedConsumer consumer = new ResourceCreatedConsumer(channel);
        channel.basicConsume(queueName, true, consumer);
    }

    /**
     * Returns channel for rabbit messaging
     *
     * @param queueName
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    private static Channel getChannel( String queueName ) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, false, false, false, null);
        log.info("Receiver waiting for messages in queue " + queueName + " ....");
        return channel;
    }
}
