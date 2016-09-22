package eu.h2020.symbiote.repository;

import eu.h2020.symbiote.model.Location;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by mateuszl on 22.09.2016.
 */

@RepositoryRestResource(collectionResourceRel = "location", path = "location")
public interface LocationRepository extends MongoRepository<Location, String> {
}
