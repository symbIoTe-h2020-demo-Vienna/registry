package eu.h2020.symbiote.controllers;

import eu.h2020.symbiote.model.Platform;
import eu.h2020.symbiote.model.Sensor;
import eu.h2020.symbiote.repository.LocationRepository;
import eu.h2020.symbiote.repository.PlatformRepository;
import eu.h2020.symbiote.repository.RepositoryManager;
import eu.h2020.symbiote.repository.SensorRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private LocationRepository locationRepo;

    @RequestMapping(value = "/cloud_api/platforms", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpEntity<String> addPlatform(@RequestBody Platform platform) {
        log.debug("Adding Platform");
        String savedPlatform = RepositoryManager.savePlatform(platform);
        return new ResponseEntity<String>(savedPlatform, HttpStatus.OK);
    }

    @RequestMapping(value = "/cloud_api/platforms/{platform_id}/resources", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpEntity<String> addSensor(@PathVariable(value = "platform_id") String platformId, @RequestBody List<Sensor> sensorsList) {
        log.debug("Adding Sensors...");
        RepositoryManager.saveSensors(platformId, sensorsList);
        String response = "Success";
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    /* HATEOAS LOCK
    @RequestMapping(value = {"/sensor", "/sensors", "/platform", "/platforms", "/cloud_api", "/cloud_api/sensors"})
    public
    @ResponseBody
    HttpEntity<String> error() {
        String message = "Error. Method not allowed";
        return new ResponseEntity<String>(message, HttpStatus.METHOD_NOT_ALLOWED);
    }
    */
}
