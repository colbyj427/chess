package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

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
//                return
                break;
            case PieceType.QUEEN:
                break;
            case PieceType.BISHOP:
                return bishopMoves(board, myPosition);
//                break;
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

    public Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition currentPosition) {
        Collection<ChessMove> validMoves  = new HashSet<>();
        //while loop for while a value is less than 8 or more than 0. May need 4 loops.
        //one for going up left, up right, down left, down right.

        //if position is occupied, do not add, break loop if in one as it can't move past it.
        //up and left:
        boolean keep_moving = false;
        ChessPosition next_square = new ChessPosition(currentPosition.getRow(),currentPosition.getColumn());
        int row = currentPosition.getRow();
        int col = currentPosition.getColumn();

        while (keep_moving) {
            if (board.getPiece(currentPosition) != null) {

            }
        }


//    validMoves.add(move)
        return validMoves;
    }
    public Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition currentPosition) {
        Collection<ChessMove> validMoves = new HashSet<>();
        return validMoves;
    }
    public Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition currentPosition) {
        Collection<ChessMove> validMoves = new HashSet<>();
        return validMoves;
    }
    public Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition currentPosition) {
        Collection<ChessMove> validMoves = new HashSet<>();
        return validMoves;
    }
    public Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition currentPosition) {
        Collection<ChessMove> validMoves = new HashSet<>();
        return validMoves;
    }
    public Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition currentPosition) {
        Collection<ChessMove> validMoves  = new HashSet<>();
//    validMoves.add(move)
        return validMoves;
    }


}