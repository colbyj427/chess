package chess;

import java.util.Collection;
import java.util.HashSet;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private ChessGame.TeamColor team_turn = TeamColor.WHITE;
    private ChessBoard board;

    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return team_turn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        team_turn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        //for each move in piecemoves,
        //clone the board and make the move, if isincheck is true, then don't add to valid moves.
        Collection<ChessMove> newMoves = new HashSet<>();
        ChessPiece piece = board.getPiece(startPosition);
        Collection<ChessMove> oldMoves = piece.pieceMoves(board, startPosition);

        for (ChessMove move : oldMoves) {
            ChessBoard newBoard = new ChessBoard(board);
            newBoard.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
            newBoard.addPiece(move.getStartPosition(), null);
            if (isInCheck(team_turn) == false) {
                newMoves.add(move);
            }
        }

        return newMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        //call validmoves for start position and if in validmoves, make move.then change color
        board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
        board.addPiece(move.getStartPosition(), null);
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        //find the king of the color passed in.
        //check the validmoves of each opposing piece in a loop to see if the kings position is there.
        boolean in_check = false;
        ChessPosition king_position = new ChessPosition(1, 1);
        //find the king
        //rows
        for (int row = 1; row <= 8; row++) {
            //columns
            for (int col = 1; col <= 8; col++) {
                ChessPosition current_position = new ChessPosition(row, col);
                ChessPiece current_piece = board.getPiece(current_position);
                if (current_piece.getPieceType() == ChessPiece.PieceType.KING) {
                    if (current_piece.getTeamColor() == teamColor) {
                        king_position = current_position;
                    }
                }
            }
        }
        //check all the moves
            //rows
            for (int row = 1; row <= 8; row++) {
                //columns
                for (int col=1; col <= 8; col++) {
                    ChessPosition current_position = new ChessPosition(row, col);
                    ChessPiece current_piece = board.getPiece(current_position);
                    if (current_piece != null && current_piece.getTeamColor() != teamColor) {
                        //if there is a piece and it is opposing, retrieve all the moves.
                        Collection<ChessMove> moves = current_piece.pieceMoves(board, current_position);
                        for (ChessMove move: moves) {
                            if (move.getEndPosition() == king_position) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
        //clone the board and run all valid moves, if isincheck never returns false, then checkmate is true
        //OR
        //a valid move never leaves you in check, so if valid moves are empty, then checkmate
        //  just loop through each piece and break loop if any have a valid move.
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (team_turn == teamColor) {
            //rows
            for (int row = 1; row <= 8; row++) {
                //columns
                for (int col=1; col <= 8; col++) {
                    ChessPosition current_position = new ChessPosition(row, col);
                    if (validMoves(current_position).size() > 0) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }
}
