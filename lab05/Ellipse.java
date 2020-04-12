package cst8284.lab05;

public class Ellipse extends Circle {

	private double height;

	public Ellipse() {
		this(1.0, 1.0);
	}

	public Ellipse(double width, double height) {
		super(width);
		setHeight(height);
	}

	public Ellipse(Ellipse ellipse) {
		this(ellipse.getWidth(), ellipse.getHeight());
	}

	@Override
	public double getArea() {
		return (Math.PI / 4.0) * getWidth() * getHeight();
	}

	@Override
	public double getPerimeter() {
		return Math.PI * Math.sqrt((getWidth() * getWidth() + getHeight() * getHeight()) / (2.0));
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return (getClass().getSimpleName() + " extends" + super.toString());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Ellipse)
			return (super.equals(obj) && this.getHeight() == ((Ellipse) obj).getHeight());

		return false;

	}
}
