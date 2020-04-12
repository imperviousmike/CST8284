package cst8284.asgmt4.roomScheduler;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Map;

import javax.swing.JTextField;

import firstyear.secondsemester.cst8284.asgmt4.room.Room;
import firstyear.secondsemester.cst8284.asgmt4.utility.RoomSchedulerConstants;
import firstyear.secondsemester.cst8284.asgmt4.view.RoomSchedulerOptionPane;

/**
 * {@code RoomScheduler} contains the main functionality in the manipulation and
 * creation for any {@link cst8284.asgmt4.roomScheduler.RoomBooking} and the
 * list that contains them, it also produces output to display for the GUI.
 * 
 * @author Michael Daly
 * @version 4.0
 * 
 */

public class RoomScheduler {

	/**
	 * The list that will hold all bookings created by the user.
	 */
	private ArrayList<RoomBooking> roomBookings;
	/**
	 * Holds the information for the room where the bookings will take place.
	 */
	private Room room;

	/**
	 * Constructs a {@code RoomScheduler} object. It attempts to load from a file if
	 * there are previous bookings from another session. It expects a room to be
	 * given to specify the room type for all the bookings.
	 * 
	 * @param room Object holding the information of the specific room in which all
	 *             the bookings take place.
	 */
	public RoomScheduler(Room room) {
		if ((roomBookings = loadBookingsFromFile(null)) == null)
			roomBookings = new ArrayList<>();
		setRoom(room);
	}

	/**
	 * Uses the {@link cst8284.asgmt4.room.Room} {@code toString()} to print out the
	 * current bookings room information.
	 * 
	 */
	public String displayRoomInfo() {
		return getRoom().toString();
	}

	/**
	 * The method takes input from the GUI and calls the correct processes to handle
	 * the option selected.
	 *
	 * @param option What the user selected on the GUI. Uses
	 *               {@code RoomSchedulerConstants} to ensure consistency between
	 *               all options and selections.
	 * 
	 * @param input  A map that has input field and the context for that field.
	 * 
	 * @return Depending on the option they return different possibilities, booleans
	 *         if the action is done purely to the booking and thats the state it
	 *         returns and String if its just output that needs to be forwarded.
	 */
	public Object executeMenuItem(RoomSchedulerConstants option,
			LinkedHashMap<RoomSchedulerConstants, JTextField> input) {
		try {
			switch (option) {
			case BOOKING_BACKUP:
				return saveBookingsToFile(input);
			case BOOKING_LOAD:
				roomBookings = loadBookingsFromFile(input);
				return true;
			case BOOKING_ADD:
				try {
					return saveRoomBooking(
							makeBookingFromUserInput((LinkedHashMap<RoomSchedulerConstants, JTextField>) input));
				} catch (BadRoomBookingException e) {

				}
			case MODIFY_BOOKING_FIND:
				return displayBooking(makeCalendarFromUserInput(input));
			case MODIFY_BOOKING_DELETE:
				return deleteBooking(makeCalendarFromUserInput(input));
			case MODIFY_BOOKING_CHANGE:
				return changeBooking(input);
			case DISPLAY_BOOKING_DAY:
				return displayDayBookings(makeCalendarFromUserInput(input));
			case DISPLAY_BOOKING_CALENDAR:
				return makeCalendarFromUserInput(input);
			default:
				return false;
			}
		} catch (BadRoomBookingException e) {

		}
		return false;

	}

	/**
	 * When a {@link cst8284.asgmt4.roomScheduler.RoomBooking} has been created this
	 * method attempts to store it in the list of bookings. It attempts to try and
	 * find any booking between the start and end time of the passed booking to see
	 * if there are any overlaps(if there is any overlap method returns false). If
	 * there are not it stores this new booking and uses
	 * {@code SortSortRoomBookingsByCalendar()} comparator to sort the stored
	 * bookings from earliest to latest. Once it has saved it returns true to
	 * confirm the job is done.
	 *
	 * @param booking The booking to be saved into the list.
	 * 
	 * @return The parameter passed end time is returned, meaning it is allowed to
	 *         be stored since it fits within the rules.
	 */
	private boolean saveRoomBooking(RoomBooking booking) {

		if (booking != null) {
			Calendar calCheck = (Calendar) booking.getTimeBlock().getStartTime().clone();
			for (int i = 0; i < booking.getTimeBlock().duration(); i++) {
				if (findBooking(calCheck) != null)
					throw new BadRoomBookingException("Overlapping/Duplicate booking",
							"A overlapping or duplicate entry has been found please check your date/time", null);
				calCheck.add(Calendar.HOUR_OF_DAY, 1);
			}
			getRoomBookings().add(booking);
			getRoomBookings().sort(new SortRoomBookingsByCalendar());
			return true;
		}
		return false;

	}

	/**
	 * If the user wishes to remove a booking from a time slot this handles that. It
	 * uses the {@code findBooking()} method to first find a booking from the time
	 * of the Calendar passed as a parameter. If one is found it shows it to the
	 * user and prompts them if they wish to continue and delete this booking. If
	 * they agree then the booking is removed.
	 * 
	 * @param cal time and date of the booking they wish to remove.
	 * @return The success of removing the booking.
	 */
	private boolean deleteBooking(Calendar cal) {
		RoomBooking rb = findBooking(cal);
		if (rb != null) {
			return getRoomBookings().remove(rb);
		}
		return false;

	}

	/**
	 * When the user wants to change the time slot of a booking this is the method
	 * that handles it. First it attempts the find the booking from the given
	 * time/date(from the parameter cal) with a call to {@code findBooking}. If
	 * there is an entry the method creates a an identical booking to the previous
	 * to store so it can be removed from the list. This is to avoid an overlap if
	 * the user is changing time from within the same time slot.
	 * 
	 * Then two {@code Calendars} are created and set the times to the new times
	 * provided by the user(which are also checked to fit within the rules establish
	 * in {@link cst8284.asgmt4.roomScheduler.RoomBookingInputValidator}). The
	 * booking with the new time slot is attempted to be added into the list with a
	 * call to the {@code saveRoomBooking()} method. If it finds another entry
	 * trying to save it lets the user know and re-adds the original booking with no
	 * changes. Otherwise this new change entry is saved to the list.
	 * 
	 * @param input input from the user with the time they wish to change, and the
	 *              new values.
	 * 
	 * @return the result of the attempt to change(if it did or did not).
	 */
	private boolean changeBooking(LinkedHashMap<RoomSchedulerConstants, JTextField> input) {
		Calendar cal = makeCalendarFromUserInput(input);
		RoomBooking rb = findBooking(cal);
		if (rb != null) {
			TimeBlock changedTb = new TimeBlock((Calendar) rb.getTimeBlock().getStartTime().clone(),
					(Calendar) rb.getTimeBlock().getEndTime().clone());
			RoomBooking changedRoom = new RoomBooking(changedTb, rb.getContactInfo(), rb.getActivity());
			getRoomBookings().remove(rb);
			while (true) {
				try {
					Calendar start = changedRoom.getTimeBlock().getStartTime();
					Calendar end = changedRoom.getTimeBlock().getEndTime();
					for (Map.Entry<RoomSchedulerConstants, JTextField> entry : input.entrySet()) {
						RoomSchedulerConstants rsc = entry.getKey();
						JTextField text = entry.getValue();
						switch (rsc) {
						case MODIFY_NEW_START:
							start.set(Calendar.HOUR_OF_DAY, Integer.parseInt(processTimeString(text)));
							break;
						case MODIFY_NEW_END:
							end.set(Calendar.HOUR_OF_DAY, Integer.parseInt(processTimeString(text)));
							RoomBookingInputValidator.compareBookingTime(start, end, text);
							break;
						default:
							break;
						}
					}

					break;
				} catch (BadRoomBookingException e) {

				}
			}

			if (!saveRoomBooking(changedRoom)) {
				getRoomBookings().add(rb);
				getRoomBookings().sort(new SortRoomBookingsByCalendar());
				throw new BadRoomBookingException("Overlapping/Duplicate booking",
						"A overlapping or duplicate entry has been found please check your date/time", null);
			} else {
				new RoomSchedulerOptionPane("Booking Changed", "Your booking has been changed");
				return true;
			}
		}
		return false;
	}

	/**
	 * If a booking is found from the time provided then this will return he found
	 * entry, if not it will display a message indicating it did not find.
	 * 
	 * @param cal time and date of the booking they wish to display.
	 * 
	 * @return A string represnetation of the found bookings, or a message
	 *         displaying it has not found anything.
	 */
	public String displayBooking(Calendar cal) {
		RoomBooking rb = findBooking(cal);
		return ((rb == null) ? String.format("No booking scheduled between %d:00 and %d:00\n",
				cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.HOUR_OF_DAY) + 1) : rb.toString());

	}

	/**
	 * This method displays all bookings for an entire day(if there is any). A new
	 * booking covering the entire day(the day provided by the user in the parameter
	 * cal) is created and attempted to match against the sorted list. The first
	 * entry it finds will be the first entry for the day(this is because of the
	 * sorting). The list is then iterated using {@code ListIterator}. This is
	 * because when we find the first entry with a {@code binarySearch} it allows an
	 * iterator to start from the specific spot and end where the logic needs it
	 * without knowing the actual position of the last entry beforehand.
	 * 
	 * From the first time slot found to the last time slot found, it prints out any
	 * entries it finds, and printing out a message showing that slot is empty for
	 * the ones it doesn't.
	 * 
	 * @param cal The date of all the bookings they wish to display.
	 * 
	 * @return A string representation of each timeblock found(or not found) from
	 *         the day provided
	 * 
	 */
	public String displayDayBookings(Calendar cal) {
		StringBuilder sb = new StringBuilder();
		cal.set(Calendar.HOUR_OF_DAY, 8);
		if (getRoomBookings().size() != 0) {
			Calendar end = (Calendar) cal.clone();
			end.set(Calendar.HOUR_OF_DAY, 23);
			getRoomBookings().sort(new SortRoomBookingsByCalendar());
			int index = Collections.binarySearch(getRoomBookings(),
					new RoomBooking(new TimeBlock(cal, end), null, null), new SortRoomBookingsByCalendar());
			if (index < 0) {
				do {
					sb.append(displayBooking(cal));
					cal.add(Calendar.HOUR_OF_DAY, 1);
				} while (cal.get(Calendar.HOUR_OF_DAY) < 23);
				return sb.toString();
			}
			ListIterator<RoomBooking> iter = getRoomBookings().listIterator(index);
			end.add(Calendar.DAY_OF_YEAR, 1);
			RoomBooking rb = iter.next();

			do {
				for (int i = cal.get(Calendar.HOUR_OF_DAY); i < rb.getTimeBlock().getStartTime()
						.get(Calendar.HOUR_OF_DAY); i++) {
					sb.append(displayBooking(cal));
					cal.add(Calendar.HOUR_OF_DAY, 1);
				}
				sb.append(rb + "\n");
				cal.add(Calendar.HOUR_OF_DAY, rb.getTimeBlock().duration());

			} while (iter.hasNext() && (rb = iter.next()).getTimeBlock().getStartTime().get(Calendar.DAY_OF_YEAR) < end
					.get(Calendar.DAY_OF_YEAR));

		}

		if (cal.get(Calendar.HOUR_OF_DAY) < 23) {
			do {
				sb.append(displayBooking(cal));
				cal.add(Calendar.HOUR_OF_DAY, 1);
			} while (cal.get(Calendar.HOUR_OF_DAY) < 23);

		}

		return sb.toString();

	}

	/**
	 * This provides the functionality to take the list of bookings and save them
	 * into a file for future use. It attempts to open the path to the file(which is
	 * in the default current directory of the running application). If the file
	 * doesn't exist it tries to create one. If both of those fail we return the
	 * failed state. Otherwise a stream is opened that writes each
	 * {@link cst8284.asgmt4.roomScheduler.RoomBooking} stored in the list to the
	 * file.
	 * 
	 * @param The file location from the GUI.
	 * 
	 * @return The result of the save(true or false if it failed).
	 */
	private boolean saveBookingsToFile(LinkedHashMap<RoomSchedulerConstants, JTextField> input) {
		File file;
		if (input != null) {
			Map.Entry<RoomSchedulerConstants, JTextField> entry = input.entrySet().iterator().next();
			String fileLocation = entry.getValue().getText();
			file = new File(fileLocation);
		} else {
			file = new File("CurrentRoomBookings.book");
		}

		try {
			if (!file.exists())
				if (!file.createNewFile())
					return false;
			FileOutputStream fileToSave = new FileOutputStream(file);
			ObjectOutputStream streamToSave = new ObjectOutputStream(fileToSave);
			for (RoomBooking rb : getRoomBookings())
				streamToSave.writeObject(rb);
			streamToSave.close();

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * This method attempts to load any saved bookings from the default file. It
	 * creates an empty list of {@link cst8284.asgmt4.roomScheduler.RoomBooking}
	 * that will be returned after attempting to load. It attempts to open the file
	 * from its default location(the current working directory of the application).
	 * It uses {@code File}s {@code canRead()} function which checks if it exists
	 * and if it can be opened and have its contents read. If it cannot be read the
	 * empty list is returned.
	 * 
	 * If the file exists and is able to be read then a stream is opened and each
	 * entry is parsed as a {@link cst8284.asgmt4.roomScheduler.RoomBooking} object
	 * and attempted to be added to the list until the file throws the EOF error
	 * which lets it know that there is nothing left to be read.
	 * 
	 * Another other unforeseen errors are returned as null to be handled
	 * appropriately.
	 * 
	 * 
	 * @param input The file location provided by the user.
	 * 
	 * @return The list containing any loaded entries(if found). Null otherwise if
	 *         any other errors occur.
	 */
	private ArrayList<RoomBooking> loadBookingsFromFile(LinkedHashMap<RoomSchedulerConstants, JTextField> input) {
		ArrayList<RoomBooking> bookingList = new ArrayList<>();
		File file;
		if (input != null) {
			Map.Entry<RoomSchedulerConstants, JTextField> entry = input.entrySet().iterator().next();
			String fileLocation = entry.getValue().getText();
			file = new File(fileLocation);
		} else {
			file = new File("CurrentRoomBookings.book");
		}

		try {
			FileInputStream loadData = new FileInputStream(file);
			try (ObjectInputStream loadStream = new ObjectInputStream(loadData)) {
				while (true) {
					bookingList.add((RoomBooking) loadStream.readObject());
				}
			}
		} catch (EOFException x) {
		} catch (Exception e) {
			return null;
		}
		return bookingList;

	}

	/**
	 * When a user selects the option to create a new booking this method handles
	 * the retrieving of the necessary values and create a new
	 * {@link cst8284.asgmt4.roomScheduler.RoomBooking} object based off them. It
	 * loops continually until it gets inputs in the correct format in order to save
	 * having to retype the same values over and over again.
	 * 
	 * All inputs use either directly or indirectly the logic in
	 * {@link cst8284.asgmt4.roomScheduler.RoomBookingInputValidator} to ensure the
	 * inputed values meet expected conditions.
	 * 
	 * @param inputs a map of all the input fields and there relevance to a booking.
	 * 
	 * @return A newly created {@link cst8284.asgmt4.roomScheduler.RoomBooking} from
	 *         containing the values just input.
	 */
	private RoomBooking makeBookingFromUserInput(LinkedHashMap<RoomSchedulerConstants, JTextField> inputs) {
		String[] nameSplit = new String[2];
		String phone = "", organization = "", event = "", description = "";
		Calendar start = Calendar.getInstance(), end = Calendar.getInstance();

		try {
			for (Map.Entry<RoomSchedulerConstants, JTextField> entry : inputs.entrySet()) {
				RoomSchedulerConstants rsc = entry.getKey();
				JTextField text = entry.getValue();

				switch (rsc) {
				case SAVE_BOOKING_NAME:
					nameSplit = RoomBookingInputValidator.processNameInput(text);
					break;
				case SAVE_BOOKING_PHONE:
					phone = RoomBookingInputValidator.processPhoneNumberInput(text);
					break;
				case SAVE_BOOKING_EVENT:
					event = RoomBookingInputValidator.processGeneralInput(text);
					break;
				case SAVE_BOOKING_ORG:
					break;
				case SAVE_BOOKING_DESC:
					description = RoomBookingInputValidator.processGeneralInput(text);
					break;
				case BOOKING_DATE:
					start.setTime(RoomBookingInputValidator.checkDateFormat(text));
					end = (Calendar) start.clone();
					break;
				case BOOKING_START:
					RoomBookingInputValidator.processGeneralInput(text);
					start.set(Calendar.HOUR_OF_DAY, Integer.parseInt(processTimeString(text)));
					break;
				case BOOKING_END:
					RoomBookingInputValidator.processGeneralInput(text);
					end.set(Calendar.HOUR_OF_DAY, Integer.parseInt(processTimeString(text)));
					end = RoomBookingInputValidator.compareBookingTime(start, end, text);
					break;
				default:
					break;

				}

			}
		} catch (BadRoomBookingException e) {
			return null;
		}
		return new RoomBooking(new TimeBlock(start, end),
				new ContactInfo(nameSplit[0], nameSplit[1], phone, organization), new Activity(event, description));

	}

	/**
	 * Creates a new Calendar object based on the date(if provided) and time(if
	 * provided) given by the user. The user can also request an hour on a
	 * previously created calendar via {@code requestHour} parameter. If a null
	 * {@code Calendar} has been provided gets the user to give a date and sets a
	 * new Calendar with that date.
	 * 
	 * If ({@code requestHour} is provided as true the user is prompted to enter a
	 * time. It is then parsed and than added to the {@code Calendar} object.
	 * 
	 * @param cal         The calendar to be created or modified.
	 * @param requestHour When a user needs to set an hour this is set to true.
	 * 
	 * @return The now modified cal provided in the parameters.
	 */
	private Calendar makeCalendarFromUserInput(LinkedHashMap<RoomSchedulerConstants, JTextField> inputs) {
		Calendar cal = Calendar.getInstance();

		try {
			for (Map.Entry<RoomSchedulerConstants, JTextField> entry : inputs.entrySet()) {
				RoomSchedulerConstants rsc = entry.getKey();
				JTextField text = entry.getValue();
				switch (rsc) {
				case BOOKING_DATE:
					cal.setTime(RoomBookingInputValidator.checkDateFormat(text));
					break;
				case BOOKING_START:
					cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(processTimeString(text)));
					break;
				default:
					break;
				}
			}
		} catch (BadRoomBookingException e) {

		}
		return cal;

	}

	/**
	 * Grabs a booking from the list that matches the time provided by the user.
	 * Another calendar is cloned and has an hour added to its time to create the 1
	 * hour time block needed for any booking. It sorts the list if it is out of
	 * order(if the application has just loaded and populated from a file that was
	 * not sorted and now wants to display, this is where it is needed). It than
	 * uses {@code binarySearch} via the Collections class to find this specific
	 * booking. If the search returns an index value, it has matched and is
	 * retrieved from that position and returned.
	 * 
	 * @param cal The time slot in which the implementer wishes to check for a
	 *            booking.
	 * 
	 * @return Either a found booking or null if nothing is found.
	 */
	private RoomBooking findBooking(Calendar cal) {
		Calendar endTime = (Calendar) cal.clone();
		endTime.add(Calendar.HOUR_OF_DAY, 1);
		getRoomBookings().sort(new SortRoomBookingsByCalendar());
		int index = Collections.binarySearch(getRoomBookings(),
				new RoomBooking(new TimeBlock(cal, endTime), null, null), new SortRoomBookingsByCalendar());

		if (index >= 0) {
			return getRoomBookings().get(index);
		}

		return null;
	}

	/**
	 * Any inputed time values need to be parsed properly before they can be stored
	 * in proper locations. Based on possible formats like 8:00, 8 am, 8am (but not
	 * inclusive), this is where they are handled. If the value contains a P/p
	 * anywhere than we know it is a P.M value and can add 12(if its not 12
	 * already). If its 2 digits or one digit only the relevant digits need to be
	 * grabbed. Any extra characters and spaces are removed and the PM value is
	 * added if it is a P.M time value.
	 * 
	 * @param t A {@code String} with the entered time value to be parsed.
	 * 
	 * @return The fixed time String.
	 */
	private static String processTimeString(JTextField t) {
		try {
			String text = t.getText();
			int fixPM = (text.toLowerCase().contains("p")) ? 12 : 0;
			String fixed = (text.length() >= 2) ? text.substring(0, 2) : text.substring(0, 1);
			return Integer.toString(Integer.parseInt(fixed.trim().replace(":", "")) + fixPM);
		} catch (Exception e) {
			throw new BadRoomBookingException("Improper time format",
					"The entered time is in the wrong format, please match the format provided by the label", t);
		}
	}

	/**
	 * Any time the list of the created bookings is needed this should be called to
	 * access it.
	 * 
	 * @return A reference to the current list of bookings.
	 */
	public ArrayList<RoomBooking> getRoomBookings() {
		return roomBookings;
	}

	/**
	 * Sets the reference to the room type for list of bookings.
	 * 
	 * @param room Reference to the new room to be saved.
	 * 
	 */
	private void setRoom(Room room) {
		this.room = room;
	}

	/**
	 * Retrieves a reference to the current saved room type.
	 * 
	 * @return Reference to the current saved room type.
	 */
	public Room getRoom() {
		return room;
	}

}
