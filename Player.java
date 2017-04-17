/**
 * The player for the game. Very basic.
 *
 * @author Per Nyberg
 * @version 2017.04.11
 * @last_updated 2017.04.17
 */

import java.awt.Color;
import java.awt.Graphics;

public class Player {
	// constants used
//---------------------------------------------------
	public final static int playerSpeed = 5; // same speed for all players
//---------------------------------------------------
	public static int size = 20; // same size for all players

	private int x, y, gravity;
	private boolean left, right, falling, setGravityToZero;

	/**
	 * Set starting position. All movement false in beginning.
	 */
	public Player(int x, int y) {
		this.x = x;
		this.y = y;

		gravity = 0;

		left = false;
		right = false;
		falling = false;
		setGravityToZero = false;
	}

	/**
	 * Set the speed for up-movement
	 */
	public void jump() {
		gravity = -(playerSpeed * 2 + 1);
		falling = true;
	}

	/**
	 * Method for adjusting movement in up/down-directions
	 */
	public void gravitize() {
		if (setGravityToZero) {
			setGravityToZero = false;
			gravity = 0;
		} else {
			gravity++;
		}
	}

	/**
	 * Stop left/right-movement
	 */
	public void stopHorisontalMovement() {
		left = false;
		right = false;
	}

	/**
	 * Stop up/down-movement
	 */
	public void stopVerticalMovement() {
		gravity = 0;
		falling = false;
	}

	/**
	 * Stop jump-before
	 */
	public void stopJump() {
		gravity /= 2;
		setGravityToZero = true;
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
	 * Adjust position for the player
	 *
	 * @param addX 	the movement along the x-axis
	 * @param addY 	the movement along the y-axis
	 */
	public void move(int addX, int addY) {
		x += addX;
		y += addY;
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
	 * @return 	the speed for the player in left/right-direction (negative for left)
	 */
	public int getHorisontalSpeed() {
		return (left ? -playerSpeed : (right ? playerSpeed : 0));
	}

	/**
	 * @return 	the speed for the player in up/down-direction (negative for up)
	 */
	public int getVerticalSpeed() {
		return gravity;
	}

	/**
	 * Check if the player may jump (again)
	 * The player may only jump if it's not falling or already jumping
	 */
	public boolean mayJump() {
		return gravity == 0 && !falling;
	}

	/**
	 * @return 	if the player is moving up
	 */
	public boolean isMovingUp() {
		return gravity < 0;
	}

	/**
	 * @return 	if the player is moving down
	 */
	public boolean isMovingDown() {
		return gravity > 0;
	}

	/**
	 * @return 	if the player is moving left
	 */
	public boolean isMovingLeft() {
		return left;
	}

	/**
	 * @return 	if the player is moving right
	 */
	public boolean isMovingRight() {
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