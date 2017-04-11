import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

public class FrameGame extends JFrame implements KeyListener {
	private static final int gameWidth = 600;
	private static final long updateTime = 100;

	private MoveableFrame topLeftFrame;
	private MoveableFrame topRightFrame;
	private MoveableFrame bottomLeftFrame;
	private MoveableFrame bottomRightFrame;

	private Player player;

	private int centerX, centerY;
	private int[][] movementIndex;

	private int blockWidth, blockHeight, blockSize;
	private int frameWidth, frameHeight;

	public FrameGame() {
		initFrames();
		initMap();
		initPlayers();

		addKeyListener(this);

		setVisible(true);
	}

	private void initFrames() {
		movementIndex = new int[2][2];

		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();

		centerX = width / 2;
		centerY = height / 2;

		blockWidth = 10;
		blockHeight = 10;
		blockSize = 20;

		frameWidth = blockWidth * blockSize + MoveableFrame.widthAdjustment;
		frameHeight = blockHeight * blockSize + MoveableFrame.heightAdjustment;

		topLeftFrame = new MoveableFrame(centerX - frameWidth, centerY - frameHeight);
		topRightFrame = new MoveableFrame(centerX, centerY - frameHeight);
		bottomLeftFrame = new MoveableFrame(centerX - frameWidth, centerY);
		bottomRightFrame = new MoveableFrame(centerX, centerY);
	}

	public void start() {
		while(true) {
			doRound();

			try {
				Thread.sleep(updateTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void initMap() {
		int[][] topLeftMap = {
								{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
								{1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
								{1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{1, 0, 1, 0, 0, 1, 1, 1, 1, 1},
								{1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{1, 1, 0, 0, 1, 1, 0, 0, 1, 1},
							};

		int[][] topRightMap = {
								{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
								{0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
								{0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
								{1, 1, 1, 0, 0, 0, 1, 0, 0, 1},
								{0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
								{0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
								{1, 1, 1, 1, 0, 0, 0, 1, 1, 1},
								{0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
								{0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
								{1, 1, 1, 0, 0, 1, 1, 0, 0, 1},
							};

		int[][] bottomLeftMap = {
									{1, 0, 0, 0, 0, 0, 0, 0, 1, 1},
									{1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
									{1, 0, 0, 1, 1, 0, 0, 0, 0, 0},
									{1, 1, 1, 0, 0, 0, 1, 0, 1, 1},
									{1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
									{1, 0, 0, 0, 0, 1, 0, 0, 0, 0},
									{1, 1, 1, 1, 0, 0, 0, 1, 1, 1},
									{1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
									{1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
									{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
								};

		int[][] bottomRightMap = {
									{1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
									{0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
									{0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
									{1, 1, 1, 0, 0, 0, 1, 0, 0, 1},
									{0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
									{0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
									{1, 1, 1, 1, 0, 0, 0, 1, 1, 1},
									{0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
									{0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
									{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
								};

		topLeftFrame.initMap(topLeftMap, blockWidth, blockHeight, blockSize);
		topRightFrame.initMap(topRightMap, blockWidth, blockHeight, blockSize);
		bottomLeftFrame.initMap(bottomLeftMap, blockWidth, blockHeight, blockSize);
		bottomRightFrame.initMap(bottomRightMap, blockWidth, blockHeight, blockSize);
	}

	private void initPlayers() {
		Player.size = blockSize;

		player = new Player(3 * blockSize, 3 * blockSize);

		topLeftFrame.addPlayer(player);
	}

	private void doRound() {
		handleFrameMovement();
		checkConnection();
		handlePlayerMovement();

		topLeftFrame.repaint();
		topRightFrame.repaint();
		bottomLeftFrame.repaint();
		bottomRightFrame.repaint();
	}

	private void handleFrameMovement() {
		int UP = MoveableFrame.UP;
		int RIGHT = MoveableFrame.RIGHT;
		int DOWN = MoveableFrame.DOWN;
		int LEFT = MoveableFrame.LEFT;

		// top-left
		if (movementIndex[0][0] == 0) {
			topLeftFrame.moveFrame(LEFT);

			if (topLeftFrame.getX() <= (centerX - gameWidth/2)) {
				movementIndex[0][0] = 1;
			}
		} else if (movementIndex[0][0] == 1) {
			topLeftFrame.moveFrame(UP);

			if (topLeftFrame.getY() <= (centerY - gameWidth/2)) {
				movementIndex[0][0] = 2;
			}
		} else if (movementIndex[0][0] == 2) {
			topLeftFrame.moveFrame(RIGHT);

			if ((centerX - frameWidth) <= topLeftFrame.getX()) {
				movementIndex[0][0] = 3;
			}
		} else if (movementIndex[0][0] == 3) {
			topLeftFrame.moveFrame(DOWN);

			if ((centerY - frameHeight) <= topLeftFrame.getY()) {
				movementIndex[0][0] = 0;
			}
		}

		// bottom-left
		if (movementIndex[0][1] == 0) {
			bottomLeftFrame.moveFrame(LEFT);

			if (bottomLeftFrame.getX() <= (centerX - gameWidth/2)) {
				movementIndex[0][1] = 1;
			}
		} else if (movementIndex[0][1] == 1) {
			bottomLeftFrame.moveFrame(DOWN);

			if ((centerY + gameWidth/2 - frameHeight) <= bottomLeftFrame.getY()) {
				movementIndex[0][1] = 2;
			}
		} else if (movementIndex[0][1] == 2) {
			bottomLeftFrame.moveFrame(RIGHT);

			if ((centerX - frameWidth) <= bottomLeftFrame.getX()) {
				movementIndex[0][1] = 3;
			}
		} else if (movementIndex[0][1] == 3) {
			bottomLeftFrame.moveFrame(UP);

			if (bottomLeftFrame.getY() <= centerY) {
				movementIndex[0][1] = 0;
			}
		}

		// top-right
		if (movementIndex[1][0] == 0) {
			topRightFrame.moveFrame(RIGHT);

			if ((centerX + gameWidth/2 - frameWidth) <= topRightFrame.getX()) {
				movementIndex[1][0] = 1;
			}
		} else if (movementIndex[1][0] == 1) {
			topRightFrame.moveFrame(UP);

			if (topRightFrame.getY() <= (centerY - gameWidth/2)) {
				movementIndex[1][0] = 2;
			}
		} else if (movementIndex[1][0] == 2) {
			topRightFrame.moveFrame(LEFT);

			if (topRightFrame.getX() <= centerX) {
				movementIndex[1][0] = 3;
			}
		} else if (movementIndex[1][0] == 3) {
			topRightFrame.moveFrame(DOWN);

			if ((centerY - frameHeight) <= topRightFrame.getY()) {
				movementIndex[1][0] = 0;
			}
		}

		// bottom-right
		if (movementIndex[1][1] == 0) {
			bottomRightFrame.moveFrame(RIGHT);

			if ((centerX + gameWidth/2 - frameWidth) <= bottomRightFrame.getX()) {
				movementIndex[1][1] = 1;
			}
		} else if (movementIndex[1][1] == 1) {
			bottomRightFrame.moveFrame(DOWN);

			if ((centerY + gameWidth/2 - frameHeight) <= bottomRightFrame.getY()) {
				movementIndex[1][1] = 2;
			}
		} else if (movementIndex[1][1] == 2) {
			bottomRightFrame.moveFrame(LEFT);

			if (bottomRightFrame.getX() <= centerX) {
				movementIndex[1][1] = 3;
			}
		} else if (movementIndex[1][1] == 3) {
			bottomRightFrame.moveFrame(UP);

			if (bottomRightFrame.getY() <= centerY) {
				movementIndex[1][1] = 0;
			}
		}
	}

	private void checkConnection() {
		// topLeft-topRight
		if ((topLeftFrame.getX()+frameWidth) >= topRightFrame.getX()) {
			topLeftFrame.connectRight(true);
			topRightFrame.connectLeft(true);

			topRightFrame.addLeftFrame(topLeftFrame);
			topLeftFrame.addRightFrame(topRightFrame);
		} else {
			topLeftFrame.connectRight(false);
			topRightFrame.connectLeft(false);
		}

		// topLeft-bottomLeft
		if ((topLeftFrame.getY()+frameHeight) >= bottomLeftFrame.getY()) {
			topLeftFrame.connectDown(true);
			bottomLeftFrame.connectUp(true);

			bottomLeftFrame.addUpFrame(topLeftFrame);
			topLeftFrame.addDownFrame(bottomLeftFrame);
		} else {
			topLeftFrame.connectDown(false);
			bottomLeftFrame.connectUp(false);
		}

		// bottomLeft-bottomRight
		if ((bottomLeftFrame.getX()+frameWidth) >= bottomRightFrame.getX()) {
			bottomLeftFrame.connectRight(true);
			bottomRightFrame.connectLeft(true);

			bottomRightFrame.addLeftFrame(bottomLeftFrame);
			bottomLeftFrame.addRightFrame(bottomRightFrame);
		} else {
			bottomLeftFrame.connectRight(false);
			bottomRightFrame.connectLeft(false);
		}

		// topLeft-bottomLeft
		if ((topRightFrame.getY()+frameHeight) >= bottomRightFrame.getY()) {
			topRightFrame.connectDown(true);
			bottomRightFrame.connectUp(true);

			bottomRightFrame.addUpFrame(topRightFrame);
			topRightFrame.addDownFrame(bottomRightFrame);
		} else {
			topRightFrame.connectDown(false);
			bottomRightFrame.connectUp(false);
		}
	}

	private void handlePlayerMovement() {
		topLeftFrame.handleMovement();
		topRightFrame.handleMovement();
		bottomLeftFrame.handleMovement();
		bottomRightFrame.handleMovement();
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			player.moveUp(true);
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			player.moveUp(false);
		}
	}

	public void keyTyped(KeyEvent e) {

	}

	public static void main(String[] args) {
		FrameGame game = new FrameGame();

		game.start();
	}
}