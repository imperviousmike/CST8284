package cst8284.asgmt4.roomScheduler;

import java.io.Serializable;

/**
 * {@code Activity} class contains the values for the type of activity for a
 * {@link cst8284.asgmt4.roomScheduler.RoomBooking} that the user has entered.
 * This class implements {@code Serializable} to allow for saving and loading
 * from a file done in the {@link cst8284.asgmt4.roomScheduler.RoomScheduler}.
 * 
 * @version 1.0
 * @author Michael Daly
 * 
 */
public class Activity implements Serializable {

	/**
	 * Description of the activity for the
	 * {@link cst8284.asgmt4.roomScheduler.RoomBooking}.
	 */
	private String description;

	/**
	 * Category of the activity for the
	 * {@link cst8284.asgmt4.roomScheduler.RoomBooking}.
	 */
	private String category;

	/**
	 * {@code serialVersionUID} is used during serialization as a way to version the
	 * object when saving, this is also needed when de-serializing a saved object to
	 * check if its the expected version.
	 */
	public static final long serialVersionUID = 1L;

	/**
	 * Constructs a {@code Activity} object. Sets the {@code category} and
	 * {@code description} based on the parameters given.
	 * 
	 * @param category    The category given for the booking.
	 * @param description The description given for the booking.
	 * 
	 */
	public Activity(String category, String description) {
		setCategory(category);
		setDescription(description);
	}

	/**
	 * Returns the reference to the object set in the {@code description} field.
	 * 
	 * @return A reference to the String value of the set {@code description}.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the reference to the object in the {@code description} field.
	 * 
	 * @param description {@code String} value used to set the field.
	 * 
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the reference to the object set in the {@code category} field.
	 * 
	 * @return A reference to the String value of the set {@code category}.
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Sets the reference to the object in the {@code category} field.
	 * 
	 * @param category {@code String} value used to set the field.
	 * 
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Returns a reference to a formatted {@code String} of the stored values in
	 * this class. Descriptions only show when they are set so if there is no value
	 * in the description field nothing is displayed.
	 * 
	 * @return A reference to the String value with the formatted values.
	 */
	@Override
	public String toString() {
		return String.format("Event: %s\nDescription: %s", getCategory(),
				(getDescription() != null) ? getDescription() : "");
	}
}
