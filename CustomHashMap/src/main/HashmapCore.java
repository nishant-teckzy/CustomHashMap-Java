/**
 * 
 */
package main;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;


/**
 * Core Class For Custom HashMap Implementation This Class implements CustomCollection
 * Interface which Provides All the necessary Methods to implement for a HashMap
 * Collection
 * 
 * @author Nishant Tiwari
 * @Created-at 27-07-2019
 * @see Node
 * @see CustomMap
 * @param <K,V> "K" is used Here As A Key Generic "V" is used Here As A Value
 *        Generic
 *
 */
public class HashmapCore<K, V> implements CustomMap<K, V> {


	private static int capacity = 16;
	private int extendedCapacity = 16;
	
	private Object key[] = new Object[capacity];
	private int keyIndex = 0;
	private Node<K, V> Entry[] = new Node[capacity];
	
	/*
	 * Variable for Linked HashMap
	 */
	protected Node<K,V> head = null;
	private boolean firsthead = true;
	private Node<K,V> currentNode = null;
	private boolean LinkedEntry = false;

	/*
	 * Misc Variable;
	 */
	
	private double loadFactor = 0.75;
	private double filled = 0.0;
	private boolean nullFlag = false;
	private int hashIndex = -1;

	public HashmapCore() {
		super();
		

	}

	/**
	 * Maximum 0.90 load factor
	 * @param loadFactor
	 */
	public HashmapCore(double loadFactor ) {
		super();
		
		if(loadFactor < 1.00)
			this.loadFactor = loadFactor;

	}
	
	@Override
	public Object get(Object k) {
		Node<?, ?> node = null;
		if(Entry.length > 0) {
		 node = Entry[generateKeyIndex(k)];
		}
		
		while(node != null) {
			if(k.equals(node.k)) {
				System.out.println("Found at : "+node);
				return node.v;
			}else
				node = node.next;
		}

		return node;
	}

	/**
	 * Method To Store Key - Value pair in Entry[]
	 * 
	 * @author Nishant Tiwari
	 * @Created-at 27-07-2019
	 * @param K,V as key : Value
	 * @return Integer : Returns null (if Failed) Returns value (if Success)
	 * 
	 *         Note : To Solve "Situation : 1" of integrity() we will do nested
	 *         Bucketing of Diferent Key having Same Index;
	 * 
	 * @see integrity() Method
	 *
	 */

	@Override
	public V put(K k, V v) {
		if(key.length < Entry.length) {
			key = new Object[Entry.length];
		}
		
		V returnValue = null;
		if(k == null && v == null)
			return null;
		
		if(k == null) {nullFlag = true;}
			/*
			 * integrity() Returns 
			 * -1 : Duplicate Key Can't Insert ,
			 * 0 : Index is Present to insert ,
			 * 1 : Index is Duplicate but Key is Different.
			 */
			switch (integrity(k)) {
			case 0:
				hashIndex = generateKeyIndex(k);
				Node<K, V> newNode = new Node<K,V>(k, v);
				Entry[hashIndex] = newNode;
				System.out.println("KeyIndex :"+keyIndex+" // Length :"+key.length);
				key[keyIndex] = k;
		
				linkedPut(newNode);
				
				keyIndex += 1;
				filled += 1;
				returnValue = v;
				break;
			case 1:
				hashIndex = generateKeyIndex(k);
				Node<K, V> node = Entry[hashIndex];
				while (node.next != null)
					node = node.next;
					
				node.next = new Node<K, V>(k, v);
				linkedPut(node.next);
				filled += 1;
				returnValue = v;
				break;
			default:
				returnValue = null;
				break;
			}
		

		checkForResize();

		return returnValue;
	}
	
	public int size() {
		return (int)filled;
	}
	
	
	public void linkedPut(Node<K,V> current) {
	
			if(firsthead) {
				head = current;
				firsthead = false;
			
			}
			
			if(currentNode == null) {
				currentNode = current;
			}else {
				currentNode.after = current;
				currentNode = current;
				 
			}
		
	}

	/**
	 * Method To show All Entry in this HashMap
	 * 
	 * @author Nishant Tiwari
	 * @Created-at 27-07-2019
	 * @return Set : Returns a Set of All Entries
	 * @see integrity() Method
	 *
	 */

	@Override
	public Object linkedEntrySet() {
		LinkedEntry = true;
		EntrySet entryset = new EntrySet();
		return entryset;
	}
	
	
	
	@Override
	public Object entrySet() {
		EntrySet entryset = new EntrySet();
		return entryset;
	}

	/**
	 * This Method Checks the Integrity of The Key For Some Situations:
	 * 
	 * 1 - Whether Key is present already in HashMap or not by Generating Index By
	 * HashCode and checking in HashMAp for that Index
	 * 
	 * 2 - A Situation can persist Where diferent Key's generated HashCode index can
	 * collide if that happens we've got to Handling that Situation in put() Method
	 * 
	 * @author Nishant Tiwari
	 * @Created-at 27-07-2019
	 * @param key is given here as a Parameter to check for Above Situation using
	 *            generateKeyIndex() Method
	 * 
	 * @return Integer : { 0 : Key Index is not Present already 1 : Key Index is
	 *         Present but Key is Different (HashCode Index Clashing Occurred) -1 :
	 *         if Key is Duplicate (Duplicate Keys Cannot be Inserted) }
	 * 
	 *         generateKeyIndex() Returns unique Index for Every Unique Key, but An
	 *         Exception Can be happen That Index Can be clashed with Different
	 *         Keys, to Resolve That We Have Implemented A Resolution by Checking
	 *         key Integrity
	 *
	 * @see generateKeyIndex() Method
	 *
	 */
	@Override
	public int integrity(Object k) {

		if (Entry[generateKeyIndex(k)] == null)
			return 0;
		else if (Entry[generateKeyIndex(k)] != null && Entry[generateKeyIndex(k)].k != k)
			return 1;
		return -1;

	}

	/**
	 * This Method Generates the index using the Key "K" by using hashCode() to
	 * place the value on the unique index
	 * 
	 * @author Nishant Tiwari
	 * @Created-at 27-07-2019
	 * @param key is given here as a Parameter and hashCode also generates using the
	 *            key
	 * @return Integer : Return the generated Index in Integer Format
	 * 
	 *         Note : This Method Returns unique Index for Every Unique Key, but An
	 *         Exception Can be happen That Index Can be clashed with Different
	 *         Keys, to Resolve That We Have Implemented A Resolution by Checking
	 *         key Integrity
	 * @see integrity() Method
	 *
	 */

	private int generateKeyIndex(Object key) {
		return key == null ? 0 :((key.hashCode()) % extendedCapacity);
	}
	

	private class EntrySet implements Iterable<Node<K, V>> {

		private final EntryIterator entryIterator;
		Node<K,V> table[] = new Node[(int)filled];

		public EntrySet() {
			entryIterator = new EntryIterator();
		}
		
		@Override
		public String toString() {
			int nodeCount= 0;
			if(LinkedEntry) {
				Node<K, V> e = head;
				while(e != null) {
					if(nodeCount < filled) {
						table[nodeCount] = e;
						nodeCount++;
						e=e.after;
					}
				}
			}else {
				for(int i =0;i < keyIndex; i++) {
					Node<K, V> node = Entry[generateKeyIndex(key[i])];	
					
					if(node != null) {
						
						table[nodeCount] = node;
						while(node.next != null){
							nodeCount+=1;
							node = node.next;
							table[nodeCount] = node;
						}
						nodeCount+=1;
					}
					
				}
			}
			
			return Arrays.toString(table);
		}
		
		@Override
		public Iterator<Node<K, V>> iterator() {
			return entryIterator;
		}

		@Override
		public void forEach(Consumer<? super Node<K, V>> action) {
			// TODO Auto-generated method stub
			for(int i=0;i<Entry.length;i++) {
				for(Node<K, V> e = Entry[i]; e != null; e=e.next) {
					action.accept(e);
				}
			}
		}
		
	}

	private class EntryIterator implements Iterator<Node<K, V>> {

		private int keyIndex;
		public EntryIterator() {
			if (nullFlag)
				keyIndex = 0;
			else
				keyIndex = 1;
		}

		@Override
		public boolean hasNext() {
        	if(key[keyIndex] != null)
        		if(Entry[generateKeyIndex(key[keyIndex])] != null)
        			return true;
			return false;
		}

		@Override
		public Node<K, V> next() {
			Node<K, V> node = Entry[generateKeyIndex(key[keyIndex])];;
			keyIndex += 1;
			return node;
		}
	}
	
	/**
	 * Method to Check & invoke Table resizing if filled TressPassess the LoadFactor %
	 * @author Nishant Tiwari
	 * @Created-at 27-07-2019
	 * 
	 */
	private void checkForResize() {
		if ((filled / extendedCapacity) == loadFactor) {
			resize();
		}
	}
	
	/**
	 * Method to resizing if LoadFactor % gets TressPassed
	 * @author Nishant Tiwari
	 * @Created-at 27-07-2019
	 * 
	 */
	private void resize() {
		/*
		 * temporary Table 'table[]' to store existing Node
		 */
		
		extendedCapacity *= 2;
		//Resizing Entry Array
		Node<K, V> table[] = new Node[extendedCapacity];
		System.arraycopy(Entry, 0, table, 0, Entry.length);
		
		Entry = new Node[extendedCapacity];
//		System.arraycopy(table, 0, Entry, 0, keyIndex);
		keyIndex = 0;
		filled = 0.0;
		for(Node<K,V> n:table) {
			if(n != null)
				put(n.k,n.v);
			else
				System.out.println("Found a null Node Skipping");
			
		}
		
	}

	@Override
	public boolean containsKey(K k) {
		for(Object currentKey:key) {
			if(currentKey == k)
				return true;
		}
		return false;
	}


}
