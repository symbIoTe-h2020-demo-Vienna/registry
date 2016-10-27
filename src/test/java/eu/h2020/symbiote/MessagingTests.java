//package eu.h2020.symbiote;
//
//import com.google.gson.Gson;
//import com.netflix.discovery.converters.Auto;
//import eu.h2020.symbiote.config.TestMongoConfig;
//import eu.h2020.symbiote.messaging.RPCReceiver;
//import eu.h2020.symbiote.messaging.RPCTestSender;
//import eu.h2020.symbiote.model.Platform;
//import eu.h2020.symbiote.model.RegistrationObject;
//import eu.h2020.symbiote.model.RegistrationObjectType;
//import eu.h2020.symbiote.repository.RepositoryManager;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//import static org.junit.Assert.fail;
//import static org.mockito.Mockito.*;
//
///**
// * Created by Mael on 25/10/2016.
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = RegistryApplication.class, properties = "eureka.client.enabled=false")
//@ContextConfiguration(classes = {TestMongoConfig.class})
//public class MessagingTests {
//
//    private static final String DATA_FOLDER = "src/test/resources/";
//    private static final String PLATFORM_1 = "platform1.json";
//    private static final String RESOURCE_1 = "resource1.json";
//
//    RPCTestSender rpcSender;
//
//    @Autowired
//    RepositoryManager manager;
//
//    @Autowired
//    RPCReceiver receiver;
//
//    @Before
//    public void init() {
//        rpcSender = new RPCTestSender();
//    }
//
//    @Test
//    @Ignore
//    public void checkIfMessageTriggersSaveForPlatform() {
//        try {
//            Platform obj = new Platform();
//            RepositoryManager mockManager = Mockito.mock(RepositoryManager.class);
//
////            Runnable r = new Runnable() {
////                @Override
////                public void run() {
////                    try {
////                        RPCReceiver.consumeRPCMessageAndResponse();
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    }
////                }
////            };
////
////            final ScheduledExecutorService execService = Executors.newSingleThreadScheduledExecutor();
////            execService.schedule(r,1, TimeUnit.SECONDS);
//
//            String message = prepareMessage(PLATFORM_1, RegistrationObjectType.PLATFORM, null);
//            String response = rpcSender.rpcCall(message);
//            System.out.println(response);
//            verify(mockManager, times(1)).savePlatform(obj);
//        } catch (IOException e) {
//            fail("IO error during platform message");
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            fail("Interrupted error during platform message");
//            e.printStackTrace();
//        }
//    }
//
//    private String prepareMessage( String filename, RegistrationObjectType type, String parentId) throws IOException {
//        Path path = Paths.get(DATA_FOLDER + filename);
//        String json = new String(Files.readAllBytes(path));
//        RegistrationObject regPOJO = new RegistrationObject(json , type, parentId);
//        Gson gson = new Gson();
//        return gson.toJson(regPOJO);
//    }
//
//
//}
