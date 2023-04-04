package cw3.pckg.model;

import java.net.URL;
import java.io.File;
import java.io.IOException;

public class Validator
{
  private String checkLink(String data)
  {
    int dotIndex = data.lastIndexOf(".");
    if (dotIndex != -1)
    {
      String extension = data.substring(dotIndex+1,data.length());
      return extension;
    }
    return null;
  }

  private boolean isIn(String target, String[] list)
  {
    boolean result = false;
    for (String data : list)
    {
      if (target.compareTo(data) == 0)
      {
        result = true;
        break;
      }
    }
    return result;
  }

  public boolean isURL(String data)
  {
    try
    {
      new URL(data).toURI();
      return true;
    }
    catch (Exception e)
    {
      return false;
    }
  }

  public boolean isList(String data)
  {
    return (checkLink(data) != null && checkLink(data).compareTo("csv") == 0);
  }

  public boolean isImage(String data)
  {
    String[] imgExt = {"jpg","png","gif","jpeg","pdf","heif"};
    return (checkLink(data) != null && isIn(checkLink(data),imgExt));
  }

  public boolean validFile(String path) throws IOException
  {
    File file = new File(path);
    return file.exists();
  }

}
