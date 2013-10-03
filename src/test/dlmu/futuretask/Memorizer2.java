package test.dlmu.futuretask;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Memorizer2<K, V> implements Computable<K, V> {
	private final Map<K, V> cache = new ConcurrentHashMap<K, V>();
	
	private final Computable<K, V> c;
	
	public Memorizer2(Computable<K, V> c){
		this.c = c;
	}
	@Override
	public V compute(K key) throws InterruptedException {
		V result = cache.get(key);
		if(result==null){
			result = c.compute(key);
			
			cache.put(key, result);
		}
		
		return result;
	}
	
	
}
