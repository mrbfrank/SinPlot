/*
  File: SinPlot.java
  Description: This program plots the sinusouidal function f(x) = sin(x) + 2.0 * cos(x)
  Author: Brian Frank
*/

import javax.swing.*;
import java.awt.*;

public class SinPlot extends JFrame
{
    private static double[]x;
    private static double[]y;
    private static final int NUM_SAMPLE_POINT = 100;
    static double interval = (2 * Math.PI) /  NUM_SAMPLE_POINT;
    
    public SinPlot () //default constructor
    {
      setTitle ( "SinPlot" );
      getContentPane().add ( new DrawingPanel() );
    }
    
    public static void getFunctionValue() //creates an array of x and y coordinates for the given sinusoidal function
    {
        double xValue = -Math.PI;
        x = new double[NUM_SAMPLE_POINT];
        y = new double[NUM_SAMPLE_POINT];
        
        for (int i=0; i < NUM_SAMPLE_POINT; i++)
        {
            x[i] = xValue;
            xValue += interval;
            y[i] = Math.sin(xValue) + 2.0 * Math.cos(xValue);
            //y[i] = xValue*xValue*xValue;
            //System.out.println(x[i]+ " " + y[i]);
        }
    }

    public static void main ( String[] args ) // main method
    {
      Plot myPlot = new Plot ("SinPlot");
      myPlot.setSize ( 500, 500 );
      myPlot.setVisible ( true );
      getFunctionValue();
      myPlot.drawGraph(x,y,"f(x) = sin(x) + 2.0 * cos(x)", "x", "y", "random");         //define the graph function and color here
    }
    
}

class Plot extends JFrame
{
    DrawingPanel panel = new DrawingPanel();
    
    public Plot()
    {
        getContentPane().add(panel);
    }
    public Plot (String FrameTitle)
    {
        setTitle(FrameTitle);
        getContentPane().add(panel);
    }
    public void drawGraph ( double[] x, double[] y, String graphTitle, String xLabel, String yLabel, String color )
    {
        panel.plotGraph( x, y, graphTitle, xLabel, yLabel, color );
    }
}


class DrawingPanel extends JPanel
{
    private double[]x,y;
    private String graphTitle;
    private String xLabel;
    private String yLabel;
    private Color color;
    private Color randcolor;
    
    public void plotGraph( double[] xValue, double[] yValue, String title, String xlbl, String ylbl, String col )
    {
        x = xValue;
        y = yValue;
        graphTitle = title;
        xLabel = xlbl;
        yLabel = ylbl;
        color = myGetColor(col);
    }
    
    
    
    public Color myGetColor( String col )
    {
        Color color = Color.black;
        if( col.equals("blue"))
            color = Color.blue;
        else if(col.equals("cyan"))
            color = Color.cyan;
        else if(col.equals("darkGray"))
            color = Color.darkGray;
        else if(col.equals("gray"))
            color = Color.gray;
        else if(col.equals("green"))
            color = Color.green;
        else if(col.equals("lightGray"))
            color = Color.lightGray;
        else if(col.equals("magenta"))
            color = Color.magenta;
        else if(col.equals("orange"))
            color = Color.orange;
        else if(col.equals("pink"))
            color = Color.pink;
        else if(col.equals("red"))
            color = Color.red;
        else if(col.equals("white"))
            color = Color.white;
        else if(col.equals("yellow"))
            color = Color.yellow;
        else if(col.equals("random")) // grabs random color from colorCycle
        {
            colorCycle();
            color = randcolor;
        }
        
        return color;
    }
    
    public void paintComponent ( Graphics g )
    {
        super.paintComponent ( g );
    
        // Set up a Cartesian coordinate system

        // get the size of the drawing area     
        Dimension size = this.getSize();

        // place the origin at the middle
        g.translate (size.width / 2, size.height / 2);

        // draw the x and y axes
        drawXYAxes (g);
        
        // draw the graph
        myPlotGraph (g);

    }
   
    private void drawXYAxes (Graphics g)
    {
        Dimension size = this.getSize();
        int hBound = size.width / 2;
        int vBound = size.height / 2;
        int tic = size.width / 100;
            
        // draw the x-axis
        g.drawLine (-hBound, 0, hBound, 0);

        // draw the tic marks along the x axis
        for (int k = -hBound; k <= hBound; k += 10)
            g.drawLine (k, tic, k, -tic);
            
        // draw the y-axis
        g.drawLine (0, vBound, 0, -vBound); 

        // draw the tic marks along the y axis
        for (int k = -vBound; k <= vBound; k += 10)
            g.drawLine (-tic , k, +tic, k);                   
    }
    
    public Color colorCycle() //under development
    {
        double randR = Math.random();
        double randG = Math.random();
        double randB = Math.random();
        //double randA = Math.random();                                                         //Alpha channel
        //randcolor = new Color( (float)randR, (float)randG, (float)randB, (float)randA );      //RGB model w/ Alpha
        randcolor = new Color( (float)randR, (float)randG, (float)randB );                     //RGB model
        return randcolor;
    }
    
    public void myPlotGraph( Graphics g )
    {
        Dimension size = this.getSize();
        int hBound = size.width/2;
        int vBound = size.height/2;
        int xTitlePosition = -230;
        int yTitlePosition = -220;
        double maxY = Math.abs(x[0]);
        double relativeX = size.width/(x.length * Math.abs(x[1] - x[0]));
        double relativeY = size.height/(maxY * 2);
        g.setColor(color);
        g.drawString(graphTitle, xTitlePosition, yTitlePosition);
        g.drawString(xLabel, 220, 20);
        g.drawString(yLabel, 5, 220);
        
  /*      for( int i=0; i < y.length; i++)
            if(Math.abs(x[i]) > maxY)
                maxY = Math.abs(x[i]);
   */    

        if(color == randcolor) //if-else *experimental*
            {
            for(int i=0; i < (x.length-1); i++)
                {   
                    colorCycle();
                    g.setColor(randcolor);
                    g.drawLine((int)(x[i] * relativeX), (int)-(y[i] * relativeY), (int)(x[i+1]), (int)-(y[i+1]));    //uncomment this line for a 3D effect!
                    g.drawLine((int)(x[i] * relativeX), (int)-(y[i] * relativeY), (int)(x[i+1] * relativeX), (int)-(y[i+1] * relativeY));
                }
            }
        else
        for(int i=0; i < (x.length-1); i++)
        {   
            /*if(color == randcolor)
            {
                colorCycle();
                g.setColor(randcolor);
            }
            */
            g.drawLine((int)(x[i] * relativeX), (int)-(y[i] * relativeY), (int)(x[i+1]), (int)-(y[i+1]));    //uncomment this line for a 3D effect!
            g.drawLine((int)(x[i] * relativeX), (int)-(y[i] * relativeY), (int)(x[i+1] * relativeX), (int)-(y[i+1] * relativeY));
            
        }
    }
}