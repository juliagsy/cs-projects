package cw3.pckg.model;

import java.util.Scanner;
import java.util.ArrayList;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class CSVLoader
{
  private DataFrame dataframe;
  private int colNum;

  CSVLoader()
  {
    dataframe = new DataFrame();
  }

  private void addData(String[] dataList)
  {
    int index = 0;
    Row row = new Row();
    for (String data : dataList)
    {
      row.addColValue(data);
      index ++;
    }
    if (row.getSize() != 0)
    {
      dataframe.addRow(row);
    }
  }

  DataFrame load(File filename) throws IOException
  {
    FileReader file = new FileReader(filename);
    Scanner incoming = new Scanner(file);
    while (incoming.hasNextLine())
    {
      String data = incoming.nextLine();
      String[] dataList = data.split(",");
      addData(dataList);
    }
    return dataframe;
  }
}
