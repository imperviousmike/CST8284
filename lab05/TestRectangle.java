package cst8284.lab05;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class TestRectangle {

	@Test
	public void testSquareRectanglePerimeter() {

		Square s = new Square(5.0);
		Rectangle r = new Rectangle(5.0,5.0);


		assertEquals(s.getPerimeter(), r.getPerimeter(), 1e-12);
		
	}

	@Test
	public void testSquareRectangleArea() {

		Square s = new Square();
		Rectangle r = new Rectangle();
		
		assertEquals(s.getArea(), r.getArea(), 1e-12);
	}

}
