package cst8284.lab09;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

public class ReverseListOrder implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		Collections.reverse(WordSorter.getListOfStrings());
		WordSorter.reloadJTextArea();

	}

}
