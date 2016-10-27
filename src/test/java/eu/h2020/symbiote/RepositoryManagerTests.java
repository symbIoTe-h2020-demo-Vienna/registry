package eu.h2020.symbiote;

import eu.h2020.symbiote.messaging.RegistrationPublisher;
import eu.h2020.symbiote.model.*;
import eu.h2020.symbiote.repository.LocationRepository;
import eu.h2020.symbiote.repository.PlatformRepository;
import eu.h2020.symbiote.repository.RepositoryManager;
import eu.h2020.symbiote.repository.SensorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Mael on 26/10/2016.
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = RegistryApplication.class, properties = "eureka.client.enabled=false")
//@ContextConfiguration(classes = {TestMongoConfig.class})
public class RepositoryManagerTests {

    RepositoryManager manager;

    LocationRepository mockedLocationRepo;
    PlatformRepository mockedPlatformRepo;
    SensorRepository mockedSensorRepo;
    RegistrationPublisher mockedPublisher;
    Platform platformToSave;
    Location location;
    Sensor sensorToSave;

    @Before
    public void init() {
        //Creates mocked repos
        mockedSensorRepo = Mockito.mock(SensorRepository.class);
        mockedPlatformRepo = Mockito.mock(PlatformRepository.class);
        mockedLocationRepo = Mockito.mock(LocationRepository.class);
        mockedPublisher = Mockito.mock(RegistrationPublisher.class);

        platformToSave = Mockito.mock(Platform.class);
        sensorToSave = Mockito.mock(Sensor.class);
        location = Mockito.mock(Location.class);

        manager = new RepositoryManager(mockedSensorRepo,mockedPlatformRepo,mockedLocationRepo, mockedPublisher);
    }

    @Test
    public void testMockCreation(){
        assertNotNull(mockedSensorRepo);
        assertNotNull(mockedPlatformRepo);
        assertNotNull(mockedLocationRepo);
        assertNotNull(mockedPublisher);
        assertNotNull(platformToSave);
    }

    @Test
    public void testSavePlatformTriggersRepo() {
        when( mockedPlatformRepo.save(platformToSave)).thenReturn(platformToSave);
        manager.savePlatform(platformToSave);
        verify(mockedPlatformRepo).save(platformToSave);
    }

    @Test
    public void testSavePlatformTriggersMessage() {
        when( mockedPlatformRepo.save(platformToSave)).thenReturn(platformToSave);
        manager.savePlatform(platformToSave);
        verify(mockedPublisher).sendPlatformCreatedMessage(platformToSave);
    }

    @Test
    public void testSaveSensorTriggersRepo() {
        String platformName = "platform";
        String sensorId = "sensorId";
        //Save triggers both sensor and location saves
        when( mockedPlatformRepo.findOne(platformName)).thenReturn(platformToSave);
        when( mockedSensorRepo.save(sensorToSave)).thenReturn(sensorToSave);
        when( mockedLocationRepo.save(location)).thenReturn(location);
        when( sensorToSave.getId()).thenReturn(sensorId);
        when( sensorToSave.getLocation()).thenReturn(location);


        List<Sensor> sensors = new ArrayList<Sensor>();
        sensors.add(sensorToSave);
        List<Sensor> savedSensors = manager.saveSensors(platformName, sensors );
        assertEquals(sensors.size(),savedSensors.size());
    }

}
