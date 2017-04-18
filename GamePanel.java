/**
 * The panel used by every frame to paint the gameboard
 *
 * @todo
 * - simplify movement-code in movePlayer()-method
 * - investigate jump-bug
 * - possibly investigate control-consistency (smooth movement?)
 *
 * @author Per Nyberg
 * @version 2017.04.11
 * @last_updated 2017.04.18
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
	 * @todo Obviously ugly code but I got pissed off so..
	 * 
	 * @param player			the player which is handled
	 *
	 * @return playerRemoved 	if the player is moved to another frame or not
	 */
	private boolean movePlayer(Player player) {
		int px = player.getX();
		int py = player.getY();
		int playerSize = Player.size;

		int xSpeed = player.getHorisontalSpeed();
		int ySpeed = player.getVerticalSpeed();

		boolean gravity = true;

		if (xSpeed == 0 && ySpeed == 0) {
			int oy = (py + playerSize) / blockSize;

			if (oy < blockHeight) {
				for (int ox = px/blockSize ; ox <= (px + playerSize - 1)/blockSize ; ox++) {
					if (map[oy][ox] == 1) {
						gravity = false;
						break;
					}
				}
			} else {
				gravity = false;
			}
		} else {
			boolean walled = false;

			if (xSpeed < 0) { // left
				if ((px - Math.abs(xSpeed)) < 0) { // left-wall
					player.setPosition(0, py);
					walled = true;
				}

				for (int ox = px/blockSize ; ox >= (px - Math.abs(xSpeed) - 1)/blockSize ; ox--) {
					if (ox < 0) {
						break;
					}
					if (walled) {
						break;
					}
					for (int oy = py/blockSize ; oy <= (py + playerSize - 1)/blockSize ; oy++) {
						if (oy >= blockHeight) {
							break;
						}
						if (map[oy][ox] == 1) {
							player.setPosition((ox+1) * blockSize, py);
							walled = true;
							break;
						}
					}
				}

				int oy = (py + playerSize) / blockSize;

				for (int ox = px/blockSize ; ox <= (px + playerSize - 1)/blockSize ; ox++) {
					if (ox >= blockWidth || oy >= blockHeight) {
						break;
					}
					if (map[oy][ox] == 1) {
						gravity = false;
						break;
					}
				}
			} else if (xSpeed > 0) { // right
				if ((px + playerSize + xSpeed) >= (blockWidth * blockSize)) { // right-wall
					player.setPosition((blockWidth-1) * blockSize, py);
					walled = true;
				}

				int px_end = px + playerSize;
				for (int ox = px_end/blockSize ; ox <= (px_end + xSpeed - 1)/blockSize ; ox++) {
					if (ox >= blockWidth) {
						break;
					}
					if (walled) {
						break;
					}
					for (int oy = py/blockSize ; oy <= (py + playerSize - 1)/blockSize ; oy++) {
						if (oy >= blockHeight) {
							break;
						}
						if (map[oy][ox] == 1) {
							player.setPosition((ox-1) * blockSize, py);
							walled = true;
							break;
						}
					}
				}

				int oy = (py + playerSize) / blockSize;
				if (oy < map.length) {
					for (int ox = px/blockSize ; ox <= (px + playerSize - 1)/blockSize ; ox++) {
						if (ox >= blockWidth) {
							break;
						}
						if (map[oy][ox] == 1) {
							gravity = false;
							break;
						}
					}
				}
			}

			if (!walled) {
				player.move(xSpeed, 0);
			}
			if (ySpeed < 0) { // jumping
				boolean roofed = false;

				if ((py - Math.abs(ySpeed)) < 0) { // roof
					player.setPosition(px, 0);
					if (!upConnect) {
						player.stopVerticalMovement();
					}
					roofed = true;
				}

				for (int oy = py/blockSize ; oy >= (py - Math.abs(ySpeed) + 1)/blockSize ; oy--) {
					if (oy < 0) {
						break;
					}
					if (roofed) {
						break;
					}
					for (int ox = px/blockSize ; ox <= (px + playerSize - 1)/blockSize ; ox++) {
						if (ox >= blockWidth) {
							break;
						}
						if (map[oy][ox] == 1) {
							player.setPosition(px, (oy+1) * blockSize);
							player.stopVerticalMovement();
							roofed = true;
							break;
						}
					}
				}

				if (!roofed) {
					player.move(0, ySpeed);
				}
			} else if (ySpeed > 0) { // gravity
				if ((py + playerSize + ySpeed) >= (blockHeight * blockSize)) { // floor
					player.setPosition(px, (blockHeight - 1) * blockSize);
					if (!downConnect) {
						player.stopVerticalMovement();
					}
					gravity = false;
				}

				int py_end = py + playerSize;
				for (int oy = py_end/blockSize ; oy <= (py_end + ySpeed - 1)/blockSize ; oy++) {
					if (oy >= blockHeight) {
						break;
					}
					if (!gravity) {
						break;
					}
					for (int ox = px/blockSize ; ox <= (px + playerSize - 1)/blockSize ; ox++) {
						if (ox >= blockWidth) {
							break;
						}
						if (map[oy][ox] == 1) {
							player.setPosition(px, (oy-1) * blockSize);
							gravity = false;
							player.stopVerticalMovement();
							break;
						}
					}
				}

				if (gravity) {
					player.move(0, ySpeed);
				}
			}
		}
		if (gravity) {
			player.gravitize();
		}

		boolean playerRemoved = handleFrameTransition(player);
		return playerRemoved;
	}

	/**
	 * Handles movement of the player between frames
	 *
	 * @param player 	the player who's possibly jumping between frames
	 *
	 * @return 			if the player is moved to another frame
	 */
	private boolean handleFrameTransition(Player player) {
		int transAdd = 2; // extra movement when going through "portal"

		if (downConnect && player.getY() == (blockHeight-1) * blockSize) {
			player.setPosition(player.getX(), 0+transAdd);
			downFrame.addPlayer(player);
			thisFrame.removePlayer(player);

			return true;
		} else if (upConnect && player.getY() == 0) {
			player.setPosition(player.getX(), (blockHeight-1) * blockSize - transAdd);
			upFrame.addPlayer(player);
			thisFrame.removePlayer(player);

			return true;
		} else if (leftConnect && player.getX() == 0) {
			player.setPosition((blockWidth-1) * blockSize - transAdd, player.getY());
			leftFrame.addPlayer(player);
			thisFrame.removePlayer(player);

			return true;
		} else if (rightConnect && player.getX() == (blockWidth-1) * blockSize) {
			player.setPosition(0+transAdd, player.getY());
			rightFrame.addPlayer(player);
			thisFrame.removePlayer(player);

			return true;
		}

		return false;
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