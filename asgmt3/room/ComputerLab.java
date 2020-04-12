package cst8284.asgmt3.room;

/**
 * {@code ComputerLab} is a concrete implementation of the
 * {@link cst8284.asgmt3.room.Room} root class. This object also holds the
 * number of seats and has functionality to provide the rest of the details.
 *
 * @version 1.0
 * @author Michael Daly
 * 
 */

public final class ComputerLab extends Room {

	/**
	 * Holds the value for the base amount for {@code Classroom}'s amount of seats.
	 */
	private static final int DEFAULT_SEATS = 30;
	/**
	 * Number of seats for the given {@link cst8284.asgmt3.room.Room}.
	 */
	private int seats;

	/**
	 * Constructs a {@code ComputerLab} object. Sets the number of seats to its
	 * default amount and uses the super {@link cst8284.asgmt3.room.Room}
	 * constructor to populate the rest of the details.
	 * 
	 */
	public ComputerLab() {
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
		return "computer lab";
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
	 * of {@link cst8284.asgmt3.room.Room}.
	 * 
	 * @return ({@code String} object with the details for this
	 *         {@link cst8284.asgmt3.room.Room}.
	 */
	@Override
	protected String getDetails() {
		return "contains outlets for 30 laptops";
	}

}
