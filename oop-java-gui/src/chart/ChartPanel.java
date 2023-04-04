package chart;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class ChartPanel extends JPanel
{
  private ChartModel chartModel;

  private JPanel piePanel;
  private JPanel barPanel;
  private JPanel pieButtonPanel;
  private JPanel barButtonPanel;
  private JPanel mainPanel;
  private JButton livingButton;
  private JButton maritalButton;
  private JButton genderButton;
  private JButton raceButton;
  private JButton ethnicityButton;

  public ChartPanel(ChartModel linkedChartModel)
  {
    super(new BorderLayout());
    chartModel = linkedChartModel;

    createStartUp();
  }

  private void createStartUp()
  {
    createButtonPanel();
    createPiePanel();
    createBarPanel();
    createMainPanel();
    add(mainPanel,BorderLayout.CENTER);
    validate();
  }

  private void createButtons()
  {
    livingButton = new JButton("Living Status");
    livingButton.addActionListener((ActionEvent act) -> makePieChart(livingButton.getText()));
    maritalButton = new JButton("Marital");
    maritalButton.addActionListener((ActionEvent act) -> makePieChart(maritalButton.getText()));
    genderButton = new JButton("Gender");
    genderButton.addActionListener((ActionEvent act) -> makePieChart(genderButton.getText()));
    raceButton = new JButton("Race");
    raceButton.addActionListener((ActionEvent act) -> makeBarChart(raceButton.getText()));
    ethnicityButton = new JButton("Ethnicity");
    ethnicityButton.addActionListener((ActionEvent act) -> makeBarChart(ethnicityButton.getText()));
  }

  private void createButtonPanel()
  {
    createButtons();
    pieButtonPanel = new JPanel(new GridLayout(3,1,10,5));
    pieButtonPanel.add(livingButton);
    pieButtonPanel.add(maritalButton);
    pieButtonPanel.add(genderButton);
    barButtonPanel = new JPanel(new GridLayout(2,1,10,5));
    barButtonPanel.add(raceButton);
    barButtonPanel.add(ethnicityButton);
  }

  private void createPiePanel()
  {
    piePanel = new JPanel(new BorderLayout());
    piePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK,1,true),"Pie Chart"));
    piePanel.add(pieButtonPanel,BorderLayout.CENTER);
  }

  private void createBarPanel()
  {
    barPanel = new JPanel(new BorderLayout());
    barPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK,1,true),"Bar Chart"));
    barPanel.add(barButtonPanel,BorderLayout.CENTER);
  }

  private void createMainPanel()
  {
    mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(piePanel,BorderLayout.NORTH);
    mainPanel.add(barPanel,BorderLayout.CENTER);
  }

  private void makePieChart(String category)
  {
    String targetColName;
    String filterBy;
    String[] labels;
    boolean haveOthers = false;
    if (category.compareTo("Living Status") == 0)
    {
      targetColName = "DEATHDATE";
      filterBy = ""; // Empty deathdate is assumed as alive
      labels = new String[]{"Alive","Dead"};
    }
    else if (category.compareTo("Marital") == 0)
    {
      targetColName = "MARITAL";
      filterBy = "S";
      labels = new String[]{"Single","Married","Others"};
      haveOthers = true;
    }
    else
    {
      targetColName = "GENDER";
      filterBy = "F";
      labels = new String[]{"Female","Male"};
    }
    Object[] categoryData = chartModel.getCategoryData(targetColName);
    new PieChart(category,categoryData,filterBy,haveOthers,labels);
  }

  private void makeBarChart(String category)
  {
    String targetColName;
    if (category.compareTo("Race") == 0)
    {
      targetColName = "RACE";
    }
    else
    {
      targetColName = "ETHNICITY";
    }
    Object[] categoryData = chartModel.getCategoryData(targetColName);
    new BarChart(category,categoryData);
  }
}
