package cst8284.lab04;

public class Complex {
	private double real = 0;
	private double imag = 0;

	// Complex constructor that takes in a single string, e.g. "2-4i"
	public Complex(String cStr) {
		this(cStr.split("(?=\\+)|(?=\\-)")); // splits cStr at + or - into an
		// array of strings having two elements. The first element of the
		// resultant array will be the real portion, while the second is the
		// imaginary portion. This array is passed to the next constructor.
	}

	// Complex constructor that takes in an array of two strings from the above
	// constructor e.g. cStr[0]="2", cStr[1]="-4i"
	public Complex(String[] cStr) {
		this(cStr[0], cStr[1]);
	}

	// Complex constructor that takes two separate strings as parameters, e.g. "2"
	// and "-4i"
	public Complex(String r, String i) {
		this(Integer.parseInt(r), Integer.parseInt(i.replace("i", "")));
	}

	// Complex constructor that takes in two ints as parameters, e.g. 2 and -4
	public Complex(int r, int i) {
		this((double) r, (double) i);
	}

	// Complex constructor that takes in two ints and stores them as floats, e.g. as
	// 2.0 and -4.0
	public Complex(double r, double i) {
		setReal(r);
		setImag(i);
	}

	// default Complex constructor; it will chain automatically to the (int, int)
	// constructor
	public Complex() {
		this(0, 0);
	}

	public double getReal() {
		return this.real;
	}

	public double getImag() {
		return this.imag;
	}

	public Complex getComplex() {
		return this;
	}

	public void setReal(double real) {
		this.real = real;
	}

	public void setImag(double imag) {
		this.imag = imag;
	}

	@Override
	public String toString() {
		return new String(getReal() + new String((getImag() < 0) ? " - " : " + ") + getImag() + "i");
	}

	public boolean isZero() {
		return (getReal() == 0 && getImag() == 0);
	}

	public Complex conjugate() {

		return new Complex(getReal(), -getImag());

	}
	
	public boolean equals(Complex c) {
		return equals(c.getReal(),c.getImag(),1e-12);
	}
	
	public boolean equals(int real, int imag) {
		return equals(new Complex(real,imag));
	}
	
	public boolean equals(String c) {
		return equals(new Complex(c));
	}
	
	public boolean equals(double real, double imag, double delta) {
		 return Math.abs(getReal()-real)<delta &&  Math.abs(getImag()-imag)<delta;
	}

}
