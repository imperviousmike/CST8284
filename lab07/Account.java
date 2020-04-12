package cst8284.lab07;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Account {
	private String accountNumber = "000-000000"; // branch number - customer account number
	private String firstName, lastName;
	private static final Calendar ACCOUNTSTARTDATE = Calendar.getInstance();
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public Account(String accountNumber, String firstName, String lastName, String startDate) {
		setAccountNumber(accountNumber);
		setFirstName(firstName);
		setLastName(lastName);
		setAccountStartDate(startDate);
	}

	private void setAccountNumber(String accountNumber) {
		if (accountNumber.trim().length() < accountNumber.length())
			throw new BadAccountInputException("Cannot have leading or trailing spaces in the input String");

		if (!isCheckDigitCorrect(accountNumber.split("-")[1]))
			throw new BadAccountInputException("Incorrect Account Number");

		this.accountNumber = accountNumber;
	}

	private static boolean isCheckDigitCorrect(String accountNumber) {
		int checksumTotal = 0, currentNum;
		String[] numbers = accountNumber.split("");
		for (int i = 0; i < accountNumber.split("").length - 1; i++) {
			currentNum = Integer.parseInt(numbers[i]);
			checksumTotal += (i % 2 == 0) ? currentNum * 2 : currentNum * 3;
		}

		return ((checksumTotal % 10) == Integer.parseInt(numbers[numbers.length - 1]));
	}

	private static boolean isInputNameCorrect(String name) {
		if (name.isEmpty() || name == null)
			throw new BadAccountInputException("Name field is null");

		return true;

	}

	public void setAccountStartDate(String startDate) {

		try {
			dateFormat.setLenient(false);
			Date date = dateFormat.parse(startDate);
			ACCOUNTSTARTDATE.setTime(date);
		} catch (RuntimeException rE) {
			throw new BadAccountInputException("General runtime exception thrown setting start date");

		} catch (ParseException pE) {
			throw new BadAccountInputException("Format is YYYY-MM-DD");
		}

	}

	private void setFirstName(String firstName) {
		if (isInputNameCorrect(firstName))
			this.firstName = firstName;
	}

	private void setLastName(String lastName) {
		if (isInputNameCorrect(lastName))
			this.lastName = lastName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Calendar getAccountStartDate() {
		return ACCOUNTSTARTDATE;
	}

	public String toString() {
		return "Customer name: " + getFirstName() + " " + getLastName() + "\nCustomer Account number: "
				+ getAccountNumber() + "\nAccount Created: " + getAccountStartDate().getTime().toString();
	}

}
