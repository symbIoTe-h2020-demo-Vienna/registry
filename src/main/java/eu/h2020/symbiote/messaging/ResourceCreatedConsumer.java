package eu.h2020.symbiote.messaging;

import com.rabbitmq.client.Channel;
import eu.h2020.symbiote.model.Sensor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by Mael on 08/09/2016.
 *
 * for future use
 *
 */
public class ResourceCreatedConsumer extends SymbioteMessageConsumer<Sensor> {

    private static Log log = LogFactory.getLog(ResourceCreatedConsumer.class);

    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public ResourceCreatedConsumer(Channel channel) {
        super(channel);
    }


    /**
     * Method implementation used for actions with object passed in delivered message (Sensor in JSON in this case)
     *
     * @param deliveredObject
     */
    @Override
    protected void handleEventObject(Sensor deliveredObject) {
        //save (deliveredObject) in database - getId()
        System.out.println("Search received message about created resource with id: " + deliveredObject.getId());
    }
}
