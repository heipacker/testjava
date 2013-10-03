package test.dlmu.futuretask;

public interface Computable<K, V> {
	V compute(K k) throws InterruptedException;
}
