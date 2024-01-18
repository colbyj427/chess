package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private ChessGame.TeamColor pieceColor;
    private ChessPiece.PieceType type;
    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {


        //you create a chessMove instance for each possible move. cant move where own team is, disregard check.
        //prmotion piece is handled for pawn, otherwise null. Each promotion option is a separate chessMove
        // instance with same start and end, four moves, cuz four promotions.

        //create a new class called ChessPieceMovement
            //create a set of subclasses to contain the movement rules for each piece.

        //get piece function.
        ChessPiece my_piece = board.getPiece(myPosition);
        //switch statement on which type
        switch (my_piece.getPieceType()) {
            case PieceType.KING:
                break;
            case PieceType.QUEEN:
                break;
            case PieceType.BISHOP:
                break;
            case PieceType.KNIGHT:
                break;
            case PieceType.ROOK:
                break;
            case PieceType.PAWN:
                break;
        }
        //instantiate piece movement rules for that piece.

        //returns a collection of moves
        return new ArrayList<>();
    }
}
