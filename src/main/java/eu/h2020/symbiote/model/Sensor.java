package eu.h2020.symbiote.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by jawora on 22.09.16.
 */
@Document
public class Sensor {

    @Id
    private String id;
    private String name;
    private String owner;
    private String description;
    private Location location;
    private String observedProperty;
    @DBRef
    private Platform platform;

    public Sensor() {
    }

    public Sensor(String name, String owner, String description, Location location, String observedProperty, Platform platform) {
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.location = location;
        this.observedProperty = observedProperty;
        this.platform = platform;
    }

    public Sensor(String id, String name, String owner, String description, Location location, String observedProperty, Platform platform) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.location = location;
        this.observedProperty = observedProperty;
        this.platform = platform;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getObservedProperty() {
        return observedProperty;
    }

    public void setObservedProperty(String observedProperty) {
        this.observedProperty = observedProperty;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }
}
