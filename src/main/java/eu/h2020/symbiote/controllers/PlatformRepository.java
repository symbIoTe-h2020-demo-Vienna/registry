package eu.h2020.symbiote.controllers;

import eu.h2020.symbiote.messaging.RegistrationPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by mateuszl on 22.09.2016.
 */
@RepositoryRestResource(collectionResourceRel = "platform", path = "platform")
interface PlatformRepository extends MongoRepository<Platform, String> {

    @Autowired
    private PlatformRepository repo;

    @RequestMapping(value="/platform", method= RequestMethod.POST)
    public @ResponseBody
    HttpEntity<Platform> addPlatform(@RequestBody Platform platform) {
        System.out.println( "Adding Platform");
        Platform savedPlatform = repo.save(platform);
        System.out.println( "Platform added! : " + savedPlatform + ". Sending message...");
        //Sending message
        RegistrationPublisher.getInstance().sendPlatformCreatedMessage( savedPlatform );
        return new ResponseEntity<Platform>( savedPlatform, HttpStatus.OK);
    }
}