package eu.h2020.symbiote.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

/**
 * Created by jawora on 22.09.16.
 */
public class LocationBasic {

    @Id
    private String id;
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private Double altitude;

    public LocationBasic() {
    }

    public LocationBasic(String name, String description, Double latitude, Double longitude, Double altitude) {
        this.name = name;
        this.description = description;
        this.altitude = altitude;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LocationBasic(String id, String name, String description, Double latitude, Double longitude, Double altitude) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.altitude = altitude;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }


}
