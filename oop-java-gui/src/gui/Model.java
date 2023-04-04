package gui;

import tools.DataFrame;
import tools.DataLoader;
import json.JSONWriter;
import java.util.Collections;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Model
{
  private DataFrame fullDataList;
  private ArrayList<String> allCols;
  private ArrayList<String> colInDisplay;
  private ArrayList<Integer> targetRecord;
  private boolean searchActivated;

  public Model()
  {
    fullDataList = new DataFrame();
  }

  protected void loadFile(File file,char fileType) throws IOException
  {
    DataLoader loader = new DataLoader();
    if (Character.compare(fileType,'v') == 0) // assume only 2 types of file will be loaded
    {
      loader.loadCSVData(file);
    }
    else if (Character.compare(fileType,'n') == 0)
    {
      loader.loadJSONData(file);
    }
    else
    {
      throw new IOException();
    }
    fullDataList = loader.getDataFrame();
    allCols = fullDataList.getColumnNames();
    colInDisplay = getColNames();
    searchActivated = false;
  }

  protected void writeJSON() throws IOException
  {
    JSONWriter jsonTool = new JSONWriter(fullDataList);
    jsonTool.save();
  }

  protected DataFrame getDataFrame()
  {
    return fullDataList;
  }

  protected ArrayList<String> getColNames()
  {
    return fullDataList.getColumnNames();
  }

  protected ArrayList<String> getColInDisplay()
  {
    return colInDisplay;
  }

  protected int getDisplayColIndex(String colName)
  {
    return colInDisplay.indexOf(colName);
  }

  protected void removeColFromDisplay(String colName)
  {
    colInDisplay.remove(colName);
  }

  protected void addColToDisplay(String colName)
  {
    int targetIndex = allCols.indexOf(colName);
    int index = 0;
    for (String name : colInDisplay)
    {
      if (allCols.indexOf(name) > targetIndex)
      {
        break;
      }
      index ++;
    }
    colInDisplay.add(index,colName);
  }

  protected void resetColDisplay()
  {
    colInDisplay = getColNames();
  }

  protected Object[][] getSelectedColumn()
  {
    int rowNum = fullDataList.getRowCount();
    Object[][] data = new Object[rowNum][colInDisplay.size()];
    int index;
    for (index=0;index<rowNum;index++)
    {
      ArrayList<String> rowData = new ArrayList<String>();
      for (String col : colInDisplay)
      {
        rowData.add(fullDataList.getValue(col,index));
      }
      data[index] = rowData.toArray();
    }
    return data;
  }

  protected Object[][] getSelectedRow()
  {
    Object[][] data = new Object[targetRecord.size()][colInDisplay.size()];
    int index = 0;
    for (Integer targetIndex : targetRecord)
    {
      ArrayList<String> targetedColData = new ArrayList<String>();
      for (String name : colInDisplay)
      {
        targetedColData.add(fullDataList.getValue(name,targetIndex));
      }
      data[index] = targetedColData.toArray();
      index ++;
    }
    return data;
  }

  protected void searchRow(String keyword)
  {
    setSearchActivated(true);
    int index;
    int allRowNum = fullDataList.getRowCount();
    targetRecord = new ArrayList<Integer>();
    for (String name : allCols)
    {
      for (index=0;index<allRowNum;index++)
      {
        String current = fullDataList.getValue(name,index);
        if (keyword.compareTo(current) == 0)
        {
          if (targetRecord.indexOf(index) == -1)
          {
            targetRecord.add(index);
          }
        }
      }
    }
    Collections.sort(targetRecord);
  }

  protected void setSearchActivated(boolean status)
  {
    searchActivated = status;
  }

  protected boolean getSearchActivated()
  {
    return searchActivated;
  }
}
