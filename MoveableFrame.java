import javax.swing.JFrame;

public class MoveableFrame extends JFrame {
	public static int UP = 0;
	public static int RIGHT = 1;
	public static int DOWN = 2;
	public static int LEFT = 3;

	public static final int movementSpeed = 5;
	public static final int widthAdjustment = 0;
	public static final int heightAdjustment = 22;

	private GamePanel board;

	private int x, y;
	private int blockWidth, blockHeight, blockSize;

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

	public void initMap(int[][] map, int blockWidth, int blockHeight, int blockSize) {
		this.blockWidth = blockWidth;
		this.blockHeight = blockHeight;
		this.blockSize = blockSize;

		board.initMap(map);
	}

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

	public void handleMovement() {
		board.handleMovement();
	}

	public void addPlayer(Player player) {
		board.addPlayer(player);
	}

	public void removePlayer(Player player) {
		board.removePlayer(player);
	}

	public void addLeftFrame(MoveableFrame leftFrame) {
		board.addLeftFrame(leftFrame);
	}

	public void addRightFrame(MoveableFrame rightFrame) {
		board.addRightFrame(rightFrame);
	}

	public void addUpFrame(MoveableFrame upFrame) {
		board.addUpFrame(upFrame);
	}

	public void addDownFrame(MoveableFrame downFrame) {
		board.addDownFrame(downFrame);
	}

	public void connectLeft(boolean leftConnect) {
		board.connectLeft(leftConnect);
	}

	public void connectRight(boolean rightConnect) {
		board.connectRight(rightConnect);
	}

	public void connectUp(boolean upConnect) {
		board.connectUp(upConnect);
	}

	public void connectDown(boolean downConnect) {
		board.connectDown(downConnect);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void repaint() {
		board.repaint();
	}
}