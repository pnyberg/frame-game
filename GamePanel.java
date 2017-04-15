/**
 * The panel used by every frame to paint the gameboard
 *
 * @author Per Nyberg
 * @version 2017.04.11
 * @last_updated 2017.04.15
 */

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	// constants used
//---------------------------------------------------
	private static final int connectorSize = 2; // same size for all panels
//---------------------------------------------------

	private ArrayList<Player> players;

	private int blockWidth, blockHeight, blockSize;
	private int[][] map;

	private MoveableFrame thisFrame, leftFrame, rightFrame, upFrame, downFrame;
	private boolean leftConnect, rightConnect, upConnect, downConnect;

	/**
	 * Set belonging frame and size of board (in dimension and block-size)
	 *
	 * @param thisFrame 	the frame the board belongs to
	 * @param blockWidth 	the width in blocks of the board
	 * @param blockHeight 	the height in blocks of the board
	 * @param blockSize 	the size of a block in pixels
	 */
	public GamePanel(MoveableFrame thisFrame, int blockWidth, int blockHeight, int blockSize) {
		this.thisFrame = thisFrame;
		this.blockWidth = blockWidth;
		this.blockHeight = blockHeight;
		this.blockSize = blockSize;

		map = new int[blockHeight][blockWidth];

		players = new ArrayList<Player>();
	}

	/**
	 * Import a int-matrix as a map
	 *
	 * @param map 	a int-matrix representing the map
	 */
	public void initMap(int[][] map) {
		this.map = map;
	}

	/**
	 * Handle movement for every player.
	 * If a player is moved to another window this is shown in the changed-variable.
	 */
	public void handleMovement() {
		for (int i = 0 ; i < players.size() ; i++) {
			Player p = players.get(i);
			boolean changed = movePlayer(p);

			if (changed) {
				i--;
			}
		}
	}

	/**
	 * Handle movement for the player, like gravity for instance
	 * @todo
	 * - add collision-bounds for alla directions (not only down)
	 * - fix so that gravity accelerates/deaccelerate upward movement
	 * 
	 * @param p		the player which is handled
	 *
	 * @return playerRemoved 	if the player is moved to another frame or not
	 */
	private boolean movePlayer(Player p) {
		int playerSpeed = blockSize / 4; // temporary speed
		boolean gravity = true; // temporary gravity
		boolean playerRemoved = false;

		// handle up-down-movement
		if (p.getUpMovement()) {
			gravity = false;

			if (p.getY() > 0) {
				p.move(0, -playerSpeed);
			}
		} else if (p.getDownMovement()) {
			p.move(0, playerSpeed);
		}

		// handle left-right-movement
		if (p.getLeftMovement()) {
			p.move(-playerSpeed, 0);
		} else if (p.getRightMovement()) {
			p.move(playerSpeed, 0);
		}

		// if the player is att the bottom of the screen then gravity is deactivated
		if (p.getY() == (blockHeight-1) * blockSize) {
			gravity = false;
		}

		// go through the map and check collision with all objects
		for (int y = 0 ; y < map.length ; y++) {
			if (!gravity) {
				break;
			}

			for (int x = 0 ; x < map[y].length ; x++) {
				if (map[y][x] == 1 && p.getY() <= (y * blockSize) && (p.getY() + Player.size) >= (y * blockSize) && 
					(((x * blockSize) <= p.getX() && p.getX() < ((x+1) * blockSize)) || 
					 ((x * blockSize) < (p.getX() + Player.size) && (p.getX() + Player.size) <= ((x+1) * blockSize)))) {
					gravity = false;
					break;
				}
			}
		}

		// gravity should always be activated
		// but instead of full speed "accelerating" downwards
		if (gravity) {
			p.move(0, playerSpeed);
		}

		// handles movement of the player between frames
		if (downConnect && p.getY() == (blockHeight-1) * blockSize) {
			p.setPosition(p.getX(), 0);
			downFrame.addPlayer(p);
			thisFrame.removePlayer(p);

			playerRemoved = true;
		} else if (upConnect && p.getY() == 0) {
			p.setPosition(p.getX(), (blockHeight-1) * blockSize);
			upFrame.addPlayer(p);
			thisFrame.removePlayer(p);

			playerRemoved = true;
		} else if (leftConnect && p.getX() == 0) {
			p.setPosition((blockWidth-1) * blockSize, p.getY());
			leftFrame.addPlayer(p);
			thisFrame.removePlayer(p);

			playerRemoved = true;
		} else if (rightConnect && p.getX() == (blockWidth-1) * blockSize) {
			p.setPosition(0, p.getY());
			rightFrame.addPlayer(p);
			thisFrame.removePlayer(p);

			playerRemoved = true;
		}

		return playerRemoved;
	}

	/**
	 * Add a player to the board of the frame
	 *
	 * @param player 	the player to be added
	 */
	public void addPlayer(Player player) {
		players.add(player);
	}

	/**
	 * Remove a player from the board of the frame
	 *
	 * @param player 	the player to be removed
	 */
	public void removePlayer(Player player) {
		players.remove(player);
	}

	/**
	 * If the current frame is connected to another frame on it's left side
	 *
	 * @param leftConnect 	if the frame are connected or not
	 */
	public void connectLeft(boolean leftConnect) {
		this.leftConnect = leftConnect;
	}

	/**
	 * If the current frame is connected to another frame on it's right side
	 *
	 * @param rightConnect 	if the frame are connected or not
	 */
	public void connectRight(boolean rightConnect) {
		this.rightConnect = rightConnect;
	}

	/**
	 * If the current frame is connected to another frame on it's up side
	 *
	 * @param upConnect 	if the frame are connected or not
	 */
	public void connectUp(boolean upConnect) {
		this.upConnect = upConnect;
	}

	/**
	 * If the current frame is connected to another frame on it's down side
	 *
	 * @param downConnect 	if the frame are connected or not
	 */
	public void connectDown(boolean downConnect) {
		this.downConnect = downConnect;
	}

	/**
	 * Add frame to be connected to the left
	 *
	 * @param leftFrame 	the frame to be connected with
	 */
	public void addLeftFrame(MoveableFrame leftFrame) {
		this.leftFrame = leftFrame;
	}

	/**
	 * Add frame to be connected to the right
	 *
	 * @param rightFrame 	the frame to be connected with
	 */
	public void addRightFrame(MoveableFrame rightFrame) {
		this.rightFrame = rightFrame;
	}

	/**
	 * Add frame to be connected upwards
	 *
	 * @param upFrame 	the frame to be connected with
	 */
	public void addUpFrame(MoveableFrame upFrame) {
		this.upFrame = upFrame;
	}

	/**
	 * Add frame to be connected downwards
	 *
	 * @param downFrame 	the frame to be connected with
	 */
	public void addDownFrame(MoveableFrame downFrame) {
		this.downFrame = downFrame;
	}

	/**
	 * Paints the board for the frame
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.white);
		g.fillRect(0, 0, blockWidth * blockSize, blockHeight * blockSize);

		// paint viable connections
		g.setColor(Color.blue);
		if (leftConnect) {
			g.fillRect(0, 0, connectorSize, blockHeight * blockSize);
		}
		if (rightConnect) {
			g.fillRect(blockWidth * blockSize - connectorSize, 0, connectorSize, blockHeight * blockSize);
		}
		if (upConnect) {
			g.fillRect(0, 0, blockWidth * blockSize, connectorSize);
		}
		if (downConnect) {
			g.fillRect(0, blockHeight * blockSize - connectorSize, blockWidth * blockSize, connectorSize);
		}

		for (Player p : players) {
			p.paint(g);
		}

		// paint "map"
		g.setColor(Color.black);
		for (int x = 0 ; x < blockWidth ; x++) {
			for (int y = 0 ; y < blockHeight ; y++) {
				if (map[y][x] == 1) {
					g.fillRect(x * blockSize, y * blockSize, blockSize, blockSize);
				}
			}
		}
	}
}