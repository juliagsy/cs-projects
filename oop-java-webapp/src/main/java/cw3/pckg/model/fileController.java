package cw3.pckg.model;

import java.io.File;
import java.io.IOException;

class FileController
{
  private final String dataPath = "./data/";

  void deleteFile(String name) throws IOException
  {
    File file = new File(dataPath+name+".csv");
    file.delete();
  }

  void renameFile(String oldname, String newname) throws IOException
  {
    File oldpath = new File(dataPath+oldname+".csv");
    File newpath = new File(dataPath+newname+".csv");
    oldpath.renameTo(newpath);
  }

  void autosave(String filename, DataFrame saveFrame) throws IOException
  {
    CSVWriter csv = new CSVWriter(filename);
    csv.write(saveFrame);
  }

}
