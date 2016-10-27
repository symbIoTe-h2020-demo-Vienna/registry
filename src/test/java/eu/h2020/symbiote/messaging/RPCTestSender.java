package eu.h2020.symbiote.messaging;

import com.google.common.io.Files;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Mael on 26/10/2016.
 */
public class RPCTestSender {
    private Connection connection;
    private Channel channel;
    private String RPC_REQUEST_QUEUE_NAME = "rpc_request_queue";
    private String RPC_REPLY_QUEUE_NAME = "rpc_reply_queue";
    private QueueingConsumer consumer;

    public RPCTestSender() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(RPC_REQUEST_QUEUE_NAME, false, false, false, null);
            channel.queueDeclare(RPC_REPLY_QUEUE_NAME, false, false, false, null);
            channel.basicQos(1);


            consumer = new QueueingConsumer(channel);
            channel.basicConsume(RPC_REPLY_QUEUE_NAME, true, consumer);
        } catch( IOException e ) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public String rpcCall(String message) throws IOException, InterruptedException {
        String response = "";
        String corrId = java.util.UUID.randomUUID().toString();

        channel.queueDeclare(RPC_REQUEST_QUEUE_NAME,false,false,false,null);
        channel.queueDeclare(RPC_REPLY_QUEUE_NAME,false,false,false,null);

        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(RPC_REPLY_QUEUE_NAME)
                .build();

        channel.basicPublish("", RPC_REQUEST_QUEUE_NAME, props, message.getBytes());
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                response = new String(delivery.getBody());
                break;
            }
        }
        return response;
    }

    public void close() throws Exception {
        connection.close();
    }

}
