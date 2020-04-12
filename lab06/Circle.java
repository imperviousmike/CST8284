package cst8284.lab06;

public class Circle extends BasicShape {

	public Circle() {
		this(new Circle(1.0));
	}

	public Circle(double width) {
		super(width);
	}

	public Circle(Circle circle) {
		this(circle.getWidth());
	}

	@Override
	public double getArea() {
		return Math.PI * (Math.pow(getWidth(), 2.0) / 4.0);
	}

	@Override
	public double getPerimeter() {
		return Math.PI * getWidth();
	}

	@Override
	public String toString() {
		return (getClass().getSimpleName() + " extends" + super.toString());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Circle)
			return super.equals(obj) || getWidth() == ((Circle) obj).getWidth();

		return false;
	}

}
