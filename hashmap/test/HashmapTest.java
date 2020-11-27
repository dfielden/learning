import org.junit.Test;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class HashmapTest {

	@Test
	public void emptyMap() {
		Hashmap<Integer, String> map = new Hashmap<>();
		assertTrue(map.isEmpty());
		assertEquals(0, map.numEntries());

		// Try a range of keys.
		for (int i = 0; i < 100; i++) {
			assertFalse(map.checkContains(i));
			assertNull(map.getMapNode(i));
		}
	}

	@Test
	public void isEmpty() {
		Hashmap<Integer, String> map = new Hashmap<>();
		assertTrue(map.isEmpty());
		map.addNodeEnd(1, generateString(1));
		assertFalse(map.isEmpty());
	}

	@Test
	public void ensureDeleteOnNonExistentKeyDoesntThrow() {
		// This test ensures it is safe to delete a non-existent key.
		Hashmap<Integer, String> map = new Hashmap<>();
		map.deleteIfPresent(123);
		assertFalse(map.checkContains(123));
	}

	@Test
	public void addEnd() {
		Hashmap<Integer, String> map = new Hashmap<>();

		// add values
		for (int i = 0; i < 100; i++) {
			map.addNodeEnd(i, generateString(i));
			assertFalse(map.isEmpty());
			assertEquals(i + 1, map.numEntries());

			for (int j = 0; j < 100; j++) {
				if (j <= i) {
					assertTrue(map.checkContains(j));
					assertEquals(generateString(j), map.getValue(j));
				} else {
					assertFalse(map.checkContains(j));
					assertNull(map.getValue(j));
				}
			}
		}
	}

	@Test
	public void addStart() {
		Hashmap<Integer, String> map = new Hashmap<>();
		assertTrue(map.isEmpty());
		assertEquals(0, map.numEntries());

		// add values
		for (int i = 0; i < 100; i++) {
			map.addNodeStart(i, generateString(i));
			assertFalse(map.isEmpty());
			assertEquals(i + 1, map.numEntries());

			for (int j = 0; j < 100; j++) {
				if (j <= i) {
					assertTrue(map.checkContains(j));
					assertEquals(generateString(j), map.getValue(j));
				} else {
					assertFalse(map.checkContains(j));
					assertNull(map.getValue(j));
				}
			}
		}
	}

	@Test
	public void updateValuesFromEnd() {
		Hashmap<Integer, String> map = new Hashmap<>();
		for (int i = 0; i < 100; i++) {
			map.addNodeEnd(i, generateString(i));
		}

		// update values
		for (int i = 0; i < 100; i++) {
			map.addNodeEnd(i, generateUpdatedString(i));
			assertFalse(map.isEmpty());
			assertEquals(100, map.numEntries());

			for (int j = 0; j < 100; j++) {
				if (j <= i) {
					assertTrue(map.checkContains(j));
					assertEquals(generateUpdatedString(j), map.getValue(j));
				} else {
					assertTrue(map.checkContains(j));
					assertEquals(generateString(j), map.getValue(j));
				}
			}
		}
	}

	@Test
	public void updateValuesFromStart() {
		Hashmap<Integer, String> map = new Hashmap<>();
		for (int i = 0; i < 100; i++) {
			map.addNodeStart(i, generateString(i));
		}

		// update values
		for (int i = 0; i < 100; i++) {
			map.addNodeStart(i, generateUpdatedString(i));
			assertFalse(map.isEmpty());
			assertEquals(100, map.numEntries());

			for (int j = 0; j < 100; j++) {
				if (j <= i) {
					assertTrue(map.checkContains(j));
					assertEquals(generateUpdatedString(j), map.getValue(j));
				} else {
					assertTrue(map.checkContains(j));
					assertEquals(generateString(j), map.getValue(j));
				}
			}
		}
	}

	@Test
	public void deleteValuesInOrder() {
		Hashmap<Integer, String> map = new Hashmap<>();
		for (int i = 0; i < 100; i++) {
			map.addNodeEnd(i, generateString(i));
		}

		for (int i = 100; i < 200; i++) {
			map.deleteIfPresent(i);
			assertEquals(100, map.numEntries());
		}

		for (int i = 0; i < 100; i++) {
			map.deleteIfPresent(i);
			assertEquals(100 - (i + 1), map.numEntries());
		}
	}

	@Test
	public void deleteValuesOutOfrder() {
		Hashmap<Integer, String> map = new Hashmap<>();
		for (int i = 0; i < 100; i++) {
			map.addNodeEnd(i, generateString(i));
		}

		for (int i = 50; i < 100; i++) {
			map.deleteIfPresent(i);
			assertEquals(100 - i + 49, map.numEntries());
		}
		for (int i = 0; i < 50; i++) {
			map.deleteIfPresent(i);
			assertEquals(50 - (i + 1), map.numEntries());
		}
	}

	private static String generateString(int i) {
		return "String_" + i;
	}

	private static String generateUpdatedString(int i) {
		return "UpdatedString_" + i;
	}
}
