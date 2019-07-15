package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.HashMap;
import java.util.Random;
import java.lang.StringBuilder;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	private ListNode getListNode(String word) {
		for (ListNode node : wordList) {
			if (node.getWord().equals(word)) {
				return node;
			}
		}
		
		return null;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		// TODO: Implement this method
		String [] words = sourceText.split("[\\s]+");
		starter = words[0];
		
		ListNode node;
		for (int i = 1; i < words.length; i++) {
			node = this.getListNode(words[i - 1]);
			if (node != null) {
				node.addNextWord(words[i]);
			} else {
				node = new ListNode(words[i - 1]);
				node.addNextWord(words[i]);
				wordList.add(node);
			}
		}
		
		node = this.getListNode(words[words.length - 1]);
		if (node != null) {
			node.addNextWord(starter);
		} else {
			node = new ListNode(words[words.length - 1]);
			node.addNextWord(starter);
			wordList.add(node);
		}
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    // TODO: Implement this method
		if (this.wordList.isEmpty()) {
			return "";
		}
		
		if (numWords > 0) {
			ListNode node = wordList.get(0);
			StringBuilder gen_text = new StringBuilder(node.getWord());
			numWords--;
			
			while (numWords-- > 0) {
				node = this.getListNode(node.getRandomNextWord(this.rnGenerator));
				gen_text.append(" ");
				gen_text.append(node.getWord());
			}
			
			return gen_text.toString();
		}
		
		return "";
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// TODO: Implement this method.
		wordList = new LinkedList<ListNode>();
		starter = "";
		this.train(sourceText);
	}
	
	// TODO: Add any private helper methods you need here.
	
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(12));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		
		String textString3 = "hi there hi Leo";
		System.out.println(textString3);
		gen.retrain(textString3);
		System.out.println(gen);
		System.out.println(gen.generateText(4));
		
		String textString4 = "Hello I am your father. Hello again and again. Hello you. Hello mother.";
		System.out.println(textString4);
		gen.retrain(textString4);
		System.out.println(gen);
		System.out.println(gen.generateText(4));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode implements Comparable<ListNode>
{
    // The word that is linking to the next words
	private String word;
	
	// The number of next words
	private int no_of_next_words;
	
	// The next words that could follow it
	private HashMap<String, Integer> nextWords;
	
	ListNode(String word) {
		this.word = word;
		this.nextWords = new HashMap<String, Integer>();
		this.no_of_next_words = 0;
	}
	
	public String getWord( ){
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.put(nextWord, nextWords.getOrDefault(nextWord, 0) + 1);
		this.no_of_next_words++;
	}
	
	public int getNoOfNextWords() {
		return this.no_of_next_words;
	}
	
	public String getRandomNextWord(Random generator)
	{
		// TODO: Implement this method
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
		
		int pos = generator.nextInt(this.getNoOfNextWords()) + 1;
		for (String word : this.nextWords.keySet()) {
			pos -= this.nextWords.get(word);
			if (pos <= 0) {
				return word;
			}
		}
		
	    return null;
	}
	
	public int compareTo(ListNode other) {
		return this.word.compareTo(other.word);
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords.keySet()) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


