// Comparator lets us sort objects based on multiple data members. For example, if we had an Author class, we could
// use comparator to sort based e.g. last name, age, number of books etc.
// this is in contrast to classes which implement the Comparable interface where we can only sort based on one data member (e.g. age)
// Comparator is from the Collections framework - and we can create as many as we want
// Comparator is external to the objects we are comparing (unlike Comparable which compares itself with another object of same type)

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ComparatorPlay {
	private final String firstName;
	private final String lastName;
	private final int score;

	ComparatorPlay(String firstName, String lastName, int score) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.score = score;
	}

	public String getFirstName () {
		return firstName;
	}

	public String getLastName () {
		return lastName;
	}

	public int getScore () {
		return score;
	}

	public static void main(String[] args) {
		ArrayList<ComparatorPlay> list = new ArrayList<>();
		list.add(new ComparatorPlay("gubby","existergubby", 80));
		list.add(new ComparatorPlay("worm","wormer", 90));
		list.add(new ComparatorPlay("tally","sheep", 98));
		list.add(new ComparatorPlay("danny","danner", 62));
		list.add(new ComparatorPlay("happyface","round", 35));
		list.add(new ComparatorPlay("doggubby","woofwoof", 91));

		System.out.println("UNSORTED LIST:");
		System.out.println("--------------------------------");

		for (ComparatorPlay cp : list) {
			System.out.println(cp.toString());
		}
		System.out.println();


		// use Collections.sort() to sort by firstName
		// Collections.sort() takes a list and a Comparator
		// it uses the compare method we have overridden to sort the element in the list
		System.out.println("LIST SORTED BY FIRST NAME:");
		System.out.println("--------------------------------");
		Collections.sort(list, new FirstNameComparator());
		for (ComparatorPlay cp : list) {
			System.out.println(cp.toString());
		}
		System.out.println();



		// use Collections.sort() to sort by lastName
		System.out.println("LIST SORTED BY LAST NAME:");
		System.out.println("--------------------------------");
		Collections.sort(list, new LastNameComparator());
		for (ComparatorPlay cp : list) {
			System.out.println(cp.toString());
		}
		System.out.println();


		// use Collections.sort() to sort by lastName
		System.out.println("LIST SORTED BY TEST SCORE:");
		System.out.println("--------------------------------");
		Collections.sort(list, new ScoreComparator());
		for (ComparatorPlay cp : list) {
			System.out.println(cp.toString());
		}
		System.out.println();
	}

	@Override
	public String toString() {
		return "Name: " + this.getFirstName() + " " + this.getLastName() + ", Score: " + this.getScore();
	}
}


// To use comparator, we need to write classes for each way we wish to compare the objects.
// the compare method takes TWO objects that we wish to compare

// For Strings, we use the compareTo method (String already implements comparable)
class FirstNameComparator implements Comparator<ComparatorPlay> {
	@Override
	public int compare(ComparatorPlay cp1, ComparatorPlay cp2){
		return cp1.getFirstName().compareTo(cp2.getFirstName());
	}
}

class LastNameComparator implements Comparator<ComparatorPlay> {
	@Override
	public int compare(ComparatorPlay cp1, ComparatorPlay cp2){
		return cp1.getLastName().compareTo(cp2.getLastName());
	}
}

class ScoreComparator implements Comparator<ComparatorPlay> {
	@Override
	public int compare(ComparatorPlay cp1, ComparatorPlay cp2) {
		if (cp1.getScore() < cp2.getScore()) {
			return -1;
		}
		if (cp1.getScore() == cp2.getScore()) {
			return 0;
		}
		return 1;
	}
}
