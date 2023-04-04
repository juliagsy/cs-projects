package gui;

import java.io.File;
import java.io.IOException;

class ButtonControl
{
  private Model model;
  private ViewGUI view;

  protected ButtonControl(Model linkedModel, ViewGUI linkedView)
  {
    model = linkedModel;
    view = linkedView;
  }

  protected void loadFile(File file)
  {
    try
    {
      String filename = file.getName();
      char fileType = filename.charAt(filename.length() - 1);
      model.loadFile(file,fileType);
      view.updateTogglePanels();
      view.updateSearchPanel();
      view.updateChartPanel();
      view.showColumn();
    }
    catch (IOException e)
    {
      view.showMessage("Unable to load file.", "File Error!", "error");
    }
  }

  protected void loadClicked()
  {
    view.showFileChooser();
  }

  protected void clearClicked()
  {
    view.resetView();
  }

  protected void saveClicked()
  {
    try
    {
      model.writeJSON();
      view.showMessage("File saved as JSON format.", "Successful!", "information");
    }
    catch (IOException e1)
    {
      view.showMessage("Unable to save file to JSON.", "File Error!", "error");
    }
    catch (IndexOutOfBoundsException e2)
    {
      view.showMessage("No file to save as JSON format.", "Save Error!", "error");
    }
  }

  protected void searchClicked(String keyword)
  {
    model.resetColDisplay();
    view.updateTogglePanels();
    if (keyword.compareTo("") == 0)
    {
      model.setSearchActivated(false);
      view.updateSearchPanel();
    }
    else
    {
      model.searchRow(keyword);
    }
    view.showColumn();
  }

  protected void resetClicked()
  {
    searchClicked("");
  }

  protected void buttonToggled(boolean status, String name)
  {
    if (status == true)
    {
      model.addColToDisplay(name);
      view.showColumn();
    }
    else
    {
      int displayIndex = model.getDisplayColIndex(name);
      model.removeColFromDisplay(name);
      view.hideColumn(displayIndex);
    }
  }
}
