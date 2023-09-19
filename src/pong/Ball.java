package pong;

import java.util.Random;

public class Ball {
	final int DIAMETER = 15;
	final int BALL_SPEED = 4;
	
	int x;
	int y;
	
	int xVelocity;
	int yVelocity;
	
	Ball(){
		this.newBall();
	}
	
	void newBall() {
		Random random = new Random();
		this.x = GamePanel.SCREEN_WIDTH / 2;
		this.y = random.nextInt(GamePanel.SCREEN_HEIGHT - DIAMETER);
		
		this.xVelocity = BALL_SPEED;
		this.yVelocity = BALL_SPEED;
		
		if (random.nextBoolean()) {
			this.xVelocity *= -1;
		}
		if (random.nextBoolean()) {
			this.yVelocity *= -1;
		}
	}
	
	void move() {
		x += xVelocity;
		y += yVelocity;
	}

}
