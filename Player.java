/**
 * The player for the game. Very basic.
 *
 * @author Per Nyberg
 * @version 2017.04.11
 * @last_updated 2017.04.15
 */

import java.awt.Color;
import java.awt.Graphics;

public class Player {
	public static int size = 20; // same size for all players

	private int x, y;
	private boolean up, left, right;

	/**
	 * Set starting position. All movement false in beginning.
	 */
	public Player(int x, int y) {
		this.x = x;
		this.y = y;

		up = false;
		left = false;
		right = false;
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
	 * @param left 	whether or not the player moves left
	 */
	public void moveLeft(boolean left) {
		this.left = left;
	}

	/**
	 * @param right 	whether or not the player moves right
	 */
	public void moveRight(boolean right) {
		this.right = right;
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
	 * @return 	if the player is moving left
	 */
	public boolean getLeftMovement() {
		return left;
	}

	/**
	 * @return 	if the player is moving right
	 */
	public boolean getRightMovement() {
		return right;
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