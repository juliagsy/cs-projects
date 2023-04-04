package SpellingChecker;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import StringArray.StringArray;

public class SpellingChecker
{
  private int dictLen;
  private StringArray suggestedWords;
  private StringArray dictionary = new StringArray();
  private StringArray wrongWords = new StringArray();

  public SpellingChecker()
  {
    createDict();
  }

  private void createDict()
  {
    try
    {
      FileReader dictFile = new FileReader("words.txt");
      Scanner incoming = new Scanner(dictFile);
      while (incoming.hasNextLine())
      {
        dictionary.add(incoming.nextLine());
      }
      dictLen = dictionary.size();
    }
    catch (FileNotFoundException exc)
    {
      System.out.println("Dictionary text file not found.");
    }
  }

  public void mainMenu() throws IOException
  {
    System.out.println("- SPELLING CHECKER -");
    System.out.println("1 - Input a String");
    System.out.println("2 - Input a File");
    System.out.println("3 - Exit Program");
    System.out.print("Enter a selection: ");
    String answer = getInput();
    switch (answer)
    {
      case "1":
        checkString();
        break;

      case "2":
        checkFile();
        break;

      case "3":
        System.out.println("Goodbye!");
        return;

      default:
        System.out.println("Please enter a valid option.");
        mainMenu();
    }
  }

  private String getInput() throws IOException
  {
    Scanner incoming = new Scanner(System.in);
    return incoming.nextLine();
  }

  private void checkString() throws IOException
  {
    System.out.print("Enter a string for spelling check: ");
    String sentence = getInput();
    checking(sentence);
    checkMistake();
  }

  private void checkFile() throws IOException
  {
    try
    {
      System.out.print("Enter a file: ");
      String filename = getInput();
      FileReader fileRead = new FileReader(filename);
      Scanner incoming = new Scanner(fileRead);
      while (incoming.hasNextLine())
      {
        String sentence = incoming.nextLine();
        checking(sentence);
      }
      fileRead.close();
      checkMistake();
    }
    catch (FileNotFoundException exc)
    {
      System.out.println("File not found. Please enter a valid file.");
    }
  }

  private void checking(String s)
  {
    s = removePunct(s);
    Scanner incoming = new Scanner(s);
    while (incoming.hasNext())
    {
      String word = incoming.next();
      if (dictionary.contains(word) == false && wrongWords.contains(word) == false)
      {
        wrongWords.add(word);
      }
    }
  }

  private String removePunct(String s)
  {
    s = s.toLowerCase();
    return s.replaceAll("\\p{Punct}","");
  }

  private void checkMistake() throws IOException
  {
    if (!noMistake())
    {
      writeFile();
      return;
    }
    System.out.println("All spellings are correct!");
  }

  private boolean noMistake()
  {
    return wrongWords.isEmpty();
  }

  private void writeFile() throws IOException
  {
    int index;
    int length = wrongWords.size();
    String newline = System.getProperty("line.separator"); // get newline on different systems.
    FileWriter spelling = new FileWriter("newSpellings.txt",false); //overwrites file if file exists
    for (index=0;index!=length;index++)
    {
      String word = wrongWords.get(index);
      spelling.write("WRONG WORD #" + (int)(index+1) + ": " + word + newline);
      checkDict(word);
      int correct;
      for (correct=0;correct!=suggestedWords.size();correct++)
      {
        spelling.write(suggestedWords.get(correct) + newline);
      }
      spelling.write(newline);
    }
    spelling.close();
    System.out.println("Predicted spellings for incorrect words have been saved to \"newSpellings.txt\" file.");
  }

  private void checkDict(String s)
  {
    int dictIndex;
    suggestedWords = new StringArray();
    int wordLen = s.length();
    for (dictIndex=0;dictIndex!=dictLen;dictIndex++)
    {
      String dictWord = dictionary.get(dictIndex);
      if (dictWord.length() >= wordLen - 1 && dictWord.length() <= wordLen + 1)
      {
        similarity(dictWord,s,wordLen);
      }
    }
  }

  private void similarity(String dictWord, String user, int userLen)
  {
    int index = 0;
    int points = 0;
    String[] letters = user.split("");
    while (index!=dictWord.length() && index!=userLen)
    {
      if (dictWord.charAt(index) == user.charAt(index))
      {
        if (index == 0)
        {
          points += 4;
          index ++;
          continue;
        }
        points += 2;
      }
      index ++;
    }
    for (String letter : letters)
    {
      if (dictWord.contains(letter))
      {
        points ++;
      }
      else
      {
        points -= 8;
      }
    }
    if (points >= userLen)
    {
      suggestedWords.add(dictWord);
    }
  }
}
