import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private final int connectorSize = 2;

	private ArrayList<Player> players;

	private int blockWidth, blockHeight, blockSize;
	private int[][] map;

	private MoveableFrame thisFrame, leftFrame, rightFrame, upFrame, downFrame;
	private boolean leftConnect, rightConnect, upConnect, downConnect;

	public GamePanel(MoveableFrame thisFrame, int blockWidth, int blockHeight, int blockSize) {
		this.thisFrame = thisFrame;
		this.blockWidth = blockWidth;
		this.blockHeight = blockHeight;
		this.blockSize = blockSize;

		map = new int[blockHeight][blockWidth];

		players = new ArrayList<Player>();
	}

	public void initMap(int[][] map) {
		this.map = map;
	}

	public void handleMovement() {
		for (int i = 0 ; i < players.size() ; i++) {
			Player p = players.get(i);
			boolean changed = movePlayer(p);

			if (changed) {
				i--;
			}
		}
	}

	private boolean movePlayer(Player p) {
		int playerSpeed = blockSize / 4;
		boolean gravity = true;
		boolean playerRemoved = false;

		if (p.getUpMovement()) {
			gravity = false;

			if (p.getY() > 0) {
				p.move(0, -playerSpeed);
			}
		}

		if (p.getY() == (blockHeight-1) * blockSize) {
			gravity = false;
		}

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

		if (gravity) {
			p.move(0, playerSpeed);
		}

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
		}

		return playerRemoved;
	}

	public void addPlayer(Player player) {
		players.add(player);
	}

	public void removePlayer(Player player) {
		players.remove(player);
	}

	public void connectLeft(boolean leftConnect) {
		this.leftConnect = leftConnect;
	}

	public void connectRight(boolean rightConnect) {
		this.rightConnect = rightConnect;
	}

	public void connectUp(boolean upConnect) {
		this.upConnect = upConnect;
	}

	public void connectDown(boolean downConnect) {
		this.downConnect = downConnect;
	}

	public void addLeftFrame(MoveableFrame leftFrame) {
		this.leftFrame = leftFrame;
	}

	public void addRightFrame(MoveableFrame rightFrame) {
		this.rightFrame = rightFrame;
	}

	public void addUpFrame(MoveableFrame upFrame) {
		this.upFrame = upFrame;
	}

	public void addDownFrame(MoveableFrame downFrame) {
		this.downFrame = downFrame;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.white);
		g.fillRect(0, 0, blockWidth * blockSize, blockHeight * blockSize);

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