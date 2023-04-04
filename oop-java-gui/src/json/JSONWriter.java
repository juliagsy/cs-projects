package json;

import tools.DataFrame;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class JSONWriter
{
  private DataFrame fullDataList;
  private ArrayList<String> allCols;
  private int colNum;
  private int rowNum;
  private FileWriter jsonFile;

  public JSONWriter(DataFrame dataList)
  {
    fullDataList = dataList;
    allCols = dataList.getColumnNames();
    rowNum = dataList.getRowCount();
    colNum = allCols.size();
  }

  private void createFile() throws IOException
  {
    jsonFile = new FileWriter("patients.json",false);
  }

  private void writeToFile() throws IOException
  {
    String newline = System.getProperty("line.separator"); // get newline on different systems
    jsonFile.write("{\"patients\":[" + newline);
    int index;
    for (index=0;index<rowNum;index++)
    {
      jsonFile.write("{");
      for (String colName : allCols)
      {
        StringBuilder col = new StringBuilder("\"" + colName + "\"");
        StringBuilder data = new StringBuilder("\""+fullDataList.getValue(colName,index)+"\"");
        jsonFile.write(col+":"+data);
        if (colName.compareTo(allCols.get(colNum-1)) != 0)
        {
          jsonFile.write("," + newline);
        }
      }
      jsonFile.write(newline + "}");
      if (index != rowNum - 1)
      {
        jsonFile.write("," + newline);
      }
    }
    jsonFile.write(newline + "]" + newline + "}" + newline);
  }

  public void save() throws IOException
  {
    createFile();
    writeToFile();
    jsonFile.close();
  }
}
