package CustomHashMap;

import java.util.Iterator;

/**
 * Model For Key Value Pair
 * this class will as an Entity of particular object to 
 * store in HashMap
 * 
 * @author NisHAnt Tiwari
 * @Created at-28-07-2019
 * @param <K>
 * @param <V>
 * @see HashMapCore
 * @see CustomMap
 */
public class Node<K,V> {
	
	K k;
	V v;
	
	/*
	 * In Case of Index Clashing We Are Going to store Key-Value in nested Entity
	 * on the Same Index Entity
	 */
	Node<K, V> next ;
	Node<K, V> after;
	
	public Node(K k, V v) {
		super();
		this.k = k;
		this.v = v;
	}
	
	public Node(K k) {
		super();
		this.k = k;
	}

	@Override
	public String toString() {
		return k+"="+v;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int hash = 7;
	    hash = 31 * hash + (k == null ? 0 : k.hashCode());
		return hash;
	}

	
	
	
	
	
	

}
