import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;
public class PrioritisingQueueTest {

	@Test
	public void empty() {
		PrioritisingQueue<Critter> pQueue = new PrioritisingQueue<>(new CritterComparator());
		assertFalse(pQueue.contains(new Critter("firstName", "lastName", 12)));
		assertNull(pQueue.peek());
		assertNull(pQueue.poll());
		assertNull(pQueue.remove(new Critter("firstName", "lastName", 12)));
		assertEquals(0, pQueue.size());
	}

	@Test
	public void add() {
		PrioritisingQueue<Critter> pQueue = new PrioritisingQueue<>(new CritterComparator());
		int highestPriority = Integer.MAX_VALUE;
		for (int i = 0; i < 1000; i++) {
			Critter c = new Critter("firstName_" + i, "lastName_" + i, generateRandomAge());
			if (c.getAge() < highestPriority) {
				highestPriority = c.getAge();
			}
			pQueue.add(c);

			assertTrue(pQueue.contains(c));
			assertEquals(highestPriority, pQueue.peek().getAge());
			assertEquals(i + 1, pQueue.size());
		}
	}

	@Test
	public void clear() {
		PrioritisingQueue<Critter> pQueue = new PrioritisingQueue<>(new CritterComparator());
		for (int i = 0; i < 10; i++) {
			Critter c = new Critter("firstName_" + i, "lastName_" + i, generateRandomAge());
			pQueue.add(c);
		}
		pQueue.clear();
		empty();
	}

	@Test
	public void pollSimple() {
		PrioritisingQueue<Critter> q = new PrioritisingQueue<>(new CritterComparator());
		Critter in = new Critter("Tally", "Sheep", 30);
		q.add(in);

		Critter out = q.poll();
		assertEquals(in, out);

		// Make sure queue size is empty.
		assertEquals(0, q.size());

		// Next poll should return nothing.
		assertNull(q.poll());
		assertNull(q.poll());
		assertNull(q.poll());
		assertNull(q.peek());
		assertNull(q.peek());
		assertNull(q.peek());
	}

	@Test
	public void tempPollProblemRepro() {
		PrioritisingQueue<Critter> q = new PrioritisingQueue<>(new CritterComparator());
		q.add(new Critter("a", "a", 4));
		q.add(new Critter("a", "a", 2));
		q.add(new Critter("a", "a", 1));
		assertEquals(1, q.poll().getAge());
		assertEquals(2, q.poll().getAge());

		assertEquals(4, q.poll().getAge());
	}

	@Test
	public void poll() {
		PrioritisingQueue<Critter> pQueue = new PrioritisingQueue<>(new CritterComparator());
		for (int i = 0; i < 1000; i++) {
			Critter c = new Critter("firstName_" + i, "lastName_" + i, generateRandomAge());
			pQueue.add(c);
		}
		for (int i = 0; i < 1000; i++) {
			Critter c = pQueue.head.getMinLeft().getObj();
			assertEquals(c, pQueue.poll());
		}
	}

	@Test
	public void removeSimple() {
		PrioritisingQueue<Critter> pQueue = new PrioritisingQueue<>(new CritterComparator());
		Critter critter1 = new Critter("firstName_1", "lastName_1", 1);
		Critter critter5 = new Critter("firstName_5", "lastName_5", 5);

		pQueue.remove(critter1);
		assertFalse(pQueue.contains(critter1));

		pQueue.add(critter1);
		assertTrue(pQueue.contains(critter1));
		pQueue.remove(critter1);
		assertEquals(0, pQueue.size());
 		assertFalse(pQueue.contains(critter1));

		pQueue.add(critter1);
		pQueue.add(critter5);
		pQueue.remove(critter1);
		assertFalse(pQueue.contains(critter1));
		assertTrue(pQueue.contains(critter5));


	}

	@Test
	public void remove() {
		PrioritisingQueue<Critter> pQueue = new PrioritisingQueue<>(new CritterComparator());
		ArrayList<Critter> critterList = new ArrayList<>();

		for (int i = 0; i < 1000; i++) {
			Critter c = new Critter("firstName_" + i, "lastName_" + i, generateRandomAge());
			pQueue.add(c);
			critterList.add(c);
		}

		Collections.shuffle(critterList);
		for (int i = 0; i < critterList.size(); i++) {
			Critter listCritter = critterList.get(i);
			int sizeBefore = pQueue.size();

			pQueue.remove(listCritter);
			int sizeAfter = pQueue.size();
			for (int j = i + 1; j < critterList.size(); j++) {
				assertTrue(pQueue.contains(critterList.get(j)));
			}
		}
		assertEquals(0, pQueue.size());
	}

	public int generateRandomAge() {
		return (int)(Math.random() * (101));
	}

	private final Random rand = new Random();

	@Test
	public void exhaustiveTest() {
		// For many different tree sizes, do a suite of random tests.
		for (int n=1; n<15; n++) {
			// Run each tree size many times.
			for (int i=0; i<1000; i++) {
				testWithTreeSize(n);
			}
		}
	}

	private void testWithTreeSize(int n) {
		PrioritisingQueue<Long> q = new PrioritisingQueue<>(Long::compare);
		long lowestVal = Long.MAX_VALUE;
		// Add elements.
		for (int i=0; i<n; i++) {
			long a = Math.abs(rand.nextLong());
			if (a < lowestVal) {
				lowestVal = a;
			}
			// Add.
			q.add(a);

			// Check size.
			assertEquals(i + 1, q.size());

			// Check other things...
			assertEquals(lowestVal, (long)q.peek());
		}

		// Remove elements, alternating first/last.
		for (int i=0; i<n; i++) {
			if (i % 2 == 0) {
				q.poll();
			} else {
				q.remove(q.head.getMaxRight().getObj());
			}
			assertEquals(n - i - 1, q.size());
		}

		// Finally, ensure empty.
		assertEquals(0, q.size());
	}
}


class Critter {
	private final String firstName;
	private final String lastName;
	private final int age;

	Critter(String firstName, String lastName, int age) {
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Critter critter = (Critter) o;
		return age == critter.age &&
				Objects.equals(firstName, critter.firstName) &&
				Objects.equals(lastName, critter.lastName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, lastName, age);
	}
}

class CritterComparator implements Comparator<Critter> {
	@Override
	public int compare(Critter c1, Critter c2) {
		if (c1.getAge() < c2.getAge()) {
			return -1;
		}
		if (c1.getAge() == c2.getAge()) {
			return 0;
		}
		return 1;
	}
}