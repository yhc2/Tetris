import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public abstract class AbstractPiece implements Piece{
	protected boolean ableToMove; // can this piece move

	protected Square[] square; // the squares that make up this piece

	// Made up of PIECE_COUNT squares
	protected Grid grid; // the board this piece is on

	// number of squares in one Tetris game piece
	protected static final int PIECE_COUNT = 4;

	/**
	 * Creates an L-Shape piece. See class description for actual location of r
	 * and c
	 * 
	 * @param r
	 *            row location for this piece
	 * @param c
	 *            column location for this piece
	 * @param g
	 *            the grid for this game piece
	 * 
	 */

	/**
	 * Draws the piece on the given Graphics context
	 */
	public void draw(Graphics g) {
		for (int i = 0; i < PIECE_COUNT; i++) {
			square[i].draw(g);
		}
	}

	/**
	 * Moves the piece if possible Freeze the piece if it cannot move down
	 * anymore
	 * 
	 * @param direction
	 *            the direction to move
	 */
	public void move(Direction direction) {
		if (canMove(direction)) {
			for (int i = 0; i < PIECE_COUNT; i++)
				square[i].move(direction);
		}
		// if we couldn't move, see if because we're at the bottom
		else if (direction == Direction.DOWN) {
			ableToMove = false;
		}
		
	}

	/**
	 * Returns the (row,col) grid coordinates occupied by this Piece
	 * 
	 * @return an Array of (row,col) Points
	 */
	public Point[] getLocations() {
		Point[] points = new Point[PIECE_COUNT];
		for (int i = 0; i < PIECE_COUNT; i++) {
			points[i] = new Point(square[i].getRow(), square[i].getCol());
		}
		return points;
	}

	/**
	 * Return the color of this piece
	 */
	public Color getColor() {
		// all squares of this piece have the same color
		return square[0].getColor();
	}

	/**
	 * Returns if this piece can move in the given direction
	 * 
	 */
	public boolean canMove(Direction direction) {
		if (!ableToMove)
			return false;

		// Each square must be able to move in that direction
		boolean answer = true;
		for (int i = 0; i < PIECE_COUNT; i++) {
			answer = answer && square[i].canMove(direction);
		}

		return answer;
	}
	public boolean canRotate(){
		int oriRow, oriCol, centralRow, centralCol, newRow, newCol;
			
		for (int i = 0; i < PIECE_COUNT; i++) {
			oriRow = square[i].getRow(); // square moving
			oriCol = square[i].getCol();
			centralRow = square[1].getRow(); // square[1] 
			centralCol = square[1].getCol();
				
	       	newRow = centralRow + (oriCol - centralCol);
	   		newCol = centralCol + (centralRow - oriRow);
	    		
	   		if (!(newRow >= 0 && newRow < Grid.HEIGHT && newCol >= 0 && newCol < Grid.WIDTH) || (grid.isSet(newRow, newCol)) ) {
               return false;
            }
	    		
			// There are four possible positions of piece rotation
	           
	        // Case 1 -> rotate from top to right
            // check from square[i] toward (+x, +y)
            if (oriRow <= centralRow && oriCol <= centralCol) {
            	for (int row = oriRow + 1; row <= newRow; row++) {
            		if(grid.isSet(row, oriCol)) {
            			return false;
	                }
	            }
	            for (int col = oriCol + 1; col <= newCol ; col++) {
	                if(grid.isSet(newRow, col)) {
	                 	return false;
	                }
                }
	        }
	            
	        // Case 2 -> rotate from right to bottom
	        // check from square[i] toward (-x, +y)
	        if (oriRow <= centralRow && oriCol >= centralCol) {
	            for (int row = oriRow + 1; row <= newRow; row++) {
	                if(grid.isSet(row, oriCol)) {
	                	return false;
	                }
	            }
	            for (int col = oriCol - 1; col >= newCol ; col--) {
	                if(grid.isSet(newRow, col)) {
	               	return false;
	                }
	            }
	        }
	            
	        // Case 3 -> rotate from bottom to left
	        // check from square[i] toward (-x, -y)
            if (oriRow >= centralRow && oriCol >= centralCol) {
	            for (int row = oriRow - 1; row >= newRow; row--) {
	                if(grid.isSet(row, oriCol)) {
	                	return false;
	                }
	            }
	            for (int col = oriCol - 1; col >= newCol ; col--) {
	                if(grid.isSet(newRow, col)) {
	                 	return false;
                    }
	            }
	        }
	            
	        // Case 4 -> rotate from left to top
	        // check from square[i] toward (+x, -y)
	        if (oriRow >= centralRow && oriCol <= centralCol) {
	        	for (int row = oriRow - 1; row >= newRow; row--) {
	                if(grid.isSet(row, oriCol)) {
	                  	return false;
	                }
	            }
	            for (int col = oriCol + 1; col <= newCol ; col++) {
	                if(grid.isSet(newRow, col)) {
	                   	return false;
	                }
	            }
	        }
	    } 
		return true;
	}	
	

	/**
	 * Rotate pieces
	 */
	/**
	 * Rotate pieces
	 */
	public void rotate() {
		if (canRotate()) {
			for (int i = 0; i < PIECE_COUNT; i++) {
				square[i].rotate(square[1]);
			}
		}
	}

	
		
}
	
	
	
	
	
	
	
	
	
	
	
	
	

