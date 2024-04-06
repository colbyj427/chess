package model;

import chess.ChessGame;

import java.util.ArrayList;
import java.util.Collection;

public record GameRecord(int gameID, String whiteUsername, String blackUsername,
                         String gameName, ChessGame game, ArrayList<String> spectators) {
}
