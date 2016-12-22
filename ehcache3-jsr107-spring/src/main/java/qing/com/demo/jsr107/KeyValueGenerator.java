package qing.com.demo.jsr107;

public interface KeyValueGenerator<K, V> {
	
	 K getKey(Number k);

	 V getValue(Number v);
}
