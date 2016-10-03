package eu.h2020.symbiote.messaging;

import com.rabbitmq.client.Channel;
import eu.h2020.symbiote.model.Platform;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by Mael on 07/09/2016.
 *
 *  for future use
 */
public class PlatformCreatedConsumer extends SymbioteMessageConsumer<Platform> {

    private static Log log = LogFactory.getLog(PlatformCreatedConsumer.class);

    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public PlatformCreatedConsumer(Channel channel) {
        super(channel);
    }

    /**
     * Method implementation used for actions with object passed in delivered message (Platform in JSON in this case)
     *
     * * @param deliveredObject
     */
    @Override
    protected void handleEventObject(Platform deliveredObject) {
        //save (deliveredObject) in database - getId()
        System.out.println("Search received message about created platform with id: " + deliveredObject.getId());
    }
}
