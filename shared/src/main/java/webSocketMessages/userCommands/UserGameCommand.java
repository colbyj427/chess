package webSocketMessages.userCommands;

import java.util.Objects;

/**
 * Represents a command a user can send the server over a websocket
 * 
 * Note: You can add to this class, but you should not alter the existing
 * methods.
 */
public class UserGameCommand {
    protected CommandType type;
    public enum CommandType {
        JOIN_PLAYER,
        JOIN_OBSERVER,
        MAKE_MOVE,
        LEAVE,
        RESIGN
    }
    public UserGameCommand(String authToken, String username, CommandType type) {
        this.authToken = authToken;
        this.username = username;
        this.type = type;
    }


    private final String authToken;
    private final String username;

    public String getAuthString() {
        return authToken;
    }

    public CommandType getCommandType() {
        return this.type;
    }
    public String getUsername() { return this.username; }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserGameCommand))
            return false;
        UserGameCommand that = (UserGameCommand) o;
        return getCommandType() == that.getCommandType() && Objects.equals(getAuthString(), that.getAuthString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommandType(), getAuthString());
    }
}
