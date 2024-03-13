package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class DrawBoard {

  private static final int BOARD_SIZE_IN_SQUARES = 10;
  private static final int SQUARE_SIZE_IN_CHARS = 3;
  private static final int LINE_WIDTH_IN_CHARS = 1;
  private static final String EMPTY = " ";
  public static void main(String[] args) {
    var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

    out.print(ERASE_SCREEN);

    drawHeaders(out);

    drawChessBoard(out);

    out.print(SET_BG_COLOR_BLACK);
    out.print(SET_TEXT_COLOR_WHITE);
  }

  private static void drawHeaders(PrintStream out) {

    setLightGray(out);

    String[] headers = { " ", "a", "b", "c", "d", "e", "f", "g", "h", " " };
    for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
      drawHeader(out, headers[boardCol]);

      if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
        out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
      }
    }

    out.println();
  }

  private static void drawHeader(PrintStream out, String headerText) {
    int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
    int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

    setLightGray(out);
    out.print(EMPTY.repeat(prefixLength));

    printHeaderText(out, headerText);

    setLightGray(out);
    out.print(EMPTY.repeat(suffixLength));
  }

  private static void printHeaderText(PrintStream out, String player) {
    out.print(SET_BG_COLOR_LIGHT_GREY);
    out.print(SET_TEXT_COLOR_BLACK);

    out.print(player);

    setBlack(out);
  }

  private static void drawChessBoard(PrintStream out) {

    for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {

      drawRowOfSquares(out);

      if (boardRow < BOARD_SIZE_IN_SQUARES - 1) {
        //drawVerticalLine(out);
        setBlack(out);
      }
    }
  }

  private static void drawRowOfSquares(PrintStream out) {

    for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_CHARS; ++squareRow) {
      for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
        setWhite(out);

        if (squareRow == SQUARE_SIZE_IN_CHARS / 2) {
          int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
          int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

          out.print(EMPTY.repeat(prefixLength));
          printPiece(out, "K");
          out.print(EMPTY.repeat(suffixLength));
        }
        else {
          out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
        }

        if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
          // Draw right line
          setRed(out);
          out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
        }

        setBlack(out);
      }

      out.println();
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
  private static void setRed(PrintStream out) {
    out.print(SET_BG_COLOR_RED);
    out.print(SET_TEXT_COLOR_RED);
  }
  private static void printPiece(PrintStream out, String piece) {
    out.print(SET_BG_COLOR_WHITE);
    out.print(SET_TEXT_COLOR_BLACK);

    out.print(piece);

    setWhite(out);
  }
}

