/**
 * 
 */
package spelling;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.LinkedList;


/**
 * A class that implements the Dictionary interface with a HashSet
 */
public class DictionaryHashSetMatchCase implements Dictionary 
{

    private HashSet<String> words;
	
	public DictionaryHashSetMatchCase()
	{
	    words = new HashSet<String>();
	}
	
    /** Add this word to the dictionary.
     * @param word The word to add
     * @return true if the word was added to the dictionary 
     * (it wasn't already there). */
	@Override
	public boolean addWord(String word) 
	{
		return words.add(word);
	}

	/** Return the number of words in the dictionary */
    @Override
	public int size()
	{
    	 return words.size();
	}
	
	/** Is this a word according to this dictionary? */
    @Override
	public boolean isWord(String s) {
    	if (s.isEmpty()) {
    		return false;
    	}
    	
    	for (String possible_word : this.allPossibleWords(s)) {
    		if (words.contains(possible_word)) {
    			return true;
    		}
    	}
    	
    	return false;
	}
    
    private LinkedList<String> allPossibleWords(String s) {
    	LinkedList<String> all_possible_words = new LinkedList<String>();
    	
    	all_possible_words.add(s);
    	if (this.isAllUpperCase(s)) {
    		all_possible_words.add(s.toLowerCase());
    		all_possible_words.add(s.charAt(0) + s.substring(1).toLowerCase());
    	} else if (this.isCamelCase(s)) {
    		all_possible_words.add(s.toLowerCase());
    	}
    	
    	return all_possible_words;
    }
    
    private boolean isCamelCase(String s) {
    	if (s.equals(Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase())) {
    		return true;
    	}
    	
    	return false;
    }
	
    private boolean isAllUpperCase(String s) {
    	if (s.equals(s.toUpperCase())) {
    		return true;
    	}
    	
    	return false;
    }
    
    private boolean isAllLowerCase(String s) {
    	if (s.equals(s.toLowerCase())) {
    		return true;
    	}
    	
    	return false;
    }
}
