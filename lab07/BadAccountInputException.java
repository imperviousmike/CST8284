package cst8284.lab07;

public class BadAccountInputException extends RuntimeException {


	private static final long serialVersionUID = 1L;

	BadAccountInputException() {
		this("�Bad input: value entered is incorrect");
	}

	BadAccountInputException(String errorString) {
		super(errorString);
	}

}
