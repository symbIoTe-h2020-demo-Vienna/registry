package eu.h2020.symbiote.model;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

/**
 * Created by Mael on 06/10/2016.
 */
public class SensorFactory {

    public static Sensor createFromBasicSensor( SensorBasic basic ) {
        GeoJsonPoint point = new GeoJsonPoint(basic.getLocation().getLongitude(),basic.getLocation().getLatitude());
        Location loc = new Location(basic.getLocation().getName(),basic.getLocation().getDescription(),point,basic.getLocation().getAltitude());
        Sensor sensor = new Sensor(basic.getName(),basic.getOwner(),basic.getDescription(),loc,basic.getObservedProperties(),basic.getPlatform(),basic.getResourceURL());
        return sensor;
    }

}
