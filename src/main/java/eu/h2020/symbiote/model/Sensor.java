package eu.h2020.symbiote.model;

import java.math.BigInteger;

/**
 * Created by jawora on 22.09.16.
 */
public class Sensor {
    private BigInteger id;
    private Platform platform;
    private String name;
    private String owner;
    private String description;
    private Location location;
    private String observedProperty;

    public Sensor() {
    }

    public Sensor(Platform platform, String name, String owner, String description, Location location, String observedProperty) {
        this.platform = platform;
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.location = location;
        this.observedProperty = observedProperty;
    }

    public Sensor(BigInteger id, Platform platform, String name, String owner, String description, Location location, String observedProperty) {
        this.id = id;
        this.platform = platform;
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.location = location;
        this.observedProperty = observedProperty;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
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
}
