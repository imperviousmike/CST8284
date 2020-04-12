package cst8284.asgmt4.roomScheduler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextField;

/**
 * {@code RoomBookingInputValidator} is used to consolidate all error checking
 * logic as it pertains to the inputting of values during the creation of a room
 * booking. This is used for easy troubleshooting and meant to be expandable for
 * more thorough checking.
 * 
 * @version 2.0
 * @author Michael Daly
 * 
 */

public final class RoomBookingInputValidator {

	/**
	 * Constructor to block the creation of RoomBookingInputValidator objects.
	 * 
	 */
	private RoomBookingInputValidator() {
	}

	/**
	 * When creating or changing a booking the user should not be allowed to enter
	 * times that end before they start or at the same time. This method leverages
	 * Calendars comparator to do a most of the work. compareTo() gives a 0 for the
	 * same time, -1 if its before, and 1 if its after. If the end compared to the
	 * start returns a value of -1 or 0 we throw the relevant error as this means
	 * our end time breaks the agreement.
	 * 
	 * @param start The given start time for the booking.
	 * @param end   The given end time for the booking.
	 * @param text  The textfield to be highlighted if an error is found
	 * 
	 * @return The parameter passed end time is returned, meaning it is allowed to
	 *         be stored since it fits within the rules.
	 */
	public static Calendar compareBookingTime(Calendar start, Calendar end, JTextField text) {

		switch (end.compareTo(start)) {
		case -1:
			throw new BadRoomBookingException("End time precedes start time",
					"The room booking start time must occur before the end time of the room booking.", text);
		case 0:
			throw new BadRoomBookingException("Zero time room booking",
					"Start and end time of the room booking are the same. The minimum time for a room booking is one hour.",
					text);
		}

		return end;

	}

	/**
	 * This method checks the input and if its valid return it to be stored. When a
	 * user inputs a value they should not be allowed to enter empty or null values.
	 * 
	 * @param text The {@code JTextField} with the input from the user.
	 * 
	 * @return The validated input {@code String} to be set.
	 */
	public static String processGeneralInput(JTextField text) {

		String input = text.getText();

		if (input == null) {
			throw new BadRoomBookingException("Null value entered",
					"An attempt was made to pass a null value to a variable.", text);
		}

		if (input.trim().isEmpty()) {
			throw new BadRoomBookingException("Missing value", "Missing an input value.", text);
		}

		return input;
	}

	/**
	 * This method validates an inputed date from a {@code JTextField} to ensure it
	 * fits the proper format. {@code DateFormat} is used to parse the String to
	 * match the format expected. setLenient() is set to false to ensure that the
	 * String only has the exact amount of characters entered.
	 * 
	 * @param text The date entered by the user.
	 * 
	 * @return The validated date.
	 */
	public static Date checkDateFormat(JTextField text) {
		processGeneralInput(text);
		String input = text.getText();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		dateFormat.setLenient(false);
		Date date;
		try {
			date = dateFormat.parse(input);
		} catch (ParseException pE) {
			throw new BadRoomBookingException("Bad Calendar format",
					"Bad calendar date was entered. The correct format is DDMMYYYY.", text);
		}
		return date;
	}

	/**
	 * This method processes the name input by the user and checks if it fits the
	 * proper rules. The name should be input FirstName Lastname so it attempts to
	 * split at a space. If the split is greater or less than a size of 2(first,
	 * last) than the error with the proper messaging is thrown. Each name should
	 * not have more than 30 characters and if it is an error is thrown. Names
	 * should only contain these specific characters {@code A-Z,a-z,.,-,'}. A regex
	 * is used matching those characters and if either name has any characters not
	 * inside those limits than an error is thrown.
	 * 
	 * @param text The {@code JTextField} with the input from the user.
	 * 
	 * @return A {@code String} array of the split input(first name, last name) that
	 *         has been validated.
	 */
	public static String[] processNameInput(JTextField text) {
		processGeneralInput(text);
		String[] firstLastName = text.getText().split("\\s");

		if (firstLastName.length != 2)
			throw new BadRoomBookingException("Invalid name input format", "Input must be FirstName LastName only",
					text);

		for (String s : firstLastName) {
			if (s.length() > 30) {
				throw new BadRoomBookingException("Name length exceeded",
						"The first or last name exceeds the 30 character maximum allowed.", text);
			}
			// [Capital A-Z || lower case a-z ||. || - || '] match 1 or more occurrence of
			// that pattern
			if (!checkPatternMatch("[A-Za-z\\.\\-\\']+", s)) {
				throw new BadRoomBookingException("Name contains illegal characters",
						"A name cannot include characters other than alphabetic characters, the dash (-), the period (.), and the apostrophe (‘).",
						text);
			}
		}

		return firstLastName;
	}

	/**
	 * This validates the users input for a phone number. If the number is larger
	 * than 12 {@code (111-111-1111)} than it does not fit the format. If its within
	 * the character limit than its checked against a regex that matches the pattern
	 * mentioned previously. If either of these cases are broken an error is thrown.
	 * 
	 * @param text The {@code JTextField} with the input from the user.
	 * 
	 * @return The passed input that is now validated.
	 */
	public static String processPhoneNumberInput(JTextField text) {
		processGeneralInput(text);
		String input = text.getText();
		if (input.length() > 12 || !checkPatternMatch("[0-9]{3}\\-[0-9]{3}\\-[0-9]{4}", input))
			throw new BadRoomBookingException("Bad telephone number",
					"The telephone number must be a 10-digit number,separated by ‘-‘ in the form, e.g. 613-555-1212.",
					text);
		return input;
	}

	/**
	 * To avoid repeated code this method takes a regex(pattern) and the input to
	 * check it against. It uses the {@code Pattern} which can create a compiled
	 * version of the regex and uses the {@code Matcher} class to match the input
	 * strings entirety against the pattern.
	 * 
	 * @param pattern The expected pattern of the input(regex).
	 * @param input   The {@code String} to match it against.
	 * 
	 * @return If the input matches it returns true, if anything does not it returns
	 *         false.
	 */
	private static boolean checkPatternMatch(String pattern, String input) {
		Pattern compiledPattern = Pattern.compile(pattern);
		Matcher match = compiledPattern.matcher(input);
		return match.matches();
	}

}
