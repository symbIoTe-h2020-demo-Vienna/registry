package eu.h2020.symbiote.model;

import java.math.BigInteger;

/**
 * Created by jawora on 22.09.16.
 */
public class Location {
    private BigInteger id;
    private String name;
    private String description;
    private Double longitude;
    private Double latitude;
    private Double altitude;

    public Location() {
    }

    public Location(String name, String description, Double longitude, Double latitude, Double altitude) {
        this.name = name;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
    }

    public Location(BigInteger id, String name, String description, Double longitude, Double latitude, Double altitude) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }
}
