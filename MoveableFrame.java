/**
 * A frame used in the game as part of the gameboard
 *
 * @author Per Nyberg
 * @version 2017.04.15
 * @last_updated 2017.04.17
 */

import javax.swing.JFrame;

public class MoveableFrame extends JFrame {
	// constants used
//---------------------------------------------------
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;

	public static final int movementSpeed = 5;
	public static final int widthAdjustment = 0;
	public static final int heightAdjustment = 22;
//---------------------------------------------------

	private GamePanel board;

	private int x, y;
	private int blockWidth, blockHeight, blockSize;

	/**
	 * Set frame-psition and set default-values (block- and map-size)
	 *
	 * @param x 	the x-coordinate for the window (on the screen)
	 * @param y 	the y-coordinate for the window (on the screen)
	 */
	public MoveableFrame(int x, int y) {
		this.x = x;
		this.y = y;

		blockWidth = 10;
		blockHeight = 10;
		blockSize = 20;

		board = new GamePanel(this, blockWidth, blockHeight, blockSize);

		int width = blockWidth * blockSize + widthAdjustment;
		int height = blockHeight * blockSize + heightAdjustment;

		add(board);

		setLocation(x, y);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(width, height);
		setVisible(true);
	}

	/**
	 * Save the information regarding board-appearance and then tell the board to create the map
	 *
	 * @param map 			int-matrix blueprinting the map
	 * @param blockWidth 	the width of the map counted in blocks
	 * @param blockHeight 	the height of the map counted in blocks
	 * @param blockSize 	the size of the blocks given in pixels
	 */
	public void initMap(int[][] map, int blockWidth, int blockHeight, int blockSize) {
		this.blockWidth = blockWidth;
		this.blockHeight = blockHeight;
		this.blockSize = blockSize;

		board.initMap(map);
	}

	/**
	 * Move the frame in given direction (speed is pre-set)
	 *
	 * @param direction 	the direction given in a number corresponding to a 90-degree turn
	 */
	public void moveFrame(int direction) {
		if (direction == UP) {
			y -= movementSpeed;
		} else if (direction == RIGHT) {
			x += movementSpeed;
		} else if (direction == DOWN) {
			y += movementSpeed;
		} else if (direction == LEFT) {
			x -= movementSpeed;
		}

		setLocation(x, y);
	}

	/**
	 * Forward the the movement-handling to the board for the frame 
	 */
	public void handleMovement() {
		board.handleMovement();
	}

	/**
	 * @param player 	the player to be added to the frame
	 */
	public void addPlayer(Player player) {
		board.addPlayer(player);
	}

	/**
	 * @param player 	the player to be removed from the frame
	 */
	public void removePlayer(Player player) {
		board.removePlayer(player);
	}

	/**
	 * Add frame to be connected to the left
	 *
	 * @param leftFrame 	the frame to be connected with
	 */
	public void addLeftFrame(MoveableFrame leftFrame) {
		board.addLeftFrame(leftFrame);
	}

	/**
	 * Add frame to be connected to the right
	 *
	 * @param rightFrame 	the frame to be connected with
	 */
	public void addRightFrame(MoveableFrame rightFrame) {
		board.addRightFrame(rightFrame);
	}

	/**
	 * Add frame to be connected upwards
	 *
	 * @param upFrame 	the frame to be connected with
	 */
	public void addUpFrame(MoveableFrame upFrame) {
		board.addUpFrame(upFrame);
	}

	/**
	 * Add frame to be connected downwards
	 *
	 * @param downFrame 	the frame to be connected with
	 */
	public void addDownFrame(MoveableFrame downFrame) {
		board.addDownFrame(downFrame);
	}

	/**
	 * @param leftConnect 	whether or not the frame should be connected to the left
	 */
	public void connectLeft(boolean leftConnect) {
		board.connectLeft(leftConnect);
	}

	/**
	 * @param rightConnect 	whether or not the frame should be connected to the right
	 */
	public void connectRight(boolean rightConnect) {
		board.connectRight(rightConnect);
	}

	/**
	 * @param upConnect 	whether or not the frame should be connected upwards
	 */
	public void connectUp(boolean upConnect) {
		board.connectUp(upConnect);
	}

	/**
	 * @param downConnect 	whether or not the frame should be connected downwards
	 */
	public void connectDown(boolean downConnect) {
		board.connectDown(downConnect);
	}

	/**
	 * @return x 	the x-coordinate for the frame
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return y 	the y-coordinate for the frame
	 */
	public int getY() {
		return y;
	}

	/**
	 * Paint the board in the frame
	 */
	public void repaint() {
		board.repaint();
	}
}