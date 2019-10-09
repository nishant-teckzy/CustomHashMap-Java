/**
 * 
 */
package CustomHashMap;

/**
 * An Interface which Consist of all necessary methods that any Map
 * should implement
 * 
 * @author Nishant Tiwari
 * @Created-at 27-07-2019
 *
 */
public interface CustomMap<K,V> {

	
	public Object get(K k);
	
	V put(K k, V v);
	
	Object entrySet();
	Object linkedEntrySet();
	
	int integrity(Object k);
	
	boolean containsKey(K k);
	
	
}
