package cw3.pckg.model;


import java.io.IOException;

public class ModelFactory
{
  private static Model model;

  public static Model getModel() throws IOException
  {
    if (model == null)
    {
      model = new Model();
    }
    return model;
  }

  public static Model resetModel() throws IOException
  {
    model = new Model();
    return model;
  }
}
