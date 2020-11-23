public final class LinkedList<T> {

	private DannyNode head;

	final void add(T value) {
		DannyNode n = new DannyNode(value);
		if (head == null) {
			head = n;
		} else {
			head.addEnd(n);
		}
	}

	final void removeFirst() {
		if (head == null) {
			throw new IllegalStateException("List is empty");
		}
		if (head.next == null) {
			head = null;
		} else {
			head = head.next;
		}
	}

	final void removeLast() {
		if (head == null) {
			throw new IllegalStateException("List is empty");
		}
		if (head.next == null) {
			head = null;
		} else {
			head.removeLast();
		}
	}

	final void removeAll() {
		head = null;
	}

	final int count() {
		if (head == null) {
			return 0;
		}
		return head.count();
	}


	private final class DannyNode {
		final T value;
		DannyNode next;

		DannyNode(T value) {
			this.value = value;
		}

		private void addEnd(DannyNode n) {
			if (this.next == null) {
				this.next = n;
			} else {
				next.addEnd(n);
			}
		}

		private void removeLast() {
			if (next.next == null) {
				next = null;
			} else {
				next.removeLast();
			}
		}

		private int count() {
			if (next == null) {
				return 1;
			}
			return 1 + next.count();
		}
	}
}
