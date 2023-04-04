package chart;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;

class PieChart extends JFrame
{
  protected PieChart(String header, Object[] information, String targetData, boolean haveOthers, String[] labels)
  {
    super("Pie Chart");
    setLayout(new BorderLayout());
    add(new Pie(information,targetData,haveOthers,labels), BorderLayout.CENTER);

    createHeader(header);
    run();
  }

  private void createHeader(String header)
  {
    JLabel headerLabel = new JLabel(header, SwingConstants.CENTER);
    add(headerLabel,BorderLayout.NORTH);
  }

  private void run()
  {
    setPreferredSize(new Dimension(500,500));
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }
}

class Pie extends JPanel
{
  private final int CIRCLE = 100;
  private final int DIAMETER = 200;
  private final int LABEL_Y = 400;
  private final Color[] COLORLIST = new Color[]{Color.RED, Color.BLUE, Color.BLACK};

  private Object[] chartInfo;
  private String filterBy;
  private boolean otherOption;
  private String[] chartLabels;
  private int[] pieProportion;

  protected Pie(Object[] information, String targetData, boolean haveOthers, String[] labels)
  {
    setPreferredSize(new Dimension(300,300));
    chartInfo = information;
    filterBy = targetData;
    otherOption = haveOthers;
    chartLabels = labels;
    calculateProportion();
  }

  @Override
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);

    int total = chartInfo.length;
    int index = 0;
    int startAngle = 0;
    int labelPosX = 100;
    for (int proportion : pieProportion)
    {
      int arcAngle = (int)((double)proportion/total*360);
      g.setColor(COLORLIST[index]);
      g.fillArc(CIRCLE,CIRCLE,DIAMETER,DIAMETER,startAngle,arcAngle);
      g.drawString(chartLabels[index], labelPosX, LABEL_Y);
      startAngle += arcAngle;
      index ++;
      labelPosX += 100;
    }
    int space = 360 - startAngle; // remove white lines due to decimal difference
    g.fillArc(CIRCLE,CIRCLE,DIAMETER,DIAMETER,startAngle,space);
  }

  private void initProportion()
  {
    pieProportion = new int[chartLabels.length];
    int index;
    for (index=0;index<chartLabels.length;index++)
    {
      pieProportion[index] = 0;
    }
  }

  private void calculateProportion()
  {
    initProportion();
    for (Object info : chartInfo)
    {
      String infoStr = info.toString();
      if (otherOption == true && infoStr.compareTo("") == 0)
      {
        pieProportion[2] += 1;
        continue;
      }
      if (infoStr.compareTo(filterBy) == 0)
      {
        pieProportion[0] += 1;
        continue;
      }
      pieProportion[1] += 1;
    }
  }
}
