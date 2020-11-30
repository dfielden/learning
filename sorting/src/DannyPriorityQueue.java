/**
 * Implementations of DannyPriorityQueue store items of type <T>. Items are ordered based on a comparator that is
 * provided at queue construction time. The item with the lowest priority is the first to be removed. If multiple items
 * of the same priority are present in the queue, the first of these items that was added will be removed first.
 */
public interface DannyPriorityQueue<T> {
	/**
	Adds element of type <T> to the queue
 	*/
	void add(T obj);


	/**
	Returns True if the queue contains at least one instance of the provided element. Returns false if the queue
	does not contain the element. Equality is defined by the supplied comparator.
	 */
	boolean contains(T obj);


	/**
	Returns but does not remove the element of lowest priority in the queue. If multiple elements have the same lowest priority
	level, the first to have been added to the queue will be returned. Returns null if the queue is empty.
 	*/
	T peek();


	/**
	Removes and returns the element of lowest priority in the queue. If multiple elements have the same lowest priority
	level, the first to have been added to the queue will be removed. Returns null if the queue is empty.
 	*/
	T poll();


	/**
	Removes and returns the first instance of the element if it is present in the queue. Returns null if the queue
	does not contain the element. Equality is defined by the supplied comparator.
 	*/
	T remove(T obj);


	/**
	Removes all elements from queue.
 	*/
	void clear();


	/**
	Returns the total number of elements in the queue.
 	*/
	int size();
}
