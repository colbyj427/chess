package chess;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPiece that)) return false;
        return pieceColor == that.pieceColor && type == that.type;
    }
    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
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
        ChessPiece myPiece = board.getPiece(myPosition);
        switch (myPiece.getPieceType()) {
            case PieceType.KING:
                return kingMoves(board, myPosition);
            case PieceType.QUEEN:
                return queenMoves(board, myPosition);
            case PieceType.BISHOP:
                return bishopMoves(board, myPosition);
            case PieceType.KNIGHT:
                return knightMoves(board, myPosition);
            case PieceType.ROOK:
                return rookMoves(board, myPosition);
            case PieceType.PAWN:
                return pawnMoves(board, myPosition);
        }
        return new ArrayList<>();
    }
    public Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition currentPosition) {
        Collection<ChessMove> validMoves  = new HashSet<>();
        boolean keepMoving = true;
        ChessPosition upLeft = new ChessPosition(currentPosition.getRow() + 1, currentPosition.getColumn() + 1);
        ChessPosition upRight = new ChessPosition(currentPosition.getRow() + 1, currentPosition.getColumn() + 1);
        ChessPosition downLeft = new ChessPosition(currentPosition.getRow() + 1, currentPosition.getColumn() + 1);
        ChessPosition downRight = new ChessPosition(currentPosition.getRow() + 1, currentPosition.getColumn() + 1);
        calcBishopUpLeftMoves(board, currentPosition, keepMoving, upLeft, validMoves);
        calcBishopUpRightMoves(board, currentPosition, keepMoving, upRight, validMoves);
        calcBishopDownLeftMoves(board, currentPosition, keepMoving, downLeft, validMoves);
        calcBishopDownRightMoves(board, currentPosition, keepMoving, downRight, validMoves);
        return validMoves;
    }
    private static void calcBishopDownRightMoves(ChessBoard board, ChessPosition currentPosition, boolean keepMoving, ChessPosition downRight, Collection<ChessMove> validMoves) {
        int row;
        int column;
        while (keepMoving) { // check the lower right boundaries
            if (downRight.getRow() <= 0 || downRight.getColumn() >= 7) {
                break;
            }
            else { //create new position to add if no same team piece is there.
                row = downRight.getRow() + 1;
                column = downRight.getColumn() + 1;
                downRight= new ChessPosition(row - 1, column + 1);
                if (board.getPiece(downRight) != null) { // if there is a piece there, either take it and stop, or stop if your team.
                    if (board.getPiece(currentPosition).pieceColor == board.getPiece(downRight).pieceColor) {
                        break;
                    }
                    else {
                        validMoves.add(new ChessMove(currentPosition, downRight, null));
                        break;
                    }
                } else {
                    validMoves.add(new ChessMove(currentPosition, downRight, null));
                }
            }
        }
    }
    private static void calcBishopDownLeftMoves(ChessBoard board, ChessPosition currentPosition, boolean keepMoving, ChessPosition downLeft, Collection<ChessMove> validMoves) {
        int row;
        int column;
        while (keepMoving) { // check lower left boundaries
            if (downLeft.getRow() <= 0 || downLeft.getColumn() <= 0) {
                break;
            }
            else { //create new position to add if no same team piece is there.
                row = downLeft.getRow() + 1;
                column = downLeft.getColumn() + 1;
                downLeft= new ChessPosition(row - 1, column - 1);
                if (board.getPiece(downLeft) != null) { // if there is a piece there, either take it and stop, or stop if your team.
                    if (board.getPiece(currentPosition).pieceColor == board.getPiece(downLeft).pieceColor) {
                        break;
                    }
                    else {
                        validMoves.add(new ChessMove(currentPosition, downLeft, null));
                        break;
                    }
                } else {
                    validMoves.add(new ChessMove(currentPosition, downLeft, null));
                }
            }
        }
    }
    private static void calcBishopUpRightMoves(ChessBoard board, ChessPosition currentPosition, boolean keepMoving, ChessPosition upRight, Collection<ChessMove> validMoves) {
        int row;
        int column;
        while (keepMoving) { // check the upper right boundaries
            if (upRight.getRow() >=7 || upRight.getColumn() >= 7) {
                break;
            }
            else { //create new position to add if no same team piece is there.
                row = upRight.getRow() + 1;
                column = upRight.getColumn() + 1;
                upRight= new ChessPosition(row + 1, column + 1);
                if (board.getPiece(upRight) != null) { // if there is a piece there, either take it and stop, or stop if your team.
                    if (board.getPiece(currentPosition).pieceColor == board.getPiece(upRight).pieceColor) {
                        break;
                    }
                    else {
                        validMoves.add(new ChessMove(currentPosition, upRight, null));
                        break;
                    }
                } else {
                    validMoves.add(new ChessMove(currentPosition, upRight, null));
                }
            }
        }
    }
    private static void calcBishopUpLeftMoves(ChessBoard board, ChessPosition currentPosition, boolean keepMoving, ChessPosition upLeft, Collection<ChessMove> validMoves) {
        int row;
        int column;
        while (keepMoving) {
            if (upLeft.getRow() >=7 || upLeft.getColumn() <= 0) { // check the upper left boundaries
                break;
            }
        else { //create new position to add if no same team piece is there.
                row = upLeft.getRow() + 1;
                column = upLeft.getColumn() + 1;
                upLeft= new ChessPosition(row + 1, column - 1);
                if (board.getPiece(upLeft) != null) { // if there is a piece there, either take it and stop, or stop if your team.
                    if (board.getPiece(currentPosition).pieceColor == board.getPiece(upLeft).pieceColor) {
                        break;
                    }
                    else {
                        validMoves.add(new ChessMove(currentPosition, upLeft, null));
                        break;
                    }
                } else {
                    validMoves.add(new ChessMove(currentPosition, upLeft, null));
                }
            }
            }
    }
    public Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition currentPosition) {
        Collection<ChessMove> validMoves = new HashSet<>();
        boolean keepMoving = true;
        ChessPosition up = new ChessPosition(currentPosition.getRow() + 1, currentPosition.getColumn() + 1);
        ChessPosition down = new ChessPosition(currentPosition.getRow() + 1, currentPosition.getColumn() + 1);
        ChessPosition left = new ChessPosition(currentPosition.getRow() + 1, currentPosition.getColumn() + 1);
        ChessPosition right = new ChessPosition(currentPosition.getRow() + 1, currentPosition.getColumn() + 1);
        calcRookUpMoves(board, currentPosition, keepMoving, up, validMoves);
        calcRookDownMoves(board, currentPosition, keepMoving, down, validMoves);
        calcRookLeftMoves(board, currentPosition, keepMoving, left, validMoves);
        calcRookRightMoves(board, currentPosition, keepMoving, right, validMoves);
        return validMoves;
    }
    private static void calcRookRightMoves(ChessBoard board, ChessPosition currentPosition, boolean keepMoving, ChessPosition right, Collection<ChessMove> validMoves) {
        int row;
        int column;
        while (keepMoving) {
            if (right.getColumn() >=7) { // check the right boundary
                break;
            }
            else { //create new position to add if no same team piece is there.
                row = right.getRow() + 1;
                column = right.getColumn() + 1;
                right= new ChessPosition(row, column + 1);
                if (board.getPiece(right) != null) { // if there is a piece there, either take it and stop, or stop if your team.
                    if (board.getPiece(currentPosition).pieceColor == board.getPiece(right).pieceColor) {
                        break;
                    }
                    else {
                        validMoves.add(new ChessMove(currentPosition, right, null));
                        break;
                    }
                } else {
                    validMoves.add(new ChessMove(currentPosition, right, null));
                }
            }
        }
    }
    private static void calcRookLeftMoves(ChessBoard board, ChessPosition currentPosition, boolean keepMoving, ChessPosition left, Collection<ChessMove> validMoves) {
        int column;
        int row;
        while (keepMoving) {
            if (left.getColumn() <= 0) { // check the left boundary
                break;
            }
            else { //create new position to add if no same team piece is there.
                row = left.getRow() + 1;
                column = left.getColumn() + 1;
                left= new ChessPosition(row, column - 1);
                if (board.getPiece(left) != null) { // if there is a piece there, either take it and stop, or stop if your team.
                    if (board.getPiece(currentPosition).pieceColor == board.getPiece(left).pieceColor) {
                        break;
                    }
                    else {
                        validMoves.add(new ChessMove(currentPosition, left, null));
                        break;
                    }
                } else {
                    validMoves.add(new ChessMove(currentPosition, left, null));
                }
            }
        }
    }
    private static void calcRookDownMoves(ChessBoard board, ChessPosition currentPosition, boolean keepMoving, ChessPosition down, Collection<ChessMove> validMoves) {
        int row;
        int column;
        while (keepMoving) {
            if (down.getRow() <=0) { // check the lower boundary
                break;
            }
            else { //create new position to add if no same team piece is there.
                row = down.getRow() + 1;
                column = down.getColumn() + 1;
                down= new ChessPosition(row - 1, column);
                if (board.getPiece(down) != null) { // if there is a piece there, either take it and stop, or stop if your team.
                    if (board.getPiece(currentPosition).pieceColor == board.getPiece(down).pieceColor) {
                        break;
                    }
                    else {
                        validMoves.add(new ChessMove(currentPosition, down, null));
                        break;
                    }
                } else {
                    validMoves.add(new ChessMove(currentPosition, down, null));
                }
            }
        }
    }
    private static void calcRookUpMoves(ChessBoard board, ChessPosition currentPosition, boolean keepMoving, ChessPosition up, Collection<ChessMove> validMoves) {
        int column;
        int row;
        while (keepMoving) {
            if (up.getRow() >=7) { // check the upper boundary
                break;
            }
            else { //create new position to add if no same team piece is there.
                row = up.getRow() + 1;
                column = up.getColumn() + 1;
                up = new ChessPosition(row + 1, column);
                if (board.getPiece(up) != null) { // if there is a piece there, either take it and stop, or stop if your team.
                    if (board.getPiece(currentPosition).pieceColor == board.getPiece(up).pieceColor) {
                        break;
                    }
                    else {
                        validMoves.add(new ChessMove(currentPosition, up, null));
                        break;
                    }
                } else {
                    validMoves.add(new ChessMove(currentPosition, up, null));
                }
            }
        }
    }
    public Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition currentPosition) {
        Collection<ChessMove> validMoves = new HashSet<>();
        if (board.getPiece(currentPosition).pieceColor == ChessGame.TeamColor.WHITE) { // white pawn moves
            ChessPosition forwardOne=new ChessPosition(currentPosition.getRow() + 2, currentPosition.getColumn() + 1);
            ChessPosition forwardTwo=new ChessPosition(currentPosition.getRow() + 3, currentPosition.getColumn() + 1);
            ChessPosition takePieceLeft=new ChessPosition(currentPosition.getRow() + 2, currentPosition.getColumn());
            ChessPosition takePieceRight=new ChessPosition(currentPosition.getRow() + 2, currentPosition.getColumn() + 2);
            if (forwardOne.getRow() == 7) {
                calcPromotionMoves(board, currentPosition, forwardOne, validMoves, takePieceLeft, takePieceRight);
            } else if (currentPosition.getRow() == 1) { //pawn hasn't moved yet
                calcPawnDoubleMoves(board, currentPosition, forwardOne, forwardTwo, validMoves, takePieceLeft, takePieceRight);
            } else { // add moves not promotion and not first moves
                calcPawnSingleMoves(board, currentPosition, forwardOne, validMoves, takePieceLeft, takePieceRight);
            }
        } else { // black pawn moves
            ChessPosition forwardOne=new ChessPosition(currentPosition.getRow(), currentPosition.getColumn() + 1);
            ChessPosition forwardTwo=new ChessPosition(currentPosition.getRow() - 1, currentPosition.getColumn() + 1);
            ChessPosition takePieceLeft=new ChessPosition(currentPosition.getRow(), currentPosition.getColumn());
            ChessPosition takePieceRight=new ChessPosition(currentPosition.getRow(), currentPosition.getColumn() + 2);
            if (forwardOne.getRow() == 0) {
                calcPromotionMoves(board, currentPosition, forwardOne, validMoves, takePieceLeft, takePieceRight);
            } else if (currentPosition.getRow() == 6) { //pawn hasn't moved yet
                calcPawnDoubleMoves(board, currentPosition, forwardOne, forwardTwo, validMoves, takePieceLeft, takePieceRight);
            } else { // add moves not promotion and not first moves
                calcPawnSingleMoves(board, currentPosition, forwardOne, validMoves, takePieceLeft, takePieceRight);
            }
        }
        return validMoves;
    }
    private static void calcPawnSingleMoves(ChessBoard board, ChessPosition currentPosition, ChessPosition forwardOne, Collection<ChessMove> validMoves, ChessPosition takePieceLeft, ChessPosition takePieceRight) {
        if (board.getPiece(forwardOne) == null) {
            validMoves.add(new ChessMove(currentPosition, forwardOne, null));
        }
        if (takePieceLeft.getColumn() >= 0 && board.getPiece(takePieceLeft) != null && board.getPiece(takePieceLeft).pieceColor != board.getPiece(currentPosition).pieceColor) {
            validMoves.add(new ChessMove(currentPosition, takePieceLeft, null));
        }
        if (takePieceRight.getColumn() <= 7 && board.getPiece(takePieceRight) != null && board.getPiece(takePieceRight).pieceColor != board.getPiece(currentPosition).pieceColor) {
            validMoves.add(new ChessMove(currentPosition, takePieceRight, null));
        }
    }
    private static void calcPawnDoubleMoves(ChessBoard board, ChessPosition currentPosition, ChessPosition forwardOne, ChessPosition forwardTwo, Collection<ChessMove> validMoves, ChessPosition takePieceLeft, ChessPosition takePieceRight) {
        //add twos
        if (board.getPiece(forwardOne) == null && board.getPiece(forwardTwo) == null) {
            validMoves.add(new ChessMove(currentPosition, forwardTwo, null));
            validMoves.add(new ChessMove(currentPosition, forwardOne, null));
        } else if (board.getPiece(forwardOne) == null) { //add ones
            validMoves.add(new ChessMove(currentPosition, forwardOne, null));
        }
        if (takePieceLeft.getColumn() >= 0 && board.getPiece(takePieceLeft) != null && board.getPiece(takePieceLeft).pieceColor != board.getPiece(currentPosition).pieceColor) {
            validMoves.add(new ChessMove(currentPosition, takePieceLeft, null));
        }
        if (takePieceRight.getColumn() <= 7 && board.getPiece(takePieceRight) != null && board.getPiece(takePieceRight).pieceColor != board.getPiece(currentPosition).pieceColor) {
            validMoves.add(new ChessMove(currentPosition, takePieceRight, null));
        }
    }
    private static void calcPromotionMoves(ChessBoard board, ChessPosition currentPosition, ChessPosition forwardOne, Collection<ChessMove> validMoves, ChessPosition takePieceLeft, ChessPosition takePieceRight) {
        if (board.getPiece(forwardOne) == null) {
            validMoves.add(new ChessMove(currentPosition, forwardOne, PieceType.ROOK));
            validMoves.add(new ChessMove(currentPosition, forwardOne, PieceType.KNIGHT));
            validMoves.add(new ChessMove(currentPosition, forwardOne, PieceType.BISHOP));
            validMoves.add(new ChessMove(currentPosition, forwardOne, PieceType.QUEEN));
        }
        if (takePieceLeft.getColumn() >= 0 && board.getPiece(takePieceLeft) != null && board.getPiece(takePieceLeft).pieceColor != board.getPiece(currentPosition).pieceColor) {
            validMoves.add(new ChessMove(currentPosition, takePieceLeft, PieceType.ROOK));
            validMoves.add(new ChessMove(currentPosition, takePieceLeft, PieceType.KNIGHT));
            validMoves.add(new ChessMove(currentPosition, takePieceLeft, PieceType.BISHOP));
            validMoves.add(new ChessMove(currentPosition, takePieceLeft, PieceType.QUEEN));
        }
        if (takePieceRight.getColumn() <= 7 && board.getPiece(takePieceRight) != null && board.getPiece(takePieceRight).pieceColor != board.getPiece(currentPosition).pieceColor) {
            validMoves.add(new ChessMove(currentPosition, takePieceRight, PieceType.ROOK));
            validMoves.add(new ChessMove(currentPosition, takePieceRight, PieceType.KNIGHT));
            validMoves.add(new ChessMove(currentPosition, takePieceRight, PieceType.BISHOP));
            validMoves.add(new ChessMove(currentPosition, takePieceRight, PieceType.QUEEN));
        }
    }
    public Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition currentPosition) {
        Collection<ChessMove> validMoves = new HashSet<>();
        ChessPosition upLeft = new ChessPosition(currentPosition.getRow() + 3, currentPosition.getColumn());
        ChessPosition upRight = new ChessPosition(currentPosition.getRow() + 3, currentPosition.getColumn() + 2);
        ChessPosition downLeft = new ChessPosition(currentPosition.getRow() - 1, currentPosition.getColumn());
        ChessPosition downRight = new ChessPosition(currentPosition.getRow() - 1, currentPosition.getColumn() + 2);
        ChessPosition leftUp = new ChessPosition(currentPosition.getRow() + 2, currentPosition.getColumn() -1);
        ChessPosition leftDown = new ChessPosition(currentPosition.getRow(), currentPosition.getColumn() -1);
        ChessPosition rightUp = new ChessPosition(currentPosition.getRow() + 2, currentPosition.getColumn() + 3);
        ChessPosition rightDown = new ChessPosition(currentPosition.getRow(), currentPosition.getColumn() + 3);
        calcMovesAroundPiece(board, currentPosition, upLeft, validMoves, upRight, downLeft, downRight);
        if (leftUp.getRow() <= 7 && leftUp.getColumn() >= 0) { // if in bounds
            if (board.getPiece(leftUp) != null) { //if there is a piece
                if (board.getPiece(leftUp).pieceColor != board.getPiece(currentPosition).pieceColor) { // if piece can be taken
                    validMoves.add(new ChessMove(currentPosition, leftUp, null));
                }
            } else { //if move is in bounds and empty
                validMoves.add(new ChessMove(currentPosition, leftUp, null));
            }
        }
        if (leftDown.getRow() >= 0 && leftDown.getColumn() >= 0) { // if in bounds
            if (board.getPiece(leftDown) != null) { //if there is a piece
                if (board.getPiece(leftDown).pieceColor != board.getPiece(currentPosition).pieceColor) { // if piece can be taken
                    validMoves.add(new ChessMove(currentPosition, leftDown, null));
                }
            } else { //if move is in bounds and empty
                validMoves.add(new ChessMove(currentPosition, leftDown, null));
            }
        }
        if (rightUp.getRow() <= 7 && rightUp.getColumn() <= 7) { // if in bounds
            if (board.getPiece(rightUp) != null) { //if there is a piece
                if (board.getPiece(rightUp).pieceColor != board.getPiece(currentPosition).pieceColor) { // if piece can be taken
                    validMoves.add(new ChessMove(currentPosition, rightUp, null));
                }
            } else { //if move is in bounds and empty
                validMoves.add(new ChessMove(currentPosition, rightUp, null));
            }
        }
        if (rightDown.getRow() >= 0 && rightDown.getColumn() <= 7) { // if in bounds
            if (board.getPiece(rightDown) != null) { //if there is a piece
                if (board.getPiece(rightDown).pieceColor != board.getPiece(currentPosition).pieceColor) { // if piece can be taken
                    validMoves.add(new ChessMove(currentPosition, rightDown, null));
                }
            } else { //if move is in bounds and empty
                validMoves.add(new ChessMove(currentPosition, rightDown, null));
            }
        }
        return validMoves;
    }
    private static void calcMovesAroundPiece(ChessBoard board, ChessPosition currentPosition, ChessPosition upLeft, Collection<ChessMove> validMoves, ChessPosition upRight, ChessPosition downLeft, ChessPosition downRight) {
        if (upLeft.getRow() <= 7 && upLeft.getColumn() >= 0) { // if in bounds
            if (board.getPiece(upLeft) != null) { //if there is a piece
                if (board.getPiece(upLeft).pieceColor != board.getPiece(currentPosition).pieceColor) { // if piece can be taken
                    validMoves.add(new ChessMove(currentPosition, upLeft, null));
                }
            } else { //if move is in bounds and empty
                validMoves.add(new ChessMove(currentPosition, upLeft, null));
            }
        }
        if (upRight.getRow() <= 7 && upRight.getColumn() <= 7) { // if in bounds
            if (board.getPiece(upRight) != null) { //if there is a piece
                if (board.getPiece(upRight).pieceColor != board.getPiece(currentPosition).pieceColor) { // if piece can be taken
                    validMoves.add(new ChessMove(currentPosition, upRight, null));
                }
            } else { //if move is in bounds and empty
                validMoves.add(new ChessMove(currentPosition, upRight, null));
            }
        }
        if (downLeft.getRow() >= 0 && downLeft.getColumn() >= 0) { // if in bounds
            if (board.getPiece(downLeft) != null) { //if there is a piece
                if (board.getPiece(downLeft).pieceColor != board.getPiece(currentPosition).pieceColor) { // if piece can be taken
                    validMoves.add(new ChessMove(currentPosition, downLeft, null));
                }
            } else { //if move is in bounds and empty
                validMoves.add(new ChessMove(currentPosition, downLeft, null));
            }
        }
        if (downRight.getRow() >= 0 && downRight.getColumn() <= 7) { // if in bounds
            if (board.getPiece(downRight) != null) { //if there is a piece
                if (board.getPiece(downRight).pieceColor != board.getPiece(currentPosition).pieceColor) { // if piece can be taken
                    validMoves.add(new ChessMove(currentPosition, downRight, null));
                }
            } else { //if move is in bounds and empty
                validMoves.add(new ChessMove(currentPosition, downRight, null));
            }
        }
    }
    public Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition currentPosition) {
        Collection<ChessMove> validMoves = new HashSet<>();
        for (ChessMove move : bishopMoves(board, currentPosition))
            validMoves.add(move);
        for (ChessMove move : rookMoves(board, currentPosition))
            validMoves.add(move);
        return validMoves;
    }
    public Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition currentPosition) {
        Collection<ChessMove> validMoves  = new HashSet<>();
        ChessPosition upLeft = new ChessPosition(currentPosition.getRow() + 2, currentPosition.getColumn());
        ChessPosition upRight = new ChessPosition(currentPosition.getRow() + 2, currentPosition.getColumn() + 2);
        ChessPosition downLeft = new ChessPosition(currentPosition.getRow(), currentPosition.getColumn());
        ChessPosition downRight = new ChessPosition(currentPosition.getRow(), currentPosition.getColumn() + 2);
        ChessPosition left = new ChessPosition(currentPosition.getRow() + 1, currentPosition.getColumn());
        ChessPosition down = new ChessPosition(currentPosition.getRow(), currentPosition.getColumn() + 1);
        ChessPosition up = new ChessPosition(currentPosition.getRow() + 2, currentPosition.getColumn() + 1);
        ChessPosition right = new ChessPosition(currentPosition.getRow() + 1, currentPosition.getColumn() + 2);
        calcMovesAroundPiece(board, currentPosition, upLeft, validMoves, upRight, downLeft, downRight);
        if (up.getRow() <= 7) { // if in bounds
            if (board.getPiece(up) != null) { //if there is a piece
                if (board.getPiece(up).pieceColor != board.getPiece(currentPosition).pieceColor) { // if piece can be taken
                    validMoves.add(new ChessMove(currentPosition, up, null));
                }
            } else { //if move is in bounds and empty
                validMoves.add(new ChessMove(currentPosition, up, null));
            }
        }
        if (down.getRow() >= 0) { // if in bounds
            if (board.getPiece(down) != null) { //if there is a piece
                if (board.getPiece(down).pieceColor != board.getPiece(currentPosition).pieceColor) { // if piece can be taken
                    validMoves.add(new ChessMove(currentPosition, down, null));
                }
            } else { //if move is in bounds and empty
                validMoves.add(new ChessMove(currentPosition, down, null));
            }
        }
        if (left.getColumn() >= 0) { // if in bounds
            if (board.getPiece(left) != null) { //if there is a piece
                if (board.getPiece(left).pieceColor != board.getPiece(currentPosition).pieceColor) { // if piece can be taken
                    validMoves.add(new ChessMove(currentPosition, left, null));
                }
            } else { //if move is in bounds and empty
                validMoves.add(new ChessMove(currentPosition, left, null));
            }
        }
        if (right.getColumn() <= 7) { // if in bounds
            if (board.getPiece(right) != null) { //if there is a piece
                if (board.getPiece(right).pieceColor != board.getPiece(currentPosition).pieceColor) { // if piece can be taken
                    validMoves.add(new ChessMove(currentPosition, right, null));
                }
            } else { //if move is in bounds and empty
                validMoves.add(new ChessMove(currentPosition, right, null));
            }
        }
        return validMoves;
    }
}