import java.io.IOException;

import SpellingChecker.SpellingChecker;

class Main
{
  public static void main(String[] args)
  {
    try
    {
      SpellingChecker sc = new SpellingChecker();
      sc.mainMenu();
    }
    catch (IOException exc)
    {
      System.out.println("I/O Exception occured.");
    }
  }
}
