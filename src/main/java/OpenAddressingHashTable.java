import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class OpenAddressingHashTable<K, V> implements Dictionary<K,V>
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
    public V get(K key) throws NoSuchElementException
    {
    
        for (Dictionary.Entry<K, V> item : this)
        {
            if (item.getKey().equals(key))
            {
                return item.getValue();
            }
        }

        throw new NoSuchElementException();
    }
    
    @Override
    public boolean contains(K key)
    {
        try {
            get(key);
            return true;
        } catch(Exception e) {
            return false;
        }
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
        return new HashIterator();
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

    private class HashIterator implements Iterator<Dictionary.Entry<K, V>> {
        private int i;

        public HashIterator() {
            this.i = 0;
        }

        @Override
        public boolean hasNext() {
            while(i < table.length) {
                if(table[i] != null) {
                    return true;
                }
                i++;
            }

            return false;
        }

        @Override
        public Entry<K, V> next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            return table[i];
        }
    }
}
