
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
*
* @author      Nathan Bussière, Sidya Galakho
* @matricule   20218547, 20207299
*/
public class FileMap<K,V> implements Map<K,V> {
    private Map<K,V> fileMap ;
    public FileMap(){
        fileMap = new HashMap<>() ;
    }
    @Override
    public int size() { return fileMap.size() ;}
    @Override
    public boolean isEmpty() { return fileMap.isEmpty() ;}
    @Override
    public boolean containsKey(Object key) { return fileMap.containsKey(key) ;}
    @Override
    public boolean containsValue(Object value) { return fileMap.containsValue(value) ;}
    @Override
    public V get(Object key) {return fileMap.get(key) ;}
    @Override
    public V put(K key, V value) { return fileMap.put(key, value) ;}
    @Override
    public V remove(Object key) { return fileMap.remove(key) ;}
    @Override
    public void putAll(Map<? extends K,? extends V> m) { fileMap.putAll(m);}
    @Override
    public void clear() { fileMap.clear();}
    @Override
    public Set<K> keySet() {return fileMap.keySet();}
    @Override
    public Collection<V> values() {return fileMap.values();}
    @Override
    public Set<Map.Entry<K,V>> entrySet() {return fileMap.entrySet() ;}
}
