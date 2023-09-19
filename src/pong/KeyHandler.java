package pong;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	boolean p1_up,p1_down,p2_up,p2_down;

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_W) {
			p1_up = true;
		}
		if (code == KeyEvent.VK_S) {
			p1_down = true;
		}
		if (code == KeyEvent.VK_UP) {
			p2_up = true;
		}
		if (code == KeyEvent.VK_DOWN) {
			p2_down = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_W) {
			p1_up = false;
		}
		if (code == KeyEvent.VK_S) {
			p1_down = false;
		}
		if (code == KeyEvent.VK_UP) {
			p2_up = false;
		}
		if (code == KeyEvent.VK_DOWN) {
			p2_down = false;
		}
		
	}

}
