package cw3.pckg.model;

import java.util.ArrayList;

class Row
{
  private ArrayList<String> columns;

  Row()
  {
    columns = new ArrayList<String>();
  }

  int getSize()
  {
    return columns.size();
  }

  ArrayList<String> getCol()
  {
    return columns;
  }

  String getColValue(int index)
  {
    return columns.get(index);
  }

  void setColValue(int index, String data)
  {
    columns.set(index,data);
  }

  void addColValue(String data)
  {
    columns.add(data);
  }

  void insertColValue(int index, String data)
  {
    columns.add(index,data);
  }

  void deleteCol(int index)
  {
    columns.remove(index);
  }

  boolean search(String item)
  {
    boolean result = false;
    for (String value : columns)
    {
      if (value.compareTo(item) == 0)
      {
        result = true;
        break;
      }
    }
    return result;
  }
}
