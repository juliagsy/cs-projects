package cw3.pckg.model;

import java.util.ArrayList;

import java.io.File;
import java.io.IOException;

public class Model
{
  private FileController fileController;
  private ArrayList<String> allLabels;
  private ArrayList<DataFrame> allDataFrames;
  private int listCount;

  public Model()
  {
    fileController = new FileController();
    allLabels = new ArrayList<String>();
    allDataFrames = new ArrayList<DataFrame>();
    listCount = 0;
  }

  private void addCol(String listname, String direction, String rowIndex, String colIndex) throws IOException
  {
    DataFrame targetFrame = allDataFrames.get(allLabels.indexOf(listname));
    if (direction.compareTo("left") == 0)
    {
      targetFrame.insertCol(Integer.valueOf(rowIndex), Integer.valueOf(colIndex));
    }
    else
    {
      targetFrame.insertCol(Integer.valueOf(rowIndex), Integer.valueOf(colIndex)+1);
    }
    fileController.autosave(listname,targetFrame);
  }

  private void addRow(String listname, String direction, String rowIndex, String colIndex) throws IOException
  {
    DataFrame targetFrame = allDataFrames.get(allLabels.indexOf(listname));
    if (direction.compareTo("up") == 0)
    {
      targetFrame.insertRow(Integer.valueOf(rowIndex));
    }
    else
    {
      targetFrame.insertRow(Integer.valueOf(rowIndex)+1);
    }
    fileController.autosave(listname,targetFrame);
  } 

  public ArrayList<String> getLabels()
  {
    return allLabels;
  }

  public void loadDataDirectory(File dataFolder) throws IOException
  {
    for (File dataFile : dataFolder.listFiles())
    {
      if (!dataFile.isDirectory())
      {
        loadFile(dataFile);
      }
    }
  }

  public void loadFile(File dataFile) throws IOException
  {
    String filename = dataFile.getName();
    int length = filename.length();

    if (filename.substring(length-3,length).compareTo("csv") == 0)
    {
      CSVLoader csv = new CSVLoader();
      DataFrame newFrame = csv.load(dataFile);
      newFrame.checkEmpty();
      String label = filename.substring(0,length-4);
      allLabels.add(label);
      allDataFrames.add(newFrame);
      listCount ++;
    }
  }

  public void createNewList(String listname) throws IOException
  {
    DataFrame newFrame = new DataFrame();
    newFrame.checkEmpty();
    allLabels.add(listname);
    allDataFrames.add(newFrame);
    listCount ++;
    fileController.autosave(listname,newFrame);
  }

  public void deleteList(String listname) throws IOException
  {
    int listIndex = allLabels.indexOf(listname);
    allLabels.remove(listIndex);
    allDataFrames.remove(listIndex);
    listCount --;
    fileController.deleteFile(listname);
  }

  public void renameList(String listname, String newname) throws IOException
  {
    int listIndex = allLabels.indexOf(listname);
    allLabels.add(listIndex,newname);
    allLabels.remove(listIndex+1);
    fileController.renameFile(listname,newname);
  }

  public ArrayList<ArrayList<String>> viewList(String listname)
  {
    int listIndex = allLabels.indexOf(listname);
    DataFrame frame = allDataFrames.get(listIndex);
    ArrayList<ArrayList<String>> allDataByRow = new ArrayList<ArrayList<String>>();
    int rowCount = frame.getRowCount();
    for (int index=0;index<rowCount;index++)
    {
      ArrayList<String> rowData = frame.getRow(index);
      allDataByRow.add(rowData);
    }
    return allDataByRow;
  }

  public void add(String target, String type, String direction) throws IOException
  {
    String[] targetArray = target.split("@"); // format = [name, rowIndex, colIndex]
    String listname = targetArray[0];

    if (type.compareTo("col") == 0)
    {
      addCol(listname,direction,targetArray[1],targetArray[2]);
      return;
    }
    addRow(listname,direction,targetArray[1],targetArray[2]);
  }

  public void remove(String target) throws IOException
  {
    String[] targetArray = target.split("@"); // format = [name, rowIndex, colIndex]

    DataFrame targetFrame = allDataFrames.get(allLabels.indexOf(targetArray[0]));
    targetFrame.deleteGrid(Integer.valueOf(targetArray[1]), Integer.valueOf(targetArray[2]));
    fileController.autosave(targetArray[0],targetFrame);

  }

  public void edit(String target, String newItem) throws IOException
  {
    String[] targetArray = target.split("@"); // format = [listname, rowIndex, colIndex]

    DataFrame targetFrame = allDataFrames.get(allLabels.indexOf(targetArray[0]));
    targetFrame.setGrid(Integer.valueOf(targetArray[1]), Integer.valueOf(targetArray[2]),newItem);
    fileController.autosave(targetArray[0],targetFrame);

  }

  public int searchList(String listname)
  {
    return allLabels.indexOf(listname);
  }

  public ArrayList<Object> searchItem(String itemname)
  {
    ArrayList<Object> collections = new ArrayList<Object>();
    ArrayList<String> labels = new ArrayList<String>();
    ArrayList<ArrayList<ArrayList<String>>> results = new ArrayList<ArrayList<ArrayList<String>>>();

    for (int index=0;index<listCount;index++)
    {
      DataFrame dataframe = allDataFrames.get(index);
      ArrayList<ArrayList<String>> rowResult = dataframe.search(itemname);
      if (rowResult.size() != 0)
      {
        labels.add(allLabels.get(index));
        results.add(rowResult);
      }
    }

    collections.add(labels);
    collections.add(results);
    return collections;
  }
}
