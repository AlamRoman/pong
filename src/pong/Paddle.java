package pong;

public class Paddle {
	static final int WIDTH = 12;
	static final int HEIGHT = 100;
	
	int x;
	int y;
	
	final int yVelocity = 10;
	
	Paddle(int x, int y){
		this.x = x;
		this.y = y;
		
	}
}
