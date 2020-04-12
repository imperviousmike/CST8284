package cst8284.asgmt4.roomScheduler;

import java.io.Serializable;

/**
 * {@code RoomBooking} class is a composition of
 * {@link cst8284.asgmt4.roomScheduler.ContactInfo},{@link cst8284.asgmt4.roomScheduler.Activity},{@link cst8284.asgmt4.roomScheduler.TimeBlock}.
 * This class consolidates all the elements needed to make up a single room
 * booking.
 * 
 * @version 1.0
 * @author Michael Daly
 * 
 */

public class RoomBooking implements Serializable {

	/**
	 * References the created object containing the bookings contact info input by
	 * the user in {@link cst8284.asgmt4.roomScheduler.RoomScheduler}.
	 */
	private ContactInfo contactInfo;
	/**
	 * References the created object containing the bookings activity info input by
	 * the user in {@link cst8284.asgmt4.roomScheduler.RoomScheduler}.
	 */
	private Activity activity;
	/**
	 * References the created object containing the date and time info input by the
	 * user in {@link cst8284.asgmt4.roomScheduler.RoomScheduler}.
	 */
	private TimeBlock timeBlock;

	/**
	 * {@code serialVersionUID} is used during serialization as a way to version the
	 * object when saving, this is also needed when de-serializing a saved object to
	 * check if its the expected version.
	 */
	public static final long serialVersionUID = 1L;

	/**
	 * Constructs a {@code RoomBooking} object. Sets the references to the relevant
	 * classes that make up a room booking passed by the implementer.
	 * 
	 * @param timeBlock   reference to a created {@code TimeBlock} object relevant
	 *                    to the other parameters to be stored.
	 * @param contactInfo reference to a created {@code ContactInfo} relevant to the
	 *                    other parameters object to be stored.
	 * @param activity    reference to a created {@code Activity} relevant to the
	 *                    other parameters object to be stored.
	 * 
	 */
	public RoomBooking(TimeBlock timeBlock, ContactInfo contactInfo, Activity activity) {
		setTimeBlock(timeBlock);
		setContactInfo(contactInfo);
		setActivity(activity);
	}

	/**
	 * Returns a reference to the object set in {@code contactInfo}.
	 * 
	 * @return reference to the stored
	 *         {@link cst8284.asgmt4.roomScheduler.ContactInfo}
	 */
	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	/**
	 * Sets the reference stored in the {@code contactInfo} field to the passed
	 * value.
	 * 
	 * @param contactInfo reference to the
	 *                    {@link cst8284.asgmt4.roomScheduler.ContactInfo} to be
	 *                    stored.
	 * 
	 */
	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

	/**
	 * Returns a reference to the object set in {@code activity}.
	 * 
	 * @return reference to the stored {@link cst8284.asgmt4.roomScheduler.Activity}
	 */
	public Activity getActivity() {
		return activity;
	}

	/**
	 * Sets the reference stored in the {@code activity} field to the passed value.
	 * 
	 * @param activity reference to the
	 *                 {@link cst8284.asgmt4.roomScheduler.Activity} to be stored.
	 * 
	 */
	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	/**
	 * Returns a reference to the object set in {@code timeBlock}.
	 * 
	 * @return reference to the stored
	 *         {@link cst8284.asgmt4.roomScheduler.TimeBlock}
	 */
	public TimeBlock getTimeBlock() {
		return timeBlock;
	}

	/**
	 * Sets the reference stored in the {@code timeBlock} field to the passed value.
	 * 
	 * @param timeBlock reference to the
	 *                  {@link cst8284.asgmt4.roomScheduler.TimeBlock} to be stored.
	 * 
	 */
	public void setTimeBlock(TimeBlock timeBlock) {
		this.timeBlock = timeBlock;
	}

	/**
	 * Returns a formatted {@code String} of the stored values of the booking by
	 * leveraging the stored attributes already implemented toString().
	 * 
	 * @return {@code String} with the formatted values.
	 */
	@Override
	public String toString() {
		String roomBreak = "---------------";
		return roomBreak + String.format("\n%s\n%s\n%s", getTimeBlock(), getActivity(), getContactInfo()) + roomBreak;
	}
}
