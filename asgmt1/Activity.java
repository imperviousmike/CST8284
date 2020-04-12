package cst8284.asgmt1;

/*
    Course Name: CST8284
    Student Name: Michael Daly
    Class name: Object Oriented Programming (Java)
    Date: February 9th, 2020
*/

public class Activity {

	private String description, category;

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
