package cst8284.lab05;

public class Triangle extends BasicShape {

	public Triangle() {
		this(1.0);
	}

	public Triangle(double width) {
		super(width);
	}

	public Triangle(Triangle triangle) {
		this(triangle.getWidth());
	}

	@Override
	public double getArea() {
		return (Math.sqrt(3.0) / 4.0) * (Math.pow(getWidth(), 2.0));
	}

	@Override
	public double getPerimeter() {
		return 3.0 * getWidth();
	}

	@Override
	public String toString() {
		return (getClass().getSimpleName() + " extends" + super.toString());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Triangle)
			return super.equals(obj) || getWidth() == ((Triangle) obj).getWidth();

		return false;

	}

}
