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
    private ChessGame.TeamColor teamTurn= TeamColor.WHITE;
    private ChessBoard board;

    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn= team;
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
        if (piece == null) {
            return null;
        }
        Collection<ChessMove> oldMoves = piece.pieceMoves(board, startPosition);

        ChessBoard newBoard = new ChessBoard(board);
        for (ChessMove move : oldMoves) {
            board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
            board.addPiece(move.getStartPosition(), null);
            if (!isInCheck(piece.getTeamColor())) {
                newMoves.add(move);
            }
            board = new ChessBoard(newBoard);
        }
        if (newMoves.isEmpty()) {
            return newMoves;
        } else {return newMoves;}
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        //call validmoves for start position and if in validmoves, make move.then change color
//1,8 1,7
        // if the moves are out of bounds, throw an exception
        if (move.getEndPosition().getRow() > 7 || move.getEndPosition().getRow() < 0) {
            throw new InvalidMoveException();
        }
        if (move.getEndPosition().getColumn() > 7 || move.getEndPosition().getColumn() < 0) {
            throw new InvalidMoveException();
        }
        if (move.getStartPosition().getRow() > 7 || move.getStartPosition().getRow() < 0) {
            throw new InvalidMoveException();
        }
        if (move.getStartPosition().getColumn() > 7 || move.getStartPosition().getColumn() < 0) {
            throw new InvalidMoveException();
        }
        //makes the valid moves, gets the position and piece of attempted move.
        Collection <ChessMove> moves =  validMoves(move.getStartPosition());
        if (moves == null) {
            throw new InvalidMoveException();
        }
        ChessPosition currentPosition = new ChessPosition(move.getStartPosition().getRow() + 1, move.getStartPosition().getColumn() + 1);
        ChessPiece currentPiece = board.getPiece(currentPosition);
        //if there is a piece, and it is their turn:
        if (currentPiece != null && currentPiece.getTeamColor() == teamTurn) {
            //if the move is in valid moves, execute it and change the turn
            if (moves.contains(move)) {
                board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
                board.addPiece(move.getStartPosition(), null);
                // if theres a promotion piece, put it on the board.
                if (move.getPromotionPiece() != null) {
                    board.addPiece(move.getEndPosition(), new ChessPiece(teamTurn, move.getPromotionPiece()));
                }

                //change whose turn it is after the move.
                if (teamTurn == TeamColor.BLACK) {
                    teamTurn= TeamColor.WHITE;
                } else if (teamTurn == TeamColor.WHITE) {
                    teamTurn= TeamColor.BLACK;
                }
            } else { throw new InvalidMoveException(); }
        }
        else { throw new InvalidMoveException(); }
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
        ChessPosition kingPosition = null;
        //find the king
        //rows
        for (int row = 1; row <= 8; row++) {
            //columns
            for (int col = 1; col <= 8; col++) {
                ChessPosition currentPosition = new ChessPosition(row, col);
                ChessPiece currentPiece = board.getPiece(currentPosition);
                if (currentPiece != null) {
                    if (currentPiece.getPieceType() == ChessPiece.PieceType.KING) {
                        if (currentPiece.getTeamColor() == teamColor) {
                            kingPosition=currentPosition;
                        }
                    }
                }
            }
        }
        if (kingPosition == null) {
            return false;
        }
        //check all the moves
            //rows
            for (int row = 1; row <= 8; row++) {
                //columns
                for (int col=1; col <= 8; col++) {
                    ChessPosition currentPosition = new ChessPosition(row, col);
                    ChessPiece currentPiece = board.getPiece(currentPosition);
                    if (currentPiece != null) {
                        if (currentPiece.getTeamColor() != teamColor) {
                            //if there is a piece and it is opposing, retrieve all the moves.
                            Collection<ChessMove> moves=currentPiece.pieceMoves(board, currentPosition);
                            for (ChessMove move : moves) {
                                if (move.getEndPosition().equals(kingPosition)) {
                                    return true;
                                }
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
        //a valid move never leaves you in check, so if valid moves are empty, then checkmate
        //  just loop through each piece and break loop if any have a valid move.
        if (isInCheck(teamColor)) {
            for (int row = 1; row <= 8; row++) {
                //columns
                for (int col=1; col <= 8; col++) {
                    ChessPosition currentPosition = new ChessPosition(row, col);
                    ChessPiece currentPiece = board.getPiece(currentPosition);
                    //if there is a piece there and it is on the team in check:
                    if (currentPiece != null) {
                        if (currentPiece.getTeamColor() == teamColor) {
                            //check for any valid moves, if any, return false
                            if (validMoves(currentPosition) != null && !validMoves(currentPosition).isEmpty()) {
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        //if its this teams turn check each square
        if (teamTurn == teamColor) {
            // is the team is in check, check for stalemate, if not, return false.
            if (!isInCheck(teamColor)) {
                //rows
                for (int row=1; row <= 8; row++) {
                    //columns
                    for (int col=1; col <= 8; col++) {
                        // at each postition, if moves is not empty, not in stalemate
                        ChessPosition currentPosition= new ChessPosition(row, col);
                        if (board.getPiece(currentPosition) != null) {
                            if (validMoves(currentPosition) != null) {
                                if (!validMoves(currentPosition).isEmpty() && board.getPiece(currentPosition).getTeamColor() == teamColor) {
                                    return false;
                                }
                            }
                        }
                    }
                }
                // if by the end of all the squares, false hasn't been given, then its true, in stalemate.
                return true;
            }
        }
        // if it isn't their turn, or they are in check, they aren't in stalemate.
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
