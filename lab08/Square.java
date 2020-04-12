package cst8284.lab08;

public class Square extends BasicShape {

	public Square() {
		this(1.0);
	}

	public Square(double width) {
		super(width);
	}

	public Square(Square square) {
		this(square.getWidth());
	}

	@Override
	public double getArea() {
		return Math.pow(getWidth(), 2.0);
	}

	@Override
	public double getPerimeter() {
		return 4.0 * getWidth();
	}

	@Override
	public String toString() {
		return (getClass().getSimpleName());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Square)
			return super.equals(obj) || getWidth() == ((Square) obj).getWidth();

		return false;
	}

}
