package cst8284.lab04;

public class ComplexCalculator {

	private java.util.Scanner op = new java.util.Scanner(System.in);
	private Complex c; // stores result of current calculation

	public ComplexCalculator(Complex c1, Complex c2) {

		System.out.println("Which math operation do you wish to perform?  Enter +, -, *, /");

		switch (op.nextLine().charAt(0)) {
		case '+':
			setComplexResult(add(c1, c2));
			break;
		case '-':
			setComplexResult(subtract(c1, c2));
			break;
		case '*':
			setComplexResult(multiply(c1, c2));
			break;
		case '/':
			setComplexResult(divide(c1, c2));
			break;
		default:
			System.out.println("Unknown operation requested");
		}
	}

	public ComplexCalculator() {
	} // Needed for Lab 4; do not change

	public Complex add(Complex c1, Complex c2) {
		double real = c1.getReal() + c2.getReal(); // As per the Lab Appendix
		double imag = c1.getImag() + c2.getImag();
		return (new Complex(real, imag));
	}

	public Complex subtract(Complex c1, Complex c2) {
		double real = c1.getReal() - c2.getReal(); // As per the Lab Appendix
		double imag = c1.getImag() - c2.getImag();
		return (new Complex(real, imag));

	}

	public Complex multiply(Complex c1, Complex c2) {
		double real = (c1.getReal() * c2.getReal()) - (c1.getImag() * c2.getImag());
		double imag = (c1.getReal() * c2.getImag()) + (c1.getImag() * c2.getReal());
		return new Complex(real, imag);

	}

	/*
	 * public Complex divide(Complex c1, Complex c2) { double xy = if (c1.isZero()
	 * || c2.isZero()) { System.out.println("Divide-by-zero error detected"); return
	 * new Complex(0.0, 0.0); } Math.pow(c2.getReal(), 2.0) + Math.pow(c2.getImag(),
	 * 2.0); double ax = c1.getReal() * c2.getReal(); double by = c1.getImag() *
	 * c2.getImag(); double xb = c2.getReal() * c1.getImag(); double ay =
	 * c1.getReal() * c2.getImag();
	 * 
	 * return new Complex((ax + by) / xy, (xb - ay) / xy); }
	 */

	public Complex divide(Complex c1, Complex c2) {

		if (c1.isZero() || c2.isZero()) {
			System.out.println("Divide-by-zero error detected");
			return new Complex(0.0, 0.0);
		}

		// you can essentially perform a division of c1 by c2 by multiplying c1 by c2*
		c1 = multiply(c1, c2.conjugate());

		// get real component of c2 * c2*
		double real = multiply(c2, c2.conjugate()).getReal();

		// and than diving by the real component of c2 * c2*
		return new Complex(c1.getReal() / real, c1.getImag() / real);

	}

	public void setComplexResult(Complex c) {

		this.c = new Complex(c.getReal(), c.getImag());
	}

	public Complex getComplexResult() {
		return this.c;
	}

	public String toString() {

		return getComplexResult().toString();
	}

}
