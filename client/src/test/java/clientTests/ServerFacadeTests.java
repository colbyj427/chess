package clientTests;

import ServerClientCommunication.ServerFacade;
import model.AuthRecord;
import model.GameRecord;
import model.JoinGameRecord;
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
    void register() throws Exception {
        UserRecord request = new UserRecord("username", "password", "email");
        AuthRecord authData = ServerFacade.register(request);
        assertTrue(authData.authToken().length() > 10);
    }
    @Test
    void badRegister() throws Exception {
        UserRecord request = new UserRecord(null, "password", "email");
        Assertions.assertThrows(Exception.class, () -> {
            AuthRecord authData=ServerFacade.register(request);
        });
    }
    @Test
    public void login() throws Exception{
        UserRecord registerRequest = new UserRecord("username", "password", "email");
        AuthRecord registerAuthData = ServerFacade.register(registerRequest);
        ServerFacade.logout(null, registerAuthData.authToken());
        UserRecord loginRequest = new UserRecord("username", "password", null);
        AuthRecord loginAuthData = ServerFacade.login(loginRequest);
        assertTrue(loginAuthData.authToken().length() > 10);
    }
    @Test
    public void badLogin() throws Exception{
        UserRecord registerRequest = new UserRecord("username", "password", "email");
        AuthRecord registerAuthData = ServerFacade.register(registerRequest);
        ServerFacade.logout(null, registerAuthData.authToken());
        UserRecord loginRequest = new UserRecord("wrongusername", "password", null);
        Assertions.assertThrows(Exception.class, () -> {
            AuthRecord authData=ServerFacade.login(loginRequest);
        });
    }
    @Test
    public void logout() throws Exception{
        UserRecord registerRequest = new UserRecord("username", "password", "email");
        AuthRecord registerAuthData = ServerFacade.register(registerRequest);
        Assertions.assertDoesNotThrow(() -> {
        ServerFacade.logout(null, registerAuthData.authToken());
        });
    }
    @Test
    public void badLogout() throws Exception{
        UserRecord registerRequest = new UserRecord("username", "password", "email");
        AuthRecord registerAuthData = ServerFacade.register(registerRequest);

        Assertions.assertThrows(Exception.class, () -> {
            ServerFacade.logout(null, "badAuthToken");
        });
    }
    @Test
    public void createGame() throws Exception{
        UserRecord registerRequest = new UserRecord("username", "password", "email");
        AuthRecord registerAuthData = ServerFacade.register(registerRequest);
        GameRecord newGame = new GameRecord(0, null, null, "newgame", null, null);
        GameRecord createdGame = ServerFacade.createGame(newGame, registerAuthData.authToken());
        assertTrue(createdGame.gameName().equals("newgame"));
    }
    @Test
    public void badCreateGame() throws Exception{
        UserRecord registerRequest = new UserRecord("username", "password", "email");
        AuthRecord registerAuthData = ServerFacade.register(registerRequest);
        GameRecord newGame = new GameRecord(0, null, null, "newgame", null, null);
        Assertions.assertThrows(Exception.class, () -> {
            ServerFacade.createGame(newGame, "badAuthToken");
        });
    }
    @Test
    public void joinGame() throws Exception{
        UserRecord registerRequest = new UserRecord("username", "password", "email");
        AuthRecord registerAuthData = ServerFacade.register(registerRequest);
        GameRecord newGame = new GameRecord(0, null, null, "newgame", null, null);
        GameRecord createdGame = ServerFacade.createGame(newGame, registerAuthData.authToken());
        JoinGameRecord joinRecord = new JoinGameRecord("BLACK", createdGame.gameID());
        Assertions.assertDoesNotThrow(() -> {
            ServerFacade.joinGame(joinRecord, registerAuthData.authToken());
        });
    }
    @Test
    public void BadjoinGame() throws Exception{
        UserRecord registerRequest = new UserRecord("username", "password", "email");
        AuthRecord registerAuthData = ServerFacade.register(registerRequest);
        GameRecord newGame = new GameRecord(0, null, null, "newgame", null, null);
        GameRecord createdGame = ServerFacade.createGame(newGame, registerAuthData.authToken());
        JoinGameRecord joinRecord = new JoinGameRecord("ORANGE", createdGame.gameID());
        Assertions.assertThrows(Exception.class, () -> {
            ServerFacade.joinGame(joinRecord, registerAuthData.authToken());
        });
    }
    @Test
    public void listGames() throws Exception{
        UserRecord registerRequest = new UserRecord("username", "password", "email");
        AuthRecord registerAuthData = ServerFacade.register(registerRequest);
        GameRecord newGame = new GameRecord(0, null, null, "newgame", null, null);
        ServerFacade.createGame(newGame, registerAuthData.authToken());
        Assertions.assertDoesNotThrow(() -> {
            ServerFacade.listGames(null, registerAuthData.authToken());
        });
    }
    @Test
    public void badListGames() throws Exception{
        UserRecord registerRequest = new UserRecord("username", "password", "email");
        AuthRecord registerAuthData = ServerFacade.register(registerRequest);
        GameRecord newGame = new GameRecord(0, null, null, "newgame", null, null);
        ServerFacade.createGame(newGame, registerAuthData.authToken());
        Assertions.assertThrows(Exception.class, () -> {
            ServerFacade.listGames(null, "badAuthToken");
        });
    }
}
