package clientTests;

import ServerClientCommunication.ServerFacade;
import model.AuthRecord;
import model.UserRecord;
import org.junit.jupiter.api.*;
import server.Server;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class ServerFacadeTests {

    private static Server server;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        ServerFacade facade = new ServerFacade(port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }
    @BeforeEach
    void clearDatabase() throws Exception {
        ServerFacade.clear(null);
    }
    @Test
    public void sampleTest() {
        assertTrue(true);
    }
    @Test
    void register() throws Exception {
        UserRecord request = new UserRecord("username", "password", "email");
        AuthRecord authData = ServerFacade.register(request);
        assertTrue(authData.authToken().length() > 10);
    }
}
