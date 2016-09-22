package eu.h2020.symbiote.controllers;

import eu.h2020.symbiote.messaging.RegistrationPublisher;
import eu.h2020.symbiote.model.Platform;
import eu.h2020.symbiote.model.Sensor;
import eu.h2020.symbiote.repository.PlatformRepository;
import eu.h2020.symbiote.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by mateuszl on 22.09.2016.
 */

@CrossOrigin(methods = RequestMethod.POST)
@RepositoryRestController
public class SensorController {

    @Autowired
    private SensorRepository sensorRepo;

    @Autowired
    private PlatformRepository platformRepo;

    @RequestMapping(value="/platform/{id}/sensors", method= RequestMethod.POST)
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
}
