package implementations;

import java.lang.reflect.Array;
import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;

public class MyDLL<E> implements ListADT<E>
{
	private Node<E> head;
	private Node<E> tail;
	private int size;

	private static class Node<E>
	{
		private E data;
		private Node<E> previous;
		private Node<E> next;

		public Node(E data)
		{
			this.data = data;
		}
	}

	public MyDLL()
	{
		head = null;
		tail = null;
		size = 0;
	}

	@Override
	public int size()
	{
		return size;
	}

	@Override
	public void clear()
	{
		Node<E> current = head;

		while (current != null)
		{
			Node<E> next = current.next;
			current.previous = null;
			current.next = null;
			current = next;
		}

		head = null;
		tail = null;
		size = 0;
	}

	@Override
	public boolean add(int index, E toAdd)
			throws NullPointerException, IndexOutOfBoundsException
	{
		if (toAdd == null)
		{
			throw new NullPointerException();
		}

		checkPositionIndex(index);

		Node<E> newNode = new Node<>(toAdd);

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
			Node<E> previous = current.previous;

			newNode.previous = previous;
			newNode.next = current;

			previous.next = newNode;
			current.previous = newNode;
		}

		size++;
		return true;
	}

	@Override
	public boolean add(E toAdd) throws NullPointerException
	{
		return add(size, toAdd);
	}

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

	@Override
	public E get(int index) throws IndexOutOfBoundsException
	{
		checkElementIndex(index);
		return getNode(index).data;
	}

	@Override
	public E remove(int index) throws IndexOutOfBoundsException
	{
		checkElementIndex(index);

		Node<E> nodeToRemove = getNode(index);
		E removedData = nodeToRemove.data;

		unlink(nodeToRemove);

		return removedData;
	}

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
				unlink(current);
				return removedData;
			}

			current = current.next;
		}

		return null;
	}

	@Override
	public E set(int index, E toChange)
			throws NullPointerException, IndexOutOfBoundsException
	{
		if (toChange == null)
		{
			throw new NullPointerException();
		}

		checkElementIndex(index);

		Node<E> node = getNode(index);
		E oldData = node.data;
		node.data = toChange;

		return oldData;
	}

	@Override
	public boolean isEmpty()
	{
		return size == 0;
	}

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
			result[index++] = current.data;
			current = current.next;
		}

		if (result.length > size)
		{
			result[size] = null;
		}

		return result;
	}

	@Override
	public Object[] toArray()
	{
		Object[] result = new Object[size];

		Node<E> current = head;
		int index = 0;

		while (current != null)
		{
			result[index++] = current.data;
			current = current.next;
		}

		return result;
	}

	@Override
	public Iterator<E> iterator()
	{
		return new DLLIterator();
	}

	private class DLLIterator implements Iterator<E>
	{
		private Node<E> current;

		public DLLIterator()
		{
			current = head;
		}

		@Override
		public boolean hasNext()
		{
			return current != null;
		}

		@Override
		public E next() throws NoSuchElementException
		{
			if (!hasNext())
			{
				throw new NoSuchElementException();
			}

			E data = current.data;
			current = current.next;

			return data;
		}
	}

	private Node<E> getNode(int index)
	{
		if (index < size / 2)
		{
			Node<E> current = head;

			for (int i = 0; i < index; i++)
			{
				current = current.next;
			}

			return current;
		}
		else
		{
			Node<E> current = tail;

			for (int i = size - 1; i > index; i--)
			{
				current = current.previous;
			}

			return current;
		}
	}

	private void unlink(Node<E> node)
	{
		Node<E> previous = node.previous;
		Node<E> next = node.next;

		if (previous == null)
		{
			head = next;
		}
		else
		{
			previous.next = next;
		}

		if (next == null)
		{
			tail = previous;
		}
		else
		{
			next.previous = previous;
		}

		node.previous = null;
		node.next = null;

		size--;
	}

	private void checkElementIndex(int index)
	{
		if (index < 0 || index >= size)
		{
			throw new IndexOutOfBoundsException();
		}
	}

	private void checkPositionIndex(int index)
	{
		if (index < 0 || index > size)
		{
			throw new IndexOutOfBoundsException();
		}
	}
}