package eu.h2020.symbiote;

import eu.h2020.symbiote.config.TestMongoConfig;
import eu.h2020.symbiote.model.Platform;
import eu.h2020.symbiote.repository.PlatformRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RegistryApplication.class, properties = "eureka.client.enabled=false")
@ContextConfiguration(classes = {TestMongoConfig.class})
public class PlatformRepositoryIntegrationTests {

	private static final String P1_NAME = "Platform 1";
	private static final String P1_OWNER = "Owner 1";
	private static final String P1_URL = "http://localhost:9991/testrapurl";
	private static final String P1_TYPE = "Type 1";

	@Autowired
	PlatformRepository platformRepository;

	@Before
	public void init() {
		//In case
		platformRepository.deleteAll();
		platformRepository.save(getTestPlatform());
	}

	@After
	public void tearDown() {
		platformRepository.deleteAll();
	}

	@Test
	public void testDataExistsTest() {
		List<Platform> all = platformRepository.findAll();
		assertNotNull(all);
		assertEquals(1, all.size());
		Platform queryPlatform = all.get(0);
		Platform testPlatform = getTestPlatform();
		assertEquals(queryPlatform.getName(),testPlatform.getName());
		assertEquals(queryPlatform.getOwner(),testPlatform.getOwner());
		assertEquals(queryPlatform.getType(),testPlatform.getType());
		assertEquals(queryPlatform.getResourceAccessProxyUrl(),testPlatform.getResourceAccessProxyUrl());
	}

	@Test
	public void addingPlatformWithoutNameThrowsError() {
		Platform platformWithoutName = new Platform();
		platformWithoutName.setOwner(P1_OWNER);
		try {
			platformWithoutName.setResourceAccessProxyUrl(new URL(P1_URL));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		platformWithoutName.setType(P1_TYPE);

		try {
			platformRepository.save(platformWithoutName);
			fail( "Repository must not allow adding platforms without name");
		} catch( Exception e ) {
			//TODO check exception
			System.out.println("Got exception " + e);

		}
	}

	private Platform getTestPlatform() {
		Platform testPlatform = new Platform();
		testPlatform.setName(P1_NAME);
		testPlatform.setOwner(P1_OWNER);
		try {
			testPlatform.setResourceAccessProxyUrl(new URL( P1_URL));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		testPlatform.setType(P1_TYPE);
		return testPlatform;
	}




}
