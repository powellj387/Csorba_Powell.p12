package test;

import java.util.Comparator;
import java.util.Iterator;

import avlmap.ULTreeMap;
import avlmap.ULTreeMap.Mapping;

/**
 * This class just checks whether your code will compile in my JUnit tests.
 * This class DOES NOT actually test your code.  It just ensures that it compiles.
 * @author Joe Meehean
 *
 */
public class P1CompilationStub {
	
	public static class NonComparableString{
		public String str;
		public NonComparableString(String aString) {
			this.str = aString;
		}
	}
	
	public static class NonCmpStrComparator implements Comparator<NonComparableString>{
		public int compare(NonComparableString lhs, NonComparableString rhs) {
			return lhs.str.compareTo(rhs.str); 
		}
	}
	
	public static void main(String[] args) {
		
		// simple map methods
		ULTreeMap<String, Integer> aMap = new ULTreeMap<>();
		aMap.insert("hello", 7);
		aMap.put("hello", 8);
		boolean inMap = aMap.containsKey("goodbye");
		Integer value = aMap.lookup("hello");
		int height = aMap.heightOfKey("hello");
		aMap.erase("goodbye");
		int size = aMap.size();
		boolean empty = aMap.empty();
		ULTreeMap<String,Integer> clone = aMap.clone();
		
		// iterator methods
		Iterator<Mapping<String,Integer>> iter = aMap.iterator();
		boolean hasNext = iter.hasNext();
		Mapping<String,Integer> mapping = iter.next();
		String key = mapping.getKey();
		value = mapping.getValue();
		
		for(Mapping<String,Integer> loopMapping : aMap) {
			key = loopMapping.getKey();
			value = loopMapping.getValue();
		}
		
		// clear after iteration (so next doesn't throw)
		aMap.clear();
		
		// map methods for non-comparable keys
		ULTreeMap<NonComparableString, Integer> nonCmpMap = new ULTreeMap<>( new NonCmpStrComparator() );
		nonCmpMap.insert(new NonComparableString("hello"), 7);
		nonCmpMap.put(new NonComparableString("hello"), 8);
		inMap = nonCmpMap.containsKey(new NonComparableString("goodbye"));
		value = nonCmpMap.lookup(new NonComparableString("hello"));
		height = nonCmpMap.heightOfKey(new NonComparableString("hello"));
		nonCmpMap.erase(new NonComparableString("goodbye"));
		size = nonCmpMap.size();
		empty = nonCmpMap.empty();		
		nonCmpMap.clear();
	}
}
