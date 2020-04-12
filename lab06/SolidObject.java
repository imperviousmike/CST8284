package cst8284.lab06;

public class SolidObject<T extends BasicShape> {

	private T shape;
	private double depth;
	
	public T getShape() {
		return shape;
	}


	public void setShape(T shape) {
		this.shape = shape;
	}


	public double getDepth() {
		return depth;
	}


	public void setDepth(double depth) {
		this.depth = depth;
	}
	
	public SolidObject(T shape, double depth) {
		setShape(shape);
		setDepth(depth);
	}
	
	public SolidObject(T shape) {
		this(shape,shape.getWidth());
	}
	
	public double getVolume() {
		return getShape().getArea() * getDepth();
	}
	
	public double getSurfaceArea() {
		T shape = getShape();
		return shape.getPerimeter() * getDepth() + 2.0 * shape.getArea();
	}
	
	
	// the toString() method is provided for you -- do not remove
	public String toString() {
		String solidTypeEquivalent = "unknown   ";
		switch (getShape().getClass().getSimpleName()) {
		   case "Circle": solidTypeEquivalent = "cylinder "; break;
		   case "Square": solidTypeEquivalent = "block     "; break;
		   case "Triangle": solidTypeEquivalent = "prism    "; break;
		}
		return solidTypeEquivalent;
	}

}
