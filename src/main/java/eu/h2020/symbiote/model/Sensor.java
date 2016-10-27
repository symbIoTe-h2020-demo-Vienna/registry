package eu.h2020.symbiote.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.net.URL;
import java.util.List;

/**
 * Created by jawora on 22.09.16.
 */
@Document
public class Sensor extends AbstractSensor {

    @Id
    private String id;

    private Location location;

    public Sensor() {
    }

    public Sensor(String name, String owner, String description, Location location, List<String> observedProperties, Platform platform, URL resourceURL) {
        setName(name);
        setOwner(owner);
        setDescription(description);
        this.location = location;
        setObservedProperties(observedProperties);
        setPlatform(platform);
        setResourceURL(resourceURL);
    }

    public Sensor(String id, String name, String owner, String description, Location location, List<String> observedProperties, Platform platform, URL resourceURL) {
        this.id = id;
        setName(name);
        setOwner(owner);
        setDescription(description);
        this.location = location;
        setObservedProperties(observedProperties);
        setPlatform(platform);
        setResourceURL(resourceURL);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);

    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

}
