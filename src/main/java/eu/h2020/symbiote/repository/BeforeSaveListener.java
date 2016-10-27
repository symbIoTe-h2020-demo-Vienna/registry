package eu.h2020.symbiote.repository;

import eu.h2020.symbiote.model.Platform;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;

@Component
public class BeforeSaveListener extends AbstractMongoEventListener<Platform> {

    @Override
    public void onBeforeSave(BeforeSaveEvent<Platform> event ) {
        System.out.println( "Caught before save event!!!!");
    }
}