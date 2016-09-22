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
import java.math.BigInteger;

/**
 * Created by mateuszl on 22.09.2016.
 */

@CrossOrigin
@RepositoryRestController
public class SensorController {

    @Autowired
    private SensorRepository sensorRepo;

    @Autowired
    private PlatformRepository platformRepo;

    @RequestMapping(value="/platforms/{id}/sensor", method= RequestMethod.POST)
    public @ResponseBody
    HttpEntity<BigInteger> addPlatform(@PathVariable(value="id") BigInteger platformId, @RequestBody Sensor sensor) {
        System.out.println( "Adding Sensor");

        Platform platform = platformRepo.findOne(platformId.toString());

        sensor.setPlatform(platform);

        Sensor savedSensor = sensorRepo.save(sensor);
        System.out.println( "Platform added! : " + savedSensor + ". Sending message...");
        //Sending message
        RegistrationPublisher.getInstance().sendSensorCreatedMessage( savedSensor );

//        return new ResponseEntity<Sensor>( savedSensor, HttpStatus.OK);
        System.out.println("Response send with id: " + savedSensor.getId());
        return new ResponseEntity<BigInteger>( savedSensor.getId(), HttpStatus.OK);
    }
}
