package tools;

import json.JSONReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DataLoader
{
  private DataFrame dataList;
  private int colNum;

  public DataLoader()
  {
    dataList = new DataFrame();
    colNum = 0;
  }

  private void setColumnNames(String[] colNameList)
  {
    for (String name : colNameList)
    {
      dataList.addColumn(name);
    }
  }

  private void addData(String[] colNameList, String[] patientDataList)
  {
    int index = 0;
    for (String patientData : patientDataList)
    {
      dataList.addValue(colNameList[index],patientData);
      index ++;
    }
    if (index == colNum - 1) // zipcode (or last column) is not stated, add empty string in
    {
      dataList.addValue(colNameList[index],"");
    }
  }

  public DataFrame getDataFrame()
  {
    return dataList;
  }

  public void loadCSVData(File filename) throws IOException
  {
    FileReader file = new FileReader(filename);
    Scanner incoming = new Scanner(file);
    String columnName = incoming.nextLine(); // first line of the file = column names
    String[] colNames = columnName.split(",");
    colNum = colNames.length;
    setColumnNames(colNames);
    while (incoming.hasNextLine()) // remaining are patient datas
    {
      String patient = incoming.nextLine();
      String[] patientList = patient.split(",");
      addData(colNames,patientList);
    }
  }

  public void loadJSONData(File filename) throws IOException
  {
    JSONReader jsonTool = new JSONReader(filename);
    dataList = jsonTool.readJSON();
    colNum = jsonTool.getColNum();
  }
}
