package implementations;

import utilities.StackADT;
import utilities.Iterator;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;

/**
 * An array implementation of the StackADT interface
 * 
 * This stack stores elements in LIFO order.
 * 
 * @param <E> the type of element stored in the stack
 */
public class MyStack<E> implements StackADT<E> {


    private MyArrayList<E> stack;

    /**
     * Constructs an empty stack
     */
    public MyStack() {
        this.stack = new MyArrayList<>();
    }

    /**
     * Pushes element to top of the stack
     * 
     * @param toAdd the element to push onto the stack
     * @throws NullPointerException if the element is null
     */
    @Override
    public void push(E toAdd) throws NullPointerException {
    	if (toAdd == null) {
    		throw new NullPointerException();
    	}
    	stack.add(toAdd);
    }
    
    /**
     * Removes and returns top element of the stack
     * 
     * @return the element removed from top of stack
     * @throws EmptyStackException if stack is empty
     */
    @Override
    public E pop() throws EmptyStackException{
    	if (stack.size() == 0) {
    		throw new EmptyStackException();
    	}
    	
    	return stack.remove(stack.size()-1);
    }

    /**
     * Returns element at top of the stack without removing it
     * 
     * @return the element at the top of the stack
     * @throws EmptyStackException if stack is empty
     */
    @Override
    public E peek() throws EmptyStackException{
    	if (stack.size() == 0) {
    		throw new EmptyStackException();
    	}
    	return stack.get(stack.size()-1);
    }
    
    /**
     * Removes all elements from the stack
     */
    @Override
    public void clear() {
    	stack.clear();
    }
    
    /**
     * Determines whether the stack has any elements
     * 
     * @return true if empty; else false
     */
    @Override
    public boolean isEmpty() {
    	return stack.isEmpty();
    }
    
    /**
     * Returns an array containing all elements in the stack
     * 
     * @return an array containing the stack elements from top to bottom
     */
    @Override
    public Object[] toArray() {
    	
    	Object[] thisArray  = new Object[this.size()];
    	Iterator<E> iterator = this.iterator();
    	int i = 0;
    	
    	while (iterator.hasNext()){
    		thisArray[i++] = iterator.next();
    	}
    	return thisArray;
    	}
    
    /**
     * Copies the elements of the stack into the specified array.
     *
     * @param holder the array to hold the stack elements
     * @return an array containing the stack elements
     * @throws NullPointerException if the specified array is null
     */
    @Override
    public E[] toArray(E[] holder) throws NullPointerException{
    	if (holder == null) {
    		throw new NullPointerException();
    	}
    	
    	E[] thisArray = stack.toArray(holder);
    	
    	Iterator<E> iterator = this.iterator();
    	int i = 0;
    	
    	while (iterator.hasNext()) {
    		thisArray[i++] = iterator.next();
    	}
    	return thisArray;
    }
    
    /**
     * Determines whether the specified element exists in the stack
     *
     * @param toFind element to search for
     * @return true if found; else flase
     * @throws NullPointerException if the specified array is null
     */
    @Override
    public boolean contains(E toFind) throws NullPointerException {
    	return stack.contains(toFind);
    }
    
    /**
     * Searches for the specified element in the stack
     * 
     * @param toFind the element to locate
     * @return the position of the element from top of stack, or -1 if not found
     */
    @Override
    public int search(E toFind) {
    	for (int i = stack.size() - 1; i != -1; i--) {
    		if (stack.get(i).equals(toFind)) {
    			return stack.size()-i;
    		}
    	}
    	return -1;
    }
    
    /**
     * Returns an iterator that traverses stack from top to bottom
     * 
     * @return an iterator over the stack elements
     */
    @Override
    public Iterator<E> iterator() {
    	return new StackIterator();
    }
    
    /**
     * An iterator that traverses the stack from top to bottom
     */
    private class StackIterator implements Iterator<E> {
    	private int current;
    	
    	/**
    	 * Constructs an iterator positioned at top of the stack
    	 */
    	public StackIterator() {
    		current = stack.size()-1;
    	}
    	
    	/**
    	 * Determines whether another element exists in the iteration
    	 * 
    	 * @return true if another element exists; else false
    	 */
    	@Override
    	public boolean hasNext() {
    		return current != -1;
    	}
    	
    	/**
    	 * Returns the next element in the iteration
    	 * 
    	 * @return the next element
    	 * @throws NoSuchElementException if no more elements exist
    	 */
    	@Override
    	public E next() throws NoSuchElementException {
    		if (!hasNext()) {
    			throw new NoSuchElementException();
    		}
    		return stack.get(current--);
    	}
    }
    
    /**
     * Compares this stack with another for equality
     * 
     * @param that the stack to compare with
     * @return true if equal; else false
     */
    @Override
    public boolean equals( StackADT<E> that) {
    	
    	if (that == null) {
    		return false;
    	}
    	
    	Object[] thisArray = this.toArray();
    	Object[] thatArray = that.toArray();
    	
    	if (thisArray.length != thatArray.length) {
    		return false;
    	}
    	
    	for (int i = 0; i < thisArray.length; i++) {
    		if (!thisArray[i].equals(thatArray[i])) {
    			return false;
    			}
    	}
    	return true;
    }
    
    /**
     * Returns the number of elements currently in stack
     * 
     * @return the number of elements in the stack
     */
    @Override
    public int size() {
    	return stack.size();
    }
    
    /**
     * Determines whether the stack has reached its max
     * 
     * @return false since it can grow dynamically
     */
    @Override
    public boolean stackOverflow() {
    	return false;
    }
}