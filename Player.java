import java.awt.Color;
import java.awt.Graphics;

/**
 * The player for the game. Very basic.
 */
public class Player {
	public static int size = 20; // same size for all players

	private int x, y;
	private boolean up;

	public Player(int x, int y) {
		this.x = x;
		this.y = y;

		up = false;
	}

	/**
	 * Move the player with given values
	 *
	 * @param addX 	the change in x-coordinate
	 * @param addY 	the change in y-coordinate
	 */
	public void move(int addX, int addY) {
		x += addX;
		y += addY;
	}

	/**
	 * @param up 	whether or not the player moves up
	 */
	public void moveUp(boolean up) {
		this.up = up;
	}

	/**
	 * Set a new position for the player
	 *
	 * @param x 	the new x-coordinate
	 * @param y 	the new y-coordinate
	 */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return 	x-coordinate for the player
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return 	y-coordinate for the player
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return 	if the player is moving up
	 */
	public boolean getUpMovement() {
		return up;
	}

	/**
	 * Paint the player
	 */
	public void paint(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(x, y, size, size);

		g.setColor(Color.black);
		g.drawRect(x, y, size, size);
	}
}