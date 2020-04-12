package cst8284.asgmt2.roomScheduler;

import java.io.Serializable;

/*
	Course Name: CST8284
	Student Name: Michael Daly
	Class name: Object Oriented Programming (Java)
	Date: March 2nd, 2020
*/

public class Activity implements Serializable {

	private String description, category;

	public static final long serialVersionUID = 1L;

	public Activity(String category, String description) {
		setCategory(category);
		setDescription(description);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return String.format("Event: %s\nDescription: %s", getCategory(),
				(getDescription() != null) ? getDescription() : "");
	}
}
