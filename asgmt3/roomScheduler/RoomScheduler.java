package cst8284.asgmt3.roomScheduler;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.ListIterator;
import java.util.Scanner;

import firstyear.secondsemester.cst8284.asgmt3.room.Room;

/**
 * {@code RoomScheduler} contains the main functionality of the entire
 * application. Here is where creation and manipulation of all
 * {@link cst8284.asgmt3.roomScheduler.RoomBooking} are done. This also handles
 * display for the user allowing them to interact with the application.
 * 
 * @author Michael Daly
 * @version 3.0
 * 
 */

public class RoomScheduler {

	/**
	 * Option for the user to enter to get the room info.
	 */
	private static final int DISPLAY_ROOM_INFORMATION = 1;
	/**
	 * Option for the user to enter to enter a new booking.
	 */
	private static final int ENTER_ROOM_BOOKING = 2;
	/**
	 * Option for the user to enter to delete a booking of their choosing.
	 */
	private static final int DELETE_BOOKING = 3;
	/**
	 * Option for the user to enter change the booking of their choosing.
	 */
	private static final int CHANGE_BOOKING = 4;
	/**
	 * Option for the user to enter to display a single booking(if it exists).
	 */
	private static final int DISPLAY_BOOKING = 5;
	/**
	 * Option for the user to enter to display an entire days bookings(if they
	 * exist).
	 */
	private static final int DISPLAY_DAY_BOOKINGS = 6;
	/**
	 * Option for the user to enter to save all currently entered bookings to a
	 * file.
	 */
	private static final int SAVE_BOOKINGS_TO_FILE = 7;
	/**
	 * Option for the user to load all saved bookings from a file.
	 */
	private static final int LOAD_BOOKINGS_FROM_FILE = 8;
	/**
	 * Option for the user to enter to exit the application.
	 */
	private static final int EXIT = 0;
	/**
	 * This {@code Scanner} is used to get input from the user to determine what
	 * they want to do in the application.
	 */
	private static Scanner scan = new Scanner(System.in);
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
		if ((roomBookings = loadBookingsFromFile()) == null)
			roomBookings = new ArrayList<>();
		setRoom(room);
	}

	/**
	 * The starting point of the application. It displays the menu and executes the
	 * option based on the user input. Runs until the user enters the proper option
	 * for exit(stored in the {@code EXIT} field).
	 * 
	 */
	public void launch() {
		int choice = 1;
		do {
			try {
				choice = displayMenu();
				executeMenuItem(choice);
			} catch (BadRoomBookingException e) {
				System.out.println("\n" + e.getHeader() + "\n" + e.getMessage() + "\n");
			}
		} while (choice != EXIT);

	}

	/**
	 * Prints out the all the possible functions of the application available to the
	 * user currently. Takes the users input and returns what they inputed to be
	 * passed up so the application can run the correct processes.
	 * 
	 * @return {@code int} value with the users selected option.
	 */
	private int displayMenu() {
		System.out.println("Enter a selection from the following menu:");
		System.out.println(DISPLAY_ROOM_INFORMATION + ". Display room information");
		System.out.println(ENTER_ROOM_BOOKING + ". Enter a room booking");
		System.out.println(DELETE_BOOKING + ". Delete a room booking");
		System.out.println(CHANGE_BOOKING + ". Change a room booking");
		System.out.println(DISPLAY_BOOKING + ". Display a booking");
		System.out.println(DISPLAY_DAY_BOOKINGS + ". Display room bookings for the whole day");
		System.out.println(SAVE_BOOKINGS_TO_FILE + ". Backup current bookings to file");
		System.out.println(LOAD_BOOKINGS_FROM_FILE + ". Load current bookings from file");
		System.out.println(EXIT + ". Exit program");
		return Integer.parseInt(getResponseTo(""));
	}

	/**
	 * Based on the choice given this method determines the proper processes to run.
	 * Outputs messages based on the result of said processes
	 * 
	 * @param choice matches with the expected options and runs the correct methods.
	 */
	private void executeMenuItem(int choice) {
		switch (choice) {
		case DISPLAY_ROOM_INFORMATION:
			displayRoomInfo();
			break;
		case ENTER_ROOM_BOOKING:
			RoomBooking rb = makeBookingFromUserInput();
			System.out.println(saveRoomBooking(rb) ? "Booking time and date saved."
					: "Error: found overlapping entry, could not save booking");
			break;
		case DELETE_BOOKING:
			System.out.println("Enter booking to delete");
			System.out.println(deleteBooking(makeCalendarFromUserInput(null, true)) ? "Booking deleted\n"
					: "Could not delete booking\n");
			// resort the list if a booking is removed to not mess up the order
			getRoomBookings().sort(new SortRoomBookingsByCalendar());

			break;
		case CHANGE_BOOKING:
			System.out.println("Enter booking to change");
			System.out.println(
					changeBooking(makeCalendarFromUserInput(null, true)) ? "" : "Could not find booking to change");
			break;
		case DISPLAY_BOOKING:
			displayBooking(makeCalendarFromUserInput(null, true));
			break;
		case DISPLAY_DAY_BOOKINGS:
			displayDayBookings(makeCalendarFromUserInput(null, false));
			break;
		case SAVE_BOOKINGS_TO_FILE:
			System.out.println(saveBookingsToFile() ? "Current room bookings backed up to file\n"
					: "Error backing up room bookings\n");
			break;
		case LOAD_BOOKINGS_FROM_FILE:
			// store it in a temp array for safety just in case
			ArrayList<RoomBooking> tempBookings = loadBookingsFromFile();
			if (tempBookings != null) {
				getRoomBookings().clear();
				getRoomBookings().addAll(loadBookingsFromFile());
				System.out.println("Current room bookings loaded from file\n");
			}
			break;
		case EXIT:
			System.out.println(saveBookingsToFile() ? "Exiting Room Booking Application"
					: "Error saving to file, Exiting Room Booking Application");
			break;
		default:
			System.out.println("Enter one of the options available above  ");
		}
	}

	/**
	 * Uses the {@link cst8284.asgmt3.room.Room} {@code toString()} to print out the
	 * current bookings room information.
	 * 
	 * @see {@link cst8284.asgmt3.room.Room#toString()}
	 */
	private void displayRoomInfo() {
		System.out.println(getRoom());
	}

	/**
	 * When a {@link cst8284.asgmt3.roomScheduler.RoomBooking} has been created this
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
					return false;
				calCheck.add(Calendar.HOUR_OF_DAY, 1);
			}
			getRoomBookings().add(booking);
			getRoomBookings().sort(new SortRoomBookingsByCalendar());
			return true;
		}
		return true;

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
			System.out.println(rb);
			if (getResponseTo("Press 'Y' to confirm deletion, any other key to abort ").toLowerCase().contains("y")) {
				return getRoomBookings().remove(rb);
			}
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
	 * in {@link cst8284.asgmt3.roomScheduler.RoomBookingInputValidator}). The
	 * booking with the new time slot is attempted to be added into the list with a
	 * call to the {@code saveRoomBooking()} method. If it finds another entry
	 * trying to save it lets the user know and re-adds the original booking with no
	 * changes. Otherwise this new change entry is saved to the list.
	 * 
	 * @param cal time and date of the booking they wish to change.
	 * 
	 * @return the result of the attempt to change(if it did or did not).
	 */
	private boolean changeBooking(Calendar cal) {
		RoomBooking rb = findBooking(cal);
		if (rb != null) {
			System.out.println(rb);
			TimeBlock changedTb = new TimeBlock((Calendar) rb.getTimeBlock().getStartTime().clone(),
					(Calendar) rb.getTimeBlock().getEndTime().clone());
			RoomBooking changedRoom = new RoomBooking(changedTb, rb.getContactInfo(), rb.getActivity());
			getRoomBookings().remove(rb);
			while (true) {
				try {
					Calendar start = changedRoom.getTimeBlock().getStartTime();
					Calendar end = changedRoom.getTimeBlock().getEndTime();
					start.set(Calendar.HOUR_OF_DAY,
							Integer.parseInt(processTimeString(getResponseTo("Enter New Start Time: "))));
					end.set(Calendar.HOUR_OF_DAY,
							Integer.parseInt(processTimeString(getResponseTo("Enter New End Time: "))));
					RoomBookingInputValidator.compareBookingTime(start, end);
					break;
				} catch (BadRoomBookingException e) {
					System.out.println("\n" + e.getHeader() + "\n" + e.getMessage() + "\n");
				}
			}

			if (!saveRoomBooking(changedRoom)) {
				System.out.println("Found overlapping entry, could not change");
				getRoomBookings().add(rb);
				getRoomBookings().sort(new SortRoomBookingsByCalendar());
			} else {
				System.out.format("Booking has been changed to:\n%s\n", changedRoom);
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
	 * @return If a booking is found that is return otherwise a null booking is
	 *         returned.
	 */
	private RoomBooking displayBooking(Calendar cal) {
		RoomBooking rb = findBooking(cal);
		System.out.println((rb == null) ? String.format("No booking scheduled between %d:00 and %d:00",
				cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.HOUR_OF_DAY) + 1) : rb);
		return rb;
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
	 */
	private void displayDayBookings(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 8);
		if (getRoomBookings().size() != 0) {
			Calendar end = (Calendar) cal.clone();
			end.set(Calendar.HOUR_OF_DAY, 23);
			getRoomBookings().sort(new SortRoomBookingsByCalendar());
			int index = Collections.binarySearch(getRoomBookings(),
					new RoomBooking(new TimeBlock(cal, end), null, null), new SortRoomBookingsByCalendar());
			ListIterator<RoomBooking> iter = getRoomBookings().listIterator(index);
			end.add(Calendar.DAY_OF_YEAR, 1);
			RoomBooking rb = iter.next();
			do {
				for (int i = cal.get(Calendar.HOUR_OF_DAY); i < rb.getTimeBlock().getStartTime()
						.get(Calendar.HOUR_OF_DAY); i++) {
					displayBooking(cal);
					cal.add(Calendar.HOUR_OF_DAY, 1);
				}
				System.out.print(rb + "\n");
				cal.add(Calendar.HOUR_OF_DAY, rb.getTimeBlock().duration());

			} while (iter.hasNext() && (rb = iter.next()).getTimeBlock().getStartTime().get(Calendar.DAY_OF_YEAR) < end
					.get(Calendar.DAY_OF_YEAR));

		}
		if (cal.get(Calendar.HOUR_OF_DAY) < 23) {
			do {
				displayBooking(cal);
				cal.add(Calendar.HOUR_OF_DAY, 1);
			} while (cal.get(Calendar.HOUR_OF_DAY) < 23);

		}

	}

	/**
	 * This provides the functionality to take the list of bookings and save them
	 * into a file for future use. It attempts to open the path to the file(which is
	 * in the default current directory of the running application). If the file
	 * doesn't exist it tries to create one. If both of those fail we return the
	 * failed state. Otherwise a stream is opened that writes each
	 * {@link cst8284.asgmt3.roomScheduler.RoomBooking} stored in the list to the
	 * file.
	 * 
	 * @return The result of the save(true or false if it failed).
	 */
	private boolean saveBookingsToFile() {
		try {
			File bookingFile = new File("CurrentRoomBookings.book");

			if (!bookingFile.exists())
				if (!bookingFile.createNewFile())
					return false;
			FileOutputStream fileToSave = new FileOutputStream("CurrentRoomBookings.book");
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
	 * creates an empty list of {@link cst8284.asgmt3.roomScheduler.RoomBooking}
	 * that will be returned after attempting to load. It attempts to open the file
	 * from its default location(the current working directory of the application).
	 * It uses {@code File}s {@code canRead()} function which checks if it exists
	 * and if it can be opened and have its contents read. If it cannot be read the
	 * empty list is returned.
	 * 
	 * If the file exists and is able to be read then a stream is opened and each
	 * entry is parsed as a {@link cst8284.asgmt3.roomScheduler.RoomBooking} object
	 * and attempted to be added to the list until the file throws the EOF error
	 * which lets it know that there is nothing left to be read.
	 * 
	 * Another other unforeseen errors are returned as null to be handled
	 * appropriately.
	 * 
	 * 
	 * @return The list containing any loaded entries(if found). Null otherwise if
	 *         any other errors occur.
	 */
	private ArrayList<RoomBooking> loadBookingsFromFile() {
		ArrayList<RoomBooking> bookingList = new ArrayList<>();
		try {
			File bookingFile = new File("CurrentRoomBookings.book");
			if (!bookingFile.canRead())
				return bookingList;
			FileInputStream loadData = new FileInputStream("CurrentRoomBookings.book");
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
	 * {@link cst8284.asgmt3.roomScheduler.RoomBooking} object based off them. It
	 * loops continually until it gets inputs in the correct format in order to save
	 * having to retype the same values over and over again. The counter is used to
	 * ensure it gets every single input and only advances if that input has been
	 * properly taken and handled.
	 * 
	 * All inputs use either directly or indirectly the logic in
	 * {@link cst8284.asgmt3.roomScheduler.RoomBookingInputValidator} to ensure the
	 * inputed values meet expected conditions.
	 * 
	 * 
	 * @return A newly created {@link cst8284.asgmt3.roomScheduler.RoomBooking} from
	 *         containing the values just input.
	 */
	private RoomBooking makeBookingFromUserInput() {
		int inputPosition = 0;
		String[] nameSplit = new String[2];
		String phone = "", organization = "", event = "", description = "";
		Calendar start = Calendar.getInstance(), end = Calendar.getInstance();

		while (true) {
			try {
				switch (inputPosition) {
				case 0:
					nameSplit = RoomBookingInputValidator
							.processNameInput(getResponseTo("Enter Client Name(as FirstName LastName):"));
					inputPosition++;
					break;
				case 1:
					phone = RoomBookingInputValidator
							.processPhoneNumberInput(getResponseTo("Phone Number (e.g. 613-555-1212): "));
					inputPosition++;
					break;
				case 2:
					organization = getResponseTo("Organization (optional): ");
					inputPosition++;
					break;
				case 3:
					event = getResponseTo("Enter event category: ");
					inputPosition++;
					break;
				case 4:
					description = getResponseTo("Enter detailed description of event: ");
					inputPosition++;
					break;
				case 5:
					start = makeCalendarFromUserInput(null, true);
					int endTime = Integer.parseInt(processTimeString(getResponseTo("End Time: ")));
					end = (Calendar) start.clone();
					end.set(Calendar.HOUR_OF_DAY, endTime);
					end = RoomBookingInputValidator.compareBookingTime(start, end);
					inputPosition++;
					break;
				default:
					return new RoomBooking(new TimeBlock(start, end),
							new ContactInfo(nameSplit[0], nameSplit[1], phone, organization),
							new Activity(event, description));
				}
			} catch (BadRoomBookingException e) {
				System.out.println("\n" + e.getHeader() + "\n" + e.getMessage() + "\n");
			}

		}

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
	private Calendar makeCalendarFromUserInput(Calendar cal, boolean requestHour) {
		if (cal == null) {
			cal = new Calendar.Builder().build();
			cal.setTime(RoomBookingInputValidator.checkDateFormat(getResponseTo("Event Date(entered as DDMMYYYY):")));
		}

		if (requestHour)
			cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(processTimeString(getResponseTo("Start Time: "))));

		return cal;
	}

	/**
	 * The method used for all retrievals of input from a user. It takes a message
	 * to give them to provide context for the input, validates what is entered via
	 * the keyboard and returns a {@code String} with that value to be stored
	 * 
	 * @param s The context for the retrieval of the users input.
	 * 
	 * @return Validated string with the users input.
	 */
	private static String getResponseTo(String s) {
		System.out.print(s);
		if (s.toLowerCase().contains("organization")) {
			return (scan.nextLine());
		}
		return RoomBookingInputValidator.processGeneralInput(scan.nextLine());
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
	private static String processTimeString(String t) {
		int fixPM = (t.toLowerCase().contains("p")) ? 12 : 0;
		String fixed = (t.length() >= 2) ? t.substring(0, 2) : t.substring(0, 1);
		return Integer.toString(Integer.parseInt(fixed.trim().replace(":", "")) + fixPM);
	}

	/**
	 * Any time the list of the created bookings is needed this should be called to
	 * access it.
	 * 
	 * @return A reference to the current list of bookings.
	 */
	private ArrayList<RoomBooking> getRoomBookings() {
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
	private Room getRoom() {
		return room;
	}

}
