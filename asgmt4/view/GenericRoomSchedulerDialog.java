package cst8284.asgmt4.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.Highlighter;

import firstyear.secondsemester.cst8284.asgmt4.roomScheduler.RoomScheduler;
import firstyear.secondsemester.cst8284.asgmt4.utility.RoomSchedulerConstants;

/**
 * This abstracted class does most of the work of setting up fields and labels
 * and formatting the window for all sub windows of the Room Scheduler. It is
 * designed to be used with any windows expected to be launched off the main
 * window with a click of the button.
 * 
 * @version 1.0
 * @author Michael Daly
 * 
 */
public abstract class GenericRoomSchedulerDialog extends JFrame {
	/**
	 * Serialized due to extending JFrame(implements TransferHandler).
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The map of all expected input fields, and there text label(THEY HAVE TO BE
	 * THE SAME SIZE).
	 */
	private static LinkedHashMap<RoomSchedulerConstants, JTextField> inputs;

	/**
	 * This constructor takes the given fields and
	 * {@link cst8284.asgmt4.utility.RoomSchedulerConstants}'s to be used as labels
	 * and aligns them in a panel. It formats the general panel and adds padding
	 * between each panel.
	 * 
	 * @param fields the array of text fields to be put into the window.
	 * @param labels the text to be used as labels for the text fields.
	 */
	protected GenericRoomSchedulerDialog(JTextField[] fields, RoomSchedulerConstants[] labels) {

		JPanel inputs = buildInputPanel(labels, fields);
		inputs.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		JPanel inputPanel = new JPanel(new BorderLayout(5, 10));
		inputPanel.add(inputs, BorderLayout.CENTER);
		inputPanel.setBorder(BorderFactory.createEmptyBorder(1, 0, 1, 0));
		this.add(inputPanel);
		for (JTextField field : fields) {
			addTextFieldListeners(field);
			field.setText("");
			field.setBackground(Color.white);
		}

		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				RoomSchedulerDialog.updateButtons(false);

			}

			@Override
			public void windowClosing(WindowEvent e) {
				windowClosed(e);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				RoomSchedulerDialog.updateButtons(true);
				RoomSchedulerDialog.setDisplayText();
				dispose();

			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}
		});
		pack();

	}

	/**
	 * Adds listeners so all closing operations of future windows are already
	 * handled. It adds listeners for the text fields to fix the fields if they have
	 * highlighting set from a previous session.
	 * 
	 * @param field the text field to apply the listener to.
	 */
	protected void addTextFieldListeners(JTextField field) {
		field.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				Highlighter highlighter = field.getHighlighter();
				highlighter.removeAllHighlights();
				field.setBackground(Color.white);

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

		});

	}

	/**
	 * Builds a {@code JPanel} containing all the fields and labels formatted in
	 * rows
	 * 
	 * @param field  the text field to apply the listener to
	 * @param labels text to be used for the text fields
	 * 
	 * @return the constructed panel containing the formatted parameters
	 */
	private static JPanel buildInputPanel(RoomSchedulerConstants[] labels, JTextField[] fields) {

		if (labels.length != fields.length) {
			return null;
		}

		LinkedHashMap<RoomSchedulerConstants, JTextField> input = new LinkedHashMap<>();
		for (int i = 0; i < labels.length; i++) {
			input.put(labels[i], fields[i]);
		}
		setInputs(input);

		JPanel panel = new JPanel();

		GroupLayout gl = new GroupLayout(panel);

		gl.setAutoCreateContainerGaps(true);
		gl.setAutoCreateGaps(true);

		panel.setLayout(gl);

		/*
		 * This was taken from Title: JPanel & components change position automatically
		 * Author: Andrew Thompson Date:Feb 9 2014 Link:
		 * https://stackoverflow.com/a/21659516
		 */
		/*
		 * Also used as reference
		 * https://docs.oracle.com/javase/tutorial/uiswing/layout/group.html
		 */
		GroupLayout.SequentialGroup horizontal = gl.createSequentialGroup();
		GroupLayout.Group labelGroup = gl.createParallelGroup(GroupLayout.Alignment.TRAILING);
		horizontal.addGroup(labelGroup);
		GroupLayout.Group fieldGroup = gl.createParallelGroup();
		horizontal.addGroup(fieldGroup);
		gl.setHorizontalGroup(horizontal);
		GroupLayout.SequentialGroup vertical = gl.createSequentialGroup();
		gl.setVerticalGroup(vertical);

		int size = GroupLayout.PREFERRED_SIZE;
		for (Map.Entry<RoomSchedulerConstants, JTextField> entry : getInputs().entrySet()) {
			JLabel label = new JLabel(entry.getKey().toString());
			JTextField field = entry.getValue();
			labelGroup.addComponent(label);
			fieldGroup.addComponent(field, size, size, size);
			vertical.addGroup(gl.createParallelGroup().addComponent(label).addComponent(field, size, size, size));
		}

		return panel;
	}

	/**
	 * This is to encourage any child objects to implement the buttons to keep a
	 * consistent design standard.
	 */
	protected abstract JPanel buildButtonPanel();

	/**
	 * This is to encourage any child objects to implement proper exit handling,
	 * there is the generic closing but in some cases that might not be enough this
	 * is the reminder
	 */
	protected abstract void exitWindow();

	/**
	 * Build the inputs needed to process the input
	 */
	public abstract void populateInputs();

	/**
	 * Enforce child windows to clear input on every new opening
	 */
	public abstract void clearInputs();

	/**
	 * Getter for the map of all labels and input fields
	 * 
	 * @return returns the list
	 */
	public static LinkedHashMap<RoomSchedulerConstants, JTextField> getInputs() {
		return inputs;
	}

	/**
	 * Setter for the map of all labels and input fields
	 * 
	 * @param a reference to the new list to store
	 */
	protected static void setInputs(LinkedHashMap<RoomSchedulerConstants, JTextField> inputs) {
		GenericRoomSchedulerDialog.inputs = inputs;
	}

	/**
	 * Set the title to match specifications
	 */
	protected void updateTitle(RoomScheduler rs) {
		setTitle(rs.getRoom().toString());
	}

}
