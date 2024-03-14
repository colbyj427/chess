package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.Array;

import static ui.EscapeSequences.*;

public class DrawBoard {

  private static final int BOARD_SIZE_IN_SQUARES = 8;
  private static final int SQUARE_SIZE_IN_CHARS = 3;
  private static final int LINE_WIDTH_IN_CHARS = 1;
  private static final Array startingPositions = new Array();
  private static final String EMPTY = " ";
  public static void main(String[] args) {
    var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

    out.print(ERASE_SCREEN);

    drawHeaders(out);

    drawChessBoard(out);

    drawHeaders(out);

    out.print(SET_BG_COLOR_BLACK);
    out.print(SET_TEXT_COLOR_WHITE);
  }

  private static void drawHeaders(PrintStream out) {

    setLightGray(out);

    String[] headers = { " ", "a", "b", "c", "d", "e", "f", "g", "h", " " };
    for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES + 1; ++boardCol) {
      drawHeader(out, headers[boardCol]);

    }

    out.println();
  }

  private static void drawHeader(PrintStream out, String headerText) {
    int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
    int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

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

  private static void drawChessBoard(PrintStream out) {

    for (int boardRow=0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {
      String boardRowString=Integer.toString(boardRow + 1);
      if (boardRow % 2 == 0) {
        drawWhiteRowOfSquares(out, boardRowString);
      } else {
        drawBlackRowOfSquares(out, boardRowString);
      }
    }
  }

  private static void drawWhiteRowOfSquares(PrintStream out, String row) {

      printRowNum(out, row);
      for (int boardCol=0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) { // draw actual squares, one at a time
        if (boardCol % 2 == 0) {
          setWhite(out);
          int prefixLength=SQUARE_SIZE_IN_CHARS / 2;
          int suffixLength=SQUARE_SIZE_IN_CHARS - prefixLength - 1;
          out.print(EMPTY.repeat(prefixLength));
          printWhitePiece(out, "K");
          out.print(EMPTY.repeat(suffixLength));
          setBlack(out);
        }
        else {
          setBlack(out);
          int prefixLength=SQUARE_SIZE_IN_CHARS / 2;
          int suffixLength=SQUARE_SIZE_IN_CHARS - prefixLength - 1;
          out.print(EMPTY.repeat(prefixLength));
          printWhitePiece(out, "K");
          out.print(EMPTY.repeat(suffixLength));
          setBlack(out);
        }
      }
      printRowNum(out, row);
      setLightGray(out);
      out.println();
  }
  private static void drawBlackRowOfSquares(PrintStream out, String row) {

    printRowNum(out, row);
    for (int boardCol=0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) { // draw actual squares, one at a time
      if (boardCol % 2 == 1) {
        setWhite(out);
        int prefixLength=SQUARE_SIZE_IN_CHARS / 2;
        int suffixLength=SQUARE_SIZE_IN_CHARS - prefixLength - 1;
        out.print(EMPTY.repeat(prefixLength));
        printWhitePiece(out, "K");
        out.print(EMPTY.repeat(suffixLength));
        setBlack(out);
      }
      else {
        setBlack(out);
        int prefixLength=SQUARE_SIZE_IN_CHARS / 2;
        int suffixLength=SQUARE_SIZE_IN_CHARS - prefixLength - 1;
        out.print(EMPTY.repeat(prefixLength));
        printWhitePiece(out, "K");
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
  private static void printWhitePiece(PrintStream out, String piece) {
    out.print(SET_TEXT_COLOR_RED);
    out.print(piece);
  }
  private static void printBlackPiece(PrintStream out, String piece) {
    out.print(SET_TEXT_COLOR_BLUE);
    out.print(piece);
  }
  private static void printRowNum(PrintStream out, String rowNum) {
    out.print(SET_BG_COLOR_LIGHT_GREY);
    out.print(SET_TEXT_COLOR_BLACK);
    out.print(EMPTY);
    out.print(rowNum);
    out.print(EMPTY);
    setWhite(out);
  }
}

