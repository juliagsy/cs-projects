package json;

import tools.DataFrame;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JSONReader
{
  private DataFrame dataList;
  private FileReader file;
  private int colNum;

  public JSONReader(File filename) throws IOException
  {
    dataList = new DataFrame();
    file = new FileReader(filename);
    colNum = 0;
  }

  private String removeComma(String oldStr)
  {
    return oldStr.replaceAll(",","");
  }

  private String removeDoubleQuote(String oldStr)
  {
    return oldStr.replaceAll("\"","");
  }

  private void addColNames(String colName)
  {
    dataList.addColumn(colName);
  }

  private void addPatient(ArrayList<String> patient)
  {
    for (String dataLine : patient)
    {
      String[] colAndData = dataLine.split(":");
      String colName = colAndData[0];
      String data = colAndData[1];
      if (colNum == 0)
      {
        addColNames(removeDoubleQuote(colName));
      }
      dataList.addValue(removeDoubleQuote(colName),removeDoubleQuote(data));
    }
    colNum = patient.size();
  }

  private void read() throws IOException
  {
    Scanner incoming = new Scanner(file);
    String data = incoming.nextLine(); // first line represents data category, ignore
    while (incoming.hasNextLine())
    {
      String nextData = removeComma(incoming.nextLine());
      if (nextData.compareTo("]") == 0) // no more patient's data
      {
        break;
      }
      else if (Character.compare(nextData.charAt(0),'{') == 0)
      {
        ArrayList<String> patientData = new ArrayList<String>();
        patientData.add(nextData.substring(1));
        nextData = removeComma(incoming.nextLine());
        while (nextData.compareTo("}") != 0)
        {
          patientData.add(nextData);
          nextData = removeComma(incoming.nextLine());
        }
        addPatient(patientData);
      }
    }
  }

  public int getColNum()
  {
    return colNum;
  }

  public DataFrame readJSON() throws IOException
  {
    read();
    return dataList;
  }
}
