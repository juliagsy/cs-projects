package gui;

import chart.ChartPanel;
import chart.ChartModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

public class ViewGUI extends JFrame
{
  private Model model;
  private ButtonControl buttonControl;

  private JPanel filePanel;
  private JPanel filterPanel;
  private JPanel searchPanel;
  private JPanel chartPanel;
  private JPanel tablePanel;
  private JPanel fileButtonPanel;
  private JPanel filterTogglePanel;
  private JPanel searchButtonPanel;
  private JPanel fileFilterPanel;
  private JPanel searchChartPanel;
  private JPanel leftPanel;
  private JPanel mainPanel;
  private JButton loadButton;
  private JButton clearButton;
  private JButton saveButton;
  private JButton searchButton;
  private JButton resetButton;
  private JTextField keywordTextBox;
  private JTable dataTable;
  private JScrollPane filterPanelScroll;
  private JScrollPane tablePanelScroll;

  public ViewGUI(Model linkedModel)
  {
    super("Patients' Data");
    model = linkedModel;
    buttonControl = new ButtonControl(model,this);

    createBasics();
    run();
  }

  private void createBasics()
  {
    createButtonPanels();
    createFilePanel();
    createFilterPanel();
    createSearchPanel();
    createChartPanel();
    createTablePanel();
    createFileFilterPanel();
    createSearchChartPanel();
    createLeftPanel();
    createMainPanel();
    add(mainPanel);
  }

  private void run()
  {
    setPreferredSize(new Dimension(800,800));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  private void createButtons()
  {
    loadButton = new JButton("Load File");
    loadButton.addActionListener((ActionEvent act) -> buttonControl.loadClicked());
    clearButton = new JButton("Clear");
    clearButton.addActionListener((ActionEvent act) -> buttonControl.clearClicked());
    saveButton = new JButton("Save File as JSON");
    saveButton.addActionListener((ActionEvent act) -> buttonControl.saveClicked());
    searchButton = new JButton("Search Keyword");
    searchButton.addActionListener((ActionEvent act) -> buttonControl.searchClicked(keywordTextBox.getText()));
    resetButton = new JButton("Reset");
    resetButton.addActionListener((ActionEvent act) -> buttonControl.resetClicked());
  }

  private void createButtonPanels()
  {
    createButtons();
    fileButtonPanel = new JPanel(new GridLayout(3,1,10,5));
    fileButtonPanel.add(loadButton);
    fileButtonPanel.add(clearButton);
    fileButtonPanel.add(saveButton);
    searchButtonPanel = new JPanel(new GridLayout(2,1,10,5));
    searchButtonPanel.add(searchButton);
    searchButtonPanel.add(resetButton);
  }

  private void createFilePanel()
  {
    filePanel = new JPanel(new BorderLayout());
    filePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK,1,true),"File"));
    filePanel.add(fileButtonPanel,BorderLayout.CENTER);
  }

  private void createTogglePanel()
  {
    filterTogglePanel = new JPanel();
    filterPanelScroll = new JScrollPane(filterTogglePanel);
  }

  private void createFilterPanel()
  {
    createTogglePanel();
    filterPanel =  new JPanel(new BorderLayout());
    filterPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK,1,true),"Filter"));
    filterPanel.add(filterPanelScroll,BorderLayout.CENTER);
  }

  private void createKeywordTextBox()
  {
    keywordTextBox = new JTextField();
  }

  private void createSearchPanel()
  {
    createKeywordTextBox();
    searchPanel = new JPanel(new BorderLayout());
    searchPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK,1,true),"Search"));
  }

  private void createChartPanel()
  {
    chartPanel = new JPanel(new BorderLayout());
    chartPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK,1,true),"Chart"));
  }

  private void createTable()
  {
    dataTable = new JTable();
    tablePanelScroll = new JScrollPane(dataTable);
  }

  private void createTablePanel()
  {
    createTable();
    tablePanel = new JPanel(new BorderLayout());
    tablePanel.add(tablePanelScroll,BorderLayout.CENTER);
  }

  private void createFileFilterPanel()
  {
    fileFilterPanel = new JPanel(new BorderLayout());
    fileFilterPanel.add(filePanel,BorderLayout.NORTH);
    fileFilterPanel.add(filterPanel,BorderLayout.CENTER);
  }

  private void createSearchChartPanel()
  {
    searchChartPanel = new JPanel(new BorderLayout());
    searchChartPanel.add(searchPanel,BorderLayout.CENTER);
    searchChartPanel.add(chartPanel,BorderLayout.SOUTH);
  }

  private void createLeftPanel()
  {
    leftPanel = new JPanel(new BorderLayout());
    leftPanel.add(fileFilterPanel,BorderLayout.CENTER);
    leftPanel.add(searchChartPanel,BorderLayout.SOUTH);
  }

  private void createMainPanel()
  {
    mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(leftPanel,BorderLayout.WEST);
    mainPanel.add(tablePanel,BorderLayout.CENTER);
  }

  private void updateFullView()
  {
    mainPanel.revalidate();
    mainPanel.repaint();
  }

  private void updateToggleButtons(Object[] colNames)
  {
    for (Object name : colNames)
    {
      String col = name.toString();
      JToggleButton toggleButton = new JToggleButton(col,true);
      toggleButton.addActionListener((ActionEvent act) -> buttonControl.buttonToggled(toggleButton.isSelected(),toggleButton.getText()));
      filterTogglePanel.add(toggleButton);
    }
  }

  private void updateTablePanel(Object[][] colDatas, Object[] colNames)
  {
    tablePanel.remove(tablePanelScroll);
    dataTable = new JTable(colDatas,colNames);
    tablePanelScroll = new JScrollPane(dataTable);
    tablePanel.add(tablePanelScroll,BorderLayout.CENTER);
    updateFullView();
  }

  protected void updateTogglePanels()
  {
    filterPanel.remove(filterPanelScroll);
    GridLayout buttonLayout = new GridLayout(model.getColNames().size(),1,8,8);
    filterTogglePanel = new JPanel(buttonLayout);
    updateToggleButtons(model.getColNames().toArray());
    filterPanelScroll = new JScrollPane(filterTogglePanel);
    filterPanel.add(filterPanelScroll,BorderLayout.CENTER);
    updateFullView();
  }

  protected void updateSearchPanel()
  {
    searchPanel.add(keywordTextBox,BorderLayout.CENTER);
    searchPanel.add(searchButtonPanel,BorderLayout.SOUTH);
    updateFullView();
  }

  protected void updateChartPanel()
  {
    chartPanel.removeAll();
    chartPanel.add(new ChartPanel(new ChartModel(model.getDataFrame())));
    updateFullView();
  }

  protected void resetView()
  {
    remove(mainPanel);
    createBasics();
    updateFullView();
  }

  protected void showColumn()
  {
    if (model.getSearchActivated() == true)
    {
      updateTablePanel(model.getSelectedRow(),model.getColInDisplay().toArray());
      return;
    }
    updateTablePanel(model.getSelectedColumn(),model.getColInDisplay().toArray());
  }

  protected void hideColumn(int displayIndex)
  {
    TableColumn target = dataTable.getColumnModel().getColumn(displayIndex);
    dataTable.removeColumn(target);
  }

  protected void showFileChooser()
  {
    JFileChooser fileChooser = new JFileChooser();
    int returnVal = fileChooser.showOpenDialog(this);
    if (returnVal == fileChooser.APPROVE_OPTION)
    {
      buttonControl.loadFile(fileChooser.getSelectedFile());
    }
  }

  protected void showMessage(String message, String title, String type)
  {
    if (type.compareTo("error") == 0)
    {
      JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
      return;
    }
    JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
  }
}
