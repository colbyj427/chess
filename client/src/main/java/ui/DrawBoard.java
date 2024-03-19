package ui;

import chess.ChessPiece;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.Array;

import static ui.EscapeSequences.*;

public class DrawBoard {

  private static final int BOARD_SIZE_IN_SQUARES=8;
  private static final int SQUARE_SIZE_IN_CHARS=3;
  private static String[][] squares=new String[8][8];
  //pass in a chessboard object, then get the piece color from the pieces and print that color.
  private static final String EMPTY=" ";

  public static void main() {
    var out=new PrintStream(System.out, true, StandardCharsets.UTF_8);
    setArrayToStart();
    out.print(ERASE_SCREEN);
    //board with black at bottom
    drawHeaders(out);
    drawChessBoardBlackBottom(out, squares);
    drawHeaders(out);

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

  private static void drawChessBoard(PrintStream out, String[][] pieceArray) {
    for (int boardRow=0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {
      String boardRowString=Integer.toString(boardRow + 1);
      if (boardRow % 2 == 0) {
        drawWhiteRowOfSquares(out, boardRowString, pieceArray);
      } else {
        drawBlackRowOfSquares(out, boardRowString, pieceArray);
      }
    }
  }
  //extra methods to draw second board. could be replaced once pieces array has the color with it.
  private static void drawChessBoardBlackBottom(PrintStream out, String[][] pieceArray) {
    for (int boardRow=0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {
      String boardRowString=Integer.toString(boardRow + 1);
      if (boardRow % 2 == 0) {
        drawWhiteRowOfSquaresBlackBottom(out, boardRowString, pieceArray);
      } else {
        drawBlackRowOfSquaresBlackBottom(out, boardRowString, pieceArray);
      }
    }
  }
  private static void drawWhiteRowOfSquaresBlackBottom(PrintStream out, String row, String[][] pieceArray) {

    int intRow=Integer.valueOf(row);
    printRowNum(out, row);
    for (int boardCol=0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) { // draw actual squares, one at a time
      if (boardCol % 2 == 0) {
        setWhite(out);
        int prefixLength=SQUARE_SIZE_IN_CHARS / 2;
        int suffixLength=SQUARE_SIZE_IN_CHARS - prefixLength - 1;
        out.print(EMPTY.repeat(prefixLength));
        if (intRow == 7 || intRow == 8) {
          printBlackPiece(out, intRow, boardCol);
        } else {
          printWhitePiece(out, intRow, boardCol);
        }
        out.print(EMPTY.repeat(suffixLength));
        setBlack(out);
      } else {
        setBlack(out);
        int prefixLength=SQUARE_SIZE_IN_CHARS / 2;
        int suffixLength=SQUARE_SIZE_IN_CHARS - prefixLength - 1;
        out.print(EMPTY.repeat(prefixLength));
        if (intRow == 7 || intRow == 8) {
          printBlackPiece(out, intRow, boardCol);
        } else {
          printWhitePiece(out, intRow, boardCol);
        }
        out.print(EMPTY.repeat(suffixLength));
        setBlack(out);
      }
    }
    printRowNum(out, row);
    setLightGray(out);
    out.println();
  }

  private static void drawBlackRowOfSquaresBlackBottom(PrintStream out, String row, String[][] pieceArray) {
    int intRow=Integer.valueOf(row);
    printRowNum(out, row);
    for (int boardCol=0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) { // draw actual squares, one at a time
      if (boardCol % 2 == 1) {
        setWhite(out);
        int prefixLength=SQUARE_SIZE_IN_CHARS / 2;
        int suffixLength=SQUARE_SIZE_IN_CHARS - prefixLength - 1;
        out.print(EMPTY.repeat(prefixLength));
        if (intRow == 7 || intRow == 8) {
          printBlackPiece(out, intRow, boardCol);
        } else {
          printWhitePiece(out, intRow, boardCol);
        }
        out.print(EMPTY.repeat(suffixLength));
        setBlack(out);
      } else {
        setBlack(out);
        int prefixLength=SQUARE_SIZE_IN_CHARS / 2;
        int suffixLength=SQUARE_SIZE_IN_CHARS - prefixLength - 1;
        out.print(EMPTY.repeat(prefixLength));
        if (intRow == 7 || intRow == 8) {
          printBlackPiece(out, intRow, boardCol);
        } else {
          printWhitePiece(out, intRow, boardCol);
        }
        out.print(EMPTY.repeat(suffixLength));
        setBlack(out);
      }
    }
    printRowNum(out, row);
    setLightGray(out);
    out.println();
  }
  //********************

  private static void drawWhiteRowOfSquares(PrintStream out, String row, String[][] pieceArray) {

    int intRow=Integer.valueOf(row);
    printRowNum(out, row);
    for (int boardCol=0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) { // draw actual squares, one at a time
      if (boardCol % 2 == 0) {
        setWhite(out);
        int prefixLength=SQUARE_SIZE_IN_CHARS / 2;
        int suffixLength=SQUARE_SIZE_IN_CHARS - prefixLength - 1;
        out.print(EMPTY.repeat(prefixLength));
        if (intRow == 1 || intRow == 2) {
          printBlackPiece(out, intRow, boardCol);
        } else {
          printWhitePiece(out, intRow, boardCol);
        }
        out.print(EMPTY.repeat(suffixLength));
        setBlack(out);
      } else {
        setBlack(out);
        int prefixLength=SQUARE_SIZE_IN_CHARS / 2;
        int suffixLength=SQUARE_SIZE_IN_CHARS - prefixLength - 1;
        out.print(EMPTY.repeat(prefixLength));
        if (intRow == 1 || intRow == 2) {
          printBlackPiece(out, intRow, boardCol);
        } else {
          printWhitePiece(out, intRow, boardCol);
        }
        out.print(EMPTY.repeat(suffixLength));
        setBlack(out);
      }
    }
    printRowNum(out, row);
    setLightGray(out);
    out.println();
  }

  private static void drawBlackRowOfSquares(PrintStream out, String row, String[][] pieceArray) {
    int intRow=Integer.valueOf(row);
    printRowNum(out, row);
    for (int boardCol=0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) { // draw actual squares, one at a time
      if (boardCol % 2 == 1) {
        setWhite(out);
        int prefixLength=SQUARE_SIZE_IN_CHARS / 2;
        int suffixLength=SQUARE_SIZE_IN_CHARS - prefixLength - 1;
        out.print(EMPTY.repeat(prefixLength));
        if (intRow == 1 || intRow == 2) {
          printBlackPiece(out, intRow, boardCol);
        } else {
          printWhitePiece(out, intRow, boardCol);
        }
        out.print(EMPTY.repeat(suffixLength));
        setBlack(out);
      } else {
        setBlack(out);
        int prefixLength=SQUARE_SIZE_IN_CHARS / 2;
        int suffixLength=SQUARE_SIZE_IN_CHARS - prefixLength - 1;
        out.print(EMPTY.repeat(prefixLength));
        if (intRow == 1 || intRow == 2) {
          printBlackPiece(out, intRow, boardCol);
        } else {
          printWhitePiece(out, intRow, boardCol);
        }
        out.print(EMPTY.repeat(suffixLength));
        setBlack(out);
      }
    }
    printRowNum(out, row);
    setLightGray(out);
    out.println();
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

  private static void setRed(PrintStream out) {
    out.print(SET_BG_COLOR_RED);
    out.print(SET_TEXT_COLOR_RED);
  }

  private static void printWhitePiece(PrintStream out, int row, int col) {
    out.print(SET_TEXT_COLOR_GREEN);
    out.print(squares[row - 1][col]);
  }

  private static void printBlackPiece(PrintStream out, int row, int col) {
    out.print(SET_TEXT_COLOR_YELLOW);
    out.print(squares[row - 1][col]);
  }

  private static void printRowNum(PrintStream out, String rowNum) {
    out.print(SET_BG_COLOR_LIGHT_GREY);
    out.print(SET_TEXT_COLOR_BLACK);
    out.print(EMPTY);
    out.print(rowNum);
    out.print(EMPTY);
    setWhite(out);
  }

  private static String[][] setArrayToStart() {
    for (int i=0; i < squares.length; i++) {
      for (int j=0; j < squares[i].length; j++) {
        squares[i][j]=" ";
      }
    }
      for (int boardCol=0; boardCol <= 7; ++boardCol) { //top pawns
        squares[1][boardCol]="P";
      }
      squares[0][0]="R";
      squares[0][1]="N";
      squares[0][2]="B";
      squares[0][3]="K";
      squares[0][4]="Q";
      squares[0][5]="B";
      squares[0][6]="N";
      squares[0][7]="R";
      for (int boardCol=0; boardCol <= 7; ++boardCol) { //bottom pawns
        squares[6][boardCol]="P";
      }
      squares[7][0]="R";
      squares[7][1]="N";
      squares[7][2]="B";
      squares[7][3]="K";
      squares[7][4]="Q";
      squares[7][5]="B";
      squares[7][6]="N";
      squares[7][7]="R";

      return squares;
    }
  }

