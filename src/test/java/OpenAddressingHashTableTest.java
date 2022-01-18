import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenAddressingHashTableTest
{

    @Test
    void testBasicFunctionality()
    {
        int size = 16;
        OpenAddressingHashTable<Integer, Integer> table = new OpenAddressingHashTable<>(64);
        assertTrue(table.isEmpty());
        assertEquals(0, table.size());
        
        for (int i = 0; i < size; i++)
        {
            table.put(i, i + 1);
            table.contains(i);
            assertEquals(i + 1, table.size());
        }
        
        for (int i = 0; i < size; i++)
        {
            assertEquals(i + 1, table.get(i));
        }
        assertFalse(table.isEmpty());
        
        for (int i = 0; i < size; i++)
        {
            Integer x = table.remove(i);
            assertEquals(i + 1, x);
            assertEquals(size - i - 1, table.size());
        }
        assertTrue(table.isEmpty());
    }
    
    @Test
    void testLinearProbing()
    {
        int size = 15;
        OpenAddressingHashTable<String, Integer> table = new OpenAddressingHashTable<>(size);
        
        assertFalse(OpenAddressingHashTable.isPower(size));
        assertTrue(OpenAddressingHashTable.isPower(table.getLength()));
        
        assertNotEquals("Aa", "BB");
        assertEquals("Aa".hashCode(), "BB".hashCode());
        
        table.put("Aa", 1);
        table.put("BB", 2);
        
        assertEquals(table.hash("Aa"), table.hash("BB"));
        assertEquals(2, table.size());
    
        int indexAa = table.getIndex("Aa") , indexBB = table.getIndex("BB");

        assertEquals((indexAa + 1) % table.getLength(), indexBB);
        
    }
    
    @Test
    void testRehashing()
    {
        int size = 16;
        OpenAddressingHashTable<Integer, Integer> table = new OpenAddressingHashTable<>(size);

        for (int i = 0; i < size; i++)
        {
            table.put(i, i + 1);
        }
        
        int[] hashCodesBinaryRepresentationStringLength = new int[size];
        for (int i = 0; i < size; i++)
        {
            hashCodesBinaryRepresentationStringLength[i] = table.hash_debug(i).length();
        }
        
        table.put(size, size + 1);
        for (int i = 0; i < size; i++)
        {
            assertEquals(hashCodesBinaryRepresentationStringLength[i] + 1, table.hash_debug(i).length());
        }
        
        assertEquals(table.getLength(), 2 * size);
        
        table.clear();
        
        assertTrue(table.isEmpty());
        assertEquals(0, table.size());
        
    }
}