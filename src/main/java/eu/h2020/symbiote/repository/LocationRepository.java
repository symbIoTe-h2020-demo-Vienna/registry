package eu.h2020.symbiote.repository;

import eu.h2020.symbiote.model.Location;
import eu.h2020.symbiote.model.Platform;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Created by mateuszl on 22.09.2016.
 */

@RepositoryRestResource(collectionResourceRel = "location", path = "location")
public interface LocationRepository extends MongoRepository<Location, String> {

    @Override
    @RestResource( exported = false )
    public Location save(Location s);

    @Override
    @RestResource( exported = false )
    public void delete(Location s);

}
