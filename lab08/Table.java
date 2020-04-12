package cst8284.lab08;

import java.util.List;

public class Table {

	public static <E extends Comparable<E>> void outputTable(List<E> arList) {

		// Print out column header
		System.out.print("\t");
		for (E columnHeader : arList)
			System.out.format("\t%8s", columnHeader);

		// Print out each row,starting with the name of the object
		for (E solidObjRow : arList) {
			System.out.println(); // Next line
			System.out.format("%-8s", solidObjRow);
			for (E solidObjColumn : arList)
				System.out.format("\t%8s", (compareResults(solidObjRow, solidObjColumn)));
		}
		System.out.println("\n");
	}

	public static <E extends Comparable<E>> String compareResults(E one, E two) {
		String result = (one.compareTo(two) > 0) ? "<" : ">";
		return (one.compareTo(two) == 0) ? "=" : result;

	}

}
