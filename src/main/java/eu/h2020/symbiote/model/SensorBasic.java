package eu.h2020.symbiote.model;

import java.net.URL;
import java.util.List;

/**
 * Created by Mael on 06/10/2016.
 */
public class SensorBasic extends AbstractSensor{

    private String id;

    private LocationBasic location;

    public SensorBasic() {
    }

    public SensorBasic(String name, String owner, String description, LocationBasic location, List<String> observedProperties, Platform platform, URL resourceURL) {
        setName(name);
        setOwner(owner);
        setDescription(description);
        this.location = location;
        setObservedProperties(observedProperties);
        setPlatform(platform);
        setResourceURL(resourceURL);
    }

    public SensorBasic(String id, String name, String owner, String description, LocationBasic location, List<String> observedProperties, Platform platform, URL resourceURL) {
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

    public LocationBasic getLocation() {
        return location;
    }

    public void setLocation(LocationBasic location) {
        this.location = location;
    }

}
