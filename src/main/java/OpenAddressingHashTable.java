import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class OpenAddressingHashTable<K, V> implements Dictionary<K, V>
{
    private static final int DEFAULT_CAPACITY = 64;
    
    private Entry<K, V>[] table;
    private int           size;
    private byte[][]      hashingTable;
    private int           b;
    
    @SuppressWarnings("unused")
    public OpenAddressingHashTable()
    {
        this(DEFAULT_CAPACITY);
    }
    
    @SuppressWarnings("unchecked")
    public OpenAddressingHashTable(int capacity)
    {
        int n = isPower(capacity) && capacity != 0 ? capacity : (int) Math.pow(2 ,
                                                                               Math.ceil(Math.log(
                                                                                       capacity) /
                                                                                         Math.log(2)));
        
        table        = (Entry<K, V>[]) Array.newInstance(Entry.class , n);
        size         = 0;
        b            = (int) (Math.log(table.length) / Math.log(2));
        hashingTable = createNewHashingTable();
        
        //          FileHandler fh = null;
        //        try
        //        {
        //            fh = new FileHandler("log.txt");
        //        } catch (
        //    IOException e)
        //        {
        //            e.printStackTrace();
        //        }
        //        fh.setFormatter(new SimpleFormatter());
        //        logger.addHandler(fh);
        
    }
    
    @Override
    @TestedAndFunctional
    public void put(K key , V value)
    {
        rehashIfNecessary();
        put_us(key , value);
    }
    
    @TestedAndFunctional
    private void put_us(K key , V value)
    {
        if (key == null) throw new NoSuchElementException();
        
        int index = hash(key);
        
        int foundDummy = - 1;
        while (table[index] != null)
        {
            if ((table[index].getKey() == null && table[index].getValue() == null) &&
                foundDummy == - 1) foundDummy = index;
            
            if (table[index].getKey().equals(key))
            {
                table[index].setValue(value);
                return;
            }
            index = (index + 1) % table.length;
        }
        
        if (foundDummy != - 1) index = foundDummy;
        
        table[index] = new Entry<>(key , value);
        size++;
    }
    
    @Override
    @TestedAndFunctional
    public V remove(K key)
    {
        rehashIfNecessary();
        if (! this.contains(key))
        {
            throw new NoSuchElementException();
        }
        
        int index = hash(key);
        //        logger.info("Removing " + key + " from index " + index);
        
        while (true)
        {
            if (table[index].getKey() == null)
            {
                index = (index + 1) % table.length;
                continue;
            }
            
            if (table[index].getKey().equals(key)) break;
            
            index = (index + 1) % table.length;
        }
        
        V returnValue = table[index].getValue();
        
        table[index] = new Entry<>(null , null);
        
        size--;
        return returnValue;
    }
    
    @Override
    @TestedAndFunctional
    public V get(K key) throws NoSuchElementException
    {
        int index = hash(key);
        
        while (table[index] != null)
        {
            if (table[index].getKey() == null)
            {
                index = (index + 1) % table.length;
                continue;
            }
            
            if (table[index].getKey().equals(key)) return table[index].getValue();
            
            index = (index + 1) % table.length;
        }
        
        throw new NoSuchElementException();
    }
    
    @Override
    @TestedAndFunctional
    public boolean contains(K key)
    {
        try
        {
            get(key);
            return true;
        } catch (Exception e)
        {
            return false;
        }
    }
    
    @Override
    @TestedAndFunctional
    public boolean isEmpty()
    {
        return size == 0;
    }
    
    @Override
    @TestedAndFunctional
    public int size()
    {
        return size;
    }
    
    @Override
    @TestedAndFunctional
    public void clear()
    {
        Arrays.fill(table , null);
        size = 0;
    }
    
    @Override
    @TestedAndFunctional
    public Iterator<Dictionary.Entry<K, V>> iterator()
    {
        return new HashIterator();
    }
    
    @TestedAndFunctional
    public int hash(K key)
    {
        String temp = Integer.toBinaryString(key.hashCode());
        //        String keyHashBits_s = String.format("%32s" , temp).replace(' ' , '0');
        
        StringBuilder keyHashBits_s = new StringBuilder(temp);
        for (int i = 0; i < 32 - temp.length(); i++)
        {
            keyHashBits_s.insert(0 , '0');
        }
        byte[] keyHashBits = new byte[32];
        for (int i = 0; i < keyHashBits.length; i++)
        {
            keyHashBits[i] = (byte) (keyHashBits_s.charAt(i));
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
        
        return Integer.parseInt(returnValue_s , 2);
    }
    
    
    @TestedAndFunctional
    private void rehashIfNecessary()
    {
        int newSize;
        if (size >= table.length)
        {
            newSize = table.length * 2;
        } else if (size <= table.length / 4 && table.length > 2)
        {
            newSize = table.length / 2;
        } else
        {
            return;
        }
        OpenAddressingHashTable<K, V> newTable = new OpenAddressingHashTable<>(newSize);
        
        for (Dictionary.Entry<K, V> item : this)
        {
            newTable.put_us(item.getKey() , item.getValue());
        }
        
        this.b            = newTable.b;
        this.hashingTable = newTable.hashingTable;
        this.table        = newTable.table;
        this.size         = newTable.size;
    }
    
    private byte[][] createNewHashingTable()
    {
        byte[][] hashingTable = new byte[b][32];
        for (int i = 0; i < hashingTable.length; i++)
        {
            for (int j = 0; j < hashingTable[i].length; j++)
            {
                hashingTable[i][j] = (byte) (Math.random() * 2);
            }
        }
        return hashingTable;
        
    }
    
    @TestedAndFunctional
    public static boolean isPower(int n)
    {
        double x = Math.log(n) / Math.log(2);
        return (int) (Math.ceil(x)) == (int) (Math.floor(x));
    }
    
    //region Inner Classes
    public static class Entry<K, V> implements Dictionary.Entry<K, V>
    {
        private final K key;
        private       V value;
        
        public Entry(K key , V value)
        {
            this.key   = key;
            this.value = value;
        }
        
        
        @Override
        public String toString()
        {
            return "Entry{" + "key=" + key + ", value=" + value + '}';
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
        
        public void setValue(V value)
        {
            this.value = value;
        }
    }
    
    private class HashIterator implements Iterator<Dictionary.Entry<K, V>>
    {
        private int i;
        
        public HashIterator()
        {
            this.i = 0;
        }
        
        @Override
        @TestedAndFunctional
        public boolean hasNext()
        {
            while (i < table.length)
            {
                if (table[i] != null)
                {
                    if ((table[i].getKey() == null && table[i].getValue() == null))
                    {
                        i++;
                        continue;
                    }
                    
                    return true;
                }
                i++;
            }
            return false;
        }
        
        @Override
        @TestedAndFunctional
        public Entry<K, V> next()
        {
            if (! hasNext()) throw new NoSuchElementException();
            
            return table[i++];
        }
    }
    //endregion
    
    //region Test Methods
    public int getLength()
    {
        return table.length;
    }
    
    public int getIndex(K key)
    {
        int length = getLength();
        for (int i = 0; i < length; i++)
        {
            if (table[i] != null)
            {
                if (table[i].getKey().equals(key)) return i;
            }
        }
        
        return - 1;
    }
    
    public String hash_debug(K key)
    {
        String temp = Integer.toBinaryString(key.hashCode());
        //        String keyHashBits_s = String.format("%32s" , temp).replace(' ' , '0');
        StringBuilder keyHashBits_s = new StringBuilder(temp);
        for (int i = 0; i < 32 - temp.length(); i++)
        {
            keyHashBits_s.insert(0 , '0');
        }
        byte[] keyHashBits = new byte[32];
        for (int i = 0; i < keyHashBits.length; i++)
        {
            keyHashBits[i] = (byte)keyHashBits_s.charAt(i);
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
        
        return returnValue_s;
    }
    //endregion
}
