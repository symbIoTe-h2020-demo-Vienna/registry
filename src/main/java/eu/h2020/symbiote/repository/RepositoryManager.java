package eu.h2020.symbiote.repository;

import com.google.gson.Gson;
import eu.h2020.symbiote.controllers.ResourceController;
import eu.h2020.symbiote.messaging.RegistrationPublisher;
import eu.h2020.symbiote.model.Location;
import eu.h2020.symbiote.model.Platform;
import eu.h2020.symbiote.model.RegistrationObject;
import eu.h2020.symbiote.model.Sensor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mateuszl on 03.10.2016.
 */
public class RepositoryManager {

    public static Log log = LogFactory.getLog(ResourceController.class);

    @Autowired
    private static SensorRepository sensorRepo;

    @Autowired
    private static PlatformRepository platformRepo;

    @Autowired
    private static LocationRepository locationRepo;

    public static String saveToDatabase(String objectInJson){
        String response ="";
        Gson gson = new Gson();
        RegistrationObject registrationObject = gson.fromJson(objectInJson, RegistrationObject.class);
        System.out.println("[][][] " + registrationObject.toString());

        switch (registrationObject.getType()){
            case PLATFORM:
                log.debug("Adding Platform");
                //TODO check if provided platform already exists
                try {
                    Platform platform = gson.fromJson(registrationObject.getRegistrationObjectBody(),Platform.class);
                    savePlatform(platform);
                }
                catch (Exception e){
                    log.error("Error occured during saving to db", e);
                }
                break;
            case SENSOR:
                try {
                    Sensor[] sensorsArray = gson.fromJson(registrationObject.getRegistrationObjectBody(),Sensor[].class);
                    List<Sensor> sensorsList = Arrays.asList(sensorsArray);
                    saveSensors(registrationObject.getParentID(),sensorsList);
                    response = "multiple IDs";
                }
                catch (Exception e){
                    log.error("Error occured during saving to db", e);
                }                break;
        }
        return response;
    }

    public static String savePlatform(Platform platform){
        String response = "";

        //TODO check if provided platform already exists

        Platform savedPlatform = platformRepo.save(platform);
        log.debug("Platform added! : " + savedPlatform + ". Sending message...");
        //Sending message
        RegistrationPublisher.getInstance().sendPlatformCreatedMessage(savedPlatform);
        log.debug("Response send with id: " + savedPlatform.getId());

        return response;
    };

    public static List<String> saveSensors(String platformId, List<Sensor> sensorsList){
        Platform foundPlatform = platformRepo.findOne(platformId);
        List<String> response = new ArrayList<>();
        List<Sensor> savedSensorsList = new ArrayList<>();
        if (foundPlatform != null) {
            for (Sensor s:sensorsList) {
                s.setPlatform(foundPlatform);
                Location location = s.getLocation();
                locationRepo.save(location);
                s.setLocation(location);
                Sensor savedSensor = sensorRepo.save(s);
                log.debug("Sensor added! : " + s.getId() + ". Sending message...");
                //Sending message
                RegistrationPublisher.getInstance().sendSensorCreatedMessage(s);
                log.debug("Response send with id: " + s.getId());
                savedSensorsList.add(s); //TODO needed or not?
                response.add(s.getId());
            }
            return response;
        } else {
            log.debug("Platform with provided ID not found!");
            return response;
        }
    }
}