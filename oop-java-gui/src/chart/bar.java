package chart;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BorderLayout;
import java.awt.geom.AffineTransform;

class BarChart extends JFrame
{
  protected BarChart(String header, Object[] information)
  {
    super("Bar Chart");
    setLayout(new BorderLayout());
    JScrollPane chartScroller = new JScrollPane(new Bar(information));
    add(chartScroller,BorderLayout.CENTER);

    createHeader(header);
    run();
  }

  private void createHeader(String header)
  {
    JLabel headerLabel = new JLabel(header,SwingConstants.CENTER);
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

class Bar extends JPanel
{
  private final int BAR_HEIGHT = 350;
  private final int BAR_WIDTH = 50;
  private final int BAR_BOTTOM = 400;

  private Object[] chartInfo;
  private ArrayList<String> barLabels;
  private int[] heightProportion;

  protected Bar(Object[] information)
  {
    setPreferredSize(new Dimension(300,300));
    chartInfo = information;
    calculateProportion();
  }

  @Override
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;
    int total = chartInfo.length;
    int startBarWidth = 50;
    for (int proportion : heightProportion)
    {
      int barHeight = (int)((double)proportion/total*BAR_HEIGHT);
      g2d.setColor(Color.BLACK);
      g2d.drawRect(startBarWidth,BAR_BOTTOM-barHeight,BAR_WIDTH,barHeight);
      startBarWidth += BAR_WIDTH;
    }
    AffineTransform affTrans = new AffineTransform();
    affTrans.rotate(Math.toRadians(90));
    g2d.setTransform(affTrans);
    g2d.setColor(Color.RED);
    startBarWidth = -50;
    int index = 0;
    for (String label : barLabels)
    {
      g2d.drawString(label,BAR_BOTTOM+20,startBarWidth-25);
      startBarWidth -= 50;
      index ++;
    }
  }

  private void calculateProportion()
  {
    initLabels();
    initProportion();
    for (Object info : chartInfo)
    {
      String data = info.toString();
      int index = barLabels.indexOf(data);
      heightProportion[index] += 1;
    }
  }

  private void initProportion()
  {
    heightProportion = new int[barLabels.size()];
    int index;
    for (index=0;index<barLabels.size();index++)
    {
      heightProportion[index] = 0;
    }
  }

  private void initLabels()
  {
    barLabels = new ArrayList<String>();
    for (Object info : chartInfo)
    {
      String data = info.toString();
      boolean result = presentLabels(data);
      if (result == false)
      {
        barLabels.add(data);
      }
    }
  }

  private boolean presentLabels(String checkLabel)
  {
    int result = barLabels.indexOf(checkLabel);
    if (result == -1)
    {
      return false;
    }
    return true;
  }
}
