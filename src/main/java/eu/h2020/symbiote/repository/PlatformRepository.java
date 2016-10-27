package eu.h2020.symbiote.repository;

import com.mongodb.DBObject;
import eu.h2020.symbiote.model.Platform;
import eu.h2020.symbiote.model.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Component;

/**
 * Created by mateuszl on 22.09.2016.
 */
@RepositoryRestResource(collectionResourceRel = "platform", path = "platform")
public interface PlatformRepository extends MongoRepository<Platform, String> {

    @Override
    @RestResource( exported = false )
    public Platform save(Platform s);

    @Override
    @RestResource( exported = false )
    public void delete(Platform s);
}


