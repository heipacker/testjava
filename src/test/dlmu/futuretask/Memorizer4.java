package test.dlmu.futuretask;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Memorizer4<K, V> implements Computable<K, V> {
	private final ConcurrentHashMap<K, Future<V>> cache = new ConcurrentHashMap<K, Future<V>>();
	
	private final Computable<K, V> c;
	
	public Memorizer4(Computable<K, V> c){
		this.c = c;
	}
	@Override
	public V compute(final K key) throws InterruptedException {
		Future<V> future = cache.get(key);
		if(future==null){
			Callable<V> eval = new Callable<V>(){

				@Override
				public V call() throws Exception {
					// TODO Auto-generated method stub
					return c.compute(key);
				}
				
			};
			FutureTask<V> f = new FutureTask<V>(eval);
			future = f;
			future = cache.putIfAbsent(key, f);
			if(future==null){
				future = f;
				f.run();
			}
		}
		
		try {
			return future.get();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			throw Util.launderThrowable(e.getCause());
		}
	}


}
