package cst8284.lab04;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class TestComplexCalculator {

	@Test
	public void testToString() {
		Complex c = new Complex(3, 2);
		assertTrue(c.toString().equals("3.0 + 2.0i"));
	}

	// Use assertNotNull() to test the ComplexCalculator() i.e. no-arg constructor
	@Test
	public void testComplexCalculatorNoArg() {
		assertNotNull(new ComplexCalculator());
	}

	// Using assertTrue(boolean), test that two Complex numbers created using the
	// Complex(double, double) constructor and the Complex(String[]) constructor
	// give the correct result using the multiply() method. Use equals(Complex) to
	// compare the actual and expected result
	@Test
	public void testMultiplyWithComplexDoubleDoubleComplexStingArray() {
		// https://www.wolframalpha.com/input/?i=%287%2B12i%29*%284-4i%29
		assertTrue(new Complex(76.0, 20.0).equals(
				new ComplexCalculator().multiply(new Complex(7.0, 12.0), new Complex(new String[] { "4", "-4" }))));
	}

	// Using one or more for() loops over a range of values, check that (A + Bi)*(A
	// - Bi) is a pure real number, i.e. that imag = 0 for each possible
	// compbination
	@Test
	public void testPureRealNumberUsingMultiply() {
		ComplexCalculator cc = new ComplexCalculator();
		for (int real = 0; real < 100; real++) {
			for (int imag = 100; imag > 0; imag--) {
				if (cc.multiply(new Complex(real, imag), new Complex(real, -imag)).getImag() != 0.0) {
					fail();
				}
			}
			assertTrue(true);
		}
	}
}
