package eu.h2020.symbiote.messaging;

import eu.h2020.symbiote.model.Platform;
import eu.h2020.symbiote.model.Sensor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by mateuszl on 22.09.2016.
 */

public class RegistrationPublisher {

    private static String PLATFORM_CREATED_QUEUE = "PlatformCreated";
    private static String RESOURCE_CREATED_QUEUE = "ResourceCreated";
    private static String CRAM_PLATFORM_CREATED_QUEUE = "CramPlatformCreated";
    private static String CRAM_RESOURCE_CREATED_QUEUE = "CramResourceCreated";

    private static Log log = LogFactory.getLog(RegistrationPublisher.class);

    private static RegistrationPublisher singleton;

    static {
        singleton = new RegistrationPublisher();
    }

    private RegistrationPublisher() {
    }

    public static RegistrationPublisher getInstance() {
        return singleton;
    }

    public void sendPlatformCreatedMessage(Platform platform) {
        try {
            RabbitMessager.sendMessage(PLATFORM_CREATED_QUEUE, platform);
            RabbitMessager.sendMessage(CRAM_PLATFORM_CREATED_QUEUE, platform);
            log.info("Platform " + platform.getId() + " created message send successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendSensorCreatedMessage(Sensor sensor) {
        try {
            RabbitMessager.sendMessage(RESOURCE_CREATED_QUEUE, sensor);
            RabbitMessager.sendMessage(CRAM_RESOURCE_CREATED_QUEUE, sensor);
            log.info("Sensor " + sensor.getId() + " created message send successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
