package cst8284.asgmt3.room;

/**
 * {@code Room} is the abstract root class of all possible Room types. Every
 * Room shares {@code Room} as the super class and its properties and methods.
 * 
 * @version 1.0
 * @author Michael Daly
 * 
 */

public abstract class Room {

	/**
	 * Used when setting the {@code roomNumber} when one is not provided during
	 * {@code Room} construction.
	 */
	private static final String DEFAULT_ROOM_NUMBER = "unknown room number";
	/**
	 * Room number of this specifc room.
	 */ 	
	private String roomNumber;

	/**
	 * Constructs a {@code Room} object. Chains to the parameterized constructing
	 * using the {@code DEFAULT_ROOM_NUMBER} to set the room number.
	 * 
	 */
	protected Room() {
		this(DEFAULT_ROOM_NUMBER);
	}

	/**
	 * Constructs a {@link cst8284.asgmt3.room.Room} object. Takes the given
	 * {@code roomNum} to set the {@code roomNumber}
	 * {@link java.lang.String#String}.
	 * 
	 * @param roomNum the initial value for the {@code roomNumber}.
	 */
	protected Room(String roomNum) {
		setRoomNumber(roomNum);
	}

	/**
	 * Takes the given {@code String} {@code roomNum} and sets {@code roomNumber} to
	 * the value.
	 * 
	 * @param roomNum the new value for the {@code roomNumber}.
	 */
	public void setRoomNumber(String roomNum) {
		roomNumber = roomNum;
	}

	/**
	 * Returns the current value set for the {@code roomNumber} property.
	 * 
	 * @return {@code String} with the current value stored for {@code roomNumber}.
	 */
	public String getRoomNumber() {
		return roomNumber;
	}

	/**
	 * The abstract declaration of the {@code getRoomType()} method to be
	 * implemented by any subclass of {@link cst8284.asgmt3.room.Room}. It will
	 * return the type of Room that was created by the implementer.
	 * 
	 * @return {@code String} with the current type of room specified in the
	 *         subclass.
	 */
	protected abstract String getRoomType();

	/**
	 * The abstract declaration of the
	 * {@code getSeats()) method to be implemented by any subclass of {@link
	 * cst8284.asgmt3.room.Room}. It will return the number of seats of the Room
	 * that was created by the implementer.
	 * 
	 * @return {@code int} with the current type of room specified in the subclass.
	 */
	protected abstract int getSeats();

	/**
	 * The abstract declaration of the {@code getDetails()} method to be implemented
	 * by any subclass of {@link cst8284.asgmt3.room.Room}. It will return the extra
	 * details that are set based on the specific subclass used by the implementer.
	 * 
	 * @return {@code String} with the extra details specific to that subclass of
	 *         {@link cst8284.asgmt3.room.Room}.
	 */
	protected abstract String getDetails();

	/**
	 * Returns a {@code String} object representing the set details for the current
	 * {@link cst8284.asgmt3.room.Room} object.
	 * 
	 * @return a {@code String} object containing the details in the {@code Room}
	 *         object.
	 */
	@Override
	public String toString() {
		return getRoomNumber() + " is a " + getRoomType() + " with " + getSeats() + " seats; " + getDetails() + "\n";
	}
}
