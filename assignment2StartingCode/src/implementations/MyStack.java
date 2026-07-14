package implementations;

import utilities.StackADT;
import utilities.Iterator;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;

public class MyStack<E> implements StackADT<E> {


    private MyArrayList<E> stack;

    
    public MyStack() {
        this.stack = new MyArrayList<>();
    }

    @Override
    public void push(E toAdd) throws NullPointerException {
    	if (toAdd == null) {
    		throw new NullPointerException();
    	}
    	stack.add(toAdd);
    }
    
    @Override
    public E pop() throws EmptyStackException{
    	if (stack.size() == 0) {
    		throw new EmptyStackException();
    	}
    	
    	return stack.remove(stack.size()-1);
    }

    @Override
    public E peek() throws EmptyStackException{
    	if (stack.size() == 0) {
    		throw new EmptyStackException();
    	}
    	return stack.get(stack.size()-1);
    }
    @Override
    public void clear() {
    	stack.clear();
    }
    @Override
    public boolean isEmpty() {
    	return stack.isEmpty();
    }
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
    @Override
    public boolean contains(E toFind) throws NullPointerException {
    	return stack.contains(toFind);
    }
    @Override
    public int search(E toFind) {
    	for (int i = stack.size() - 1; i != -1; i--) {
    		if (stack.get(i).equals(toFind)) {
    			return stack.size()-i;
    		}
    	}
    	return -1;
    }
    @Override
    public Iterator<E> iterator() {
    	return new StackIterator();
    }
    
    private class StackIterator implements Iterator<E> {
    	private int current;
    	
    	public StackIterator() {
    		current = stack.size()-1;
    	}
    	
    	@Override
    	public boolean hasNext() {
    		return current != -1;
    	}
    	@Override
    	public E next() throws NoSuchElementException {
    		if (!hasNext()) {
    			throw new NoSuchElementException();
    		}
    		return stack.get(current--);
    	}
    }
    
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
    @Override
    public int size() {
    	return stack.size();
    }
    @Override
    public boolean stackOverflow() {
    	return false;
    }
}