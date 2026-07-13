package implementations;

import java.lang.reflect.Array;
import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;

public class MyArrayList<E> implements ListADT<E> {

    private static final int DEFAULT_CAPACITY = 10;

    private E[] data;
    private int size;

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

	@Override
	public int size() {
		return size;
	}

	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			data[i] = null;
		}
		size = 0;
	}

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

	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		return data[index];
	}

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

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

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

	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		
		for (int i = 0; i < size; i++) {
			array[i] = data[i];
		}
		return array;
	}

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