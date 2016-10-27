package eu.h2020.symbiote.repository;

import com.google.gson.Gson;
import eu.h2020.symbiote.messaging.RegistrationPublisher;
import eu.h2020.symbiote.model.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mateuszl on 03.10.2016.
 */
@Component
public class RepositoryManager {

    public static Log log = LogFactory.getLog(RepositoryManager.class);
    private SensorRepository sensorRepo;
    private PlatformRepository platformRepo;
    private LocationRepository locationRepo;
    private RegistrationPublisher publisher;

    @Autowired
    public RepositoryManager(SensorRepository sensorRepo, PlatformRepository platformRepo, LocationRepository locationRepo, RegistrationPublisher publisher) {
        this.sensorRepo = sensorRepo;
        this.platformRepo = platformRepo;
        this.locationRepo = locationRepo;
        this.publisher = publisher;
    }

    /**
     * @param regObjectInJson
     * @return JSON id String - with either List<Sensor> or PlatformId
     */
    public String saveToDatabase(String regObjectInJson) {
        String response = "";
        Gson gson = new Gson();
        RegistrationObject registrationObject = gson.fromJson(regObjectInJson, RegistrationObject.class);
        System.out.println("[] Registration Object Body: \n" + registrationObject.getRegistrationObjectBody());

        switch (registrationObject.getType()) {
            case PLATFORM:
                log.debug("Adding Platform");
                try {
                    Platform platform = gson.fromJson(registrationObject.getRegistrationObjectBody(), Platform.class);
                    String savedPlatformId = "";
                    if (platform != null) {
                        savedPlatformId = savePlatform(platform);
                        //savePlatform returns JSON already
                        log.info("Platform with id: " + savedPlatformId + " saved !");
                    }
                    response = "{\"id\": \"" + savedPlatformId + "\"}";
//                    response = "[" + savedPlatformId + "]";
                } catch (Exception e) {
                    log.error("Error occured during Platform saving to db", e);
                }
                break;
            case SENSOR:
                try {
                    SensorBasic[] sensorsArray = gson.fromJson(registrationObject.getRegistrationObjectBody(), SensorBasic[].class);
                    List<SensorBasic> sensorsList = new ArrayList<>(Arrays.asList(sensorsArray));

                    List<Sensor> savedSensorsWithIDsList = saveSensors(registrationObject.getParentID(), transformBasicToInternalSensors(sensorsList));
                    StringBuilder sb = new StringBuilder();
                    sb.append("[");
                    Iterator<Sensor> iterator = savedSensorsWithIDsList.iterator();
                    while (iterator.hasNext()) {
                        Sensor sensor = iterator.next();
                        String s = gson.toJson(SensorFactory.createFromSensor(sensor));
                        sb.append(s);
                        if (iterator.hasNext()) {
                            sb.append(",");
                        }
                    }
                    sb.append("]");
                    response = sb.toString();
                    System.out.println(response);
                    log.info("Sensors saved !");
                } catch (Exception e) {
                    log.error("Error occured during Sensor saving to db", e);
                }
                break;
        }
        return response; //JSON String
    }

    /**
     * @param platform
     * @return JSON with String with ID of saved Platform
     */
    public String savePlatform(Platform platform) {
        //TODO check if provided platform already exists
        Platform savedPlatform = platformRepo.save(platform);
        log.info("Platform added! : " + savedPlatform + ". Sending message...");
        //Sending message
        publisher.sendPlatformCreatedMessage(savedPlatform);
        String savedPlatformId = savedPlatform.getId();
        log.info("Response send with id: " + savedPlatformId);
        return savedPlatformId;
    }

    /**
     * @param platformId
     * @param sensorsList
     * @return String with JSON of added resources (containing IDs)
     */
    public List<Sensor> saveSensors(String platformId, List<Sensor> sensorsList) {
        Platform foundPlatform = platformRepo.findOne(platformId);
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
                s.setId(savedSensor.getId());
                publisher.sendSensorCreatedMessage(s);
                log.info("Response send with id: " + savedSensor.getId());
//                Gson gson = new Gson();
//                savedSensorsJsonsList.add(gson.toJson(s));
                savedSensorsList.add(savedSensor);
            }
        }
        return savedSensorsList;
    }

    public List<Sensor> transformBasicToInternalSensors( List<SensorBasic> basicSensors ) {
        List<Sensor> resultList = new ArrayList<>();
        if( basicSensors != null ) {
            resultList = basicSensors.stream().map( p -> SensorFactory.createFromBasicSensor(p)).collect(Collectors.toList());
        }
        return resultList;
    }
}