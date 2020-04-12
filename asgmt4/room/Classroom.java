package cst8284.asgmt4.room;

/**
 * {@code Classroom} is a concrete implementation of the
 * {@link cst8284.asgmt4.room.Room} root class. This object also holds the
 * number of seats and has functionality to provide the rest of the details.
 *
 * @version 1.0
 * @author Michael Daly
 * 
 */

public final class Classroom extends Room {

	/**
	 * Holds the value for the base amount for all {@code Classroom}'s amount of
	 * seats.
	 */
	private static final int DEFAULT_SEATS = 120;
	/**
	 * Number of seats for the given {@link cst8284.asgmt4.room.Room}.
	 */
	private int seats;

	/**
	 * Constructs a {@code Classroom} object. Sets the number of seats to its
	 * default amount and uses the super {@link cst8284.asgmt4.room.Room}
	 * constructor to populate the rest of the details.
	 * 
	 */
	public Classroom() {
		super();
		seats = DEFAULT_SEATS;
	}

	/**
	 * Provides a {@code String} object with the current Room type.
	 * 
	 * @return {@code String} containing the value of this room type.
	 */
	@Override
	protected String getRoomType() {
		return "class room";
	}

	/**
	 * Provides a {@code int} with the value of the seats set by the constructor.
	 * 
	 * @return {@code int} with the amount of seats for this room type.
	 */
	@Override
	protected int getSeats() {
		return seats;
	}

	/**
	 * Provides a {@code String} object with the extra details unique to this type
	 * of {@link cst8284.asgmt4.room.Room}.
	 * 
	 * @return String object with the details for this
	 *         {@link cst8284.asgmt4.room.Room}.
	 */
	@Override
	protected String getDetails() {
		return "contains overhead projector";
	}

}
