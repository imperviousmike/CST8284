package cst8284.lab09;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;

public class SortByStringLength implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		Collections.sort(WordSorter.getListOfStrings(),new StringComparator());
		WordSorter.reloadJTextArea();

	}
	
	static class StringComparator implements Comparator<String>
	 {
	     @Override
			public int compare(String s1, String s2) {
	    	 return s1.length() - s2.length();
	     }
		
	 }

}