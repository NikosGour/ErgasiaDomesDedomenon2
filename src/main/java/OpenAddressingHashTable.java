import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class OpenAddressingHashTable<K, V> implements Dictionary<K,V>
{
    private static final int DEFAULT_CAPACITY = 64;
    public Entry<K,V>[] table;
    int size;
    Byte[][] hashingTable;
    int b;
    
    public OpenAddressingHashTable()
    {
        this(DEFAULT_CAPACITY);
    }
    
    @SuppressWarnings("unchecked")
    public OpenAddressingHashTable(int capacity)
    {
        table = (Entry<K, V>[]) Array.newInstance(Entry.class, capacity);
        size = 0;
        b = (int)(Math.log(table.length) / Math.log(2));
        hashingTable = createHashingTable();
        
    }
    @Override
    public void put(K key , V value)
    {
        if (size == table.length)
        {
            doubleCapacity();
        }
        int index = hash(key);
        while (true)
        {
            if (table[index] == null)
            {
                table[index] = new Entry<>(key , value);
                size++;
                return;
            } else
            {
                index = (index + 1) % table.length;
            }
        }
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
    public int hash(K key)
    {
        String temp = Integer.toBinaryString(key.hashCode());
        String keyHashBits_s = String.format("%32s", temp).replace(' ', '0');
        Byte[] keyHashBits = new Byte[32];
        for (int i = 0; i < keyHashBits.length; i++)
        {
            keyHashBits[i] = Byte.parseByte( Character.toString(keyHashBits_s.charAt(i)));
        }
        String returnValue_s = "";
    
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < this.hashingTable.length; i++)
        {
            int sum = 0;
            for (int j = 0; j < this.hashingTable[i].length; j++)
            {
                sum += this.hashingTable[i][j] * keyHashBits[j];
            }
            //noinspection StringConcatenationInLoop
            returnValue_s += sum % 2;
        }
        
        return Integer.parseInt(returnValue_s, 2);
    }
    private void rehash()
    {
    
    }
    
    private Byte[][] createHashingTable()
    {
        Byte[][] hashingTable = new Byte[b][32];
        for (int i = 0; i < hashingTable.length; i++)
        {
            for (int j = 0; j < hashingTable[i].length; j++)
            {
                hashingTable[i][j] = (byte) (Math.random() * 2);
            }
        }
        return hashingTable;
    
    }
    
    private static class Entry<K, V> implements Dictionary.Entry<K, V>
    {
        private K key;
        private V value;
    
        public Entry(K key , V value)
        {
            this.key   = key;
            this.value = value;
        }
    
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
