package spelling;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class NearbyWordsTester {
	private String dict_file = "data/words.small.txt";

	DictionaryHashSet dict;
	DictionaryHashSet large_dict;
	NearbyWords nearby_words;
	NearbyWords large_nearby_words;
	
	@Before
	public void setUp() throws Exception {
		dict = new DictionaryHashSet();
				
		dict.addWord("mama");
		dict.addWord("mam");
		dict.addWord("niemam");
		dict.addWord("mami");
		dict.addWord("omami");
		dict.addWord("sami");
		dict.addWord("lama");
		dict.addWord("dama");
		dict.addWord("rama");
		dict.addWord("rana");
		dict.addWord("ranka");
		dict.addWord("firanka");
		dict.addWord("runa");
		dict.addWord("rani");
		dict.addWord("rai");
		dict.addWord("ran");
		dict.addWord("sani");
		dict.addWord("ani");
		dict.addWord("damka");
		dict.addWord("adama");
		dict.addWord("a");
		dict.addWord("z");
		dict.addWord("w");
		
		nearby_words = new NearbyWords(dict);
		
		Dictionary large_dict = new DictionaryHashSet();
		DictionaryLoader.loadDictionary(large_dict, "test_cases/dict2.txt");
		
		large_nearby_words = new NearbyWords(large_dict);
	}
	
	@Test
	public void testInsertions() {
		List<String> currentList = new LinkedList<String>();
		
		nearby_words.insertions("dama", currentList, true);
		assertEquals(2, currentList.size());
		
		currentList.clear();
		nearby_words.insertions("mam", currentList, true);
		assertEquals(2, currentList.size());
		
		currentList.clear();
		nearby_words.insertions("a", currentList, false);
		assertEquals(51, currentList.size());
		
		currentList.clear();
		nearby_words.insertions("", currentList, false);
		assertEquals(26, currentList.size());
		
		currentList.clear();
		nearby_words.insertions("", currentList, true);
		assertEquals(3, currentList.size());
	}
	
	@Test
	public void testDeletions() {
		List<String> currentList = new LinkedList<String>();
		
		nearby_words.deletions("rani", currentList, true);
		assertEquals(3, currentList.size());
		
		currentList.clear();
		nearby_words.deletions("a", currentList, true);
		assertEquals(0, currentList.size());
		
		currentList.clear();
		nearby_words.deletions("kleopatra", currentList, false);
		assertEquals(9, currentList.size());
	}
	
	@Test
	public void testSuggestions() {
		List<String> l = large_nearby_words.suggestions("dag", 4);
		System.out.println(l);
		assertEquals(2, l.size());
	}
}
