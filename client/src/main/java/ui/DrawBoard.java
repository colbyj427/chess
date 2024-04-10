package ui;

import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import static ui.EscapeSequences.*;

public class DrawBoard {

  private static final int BOARD_SIZE_IN_SQUARES=8;
  private static final int SQUARE_SIZE_IN_CHARS=3;
  //private static ChessPiece[][] squares=new ChessPiece[8][8];
  private static ChessPiece[][] squares;
  //pass in a chessboard object, then get the piece color from the pieces and print that color.
  private static final String EMPTY=" ";
  private static Collection<ChessPosition> endPositions= null;

  public static void main(ChessPiece[][] boardSetup, String color, Collection<ChessPosition> highlightMoves) {
    squares = boardSetup;
    endPositions= highlightMoves;
    var out=new PrintStream(System.out, true, StandardCharsets.UTF_8);
    out.print(ERASE_SCREEN);
    color = color.toLowerCase();
    out.print("\n");
    if (color.equals("black")) {
      //board with black at bottom
      drawHeadersBackwards(out);
      drawChessBoardBlackBottom(out, squares);
      drawHeadersBackwards(out);

      out.print(SET_BG_COLOR_BLACK);
      out.print(SET_TEXT_COLOR_WHITE);
      out.println();
    }
    else {
      //board with white at bottom
      drawHeaders(out);
      drawChessBoard(out, squares);
      drawHeaders(out);

      out.print(SET_BG_COLOR_BLACK);
      out.print(SET_TEXT_COLOR_WHITE);
    }
    endPositions= null;
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
      for (int boardCol=7; boardCol >= 0; --boardCol) { // draw actual squares, one at a time // ******************** could flip this? didn't really work?
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
    ChessPosition position = new ChessPosition(Integer.valueOf(row), 8-boardCol);
    if (pieceArray[((Integer.valueOf(row))-1)][7-boardCol] != null) {
      pieceType=getPieceType(row, 7-boardCol, pieceArray);
      pieceColor=getPieceColor(row, 7-boardCol, pieceArray);
    }
    if (boardCol % 2 == 0) {
      chooseWhiteSquare(out, position, pieceType, pieceColor);
    } else {
      chooseBlackSquare(out, position, pieceType, pieceColor);
    }
  }
  private static void drawBlackRowSquares(PrintStream out, String row, ChessPiece[][] pieceArray, int boardCol) {
    String pieceType = " ";
    String pieceColor = "WHITE";
    ChessPosition position = new ChessPosition(Integer.valueOf(row), 8-boardCol); //may need to decrement row by one or subtract col from 7
    if (pieceArray[(Integer.valueOf(row)-1)][7-boardCol] != null) {
      pieceType=getPieceType(row, 7-boardCol, pieceArray);
      pieceColor=getPieceColor(row, 7-boardCol, pieceArray);
    }
    if (boardCol % 2 == 1) {
      chooseWhiteSquare(out, position, pieceType, pieceColor);
    } else {
      chooseBlackSquare(out, position, pieceType, pieceColor);
    }
  }
  private static void chooseBlackSquare(PrintStream out, ChessPosition position, String pieceType, String pieceColor) {
    if (endPositions != null) {
      if (endPositions.contains(position)) {
        drawHighligtedBlackSquare(out, pieceType, pieceColor);
      } else {
        drawBlackSquare(out, pieceType, pieceColor);
      }
    } else {
      drawBlackSquare(out, pieceType, pieceColor);
    }
  }
  private static void chooseWhiteSquare(PrintStream out, ChessPosition position, String pieceType, String pieceColor) {
    if (endPositions != null) {
      if (endPositions.contains(position)) {
        drawHighligtedWhiteSquare(out, pieceType, pieceColor);
      } else {
        drawWhiteSquare(out, pieceType, pieceColor);
      }
    }
    else {
      drawWhiteSquare(out, pieceType, pieceColor);
    }
  }
  private static void drawBlackSquare(PrintStream out, String pieceType, String pieceColor) {
    setBlack(out);
    int prefixLength=SQUARE_SIZE_IN_CHARS / 2;
    int suffixLength=SQUARE_SIZE_IN_CHARS - prefixLength - 1;
    out.print(EMPTY.repeat(prefixLength));
    printPiece(out, pieceType, pieceColor);
    out.print(EMPTY.repeat(suffixLength));
    setBlack(out);
  }
  private static void drawWhiteSquare(PrintStream out, String pieceType, String pieceColor) {
    setWhite(out);
    int prefixLength=SQUARE_SIZE_IN_CHARS / 2;
    int suffixLength=SQUARE_SIZE_IN_CHARS - prefixLength - 1;
    out.print(EMPTY.repeat(prefixLength));
    printPiece(out, pieceType, pieceColor);
    out.print(EMPTY.repeat(suffixLength));
    setBlack(out);
  }
  private static void drawHighligtedWhiteSquare(PrintStream out, String pieceType, String pieceColor) {
    setGreen(out);
    int prefixLength=SQUARE_SIZE_IN_CHARS / 2;
    int suffixLength=SQUARE_SIZE_IN_CHARS - prefixLength - 1;
    out.print(EMPTY.repeat(prefixLength));
    printPiece(out, pieceType, pieceColor);
    out.print(EMPTY.repeat(suffixLength));
    setBlack(out);
  }
  private static void drawHighligtedBlackSquare(PrintStream out, String pieceType, String pieceColor) {
    setDarkGreen(out);
    int prefixLength=SQUARE_SIZE_IN_CHARS / 2;
    int suffixLength=SQUARE_SIZE_IN_CHARS - prefixLength - 1;
    out.print(EMPTY.repeat(prefixLength));
    printPiece(out, pieceType, pieceColor);
    out.print(EMPTY.repeat(suffixLength));
    setBlack(out);
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
  private static void setGreen(PrintStream out) {
    out.print(SET_BG_COLOR_GREEN);
    out.print(SET_TEXT_COLOR_GREEN);
  }
  private static void setDarkGreen(PrintStream out) {
    out.print(SET_BG_COLOR_DARK_GREEN);
    out.print(SET_TEXT_COLOR__DARK_GREEN);
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
}

