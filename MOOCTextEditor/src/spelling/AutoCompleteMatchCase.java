package spelling;

import java.util.List;
import java.util.Set;

import javax.rmi.CORBA.Tie;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteMatchCase implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteMatchCase()
	{
		this.root = new TrieNode();
		this.size = 0;
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should convert the 
	 * string to all lower case before you insert it. 
	 * 
	 * This method adds a word by creating and linking the necessary trie nodes 
	 * into the trie, as described outlined in the videos for this week. It 
	 * should appropriately use existing nodes in the trie, only creating new 
	 * nodes when necessary. E.g. If the word "no" is already in the trie, 
	 * then adding the word "now" would add only one additional node 
	 * (for the 'w').
	 * 
	 * @return true if the word was successfully added or false if it already exists
	 * in the dictionary.
	 */
	public boolean addWord(String word)
	{
	    //TODO: Implement this method.
		TrieNode trie_node = root;
		
		for (Character c : word.toCharArray()) {
			if (trie_node.hasChild(c)) {
				trie_node = trie_node.getChild(c);
			} else {
				trie_node = trie_node.insert(c);
			}
		}
		
		if (!trie_node.endsWord()) {
			trie_node.setEndsWord(true);
			return true;
		}
		
	    return false;
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    //TODO: Implement this method
	    return dfs(this.root);
	}
	
	private int dfs(TrieNode trie_node) {
		int no_of_words = 0;
		
		if (trie_node.endsWord()) {
			no_of_words++;
		}
		
		for (Character c : trie_node.getValidNextCharacters()) {
			no_of_words += dfs(trie_node.getChild(c));
		}
		
		return no_of_words; 
	}
	
	
	/** Returns whether the string is a word in the trie, using the algorithm
	 * described in the videos for this week. */
	@Override
	public boolean isWord(String s) 
	{
	    // TODO: Implement this method
		if (s.isEmpty()) {
    		return false;
    	}
    	
    	for (String possible_word : this.allPossibleWords(s)) {
    		if (isInDict(possible_word)) {
    			return true;
    		}
    	}
    	
    	return false;
	}
	
	private boolean isInDict(String s) {
		TrieNode trie_node = root;
		
		for (Character c : s.toCharArray()) {
			if (trie_node.hasChild(c)) {
				trie_node = trie_node.getChild(c);
			} else {
				return false;
			}
		}
		
		if (trie_node.endsWord()) {
			return true;
		}
		
	    return false;
	}

	/** 
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions 
     * of the prefix string. All legal completions must be valid words in the 
     * dictionary. If the prefix itself is a valid word, it is included 
     * in the list of returned words. 
     * 
     * The list of completions must contain 
     * all of the shortest completions, but when there are ties, it may break 
     * them in any order. For example, if there the prefix string is "ste" and 
     * only the words "step", "stem", "stew", "steer" and "steep" are in the 
     * dictionary, when the user asks for 4 completions, the list must include 
     * "step", "stem" and "stew", but may include either the word 
     * "steer" or "steep".
     * 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
    	 
		List<String> list_of_completions = new LinkedList<String>();
		
		if (numCompletions == 0) {
			return list_of_completions;
		}
		
		if (prefix.isEmpty()) {
			return predictCompletionsForEmptyPrefix(numCompletions);
		}
		
		Queue<TrieNode> trie_node_queue = new LinkedList<TrieNode>(this.allPossibleTrieNodes(prefix));
		
		boolean is_all_upper_case = this.isAllUpperCase(prefix);
		boolean is_camel_case = this.isCamelCase(prefix);
		
		TrieNode trie_node;
		while (!trie_node_queue.isEmpty() && numCompletions > list_of_completions.size()) {
			trie_node = trie_node_queue.remove();
			
			if (trie_node.endsWord()) {
				if (is_all_upper_case && !list_of_completions.contains(trie_node.getText().toUpperCase())) {
					list_of_completions.add(trie_node.getText().toUpperCase());
					if (numCompletions <= list_of_completions.size()) {
						break;
					}
				} 
				
				if (is_camel_case && !list_of_completions.contains(this.toCamelCase(trie_node.getText()))) {
					list_of_completions.add(this.toCamelCase(trie_node.getText()));
					if (numCompletions <= list_of_completions.size()) {
						break;
					}
				} 
				
				if (!is_all_upper_case && !is_camel_case && !list_of_completions.contains(trie_node.getText())){
					list_of_completions.add(trie_node.getText());
					if (numCompletions <= list_of_completions.size()) {
						break;
					}
				}
			}
			
			for (Character c : trie_node.getValidNextCharacters()) {
				trie_node_queue.add(trie_node.getChild(c));
			}
		}
		
		return list_of_completions;
    }
     
     
    private List<String> predictCompletionsForEmptyPrefix(int numCompletions) 
    {   	 
		List<String> list_of_completions = new LinkedList<String>();
		TrieNode trie_node = root;
		   	
		Queue<TrieNode> trie_node_queue = new LinkedList<TrieNode>();
		trie_node_queue.add(trie_node);
		
		while (!trie_node_queue.isEmpty() && numCompletions > list_of_completions.size()) {
			trie_node = trie_node_queue.remove();
			
			if (trie_node.endsWord()) {
				list_of_completions.add(trie_node.getText());
			}
			
			for (Character c : trie_node.getValidNextCharacters()) {
				trie_node_queue.add(trie_node.getChild(c));
			}
		}
		
		return list_of_completions;
    }
     
    private String toCamelCase(String s) {
    	return Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase();
    }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	
 	private LinkedList<TrieNode> allPossibleTrieNodes(String s) {
 		LinkedList<TrieNode> all_possible_trie_nodes = new LinkedList<TrieNode>();
 		
 		TrieNode trie_node;
 		for (String prefix : this.allPossibleWords(s)) {
 			trie_node = getPrefixNode(prefix);
 			if (trie_node != null) {
 				all_possible_trie_nodes.add(trie_node);
 			}
 		}
 		
 		return all_possible_trie_nodes;
 	}
 	
 	private TrieNode getPrefixNode(String prefix) {
 		TrieNode trie_node = this.root;
 		
		for (Character c : prefix.toCharArray()) {
			if (trie_node.hasChild(c)) {
				trie_node = trie_node.getChild(c);
			} else {
				return null;
			}
		}
		
		return trie_node;
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
	
}