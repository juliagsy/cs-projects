package StringArray;

public class StringArray
{
  private String[] strArray;
  private int currentSize = 0; // keep track of the size of the array with valid index
  private int maxSize = 50; // max size of the array
  private int targetIndex; // shared between contains(), indexOf() and their MatchingCase versions to avoid searching twice

  public StringArray()
  {
    strArray = new String[maxSize];
  }

  public StringArray(StringArray a)
  {
    int aLen = a.size();
    maxSize = aLen;
    strArray = new String[maxSize];
    int index = 0;
    while (index != aLen)
    {
      strArray[index] = (a.get(index));
      index ++;
    }
    currentSize = aLen;
  }

  private void checkSize() // check if array is full
  {
    if (currentSize == maxSize)
    {
      upsizeArray();
    }
  }

  private void upsizeArray()
  {
    int index = maxSize;
    double increase = maxSize * 0.5;
    maxSize += (int)increase;
    String[] newStrArr = new String[maxSize];
    int newIndex;
    for (newIndex=0;newIndex<index;newIndex++)
    {
      newStrArr[newIndex] = strArray[newIndex];
    }
    strArray = new String[maxSize];
    strArray = newStrArr;
  }

  public int size()
  {
    return currentSize;
  }

  public boolean isEmpty()
  {
    return (currentSize == 0);
  }

  public String get(int index)
  {
    try
    {
      String word = strArray[index];
      return word;
    }
    catch (ArrayIndexOutOfBoundsException exc)
    {
      return ("Index out of range.");
    }
  }

  public void set(int index, String s)
  {
    try
    {
      if (strArray[index] == null)
      {
        return; // invalid index, do nothing
      }
      strArray[index] = s;
    }
    catch (ArrayIndexOutOfBoundsException exc)
    {
      return; // index out of bound, do nothing
    }
  }

  public void add(String s)
  {
    checkSize();
    strArray[currentSize] = s;
    currentSize ++;
  }

  public void insert(int index, String s)
  {
    try
    {
      if (isEmpty() && index == 0)
      {
        strArray[0] = s;
        currentSize ++;
        return;
      }
      else if (strArray[index] == null)
      {
        return; // invalid index, do nothing
      }
      else
      {
        checkSize();
        int shiftIndex = currentSize;
        while (shiftIndex != index)
        {
          strArray[shiftIndex] = strArray[shiftIndex-1];
          shiftIndex --;
        }
        strArray[index] = s;
        currentSize ++;
      }
    }
    catch (ArrayIndexOutOfBoundsException exc)
    {
      return; // index out of bound, do nothing
    }
  }

  public void remove(int index)
  {
    try
    {
      if (strArray[index] == null)
      {
        return; // invalid index, do nothing
      }
      while (index != currentSize-1)
      {
        strArray[index] = strArray[index+1];
        index ++;
      }
      strArray[index] = null;
      currentSize --;
    }
    catch (ArrayIndexOutOfBoundsException exc)
    {
      return; // index out of bound, do nothing
    }
  }

  public boolean contains(String s)
  {
    s = s.toLowerCase(); // lower case for easy compare
    int index;
    boolean result = false;
    targetIndex = -1;
    for (index=0;index<currentSize;index++)
    {
      if (s.compareTo(strArray[index].toLowerCase()) == 0)
      {
        result = true;
        targetIndex = index;
        break;
      }
    }
    return result;
  }

  public boolean containsMatchingCase(String s)
  {
    int index;
    boolean result = false;
    targetIndex = -1;
    for (index=0;index<currentSize;index++)
    {
      if (s.compareTo(strArray[index]) == 0)
      {
        result = true;
        targetIndex = index;
        break;
      }
    }
    return result;
  }

  public int indexOf(String s)
  {
    boolean isElem = contains(s);
    return targetIndex;
  }

  public int indexOfMatchingCase(String s)
  {
    boolean isElem = containsMatchingCase(s);
    return targetIndex;
  }
}
