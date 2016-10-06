package eu.h2020.symbiote.messaging;

//import com.rabbitmq.client.*;

import com.rabbitmq.client.*;
import eu.h2020.symbiote.repository.RepositoryManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by mateuszl on 03.10.2016.
 */
public class RPCReceiver {

    public static Log log = LogFactory.getLog(RPCReceiver.class);

    private static final String RPC_REQUEST_QUEUE_NAME = "rpc_request_queue";
    private static final String RPC_REPLY_QUEUE_NAME = "rpc_reply_queue";

    public static void consumeRPCMessageAndResponse() throws IOException, TimeoutException, InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(RPC_REQUEST_QUEUE_NAME, false, false, false, null);
        channel.queueDeclare(RPC_REPLY_QUEUE_NAME, false, false, false, null);

        channel.basicQos(1);

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(RPC_REQUEST_QUEUE_NAME, false, consumer);

        log.info(" [x] Awaiting RPC requests");

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();

            AMQP.BasicProperties props = delivery.getProperties();
            AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                    .Builder()
                    .correlationId(props.getCorrelationId())
                    .build();

            String receivedRegistrationObjectInJSON = new String(delivery.getBody());
            log.info("[][] RPC Message Received :\n" + receivedRegistrationObjectInJSON);

            String response = RepositoryManager.saveToDatabase(receivedRegistrationObjectInJSON);
            //saveToDatabase returns JSON with string
            log.info(response);
            log.info("[][] RPC Response with message sent back \n");
            channel.basicPublish("", props.getReplyTo(), replyProps, response.getBytes());

            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }

}
