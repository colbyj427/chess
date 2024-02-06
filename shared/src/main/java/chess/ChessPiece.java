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


        //you create a chessMove instance for each possible move. cant move where own team is, disregard check.
        //promotion piece is handled for pawn, otherwise null. Each promotion option is a separate chessMove
        // instance with same start and end, four moves, cuz four promotions.

        //create a new class called ChessPieceMovement
            //create a set of subclasses to contain the movement rules for each piece.

        //get piece function.
        ChessPiece my_piece = board.getPiece(myPosition);
        //switch statement on which type
        switch (my_piece.getPieceType()) {
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
        //instantiate piece movement rules for that piece.

        //returns a collection of moves
        return new ArrayList<>();


    }

    public Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition currentPosition) {
        Collection<ChessMove> validMoves  = new HashSet<>();
        //while loop for while a value is less than 8 or more than 0. May need 4 loops.
        //one for going up left, up right, down left, down right.

        //if position is occupied, do not add, break loop if in one as it can't move past it.
        boolean keepMoving = true;
        ChessPosition upLeft = new ChessPosition(currentPosition.getRow() + 1, currentPosition.getColumn() + 1);
        ChessPosition upRight = new ChessPosition(currentPosition.getRow() + 1, currentPosition.getColumn() + 1);
        ChessPosition downLeft = new ChessPosition(currentPosition.getRow() + 1, currentPosition.getColumn() + 1);
        ChessPosition downRight = new ChessPosition(currentPosition.getRow() + 1, currentPosition.getColumn() + 1);

        int row;
        int column;

        //up and left:
        while (keepMoving) {
            // check the upper left boundaries
            if (upLeft.getRow() >=7 || upLeft.getColumn() <= 0) {
                keepMoving=false;
                break;
            }
        else {
                //create new position to add if no same team piece is there.
                row = upLeft.getRow() + 1;
                column = upLeft.getColumn() + 1;
                upLeft = new ChessPosition(row + 1, column - 1);

                // if there is a piece there, either take it and stop, or stop if your team.
                if (board.getPiece(upLeft) != null) {
                    if (board.getPiece(currentPosition).pieceColor == board.getPiece(upLeft).pieceColor) {
                        keepMoving=false;
                        break;
                    }
                    else {
                        validMoves.add(new ChessMove(currentPosition, upLeft, null));
                        keepMoving = false;
                        break;
                    }
                } else {
                    validMoves.add(new ChessMove(currentPosition, upLeft, null));
                }
            }
            }
        //up and right
        keepMoving = true;
        while (keepMoving) {
            // check the upper right boundaries
            if (upRight.getRow() >=7 || upRight.getColumn() >= 7) {
                keepMoving=false;
                break;
            }
            else {
                //create new position to add if no same team piece is there.
                row = upRight.getRow() + 1;
                column = upRight.getColumn() + 1;
                upRight = new ChessPosition(row + 1, column + 1);

                // if there is a piece there, either take it and stop, or stop if your team.
                if (board.getPiece(upRight) != null) {
                    if (board.getPiece(currentPosition).pieceColor == board.getPiece(upRight).pieceColor) {
                        keepMoving=false;
                        break;
                    }
                    else {
                        validMoves.add(new ChessMove(currentPosition, upRight, null));
                        keepMoving = false;
                        break;
                    }
                } else {
                    validMoves.add(new ChessMove(currentPosition, upRight, null));
                }
            }
        }
        //down and left
        keepMoving = true;
        while (keepMoving) {
            // check lower left boundaries
            if (downLeft.getRow() <= 0 || downLeft.getColumn() <= 0) {
                keepMoving=false;
                break;
            }
            else {
                //create new position to add if no same team piece is there.
                row = downLeft.getRow() + 1;
                column = downLeft.getColumn() + 1;
                downLeft = new ChessPosition(row - 1, column - 1);

                // if there is a piece there, either take it and stop, or stop if your team.
                if (board.getPiece(downLeft) != null) {
                    if (board.getPiece(currentPosition).pieceColor == board.getPiece(downLeft).pieceColor) {
                        keepMoving=false;
                        break;
                    }
                    else {
                        validMoves.add(new ChessMove(currentPosition, downLeft, null));
                        keepMoving = false;
                        break;
                    }
                } else {
                    validMoves.add(new ChessMove(currentPosition, downLeft, null));
                }
            }
        }

        //down and right
        keepMoving = true;
        while (keepMoving) {
            // check the lower right boundaries
            if (downRight.getRow() <= 0 || downRight.getColumn() >= 7) {
                keepMoving=false;
                break;
            }
            else {
                //create new position to add if no same team piece is there.
                row = downRight.getRow() + 1;
                column = downRight.getColumn() + 1;
                downRight = new ChessPosition(row - 1, column + 1);

                // if there is a piece there, either take it and stop, or stop if your team.
                if (board.getPiece(downRight) != null) {
                    if (board.getPiece(currentPosition).pieceColor == board.getPiece(downRight).pieceColor) {
                        keepMoving=false;
                        break;
                    }
                    else {
                        validMoves.add(new ChessMove(currentPosition, downRight, null));
                        keepMoving = false;
                        break;
                    }
                } else {
                    validMoves.add(new ChessMove(currentPosition, downRight, null));
                }
            }
        }

//    validMoves.add(move)
        return validMoves;
    }
    public Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition currentPosition) {
        Collection<ChessMove> validMoves = new HashSet<>();

        //if position is occupied, do not add, break loop if in one as it can't move past it.
        boolean keepMoving = true;
        ChessPosition up = new ChessPosition(currentPosition.getRow() + 1, currentPosition.getColumn() + 1);
        ChessPosition down = new ChessPosition(currentPosition.getRow() + 1, currentPosition.getColumn() + 1);
        ChessPosition left = new ChessPosition(currentPosition.getRow() + 1, currentPosition.getColumn() + 1);
        ChessPosition right = new ChessPosition(currentPosition.getRow() + 1, currentPosition.getColumn() + 1);

        int row;
        int column;

        //up:
        while (keepMoving) {
            // check the upper boundary
            if (up.getRow() >=7) {
                keepMoving=false;
                break;
            }
            else {
                //create new position to add if no same team piece is there.
                row = up.getRow() + 1;
                column = up.getColumn() + 1;
                up = new ChessPosition(row + 1, column);

                // if there is a piece there, either take it and stop, or stop if your team.
                if (board.getPiece(up) != null) {
                    if (board.getPiece(currentPosition).pieceColor == board.getPiece(up).pieceColor) {
                        keepMoving=false;
                        break;
                    }
                    else {
                        validMoves.add(new ChessMove(currentPosition, up, null));
                        keepMoving = false;
                        break;
                    }
                } else {
                    validMoves.add(new ChessMove(currentPosition, up, null));
                }
            }
        }
        //down:
        keepMoving = true;
        while (keepMoving) {
            // check the lower boundary
            if (down.getRow() <=0) {
                keepMoving=false;
                break;
            }
            else {
                //create new position to add if no same team piece is there.
                row = down.getRow() + 1;
                column = down.getColumn() + 1;
                down = new ChessPosition(row - 1, column);

                // if there is a piece there, either take it and stop, or stop if your team.
                if (board.getPiece(down) != null) {
                    if (board.getPiece(currentPosition).pieceColor == board.getPiece(down).pieceColor) {
                        keepMoving=false;
                        break;
                    }
                    else {
                        validMoves.add(new ChessMove(currentPosition, down, null));
                        keepMoving = false;
                        break;
                    }
                } else {
                    validMoves.add(new ChessMove(currentPosition, down, null));
                }
            }
        }
        //left:
        keepMoving = true;
        while (keepMoving) {
            // check the left boundary
            if (left.getColumn() <= 0) {
                keepMoving=false;
                break;
            }
            else {
                //create new position to add if no same team piece is there.
                row = left.getRow() + 1;
                column = left.getColumn() + 1;
                left = new ChessPosition(row, column - 1);

                // if there is a piece there, either take it and stop, or stop if your team.
                if (board.getPiece(left) != null) {
                    if (board.getPiece(currentPosition).pieceColor == board.getPiece(down).pieceColor) {
                        keepMoving=false;
                        break;
                    }
                    else {
                        validMoves.add(new ChessMove(currentPosition, left, null));
                        keepMoving = false;
                        break;
                    }
                } else {
                    validMoves.add(new ChessMove(currentPosition, left, null));
                }
            }
        }
        //right
        keepMoving = true;
        while (keepMoving) {
            // check the right boundary
            if (right.getColumn() >=7) {
                keepMoving=false;
                break;
            }
            else {
                //create new position to add if no same team piece is there.
                row = right.getRow() + 1;
                column = right.getColumn() + 1;
                right = new ChessPosition(row, column + 1);

                // if there is a piece there, either take it and stop, or stop if your team.
                if (board.getPiece(right) != null) {
                    if (board.getPiece(currentPosition).pieceColor == board.getPiece(right).pieceColor) {
                        keepMoving=false;
                        break;
                    }
                    else {
                        validMoves.add(new ChessMove(currentPosition, right, null));
                        keepMoving = false;
                        break;
                    }
                } else {
                    validMoves.add(new ChessMove(currentPosition, right, null));
                }
            }
        }
        return validMoves;
    }
    public Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition currentPosition) {
        Collection<ChessMove> validMoves = new HashSet<>();

        // white pawn moves
        if (board.getPiece(currentPosition).pieceColor == ChessGame.TeamColor.WHITE) {
            ChessPosition forwardOne=new ChessPosition(currentPosition.getRow() + 2, currentPosition.getColumn() + 1);
            ChessPosition forwardTwo=new ChessPosition(currentPosition.getRow() + 3, currentPosition.getColumn() + 1);
            ChessPosition takePieceLeft=new ChessPosition(currentPosition.getRow() + 2, currentPosition.getColumn());
            ChessPosition takePieceRight=new ChessPosition(currentPosition.getRow() + 2, currentPosition.getColumn() + 2);

            //promotion moves
            if (forwardOne.getRow() == 7) {
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
            } else if (currentPosition.getRow() == 1) { //pawn hasn't moved yet
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
            } else { // add moves not promotion and not first moves
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
        } else { // black pawn moves
            ChessPosition forwardOne=new ChessPosition(currentPosition.getRow(), currentPosition.getColumn() + 1);
            ChessPosition forwardTwo=new ChessPosition(currentPosition.getRow() - 1, currentPosition.getColumn() + 1);
            ChessPosition takePieceLeft=new ChessPosition(currentPosition.getRow(), currentPosition.getColumn());
            ChessPosition takePieceRight=new ChessPosition(currentPosition.getRow(), currentPosition.getColumn() + 2);

            //promotion moves
            if (forwardOne.getRow() == 0) {
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
            } else if (currentPosition.getRow() == 6) { //pawn hasn't moved yet
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
            } else { // add moves not promotion and not first moves
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
        }
        return validMoves;
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

        // up and left:
        // if in bounds
        if (upLeft.getRow() <= 7 && upLeft.getColumn() >= 0) {
            //if there is a piece
            if (board.getPiece(upLeft) != null) {
                // if piece can be taken
                if (board.getPiece(upLeft).pieceColor != board.getPiece(currentPosition).pieceColor) {
                    validMoves.add(new ChessMove(currentPosition, upLeft, null));
                }
            } else { //if move is in bounds and empty
                validMoves.add(new ChessMove(currentPosition, upLeft, null));
            }
        }
        // if in bounds
        if (upRight.getRow() <= 7 && upRight.getColumn() <= 7) {
            //if there is a piece
            if (board.getPiece(upRight) != null) {
                // if piece can be taken
                if (board.getPiece(upRight).pieceColor != board.getPiece(currentPosition).pieceColor) {
                    validMoves.add(new ChessMove(currentPosition, upRight, null));
                }
            } else { //if move is in bounds and empty
                validMoves.add(new ChessMove(currentPosition, upRight, null));
            }
        }
        // if in bounds
        if (downLeft.getRow() >= 0 && downLeft.getColumn() >= 0) {
            //if there is a piece
            if (board.getPiece(downLeft) != null) {
                // if piece can be taken
                if (board.getPiece(downLeft).pieceColor != board.getPiece(currentPosition).pieceColor) {
                    validMoves.add(new ChessMove(currentPosition, downLeft, null));
                }
            } else { //if move is in bounds and empty
                validMoves.add(new ChessMove(currentPosition, downLeft, null));
            }
        }
        // if in bounds
        if (downRight.getRow() >= 0 && downRight.getColumn() <= 7) {
            //if there is a piece
            if (board.getPiece(downRight) != null) {
                // if piece can be taken
                if (board.getPiece(downRight).pieceColor != board.getPiece(currentPosition).pieceColor) {
                    validMoves.add(new ChessMove(currentPosition, downRight, null));
                }
            } else { //if move is in bounds and empty
                validMoves.add(new ChessMove(currentPosition, downRight, null));
            }
        }
        // if in bounds
        if (leftUp.getRow() <= 7 && leftUp.getColumn() >= 0) {
            //if there is a piece
            if (board.getPiece(leftUp) != null) {
                // if piece can be taken
                if (board.getPiece(leftUp).pieceColor != board.getPiece(currentPosition).pieceColor) {
                    validMoves.add(new ChessMove(currentPosition, leftUp, null));
                }
            } else { //if move is in bounds and empty
                validMoves.add(new ChessMove(currentPosition, leftUp, null));
            }
        }
        // if in bounds
        if (leftDown.getRow() >= 0 && leftDown.getColumn() >= 0) {
            //if there is a piece
            if (board.getPiece(leftDown) != null) {
                // if piece can be taken
                if (board.getPiece(leftDown).pieceColor != board.getPiece(currentPosition).pieceColor) {
                    validMoves.add(new ChessMove(currentPosition, leftDown, null));
                }
            } else { //if move is in bounds and empty
                validMoves.add(new ChessMove(currentPosition, leftDown, null));
            }
        }
        // if in bounds
        if (rightUp.getRow() <= 7 && rightUp.getColumn() <= 7) {
            //if there is a piece
            if (board.getPiece(rightUp) != null) {
                // if piece can be taken
                if (board.getPiece(rightUp).pieceColor != board.getPiece(currentPosition).pieceColor) {
                    validMoves.add(new ChessMove(currentPosition, rightUp, null));
                }
            } else { //if move is in bounds and empty
                validMoves.add(new ChessMove(currentPosition, rightUp, null));
            }
        }
        // if in bounds
        if (rightDown.getRow() >= 0 && rightDown.getColumn() <= 7) {
            //if there is a piece
            if (board.getPiece(rightDown) != null) {
                // if piece can be taken
                if (board.getPiece(rightDown).pieceColor != board.getPiece(currentPosition).pieceColor) {
                    validMoves.add(new ChessMove(currentPosition, rightDown, null));
                }
            } else { //if move is in bounds and empty
                validMoves.add(new ChessMove(currentPosition, rightDown, null));
            }
        }
        return validMoves;
    }
    public Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition currentPosition) {
        Collection<ChessMove> validMoves = new HashSet<>();
        //just call for rook and bishop
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

        // up and left:
        // if in bounds
        if (upLeft.getRow() <= 7 && upLeft.getColumn() >= 0) {
            //if there is a piece
            if (board.getPiece(upLeft) != null) {
                // if piece can be taken
                if (board.getPiece(upLeft).pieceColor != board.getPiece(currentPosition).pieceColor) {
                    validMoves.add(new ChessMove(currentPosition, upLeft, null));
                }
            } else { //if move is in bounds and empty
                validMoves.add(new ChessMove(currentPosition, upLeft, null));
            }
        }
        // if in bounds
        if (upRight.getRow() <= 7 && upRight.getColumn() <= 7) {
            //if there is a piece
            if (board.getPiece(upRight) != null) {
                // if piece can be taken
                if (board.getPiece(upRight).pieceColor != board.getPiece(currentPosition).pieceColor) {
                    validMoves.add(new ChessMove(currentPosition, upRight, null));
                }
            } else { //if move is in bounds and empty
                validMoves.add(new ChessMove(currentPosition, upRight, null));
            }
        }
        // if in bounds
        if (downLeft.getRow() >= 0 && downLeft.getColumn() >= 0) {
            //if there is a piece
            if (board.getPiece(downLeft) != null) {
                // if piece can be taken
                if (board.getPiece(downLeft).pieceColor != board.getPiece(currentPosition).pieceColor) {
                    validMoves.add(new ChessMove(currentPosition, downLeft, null));
                }
            } else { //if move is in bounds and empty
                validMoves.add(new ChessMove(currentPosition, downLeft, null));
            }
        }
        // if in bounds
        if (downRight.getRow() >= 0 && downRight.getColumn() <= 7) {
            //if there is a piece
            if (board.getPiece(downRight) != null) {
                // if piece can be taken
                if (board.getPiece(downRight).pieceColor != board.getPiece(currentPosition).pieceColor) {
                    validMoves.add(new ChessMove(currentPosition, downRight, null));
                }
            } else { //if move is in bounds and empty
                validMoves.add(new ChessMove(currentPosition, downRight, null));
            }
        }
        // if in bounds
        if (up.getRow() <= 7) {
            //if there is a piece
            if (board.getPiece(up) != null) {
                // if piece can be taken
                if (board.getPiece(up).pieceColor != board.getPiece(currentPosition).pieceColor) {
                    validMoves.add(new ChessMove(currentPosition, up, null));
                }
            } else { //if move is in bounds and empty
                validMoves.add(new ChessMove(currentPosition, up, null));
            }
        }
        // if in bounds
        if (down.getRow() >= 0) {
            //if there is a piece
            if (board.getPiece(down) != null) {
                // if piece can be taken
                if (board.getPiece(down).pieceColor != board.getPiece(currentPosition).pieceColor) {
                    validMoves.add(new ChessMove(currentPosition, down, null));
                }
            } else { //if move is in bounds and empty
                validMoves.add(new ChessMove(currentPosition, down, null));
            }
        }
        // if in bounds
        if (left.getColumn() >= 0) {
            //if there is a piece
            if (board.getPiece(left) != null) {
                // if piece can be taken
                if (board.getPiece(left).pieceColor != board.getPiece(currentPosition).pieceColor) {
                    validMoves.add(new ChessMove(currentPosition, left, null));
                }
            } else { //if move is in bounds and empty
                validMoves.add(new ChessMove(currentPosition, left, null));
            }
        }
        // if in bounds
        if (right.getColumn() <= 7) {
            //if there is a piece
            if (board.getPiece(right) != null) {
                // if piece can be taken
                if (board.getPiece(right).pieceColor != board.getPiece(currentPosition).pieceColor) {
                    validMoves.add(new ChessMove(currentPosition, right, null));
                }
            } else { //if move is in bounds and empty
                validMoves.add(new ChessMove(currentPosition, right, null));
            }
        }
        return validMoves;
    }


}