package cst8284.lab06;

import java.util.ArrayList;
import java.util.Collections;

public class SolidObjectLauncher {

	private static ArrayList<SolidObject<BasicShape>> solidObjects = new ArrayList<>();

	public static void main(String[] args) {

		Collections.addAll(solidObjects, new SolidObject<>(new Circle(3.0), 39.2436654315),
				new SolidObject<>(new Circle(3.0), 39.2436654315), 
				new SolidObject<>(new Square(16.0), 2.0),
				new SolidObject<>(new Triangle(6.0), 4.5), new SolidObject<>(new Square(8)));

		displayVolumeComparison(solidObjects);
		displaySurfaceAreaComparison(solidObjects);
	}

	public static boolean isVolumeEqual(SolidObject<BasicShape> first, SolidObject<BasicShape> second) {
		return Math.abs(first.getVolume() - second.getVolume()) < 1e-8;
	}

	public static boolean isSurfaceAreaEqual(SolidObject<BasicShape> first, SolidObject<BasicShape> second) {
		return Math.abs(first.getSurfaceArea() - second.getSurfaceArea()) < 1e-8;
	}

	public static void displayVolumeComparison(ArrayList<SolidObject<BasicShape>> arList) {

		// Print out column header
		System.out.println("Compare the objects' volumes.  Are they equal?");
		System.out.print("\t\t");
		for (SolidObject<BasicShape> columnHeader : arList)
			System.out.print("\t" + columnHeader);

		// Print out each row,starting with the name of the object
		for (SolidObject<BasicShape> solidObjRow : arList) {
			System.out.println(); // Next line
			System.out.print(solidObjRow);
			for (SolidObject<BasicShape> solidObjColumn : arList)
				System.out.print("\t\t" + (isVolumeEqual(solidObjColumn, solidObjRow) ? "TRUE" : false));
		}
		System.out.println("\n");
	}

	public static void displaySurfaceAreaComparison(ArrayList<SolidObject<BasicShape>> arList) {

		System.out.println("Compare the objects' surface areas.  Are they equal?");
		// Print out the header
		System.out.print("\t\t");
		for (SolidObject<BasicShape> columnHeader : arList)
			System.out.print("\t" + columnHeader);

		// Print out each row,starting with the name of the object
		for (SolidObject<BasicShape> solidObjRow : arList) {
			System.out.println(); // Next line
			System.out.print(solidObjRow);
			for (SolidObject<BasicShape> solidObjColumn : arList)
				System.out.print("\t\t" + (isSurfaceAreaEqual(solidObjColumn, solidObjRow) ? "TRUE" : false));
		}
		System.out.println("\n");
	}
}
