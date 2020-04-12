package cst8284.asgmt2.roomScheduler;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import firstyear.secondsemester.cst8284.asgmt2.room.Room;

/*
    Course Name: CST8284
    Student Name: Michael Daly
    Class name: Object Oriented Programming (Java)
    Date: March 2nd, 2020
*/

public class RoomScheduler {

	private static final int DISPLAY_ROOM_INFORMATION = 1, ENTER_ROOM_BOOKING = 2, DELETE_BOOKING = 3,
			CHANGE_BOOKING = 4, DISPLAY_BOOKING = 5, DISPLAY_DAY_BOOKINGS = 6, SAVE_BOOKINGS_TO_FILE = 7,
			LOAD_BOOKINGS_FROM_FILE = 8, EXIT = 0;
	private static Scanner scan = new Scanner(System.in);
	private ArrayList<RoomBooking> roomBookings;
	private Room room;

	public RoomScheduler(Room room) {
		roomBookings = loadBookingsFromFile();
		setRoom(room);
	}

	public void launch() {
		int choice;
		do {
			choice = displayMenu();
			executeMenuItem(choice);
		} while (choice != EXIT);

	}

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
			/*
			 * System.out.println(deleteBooking(makeCalendarFromUserInput(new
			 * Calendar.Builder().setDate(2020, 9, 10).build(), true)) ? "Booking deleted\n"
			 * : "Could not delete booking\n");
			 */

			System.out.println(deleteBooking(makeCalendarFromUserInput(null, true)) ? "Booking deleted\n"
					: "Could not delete booking\n");
			break;
		case CHANGE_BOOKING:
			System.out.println("Enter booking to change");
			// changeBooking(makeCalendarFromUserInput(new Calendar.Builder().setDate(2020,
			// 9, 10).build(), true));
			System.out.println(
					changeBooking(makeCalendarFromUserInput(null, true)) ? "" : "Booking not found, could not change");
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

	private void displayRoomInfo() {
		System.out.println(getRoom());
	}

	private boolean saveRoomBooking(RoomBooking booking) {

		if (booking != null) {
			Calendar calIter = (Calendar) booking.getTimeBlock().getStartTime().clone();
			int counter = 0;
			do {
				calIter.set(Calendar.HOUR_OF_DAY, calIter.get(Calendar.HOUR_OF_DAY) + counter++);
				if (findBooking(calIter) != null)
					return false;
			} while (counter < booking.getTimeBlock().duration());
		}
		getRoomBookings().add(booking);
		return true;
	}

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

	private boolean changeBooking(Calendar cal) {
		RoomBooking rb = findBooking(cal);
		if (rb != null) {
			System.out.println(rb);
			TimeBlock changedTb = new TimeBlock((Calendar) rb.getTimeBlock().getStartTime().clone(),
					(Calendar) rb.getTimeBlock().getEndTime().clone());
			RoomBooking changedRoom = new RoomBooking(changedTb, rb.getContactInfo(), rb.getActivity());
			getRoomBookings().remove(rb);
			changedRoom.getTimeBlock().getStartTime().set(Calendar.HOUR_OF_DAY,
					Integer.parseInt(processTimeString(getResponseTo("Enter New Start Time: "))));
			changedRoom.getTimeBlock().getEndTime().set(Calendar.HOUR_OF_DAY,
					Integer.parseInt(processTimeString(getResponseTo("Enter New End Time: "))));

			if (!saveRoomBooking(changedRoom)) {
				System.out.println("Found overlapping entry, could not change");
				getRoomBookings().add(rb);
			} else {
				System.out.format("Booking has been changed to:\n%s\n", changedRoom);
				return true;
			}
		}
		return false;
	}

	private RoomBooking displayBooking(Calendar cal) {
		RoomBooking rb = findBooking(cal);
		System.out.println((rb == null) ? String.format("No booking scheduled between %d:00 and %d:00",
				cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.HOUR_OF_DAY) + 1) : rb.toString());
		return rb;
	}

	private void displayDayBookings(Calendar cal) {
		int increment;
		cal.set(Calendar.HOUR_OF_DAY, 8);
		do {
			RoomBooking rb = displayBooking(cal);
			increment = (rb != null) ? rb.getTimeBlock().duration() : 1;
			cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + increment);

		} while (cal.get(Calendar.HOUR_OF_DAY) < 23);
	}

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

		}
		catch (Exception e) {
			return false;
		}
		return true;
	}

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
		}
		catch (Exception e) {
			return null;
		}
		return bookingList;

	}

	private RoomBooking makeBookingFromUserInput() {
		String[] nameSplit = getResponseTo("Enter Client Name(as FirstName LastName):").split(" ");
		String phone = getResponseTo(("Phone Number (e.g. 613-555-1212): "));
		String organization = getResponseTo("Organization (optional): ");
		String event = getResponseTo("Enter event category: ");
		String description = getResponseTo("Enter detailed description of event: ");
		Calendar start = makeCalendarFromUserInput(null, true);
		int endTime = Integer.parseInt(processTimeString(getResponseTo("End Time: ")));

		Calendar end = (Calendar) start.clone();
		end.set(Calendar.HOUR_OF_DAY, endTime);

		return new RoomBooking(new TimeBlock(start, end),
				new ContactInfo(nameSplit[0], nameSplit[1], phone, organization), new Activity(event, description));

	}

	private Calendar makeCalendarFromUserInput(Calendar cal, boolean requestHour) {

		if (cal == null) {
			String date = getResponseTo("Event Date(entered as DDMMYYYY):");
			cal = new Calendar.Builder().setDate(Integer.parseInt(date.substring(4, 8)),
					Math.abs(Integer.parseInt(date.substring(2, 4))) - 1, Integer.parseInt(date.substring(0, 2)))
					.build();
		}
		if (requestHour)
			cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(processTimeString(getResponseTo("Start Time: "))));

		return cal;
	}

	private String getResponseTo(String s) {
		System.out.print(s);
		return (scan.nextLine());
	}

	private RoomBooking findBooking(Calendar cal) {
		Calendar end = (Calendar) cal.clone();

		end.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + 1);

		TimeBlock tb = new TimeBlock(cal, end);

		for (RoomBooking rb : getRoomBookings()) {
			if (rb.getTimeBlock().overlaps(tb))
				return rb;
		}

		return null;
	}

	private static String processTimeString(String t) {
		int fixPM = (t.toLowerCase().contains("p")) ? 12 : 0;
		String fixed = (t.length() >= 2) ? t.substring(0, 2) : t.substring(0, 1);
		return Integer.toString(Integer.parseInt(fixed.trim().replace(":", "")) + fixPM);
	}

	private ArrayList<RoomBooking> getRoomBookings() {
		return roomBookings;
	}

	private void setRoom(Room room) {
		this.room = room;
	}

	private Room getRoom() {
		return room;
	}

}
