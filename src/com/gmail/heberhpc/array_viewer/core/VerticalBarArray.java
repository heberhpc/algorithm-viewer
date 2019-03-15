/**/


package com.gmail.heberhpc.array_viewer.core;


import java.awt.Color;


import acm.graphics.GCompound;
import acm.graphics.GLabel;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;


public class VerticalBarArray extends GraphicsProgram{
	
	//---FIELDS---//
	//matrix of data
	private int[] data;
	
	//maximum and minimum value in the array
	private int max;
	private int min;
	
	//matrix of bars to visualize data
	private GRect[] bars;
		
	//---DISPLAY'S ELEMENTS---//
	//frame:the rectangle that encompasses all visual elements
	private GRect frame;
	private double frameWidth = 600;
	private double frameHeight = 480;
	private double offSetMargin=frameWidth*0.2; // empty white border

	//background:rectangular area of ​​bars
	private GRect background;
	private double backgroundWidth=frameWidth;
	private double backgroundHeight=frameHeight*0.9;
	
	//infoPanel:bottom information area
	private GRect infoPanel;
	private double infoPanelWidth=frameWidth;
	private double infoPanelHeight=frameHeight-backgroundHeight;
	private Color infoPanelColor = Color.BLACK;
	
	//Info Texts
	GCompound Labels; // graphic object that groups all labels
	private GLabel readingLabel; //how many readings
	private GLabel writingLabel; //how many writings
	private GLabel otherInfo;	//other information
	private GLabel algotithmName; //algorithm title
	private String algName = "Unknow"; // default algorithm title


	//graphicElements:graphic object that groups all other graphic elements
	private GCompound graphicElements;
	
	//default colors
	private Color barNormalColor = Color.GRAY;
	private Color barReadColor = Color.YELLOW;
	private Color barWriteColor = Color.RED;
	private Color setFoundColor = Color.GREEN;
	
	//speed: pause to reduce velocity 
	//as higher the value, more slow is the animation
	private int pauseTime = 5;
	
	//statistics
	private int readings;
	private int recordings;
	
	
	//CONSTRUCTORS//
	public VerticalBarArray (int [] data){
		//make a clone of the array	
		this.data = data.clone();
		min=this.getMin();
		max=this.getMax();
		this.start();	
	}


	//---RUN:animation---//
	public void run() {
		setup();
	}


	//---ARRAY OPERATIONS---//
	/**
	 * Returns the size of the array.
	 * @return int a value that indicates the size of the internal array.
	 */
	public int length() {
		return data.length;
	}
	
	
	/**
	 * Returns a value in a specific index "index".
	 * @param index the index of the array (index origin=0).
	 * @return int value stored in the index.
	 */
	public int read(int index) throws IndexOutOfBoundsException{
		//VERIFICATIONS
		//the index is valid?
		if (index < 0 || index > data.length-1) {
			throw new IndexOutOfBoundsException("Invalid Index:"+index);
		}
		
		//effects
		readEffect(bars[index], data[index]);
		gLabelUpdate();
		
		//update statistics and return value
		readings++;
		return data[index];
	}
	

	/**
	 * Write a value inside the array in the specific index.
	 * @param index the index of the array where the value will be written (index origin=0). 
	 * @param value the value to be written.
	 */
	public void write(int index, int value) throws IndexOutOfBoundsException {
		//VERIFICATIONS
		//the index is valid?
		if (index < 0 || index > data.length-1) {
			throw new IndexOutOfBoundsException("Invalid Index:"+index);
		}
		
		//update value/statistics
		data[index]=value;
		recordings++;
		
		//update bars
		graphicElements.remove(bars[index]);
		bars[index] = createBar(index);
		graphicElements.add(bars[index]);
				
		//effects
		writeEffect(bars[index], data[index]);
		gLabelUpdate();
	}
	
	
	/** 
	 * Print all Values of the array
	 */
	public void printAll() {
		System.out.println("***PRINTING-START***");
		for (int i = 0 ; i < data.length ; i++) {
			System.out.println("Index ["+i+"] = "+data[i]);
		}
		System.out.println("***PRINTING-END***");
	}

	
	//find the smallest value in the array
	private int getMin() {
		int min = data[0];
		for (int i = 0 ; i < data.length ; i++) {
			if (data[i] < min) {
				min = data[i];
			}
		}
		System.out.println("Minimum value: "+min);
		return min;
	}

	
	//find the highest value in the array
	public int getMax() {
		int max = data[0];
		for (int i = 0 ; i < data.length ; i++) {
			if (data[i] > max) {
				max = data[i];
			}
		}
		System.out.println("Maximum value: "+max);
		return max;
	}

	
	//---ANIMATION OPERATION---//
	//setup elements of visualization
	private void setup() {
		
		//set size
		this.resize((int)(frameWidth+offSetMargin),(int) (frameHeight+offSetMargin));
		
		//graphicElements:graphic object that groups all other graphic elements
		graphicElements = new GCompound();
		
		//frame:the rectangle that encompasses all visual elements
		frame = new GRect(this.getX(),this.getY(), frameWidth, frameHeight);
		frame.setColor(Color.RED);
		frame.setFilled(true);
		frame.setFillColor(Color.RED);
		graphicElements.add(frame);
		
		
		//background:rectangular area of ​​bars
		background = new GRect(this.getX(),this.getY(), backgroundWidth, backgroundHeight);
		background.setFilled(true);
		background.setFillColor(Color.WHITE);
		graphicElements.add(background);

		//infoPanel:bottom information area
		double inforPanelX = background.getX();
		double inforPanelY = background.getY()+background.getHeight();
		infoPanel = new GRect (inforPanelX, inforPanelY, infoPanelWidth, infoPanelHeight);
		infoPanel.setFilled(true);
		infoPanel.setFillColor(infoPanelColor);
		graphicElements.add(infoPanel);
		
		//STATISTICS LABELS
		Labels = new GCompound();// graphic object that groups all labels

		//positon references
		double space = 40;
		double yLabelsPosition = infoPanel.getY()+infoPanel.getHeight();
				
		//readings label
		String read = " READINGS: "+readings;
		readingLabel = new GLabel(read);
		readingLabel.setFont("arial");
		readingLabel.setColor(barReadColor);
		readingLabel.setLocation(0, yLabelsPosition);
		Labels.add(readingLabel);
		
		//recordings label
		String rec = " RECORDINGS: "+recordings;
		writingLabel = new GLabel(rec);
		writingLabel.setFont("arial");
		writingLabel.setColor(barWriteColor);
		writingLabel.setLocation(readingLabel.getX() + readingLabel.getWidth() + space, yLabelsPosition);
		Labels.add(writingLabel);
		
		//other info label
		String info = " ARRAY SIZE: "+data.length;
		otherInfo = new GLabel(info);
		otherInfo.setFont("arial");
		otherInfo.setColor(Color.DARK_GRAY);
		otherInfo.setLocation(writingLabel.getX() + writingLabel.getWidth() + space, yLabelsPosition);
		Labels.add(otherInfo);
		
		//algorithm name label
		String alg = " ALGORT: "+algName;
		algotithmName = new GLabel(alg);
		algotithmName.setFont("arial");
		algotithmName.setColor(Color.WHITE);
		double xP=(Labels.getWidth() - algotithmName.getWidth())/2;
		algotithmName.setLocation(xP, yLabelsPosition - otherInfo.getHeight() - otherInfo.getDescent() );
		Labels.add(algotithmName);

		//labels positioning
		double xLabels = (infoPanel.getWidth() -  Labels.getWidth())/2;
		double yLabels = 0 - (infoPanel.getHeight() - Labels.getHeight())/2;
		Labels.setLocation(xLabels, yLabels );
		
		//group all labels
		graphicElements.add(Labels);
		
		//position of all elements
		double xGraphicElements = (this.getWidth()-graphicElements.getWidth()) / 2;
		double ygraphicElements = (this.getHeight()-graphicElements.getHeight()) / 2;	
		graphicElements.setLocation(xGraphicElements, ygraphicElements);

		//create array of bars to visualize data
		bars = new GRect[data.length];
		for (int i = 0 ; i < bars.length ; i++) {
			bars[i] = createBar(i);
			graphicElements.add(bars[i]);
		}

		//add all elements to the area
		add(graphicElements);
	}
	
	
	//update statistic label
	private void gLabelUpdate() {
		
		String read = "READINGS: "+readings;
		String rec = "RECORDINGS: "+recordings;
		String info = "ARRAY SIZE: "+data.length;
		
		readingLabel.setLabel(read);
		writingLabel.setLabel(rec);
		otherInfo.setLabel(info);
	}

	
	//create and return a bar with correct size and location
	private GRect createBar(int index) {
		
		//define size
		double barvWidth = ((double) background.getWidth() / (double) data.length);
		double barHeight = ((double) data[index] / (double) max * background.getHeight());
			
		//define location
		double xPosBar = index * barvWidth + background.getX();
		double yPosBar = (background.getHeight() - barHeight) + background.getY();

		//creation
		GRect a = new GRect (xPosBar, yPosBar, barvWidth, barHeight);
		a.setColor(Color.BLACK);
		a.setFilled(true);
		a.setFillColor(barNormalColor);
		
		return a;
	}
	
	
	//effects of readings
	private void readEffect(GRect g, int value) {
		
		//save old state
		Color borderCurrent = g.getColor(); 
		Color fillCurrent = g.getFillColor();
		boolean isFilled = g.isFilled();
		
		//blink
		g.setColor(barReadColor);
		g.setFillColor(barReadColor);
		pause(pauseTime);
		
		//reset to old state
		g.setColor(borderCurrent);
		g.setFillColor(fillCurrent);
		g.setFilled(isFilled);
		
	}

	
	//effects of writing
	private void writeEffect(GRect g, int value) {
		
		//save old state
		Color borderCurrent = g.getColor(); 
		Color fillCurrent = g.getFillColor();
		boolean isFilled = g.isFilled();
		
		//blink
		g.setColor(barWriteColor);
		g.setFillColor(barWriteColor);
		pause(pauseTime);
		
		//reset to old state
		g.setColor(borderCurrent);
		g.setFillColor(fillCurrent);
		g.setFilled(isFilled);
		
	}
	
	
	//---EXTERNAL EFFECTS---//
	
	/**
	 * Mark a value/bar as found
	 * @param index where the value was found.
	 */
	public void markValueAsFound(int index) {
		bars[index].setFillColor(setFoundColor);
	}
	
	
	/**
	* Setup a name for the running algorithm to appear in the info area.
	* @param name is the name that identifies the algorithm.
	*/
	public void setAlgorithmName(String name) {
		//update string
		this.algName = name;
		
		//store old and useful value of y position
		double oldY = algotithmName.getY();
		
		//remove from label group: important for resize correctly
		Labels.remove(algotithmName);
		
		//update label
		algotithmName.setLabel(algName);
		
		//update position and add it again
		double xP=(Labels.getWidth() - algotithmName.getWidth())/2;
		algotithmName.setLocation(xP, oldY);
		Labels.add(algotithmName);
	}
	
	
	//setup the velocity
	/**
	* speed: pause to reduce velocity 
	* as higher the value, more slow is the animation
	* @param pauseTime is how miliseconds the animation will stop
	*/
	public void setPauseVelocity(int pauseTime) {
		this.pauseTime = pauseTime;
	}
}