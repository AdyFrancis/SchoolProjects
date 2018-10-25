import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.sound.midi.Instrument;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.Write;

/**
 * credit to: 
 * https://www.dreamincode.net/forums/topic/379672-cartesian-plane/
 * 
 *
 */
class Animate extends JPanel implements ActionListener  {

	double xAxis = 0;
    double yAxis = 0;
	private static ArrayList<Point> ptsArray; 
	private static ArrayList<Line> lineArray;
	
	Color color;

	Timer time = new Timer(1020, (ActionListener) this);
	
	public Animate() {
		Parser parser = new Parser("square.txt");
		
		ptsArray = parser.getPointArray();
		lineArray = new ArrayList<Line>();
		
		//make the lines
		for (int i = 0; i < ptsArray.size() - 1; i++) {
			Point x = ptsArray.get(i);
			Point y = ptsArray.get(i + 1);
			Line line = new Line (x, y);
			lineArray.add(line);
		}
		
		lineArray.add(new Line (ptsArray.get(0), ptsArray.get(ptsArray.size() - 1)));
		color = Color.black;
		setBackground(Color.WHITE);
		time.start();
		
	}
	
	public void drawLine(Graphics2D g2, double x1, double y1, double x2, double y2) {
		
		g2.draw(new Line2D.Double(x1, y1, x2, y2));
	}
	
	public void drawPoint(Graphics2D g2, double x, double y) {
		g2.draw(new Ellipse2D.Double(x, y, 1, 1));
	}
	

	
	public void animate(Graphics2D g2) {
		g2.setColor(color);
		g2.setStroke(new BasicStroke(5));
	
		
		for (Point p: ptsArray) {
			drawPoint(g2, p.getX() + xAxis , p.getY() + yAxis );
			time.stop();
			Play.midi(new Note());
			time.start();
		}
		g2.setStroke(new BasicStroke(3));
		for (Line l: lineArray) {
			drawLine(g2, l.getX().getX() + xAxis, l.getX().getY() + yAxis, l.getY().getX() + xAxis, l.getY().getY() + yAxis);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		animate(g2);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (xAxis < 500 & yAxis < 500) {
			xAxis++;
			yAxis++;
			repaint();
		}
		
	}	
}

public class Visualizer {

	public static void main(String[] args) {
		
		
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
			JFrame frame = new JFrame();
			frame.setTitle("Visualizer");
			frame.setSize(1000, 1000);
			frame.setVisible(true);
			frame.setResizable(false);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.add(new Animate());
			}
		});
	}
	
	
}
