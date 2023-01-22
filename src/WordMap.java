import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WordMap<K,V> implements Map<K,V> {
    private ProbeHashMap<K,V> wordMap ;
    public WordMap(){
        wordMap = new ProbeHashMap<>() ;
    }
    @Override
    public int size() { return wordMap.size();}
    @Override
    public boolean isEmpty() { return wordMap.isEmpty() ;}
    @SuppressWarnings("unchecked")
	@Override
    public boolean containsKey(Object key) { return wordMap.containsKey((K) key);}
    @Override
    public boolean containsValue(Object value) {
    	for (K key : wordMap.keySet()) {
    		if (wordMap.get(key).equals(value)) return true ;
    	}
    	return false ;
    	}
    @SuppressWarnings("unchecked")
	@Override
    public V get(Object key) {return wordMap.get((K) key);}
    @Override
    public V put(K key, V value) { return wordMap.put(key, value) ;}
    @SuppressWarnings("unchecked")
	@Override
    public V remove(Object key) {return wordMap.remove((K) key);}
    @Override
    public Set<K> keySet() {return   (Set<K>) wordMap.keySet();}
    @Override
    public Collection<V> values() {return (Collection<V>) wordMap.values();}
    @Override
    public void putAll(Map<? extends K,? extends V> m) {
    	for (K key : m.keySet()) {
    		wordMap.put(key,m.get(key)) ;
    	}
    }
    @Override
    public Set<Entry<K, V>> entrySet() {
    	Map<K, V> m = new HashMap<>();
    	putAll(m) ;
    	return m.entrySet() ;
    } 
    @Override
    public void clear() { 
    	for (K key : wordMap.keySet()) {
    		wordMap.remove(key) ; 
    	}
    }
}
