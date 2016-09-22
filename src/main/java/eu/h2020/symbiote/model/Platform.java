package eu.h2020.symbiote.model;

import java.math.BigInteger;
import java.net.URL;

/**
 * Created by jawora on 22.09.16.
 */
public class Platform {
    private BigInteger id;
    private String owner;
    private String name;
    private String type;
    private URL resourceAccessProxyUrl;

    public Platform() {

    }

    public Platform(String owner, String name, String type, URL resourceAccessProxyUrl) {
        this.owner = owner;
        this.name = name;
        this.type = type;
        this.resourceAccessProxyUrl = resourceAccessProxyUrl;
    }

    public Platform(BigInteger id, String owner, String name, String type, URL resourceAccessProxyUrl) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.type = type;
        this.resourceAccessProxyUrl = resourceAccessProxyUrl;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public URL getResourceAccessProxyUrl() {
        return resourceAccessProxyUrl;
    }

    public void setResourceAccessProxyUrl(URL resourceAccessProxyUrl) {
        this.resourceAccessProxyUrl = resourceAccessProxyUrl;
    }
}
