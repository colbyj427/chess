package ui;

import java.util.Scanner;
import ui.EscapeSequences;

import static ui.EscapeSequences.*;

public class Client {

//  String serverUrl;

//  public Repl(String serverUrl) {
//    String serverUrl = this.serverUrl;
//  }


  public void run() {
    System.out.println("\uD83D\uDC36 Welcome to the pet store. Sign in to start.");
    System.out.print(help());

    Scanner scanner = new Scanner(System.in);
    var result = "";
    while (!result.equals("quit")) {
      printPrompt();
      String line = scanner.nextLine();

      try {
        result = eval(line);
        System.out.print(SET_TEXT_COLOR_BLUE + result);
      } catch (Throwable e) {
        var msg = e.toString();
        System.out.print(msg);
      }
    }
    System.out.println();
  }

  private void printPrompt() {
  }

  private String help() {
    return "";
  }
  private String eval(String line) {
    return "";
  }
}
