package tools;

import java.util.ArrayList;

class Column
{
  private String colName;
  private ArrayList<String> rows;

  protected Column()
  {
    rows = new ArrayList<String>();
    colName = "";
  }

  protected Column(String name)
  {
    rows = new ArrayList<String>();
    colName = name;
  }

  protected String getName()
  {
    return colName;
  }

  protected int getSize()
  {
    return rows.size();
  }

  protected String getRowValue(int index)
  {
    return rows.get(index);
  }

  protected void setRowValue(int index, String data)
  {
    rows.set(index,data);
  }

  protected void addRowValue(String data)
  {
    rows.add(data);
  }
}
