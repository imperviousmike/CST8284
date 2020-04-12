package cst8284.asgmt3.roomScheduler;

/**
 * {@code BadRoomBookingException} is the custom exception intended for errors
 * thrown when setting properties of the
 * {@link cst8284.asgmt3.roomScheduler.RoomBooking}, specifically its
 * compositional classes. It extends RuntimeException as these errors will be
 * unchecked by design because they are programming issues and not issues with
 * the language at large.
 * 
 * @version 1.0
 * @author Michael Daly
 * 
 */
public class BadRoomBookingException extends RuntimeException {

	/**
	 * {@code serialVersionUID} is here since we are extending RuntimeException and
	 * in its hierarchy is is derived from {@code Throwable} which implements
	 * {@code Serializable}. We therefore must include a serial UID here.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * A header to give specific relevance as to why the error was thrown when
	 * outputting the information.
	 */
	private String header;

	/**
	 * Constructs a {@code BadRoomBookingException}. It chains to the main
	 * constructor providing default values when an error is thrown but no details
	 * are provided by the code throwing the error.
	 */
	public BadRoomBookingException() {
		this("Bad room booking entered", "Please try again");
	}

	/**
	 * Constructs a {@code BadRoomBookingException}. This is used when the code
	 * throwing the error provides the reasoning and needs to store it. It calls up
	 * to the RuntimeException constructor providing the message needed to be stored
	 * and then keeps the header for the context of the message.
	 * 
	 * @param header  The header message for the context.
	 * 
	 * @param message The actual message of the error.
	 */
	public BadRoomBookingException(String header, String message) {
		super(message);
		setHeader(header);
	}

	/**
	 * Returns the header of this {@code BadRoomBookingException}.
	 * 
	 * @return A reference to the String containing the header stored in this class.
	 */
	public String getHeader() {
		return header;
	}

	/**
	 * Sets the header of this {@code BadRoomBookingException}.
	 * 
	 * @param header Contains a new header for this {@code BadRoomBookingException}.
	 */
	public void setHeader(String header) {
		this.header = header;
	}

}
