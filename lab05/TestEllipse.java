package cst8284.lab05;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestEllipse {

	@Test
	public void testCircleEllipsePerimeter() {

		Circle c = new Circle(5.0);
		Ellipse e = new Ellipse(5.0,5.0);


		assertEquals(c.getPerimeter(), e.getPerimeter(), 1e-12);
		
	}

	@Test
	public void testCircleEllipseArea() {

		Circle c = new Circle();
		Ellipse e = new Ellipse();

		assertEquals(c.getArea(), e.getArea(), 1e-12);
	}
	
	
	@Test
	public void testEllipse() {

		Ellipse e = new Ellipse(5.0,5.0);
		Ellipse c = new Ellipse(3.0,5.0);
		
		assertTrue(e.equals(c));


	
		
	}

}
