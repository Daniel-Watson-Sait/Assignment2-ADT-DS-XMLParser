package implementations;

import java.lang.reflect.Array;
import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;

/**
 * An array implementation of the ListADT interface
 * 
 * This list stores elements in an array that can automatically
 * double it's capacity when it becomes full.
 * 
 * @param <E> the type of element stored in the list
 */
public class MyArrayList<E> implements ListADT<E> {

    private static final int DEFAULT_CAPACITY = 10;

    private E[] data;
    private int size;

    /**
     * Constructs an empty list with the default initial capacity
     */
    @SuppressWarnings("unchecked")
    public MyArrayList() {
        data = (E[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }
    
    /**
     * Doubles capacity when array is full
     */
    @SuppressWarnings("unchecked")
    private void checkCapacity() {
    	if (size < data.length) {
    		return;
    	}
    	
    	E[] temp = (E[]) new Object[data.length * 2];
    	
    	System.arraycopy(data, 0, temp, 0, size);
    	
    	data = temp;
    }

    /**
     * Returns the number of elements currently stored in the list
     * 
     * @return the number of elements in the list
     */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Removes all elements from list
	 */
	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			data[i] = null;
		}
		size = 0;
	}

	/**
	 * Inserts an element at specified index
	 * 
	 * @param index the position to insert the element at
	 * @param toAdd the element to add
	 * @return true if the element is added successfully
	 * 
	 * @throws NullPointerException if element is null
	 * @throws IndexOutOfBoundsException if index is invalid
	 */
	@Override
	public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
		if (toAdd == null) {
			throw new NullPointerException();
		}
		
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		
		checkCapacity();
		
		for (int i = size; i > index; i--) {
			data[i] = data[i - 1];
		}
		
		data[index] = toAdd;
		size++;
		
		return true;
	}

	/**
	 * Appends an element to the end of the list
	 * 
	 * @param toAdd the element to add
	 * @return true if the element is added successfully
	 * 
	 * @throws NullPointerException if element is null
	 */
	@Override
	public boolean add(E toAdd) throws NullPointerException {
		if (toAdd == null) {
			throw new NullPointerException();
		}
		
		checkCapacity();
		
		data[size] = toAdd;
		size++;
		
		return true;
	}

	/**
	 * Appends all elements from the specified list to this list
	 * 
	 * @param toAdd the list containing elements to add
	 * @return true if all elements are add successfully
	 * 
	 * @throws NullPointerException if list is null
	 */
	@Override
	public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
		if (toAdd == null) {
			throw new NullPointerException();
		}
		
		Iterator<? extends E> it = toAdd.iterator();
		
		while (it.hasNext()) {
			add(it.next());
		}
		return true;
	}

	/**
	 * Returns the element at the specified index.
	 * 
	 * @param index the index of the element to retrieve
	 * @return the element at specified index
	 * 
	 * @throws IndexOutOfBoundsException if index is invalid
	 */
	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		return data[index];
	}

	/**
	 * Removes and returns element at specified index
	 * 
	 * @param index the index of the element to remove
	 * @return the removed element
	 * 
	 * @throws IndexOutOfBoundsException if index is invalid
	 */
	@Override
	public E remove(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		E removed = data[index];
		
		for (int i = index; i < size - 1; i++) {
			data[i] = data[i + 1];
		}
		
		data[size - 1] = null;
		size--;
		
		return removed;
	}

	/**
	 * Removes the first of specified element
	 * 
	 * @param toRemove the element to remove
	 * @return the removed element; null if not found
	 * 
	 * @throws NullPointerException if element is null
	 */
	@Override
	public E remove(E toRemove) throws NullPointerException {
		if (toRemove == null) {
			throw new NullPointerException();
		}
		
		for (int i = 0; i < size; i++) {
			if (data[i].equals(toRemove)) {
				return remove(i);
			}
		}
		return null;
	}

	/**
	 * Replaces the element at the specified index.
	 * 
	 * @param index the index of the element to replace
	 * @param toChange the new element
	 * @return the element previously stored at index
	 * 
	 * @throws NullPointerException if element is null
	 * @throws IndexOutOfBoundsException if index is invalid
	 */
	@Override
	public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
		if (toChange == null) {
			throw new NullPointerException();
		}
		
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		E old = data[index];
		data[index] = toChange;
		
		return old;
	}

	/**
	 * Determines if list is empty
	 * 
	 * @return true if empty; else false
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Determines whether the specified element exists in the list.
	 * 
	 * @param toFind the element to search for
	 * @return true if element is found; else false
	 * 
	 * @throws NullPointerException if element is null
	 */
	@Override
	public boolean contains(E toFind) throws NullPointerException {
		if (toFind == null) {
			throw new NullPointerException();
		}
		
		for (int i = 0; i < size; i++) {
			if (data[i].equals(toFind)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Copies the elements of the list into the specified array.
	 * 
	 * @param toHold the array that will hold the elements
	 * @return an array containing the elements of the list
	 * 
	 * @throws NullPointerException if array is null
	 */
	@SuppressWarnings("unchecked")
	@Override
	public E[] toArray(E[] toHold) throws NullPointerException {
		if (toHold == null) {
			throw new NullPointerException();
		}
		
		if (toHold.length < size) {
			toHold = (E[]) Array.newInstance(toHold.getClass().getComponentType(), size);
		}
		
		for (int i = 0; i < size; i++) {
			toHold[i] = data[i];
		}
		
		if (toHold.length > size) {
			toHold[size] = null;
		}
		
		return toHold;
	}

	/**
	 * Returns an array containing all elements in the list.
	 * 
	 * @return array the array containing the elements of the list
	 */
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		
		for (int i = 0; i < size; i++) {
			array[i] = data[i];
		}
		return array;
	}

	/**
	 * Returns an iterator for traversing the elements in the list
	 * @return an iterator over the elements in the list
	 */
	@Override
	public Iterator<E> iterator() {
		return new  ArrayIterator();
	}
	
	/**
	 * Iterator implementation for MyArrayList
	 */
	private class ArrayIterator implements Iterator<E> {

		private int current;
		
		public ArrayIterator() {
			current = 0;
		}
		
		@Override
		public boolean hasNext() {
			return current < size;
		}

		@Override
		public E next() throws NoSuchElementException {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return data[current++];
		}
		
	}
}