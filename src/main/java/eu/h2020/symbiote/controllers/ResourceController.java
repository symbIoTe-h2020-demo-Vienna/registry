package eu.h2020.symbiote.controllers;

import eu.h2020.symbiote.messaging.RegistrationPublisher;
import eu.h2020.symbiote.model.Platform;
import eu.h2020.symbiote.model.Sensor;
import eu.h2020.symbiote.repository.PlatformRepository;
import eu.h2020.symbiote.repository.SensorRepository;
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

    @Autowired
    private SensorRepository sensorRepo;

    @Autowired
    private PlatformRepository platformRepo;

    @RequestMapping(value="/register/platform", method= RequestMethod.POST)
    public @ResponseBody
    HttpEntity<String> addPlatform(@RequestBody Platform platform) {
        System.out.println( "Adding Platform");
        Platform savedPlatform = platformRepo.save(platform);

        System.out.println( "Platform added! : " + savedPlatform + ". Sending message...");
        //Sending message
        RegistrationPublisher.getInstance().sendPlatformCreatedMessage( savedPlatform );

//        return new ResponseEntity<Platform>( savedPlatform, HttpStatus.OK);
        System.out.println("Response send with id: " + savedPlatform.getId());
        return new ResponseEntity<String>( savedPlatform.getId(), HttpStatus.OK);
    }

    @RequestMapping(value="/register/platform/{id}/resources", method= RequestMethod.POST)
    public @ResponseBody
    HttpEntity<String> addSensor(@PathVariable(value="id") String platformId, @RequestBody Sensor sensor) {
        System.out.println( "Adding Sensor");

        Platform foundPlatform = platformRepo.findOne(platformId.toString());

        sensor.setPlatform(foundPlatform);

        Sensor savedSensor = sensorRepo.save(sensor);
        System.out.println( "Sensor added! : " + savedSensor + ". Sending message...");
        //Sending message
        RegistrationPublisher.getInstance().sendSensorCreatedMessage( savedSensor );

//        return new ResponseEntity<Sensor>( savedSensor, HttpStatus.OK);
        System.out.println("Response send with id: " + savedSensor.getId());
        return new ResponseEntity<String>( savedSensor.getId(), HttpStatus.OK);
    }

    @RequestMapping(value={"/sensor","/platform"})
    public @ResponseBody
    HttpEntity<String> error() {
        String message = "Error. Method not allowed";
        return new ResponseEntity<String>( message, HttpStatus.METHOD_NOT_ALLOWED);
    }

}
