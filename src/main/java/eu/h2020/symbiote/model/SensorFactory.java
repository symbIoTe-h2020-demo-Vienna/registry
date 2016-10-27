package eu.h2020.symbiote.model;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

/**
 * Created by Mael on 06/10/2016.
 */
public class SensorFactory {

    public static Sensor createFromBasicSensor( SensorBasic basic ) {
        GeoJsonPoint point = new GeoJsonPoint(basic.getLocation().getLongitude(),basic.getLocation().getLatitude());
        Location loc = new Location(basic.getLocation().getName(),basic.getLocation().getDescription(),point,basic.getLocation().getAltitude());
        Sensor sensor = new Sensor(basic.getId(),basic.getName(),basic.getOwner(),basic.getDescription(),loc,basic.getObservedProperties(),basic.getPlatform(),basic.getResourceURL());
        return sensor;
    }

    public static SensorBasic createFromSensor( Sensor normal ) {
        LocationBasic basicLocation = new LocationBasic(normal.getLocation().getId(),normal.getLocation().getName(),normal.getLocation().getDescription(),normal.getLocation().getPoint().getY(),normal.getLocation().getPoint().getX(),normal.getLocation().getAltitude());
        SensorBasic sensorBasic = new SensorBasic(normal.getId(),normal.getName(),normal.getOwner(),normal.getDescription(),basicLocation, normal.getObservedProperties(),normal.getPlatform(),normal.getResourceURL());
        return sensorBasic;
    }

}
