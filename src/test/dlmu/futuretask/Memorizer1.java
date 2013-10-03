package test.dlmu.futuretask;

import java.util.HashMap;
import java.util.Map;

public class Memorizer1<K, V> implements Computable<K, V> {
	
	private final Map<K, V> cache = new HashMap<K, V>();
	
	private final Computable<K, V> c;
	
	public Memorizer1(Computable<K, V> c){
		this.c = c;
	}
	
	@Override
	public synchronized V compute(K key) throws InterruptedException {
		V result = cache.get(key);
		if(result==null){
			result = c.compute(key);
			
			cache.put(key, result);
		}
		
		return result;
	}

}
