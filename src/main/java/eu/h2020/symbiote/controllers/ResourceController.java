package eu.h2020.symbiote.controllers;

import eu.h2020.symbiote.messaging.RegistrationPublisher;
import eu.h2020.symbiote.model.Platform;
import eu.h2020.symbiote.model.Sensor;
import eu.h2020.symbiote.repository.PlatformRepository;
import eu.h2020.symbiote.repository.SensorRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by mateuszl on 22.09.2016.
 */
@CrossOrigin
@RestController
public class ResourceController {

    public static Log log = LogFactory.getLog(ResourceController.class);

    @Autowired
    private SensorRepository sensorRepo;

    @Autowired
    private PlatformRepository platformRepo;

    @RequestMapping(value = "/cloud_api/platforms", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpEntity<String> addPlatform(@RequestBody Platform platform) {
        log.debug("Adding Platform");

        //TODO check if provided platform already exists

        Platform savedPlatform = platformRepo.save(platform);
        log.debug("Platform added! : " + savedPlatform + ". Sending message...");
        //Sending message
        RegistrationPublisher.getInstance().sendPlatformCreatedMessage(savedPlatform);

        log.debug("Response send with id: " + savedPlatform.getId());
        return new ResponseEntity<String>(savedPlatform.getId(), HttpStatus.OK);
    }

    @RequestMapping(value = "/cloud_api/platforms/{platform_id}/resources", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpEntity<String> addSensor(@PathVariable(value = "platform_id") String platformId, @RequestBody Sensor sensor) {
        log.debug("Adding Sensor...");
        Platform foundPlatform = platformRepo.findOne(platformId);

        if (foundPlatform != null) {
            sensor.setPlatform(foundPlatform);
            Sensor savedSensor = sensorRepo.save(sensor);
            log.debug("Sensor added! : " + savedSensor + ". Sending message...");
            //Sending message
            RegistrationPublisher.getInstance().sendSensorCreatedMessage(savedSensor);

            log.debug("Response send with id: " + savedSensor.getId());
            return new ResponseEntity<String>(savedSensor.getId(), HttpStatus.OK);
        } else {
            log.debug("Platform with provided ID not found!");
            return new ResponseEntity<String>("Platform with provided ID not found!", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = {"/sensor", "/sensors", "/platform", "/platforms", "/cloud_api", "/cloud_api/sensors"})
    public
    @ResponseBody
    HttpEntity<String> error() {
        String message = "Error. Method not allowed";
        return new ResponseEntity<String>(message, HttpStatus.METHOD_NOT_ALLOWED);
    }

}
