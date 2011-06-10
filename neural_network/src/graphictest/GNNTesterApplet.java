package graphictest;

import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.text.DecimalFormat;

import neural_network.NeuralNetwork;
import neural_network.SimpleValue;
import processing.core.PApplet;
import processing.core.PFont;
import training.ITrainingInstance;
import training.TrainingSet;

// Based on the work of Daniel Shiffman
// http://www.openprocessing.org/visuals/?visualID=159
public class GNNTesterApplet extends PApplet implements MouseWheelListener
{
	private static final long serialVersionUID = 8922750214836902499L;
	
	private float 			angleX, angleY;
	private float			distance;
	private PFont 			font;
	
	private int 			segments;
	private int 			segmentSize;
	private int 			totalSize;
	private double[][] 		data;
	
	private NeuralNetwork	nn;
	private TrainingSet 	ts;
	
	private double 			error;
	private int 			iterations;
	
	private boolean 		animate;
	
	public GNNTesterApplet(NeuralNetwork nn, TrainingSet ts, int segments, int segmentSize)
	{
		this.segments = segments;
		this.segmentSize = segmentSize;
		this.totalSize = segmentSize * (segments - 1);
		
		data = new double[segments][segments];
		
		this.nn = nn;
		this.ts = ts;
		
		angleX = 0;
		angleY = 0;
		distance = totalSize;
			
		iterations = 0;
		
		animate = true;
		
		this.addMouseWheelListener(this);
	}
	
	public void keyReleased(KeyEvent ev)
	{
		if(ev.getKeyCode() == KeyEvent.VK_SPACE)
			animate = !animate;
	}
	
	public void mouseWheelMoved(MouseWheelEvent ev)
	{
		distance += ev.getWheelRotation() * 10;
	}
	
	public void updateInfo()
	{
		iterations++;
		error = nn.rootMeanSquaredError(ts);
		
		// Update data table
		SimpleValue[] inputs = new SimpleValue[]{new SimpleValue(0), new SimpleValue(0)};
	
		for(int i = 0; i < segments; i++)
		for(int j = 0; j < segments; j++)
		{
			inputs[0].value = (double)i / (segments - 1);
			inputs[1].value = (double)j / (segments - 1);
				
			nn.setInputs(inputs);
			
			data[i][j] = 0.95 * data[i][j] + 0.05 * (nn.getOutput()[0].getValue() - 0.5);
		}
	}

	public void setup()
	{
		size(500, 500, P3D);
		
		font = loadFont("GillSans-16.vlw");
	}
	
	public void draw()
	{
		background(50, 50, 50);
		
		pushMatrix();
		translate(width / 2, height / 2, -distance);
		rotateX(PI / 3 + angleY);
		rotateZ(angleX);
		
		pushMatrix();
		stroke(200);
		noFill();
		box(totalSize);
		popMatrix();
		
		// Draw surface
		stroke(128);
		for (int x = 0; x < segments - 1; x++)
		for (int y = 0; y < segments - 1; y++)
		{
			pushMatrix();
			
			beginShape(QUADS);
			translate(x * segmentSize - totalSize / 2, y * segmentSize - totalSize / 2, 0);
			fill((float)(255 * (data[x]    [y]     + 0.5)), 200);
			vertex(0,                     0, (int)(totalSize * data[x]    [y]    ));
			fill((float)(255 * (data[x + 1][y]     + 0.5)), 200);
			vertex(segmentSize,           0, (int)(totalSize * data[x + 1][y]    ));
			fill((float)(255 * (data[x + 1][y + 1] + 0.5)), 200);
			vertex(segmentSize, segmentSize, (int)(totalSize * data[x + 1][y + 1]));
			fill((float)(255 * (data[x]    [y + 1] + 0.5)), 200);
			vertex(0,           segmentSize, (int)(totalSize * data[x]    [y + 1]));
			endShape();
			
			popMatrix();
		}
		
		// Draw goal points
		sphereDetail(5);
		for(ITrainingInstance instance : ts)
		{
			int posX = (int)(totalSize * (instance.getInputs()[0].value - 0.5));
			int posY = (int)(totalSize * (instance.getInputs()[1].value - 0.5));
			int posZ = (int)(totalSize * (instance.getOutputs()[0].value - 0.5));
			
			pushMatrix();
			
			noStroke();
			translate(posX, posY, posZ);
			fill(255, 0, 0, 255);
			sphere(totalSize / 50);

			popMatrix();
		}

		popMatrix();
		
		textFont(font);
		fill(255);
		text("Iterations: " + iterations, 10, 20);
		text("Error: " + new DecimalFormat("0.0000").format(error), 10, 40);
		
		if(mousePressed)
		{
			angleX += (pmouseX - mouseX) / 100f;
			angleY += (pmouseY - mouseY) / 100f;
			
			if(Math.abs(angleY) > 1)
				angleY = Math.signum(angleY);
		}
		else
		{
			if(animate)
			{
				angleX += 0.005;
				angleY -= angleY / 10;
			}
		}
	}
}