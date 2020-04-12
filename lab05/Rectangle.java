package cst8284.lab05;

public class Rectangle extends Square {

	private double height;

	public Rectangle() {
		this(1.0, 1.0);
	}

	public Rectangle(double width, double height) {
		super(width);
		setHeight(height);
	}

	public Rectangle(Rectangle Rectangle) {
		this(Rectangle.getWidth(), Rectangle.getHeight());
	}

	@Override
	public double getArea() {
		return getHeight() * getWidth();
	}

	@Override
	public double getPerimeter() {
		return 2.0 * (getHeight() + getWidth());
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Rectangle)
			return (super.equals(obj) && this.getHeight() == ((Rectangle) obj).getHeight());

		return false;

	}
}
