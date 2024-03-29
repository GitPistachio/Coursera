/**
 * 
 */
package spelling;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 *
 */
public class AutoCompleteMatchCaseTester {

	private String dictFile = "data/words.small.txt"; 

	AutoCompleteMatchCase emptyDict; 
	AutoCompleteMatchCase smallDict;
	AutoCompleteMatchCase largeDict;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		emptyDict = new AutoCompleteMatchCase();
		smallDict = new AutoCompleteMatchCase();
		largeDict = new AutoCompleteMatchCase();

		smallDict.addWord("Hello");
		smallDict.addWord("HElLo");
		smallDict.addWord("help");
		smallDict.addWord("he");
		smallDict.addWord("hem");
		smallDict.addWord("hot");
		smallDict.addWord("hey");
		smallDict.addWord("a");
		smallDict.addWord("a");
		smallDict.addWord("subsequent");
		
		DictionaryLoader.loadDictionary(largeDict, dictFile);
	}

	
	/** Test if the size method is working correctly.
	 */
	@Test
	public void testSize()
	{
		assertEquals("Testing size for empty dict", 0, emptyDict.size());
		assertEquals("Testing size for small dict", 9, smallDict.size());
		assertEquals("Testing size for large dict", 4440, largeDict.size());
	}
	
	/** Test the isWord method */
	@Test
	public void testIsWord()
	{
		assertEquals("Testing isWord on empty: Hello", false, emptyDict.isWord("Hello"));
		assertEquals("Testing isWord on small: Hello", true, smallDict.isWord("Hello"));
		assertEquals("Testing isWord on large: Hello", true, largeDict.isWord("Hello"));
		
		assertEquals("Testing isWord on small: hello", false, smallDict.isWord("hello"));
		assertEquals("Testing isWord on large: hello", true, largeDict.isWord("hello"));

		assertEquals("Testing isWord on small: hellow", false, smallDict.isWord("hellow"));
		assertEquals("Testing isWord on large: hellow", false, largeDict.isWord("hellow"));
		
		assertEquals("Testing isWord on empty: empty string", false, emptyDict.isWord(""));
		assertEquals("Testing isWord on small: empty string", false, smallDict.isWord(""));
		assertEquals("Testing isWord on large: empty string", false, largeDict.isWord(""));
		
		assertEquals("Testing isWord on small: no", false, smallDict.isWord("no"));
		assertEquals("Testing isWord on large: no", true, largeDict.isWord("no"));
		
		assertEquals("Testing isWord on small: subsequent", true, smallDict.isWord("subsequent"));
		assertEquals("Testing isWord on large: subsequent", true, largeDict.isWord("subsequent"));
		
		assertEquals("Testing isWord on small: HElLo", true, smallDict.isWord("HElLo"));
		assertEquals("Testing isWord on large: HElLo", false, largeDict.isWord("HElLo"));
		
		assertEquals("Testing isWord on small: HELLO", true, smallDict.isWord("HELLO"));
		assertEquals("Testing isWord on large: DOCTOR", true, largeDict.isWord("DOCTOR"));
		
		assertEquals("Testing isWord on small: HELP", true, smallDict.isWord("HELP"));
		assertEquals("Testing isWord on large: YET", true, largeDict.isWord("YET"));
	}
	
	/** Test the addWord method */
	@Test
	public void testAddWord()
	{
		
		
		assertEquals("Asserting hellow is not in empty dict", false, emptyDict.isWord("hellow"));
		assertEquals("Asserting hellow is not in small dict", false, smallDict.isWord("hellow"));
		assertEquals("Asserting hellow is not in large dict", false, largeDict.isWord("hellow"));
		
		emptyDict.addWord("hellow");
		smallDict.addWord("hellow");
		largeDict.addWord("hellow");

		assertEquals("Asserting hellow is in empty dict", true, emptyDict.isWord("hellow"));
		assertEquals("Asserting hellow is in small dict", true, smallDict.isWord("hellow"));
		assertEquals("Asserting hellow is in large dict", true, largeDict.isWord("hellow"));

		assertEquals("Asserting xyzabc is not in empty dict", false, emptyDict.isWord("xyzabc"));
		assertEquals("Asserting xyzabc is not in small dict", false, smallDict.isWord("xyzabc"));
		assertEquals("Asserting xyzabc is in large dict", false, largeDict.isWord("xyzabc"));

		
		emptyDict.addWord("XYZAbC");
		smallDict.addWord("XYZAbC");
		largeDict.addWord("XYZAbC");

		assertEquals("Asserting xyzabc is in empty dict", false, emptyDict.isWord("xyzabc"));
		assertEquals("Asserting xyzabc is in small dict", false, smallDict.isWord("xyzabc"));
		assertEquals("Asserting xyzabc is large dict", false, largeDict.isWord("xyzabc"));
		
		
		assertEquals("Testing isWord on empty: empty string", false, emptyDict.isWord(""));
		assertEquals("Testing isWord on small: empty string", false, smallDict.isWord(""));
		assertEquals("Testing isWord on large: empty string", false, largeDict.isWord(""));
		
		assertEquals("Testing isWord on small: no", false, smallDict.isWord("no"));
		assertEquals("Testing isWord on large: no", true, largeDict.isWord("no"));
		
		assertEquals("Testing isWord on small: subsequent", true, smallDict.isWord("subsequent"));
		assertEquals("Testing isWord on large: subsequent", true, largeDict.isWord("subsequent"));
		
		
	}
	
	@Test
	public void testPredictCompletions()
	{
		List<String> completions;
		completions = smallDict.predictCompletions("", 0);
		assertEquals(0, completions.size());
		
		completions = smallDict.predictCompletions("",  4);
		assertEquals(4, completions.size());
		assertTrue(completions.contains("a"));
		assertTrue(completions.contains("he"));
		boolean twoOfThree = completions.contains("hey") && completions.contains("hot") ||
				             completions.contains("hey") && completions.contains("hem") ||
				             completions.contains("hot") && completions.contains("hem");
		assertTrue(twoOfThree);
		
		completions = smallDict.predictCompletions("he", 2);
		boolean allIn = completions.contains("he") && 
				(completions.contains("hem") || completions.contains("hey"));
		assertEquals(2, completions.size());
		assertTrue(allIn);
		
		completions = smallDict.predictCompletions("hel", 10);
		assertEquals(1, completions.size());
		allIn = !completions.contains("hello") && completions.contains("help");
		assertTrue(allIn);
	
		completions = smallDict.predictCompletions("x", 5);
		assertEquals(0, completions.size());
	}
	
	
	
	
}
