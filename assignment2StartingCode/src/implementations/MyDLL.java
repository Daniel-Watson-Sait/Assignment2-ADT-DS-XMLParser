package implementations;

import java.lang.reflect.Array;
import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;

/**
 * MyDLL is a doubly linked list implementation of ListADT.
 * Each node stores an element and references to the previous
 * and next nodes in the list.
 *
 * @param <E> the type of element stored in the list
 * @author Habin Park
 * @version 1.0
 */
public class MyDLL<E> implements ListADT<E>
{
	private Node<E> head;
	private Node<E> tail;
	private int size;

	/**
	 * Node used to store data inside the doubly linked list.
	 *
	 * @param <E> the type of data stored in the node
	 */
	private static class Node<E>
	{
		private E data;
		private Node<E> previous;
		private Node<E> next;

		/**
		 * Creates a new node.
		 *
		 * @param data the data stored in the node
		 */
		public Node(E data)
		{
			this.data = data;
			this.previous = null;
			this.next = null;
		}
	}

	/**
	 * Creates an empty doubly linked list.
	 */
	public MyDLL()
	{
		head = null;
		tail = null;
		size = 0;
	}

	/**
	 * Returns the number of elements in the list.
	 *
	 * @return the current size of the list
	 */
	@Override
	public int size()
	{
		return size;
	}

	/**
	 * Removes all elements from the list.
	 */
	@Override
	public void clear()
	{
		Node<E> current = head;

		while (current != null)
		{
			Node<E> nextNode = current.next;

			current.previous = null;
			current.next = null;

			current = nextNode;
		}

		head = null;
		tail = null;
		size = 0;
	}

	/**
	 * Adds an element at a specific index in the list.
	 *
	 * @param index the index where the element will be added
	 * @param toAdd the element to add
	 * @return true when the element is added
	 * @throws NullPointerException if the element is null
	 * @throws IndexOutOfBoundsException if the index is invalid
	 */
	@Override
	public boolean add(int index, E toAdd)
			throws NullPointerException, IndexOutOfBoundsException
	{
		if (toAdd == null)
		{
			throw new NullPointerException();
		}

		if (index < 0 || index > size)
		{
			throw new IndexOutOfBoundsException();
		}

		Node<E> newNode = new Node<E>(toAdd);

		if (size == 0)
		{
			head = newNode;
			tail = newNode;
		}
		else if (index == 0)
		{
			newNode.next = head;
			head.previous = newNode;
			head = newNode;
		}
		else if (index == size)
		{
			newNode.previous = tail;
			tail.next = newNode;
			tail = newNode;
		}
		else
		{
			Node<E> current = getNode(index);
			Node<E> previousNode = current.previous;

			newNode.previous = previousNode;
			newNode.next = current;

			previousNode.next = newNode;
			current.previous = newNode;
		}

		size++;
		return true;
	}

	/**
	 * Adds an element to the end of the list.
	 *
	 * @param toAdd the element to add
	 * @return true when the element is added
	 * @throws NullPointerException if the element is null
	 */
	@Override
	public boolean add(E toAdd) throws NullPointerException
	{
		return add(size, toAdd);
	}

	/**
	 * Adds all elements from another ListADT to the end of this list.
	 *
	 * @param toAdd the list containing the elements to add
	 * @return true when the elements are added
	 * @throws NullPointerException if the provided list is null
	 */
	@Override
	public boolean addAll(ListADT<? extends E> toAdd)
			throws NullPointerException
	{
		if (toAdd == null)
		{
			throw new NullPointerException();
		}

		Iterator<? extends E> iterator = toAdd.iterator();

		while (iterator.hasNext())
		{
			add(iterator.next());
		}

		return true;
	}

	/**
	 * Returns the element at the provided index.
	 *
	 * @param index the index of the element
	 * @return the element at the index
	 * @throws IndexOutOfBoundsException if the index is invalid
	 */
	@Override
	public E get(int index) throws IndexOutOfBoundsException
	{
		checkIndex(index);

		return getNode(index).data;
	}

	/**
	 * Removes the element at the provided index.
	 *
	 * @param index the index of the element to remove
	 * @return the removed element
	 * @throws IndexOutOfBoundsException if the index is invalid
	 */
	@Override
	public E remove(int index) throws IndexOutOfBoundsException
	{
		checkIndex(index);

		Node<E> nodeToRemove = getNode(index);
		E removedData = nodeToRemove.data;

		removeNode(nodeToRemove);

		return removedData;
	}

	/**
	 * Removes the first matching element from the list.
	 *
	 * @param toRemove the element to remove
	 * @return the removed element or null if it was not found
	 * @throws NullPointerException if the element is null
	 */
	@Override
	public E remove(E toRemove) throws NullPointerException
	{
		if (toRemove == null)
		{
			throw new NullPointerException();
		}

		Node<E> current = head;

		while (current != null)
		{
			if (toRemove.equals(current.data))
			{
				E removedData = current.data;

				removeNode(current);

				return removedData;
			}

			current = current.next;
		}

		return null;
	}

	/**
	 * Replaces the element at the provided index.
	 *
	 * @param index the index of the element to replace
	 * @param toChange the new element
	 * @return the previous element
	 * @throws NullPointerException if the new element is null
	 * @throws IndexOutOfBoundsException if the index is invalid
	 */
	@Override
	public E set(int index, E toChange)
			throws NullPointerException, IndexOutOfBoundsException
	{
		if (toChange == null)
		{
			throw new NullPointerException();
		}

		checkIndex(index);

		Node<E> current = getNode(index);
		E oldData = current.data;

		current.data = toChange;

		return oldData;
	}

	/**
	 * Checks if the list is empty.
	 *
	 * @return true if the list contains no elements
	 */
	@Override
	public boolean isEmpty()
	{
		return size == 0;
	}

	/**
	 * Checks whether an element exists in the list.
	 *
	 * @param toFind the element to search for
	 * @return true if the element is found
	 * @throws NullPointerException if the element is null
	 */
	@Override
	public boolean contains(E toFind) throws NullPointerException
	{
		if (toFind == null)
		{
			throw new NullPointerException();
		}

		Node<E> current = head;

		while (current != null)
		{
			if (toFind.equals(current.data))
			{
				return true;
			}

			current = current.next;
		}

		return false;
	}

	/**
	 * Returns the elements in an array of the provided type.
	 *
	 * @param toHold the array used to hold the elements
	 * @return an array containing the list elements
	 * @throws NullPointerException if the provided array is null
	 */
	@SuppressWarnings("unchecked")
	@Override
	public E[] toArray(E[] toHold) throws NullPointerException
	{
		if (toHold == null)
		{
			throw new NullPointerException();
		}

		E[] result = toHold;

		if (toHold.length < size)
		{
			result = (E[]) Array.newInstance(
					toHold.getClass().getComponentType(),
					size);
		}

		Node<E> current = head;
		int index = 0;

		while (current != null)
		{
			result[index] = current.data;

			index++;
			current = current.next;
		}

		if (result.length > size)
		{
			result[size] = null;
		}

		return result;
	}

	/**
	 * Returns the elements of the list in an Object array.
	 *
	 * @return an Object array containing the list elements
	 */
	@Override
	public Object[] toArray()
	{
		Object[] result = new Object[size];

		Node<E> current = head;
		int index = 0;

		while (current != null)
		{
			result[index] = current.data;

			index++;
			current = current.next;
		}

		return result;
	}

	/**
	 * Returns an iterator for the elements in the list.
	 *
	 * @return an iterator for this list
	 */
	@Override
	public Iterator<E> iterator()
	{
		return new DLLIterator();
	}

	/**
	 * Iterator used to move through the elements of the list.
	 */
	private class DLLIterator implements Iterator<E>
	{
		private Object[] elements;
		private int currentIndex;

		/**
		 * Creates an iterator and copies the elements from the list.
		 */
		public DLLIterator()
		{
			elements = MyDLL.this.toArray();
			currentIndex = 0;
		}

		/**
		 * Checks if another element is available.
		 *
		 * @return true if another element exists
		 */
		@Override
		public boolean hasNext()
		{
			return currentIndex < elements.length;
		}

		/**
		 * Returns the next element from the iterator.
		 *
		 * @return the next element
		 * @throws NoSuchElementException if there are no more elements
		 */
		@SuppressWarnings("unchecked")
		@Override
		public E next() throws NoSuchElementException
		{
			if (!hasNext())
			{
				throw new NoSuchElementException();
			}

			E data = (E) elements[currentIndex];
			currentIndex++;

			return data;
		}
	}

	/**
	 * Returns the node at the provided index.
	 *
	 * @param index the node index
	 * @return the node at the index
	 */
	private Node<E> getNode(int index)
	{
		Node<E> current;

		if (index < size / 2)
		{
			current = head;

			for (int i = 0; i < index; i++)
			{
				current = current.next;
			}
		}
		else
		{
			current = tail;

			for (int i = size - 1; i > index; i--)
			{
				current = current.previous;
			}
		}

		return current;
	}

	/**
	 * Removes a node from the list and reconnects the surrounding nodes.
	 *
	 * @param node the node to remove
	 */
	private void removeNode(Node<E> node)
	{
		if (node.previous == null)
		{
			head = node.next;
		}
		else
		{
			node.previous.next = node.next;
		}

		if (node.next == null)
		{
			tail = node.previous;
		}
		else
		{
			node.next.previous = node.previous;
		}

		node.previous = null;
		node.next = null;

		size--;
	}

	/**
	 * Checks if an index is valid for an existing element.
	 *
	 * @param index the index to check
	 * @throws IndexOutOfBoundsException if the index is invalid
	 */
	private void checkIndex(int index) throws IndexOutOfBoundsException
	{
		if (index < 0 || index >= size)
		{
			throw new IndexOutOfBoundsException();
		}
	}
}