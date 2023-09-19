package pong;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
    
	private static final long serialVersionUID = 1L;
	static final int SCREEN_WIDTH = 800;
    static final int SCREEN_HEIGHT = 450;
    
    static final int FPS = 60;
    
    int scorePlayer1 = 0;
	int scorePlayer2 = 0;
    
    Thread gameThread;
    
    Paddle paddle1 = new Paddle(0, SCREEN_HEIGHT/2 - Paddle.HEIGHT/2);
    Paddle paddle2 = new Paddle(SCREEN_WIDTH - Paddle.WIDTH, SCREEN_HEIGHT/2 - Paddle.HEIGHT/2);
    
    KeyHandler keyH = new KeyHandler();
    
    Ball ball = new Ball();

    GamePanel(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    
    public void update() {
    	if (keyH.p1_up && paddle1.y > 0) {
			paddle1.y -= paddle1.yVelocity;
		}
    	if (keyH.p1_down && paddle1.y < SCREEN_HEIGHT - Paddle.HEIGHT) {
			paddle1.y += paddle1.yVelocity;
		}
    	
    	if (keyH.p2_up && paddle2.y > 0) {
			paddle2.y -= paddle2.yVelocity;
		}
    	if (keyH.p2_down && paddle2.y < SCREEN_HEIGHT - Paddle.HEIGHT) {
			paddle2.y += paddle2.yVelocity;
		}
    	
    	checkCollision();
    	
    	ball.move();
    	
    	checkWin();
    }
    
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	
    	Graphics2D g2d = (Graphics2D) g;
    	
    	//draw center line
    	g2d.setColor(Color.gray);
    	Stroke dashedStroke = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, new float[]{5, 5}, 0);
        g2d.setStroke(dashedStroke);
        g2d.drawLine(SCREEN_WIDTH/2, 0, SCREEN_WIDTH/2, SCREEN_HEIGHT);
        
        g2d.setStroke(new BasicStroke());
        g2d.setColor(Color.white);
        
        //draw ball
    	g2d.fillOval(ball.x, ball.y, ball.DIAMETER, ball.DIAMETER);
    	
    	//draw paddle 1
        g2d.setColor(Color.blue);
    	g2d.fillRect(paddle1.x, paddle1.y, Paddle.WIDTH, Paddle.HEIGHT);
    	
    	//draw paddle 2
    	g2d.setColor(Color.red);
    	g2d.fillRect(paddle2.x, paddle2.y, Paddle.WIDTH, Paddle.HEIGHT);
    	
    	//draw score
    	Font font = new Font("Arial", Font.BOLD, 30);
    	g2d.setFont(font);
    	
    	g2d.setColor(Color.blue);
    	g2d.drawString(new String("" + scorePlayer1), SCREEN_WIDTH*0.25f, 50);
    	
    	g2d.setColor(Color.red);
    	g2d.drawString(new String("" + scorePlayer2), SCREEN_WIDTH*0.75f, 50);
    	
    }
    
    public void startGameThread() {
    	gameThread = new Thread(this);
    	gameThread.start();
    }

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long currentTime;
		double delay = 1000000000 / (double) FPS;
		
		while(gameThread != null) {
			currentTime = System.nanoTime();
			
			if (currentTime - lastTime >= delay) {
				update();
				
				repaint();
				
				lastTime = currentTime;
				
			}
			
			try {
	            Thread.sleep(1);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
			
		}
		
	}
	
	void checkWin() {
		if (ball.x < 0) {
			scorePlayer2++;
			ball.newBall();
		}else if (ball.x > SCREEN_WIDTH) {
			scorePlayer1++;
			ball.newBall();
		}
	}
	
	void checkCollision() {
		//ball and top and bottom wall collision
		if (ball.y < 0 || ball.y > SCREEN_HEIGHT - ball.DIAMETER) {
			ball.yVelocity *= -1;
		}
		
		Rectangle ballRect = new Rectangle(ball.x, ball.y, ball.DIAMETER, ball.DIAMETER);
		
		Line2D line1 = new Line2D.Double(paddle1.x + Paddle.WIDTH, paddle1.y - 1, paddle1.x + Paddle.WIDTH, paddle1.y + Paddle.HEIGHT + 1);
		Line2D line2 = new Line2D.Double(paddle2.x,paddle2.y - 1,paddle2.x, paddle2.y + Paddle.HEIGHT + 1);
		
		if (ballRect.intersectsLine(line1) || ballRect.intersectsLine(line2)) {
			ball.xVelocity *= -1;
		}
		
		Line2D topLine1 = null, bottomLine1 = null, topLine2 = null, bottomLine2 = null;
		
		topLine1 = new Line2D.Double(paddle1.x, paddle1.y, paddle1.x + Paddle.WIDTH, paddle1.y);
		bottomLine1 = new Line2D.Double(paddle1.x, paddle1.y + Paddle.HEIGHT, paddle1.x + Paddle.WIDTH, paddle1.y + Paddle.HEIGHT);
		
		topLine2 = new Line2D.Double(paddle2.x, paddle2.y, paddle2.x + Paddle.WIDTH, paddle2.y);
		bottomLine2 = new Line2D.Double(paddle2.x, paddle2.y + Paddle.HEIGHT, paddle2.x + Paddle.WIDTH, paddle2.y + Paddle.HEIGHT);
		
		if (ballRect.intersectsLine(topLine1) || ballRect.intersectsLine(bottomLine1) || ballRect.intersectsLine(topLine2) || ballRect.intersectsLine(bottomLine2)) {
			ball.yVelocity *= -1;
		}
		
		
	}
}
