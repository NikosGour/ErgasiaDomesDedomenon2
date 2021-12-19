import java.util.Arrays;
import java.util.Iterator;

public class OpenAddressingHashTable<K, V> implements Dictionary<K, V>
{
    private static final int DEFAULT_CAPACITY = 64;
    Entry<K,V>[] table;
    int size;
    
    public OpenAddressingHashTable()
    {
        this(DEFAULT_CAPACITY);
    }
    @SuppressWarnings("unchecked")
    public OpenAddressingHashTable(int capacity)
    {
        table = (Entry<K, V>[]) new Object[capacity];
        size = 0;
    }
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
        return size == 0;
    }
    
    @Override
    public int size()
    {
        return size;
    }
    
    @Override
    public void clear()
    {
        Arrays.fill(table , null);
        size = 0;
    }
    
    @Override
    public Iterator<Dictionary.Entry<K, V>> iterator()
    {
        return null;
    }
    
    private void doubleCapacity()
    {
    
    }
    private void halfCapacity()
    {
    
    }
    private int hash(K key)
    {
        return 0;
    }
    private void rehash()
    {
    
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
