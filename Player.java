import java.awt.Color;
import java.awt.Graphics;

public class Player {
	public static int size = 20;

	private int x, y;
	private boolean up;

	public Player(int x, int y) {
		this.x = x;
		this.y = y;

		up = false;
	}

	public void move(int addX, int addY) {
		x += addX;
		y += addY;
	}

	public void moveUp(boolean up) {
		this.up = up;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean getUpMovement() {
		return up;
	}

	public void paint(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(x, y, size, size);

		g.setColor(Color.black);
		g.drawRect(x, y, size, size);
	}
}