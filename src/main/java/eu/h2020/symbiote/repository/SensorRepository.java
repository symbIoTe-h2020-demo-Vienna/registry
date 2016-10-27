package eu.h2020.symbiote.repository;

import eu.h2020.symbiote.model.Sensor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * Created by mateuszl on 22.09.2016.
 */

@RepositoryRestResource(collectionResourceRel = "sensor", path = "sensor")
public interface SensorRepository extends MongoRepository<Sensor, String> {

    @Override
    @RestResource( exported = false )
    public Sensor save(Sensor s);

    @Override
    @RestResource( exported = false )
    public void delete(Sensor s);

}
