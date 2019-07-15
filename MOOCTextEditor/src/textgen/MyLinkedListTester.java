/**
 * 
 */
package textgen;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH = 10; 

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;
	MyLinkedList<Integer> pi_digits;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
	    shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++) {
			longerList.add(i);
		}
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);
		
		pi_digits = new MyLinkedList<Integer>();
	}

	
	/** Test if the get method is working correctly.
	 */
	/*You should not need to add much to this method.
	 * We provide it as an example of a thorough test. */
	@Test
	public void testGet()
	{
		//test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		
		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));
		
		try {
			shortList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		// test longer list contents
		for(int i = 0; i < LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check " + i + " element", (Integer)i, longerList.get(i));
		}
		
		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
	}
	
	
	/** Test removing an element from the list.
	 * We've included the example from the concept challenge.
	 * You will want to add more tests.  */
	@Test
	public void testRemove()
	{
		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());
		
		try {
			pi_digits.remove(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
				
		pi_digits.add(3);
		try {
			pi_digits.remove(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
		a = pi_digits.remove(0);
		assertEquals("Remove: check a is correct ", 3, a);
		assertEquals("Remove: check size is correct ", 0, pi_digits.size());
		
		pi_digits.add(3);
		pi_digits.add(1);
		a = pi_digits.remove(1);
		assertEquals("Remove: check a is correct ", 1, a);
		assertEquals("Remove: check element 0 is correct ", (Integer)3, pi_digits.get(0));
		assertEquals("Remove: check size is correct ", 1, pi_digits.size());
		
		pi_digits.add(1);
		pi_digits.add(4);
		pi_digits.add(1);
		a = pi_digits.remove(2);
		assertEquals("Remove: check a is correct ", 4, a);
		assertEquals("Remove: check element 2 is correct ", (Integer)1, pi_digits.get(2));
		assertEquals("Remove: check element 1 is correct ", (Integer)1, pi_digits.get(1));
		assertEquals("Remove: check size is correct ", 3, pi_digits.size());
		
		// TODO: Add more tests here
	}
	
	/** Test adding an element into the end of the list, specifically
	 *  public boolean add(E element)
	 * */
	@Test
	public void testAddEnd()
	{
        // TODO: implement this test
		try {
			pi_digits.add(null);
			fail("Check out of null addition.");
		} catch (NullPointerException exc) {
			
		}
	}

	
	/** Test the size of the list */
	@Test
	public void testSize()
	{
		// TODO: implement this test
		assertEquals("Size: check size of empty list", 0, pi_digits.size());
		pi_digits.add((Integer)3);
		assertEquals("Size: check size of empty list", 1, pi_digits.size());
		pi_digits.add((Integer)1);
		assertEquals("Size: check size of empty list", 2, pi_digits.size());
		pi_digits.remove(0);
		assertEquals("Size: check size of empty list", 1, pi_digits.size());
		pi_digits.remove(0);
		assertEquals("Size: check size of empty list", 0, pi_digits.size());
	}

	
	
	/** Test adding an element into the list at a specified index,
	 * specifically:
	 * public void add(int index, E element)
	 * */
	@Test
	public void testAddAtIndex()
	{
        // TODO: implement this test
		try {
			pi_digits.add(0, null);
			fail("Check out of null addition.");
		} catch (NullPointerException exc) {
			
		}
		
		try {
			pi_digits.add(pi_digits.size() + 1, 3);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
		try {
			pi_digits.add(-1, 3);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
		pi_digits.add(0, 3);
		assertEquals("Adding at index: check adding to an empty list", (Integer)3, pi_digits.get(0));
		pi_digits.add(1, 1);
		assertEquals("Adding at index: check adding to an empty list", (Integer)1, pi_digits.get(1));
		pi_digits.add(2, 5);
		assertEquals("Adding at index: check adding to an empty list", (Integer)5, pi_digits.get(2));
		pi_digits.add(2, 4);
		assertEquals("Adding at index: check adding to an empty list", (Integer)4, pi_digits.get(2));
		pi_digits.add(3, 1);
		assertEquals("Adding at index: check adding to an empty list", (Integer)1, pi_digits.get(3));
		pi_digits.add(0, 0);
		assertEquals("Adding at index: check adding to an empty list", (Integer)0, pi_digits.get(0));
		
		pi_digits = new MyLinkedList<Integer>();
	}
	
	/** Test setting an element in the list */
	@Test
	public void testSet()
	{
	    // TODO: implement this test
		pi_digits.add(4);
		
		try {
			pi_digits.set(-1, 3);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
		try {
			pi_digits.set(1, 4);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
		try {
			pi_digits.add(0, null);
			fail("Check out of null addition.");
		} catch (NullPointerException exc) {
			
		}
		
		pi_digits.set(0, 3);
		assertEquals("Adding at index: check adding to an empty list", (Integer)3, pi_digits.get(0));
		pi_digits.add(5);
		pi_digits.set(1, 1);
		assertEquals("Adding at index: check adding to an empty list", (Integer)1, pi_digits.get(1));
	}
	
	
	// TODO: Optionally add more test methods.
	
}
