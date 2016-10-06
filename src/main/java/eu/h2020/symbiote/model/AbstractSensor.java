package eu.h2020.symbiote.model;

import java.net.URL;
import java.util.List;

/**
 * Created by Mael on 06/10/2016.
 */
public abstract class AbstractSensor {

    private String name;
    private String owner;
    private String description;
    private List<String> observedProperties;
    private Platform platform;
    private URL resourceURL;


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

    public List<String> getObservedProperties() {
        return observedProperties;
    }

    public void setObservedProperties(List<String> observedProperties) {
        this.observedProperties = observedProperties;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public URL getResourceURL() {
        return resourceURL;
    }

    public void setResourceURL(URL resourceURL) {
        this.resourceURL = resourceURL;
    }
}
