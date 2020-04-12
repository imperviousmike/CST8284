package cst8284.lab04;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestComplex {

	// Use assertEquals(double, double, double) to test if getReal() returns the
	// correct value set using the Complex(String, String) constructor
	@Test
	public void testGetRealUsingStringStringConstructor() {
		Complex c = new Complex("3", "6i");
		assertEquals(3.0, c.getReal(), 1e-12);
	}

	// Use assertEquals(double, double, double) to test if setReal() correctly
	// resets the value set using the Complex(String, String) constructor
	@Test
	public void testSetRealUsingStringStringConstructor() {
		Complex c = new Complex("3", "6i");
		c.setReal(4.0);
		assertEquals(4.0, c.getReal(), 1e-12);
	}

	// Use the no-arg Complex() constructor, and then use the real and imaginary
	// setters to set new integer values. Then use assertTrue(boolean) to test the
	// validity of your newly-added equals(int, int) method (1 mark)
	@Test
	public void testSettersWithNoArgConstructor() {
		Complex c = new Complex();
		int real = 5;
		int imag = 10;
		c.setReal(real);
		c.setImag(imag);
		assertTrue(c.equals(real, imag));
	}

}
