package cw3.pckg.model;

import java.util.ArrayList;

class DataFrame
{
  private ArrayList<Row> rows;

  DataFrame()
  {
    rows = new ArrayList<Row>();
  }

  void checkEmpty()
  {
    if (getRowCount() == 0)
    {
      Row row = new Row();
      row.addColValue("\n");
      rows.add(row);
    }
  }

  void addRow(Row newRow)
  {
    rows.add(newRow);
  }

  int getRowCount()
  {
    return rows.size();
  }

  ArrayList<String> getRow(int index)
  {
    Row targetRow = rows.get(index);
    ArrayList<String> rowData = targetRow.getCol();
    return rowData;
  }

  void deleteGrid(int rowIndex, int colIndex)
  {
    Row row = rows.get(rowIndex);
    row.deleteCol(colIndex);
    if (row.getSize() == 0)
    {
      rows.remove(rowIndex);
    }
    checkEmpty();
  }

  void insertRow(int rowIndex)
  {
    Row row = new Row();
    row.addColValue(" ");
    rows.add(rowIndex,row);
  }

  void insertCol(int rowIndex, int colIndex)
  {
    Row row = rows.get(rowIndex);
    row.insertColValue(colIndex," ");
  }

  void setGrid(int rowIndex, int colIndex, String newItem)
  {
    Row targetRow = rows.get(rowIndex);
    targetRow.setColValue(colIndex, newItem);
  }

  ArrayList<ArrayList<String>> search(String target)
  {
    ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
    for (int index=0;index<rows.size();index++)
    {
      boolean containItem = rows.get(index).search(target);
      if (containItem)
      {
        result.add(getRow(index));
      }
    }
    return result;
  }

}
