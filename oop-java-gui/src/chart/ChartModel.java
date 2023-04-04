package chart;

import tools.DataFrame;
import java.util.ArrayList;

public class ChartModel
{
  private DataFrame fullDataList;
  private int rowCount;

  public ChartModel(DataFrame allData)
  {
    fullDataList = allData;
    rowCount = fullDataList.getRowCount();
  }

  protected Object[] getCategoryData(String colName)
  {
    ArrayList<String> rowCategory = new ArrayList<String>();
    int index;
    for (index=0;index<rowCount;index++)
    {
      rowCategory.add(fullDataList.getValue(colName,index));
    }
    return rowCategory.toArray();
  }
}
