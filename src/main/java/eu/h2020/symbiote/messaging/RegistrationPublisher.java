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
    private static String SENSOR_CREATED_QUEUE = "SensorCreated";
//    private static String MAPPING_CREATED_QUEUE = "MappingCreated";


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

    public void sendPlatformCreatedMessage( Platform platform ) {
        try {
//            CreatedPlatform createdPlatform = new CreatedPlatform(platform.getId(),platform.getInstance(),platform.getFormat(),modelId);
            RabbitMessager.sendMessage(PLATFORM_CREATED_QUEUE, platform);
            log.info("Platform " + platform.getId() + " created message send successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendSensorCreatedMessage(Sensor sensor ) {
        try {
//            OntologyModel ontologyModel = new OntologyModel(modelId,model,format);
            RabbitMessager.sendMessage(SENSOR_CREATED_QUEUE, sensor);
            log.info("Sensor " + sensor.getId() + " created message send successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/*
    public void sendMappingCreatedMessage(Mapping savedMapping) {
        try {

            RabbitMessager.sendMessage(MAPPING_CREATED_QUEUE, savedMapping);
            log.info("Mapping " + savedMapping.getId() + " created message send successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */
}
