import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class BubbleSorter<T> {
	private final Comparator<T> comparator;

	BubbleSorter(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	public void sortList(ArrayList<T> list) {
		System.out.println("UNSORTED LIST:");
		printList(list);

		int sorted = 0;
		while (sorted < list.size() - 1) {
			for (int i = 0; i < list.size() - sorted - 1; i++) {
				if (comparator.compare(list.get(i), list.get(i + 1))  > 0) {
					T left = list.get(i);
					T right = list.get(i + 1);
					list.set(i, right);
					list.set(i + 1,  left);
				}
			}
			System.out.println("SORT CYCLE: " + (sorted + 1));
			printList(list);
			sorted++;
		}
	}

	public void printList(ArrayList<T> list) {
		System.out.println("-------------------------------");
		for (T obj : list) {
			System.out.println(obj.toString());
		}
		System.out.println();
	}

	public static void main(String[] args) {
		ArrayList<Person> personList = new ArrayList<>();
		personList.add(new Person("gubby","existergubby", 80));
		personList.add(new Person("worm","wormer", 90));
		personList.add(new Person("tally","sheep", 98));
		personList.add(new Person("danny","danner", 62));
		personList.add(new Person("happyface","round", 35));
		personList.add(new Person("doggubby","woofwoof", 91));

		BubbleSorter<Person> personBubbleSorter = new BubbleSorter<>(new PersonAgeComparator());
		personBubbleSorter.sortList(personList);

		//BubbleSorter<String> sorter = new BubbleSorter<>((a, b) -> a.compareTo(b));
		//sorter.sortList(Arrays.asList("z", "a", "g", "x", "b");

	}

}

class Person {
	private final String firstName;
	private final String lastName;
	private final int age;

	Person(String firstName, String lastName, int age) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getAge() {
		return age;
	}

	@Override
	public String toString() {
		return "Name: " + this.getFirstName() + " " + this.getLastName() + ", Age: " + this.getAge();
	}
}


class PersonAgeComparator implements Comparator<Person> {
	@Override
	public int compare(Person p1, Person p2) {
		if (p1.getAge() < p2.getAge()) {
			return -1;
		}
		if (p1.getAge() == p2.getAge()) {
			return 0;
		}
		return 1;
	}
}