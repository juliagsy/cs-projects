import gui.Model;
import gui.ViewGUI;
import javax.swing.SwingUtilities;

class Main
{
  private Model model;
  private ViewGUI view;

  Main()
  {
    model = new Model();
    view = new ViewGUI(model);
  }

  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(Main::new);
  }
}
