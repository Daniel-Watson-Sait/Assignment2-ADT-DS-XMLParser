package implementations;

import exceptions.EmptyQueueException;
import utilities.Iterator;
import utilities.QueueADT;

/**
 * Queue via MyDLL (FIFO)
 *
 * @param <E> type of element stored
 */
public class MyQueue<E> implements QueueADT<E>{
	private MyDLL<E> queue;

	/**
	 * Creates empty queue.
	 */
	public MyQueue(){
		queue=new MyDLL<>();
	}

	/**
	 * Adds element to back of queue.
	 *
	 * @param toAdd element to be added
	 * @throws NullPointerException when element is null
	 */
	@Override
	public void enqueue(E toAdd)throws NullPointerException{
		if(toAdd==null){
			throw new NullPointerException();
		}
		queue.add(toAdd);
	}

	/**
	 * Removes element from the front of queue.
	 *
	 * @return removed element
	 * @throws EmptyQueueException when queue is empty
	 */
	@Override
	public E dequeue()throws EmptyQueueException{
		if(queue.isEmpty()){
			throw new EmptyQueueException();
		}
		return queue.remove(0);
	}

	/**
	 * Returns first element without removing it.
	 *
	 * @return element 1
	 * @throws EmptyQueueException when queue is empty
	 */
	@Override
	public E peek()throws EmptyQueueException{
		if(queue.isEmpty()){
			throw new EmptyQueueException();
		}
		return queue.get(0);
	}

	/**
	 * Removes all elements from the queue.
	 */
	@Override
	public void dequeueAll(){
		queue.clear();
	}

	/**
	 * Checks if queue is empty.
	 *
	 * @return true when empty, false if not
	 */
	@Override
	public boolean isEmpty(){
		return queue.isEmpty();
	}

	/**
	 * Checks element exists in queue.
	 *
	 * @param toFind element to find
	 * @return true when found, otherwise false
	 * @throws NullPointerException when element is null
	 */
	@Override
	public boolean contains(E toFind)throws NullPointerException{
		return queue.contains(toFind);
	}

	/**
	 * Returns placement from front(no. 1) of queue.
	 *
	 * @param toFind element to find
	 * @return placement or -1 when not found
	 */
	@Override
	public int search(E toFind){
		if(toFind==null){
			throw new NullPointerException();
		}
		Iterator<E> iterator=iterator();
		int placement=1;
		while(iterator.hasNext()){
			if(iterator.next().equals(toFind)){
				return placement;
			}
			placement++;
		}
		return -1;
	}

	/**
	 * Returns iterator from front to back.
	 *
	 * @return queue iterator
	 */
	@Override
	public Iterator<E> iterator(){
		return queue.iterator();
	}

	/**
	 * Checks if two queues have same elements and order.
	 *
	 * @param that queue to compare
	 * @return true when queues are the same
	 */
	@Override
	public boolean equals(QueueADT<E> that){
		if(that==null){
			return false;
		}
		Object[] arrayThis=toArray();
		Object[] arrayThat=that.toArray();
		if(arrayThis.length!=arrayThat.length){
			return false;
		}
		for(int i=0;i<arrayThis.length;i++){
			if(!arrayThis[i].equals(arrayThat[i])){
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns elements in array.
	 *
	 * @return array of elements in queue
	 */
	@Override
	public Object[] toArray(){
		return queue.toArray();
	}

	/**
	 * Returns elements in target array.
	 *
	 * @param holder array to hold elements
	 * @return array of queue elements
	 * @throws NullPointerException when array is null
	 */
	@Override
	public E[] toArray(E[] holder)throws NullPointerException{
		if(holder==null){
			throw new NullPointerException();
		}
		return queue.toArray(holder);
	}

	/**
	 * Checks if queue is full.
	 *
	 * @return false because queue has no fixed size
	 */
	@Override
	public boolean isFull(){
		return false;
	}

	/**
	 * Returns no. of elements stored in queue.
	 *
	 * @return queue size
	 */
	@Override
	public int size(){
		return queue.size();
	}
}
