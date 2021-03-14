import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JFrame;

import org.junit.Test;

public class TetrisUnitTest {
	private Piece piece;
	private Game game;

	@Test
	public void testCheckRows() {
		// Create a grid with a full row at the bottom
		// and two squares above the full bottom row
		Grid g = new Grid();
		// full bottom row
		for (int col = 0; col < Grid.WIDTH; col++) {
			g.set(Grid.HEIGHT - 1, col, Color.GREEN);
		}
		// add two squares above the bottom row
		g.set(Grid.HEIGHT - 2, 3, Color.RED);
		g.set(Grid.HEIGHT - 3, 3, Color.RED);
		// remove the full row
		g.checkRows();
		// check that the grid has been updated correctly
		for (int row = 0; row < Grid.HEIGHT; row++) {
			for (int col = 0; col < Grid.WIDTH; col++) {
				// check the square at (row,col)
				// must have: (18,3) and (19,3) set
				// and all of the others not set
				if ((row == 18 || row == 19) && col == 3) {
					assertTrue(g.isSet(row, col));

				} else {
					assertFalse(g.isSet(row, col));
				}
			}
		}
	}
	
	@Test
	public void testCheckMovingRight() {
		// test if moving right works correctly 
		// also test if it moves correctly when there're other pieces besides
		
		// Create a full column at 7th column
		Grid g = new Grid();
		for (int row = 0 ; row < Grid.HEIGHT ; row++) {
			g.set(row, 7, Color.RED);
		}
		
		// create a LShape piece in the middle of the top of grid 
		// move it to the right for two times
		// it should be moved but stopped by the full column on its right side
		// the previous locations of LShape piece: (0,4) (1,4) (2,4) (2,5)
		// check if the four squares locate at (0,5) (1,5) (2,5) (2,6) after moving
		piece = new LShape(1, Grid.WIDTH / 2 - 1, g);
		for (int count = 0; count < 2; count++) {
				piece.move(Direction.RIGHT);
			}
		
		Point[] p = piece.getLocations();
		Color c = piece.getColor();
		for (int i = 0; i < p.length; i++) {
			g.set((int) p[i].getX(), (int) p[i].getY(), c);
			

		}
		
		assertTrue(g.isSet(0,5) && g.isSet(1,5) && g.isSet(2,5) && g.isSet(2,6)); 
		
		
	}
	
	@Test
	public void testCheckMovingLeft() {
		// test if moving left works correctly 
		// also test if it moves correctly when there're other pieces besides
		
		// Create a full column at 1st column
		Grid g = new Grid();
		for (int row = 0 ; row < Grid.HEIGHT ; row++) {
			g.set(row, 1, Color.RED);
		}
		
		// create a LShape piece in the middle of the top of grid 
		// move it to the left for three times
		// it should be moved but stopped by the full column on its left side
		// the previous locations of LShape piece: (0,4) (1,4) (2,4) (2,5)
		// check if the four squares locate at (0,2) (1,2) (2,2) (2,3) after moving
		piece = new LShape(1, Grid.WIDTH / 2 - 1, g);
		for (int count = 0; count < 2; count++) {
				piece.move(Direction.LEFT);
			}
		
		Point[] p = piece.getLocations();
		Color c = piece.getColor();
		for (int i = 0; i < p.length; i++) {
			g.set((int) p[i].getX(), (int) p[i].getY(), c);
			

		}
		
		assertTrue(g.isSet(0,2) && g.isSet(1,2) && g.isSet(2,2) && g.isSet(2,3)); 
	
	}
	@Test
	public void testCheckDropDown() {
		// test if moving left works correctly 
		// also test if it moves correctly when there're other pieces below
		
		// create a LShape piece in the middle of the top of grid
		// the previous locations of LShape piece: (0,4) (1,4) (2,4) (2,5)
		Grid g = new Grid(); 
		piece = new LShape(1, Grid.WIDTH / 2 - 1, g);
		
		// create a full row at the bottom 
		for (int col = 0; col < Grid.WIDTH; col++) {
			g.set(19, col, Color.RED);
		}
		
		// drop the piece down
		while(piece.canMove(Direction.DOWN)) {
			piece.move(Direction.DOWN);
		}
		
		Point[] p = piece.getLocations();
		Color c = piece.getColor();
		for (int i = 0; i < p.length; i++) {
			g.set((int) p[i].getX(), (int) p[i].getY(), c);
		}
		
		//it should be brought toward the bottom but stopped by the full row below it
		// check if the four squares locate at (16,4) (17,4) (18,4) (18,5) after being dropped down
		assertTrue(g.isSet(16,4) && g.isSet(17,4) && g.isSet(18,4) && g.isSet(18,5)); 

	}
	
	/**
	 * Test rotation for LShape
	 */
	@Test
	public void testLShapeAllowedRotate() {
		// test when the piece is allowed to rotate
		Grid g = new Grid();
		// the previous locations of LShape piece: (0,4) (1,4) (2,4) (2,5)
		piece = new LShape(1, Grid.WIDTH / 2 - 1, g);
		
		// rotate the piece three times
		for (int i = 0; i < 3; i++) {
			piece.rotate();
		}
		
		Point[] p = piece.getLocations();
		Color c = piece.getColor();
		for (int i = 0; i < p.length; i++) {
			g.set((int) p[i].getX(), (int) p[i].getY(), c);
		}
		
		// the piece should be rotate 270 degrees clockwise
		// check if the four squares locate at (0,5) (1,3) (1,4) (1,5)
		assertTrue(g.isSet(0,5) && g.isSet(1,3) && g.isSet(1,4) && g.isSet(1,5)); 

	}
	
	/**
	 * Test rotation for JShape
	 */
	@Test
	public void testJShapeAllowedRotate() {
		// test when the piece is allowed to rotate
		Grid g = new Grid();
		// the previous locations of LShape piece: (0,4) (1,4) (2,4) (2,3)
		piece = new JShape(1, Grid.WIDTH / 2 - 1, g);
		
		// rotate the piece three times
		for (int i = 0; i < 3; i++) {
			piece.rotate();
		}
		
		Point[] p = piece.getLocations();
		Color c = piece.getColor();
		for (int i = 0; i < p.length; i++) {
			g.set((int) p[i].getX(), (int) p[i].getY(), c);
		}
		
		// the piece should be rotate 270 degrees clockwise
		// check if the four squares locate at (1,3) (1,4) (1,5) (2,5)
		assertTrue(g.isSet(1,3) && g.isSet(1,4) && g.isSet(1,5) && g.isSet(2,5)); 

	}
	
	/**
	 * Test rotation for TShape
	 */
	@Test
	public void testTShapeAllowedRotate() {
		// test when the piece is allowed to rotate
		Grid g = new Grid();
		// the previous locations of LShape piece: (1,1) (1,2) (1,3) (2,2)
		piece = new TShape(1, 2, g);
		
		// rotate the piece three times
		for (int i = 0; i < 3; i++) {
			piece.rotate();
		}
		
		Point[] p = piece.getLocations();
		Color c = piece.getColor();
		for (int i = 0; i < p.length; i++) {
			g.set((int) p[i].getX(), (int) p[i].getY(), c);
		}
		
		// the piece should be rotate 270 degrees clockwise
		// check if the four squares locate at (0,2) (1,2) (2,2) (1,3)
		assertTrue(g.isSet(0,2) && g.isSet(1,2) && g.isSet(2,2) && g.isSet(1,3)); 

	}
	
	/**
	 * Test rotation for ZShape
	 */
	@Test
	public void testZShapeAllowedRotate() {
		// test when the piece is allowed to rotate
		Grid g = new Grid();
		// the previous locations of LShape piece: (1,1) (1,2) (2,2) (2,3)
		piece = new ZShape(1, 2, g);
		
		// rotate the piece three times
		for (int i = 0; i < 3; i++) {
			piece.rotate();
		}
		
		Point[] p = piece.getLocations();
		Color c = piece.getColor();
		for (int i = 0; i < p.length; i++) {
			g.set((int) p[i].getX(), (int) p[i].getY(), c);
		}
		
		// the piece should be rotate 270 degrees clockwise
		// check if the four squares locate at (1,2) (2,2) (1,3) (0,3)
		assertTrue(g.isSet(1,2) && g.isSet(2,2) && g.isSet(1,3) && g.isSet(0,3)); 

	}
	
	/**
	 * Test rotation for SShape
	 */
	@Test
	public void testSShapeAllowedRotate() {
		// test when the piece is allowed to rotate
		Grid g = new Grid();
		// the previous locations of LShape piece: (1,1) (1,2) (2,0) (2,1)
		piece = new SShape(1, 1, g);

		// rotate the piece three times
		for (int i = 0; i < 3; i++) {
			piece.rotate();
		}
		
		Point[] p = piece.getLocations();
		Color c = piece.getColor();
		for (int i = 0; i < p.length; i++) {
			g.set((int) p[i].getX(), (int) p[i].getY(), c);
		}
		
		// the piece should be rotate 270 degrees clockwise
		// check if the four squares locate at (0,1) (1,1) (1,2) (2,2)
		assertTrue(g.isSet(0,1) && g.isSet(1,1) && g.isSet(1,2) && g.isSet(2,2)); 

	}
	
	/**
	 * Test rotation for BarShape
	 */
	@Test
	public void testBarShapeAllowedRotate() {
		// test when the piece is allowed to rotate
		Grid g = new Grid();
		// the previous locations of LShape piece: (2,1) (2,2) (2,3) (2,4) 
		piece = new BarShape(2, 2, g);
		
		// rotate the piece three times
		for (int i = 0; i < 3; i++) {
			piece.rotate();
		}
		
		Point[] p = piece.getLocations();
		Color c = piece.getColor();
		for (int i = 0; i < p.length; i++) {
			g.set((int) p[i].getX(), (int) p[i].getY(), c);
		}
		
		// the piece should be rotate 270 degrees clockwise
		// check if the four squares locate at (0,2) (1,2) (2,2) (3,2)
		assertTrue(g.isSet(0,2) && g.isSet(1,2) && g.isSet(2,2) && g.isSet(3,2)); 

	}
	
	/**
	 * Test rotation when any of the squares after rotation is not within he boundaries of the grid 
	 */
	@Test
	public void testNotAllowedRotateOne() {
		// test when the piece doesn't have enough space to rotate
		Grid g = new Grid();
		// the previous locations of BarShape piece: (1,0) (1,1) (1,2) (1,3)
		piece = new BarShape(1, 1, g);
		
		// rotate the piece two times
		for (int i = 0; i < 2; i++) {
			piece.rotate();
		}
		
		Point[] p = piece.getLocations();
		Color c = piece.getColor();
		for (int i = 0; i < p.length; i++) {
			g.set((int) p[i].getX(), (int) p[i].getY(), c);
		}
		
		// the piece should only be successfully rotated once 
		// because after the first rotation, there will be no enough space on its left side
		// check if the four squares locate at (0,1) (1,1) (2,1) (3,1)
		assertTrue(g.isSet(0,1) && g.isSet(1,1) && g.isSet(2,1) && g.isSet(3,1));
	}
	
	/**
	 * Test rotation when the grids that the squares will pass through are not all empty 
	 */
	@Test
	public void testNotAllowedRotateTwo() {
		// test when the piece doesn't have enough space to rotate
		Grid g = new Grid();
		// the previous locations of BarShape piece: (1,0) (1,1) (1,2) (1,3)
		piece = new BarShape(1, 1, g);
		
		// set a square at (3,3)
		g.set(3,3, Color.RED);

		// rotate the piece once 
		piece.rotate();
		
		Point[] p = piece.getLocations();
		Color c = piece.getColor();
		for (int i = 0; i < p.length; i++) {
			g.set((int) p[i].getX(), (int) p[i].getY(), c);
		}
		
		// the piece should not rotate because the path is not empty 
		// check if the four squares locate at (1,0) (1,1) (1,2) (1,3)
		assertTrue(g.isSet(1,0) && g.isSet(1,1) && g.isSet(1,2) && g.isSet(1,3));
	}
	
	/**
	 * Test rotation when the grids that the squares will occupy are not all empty 
	 */
	@Test
	public void testNotAllowedRotateThree() {
		// test when the piece doesn't have enough space to rotate
		Grid g = new Grid();
		// the previous locations of BarShape piece: (1,0) (1,1) (1,2) (1,3)
		piece = new BarShape(1, 1, g);
		
		// set a square at (3,1)
		g.set(3, 1, Color.RED);
		
		// rotate the piece two times
		for (int i = 0; i < 2; i++) {
			piece.rotate();
		}
		
		Point[] p = piece.getLocations();
		Color c = piece.getColor();
		for (int i = 0; i < p.length; i++) {
			g.set((int) p[i].getX(), (int) p[i].getY(), c);
		}

		// the piece should not rotate because the new location after rotation is occupied
		// check if the four squares locate at (1,0) (1,1) (1,2) (1,3)
		assertTrue(g.isSet(1,0) && g.isSet(1,1) && g.isSet(1,2) && g.isSet(1,3));
	}

	
	

	
	
	
}