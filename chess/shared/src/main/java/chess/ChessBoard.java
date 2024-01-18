package chess;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] squares = new ChessPiece[8][8];
    public ChessBoard() {
        
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow()][position.getColumn()] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return squares[position.getRow()][position.getColumn()];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        //manually create all pieces in the original location.
        //instantiate pieces in each corresponding position.
        squares[2][1] = null;
        squares[2][2] = null;
        squares[2][3] = null;
        squares[2][4] = null;
        squares[2][5] = null;
        squares[2][6] = null;
        squares[2][7] = null;
        squares[2][8] = null;

        addPiece(new ChessPosition(2, 1), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN));
        addPiece(new ChessPosition(2, 2), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN));
        addPiece(new ChessPosition(2, 3), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN));
        addPiece(new ChessPosition(2, 4), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN));
        addPiece(new ChessPosition(2, 5), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN));
        addPiece(new ChessPosition(2, 6), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN));
        addPiece(new ChessPosition(2, 7), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN));
        addPiece(new ChessPosition(2, 8), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN));

    }
}
