import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

/**
 * Manages the game Tetris. Keeps track of the current piece and the grid.
 * Updates the display whenever the state of the game has changed.
 * 
 * @author CSC 143
 */
public class Game {

	private Grid grid; // the grid that makes up the Tetris board

	private Tetris display; // the visual for the Tetris game

	private Piece piece; // the current piece that is dropping

	private boolean isOver; // has the game finished?

	/**
	 * Creates a Tetris game
	 * 
	 * @param Tetris
	 *            the display
	 */
	public Game(Tetris display) {
		grid = new Grid();
		this.display = display;
		isOver = false;
	}

	/**
	 * Draws the current state of the game
	 * 
	 * @param g
	 *
	 * the Graphics context on which to draw
	 */
	public void draw(Graphics g) {
		grid.draw(g);
		if (piece != null) {
			piece.draw(g);
		}
	}

	/**
	 * Moves the piece in the given direction
	 * 
	 * @param the
	 *            direction to move
	 */
	public void movePiece(Direction direction) {
		if (piece != null) {
			// if the piece is dropped down, move it down as long as it can move down
			if (direction == Direction.DROPDOWN) {
				while(piece.canMove(Direction.DOWN)) {
					piece.move(Direction.DOWN);
				}
			}
			else if (direction == Direction.ROTATE){
				piece.rotate();
			}
			else {
			piece.move(direction);
		}
		}
		updatePiece();
		display.update();
		grid.checkRows();
	
	}

	/**
	 * Returns true if the game is over
	 */
	public boolean isGameOver() {
		// game is over if the piece occupies the same space as some non-empty
		// part of the grid. Usually happens when a new piece is made
		if (piece == null) {
			return false;
		}

		// check if game is already over
		if (isOver) {
			return true;
		}

		// check every part of the piece
		Point[] p = piece.getLocations();
		for (int i = 0; i < p.length; i++) {
			if (grid.isSet((int) p[i].getX(), (int) p[i].getY())) {
				isOver = true;
				return true;
			}
		}
		return false;
	}

	/** Updates the piece */
	private void updatePiece() {
		if (piece == null) {
			grid.checkRows();
			Random r = new Random();
			int randomNumber = r.nextInt(7);
			// CREATE A NEW PIECE HERE
			switch(randomNumber) {
			case 0:
				piece = new LShape(1, Grid.WIDTH/2 - 1, grid);
				break;
			case 1:
				piece = new ZShape(0, Grid.WIDTH/2 - 1, grid);
				break;
			case 2:
				piece = new SquareShape(0, Grid.WIDTH/2 , grid);
				break;
			case 3:
				piece = new JShape(1, Grid.WIDTH/2 , grid);
				break;
			case 4:
				piece = new TShape(0, Grid.WIDTH/2 - 1, grid);
				break;
			case 5:
				piece = new SShape(0, Grid.WIDTH/2 - 1, grid);
				break;
			case 6:
				piece = new BarShape(0, Grid.WIDTH/2 - 1, grid);
				break;

			}
			
			
		}

		// set Grid positions corresponding to frozen piece
		// and then release the piece
		else if (!piece.canMove(Direction.DOWN)) {
			Point[] p = piece.getLocations();
			Color c = piece.getColor();
			for (int i = 0; i < p.length; i++) {
				grid.set((int) p[i].getX(), (int) p[i].getY(), c);
			}
			piece = null;
		}

	}
	
	/**
	 * Rotate pieces
	 */
	
	public void rotatePiece() {
		if (piece != null) {
			piece.rotate();
		}
		updatePiece();
		display.update();
		grid.checkRows();
	}
	

}