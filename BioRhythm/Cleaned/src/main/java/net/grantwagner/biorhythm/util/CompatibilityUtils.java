package net.grantwagner.biorhythm.util;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CompatibilityUtils {

  private static PrintWriter out;
  static {
      out = new PrintWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8), true);
  }
  
  private static Scanner scanner;
  static {
    scanner = new Scanner(new java.io.BufferedInputStream(System.in), StandardCharsets.UTF_8);
    scanner.useLocale(Locale.US);
  }
  
  private static final Pattern EMPTY_PATTERN = Pattern.compile("");

  /**
   * Prints an object to this output stream and then terminates the line.
   *
   * @param x the object to print
   */
  public static void println(Object x) {
      out.println(x);
  }

  /**
   * Prints an object to standard output and flushes standard output.
   *
   * @param x the object to print
   */
  public static void print(Object x) {
      out.print(x);
      out.flush();
  }

  /**
   * Reads and returns the next character.
   *
   * @return the next {@code char}
   * @throws NoSuchElementException if standard input is empty
   */
  public static char readChar() {
      try {
        Pattern existingDelimiter = scanner.delimiter();
          scanner.useDelimiter(EMPTY_PATTERN);
          String ch = scanner.next();
          assert ch.length() == 1 : "Internal (Std)In.readChar() error!"
              + " Please contact the authors.";
          scanner.useDelimiter(existingDelimiter);
          return ch.charAt(0);
      }
      catch (NoSuchElementException e) {
          throw new NoSuchElementException("attempts to read a 'char' value from standard input, "
                                         + "but no more tokens are available");
      }
  }

  /**
   * Reads and returns the next character.
   *
   * @return the next {@code char}
   * @throws NoSuchElementException if standard input is empty
   */
  public static String readLine() {
    return scanner.nextLine();
  }

  /**
   * Reads the next token from standard input, parses it as an integer, and returns the integer.
   *
   * @return the next integer on standard input
   * @throws NoSuchElementException if standard input is empty
   * @throws InputMismatchException if the next token cannot be parsed as an {@code int}
   */
  public static int readInt() {
      try {
          return scanner.nextInt();
      }
      catch (InputMismatchException e) {
          String token = scanner.next();
          throw new InputMismatchException("attempts to read an 'int' value from standard input, "
                                         + "but the next token is \"" + token + "\"");
      }
      catch (NoSuchElementException e) {
          throw new NoSuchElementException("attemps to read an 'int' value from standard input, "
                                         + "but no more tokens are available");
      }
  
  }

  /**
   * Attempts to duplicate the original behavior of the TI Basic SEG routine.
   */
  public static String seg(String input, int startIndex, int length) {
    //convert from 1 indexed to 0 index
    startIndex -= 1;
    return input.substring(startIndex, startIndex + length); 
  }
}
