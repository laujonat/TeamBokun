package org.openstreetmap.gui.jmapviewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import central.BokunCentral;

public class HistDataWindow extends JFrame {
	
	public HistDataWindow() {
		
		super("Historical Data");
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setSize(1400, 500);
		
		JPanel tablePanel = new JPanel() {
			public Dimension getPreferredSize() {
				return new Dimension(600,665);
			}
		};
		//tablePanel.setBackground(Color.BLUE);
		
		JPanel graphPanel = new JPanel() {
			public Dimension getPreferredSize() {
				return new Dimension(670, 665);
			}
		};
		//graphPanel.setBackground(Color.BLUE);
		
		//retrieve freeway data
		int firstFilled = 0;
		int lastFilled = 23;
		
		double[] E10Speeds = null;
		double[] W10Speeds = null;
		double[] E105Speeds = null;
		double[] W105Speeds = null;
		double[] S101Speeds = null;
		double[] N101Speeds = null;
		double[] S405Speeds = null;
		double[] N405Speeds = null;
		try {
			E10Speeds = BokunCentral.SQLConnection.getAverageSpeedsOf("E10");
			W10Speeds = BokunCentral.SQLConnection.getAverageSpeedsOf("W10");
			E105Speeds = BokunCentral.SQLConnection.getAverageSpeedsOf("E105");
			W105Speeds = BokunCentral.SQLConnection.getAverageSpeedsOf("W105");
			S101Speeds = BokunCentral.SQLConnection.getAverageSpeedsOf("S101");
			N101Speeds = BokunCentral.SQLConnection.getAverageSpeedsOf("N101");
			S405Speeds = BokunCentral.SQLConnection.getAverageSpeedsOf("S405");
			N405Speeds = BokunCentral.SQLConnection.getAverageSpeedsOf("N405");
		}
		catch(SQLException e) { e.printStackTrace(); }
		
		double[] searchMe = E10Speeds;
		boolean firstFound = false;
		for(int i = 0; i < 24; i++)
		{
			if(!firstFound)
			{
				if(searchMe[i] != 0)
				{
					firstFilled = i;
					firstFound = true;
				}
			}
			else
			{
				if(searchMe[i] == 0)
				{
					lastFilled = (i - 1);
				}
			}
		}
		
		
		//TABLE
		String[] columnNames = { "Hours", "East 10", "West 10", "East 105", "West 105", "South 101", "North 101", "South 405", "North 405"};
		Object[][] data = new Object[24][9]; // changed from 10 --> 9
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 24; j++)
			{
				if(i == 0)
				{
					data[j][i] = j;
				}
				else if(i == 1)
				{
					data[j][i] = E10Speeds[j];
				}
				else if(i == 2)
				{
					data[j][i] = W10Speeds[j];
				}
				else if(i == 3)
				{
					data[j][i] = E105Speeds[j];
				}
				else if(i == 4)
				{
					data[j][i] = W105Speeds[j];
				}
				else if(i == 5)
				{
					data[j][i] = S101Speeds[j];
				}
				else if(i == 6)
				{
					data[j][i] = N101Speeds[j];
				}
				else if(i == 7)
				{
					data[j][i] = S405Speeds[j];
				}
				else if(i == 8)
				{
					data[j][i] = N405Speeds[j];
				}
			}
		}
		
		JTable table = new JTable(data, columnNames);
		table.setPreferredScrollableViewportSize(new Dimension(595, 385));
		table.setFillsViewportHeight(true);
		
		JScrollPane scrollPane = new JScrollPane(table);
		JLabel tableLable = new JLabel("Average Speeds");
		tablePanel.add(tableLable);
		tablePanel.add(scrollPane);
		
		//GRAPH
		
//		XYSeries east10 = new XYSeries("East 10");
//		for(int i = firstFilled; i < (lastFilled + 1); i++)
//		{
//			east10.add(i, E10Speeds[i]);
//		}
//		
//		XYSeries west10 = new XYSeries("West 10");
//		for(int i = firstFilled; i < (lastFilled + 1); i++)
//		{
//			west10.add(i, W10Speeds[i]);
//		}
//		
//		XYSeries east105 = new XYSeries("East 105");
//		for(int i = firstFilled; i < (lastFilled + 1); i++)
//		{
//			east105.add(i, E105Speeds[i]);
//		}
//		
//		XYSeries west105 = new XYSeries("West 105");
//		for(int i = firstFilled; i < (lastFilled + 1); i++)
//		{
//			west105.add(i, W105Speeds[i]);
//		}
//		
//		XYSeries south101 = new XYSeries("South 101");
//		for(int i = firstFilled; i < (lastFilled + 1); i++)
//		{
//			south101.add(i, S101Speeds[i]);
//		}
//		
//		XYSeries north101 = new XYSeries("North 101");
//		for(int i = firstFilled; i < (lastFilled + 1); i++)
//		{
//			north101.add(i, N101Speeds[i]);
//		}
//		
//		XYSeries south405 = new XYSeries("South 405");
//		for(int i = firstFilled; i < (lastFilled + 1); i++)
//		{
//			south405.add(i, S405Speeds[i]);
//		}
//		
//		XYSeries north405 = new XYSeries("North 405");
//		for(int i = firstFilled; i < (lastFilled + 1); i++)
//		{
//			north405.add(i, N405Speeds[i]);
//		}
		
		XYSeries east10 = new XYSeries("East 10");
		for(int i = 0; i < 24; i++)
		{
			east10.add(i, E10Speeds[i]);
		}
		
		XYSeries west10 = new XYSeries("West 10");
		for(int i = 0; i < 24; i++)
		{
			west10.add(i, W10Speeds[i]);
		}
		
		XYSeries east105 = new XYSeries("East 105");
		for(int i = 0; i < 24; i++)
		{
			east105.add(i, E105Speeds[i]);
		}
		
		XYSeries west105 = new XYSeries("West 105");
		for(int i = 0; i < 24; i++)
		{
			west105.add(i, W105Speeds[i]);
		}
		
		XYSeries south101 = new XYSeries("South 101");
		for(int i = 0; i < 24; i++)
		{
			south101.add(i, S101Speeds[i]);
		}
		
		XYSeries north101 = new XYSeries("North 101");
		for(int i = 0; i < 24; i++)
		{
			north101.add(i, N101Speeds[i]);
		}
		
		XYSeries south405 = new XYSeries("South 405");
		for(int i = 0; i < 24; i++)
		{
			south405.add(i, S405Speeds[i]);
		}
		
		XYSeries north405 = new XYSeries("North 405");
		for(int i = 0; i < 24; i++)
		{
			north405.add(i, N405Speeds[i]);
		}
		
		// Add the series to your data set
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(east10);
		dataset.addSeries(west10);
		dataset.addSeries(east105);
		dataset.addSeries(west105);
		dataset.addSeries(south101);
		dataset.addSeries(north101);
		dataset.addSeries(south405);
		dataset.addSeries(north405);

//		XYSeries series = new XYSeries("East 101");
//		series.add(1,1);
//		series.add(1,2);
//		series.add(4,10);
//		
//		XYSeriesCollection dataset = new XYSeriesCollection();
//		dataset.addSeries(series);
		
		// Generate the graph
		JFreeChart chart = ChartFactory.createXYLineChart(
				"Average Speeds", // Title
				"hours of the day", // x-axis Label
				"average speed", // y-axis Label
				dataset, // Dataset
				PlotOrientation.VERTICAL, // Plot Orientation
				true, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
		);	
		   
		ChartPanel CP = new ChartPanel(chart);
		   
		graphPanel.add(CP);
		 
		add(tablePanel);
		add(graphPanel);
		
		
		
		setVisible(true);
	}

}

