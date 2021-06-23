package CustomHashMap;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A Drive Class For Custom HashMap Implementation
 * @author Nis-H-Ant
 * @Created-at : 27-07-2019
 */
public class HashMapDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Map<Integer,String>hmo = new LinkedHashMap();
//		Map<Integer,String>hm = new HashMap();
//		hm.put("vsvds", 55);
		HashmapCore<Integer,String> hm = new HashmapCore<>();
		
		for(int i=0;i<250;i++)
			hm.put( i*i , i+"");
		
		System.out.println("Get : -->"+hm.linkedEntrySet());

		
		
//		hm.put(null, "hello");
//		
//		
//		
////		System.out.println("value 5 00 " + hm.get(500));
//		
//		System.out.println( hm.entrySet());
//		
//		System.out.println(hm.linkedEntrySet());
//		
//		System.out.println( hm.entrySet());
		
		

	}

}
