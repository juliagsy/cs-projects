package tools;

import java.util.ArrayList;

public class DataFrame
{
  private ArrayList<Column> columns;

  public DataFrame()
  {
    columns = new ArrayList<Column>();
  }

  private Column searchCol(String colName)
  {
    Column target = new Column("");
    for (Column col : columns)
    {
      String name = col.getName();
      if (name.compareTo(colName) == 0)
      {
        target = col;
        break;
      }
    }
    return target;
  }

  public void addColumn(String s)
  {
    Column newCol = new Column(s);
    columns.add(newCol);
  }

  public ArrayList<String> getColumnNames()
  {
    ArrayList<String> nameList = new ArrayList<String>();
    for (Column col : columns)
    {
      nameList.add(col.getName());
    }
    return nameList;
  }

  public int getRowCount()
  {
    Column col = columns.get(0);
    return col.getSize();
  }

  public String getValue(String colName, int rowNum)
  {
    Column target = searchCol(colName);
    if (target.getName().compareTo("") == 0)
    {
      return "Column is invalid.";
    }
    return target.getRowValue(rowNum);
  }

  public void putValue(String colName, int rowNum, String data)
  {
    Column target = searchCol(colName);
    if (target.getName().compareTo("") == 0)
    {
      System.out.println("Column is invalid.");
    }
    target.setRowValue(rowNum,data);
  }

  public void addValue(String colName, String data)
  {
    Column target = searchCol(colName);
    if (target.getName().compareTo("") == 0)
    {
      System.out.println("Column is invalid.");
    }
    target.addRowValue(data);
  }
}
