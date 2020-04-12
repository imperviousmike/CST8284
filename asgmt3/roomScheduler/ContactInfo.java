package cst8284.asgmt3.roomScheduler;

import java.io.Serializable;

/**
 * {@code ContactInfo} class is used to hold the values for the contact for a
 * {@link cst8284.asgmt3.roomScheduler.RoomBooking}. This class implements
 * {@code Serializable} to allow for saving and loading from a file done in the
 * {@link cst8284.asgmt3.roomScheduler.RoomScheduler}.
 * 
 * @version 1.0
 * @author Michael Daly
 * 
 */

public class ContactInfo implements Serializable {

	/**
	 * First name of the contact for the
	 * {@link cst8284.asgmt3.roomScheduler.RoomBooking}.
	 */
	private String firstName;
	/**
	 * Last name of the contact for the
	 * {@link cst8284.asgmt3.roomScheduler.RoomBooking}.
	 */
	private String lastName;
	/**
	 * Phone number of the contact for the
	 * {@link cst8284.asgmt3.roomScheduler.RoomBooking}.
	 */
	private String phoneNumber;
	/**
	 * Organization of the contact for the
	 * {@link cst8284.asgmt3.roomScheduler.RoomBooking}.
	 */
	private String organization;

	/**
	 * {@code serialVersionUID} is used during serialization as a way to version the
	 * object when saving, this is also needed when de-serializing a saved object to
	 * check if its the expected version.
	 */
	public static final long serialVersionUID = 1L;

	/**
	 * Constructs a {@code ContactInfo} object. Sets the relevant info provided by
	 * the user in the associated fields.
	 * 
	 * @param firstName    The first name of the contact for the booking to be set.
	 * @param lastName     The first name of the contact for the booking to be set.
	 * @param phoneNumber  The phone number of the contact for the booking to be
	 *                     set.
	 * @param organization The organization of the contact for the booking to be
	 *                     set.
	 * 
	 */
	public ContactInfo(String firstName, String lastName, String phoneNumber, String organization) {
		setFirstName(firstName);
		setLastName(lastName);
		setPhoneNumber(phoneNumber);
		setOrganization(organization);
	}

	/**
	 * Constructs a {@code ContactInfo} where no organization is provided. It sets a
	 * default value for the organization and chains it to the main constructor
	 * 
	 * @param firstName   The first name of the contact for the booking to be set.
	 * @param lastName    The first name of the contact for the booking to be set.
	 * @param phoneNumber The phone number of the contact for the booking to be set.
	 * 
	 */
	public ContactInfo(String firstName, String lastName, String phoneNumber) {
		this(firstName, lastName, phoneNumber, "Algonquin College");
	}

	/**
	 * Returns the reference to the object set in the {@code firstName} field.
	 * 
	 * @return A reference to the String value of the set {@code firstName}.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the reference to the object of the {@code firstName} field.
	 * 
	 * @param firstName {@code String} value used to set the field.
	 * 
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns the reference to the objectset in the {@code description} field.
	 * 
	 * @return A reference to the String value of the set {@code lastName}.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the reference to the objectof the {@code lastName} field.
	 * 
	 * @param lastName {@code String} value used to set the field.
	 * 
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns the reference to the objectset in the {@code phoneNumber} field.
	 * 
	 * @return A reference to the String value of the set {@code phoneNumber}.
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Sets the reference to the object of the {@code phoneNumber} field.
	 * 
	 * @param phoneNumber {@code String} value used to set the field.
	 * 
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Returns the reference to the object set in the {@code organization} field.
	 * 
	 * @return A reference to the String value of the set {@code organization}.
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * Sets the reference to the object of the {@code organization} field.
	 * 
	 * @param organization {@code String} value used to set the field.
	 * 
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	/**
	 * Returns a reference to a formatted {@code String} of the stored values in
	 * this class.
	 * 
	 * @return A reference to the String with the formatted values.
	 */
	@Override
	public String toString() {
		return String.format("Contact Information:%s %s \nPhone: %s \n%s", getFirstName(), getLastName(),
				getPhoneNumber(),
				(getOrganization()) == null || getOrganization().isEmpty() ? "" : getOrganization() + "\n");
	}

}
