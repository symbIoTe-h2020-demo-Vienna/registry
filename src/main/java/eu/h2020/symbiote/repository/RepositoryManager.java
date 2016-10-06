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


    /**
     * @param regObjectInJson
     * @return JSON id String - with either List<Sensor> or PlatformId
     */
    public static String saveToDatabase(String regObjectInJson) {
        String response="";
        Gson gson = new Gson();
        RegistrationObject registrationObject = gson.fromJson(regObjectInJson, RegistrationObject.class);
        System.out.println("[] Registration Object Body: \n" + registrationObject.getRegistrationObjectBody());

        switch (registrationObject.getType()) {
            case PLATFORM:
                log.debug("Adding Platform");
                try {
                    Platform platform = gson.fromJson(registrationObject.getRegistrationObjectBody(), Platform.class);
                    String savedPlatformId ="";
                    if (platform!=null) {
                        savedPlatformId = savePlatform(platform);
                        //savePlatform returns JSON already
                        log.info("Platform with id: " + savedPlatformId + " saved !");
                    }
                    response = savedPlatformId;
                } catch (Exception e) {
                    log.error("Error occured during Platform saving to db", e);
                }
                break;
            case SENSOR:
                try {
                    Sensor[] sensorsArray = gson.fromJson(registrationObject.getRegistrationObjectBody(), Sensor[].class);
                    List<Sensor> sensorsList = new ArrayList<>(Arrays.asList(sensorsArray));
                    response = saveSensors(registrationObject.getParentID(), sensorsList);
                    //saveSensor returns JSON  String already
                    log.info("Sensors saved !");
                } catch (Exception e) {
                    log.error("Error occured during Sensor saving to db", e);
                }
                break;
        }
        return response; //JSON with String
    }

    /**
     * @param platform
     * @return JSON with String with ID of saved Platform
     */
    public static String savePlatform(Platform platform) {
        //TODO check if provided platform already exists

        Platform savedPlatform = platformRepo.save(platform);
        log.info("Platform added! : " + savedPlatform + ". Sending message...");
        //Sending message
        RegistrationPublisher.getInstance().sendPlatformCreatedMessage(savedPlatform);
        String savedPlatformId = savedPlatform.getId();
        log.info("Response send with id: " + savedPlatformId);

        Gson gson = new Gson();
        String savedPlatformIdInJson = gson.toJson(savedPlatformId);
        return savedPlatformIdInJson;
    }

    /**
     * @param platformId
     * @param sensorsList
     * @return String with JSON of added resources (containing IDs)
     */
    public static String saveSensors(String platformId, List<Sensor> sensorsList) {
        Platform foundPlatform = platformRepo.findOne(platformId);
//        List<String> savedSensorsIDsList = new ArrayList<>();
        List<Sensor> savedSensorsList = new ArrayList<>();
        if (foundPlatform != null) {
            for (Sensor s : sensorsList) {
                s.setPlatform(foundPlatform);
                Location location = s.getLocation();
                Location savedLocation = locationRepo.save(location);
                s.setLocation(savedLocation);
                Sensor savedSensor = sensorRepo.save(s);
                log.info("Sensor added! : " + savedSensor.getId() + ". Sending message...");
                //Sending message
                RegistrationPublisher.getInstance().sendSensorCreatedMessage(s);
                log.info("Response send with id: " + savedSensor.getId());
                savedSensorsList.add(savedSensor);
//                savedSensorsIDsList.add(savedSensor.getId());
            }
            Gson gson = new Gson();
            String savedSensorsListInJson = gson.toJson(savedSensorsList);
            return savedSensorsListInJson;
        } else {
            log.info("Platform with provided ID not found!");
            return "FAIL! Platform with provided ID not found!";
        }
    }
}