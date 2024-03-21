package ui;

import chess.ChessGame;
import chess.ChessPiece;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class DrawBoard {

  private static final int BOARD_SIZE_IN_SQUARES=8;
  private static final int SQUARE_SIZE_IN_CHARS=3;
  private static ChessPiece[][] squares=new ChessPiece[8][8];
  //pass in a chessboard object, then get the piece color from the pieces and print that color.
  private static final String EMPTY=" ";

  public static void main() {
    var out=new PrintStream(System.out, true, StandardCharsets.UTF_8);
    setArrayToStart();
    out.print(ERASE_SCREEN);

    //board with black at bottom
    drawHeadersBackwards(out);
    drawChessBoardBlackBottom(out, squares);
    drawHeadersBackwards(out);

    out.print(SET_BG_COLOR_BLACK);
    out.print(SET_TEXT_COLOR_WHITE);
    out.println();

    //board with white at bottom
    drawHeaders(out);
    drawChessBoard(out, squares);
    drawHeaders(out);

    out.print(SET_BG_COLOR_BLACK);
    out.print(SET_TEXT_COLOR_WHITE);
  }

  private static void drawHeaders(PrintStream out) {
    setLightGray(out);

    String[] headers={" ", "a", "b", "c", "d", "e", "f", "g", "h", " "};
    for (int boardCol=0; boardCol < BOARD_SIZE_IN_SQUARES + 1; ++boardCol) {
      drawHeader(out, headers[boardCol]);
    }
    out.println();
  }
  private static void drawHeadersBackwards(PrintStream out) {
    setLightGray(out);

    String[] headers={" ", "h", "g", "f", "e", "d", "c", "b", "a", " "};
    for (int boardCol=0; boardCol < BOARD_SIZE_IN_SQUARES + 1; ++boardCol) {
      drawHeader(out, headers[boardCol]);
    }
    out.println();
  }

  private static void drawHeader(PrintStream out, String headerText) {
    setLightGray(out);
    out.print(EMPTY);

    printHeaderText(out, headerText);

    setLightGray(out);
    out.print(EMPTY);
  }

  private static void printHeaderText(PrintStream out, String headerString) {
    out.print(SET_BG_COLOR_LIGHT_GREY);
    out.print(SET_TEXT_COLOR_BLACK);
    out.print(headerString);
    setBlack(out);
  }

  private static void drawChessBoard(PrintStream out, ChessPiece[][] pieceArray) {
    for (int boardRow=7; boardRow >= 0; --boardRow) {
      String boardRowString=Integer.toString(boardRow + 1);
      if (boardRow % 2 == 0) {
        drawWhiteRow(out, boardRowString, pieceArray, false);
      } else {
        drawBlackRow(out, boardRowString, pieceArray, false);
      }
    }
  }
  private static void drawChessBoardBlackBottom(PrintStream out, ChessPiece[][] pieceArray) {
    for (int boardRow=0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {
      String boardRowString=Integer.toString(boardRow + 1);
      if (boardRow % 2 == 0) {
        drawWhiteRow(out, boardRowString, pieceArray, true);
      } else {
        drawBlackRow(out, boardRowString, pieceArray, true);
      }
    }
  }
  private static void drawWhiteRow(PrintStream out, String row, ChessPiece[][] pieceArray, boolean blackBottom) {
    printRowNum(out, row);
    if (blackBottom == true) {
      for (int boardCol=0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) { // draw actual squares, one at a time
        drawWhiteRowSquares(out, row, pieceArray, boardCol);
      }
    }
    else {
      for (int boardCol=7; boardCol >= 0; --boardCol) { // draw actual squares, one at a time
        drawWhiteRowSquares(out, row, pieceArray, boardCol);
      }
    }
    printRowNum(out, row);
    setLightGray(out);
    out.println();
  }
  private static void drawBlackRow(PrintStream out, String row, ChessPiece[][] pieceArray, boolean blackBottom) {
    printRowNum(out, row);
    if (blackBottom == true) {
      for (int boardCol=0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) { // draw actual squares, one at a time
        drawBlackRowSquares(out, row, pieceArray, boardCol);
      }
    }
    else {
      for (int boardCol=7; boardCol >= 0; --boardCol) { // draw actual squares, one at a time
        drawBlackRowSquares(out, row, pieceArray, boardCol);
      }
    }
    printRowNum(out, row);
    setLightGray(out);
    out.println();
  }
  private static void drawWhiteRowSquares(PrintStream out, String row, ChessPiece[][] pieceArray, int boardCol) {
    String pieceType = " ";
    String pieceColor = "WHITE";
    if (squares[((Integer.valueOf(row))-1)][boardCol] != null) {
      pieceType=getPieceType(row, boardCol, pieceArray);
      pieceColor=getPieceColor(row, boardCol, pieceArray);
    }
    if (boardCol % 2 == 0) {
      setWhite(out);
      int prefixLength=SQUARE_SIZE_IN_CHARS / 2;
      int suffixLength=SQUARE_SIZE_IN_CHARS - prefixLength - 1;
      out.print(EMPTY.repeat(prefixLength));
      printPiece(out, pieceType, pieceColor);
      out.print(EMPTY.repeat(suffixLength));
      setBlack(out);
    } else {
      setBlack(out);
      int prefixLength=SQUARE_SIZE_IN_CHARS / 2;
      int suffixLength=SQUARE_SIZE_IN_CHARS - prefixLength - 1;
      out.print(EMPTY.repeat(prefixLength));
      printPiece(out, pieceType, pieceColor);
      out.print(EMPTY.repeat(suffixLength));
      setBlack(out);
    }
  }

  private static void drawBlackRowSquares(PrintStream out, String row, ChessPiece[][] pieceArray, int boardCol) {
    String pieceType = " ";
    String pieceColor = "WHITE";
    if (squares[(Integer.valueOf(row)-1)][boardCol] != null) {
      pieceType=getPieceType(row, boardCol, pieceArray);
      pieceColor=getPieceColor(row, boardCol, pieceArray);
    }
    if (boardCol % 2 == 1) {
      setWhite(out);
      int prefixLength=SQUARE_SIZE_IN_CHARS / 2;
      int suffixLength=SQUARE_SIZE_IN_CHARS - prefixLength - 1;
      out.print(EMPTY.repeat(prefixLength));
      printPiece(out, pieceType, pieceColor);
      out.print(EMPTY.repeat(suffixLength));
      setBlack(out);
    } else {
      setBlack(out);
      int prefixLength=SQUARE_SIZE_IN_CHARS / 2;
      int suffixLength=SQUARE_SIZE_IN_CHARS - prefixLength - 1;
      out.print(EMPTY.repeat(prefixLength));
      printPiece(out, pieceType, pieceColor);
      out.print(EMPTY.repeat(suffixLength));
      setBlack(out);
    }
  }

  private static void setBlack(PrintStream out) {
    out.print(SET_BG_COLOR_BLACK);
    out.print(SET_TEXT_COLOR_BLACK);
  }

  private static void setWhite(PrintStream out) {
    out.print(SET_BG_COLOR_WHITE);
    out.print(SET_TEXT_COLOR_WHITE);
  }

  private static void setLightGray(PrintStream out) {
    out.print(SET_BG_COLOR_LIGHT_GREY);
    out.print(SET_TEXT_COLOR_LIGHT_GREY);
  }

  private static void printPiece(PrintStream out, String type, String color) {
    if (type == " ") {
      out.print(" ");
    }
    else if (color.equals("BLACK")) {
      out.print(SET_TEXT_COLOR_BLUE);
      out.print(type);
    }
    else {
      out.print(SET_TEXT_COLOR_RED);
      out.print(type);
    }
  }
  private static void printRowNum(PrintStream out, String rowNum) {
    out.print(SET_BG_COLOR_LIGHT_GREY);
    out.print(SET_TEXT_COLOR_BLACK);
    out.print(EMPTY);
    out.print(rowNum);
    out.print(EMPTY);
    setWhite(out);
  }
  private static String getPieceType(String row, int numCol, ChessPiece[][] pieceArray) {
    int numRow = Integer.valueOf(row);
    ChessPiece.PieceType pieceType = squares[numRow-1][numCol].getPieceType();
    if (pieceType.equals(ChessPiece.PieceType.KING)) {
      return "K";
    }if (pieceType.equals(ChessPiece.PieceType.QUEEN)) {
      return "Q";
    }if (pieceType.equals(ChessPiece.PieceType.BISHOP)) {
      return "B";
    }if (pieceType.equals(ChessPiece.PieceType.ROOK)) {
      return "R";
    }if (pieceType.equals(ChessPiece.PieceType.KNIGHT)) {
      return "N";
    }if (pieceType.equals(ChessPiece.PieceType.PAWN)) {
      return "P";
    }
    return " ";
  }
  private static String getPieceColor(String row, int numCol, ChessPiece[][] pieceArray) {
    int numRow = Integer.valueOf(row);
    ChessGame.TeamColor pieceColor = squares[numRow-1][numCol].getTeamColor();
    return pieceColor.toString();
  }
  private static ChessPiece[][] setArrayToStart() {
    for (int i=0; i < squares.length; i++) {
      for (int j=0; j < squares[i].length; j++) {
        squares[i][j]=null;
      }
    }
    for (int boardCol=0; boardCol <= 7; ++boardCol) { //top pawns
      squares[1][boardCol]=new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
    }
    squares[0][0]=new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
    squares[0][1]=new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
    squares[0][2]=new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
    squares[0][3]=new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
    squares[0][4]=new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
    squares[0][5]=new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
    squares[0][6]=new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
    squares[0][7]=new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
    for (int boardCol=0; boardCol <= 7; ++boardCol) { //bottom pawns
      squares[6][boardCol]=new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
    }
    squares[7][0]=new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
    squares[7][1]=new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
    squares[7][2]=new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
    squares[7][3]=new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
    squares[7][4]=new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
    squares[7][5]=new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
    squares[7][6]=new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
    squares[7][7]=new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);

    return squares;
  }
}

