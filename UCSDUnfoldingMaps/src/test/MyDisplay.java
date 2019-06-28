package test;

import processing.core.PApplet;

public class MyDisplay extends PApplet{
	public void setup() {
		size(500, 500);
		
		background(255, 100, 200);
	}
	
	public void draw() {
		fill(255, 255, 0);
		ellipse(250, 250, 250, 250);
		fill(0, 0, 0);
		ellipse(200, 200, 50, 60);
		ellipse(300, 200, 50, 60);
		
		noFill();
		arc(250, 300, 75, 75, 0, HALF_PI);
	}
}
