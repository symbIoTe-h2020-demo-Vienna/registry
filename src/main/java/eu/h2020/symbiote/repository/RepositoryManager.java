package eu.h2020.symbiote.repository;

import com.google.gson.Gson;
import eu.h2020.symbiote.messaging.RegistrationPublisher;
import eu.h2020.symbiote.model.Location;
import eu.h2020.symbiote.model.Platform;
import eu.h2020.symbiote.model.RegistrationObject;
import eu.h2020.symbiote.model.Sensor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mateuszl on 03.10.2016.
 */
@Component
public class RepositoryManager {

    public static Log log = LogFactory.getLog(RepositoryManager.class);

    @Autowired
    public RepositoryManager(SensorRepository sensorRepo, PlatformRepository platformRepo, LocationRepository locationRepo) {
        this.sensorRepo = sensorRepo;
        this.platformRepo = platformRepo;
        this.locationRepo = locationRepo;
    }

    private static SensorRepository sensorRepo;

    private static PlatformRepository platformRepo;

    private static LocationRepository locationRepo;


    public static List<String> saveToDatabase(String objectInJson) {
        List<String> response = new ArrayList<>();
        Gson gson = new Gson();
        RegistrationObject registrationObject = gson.fromJson(objectInJson, RegistrationObject.class);
        System.out.println("[][][] " + registrationObject.toString());

        switch (registrationObject.getType()) {
            case PLATFORM:
                log.debug("Adding Platform");
                try {
                    Platform platform = gson.fromJson(registrationObject.getRegistrationObjectBody(), Platform.class);
                    String saved ="";
                    if (platform!=null) {
                        saved = savePlatform(platform);
                        log.info("Platform with id: " + platform.getId() + " saved !");
                    }
                    response.add(saved);
                } catch (Exception e) {
                    log.error("Error occured during Platform saving to db", e);
                }
                break;
            case SENSOR:
                try {
                    Sensor[] sensorsArray = gson.fromJson(registrationObject.getRegistrationObjectBody(), Sensor[].class);
                    List<Sensor> sensorsList = new ArrayList<>(Arrays.asList(sensorsArray));
                    response = saveSensors(registrationObject.getParentID(), sensorsList);
                    log.info("Sensors saved !");
                } catch (Exception e) {
                    log.error("Error occured during Sensor saving to db", e);
                }
                break;
        }
        return response;
    }

    public static String savePlatform(Platform platform) {
        String response = "";

        //TODO check if provided platform already exists

        Platform savedPlatform = platformRepo.save(platform);
        log.debug("Platform added! : " + savedPlatform + ". Sending message...");
        //Sending message
        RegistrationPublisher.getInstance().sendPlatformCreatedMessage(savedPlatform);
        response = savedPlatform.getId();
        log.debug("Response send with id: " + response);

        return response;
    }

    public static List<String> saveSensors(String platformId, List<Sensor> sensorsList) {
        Platform foundPlatform = platformRepo.findOne(platformId);
        List<String> response = new ArrayList<>();
        List<Sensor> savedSensorsList = new ArrayList<>();
        if (foundPlatform != null) {
            for (Sensor s : sensorsList) {
                s.setPlatform(foundPlatform);
                Location location = s.getLocation();
                Location savedLocation = locationRepo.save(location);
                s.setLocation(savedLocation);
                Sensor savedSensor = sensorRepo.save(s);
                log.debug("Sensor added! : " + savedSensor.getId() + ". Sending message...");
                //Sending message
                RegistrationPublisher.getInstance().sendSensorCreatedMessage(s);
                log.debug("Response send with id: " + savedSensor.getId());
                savedSensorsList.add(savedSensor);
                response.add(savedSensor.getId());
            }
            return response;
        } else {
            log.debug("Platform with provided ID not found!");
            return response;
        }
    }
}