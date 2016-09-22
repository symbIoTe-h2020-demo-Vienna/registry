package eu.h2020.symbiote.controllers;

import eu.h2020.symbiote.messaging.RegistrationPublisher;
import eu.h2020.symbiote.model.Platform;
import eu.h2020.symbiote.repository.PlatformRepository;
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
public class PlatformController {

    @Autowired
    private PlatformRepository repo;

    @RequestMapping(value="/platform", method= RequestMethod.POST)
    public @ResponseBody
    HttpEntity<BigInteger> addPlatform(@RequestBody Platform platform) {
        System.out.println( "Adding Platform");
        Platform savedPlatform = repo.save(platform);

        System.out.println( "Platform added! : " + savedPlatform + ". Sending message...");
        //Sending message
        RegistrationPublisher.getInstance().sendPlatformCreatedMessage( savedPlatform );

//        return new ResponseEntity<Platform>( savedPlatform, HttpStatus.OK);
        System.out.println("Response send with id: " + savedPlatform.getId());
        return new ResponseEntity<BigInteger>( savedPlatform.getId(), HttpStatus.OK);
    }
}
