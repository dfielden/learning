import java.util.Arrays;

	// Comparable interface is for objects that can be compared to one another. It can be used to compare objects of
	// the same type.
	// it compares THIS to another object of same type
	// Integer and String class already  implement comparable - so we can sort them with Collections.sort
	// for our own classes/objects, we need to declare that it implements Comparable
	// Any class that implements Comparable must implement the compareTo(T) method

public class ComparablePlay implements Comparable<ComparablePlay> {
	private final String firstName;
	private final String lastName;
	private final int score;

	ComparablePlay(String firstName, String lastName, int score) {
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

		// EXAMPLE WITH STRING
		String[] strings = {"gubby", "worm", "tally", "danny", "happyface", "doggubby"};
		for (int i = 0; i < strings.length; i++) {
			System.out.println(i + ") " + strings[i]);
		}
		System.out.println();

		// sort the array with Arrays.sort - which requires that all elements of array implement comparable interface
		Arrays.sort(strings);
		for (int i = 0; i < strings.length; i++) {
			System.out.println(i + ") " + strings[i]);
		}
		System.out.println("============");



		// EXAMPLE WITH COMPARABLEPLAY
		ComparablePlay a = new ComparablePlay("gubby","existergubby", 80);
		ComparablePlay b = new ComparablePlay("worm","wormer", 90);
		ComparablePlay c = new ComparablePlay("tally","sheep", 98);
		ComparablePlay d = new ComparablePlay("danny","danner", 62);
		ComparablePlay e = new ComparablePlay("happyface","round", 35);
		ComparablePlay f = new ComparablePlay("doggubby","woofwoof", 91);
		ComparablePlay[] comparables = {a, b, c, d, e, f};


		for (int i = 0; i < comparables.length; i++) {
			System.out.println(i + ") " + comparables[i]);
		}
		System.out.println("============");

		// sort the array with Arrays.sort - which requires that all elements of array implement comparable interface
		// we have ensured that ComparablePlay implements comparable interface (which itself contains only the compareTo method)
		Arrays.sort(comparables);
		for (int i = 0; i < comparables.length; i++) {
			System.out.println(i + ") " + comparables[i]);
		}
	}


	@Override
	public int compareTo(ComparablePlay a) {
		// Compares this object with the specified object for order.  Returns a
		// negative integer, zero, or a positive integer as this object is less
		// than, equal to, or greater than the specified object.


		// It is up to us to determine how we want to sort/compare. In this case we have decided to compare by score.
		if (this.getScore() < a.getScore()) {
			return -1;
		}
		if (this.getScore() == a.getScore()) {
			return 0;
		}
		return 1;
	}

	@Override
	public String toString() {
		return "Name: " + this.getFirstName() + " " + this.getLastName() + ", Score: " + this.getScore();
	}
}