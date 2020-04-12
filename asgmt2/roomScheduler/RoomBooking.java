package cst8284.asgmt2.roomScheduler;

import java.io.Serializable;

/*
	Course Name: CST8284
	Student Name: Michael Daly
	Class name: Object Oriented Programming (Java)
	Date: March 2nd, 2020
*/

public class RoomBooking implements Serializable {

	private ContactInfo contactInfo;
	private Activity activity;
	private TimeBlock timeBlock;

	public static final long serialVersionUID = 1L;

	public RoomBooking(TimeBlock timeBlock, ContactInfo contactInfo, Activity activity) {
		setTimeBlock(timeBlock);
		setContactInfo(contactInfo);
		setActivity(activity);
	}

	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public TimeBlock getTimeBlock() {
		return timeBlock;
	}

	public void setTimeBlock(TimeBlock timeBlock) {
		this.timeBlock = timeBlock;
	}

	@Override
	public String toString() {
		String roomBreak = "---------------";
		return roomBreak + String.format("\n%s\n%s\n%s", getTimeBlock(), getActivity(), getContactInfo()) + roomBreak;
	}
}
