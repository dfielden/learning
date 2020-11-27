public final class Hashmap<K, V> {
	private final Object[] map = new Object[8];

	public void addNodeEnd(K key, V value) {
		// update value if key already in tree
		MapNode n = getMapNode(key);
		if (n != null) {
			n.value = value;
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

	public void addNodeStart(K key, V value) {
		MapNode n = getMapNode(key);
		if (n != null) {
			n.value = value;
		} else {
			int pos = hash(key);
			MapNode node = new MapNode(key, value);
			// current bucket is empty
			if (map[pos] == null) {
				map[pos] = node;
			} else {
				// current bucket already contains a node
				MapNode head = (MapNode)map[pos];
				node.next = head;
				map[pos] = node;
			}
		}
	}

	public boolean checkContains(K key) {
		int pos = hash(key);
		if (map[pos] == null) {
			return false;
		}
		MapNode n = (MapNode)map[pos];
		return n.containsKey(key);
	}

	public boolean isEmpty() {
		for (int i = 0; i < map.length; i++) {
			if (map[i] != null) {
				return false;
			}
		}
		return true;
	}

	public int numEntries() {
		int total = 0;
		for (int i = 0; i < map.length; i++) {
			MapNode n = (MapNode)map[i];
			if (n != null) {
				total += n.countNext();
			}
		}
		return total;
	}

	public V getValue(K key) {
		int pos = hash(key);
		if (map[pos] != null) {
			MapNode n = (MapNode)map[pos];
			return n.getValue(key);
		} else {
			return null;
		}
	}

	public void deleteIfPresent(K key) {
		int pos = hash(key);
		MapNode head = (MapNode)map[pos];
		if (head == null) {
			return;
		}
		if (head.key == key) {
			if (head.next == null) {
				map[pos] = null;
			} else {
				map[pos] = head.next;
				head.next = null;
			}
		} else {
			head.deleteDownstream(key);
		}
	}

	public MapNode getMapNode(K key) {
		int pos = hash(key);
		MapNode n = (MapNode)map[pos];
		if (n != null) {
			return n.getMapNode(key);
		} else {
			return null;
		}
	}

	private int hash(K key) {
		return Math.abs(key.hashCode()) % map.length;
	}


	final class MapNode {
		final K key;
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
			if (this.key == key) {
				return this.value;
			}
			if (next == null) {
				return null;
			}
			return next.getValue(key);
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
				return;
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
			// This will never occur because we always check ifExists(key) before calling this function
			if (next == null) {
				return null;
			}
			return next.getMapNode(key);
		}

		int countNext() {
			if (next == null) {
				return 1;
			}
			return 1 + next.countNext();
		}
	}
}

