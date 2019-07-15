package textgen;

import java.util.AbstractList;
import java.lang.StringBuilder;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		// TODO: Implement this method
		if (element == null) {
			throw new NullPointerException("Inserting null obejct to the list is not allowed.");
		}
		
		LLNode<E> node = new LLNode<E>(element);
		if (this.size > 1) {
			node.prev = this.tail;
			this.tail.next = node;
			this.tail = node;
		} else if (this.size == 1) {
			node.prev = this.head;
			this.head.next = node;
			this.tail = node;
		} else {
			this.head = node;
			this.tail = node;
		}
		
		size++;
		return false;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// TODO: Implement this method.
		if (this.size == 0 || index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", size: " + this.size());
		}
		
		LLNode<E> node = this.head;
		index--;
		
		while (index-- >= 0) {
			node = node.next; 
		}
		
		return node.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// TODO: Implement this method
		if (element == null) {
			throw new NullPointerException("Inserting null obejct to the list is not allowed.");
		}
		
		if (index < 0 || index > this.size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", size: " + this.size());
		}
			
		if (index == this.size) {
			// Adding as a last element
			
			this.add(element);
			return;
		} else if (index == 0) {
			// Adding as the first element
			
			LLNode<E> new_node = new LLNode<E>(element, null, this.head);
			
			this.head.prev = new_node;
			this.head = new_node;
		} else {
			LLNode<E> prev_node = this.head;
			index--;
			
			while (index-- > 0) {
				prev_node = prev_node.next;
			}
			LLNode<E> new_node = new LLNode<E>(element, prev_node, prev_node.next);
			
			prev_node.next.prev = new_node;
			prev_node.next = new_node;
		}
		
		this.size++;
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return this.size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		if (index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", size: " + this.size());
		}
		
		LLNode<E> node = null;
		if (index == this.size - 1) {
			node = this.tail;
			if (this.size == 1) {
				this.head = null;
				this.tail = null;
			} else {
				this.tail.prev.next = null;
				this.tail = this.tail.prev;
			}
		} else if (index == 0) {
			node = this.head;
			this.head.next.prev = null;
			this.head = this.head.next;
		} else {
			node = this.head;
			index--;
			
			while (index-- >= 0) {
				node = node.next;
			}
			
			node.prev.next = node.next;
			node.next.prev = node.prev;
		}
		
		
		this.size--;
		return node.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		if (element == null) {
			throw new NullPointerException("Inserting null obejct to the list is not allowed.");
		}
		
		if (index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", size: " + this.size());
		}
		
		
		LLNode<E> node = this.head;
		index--;
		
		while (index-- >= 0) {
			node = node.next; 
		}
		
		E old_data = node.data;
		node.data = element;
		
		return old_data;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder('[');
		
		LLNode<E> node = this.head;
		
		while (node != null) {
			sb.append(node.toString());
			sb.append(", ");
			node = node.next;
		}
		
		sb.append(']');
		
		return sb.toString();
	}
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) {
		this.data = e;
		this.prev = null;
		this.next = null;
	}
	
	public LLNode(E element, LLNode<E> prev, LLNode<E> next) {
		this.data = element;
		this.prev = prev;
		this.next = next;
	}
	
	public String toString() {
		return this.data.toString();
	}
}
