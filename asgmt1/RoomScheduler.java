package cst8284.asgmt1;

import java.util.Calendar;
import java.util.Scanner;

/*
    Course Name: CST8284
    Student Name: Michael Daly
    Class name: Object Oriented Programming (Java)
    Date: February 9th, 2020
*/

public class RoomScheduler {

	private static final int ENTER_ROOM_BOOKING = 1, DISPLAY_BOOKING = 2, DISPLAY_DAY_BOOKINGS = 3, EXIT = 0;
	private static Scanner scan = new Scanner(System.in);
	private RoomBooking[] roomBookings = new RoomBooking[80];
	private int lastBookingIndex = 0;

	public RoomScheduler() {
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
		System.out.println(ENTER_ROOM_BOOKING + ". Enter a room booking");
		System.out.println(DISPLAY_BOOKING + ". Display a booking");
		System.out.println(DISPLAY_DAY_BOOKINGS + ". Display room bookings for the whole day");
		System.out.println(EXIT + ". Exit program");
		return Integer.parseInt(getResponseTo(""));
	}

	private void executeMenuItem(int choice) {
		System.out.println();
		switch (choice) {
		case ENTER_ROOM_BOOKING:
			RoomBooking rb = makeBookingFromUserInput();
			if (saveRoomBooking(rb))
				System.out.println("Booking time and date saved.");
			break;
		case DISPLAY_BOOKING:
			displayBooking(makeCalendarFromUserInput(null, true));
			break;
		case DISPLAY_DAY_BOOKINGS:
			displayDayBookings(makeCalendarFromUserInput(null, false));
			break;
		case EXIT:
			System.out.println("Exiting Room Booking Application");
			break;
		default:
			System.out.println("Enter one of the options available above  ");
		}
		System.out.println();

	}

	private boolean saveRoomBooking(RoomBooking booking) {
		if (booking != null) {
			Calendar calIter = (Calendar) booking.getTimeBlock().getStartTime().clone();
			int iter = 0;
			do {
				calIter.set(Calendar.HOUR_OF_DAY, calIter.get(Calendar.HOUR_OF_DAY) + iter++);
				if (findBooking(calIter) != null)
					return false;
			} while (iter < booking.getTimeBlock().duration());
		}
		getRoomBookings()[getBookingIndex()] = booking;
		setBookingIndex(getBookingIndex() + 1);
		return true;

	}

	private RoomBooking displayBooking(Calendar cal) {
		RoomBooking rb = findBooking(cal);
		System.out.println((rb == null) ? String.format("No booking scheduled between %d:00 and %d:00",
				cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.HOUR_OF_DAY) + 1) : rb.toString());
		return rb;
	}

	private void displayDayBookings(Calendar cal) {
		int increment;

		do {
			RoomBooking rb = displayBooking(cal);
			increment = (rb != null) ? rb.getTimeBlock().duration() : 1;
			cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + increment);

		} while (cal.get(Calendar.HOUR_OF_DAY) < 23);
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
			cal = new Calendar.Builder().build();
			String date = getResponseTo("Event Date(entered as DDMMYYYY):");
			cal.set(Integer.parseInt(date.substring(4, 8)), Math.abs(Integer.parseInt(date.substring(2, 4))) - 1,
					Integer.parseInt(date.substring(0, 2)));
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
			if (rb != null) {
				if (rb.getTimeBlock().overlaps(tb))
					return rb;
			}
		}

		return null;
	}

	private static String processTimeString(String t) {
		int fixPM = (t.toLowerCase().contains("p")) ? 12 : 0;
		String fixed = (t.length() >= 2) ? t.substring(0, 2) : t.substring(0, 1);
		return Integer.toString(Integer.parseInt(fixed.trim().replace(":", "")) + fixPM);
	}

	private RoomBooking[] getRoomBookings() {
		return roomBookings;
	}

	private int getBookingIndex() {
		return lastBookingIndex;
	}

	private void setBookingIndex(int lastBookingIndex) {
		this.lastBookingIndex = lastBookingIndex;
	}

}
