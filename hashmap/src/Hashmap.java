

public final class Hashmap<K, V> {
	private final Object[] map = new Object[8];
	public final static String keyNotPresent = "Map does not contain key, ";

	void addNodeEnd(K key, V value) {
		if (checkContains(key)) {
			// update value
			MapNode n = getMapNode(key);
			n.getMapNode(key).value = value;
		} else {
			int pos = hash(key);
			MapNode node = new MapNode(key, value);
			if (map[pos] == null) {
				map[pos] = node;
			} else {
				MapNode head = (MapNode)map[pos];
				head.addEnd(node);
			}
		}
	}

	void addNodeStart(K key, V value) {
		if (checkContains(key)) {
			// update value
			MapNode n = getMapNode(key);
			n.getMapNode(key).value = value;
		} else {
			int pos = hash(key);
			MapNode node = new MapNode(key, value);
			if (map[pos] == null) {
				map[pos] = node;
			} else {
				MapNode head = (MapNode)map[pos];
				node.next = head;
				map[pos] = head;
			}
		}
	}

	boolean checkContains(K key) {
		int pos = hash(key);
		if (map[pos] == null) {
			return false;
		}
		MapNode n = (MapNode)map[pos];
		return n.containsKey(key);
	}


	V getValue(K key) {
		int pos = hash(key);
		if (map[pos] == null) {
			throw new IllegalStateException(keyNotPresent + key);
		}
		MapNode n = (MapNode)map[pos];
		return n.getValue(key);
	}

	void delete(K key) {
		if (!checkContains(key)) {
			throw new IllegalStateException(keyNotPresent + key);
		}
		int pos = hash(key);
		MapNode head = (MapNode)map[pos];
		if (head.key == key) {
			if (head.next == null) {
				head = null;
			} else {
				head.next = head;
			}
		} else {
			head.deleteDownstream(key);
		}
	}

	MapNode getMapNode(K key) {
		if (!checkContains(key)) {
			throw new IllegalStateException(keyNotPresent + key);
		}
		int pos = hash(key);
		MapNode n = (MapNode)map[pos];
		return n.getMapNode(key);
		}

	private int hash(K key) {
		return hash(key) % 8;
	}


	final class MapNode {
		K key;
		V value;
		MapNode next;

		MapNode(K key, V value) {
			this.key = key;
			this.value = value;
		}

		boolean containsKey(K key) {
			if (this.key == key) {
				return true;
			} else {
				if (next == null) {
					return false;
				} else {
					return next.containsKey(key);
				}
			}
		}

		V getValue(K key) {
			MapNode n = this.getMapNode(key);
			return n.value;
		}

		void addEnd(MapNode n) {
			if (this.next == null) {
				next = n;
			} else {
				next.addEnd(n);
			}
		}

		void deleteDownstream(K key) {
			if (next == null) {
				throw new IllegalStateException(keyNotPresent + key);
			}
			if (next.key == key) {
				if (next.next == null) {
					next = null;
				} else {
					this.next = next.next;
				}
			} else {
				next.deleteDownstream(key);
			}
		}

		MapNode getMapNode(K key) {
			if (this.key == key) {
				return this;
			}
			if (next == null) {
				throw new IllegalStateException(keyNotPresent + key);
			}
			return next.getMapNode(key);
		}
	}
}
