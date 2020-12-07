import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Comparator;

public class PrioritisingQueue<T> implements DannyPriorityQueue<T> {
	private final Comparator<T> comparator;
	QueueNode head = null;

	PrioritisingQueue(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	@Override
	public void add(T obj) {
		QueueNode node = new QueueNode(obj);
		if (head == null) {
			head = node;
		} else {
			head.addNext(node);
		}
	}

	@Override
	public boolean contains(T obj) {
		return head != null && head.checkContains(obj);
	}

	@Override
	@Nullable
	public T peek() {
		return head == null ? null : head.getMinLeft().getObj();
	}

	@Override
	@Nullable
	public T poll() {
		return head == null ? null : remove(head.getMinLeft().getObj());
	}

	@Override
	@Nullable
	public T remove(T obj) {
		// PRIORITY QUEUE IS EMPTY
		if (head == null) {
			return null;
		}
		QueueNode targetNode = head.getNode(obj);

		// PRIORITY QUEUE DOES NOT CONTAIN ITEM TO BE DELETED
		if (targetNode == null) {
			return null;
		}

		// ITEM TO BE DELETED IS IN HEAD NODE
		if (targetNode.checkEqualPriority(head.getObj())) {
			return removeHead();
		}

		QueueNode parentNode = head.getParentNode(obj);
		T t = targetNode.getObj();

		// TARGET NODE HAS CHILDREN OF SAME PRIORITY
		if (targetNode.middle != null) {
			targetNode.deleteThisContainingSamePriority();
			return t;
		}

		// TARGET NODE IS A LEAF
		if (targetNode.isLeaf()) {
			assert(parentNode != null); // can't be null because we know target node is not head - node must have parent
			if (parentNode.right != null && parentNode.right.checkEqualPriority(obj)) {
				parentNode.right = null;
			} else if (parentNode.left != null && parentNode.left.checkEqualPriority(obj)) {
				parentNode.left = null;
			}
			return t;
		}

		// TARGET NODE HAS CHILDREN IN ONLY ONE DIRECTION
		if (targetNode.left == null || targetNode.right == null) {
			assert(parentNode != null); // can't be null because we know target node is not head - node must have parent
			if (parentNode.right != null && parentNode.right.checkEqualPriority(obj)) {
				if (targetNode.right != null) {
					parentNode.right = targetNode.right;
				} else {
					parentNode.right = targetNode.left;
				}
			} else if (parentNode.left != null && parentNode.left.checkEqualPriority(obj)) {
				if (targetNode.right != null) {
					parentNode.left = targetNode.right;
				} else {
					parentNode.left = targetNode.left;
				}
			}
			return t;
		}

		// CHILD NODES OF BOTH HIGHER AND LOWER PRIORITY
		removeHasChildNodesLeftAndRight(targetNode);
		return t;
	}

	@Override
	public void clear() {
		head = null;
	}

	@Override
	public int size() {
		return head == null ? 0 : head.count();
	}

	private T removeHead() {
		T obj = head.getObj();
		if (head.isLeaf()) {
			head = null;
			return obj;
		}
		if (head.middle != null) {
			head.deleteThisContainingSamePriority();
		} else {
			// HEAD HAS ONLY CHILDREN IN ONLY ONE DIRECTION
			if (head.left == null) {
				head = head.right;
			} else if (head.right == null) {
				head = head.left;
			} else {
				// HEAD HAS HIGHER AND LOWER PRIORITY BRANCHES
				removeHasChildNodesLeftAndRight(head);
			}
		}
		return obj;
	}

	private void removeHasChildNodesLeftAndRight(QueueNode targetNode) {
		QueueNode terminalNode = targetNode.right.getMinLeft();

		QueueNode terminalNodeParent = head.getParentNode(terminalNode.getObj());
		targetNode.obj = terminalNode.obj;
		if (terminalNode.middle != null) {
			targetNode.middle = terminalNode.middle;
		}
		assert (terminalNodeParent != null); // can't be null because we know target node is not head - node must have parent
		if (terminalNodeParent.right != null && terminalNodeParent.right.checkEqualPriority(terminalNode.obj)) {
			terminalNodeParent.right = terminalNode.right;
		} else {
			terminalNodeParent.left = terminalNode.right;
		}
	}

	class QueueNode {
		T obj;
		QueueNode left; // nodes of lower priority than this
		QueueNode right; // nodes of higher priority than this
		QueueNode middle; // nodes of same priority as this

		QueueNode(T obj) {
			this.obj = obj;
		}

		T getObj() {
			return obj;
		}

		void addNext(QueueNode node) {
			int compVal = comparator.compare(node.getObj(), this.getObj());
			if (compVal < 0) {
				if (left == null) {
					left = node;
				} else {
					left.addNext(node);
				}
			} else {
				if (compVal == 0) {
					if (middle == null) {
						middle = node;
					} else {
						middle.addNext(node);
					}
				} else {
					if (right == null) {
						right = node;
					} else {
						right.addNext(node);
					}
				}
			}
		}

		boolean checkContains(T obj) {
			int compVal = comparator.compare(obj, this.getObj());
			if (compVal == 0) {
				return true;
			}
			if (compVal < 0) {
				return left != null ? left.checkContains(obj) : false;
			} else {
				return right != null ? right.checkContains(obj) : false;
			}
		}

		@Nullable
		QueueNode getNode(T obj) {
			int compVal = comparator.compare(obj, this.getObj());
			if (compVal == 0) {
				return this;
			}
			if (compVal < 0) {
				return left != null ? left.getNode(obj) : null;
			} else {
				return right != null ? right.getNode(obj) : null;
			}
		}

		@Nullable
		QueueNode getParentNode(T obj) {
			boolean checkLeft = left != null && left.checkEqualPriority(obj);
			boolean checkRight = right != null && right.checkEqualPriority(obj);
			int compVal = comparator.compare(obj, this.getObj());

			if (checkLeft || checkRight ) {
				return this;
			}
			if (compVal < 0) {
				return left != null ? left.getParentNode(obj) : null;
			} else {
				return right != null ? right.getParentNode(obj) : null;
			}
		}

		@Nonnull
		QueueNode getMinLeft() {
			return left == null ? this : left.getMinLeft();
		}

		@Nonnull
		QueueNode getMaxRight() {
			return right == null ? this : right.getMaxRight();
		}

		boolean isLeaf() {
			return left == null && middle == null && right == null;
		}

		boolean checkEqualPriority(T obj) {
			return comparator.compare(obj, this.obj) == 0;
		}

		int count() {
			return 1 + (left == null ? 0 : left.count())
					+ (middle == null ? 0 : middle.count())
					+ (right == null ? 0 : right.count());
		}

		void deleteThisContainingSamePriority() {
			this.obj = middle.obj;
			if (middle.middle == null) {
				middle = null;
			} else {
				middle.deleteThisContainingSamePriority();
			}
		}
	}
}

