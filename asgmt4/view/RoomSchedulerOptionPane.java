package cst8284.asgmt4.view;

import javax.swing.JOptionPane;

/**
 * When creating a dialog this does most of the work to prevent code
 * duplication. It builds a simply JOptionPane and sets the title and message
 * from provided values.
 * 
 * @version 1.0
 * @author Michael Daly
 * 
 */
public class RoomSchedulerOptionPane extends JOptionPane {

	/**
	 * Serialization necessary from parent component
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * stores the result from the option selected by the user
	 */
	private int response;

	/**
	 * Constructor for a more specified {@code JOptionPane} used here. Takes a
	 * header and message to set the what's displayed to the user in the box and
	 * then creates it.
	 * 
	 * @param header  used to set the title of the pop up
	 * 
	 * @param message sets the message shown to the user
	 */
	public RoomSchedulerOptionPane(String header, String message) {
		setResponse(JOptionPane.YES_NO_OPTION);
		JOptionPane.showConfirmDialog(null, message, header, getResponse());
	}

	/**
	 * Getter for the response field. Used when retrieving what option the user
	 * selected from the prompt
	 * 
	 * @return int value of the selection
	 */
	public int getResponse() {
		return response;
	}

	/**
	 * Setter for the response field. Once the user has mad a selection it stores it
	 * for future processing.
	 * 
	 * @param response the value of the option selected to be stored
	 */
	private void setResponse(int response) {
		this.response = response;
	}

}
