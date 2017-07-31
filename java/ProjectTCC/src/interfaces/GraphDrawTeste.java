package interfaces;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import basic.FreqGraph;
import basic.Utils;

import java.awt.EventQueue;
import java.util.ArrayList;
import javax.swing.*;

public class GraphDrawTeste extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JIFRelatorioPorTag frame = new JIFRelatorioPorTag();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
   
   public GraphDrawTeste(ArrayList<FreqGraph> lFreq) {
	   	         
	   //XYSeries series = new XYSeries("Average Weight");
	   DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	   
	   for(int i=0;i<lFreq.size();i++){
		//   series.add(i, lFreq.get(i).getFrequencia());
		   dataset.setValue(lFreq.get(i).getFrequencia(), "Frequencia", Utils.parseToDataGraph(lFreq.get(i).getData()));
	   }
	   
		JFreeChart chart = ChartFactory.createBarChart("Frequência Semanal",
		  "Periodo", "Frequencia", dataset, PlotOrientation.VERTICAL,
		   false, true, false);
		
		//XYDataset xyDataset = new XYSeriesCollection(series);
		//JFreeChart chart = ChartFactory.createXYLineChart("Frequência Semanal", "Day", "Freq", xyDataset, PlotOrientation.VERTICAL, true, true, false);
		
		ChartFrame frame1=new ChartFrame("Freq Semanal",chart);
		frame1.setVisible(true);
		frame1.setSize(800,250);
		

   }
}