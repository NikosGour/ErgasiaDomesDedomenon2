import java.util.Iterator;

public class OpenAddressingHashTable<K, V> implements Dictionary<K, V>
{
    @Override
    public void put(K key , V value)
    {
    
    }
    
    @Override
    public V remove(K key)
    {
        return null;
    }
    
    @Override
    public V get(K key)
    {
        return null;
    }
    
    @Override
    public boolean contains(K key)
    {
        return false;
    }
    
    @Override
    public boolean isEmpty()
    {
        return false;
    }
    
    @Override
    public int size()
    {
        return 0;
    }
    
    @Override
    public void clear()
    {
    
    }
    
    @Override
    public Iterator<Dictionary.Entry<K, V>> iterator()
    {
        return null;
    }
    
    private static class Entry<K, V> implements Dictionary.Entry<K, V>
    {
        private K key;
        private V value;
        
        @Override
        public K getKey()
        {
            return key;
        }
        
        @Override
        public V getValue()
        {
            return value;
        }
    }
}
